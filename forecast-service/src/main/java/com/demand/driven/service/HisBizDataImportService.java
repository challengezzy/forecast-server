package com.demand.driven.service;

import com.demand.driven.dto.BizDataRequest;
import com.demand.driven.dto.ImportBizDataResponse;

/**
 * Created by zzy on 2019/11/3.
 */
public interface HisBizDataImportService {

    public ImportBizDataResponse importHizBizData(BizDataRequest bizDataRequest);
}
