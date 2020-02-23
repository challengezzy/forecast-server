package com.demand.driven.service;

import com.demand.driven.dto.BizDataRequest;
import com.demand.driven.dto.ImportBizDataResponse;
import com.demand.driven.entity.*;
import com.demand.driven.mapper.BizDataMapper;
import com.demand.driven.mapper.HistoryDataMapper;
import com.demand.driven.mapper.OrganizationMapper;
import com.demand.driven.mapper.UnitMapper;
import com.demand.driven.support.ProductManager;
import com.demand.driven.util.DateUtil;
import com.demand.driven.vo.ImpDataValidateRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Created by zzy on 2019/11/3.
 */
@Service
@Slf4j
public class HisBizDataImportServiceImpl implements HisBizDataImportService {

    @Autowired
    private ProductManager productManager;
    @Autowired
    private OrganizationMapper organizationMapper;
    @Autowired
    private UnitMapper unitMapper;
    @Autowired
    private BizDataMapper bizDataMapper;
    @Autowired
    private HistoryDataMapper historyDataMapper;

    public ImportBizDataResponse importHizBizData(BizDataRequest bizDataRequest){

        ImportBizDataResponse response = new ImportBizDataResponse();
        //数据校验
        ImpDataValidateRes validateRes = validateData(bizDataRequest);
        if( !validateRes.isSuccess()){
            log.info("导入数据校验不通过，errorCode=[{}],errorMessage=[{}]",validateRes.getErrorCode(),validateRes.getErrorMessage());

            response.setSuccess(validateRes.isSuccess());
            response.setErrorCode(validateRes.getErrorCode());
            response.setErrorMessage(validateRes.getErrorMessage());

            return response;
        }

        //更新或插入业务数据
        HistoryData queryData = new HistoryData();
        //根据校验时获得的数据，构造查询条件对象
        queryData.setProductId(validateRes.getProduct().getId());
        queryData.setOrganizationId(validateRes.getOrganization().getId());
        queryData.setBizDataId(validateRes.getBizData().getId());
        queryData.setPeriod(bizDataRequest.getPeriod());

        HistoryData oldHisData = historyDataMapper.getDataByPOBP(queryData);
        String timeStr = DateUtil.formatDateLongStr(new Date());
        String impModel;
        if(oldHisData != null){
            //更新数据
            oldHisData.setVersion( oldHisData.getVersion() + 1);
            oldHisData.setComments("["+ timeStr +"]接口导入更新");
            oldHisData.setValue(new Double(bizDataRequest.getValue()));

            historyDataMapper.update(oldHisData);
            impModel = "update";
        }else{
            //插入数据
            queryData.setVersion(1);
            queryData.setComments("["+ timeStr +"]接口导入新增");
            queryData.setValue(new Double(bizDataRequest.getValue()));

            historyDataMapper.insert(queryData);
            impModel = "insert";
        }

        log.info("历史业务数据导入[{}]成功,bizDataRequest=[{}]", impModel, bizDataRequest);
        response.setSuccess(true);
        return response;
    }

    public ImpDataValidateRes validateData(BizDataRequest bizDataRequest){
        ImpDataValidateRes res = new ImpDataValidateRes();
        res.setSuccess(false);

        //产品检验
        Product product =  productManager.getProductByCode(bizDataRequest.getProductCode());
        if(product == null || product.getIsvalid() != 1){
            res.setErrorCode("101");
            res.setErrorMessage("产品编码不合法");
            return res;
        }
        res.setProduct(product);

        //组合合法性校验
        Organization organization =  organizationMapper.getOrganizationByCode(bizDataRequest.getOrganizationCode());
        if(organization == null || organization.getIsvalid() != 1){
            res.setErrorCode("102");
            res.setErrorMessage("组织编码不合法");
            return res;
        }
        res.setOrganization(organization);

        //业务数据校验
        BizData bizData =  bizDataMapper.getBizDataByCode(bizDataRequest.getBizDataCode());
        if(bizData != null && bizData.getIsvalid() == 1 ){
            if( !(bizData.getType() == 0 || bizData.getType() == 1) ){
                //不是 有效的、历史或历史调整类业务数据
                res.setErrorCode("103");
                res.setErrorMessage("业务数据类型不合法");
                return res;
            }
        }else{
            res.setErrorCode("103");
            res.setErrorMessage("业务数据编码不合法");
            return res;
        }
        res.setBizData(bizData);

        //期间校验
        if( !isValidPeriodFormat(bizDataRequest.getPeriod()) ){
            res.setErrorCode("104");
            res.setErrorMessage("期间值不合法");
            return res;
        }

        //数据数据值校验
        if( !isInteger(bizDataRequest.getValue()) ){
            res.setErrorCode("105");
            res.setErrorMessage("业务数据值不合法");
            return res;
        }

        //单位编码校验
        Unit unit =  unitMapper.getUnitByCode(bizDataRequest.getUnitCode());
        if(unit == null ){
            res.setErrorCode("106");
            res.setErrorMessage("单位编码不合法");
            return res;
        }

        res.setSuccess(true);
        return res;
    }

    /**
     * 是否合法数字
     * @param str
     * @return
     */
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * 是否合法期间
     */
    public static boolean isValidPeriodFormat(String periodStr) {
        boolean convertSuccess=true;

        if(StringUtils.isEmpty(periodStr) || periodStr.length() != 6 ){
            return false;
        }

        if( periodStr.compareTo("201001") < 0 || periodStr.compareTo("202501") > 0 ){
            return false;
        }

        // 指定月分格式为yyyyMM；
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        try {
            // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(false);
            format.parse(periodStr);
        } catch (ParseException e) {
            // e.printStackTrace();
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            convertSuccess=false;
        }
        return convertSuccess;
    }

}
