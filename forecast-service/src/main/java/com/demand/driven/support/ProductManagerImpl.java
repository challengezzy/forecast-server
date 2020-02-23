package com.demand.driven.support;

import com.demand.driven.mapper.ProductMapper;
import com.demand.driven.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zzy on 2019/11/2.
 */
@Service
public class ProductManagerImpl implements ProductManager{

    @Autowired
    private ProductMapper productMapper;

    /**
     * 根据产品编码获取产品信息
     * @param code
     * @return
     */
    public Product getProductByCode(String code){

        return productMapper.getProductByCode(code);
    }

}
