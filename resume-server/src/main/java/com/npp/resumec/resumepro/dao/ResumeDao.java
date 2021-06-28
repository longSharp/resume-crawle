package com.npp.resumec.resumepro.dao;

import com.npp.resumec.resumepro.pojo.Resume;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 龙朝敏
 * @describe
 * @create 2021-06-08
 */

public interface ResumeDao {
    @Insert("insert into resumes(id,title,url,type_id) value('${resume.id}','${resume.title}','${resume.url}','${resume.typeId}')")
    void save(@Param("resume") Resume resume);

    @Select("select id,title,url,type_id as typeId from resumes")
    List<Resume> findAll();

    @Select("select id,title,url,type_id as typeId from resumes where type_id=#{id}")
    List<Resume> findByTypeId(String typeId);

    @Select("select id,title,url,type_id as typeId from resumes where title like concat('%',#{title},'%') limit #{page},#{limit}")
    List<Resume> findByTitle(String title,int page,int limit);

    @Select("select id,title,url,type_id as typeId from resumes limit #{page},#{limit}")
    List<Resume> findByPage(int page,int limit);

    @Select("select id,title,url,type_id as typeId from resumes where type_id=#{typeId} limit #{page},#{limit}")
    List<Resume> findByPageTo(int page,int limit,String typeId);

    @Select("select count(*) from resumes where title like concat('%',#{title},'%')")
    int findByTitleToPage(String title);
}
