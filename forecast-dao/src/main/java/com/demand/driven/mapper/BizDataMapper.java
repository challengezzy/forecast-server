package com.demand.driven.mapper;

import com.demand.driven.entity.BizData;
import com.demand.driven.entity.Unit;
import org.springframework.stereotype.Repository;

/**
 * Created by zzy on 2019/11/2.
 */

@Repository
public interface BizDataMapper {

    BizData getBizDataByCode(String code);
}
