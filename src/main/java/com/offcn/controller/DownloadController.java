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

//���ｫҪ���з�֧�ĺϲ�����

@Controller
public class DownloadController {

	@Autowired
	private NewStudentService newStudentService;
	
	@RequestMapping(value="downloadexcel")
	public void downloadexcel(HttpServletRequest request,HttpServletResponse response,Model model) throws FileNotFoundException, IOException{
		List<Newstudentinfo> list = newStudentService.getAllStudent();
				//��ȡ��������·��
				String path=request.getServletContext().getRealPath("down");
				String fileName="testexcel.xlsx";
				//�����洢File
				File targetFile=new File(path+"\\"+fileName);
				//�����洢Ŀ¼
				File targetPath=new File(path);				
				
				//�жϷ�������Ŀ¼�Ƿ����,��������ڴ���Ŀ¼
				if(!targetPath.exists()){
					targetPath.mkdir();
				}
				//����excel
		XSSFWorkbook workbook=new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("ѧ���ɼ���");
		int rownum=0;
		for(Newstudentinfo stu:list){
			System.out.println("ѧ����Ϣ:"+stu);
			//id\����\�ɼ�\�ֻ�����
			XSSFRow row = sheet.createRow(rownum);			
			row.createCell(0).setCellValue(stu.getId());
			row.createCell(1).setCellValue(stu.getName());
			row.createCell(2).setCellValue(stu.getScore());
			row.createCell(3).setCellValue(stu.getPhone());
			rownum++;
		}
		//�ѹ���������д��������˴���
		System.out.println("�����ļ�:"+targetFile);
		workbook.write(new FileOutputStream(targetFile));
		//�趨��Ӧͷ��������֪ͨ�������Ҫ���ص��ļ�����
		response.setContentType("application/x-xls;charset=GBK");
		//�趨�����������ʾ
		response.setHeader("Content-Disposition", "attachment;filename=\"" + new String(fileName.getBytes(), "ISO8859-1") + "\"");
		
		//ȷ����Ӧ���ļ��ĳ���
		response.setContentLength((int) targetFile.length());
		//����Ӧ�ļ���������д���ļ�
		byte[] buf=new byte[4096];
		BufferedOutputStream output=null;
		BufferedInputStream input=null;
		
		output=new BufferedOutputStream(response.getOutputStream());
		input=new BufferedInputStream(new FileInputStream(targetFile));
		
		//�����ļ�
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
