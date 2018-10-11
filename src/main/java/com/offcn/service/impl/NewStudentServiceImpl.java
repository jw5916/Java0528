package com.offcn.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.offcn.mapper.NewstudentinfoMapper;
import com.offcn.pojo.Newstudentinfo;
import com.offcn.pojo.NewstudentinfoExample;
import com.offcn.pojo.NewstudentinfoExample.Criteria;
import com.offcn.service.NewStudentService;
import com.sun.tools.javac.util.List;

@Service
public class NewStudentServiceImpl implements NewStudentService {

	@Autowired
	private NewstudentinfoMapper newstudentinfoMapper;

	@Override
	public java.util.List<Newstudentinfo> getAllStudent() {
		
		NewstudentinfoExample example = new NewstudentinfoExample();
		java.util.List<Newstudentinfo> selectByExample = newstudentinfoMapper.selectByExample(example );
		return selectByExample;
	}

	@Override
	public void saveStudent(Newstudentinfo newstudentinfo) {
		
		int insert = newstudentinfoMapper.insert(newstudentinfo);
	}
	
	

}
