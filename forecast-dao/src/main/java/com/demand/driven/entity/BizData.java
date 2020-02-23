package com.demand.driven.entity;

import lombok.Data;

/**
 * Created by zzy on 2019/11/2.
 */
@Data
public class BizData {

    private Long id;
    private String code;
    private String name;
    private Integer type; //业务数据类型, 0-历史数据, 1-历史调整数据
    private Integer isvalid; //是否有效
}
