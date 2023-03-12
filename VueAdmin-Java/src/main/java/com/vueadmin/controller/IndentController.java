package com.vueadmin.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vueadmin.common.lang.Result;
import com.vueadmin.entity.Car;
import com.vueadmin.entity.Indent;
import com.vueadmin.entity.Park;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 计科5班王正霆20194249
 * @since 2023-03-12
 */
@RestController
@RequestMapping("/ordman")
public class IndentController extends BaseController {

    @GetMapping("/order/info/{id}")
    @PreAuthorize("hasAuthority('ordman:order:list')")
    public Result info(@PathVariable("id") Long id) {

        Indent indent= indentService.getById(id);

        return Result.succ(indent);
    }

    @GetMapping("/order/list")
    @PreAuthorize("hasAuthority('ordman:order:list')")
    public Result list(String ordernum) {

        Page<Indent> pageData = indentService.page(getPage(),
                new QueryWrapper<Indent>()
                        .like(StrUtil.isNotBlank(ordernum),"ordernum",ordernum)
        );

        return Result.succ(pageData);
    }

    @PostMapping("/order/update")
    @PreAuthorize("hasAuthority('ordman:order:update')")
    public Result updata(@Validated @RequestBody Indent indent) {

        Park park = parkService.getOne(new QueryWrapper<Park>().eq("parknum",indent.getParknum()));

        if(indent.getStatu()==0){
            park.setStatu(1);
        }else if(indent.getStatu()==1){
            park.setStatu(2);
        }

        Car car = carService.getOne(new QueryWrapper<Car>().eq("carnum",indent.getCarnum()));

        if(indent.getStatu()==0){
            car.setStatu(1);
        }else if(indent.getStatu()==1){
            car.setStatu(2);
        }

        carService.updateById(car);

        parkService.updateById(park);

        indentService.updateById(indent);

        return Result.succ(indent);
    }




    @GetMapping("/myorder/info/{id}")
    @PreAuthorize("hasAuthority('ordman:myorder:list')")
    public Result myinfo(@PathVariable("id") Long id) {

        Indent indent= indentService.getById(id);

        return Result.succ(indent);
    }

    @GetMapping("/myorder/list")
    @PreAuthorize("hasAuthority('ordman:myorder:list')")
    public Result mylist(String ordernum, Principal principal) {

        QueryWrapper<Indent> queryWrapper = new QueryWrapper<Indent>()
                .like(StrUtil.isNotBlank(ordernum),"ordernum",ordernum);

        queryWrapper.and(qr ->
                qr.in("lease",principal.getName())
                .or()
                .in("rent",principal.getName())
        );

        Page<Indent> pageData = indentService.page(getPage(),queryWrapper);

        return Result.succ(pageData);
    }

    @PostMapping("/myorder/update")
    @PreAuthorize("hasAuthority('ordman:myorder:update')")
    public Result myupdata(@Validated @RequestBody Indent indent) {

        Park park = parkService.getOne(new QueryWrapper<Park>().eq("parknum",indent.getParknum()));

        if(indent.getStatu()==0){
            park.setStatu(1);
        }else if(indent.getStatu()==1){
            park.setStatu(2);
        }

        Car car = carService.getOne(new QueryWrapper<Car>().eq("carnum",indent.getCarnum()));

        if(indent.getStatu()==0){
            car.setStatu(1);
        }else if(indent.getStatu()==1){
            car.setStatu(2);
        }

        carService.updateById(car);

        parkService.updateById(park);

        indentService.updateById(indent);

        return Result.succ(indent);
    }

}
