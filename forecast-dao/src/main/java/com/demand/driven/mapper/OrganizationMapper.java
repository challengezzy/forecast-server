package com.demand.driven.mapper;

import com.demand.driven.entity.Organization;
import com.demand.driven.entity.Unit;
import org.springframework.stereotype.Repository;

/**
 * Created by zzy on 2019/11/2.
 */

@Repository
public interface OrganizationMapper {

    Organization getOrganizationByCode(String code);
}
