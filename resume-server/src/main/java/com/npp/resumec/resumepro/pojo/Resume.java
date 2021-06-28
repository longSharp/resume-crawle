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
public class Resume {
    private String id;
    private String title;
    private String url;
    private String typeId;
}
