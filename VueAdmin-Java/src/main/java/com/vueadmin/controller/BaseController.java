package com.vueadmin.controller;

import com.vueadmin.service.*;
import com.vueadmin.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

public class BaseController {
    @Autowired
    HttpServletRequest req;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    MenuService menuService;

    @Autowired
    RoleUserService roleUserService;

    @Autowired
    RoleMenuService roleMenuService;
}