package com.npp.resumec.resumepro.service;

import com.npp.resumec.resumepro.pojo.ResumeCon;

/**
 * @author 龙朝敏
 * @describe
 * @create 2021-06-08
 */
public interface ResumeConService {

    /**
     * 保存简历详情信息
     * @param resumeCon
     */
    void save(ResumeCon resumeCon);

    /**
     * 根据简历id查询详情
     * @param tId 简历id
     * @return 返回简历详情对象
     */
    ResumeCon findByTId(String tId);

}
