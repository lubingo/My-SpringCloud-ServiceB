package com.cloud.springcloud.ecommerce.order.service.imp;

import com.cloud.springcloud.ecommerce.order.dao.EcommerceOrderMapper;
import com.cloud.springcloud.ecommerce.order.entity.EcommerceOrder;
import com.cloud.springcloud.ecommerce.order.service.EcommerceOrderService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lubing
 * @describe 一句话描述
 * @Date 2019/12/19 19:03
 * @since
 */
@Service
public class EcommerceOrderServiceImp  implements EcommerceOrderService {

    @Autowired
    private EcommerceOrderMapper ecommerceOrderMapper ;

    public List<EcommerceOrder> selectOrderByExportPeopleCode(String exportPeopleCode){
        Map map = new HashMap() ;
        map.put("exportPeopleCode",exportPeopleCode);
        return ecommerceOrderMapper.selectOrderByExportPeopleCode(map);
    }
}
