package com.vueadmin.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vueadmin.common.lang.Result;
import com.vueadmin.entity.Car;
import com.vueadmin.entity.Park;
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
@RequestMapping("/parkman")
public class ParkController extends BaseController {
    @GetMapping("/park/info/{id}")
    @PreAuthorize("hasAuthority('parkman:park:list')")
    public Result info(@PathVariable("id") Long id) {

        Park park = parkService.getById(id);

        return Result.succ(park);
    }

    @GetMapping("/park/list")
    @PreAuthorize("hasAuthority('parkman:park:list')")
    public Result list(String parknum) {

        Page<Park> pageData = parkService.page(getPage(),
                new QueryWrapper<Park>()
                        .like(StrUtil.isNotBlank(parknum),"parknum",parknum)
        );

        return Result.succ(pageData);
    }


    @GetMapping("/park/getuserlist")
    public Result getuserlist() {

        Page<User> pageData = userService.page(getPage(),
                new QueryWrapper<User>()
                        .in("statu",1)
        );

        return Result.succ(pageData);
    }

    @GetMapping("/park/getvillagelist")
    public Result getvillagelist() {

        Page<Village> pageData = villageService.page(getPage(),
                new QueryWrapper<Village>()
                        .in("statu",1)
        );

        return Result.succ(pageData);
    }


    @PostMapping("/park/save")
    @PreAuthorize("hasAuthority('parkman:park:save')")
    public Result save(@Validated @RequestBody Park park) {

        parkService.save(park);

        return Result.succ(park);
    }

    @PostMapping("/park/update")
    @PreAuthorize("hasAuthority('parkman:park:update')")
    public Result updata(@Validated @RequestBody Park park) {

        parkService.updateById(park);

        return Result.succ(park);
    }

    @PostMapping("/park/delete")
    @PreAuthorize("hasAuthority('parkman:park:delete')")
    public Result delete(@RequestBody Long[] ids) {

        parkService.removeByIds(Arrays.asList(ids));

        return Result.succ("");
    }




    @GetMapping("/mypark/info/{id}")
    @PreAuthorize("hasAuthority('parkman:mypark:list')")
    public Result myinfo(@PathVariable("id") Long id) {

        Park park = parkService.getById(id);

        return Result.succ(park);
    }

    @GetMapping("/mypark/list")
    @PreAuthorize("hasAuthority('parkman:mypark:list')")
    public Result mylist(String parknum, Principal principal) {

        Page<Park> pageData = parkService.page(getPage(),
                new QueryWrapper<Park>()
                        .like(StrUtil.isNotBlank(parknum),"parknum",parknum)
                        .in("username",principal.getName())
        );

        return Result.succ(pageData);
    }


    @GetMapping("/mypark/getuserlist")
    public Result mygetuserlist(Principal principal) {

        Page<User> pageData = userService.page(getPage(),
                new QueryWrapper<User>()
                        .in("statu",1)
                        .in("username",principal.getName())
        );

        return Result.succ(pageData);
    }

    @GetMapping("/mypark/getvillagelist")
    public Result mygetvillagelist() {

        Page<Village> pageData = villageService.page(getPage(),
                new QueryWrapper<Village>()
                        .in("statu",1)
        );

        return Result.succ(pageData);
    }


    @PostMapping("/mypark/save")
    @PreAuthorize("hasAuthority('parkman:mypark:save')")
    public Result mysave(@Validated @RequestBody Park park) {

        parkService.save(park);

        return Result.succ(park);
    }

    @PostMapping("/mypark/update")
    @PreAuthorize("hasAuthority('parkman:mypark:update')")
    public Result myupdata(@Validated @RequestBody Park park) {

        parkService.updateById(park);

        return Result.succ(park);
    }

    @PostMapping("/mypark/delete")
    @PreAuthorize("hasAuthority('parkman:mypark:delete')")
    public Result mydelete(@RequestBody Long[] ids) {

        parkService.removeByIds(Arrays.asList(ids));

        return Result.succ("");
    }
}
