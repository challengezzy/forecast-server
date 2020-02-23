package com.demand.driven.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.demand.driven.dto.BaseRequest;
import com.demand.driven.dto.BaseResponse;
import com.demand.driven.dto.BizDataRequest;
import com.demand.driven.dto.ImportBizDataResponse;
import com.demand.driven.service.HisBizDataImportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;

import static com.demand.driven.dto.ResultType.SUCCESS;

@RestController
@Validated
@Slf4j
public class ForecastDataImportController {

    @Autowired
    private HisBizDataImportService hisBizDataImportService;

    @PostMapping("/dataImport/hisBizDataSingle")
    public BaseResponse<String> dataImport(BaseRequest<String> dataReqeust) {

        BizDataRequest bizDataReq = JSON.parseObject(dataReqeust.getData(),BizDataRequest.class);
        log.info("预测业务数据导入，request={}", JSON.toJSONString(bizDataReq));

        ImportBizDataResponse bizDataResponse = hisBizDataImportService.importHizBizData(bizDataReq);

        BaseResponse response;
        if(bizDataResponse.isSuccess()){
            response = BaseResponse.success(bizDataResponse);
        }else{
            response = BaseResponse.error(bizDataResponse.getErrorCode(),bizDataResponse.getErrorMessage());
        }

        log.info("预测业务数据导入，response={}", JSON.toJSONString(response));
        return response;
    }

    @RequestMapping( value="/dataImport/test", method = RequestMethod.GET)
    public BaseResponse<String> dataImport(@RequestParam String appId) {
        log.info("test request={}", appId);
        BaseResponse response = buildResponse();
        log.info("test response={}", JSON.toJSONString(response));
        return response;
    }

    private BaseResponse buildResponse() {
      return   new BaseResponse().builder().status(SUCCESS.getCode()).data("").build();
    }

}
