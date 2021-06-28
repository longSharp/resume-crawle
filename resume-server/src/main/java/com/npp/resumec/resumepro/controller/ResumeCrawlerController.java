package com.npp.resumec.resumepro.controller;

import com.npp.resumec.resumepro.service.ResumeCrawlerService;
import com.npp.resumec.resumepro.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 龙朝敏
 * @describe
 * @create 2021-06-09
 */
@RestController
@RequestMapping("/crawler")
public class ResumeCrawlerController {
    @Autowired
    private ResumeCrawlerService resumeCrawlerService;

    @RequestMapping("/start")
    public Result start(){
        resumeCrawlerService.start();
        return Result.ok();
    }

    @RequestMapping("/ossStart")
    public Result ossStart(){
        resumeCrawlerService.start();
        return Result.ok();
    }
}
