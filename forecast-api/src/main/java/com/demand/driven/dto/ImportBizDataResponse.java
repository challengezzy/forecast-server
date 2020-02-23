package com.demand.driven.dto;

import lombok.Data;

/**
 * Created by zzy on 2019/11/3.
 */
@Data
public class ImportBizDataResponse {

    private boolean isSuccess;

    private String errorCode;

    private String errorMessage;

}
