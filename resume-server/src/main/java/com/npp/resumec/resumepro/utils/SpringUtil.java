package com.npp.resumec.resumepro.utils;

import com.npp.resumec.resumepro.dao.ResumeConDao;
import com.npp.resumec.resumepro.dao.ResumeDao;
import com.npp.resumec.resumepro.dao.ResumeTypeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author 龙朝敏
 * @describe
 * @create 2021-06-09
 */
@Component
public class SpringUtil {
    @Autowired
    private ResumeDao resumeDao;

    @Autowired
    private ResumeTypeDao resumeTypeDao;

    @Autowired
    private ResumeConDao resumeConDao;

    private static SpringUtil springUtil;

    @PostConstruct
    public void init(){
        springUtil = this;
        springUtil.resumeDao = this.resumeDao;
        springUtil.resumeConDao = this.resumeConDao;
        springUtil.resumeTypeDao = this.resumeTypeDao;
    }

}
