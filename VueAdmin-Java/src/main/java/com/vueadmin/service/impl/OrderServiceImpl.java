package com.vueadmin.service.impl;

import com.vueadmin.entity.Order;
import com.vueadmin.mapper.OrderMapper;
import com.vueadmin.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 王正霆201942429
 * @since 2023-02-28
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

}
