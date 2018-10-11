package com.offcn.mapper;

import com.offcn.pojo.Newstudentinfo;
import com.offcn.pojo.NewstudentinfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NewstudentinfoMapper {
    int countByExample(NewstudentinfoExample example);

    int deleteByExample(NewstudentinfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Newstudentinfo record);

    int insertSelective(Newstudentinfo record);

    List<Newstudentinfo> selectByExample(NewstudentinfoExample example);

    Newstudentinfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Newstudentinfo record, @Param("example") NewstudentinfoExample example);

    int updateByExample(@Param("record") Newstudentinfo record, @Param("example") NewstudentinfoExample example);

    int updateByPrimaryKeySelective(Newstudentinfo record);

    int updateByPrimaryKey(Newstudentinfo record);
    
    void save(Newstudentinfo record);

}