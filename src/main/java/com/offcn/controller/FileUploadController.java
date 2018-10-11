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
		
		/*��ȡ���������ϴ�Ŀ¼��·�������ϴ����ļ��洢��upload�ļ��У�*/
		String path = request.getServletContext().getRealPath("upload");
		/*��ȡ�ϴ��ļ���ԭʼ����*/
		String filename = file.getOriginalFilename();
		File targetFile = new File(path + "\\" + filename);
		/*�ж�upload�ļ����Ƿ���ڣ���������ڣ�����upload�ļ���*/
		File targetPath = new File(path);
		
		if(!targetPath.exists()) {
			
			targetPath.mkdir();
		}
		/*���ϴ����ļ��洢����������*/
		try {
			file.transferTo(targetFile);
		}catch(IllegalStateException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		/*��ȡ�ϴ����������˵��ļ�,����excel*/
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
						/*�������ָ�ʽ��������*/
						DecimalFormat df = new DecimalFormat("####");
						/*�Ѵ�cell��Ԫ���ȡ��������,���и�ʽ����ֹ��ѧ��������ʽ��ʾ*/
						buffer.append(df.format(cell.getNumericCellValue()) + "~");
					}
				}
				
				/*��Ԫ��ѭ����ɺ��ȡ������һ������  1~xingming~88*/
				String hang = buffer.toString();
				String[] rows = hang.split("~");
				Newstudentinfo newstudentinfo = new Newstudentinfo();
				newstudentinfo.setName(rows[1]);
				newstudentinfo.setScore(Double.valueOf(rows[2]));
				newstudentinfo.setPhone(rows[3]);
				System.out.println("�ϴ�ѧ����Ϣ��" + newstudentinfo);
				newStudentService.saveStudent(newstudentinfo);
			}
		}catch (InvalidFormatException | IOException e) {
			e.printStackTrace();
		}
		
		return "success";
	}
	
}