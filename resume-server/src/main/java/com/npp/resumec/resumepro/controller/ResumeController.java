package com.npp.resumec.resumepro.controller;

import com.npp.resumec.resumepro.pojo.Resume;
import com.npp.resumec.resumepro.service.ResumeService;
import com.npp.resumec.resumepro.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 龙朝敏
 * @describe
 * @create 2021-06-08
 */
@RestController
@RequestMapping("/resume")
public class ResumeController {

    @Autowired
    ResumeService resumeService;

    @RequestMapping(path = "/add",method = RequestMethod.POST)
    public Result save(@RequestBody Resume resume){
        this.resumeService.save(resume);
        return Result.ok();
    }

    @RequestMapping("/all")
    public Result getAll(){
        List<Resume> all = this.resumeService.findAll();
        return Result.ok().put("list",all);
    }

    @RequestMapping("/res/type")
    public Result getByTypeId(@RequestParam String id){
        List<Resume> byTypeId = this.resumeService.findByTypeId(id);
        return Result.ok().put("list",byTypeId);
    }

    @RequestMapping("/res/title")
    public Result getByTitle(@RequestParam String title,@RequestParam int page,@RequestParam int limit){
        ArrayList<Map<String, Object>> byTitle = this.resumeService.findByTitle(title,page,limit);
        return Result.ok().put("list",byTitle);
    }

    @RequestMapping("/res/page")
    public Result getByPage(@RequestParam int page,@RequestParam int limit){
        List<Resume> byPage = this.resumeService.findByPage(page, limit);
        return Result.ok().put("list",byPage);
    }

    @RequestMapping("/res/page/id")
    public Result getByPageById(@RequestParam int page,
                                @RequestParam int limit,
                                @RequestParam String typeId){
        List<Resume> byPage = this.resumeService.findByPage(page, limit, typeId);
        List<Resume> size = this.resumeService.findByTypeId(typeId);
        int pageSize = size.size()/ limit;
        pageSize += size.size()%limit == 0 ? 0 : 1;
        return Result.ok().put("list",byPage).put("pageSize",pageSize);
    }


}
