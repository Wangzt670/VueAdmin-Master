package com.vueadmin.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vueadmin.common.lang.Result;
import com.vueadmin.entity.Park;
import com.vueadmin.entity.User;
import com.vueadmin.entity.Village;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/vilman")
public class VillageController extends BaseController {
    @GetMapping("/village/info/{id}")
    @PreAuthorize("hasAuthority('vilman:village:list')")
    public Result info(@PathVariable("id") Long id) {

        Village village = villageService.getById(id);

        return Result.succ(village);
    }

    @GetMapping("/village/list")
    @PreAuthorize("hasAuthority('vilman:village:list')")
    public Result list(String villagename) {

        Page<Village> pageData = villageService.page(getPage(),
                new QueryWrapper<Village>()
                        .like(StrUtil.isNotBlank(villagename),"villagename",villagename)
        );

        return Result.succ(pageData);
    }

    @PostMapping("/village/save")
    @PreAuthorize("hasAuthority('vilman:village:save')")
    public Result save(@Validated @RequestBody Village village) {

        villageService.save(village);

        return Result.succ(village);
    }

    @PostMapping("/village/update")
    @PreAuthorize("hasAuthority('vilman:village:update')")
    public Result updata(@Validated @RequestBody Village village) {

        villageService.updateById(village);

        return Result.succ(village);
    }

    @PostMapping("/village/delete")
    @PreAuthorize("hasAuthority('vilman:village:delete')")
    public Result delete(@RequestBody Long[] ids) {

        villageService.removeByIds(Arrays.asList(ids));

        return Result.succ("");
    }


    @GetMapping("/myvillage/info/{id}")
    @PreAuthorize("hasAuthority('vilman:myvillage:list')")
    public Result myinfo(@PathVariable("id") Long id) {

        Village village = villageService.getById(id);

        return Result.succ(village);
    }

    @GetMapping("/myvillage/list")
    @PreAuthorize("hasAuthority('vilman:myvillage:list')")
    public Result mylist(String villagename) {

        Page<Village> pageData = villageService.page(getPage(),
                new QueryWrapper<Village>()
                        .like(StrUtil.isNotBlank(villagename),"villagename",villagename)
        );

        return Result.succ(pageData);
    }

    @PostMapping("/myvillage/save")
    @PreAuthorize("hasAuthority('vilman:myvillage:save')")
    public Result mysave(@Validated @RequestBody Village village) {

        villageService.save(village);

        return Result.succ(village);
    }

    @PostMapping("/myvillage/update")
    @PreAuthorize("hasAuthority('vilman:myvillage:update')")
    public Result myupdata(@Validated @RequestBody Village village) {

        villageService.updateById(village);

        return Result.succ(village);
    }


    @PostMapping("/myvillage/delete")
    @PreAuthorize("hasAuthority('vilman:myvillage:delete')")
    public Result mydelete(@RequestBody Long[] ids) {

        villageService.removeByIds(Arrays.asList(ids));

        return Result.succ("");
    }

    @GetMapping("/village/getparklist")
    public Result getparklist(String currentVillageName) {

        Page<Park> pageData = parkService.page(getPage(),
                new QueryWrapper<Park>()
                        .in("villagename",currentVillageName)
        );

        return Result.succ(pageData);
    }

    @GetMapping("/myvillage/getparklist")
    public Result getmyparklist(String currentVillageName) {

        Page<Park> pageData = parkService.page(getPage(),
                new QueryWrapper<Park>()
                        .in("villagename",currentVillageName)
        );

        return Result.succ(pageData);
    }




}
