package com.demand.driven.mapper;

import com.demand.driven.entity.BizData;
import com.demand.driven.entity.HistoryData;
import org.springframework.stereotype.Repository;

/**
 * Created by zzy on 2019/11/2.
 */

@Repository
public interface HistoryDataMapper {

    HistoryData getDataByPOBP(HistoryData queryData);

    int insert(HistoryData newData);

    int update(HistoryData uptData);
}
