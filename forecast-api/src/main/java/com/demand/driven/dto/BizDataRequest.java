package com.demand.driven.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BizDataRequest {

    private String productCode;

    private String organizationCode;

    private String bizDataCode;

    private String bizDataType;

    private String period;

    private String value;

    private String unitCode;

    private String extension;
}
