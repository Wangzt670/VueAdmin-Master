package com.vueadmin.service.impl;

import com.vueadmin.entity.Car;
import com.vueadmin.mapper.CarMapper;
import com.vueadmin.service.CarService;
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
public class CarServiceImpl extends ServiceImpl<CarMapper, Car> implements CarService {

}
