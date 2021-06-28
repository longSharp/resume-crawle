package com.npp.resumec.resumepro.service.impl;

import com.npp.resumec.resumepro.dao.ResumeTypeDao;
import com.npp.resumec.resumepro.pojo.ResumeType;
import com.npp.resumec.resumepro.service.ResumeTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author 龙朝敏
 * @describe
 * @create 2021-06-08
 */
@Service
public class ResumeTypeServiceImpl implements ResumeTypeService {

    @Autowired
    ResumeTypeDao resumeTypeDao;

    @Override
    public void save(ResumeType resumeType) {
        UUID uuid = UUID.randomUUID();
        String s = uuid.toString().replace("-","").toLowerCase();
        resumeType.setId(s);
        this.resumeTypeDao.save(resumeType);
    }

    @Override
    public List<ResumeType> findAll() {
        return resumeTypeDao.findAll();
    }
}
