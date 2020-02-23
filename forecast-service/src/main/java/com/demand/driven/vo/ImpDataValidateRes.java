package com.demand.driven.vo;

import com.demand.driven.entity.BizData;
import com.demand.driven.entity.Organization;
import com.demand.driven.entity.Product;
import com.demand.driven.entity.Unit;
import lombok.Data;

/**
 * Created by zzy on 2019/11/3.
 */
@Data
public class ImpDataValidateRes {

    private boolean isSuccess;
    private String errorCode;
    private String errorMessage;

    private Product product;
    private Organization organization ;
    private Unit unit;
    private BizData bizData;

}
