package com.demand.driven.entity;

import lombok.Data;

/**
 * Created by zzy on 2019/11/2.
 */
@Data
public class Unit {

    private Long id;
    private String code;
    private String name;
    private Long exchangerate; //与基本单位转换比例
}
