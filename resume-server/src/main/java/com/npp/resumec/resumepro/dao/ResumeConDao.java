package com.npp.resumec.resumepro.dao;

import com.npp.resumec.resumepro.pojo.ResumeCon;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author 龙朝敏
 * @describe
 * @create 2021-06-08
 */
public interface ResumeConDao {
    @Insert("insert into resume_con(img_url,download_url,title,t_id) value('${resumeCon.imgUrl}','${resumeCon.downloadUrl}','${resumeCon.title}','${resumeCon.tId}')")
    void save(@Param("resumeCon") ResumeCon resumeCon);

    @Select("select id,img_url as imgUrl,download_url as downloadUrl,title,t_id as tId from resume_con where t_id=#{tId}")
    ResumeCon findByTId(String tId);
}
