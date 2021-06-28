package com.npp.resumec.resumepro.dao;

import com.npp.resumec.resumepro.pojo.ResumeType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 龙朝敏
 * @describe
 * @create 2021-06-08
 */
public interface ResumeTypeDao {
    @Insert("insert into resume_type(id,type_name) value('${resumeType.id}','${resumeType.typeName}')")
    void save(@Param("resumeType") ResumeType resumeType);

    @Select("select id,type_name as typeName from resume_type")
    List<ResumeType> findAll();
}
