package com.offcn.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.offcn.pojo.Newstudentinfo;
import com.offcn.service.NewStudentService;

//这里将要进行分支的合并操作

@Controller
public class DownloadController {

	@Autowired
	private NewStudentService newStudentService;
	
	@RequestMapping(value="downloadexcel")
	public void downloadexcel(HttpServletRequest request,HttpServletResponse response,Model model) throws FileNotFoundException, IOException{
		List<Newstudentinfo> list = newStudentService.getAllStudent();
				//获取服务器端路径
				String path=request.getServletContext().getRealPath("down");
				String fileName="testexcel.xlsx";
				//创建存储File
				File targetFile=new File(path+"\\"+fileName);
				//创建存储目录
				File targetPath=new File(path);				
				
				//判断服务器端目录是否存在,如果不存在创建目录
				if(!targetPath.exists()){
					targetPath.mkdir();
				}
				//生成excel
		XSSFWorkbook workbook=new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("学生成绩表");
		int rownum=0;
		for(Newstudentinfo stu:list){
			System.out.println("学生信息:"+stu);
			//id\姓名\成绩\手机号码
			XSSFRow row = sheet.createRow(rownum);			
			row.createCell(0).setCellValue(stu.getId());
			row.createCell(1).setCellValue(stu.getName());
			row.createCell(2).setCellValue(stu.getScore());
			row.createCell(3).setCellValue(stu.getPhone());
			rownum++;
		}
		//把工作薄对象写入服务器端磁盘
		System.out.println("创建文件:"+targetFile);
		workbook.write(new FileOutputStream(targetFile));
		//设定响应头，服务器通知浏览器，要下载的文件类型
		response.setContentType("application/x-xls;charset=GBK");
		//设定浏览器下载提示
		response.setHeader("Content-Disposition", "attachment;filename=\"" + new String(fileName.getBytes(), "ISO8859-1") + "\"");
		
		//确定响应的文件的长度
		response.setContentLength((int) targetFile.length());
		//向响应文件流缓冲区写入文件
		byte[] buf=new byte[4096];
		BufferedOutputStream output=null;
		BufferedInputStream input=null;
		
		output=new BufferedOutputStream(response.getOutputStream());
		input=new BufferedInputStream(new FileInputStream(targetFile));
		
		//便厉文件
		int len=-1;
		
		while((len=input.read(buf))!=-1){
			output.write(buf,0,len);
		}
		output.flush();
		response.flushBuffer();
		
		if(input!=null){
			input.close();
		}if(output!=null){
			output.close();
		}
		
	}

}
