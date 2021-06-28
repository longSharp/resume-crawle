package com.npp.resumec.resumepro.controller;

import com.npp.resumec.resumepro.pojo.ResumeType;
import com.npp.resumec.resumepro.service.ResumeTypeService;
import com.npp.resumec.resumepro.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 龙朝敏
 * @describe
 * @create 2021-06-09
 */
@RestController
@RequestMapping("/type")
public class ResumeTypeController {

    @Autowired
    private ResumeTypeService resumeTypeService;

    @RequestMapping(path = "/save",method = RequestMethod.POST)
    public Result save(@RequestBody ResumeType resumeType){
        this.resumeTypeService.save(resumeType);
        return Result.ok();
    }

    @RequestMapping("/find/all")
    public Result getAll(){
        List<ResumeType> all = this.resumeTypeService.findAll();
        return Result.ok().put("list",all);
    }

}
