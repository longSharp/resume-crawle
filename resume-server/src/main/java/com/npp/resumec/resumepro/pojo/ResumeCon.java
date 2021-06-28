package com.npp.resumec.resumepro.pojo;

import lombok.Data;
import lombok.ToString;
/**
 * @author 龙朝敏
 * @describe
 * @create 2021-06-08
 */
@Data
@ToString
public class ResumeCon {
    private Integer id;
    private String imgUrl;
    private String downloadUrl;
    private String title;
    private String tId;
}
