package com.vueadmin.service.impl;

import com.vueadmin.entity.User;
import com.vueadmin.mapper.UserMapper;
import com.vueadmin.service.UserService;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
