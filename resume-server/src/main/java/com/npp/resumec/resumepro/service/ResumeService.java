package com.npp.resumec.resumepro.service;

import com.npp.resumec.resumepro.pojo.Resume;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 龙朝敏
 * @describe
 * @create 2021-06-08
 */
public interface ResumeService{
    /**
     * 保存建立封面信息
     * @param resume 保存对象
     */
    void save(Resume resume);


    /**
     * 查询所有建立封面信息
     * @return 返回封面对象列表
     */
    List<Resume> findAll();

    /**
     * 根据类型id查询简历封面信息
     * @param typeId 类型id
     * @return 返回封面对象列表
     */
    List<Resume> findByTypeId(String typeId);

    /**
     * 根据简历名称模糊查询简历封面信息
     * @param title 查询的字段
     * @return 返回封面对象列表
     */
    ArrayList<Map<String, Object>> findByTitle(String title, int page, int limit);

    /**
     * 分页查询
     * @param page 页数
     * @param limit 每页条数
     * @return 返回封面对象列表
     */
    List<Resume> findByPage(int page,int limit);

    /**
     * 根据类型id分页查询
     * @param page 页数
     * @param limit 每页条数
     * @param typeId 类型id
     * @return 返回封面对象列表
     */
    List<Resume> findByPage(int page,int limit,String typeId);

    int findByTitleToPage(String title);
}
