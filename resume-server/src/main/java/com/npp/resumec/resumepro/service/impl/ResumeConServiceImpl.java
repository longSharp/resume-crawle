package com.npp.resumec.resumepro.service.impl;

import com.npp.resumec.resumepro.dao.ResumeConDao;
import com.npp.resumec.resumepro.pojo.ResumeCon;
import com.npp.resumec.resumepro.service.ResumeConService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 龙朝敏
 * @describe
 * @create 2021-06-08
 */
@Service
public class ResumeConServiceImpl implements ResumeConService {

    @Autowired
    ResumeConDao resumeConDao;

    @Override
    public void save(ResumeCon resumeCon) {
        this.resumeConDao.save(resumeCon);
    }

    @Override
    public ResumeCon findByTId(String tId) {
        return this.resumeConDao.findByTId(tId);
    }
}
