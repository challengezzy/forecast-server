package com.demand.driven.entity;

import lombok.Data;

/**
 * Created by zzy on 2019/11/2.
 */
@Data
public class HistoryData {

    private Long id;
    private Integer version; //数据版本号
    private Long productId;
    private Long bizDataId;
    private Long organizationId;
    private String period;
    private Double value; //是否有效
    private String comments; //备注，数据来源+时间
}
