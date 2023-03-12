package com.vueadmin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vueadmin.common.lang.Result;
import com.vueadmin.entity.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/locview/locview")
public class LoctionViewController extends BaseController{

    @GetMapping("/getvillagelist")
    public Result getvillagelist() {

        Page<Village> pageData = villageService.page(getPage(),
                new QueryWrapper<Village>()
                        .in("statu",1)
        );

        return Result.succ(pageData);
    }


    @GetMapping("/getparklist")
    public Result getparklist(String currentVillageName) {

        Page<Park> pageData = parkService.page(getPage(),
                new QueryWrapper<Park>()
                        .in("statu",1)
                        .in("villagename",currentVillageName)
        );

        return Result.succ(pageData);
    }

    @GetMapping("/getuserinfo")
    public Result getuserinfo(Principal principal) {

        Page<User> pageData = userService.page(getPage(),
                new QueryWrapper<User>()
                        .in("statu",1)
                        .in("username",principal.getName())
        );

        return Result.succ(pageData);
    }

    @GetMapping("/getcarlist")
    public Result getcarlist(Principal principal) {

        Page<Car> pageData = carService.page(getPage(),
                new QueryWrapper<Car>()
                        .in("statu",1)
                        .in("username",principal.getName())
        );

        return Result.succ(pageData);
    }

    @GetMapping("/parkinfo/{id}")
//    @PreAuthorize("hasAuthority('locview:locview:save')")
    public Result info(@PathVariable("id") Long id) {

        Park park = parkService.getById(id);

        return Result.succ(park);
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('locview:locview:save')")
    public Result save(@Validated @RequestBody Indent indent) {

        indent.setStatu(1);

        Park park = parkService.getOne(new QueryWrapper<Park>().eq("parknum",indent.getParknum()));

        park.setStatu(2);

        Car car = carService.getOne(new QueryWrapper<Car>().eq("carnum",indent.getCarnum()));

        car.setStatu(0);

        carService.updateById(car);

        parkService.updateById(park);

        indentService.save(indent);

        return Result.succ(indent);
    }

}
