package com.demand.driven.mapper;

import com.demand.driven.entity.Product;
import com.demand.driven.entity.Unit;
import org.springframework.stereotype.Repository;

/**
 * Created by zzy on 2019/11/2.
 */

@Repository
public interface UnitMapper {

    Unit getUnitByCode(String code);
}
