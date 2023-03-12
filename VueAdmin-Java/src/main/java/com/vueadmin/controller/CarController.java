package com.vueadmin.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vueadmin.common.lang.Result;
import com.vueadmin.entity.Car;
import com.vueadmin.entity.Role;
import com.vueadmin.entity.User;
import com.vueadmin.entity.Village;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Arrays;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 计科5班王正霆20194249
 * @since 2023-03-03
 */
@RestController
@RequestMapping("/carman")
public class CarController extends BaseController {
    @GetMapping("/car/info/{id}")
    @PreAuthorize("hasAuthority('carman:car:list')")
    public Result info(@PathVariable("id") Long id) {

        Car car = carService.getById(id);

        return Result.succ(car);
    }

    @GetMapping("/car/list")
    @PreAuthorize("hasAuthority('carman:car:list')")
    public Result list(String carnum) {

        Page<Car> pageData = carService.page(getPage(),
                new QueryWrapper<Car>()
                        .like(StrUtil.isNotBlank(carnum),"carnum",carnum)
        );

        return Result.succ(pageData);
    }


    @GetMapping("/car/getuserlist")
    public Result getuserlist() {

        Page<User> pageData = userService.page(getPage(),
                new QueryWrapper<User>()
                        .in("statu",1)
        );

        return Result.succ(pageData);
    }


    @PostMapping("/car/save")
    @PreAuthorize("hasAuthority('carman:car:save')")
    public Result save(@Validated @RequestBody Car car) {

        carService.save(car);

        return Result.succ(car);
    }

    @PostMapping("/car/update")
    @PreAuthorize("hasAuthority('carman:car:update')")
    public Result updata(@Validated @RequestBody Car car) {

        carService.updateById(car);

        return Result.succ(car);
    }

    @PostMapping("/car/delete")
    @PreAuthorize("hasAuthority('carman:car:delete')")
    public Result delete(@RequestBody Long[] ids) {

        carService.removeByIds(Arrays.asList(ids));

        return Result.succ("");
    }



    @GetMapping("/mycar/info/{id}")
    @PreAuthorize("hasAuthority('carman:mycar:list')")
    public Result myinfo(@PathVariable("id") Long id) {

        Car car = carService.getById(id);

        return Result.succ(car);
    }

    @GetMapping("/mycar/list")
    @PreAuthorize("hasAuthority('carman:mycar:list')")
    public Result mylist(String carnum, Principal principal) {

        Page<Car> pageData = carService.page(getPage(),
                new QueryWrapper<Car>()
                        .like(StrUtil.isNotBlank(carnum),"carnum",carnum)
                        .in("username",principal.getName())
        );

        return Result.succ(pageData);
    }


    @GetMapping("/mycar/getuserlist")
    public Result mygetuserlist(Principal principal) {

        Page<User> pageData = userService.page(getPage(),
                new QueryWrapper<User>()
                        .in("statu",1)
                        .in("username",principal.getName())
        );


        return Result.succ(pageData);
    }


    @PostMapping("/mycar/save")
    @PreAuthorize("hasAuthority('carman:mycar:save')")
    public Result mysave(@Validated @RequestBody Car car) {

        carService.save(car);

        return Result.succ(car);
    }

    @PostMapping("/mycar/update")
    @PreAuthorize("hasAuthority('carman:mycar:update')")
    public Result myupdata(@Validated @RequestBody Car car) {

        carService.updateById(car);

        return Result.succ(car);
    }

    @PostMapping("/mycar/delete")
    @PreAuthorize("hasAuthority('carman:mycar:delete')")
    public Result mydelete(@RequestBody Long[] ids) {

        carService.removeByIds(Arrays.asList(ids));

        return Result.succ("");
    }

}
