package com.npp.resumec.resumepro.controller;

import com.npp.resumec.resumepro.pojo.ResumeCon;
import com.npp.resumec.resumepro.service.ResumeConService;
import com.npp.resumec.resumepro.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 龙朝敏
 * @describe
 * @create 2021-06-09
 */

@RestController
@RequestMapping("/con")
public class ResumeConController {

    @Autowired
    private ResumeConService resumeConService;

    @RequestMapping(path = "/save",method = RequestMethod.POST)
    public Result save(@RequestBody ResumeCon resumeCon){
        this.resumeConService.save(resumeCon);
        return Result.ok();
    }

    @RequestMapping("/find/tid")
    public Result getByTId(@RequestParam String tid){
        ResumeCon byTId = this.resumeConService.findByTId(tid);
        return Result.ok().put("list",byTId);
    }

}
