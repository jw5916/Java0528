package com.offcn.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.offcn.pojo.Newstudentinfo;
import com.offcn.service.NewStudentService;

@Controller
public class FileUploadController {

	@Autowired
	private NewStudentService newStudentService;
	
	@RequestMapping("importexcel")
	public String uploadExcel(@RequestParam("file")MultipartFile file,HttpServletRequest request,Model model) throws Exception{
		
		/*获取服务器端上传目录的路径（把上传的文件存储到upload文件夹）*/
		String path = request.getServletContext().getRealPath("upload");
		/*获取上传文件的原始名称*/
		String filename = file.getOriginalFilename();
		File targetFile = new File(path + "\\" + filename);
		/*判断upload文件夹是否存在，如果不存在，创建upload文件夹*/
		File targetPath = new File(path);
		
		if(!targetPath.exists()) {
			
			targetPath.mkdir();
		}
		/*把上传的文件存储到服务器端*/
		try {
			file.transferTo(targetFile);
		}catch(IllegalStateException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		/*读取上传到服务器端的文件,遍历excel*/
		try {
			
			Workbook workbook = WorkbookFactory.create(targetFile);
			Sheet sheet = workbook.getSheet("Sheet1");
			int rownum = sheet.getPhysicalNumberOfRows();
			for (int i = 0; i < rownum; i++) {
				Row row = sheet.getRow(i);
				int cellnum = row.getPhysicalNumberOfCells();
				StringBuffer buffer = new StringBuffer();
				for (int j = 0; j < cellnum; j++) {
					Cell cell = row.getCell(j);
					if(cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
						buffer.append(cell.getStringCellValue() + "~");
					}else if(cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC){
						/*创建数字格式化工具类*/
						DecimalFormat df = new DecimalFormat("####");
						/*把从cell单元格读取到的数字,进行格式化防止科学计数法形式显示*/
						buffer.append(df.format(cell.getNumericCellValue()) + "~");
					}
				}
				
				/*单元格循环完成后读取到的是一行内容  1~xingming~88*/
				String hang = buffer.toString();
				String[] rows = hang.split("~");
				Newstudentinfo newstudentinfo = new Newstudentinfo();
				newstudentinfo.setName(rows[1]);
				newstudentinfo.setScore(Double.valueOf(rows[2]));
				newstudentinfo.setPhone(rows[3]);
				System.out.println("上传学生信息：" + newstudentinfo);
				newStudentService.saveStudent(newstudentinfo);
			}
		}catch (InvalidFormatException | IOException e) {
			e.printStackTrace();
		}
		
		return "success";
	}
	
}