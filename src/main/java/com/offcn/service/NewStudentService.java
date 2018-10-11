package com.offcn.service;

import com.offcn.pojo.Newstudentinfo;
import com.sun.tools.javac.util.List;

public interface NewStudentService {

	java.util.List<Newstudentinfo> getAllStudent();

	void saveStudent(Newstudentinfo newstudentinfo);

}
