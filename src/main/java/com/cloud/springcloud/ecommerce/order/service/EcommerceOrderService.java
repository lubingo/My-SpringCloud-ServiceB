package com.cloud.springcloud.ecommerce.order.service;

import com.cloud.springcloud.ecommerce.order.entity.EcommerceOrder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lubing
 * @describe 一句话描述
 * @Date 2019/12/19 13:36
 * @since
 */
@Service
public interface EcommerceOrderService {
    List<EcommerceOrder> selectOrderByExportPeopleCode(String exportPeopleCode);
}
