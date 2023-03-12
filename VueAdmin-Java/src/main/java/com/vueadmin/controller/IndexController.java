package com.vueadmin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vueadmin.common.lang.Result;
import com.vueadmin.entity.Village;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/index")
public class IndexController extends BaseController{
    @GetMapping("/getvillagelist")
    public Result getvillagelist() {

        Page<Village> pageData = villageService.page(getPage(),
                new QueryWrapper<Village>()
                        .in("statu",1)
        );

        return Result.succ(pageData);
    }

}
