package com.demand.driven.support;

import com.demand.driven.entity.Product;

/**
 * Created by zzy on 2019/11/2.
 */
public interface ProductManager {

    public Product getProductByCode(String code);
}
