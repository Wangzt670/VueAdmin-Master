package com.vueadmin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vueadmin.common.lang.Result;
import com.vueadmin.entity.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sta/staview")
public class StatisticsController extends BaseController{

    @GetMapping("/usertotal")
    public Result usertotal() {

        Page<User> pageData = userService.page(getPage(),
                new QueryWrapper<User>()
                        .in("statu",1)
        );

        return Result.succ(pageData);
    }

    @GetMapping("/villagetotal")
    public Result villagetotal() {

        Page<Village> pageData = villageService.page(getPage(),
                new QueryWrapper<Village>()
                        .in("statu",1)
        );

        return Result.succ(pageData);
    }

    @GetMapping("/parktotal")
    public Result parktotal() {

        Page<Park> pageData = parkService.page(getPage(),
                new QueryWrapper<Park>()
                        .in("statu",1)
                        .or()
                        .in("statu",2)
        );

        return Result.succ(pageData);
    }

    @GetMapping("/cartotal")
    public Result cartotal() {

        Page<Car> pageData = carService.page(getPage(),
                new QueryWrapper<Car>()
                        .in("statu",1)
        );

        return Result.succ(pageData);
    }

    @GetMapping("/ordertotal")
    public Result ordertotal(){

        Page<Indent> pageData = indentService.page(getPage(),
                new QueryWrapper<Indent>()
        );

        return Result.succ(pageData);
    }

    @GetMapping("/parklist")
    public Result parklist(){

        Page<Park> pageData = parkService.page(getPage(),
                new QueryWrapper<Park>()
        );

        return Result.succ(pageData);
    }
}
