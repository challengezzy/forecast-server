package com.demand.driven.controller;

import com.alibaba.fastjson.JSONObject;
import com.demand.driven.entity.Product;
import com.demand.driven.support.ProductManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zzy on 2019/11/2.
 */
@RestController
@Slf4j
public class ProductController {

    @Autowired
    private ProductManager productManager;

    @RequestMapping(value="product/getProductByCode", method = RequestMethod.GET)
    public Product getProductByCode(@RequestParam String productCode ){
        log.info("getProductByCode, request={}", productCode);
        Product product = productManager.getProductByCode(productCode);
        log.info("getProductByCode, response={}", JSONObject.toJSONString(product));

        return product;
    }


}
