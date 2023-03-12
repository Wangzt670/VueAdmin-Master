package com.vueadmin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vueadmin.entity.Indent;
import com.vueadmin.service.*;
import com.vueadmin.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;

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

    @Autowired
    VillageService villageService;

    @Autowired
    CarService carService;

    @Autowired
    ParkService parkService;

    @Autowired
    OrderService orderService;

    @Autowired
    IndentService indentService;

    //获取页码
    public Page getPage(){

        int current = ServletRequestUtils.getIntParameter(req,"current",1);
        int size = ServletRequestUtils.getIntParameter(req,"size",10);

        return new Page(current,size);
    }
}