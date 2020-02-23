package com.demand.driven.entity;

import lombok.Data;

/**
 * Created by zzy on 2019/11/2.
 */
@Data
public class Product {

    private Long id;
    private String code;
    private String name;
    private Integer iscatalog;//是否目录
    private Integer isvalid; //是否有效
}
