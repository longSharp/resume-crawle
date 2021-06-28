package com.npp.resumec.resumepro.service;

import com.npp.resumec.resumepro.pojo.ResumeType;

import java.util.List;

/**
 * @author 龙朝敏
 * @describe
 * @create 2021-06-08
 */
public interface ResumeTypeService {

    /**
     * 保存分类信息
     * @param resumeType 分类对象
     */
    void save(ResumeType resumeType);

    /**
     * 获取所有类型
     * @return
     */
    List<ResumeType> findAll();

}
