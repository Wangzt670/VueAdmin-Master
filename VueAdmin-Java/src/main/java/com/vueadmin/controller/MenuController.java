package com.vueadmin.controller;


import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vueadmin.common.dto.MenuDto;
import com.vueadmin.entity.Menu;
import com.vueadmin.entity.RoleMenu;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import com.vueadmin.common.lang.Result;
import com.vueadmin.entity.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 计科5班王正霆20194249
 * @since 2023-03-03
 */
@RestController
@RequestMapping("/sys/menu")
public class MenuController extends BaseController {

    @GetMapping("/nav")
    public Result nav(Principal principal){
        User user = userService.getByUsername(principal.getName());


        //获取权限信息
        String authorityInfo = userService.getUserAuthorityInfo(user.getId());
        String[] authorityInfoArray = StringUtils.tokenizeToStringArray(authorityInfo, ",");

        //获取导航栏信息
        List<MenuDto> navs = menuService.getCurrentUserNav();


        return Result.succ(MapUtil.builder()
                .put("authoritys", authorityInfoArray)
                .put("nav", navs)
                .map());
    }

    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('sys:menu:list')")
    public Result info(@PathVariable(name = "id") Long id) {
        return Result.succ(menuService.getById(id));
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sys:menu:list')")
    public Result list() {
        List<Menu> menus = menuService.tree();
        return Result.succ(menus);
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sys:menu:save')")
    public Result save(@Validated @RequestBody Menu menu) {

        menuService.save(menu);

        return Result.succ(menu);
    }

    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sys:menu:update')")
    public Result update(@Validated @RequestBody Menu menu) {

        menuService.updateById(menu);

        // 清除所有与该菜单相关的权限缓存
        userService.clearUserAuthorityInfoByMenuId(menu.getId());

        return Result.succ(menu);
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('sys:menu:delete')")
    public Result delete(@PathVariable("id") Long id) {

        int count = menuService.count(new QueryWrapper<Menu>().eq("parent_id", id));
        if (count > 0) {
            return Result.fail("请先删除子菜单");
        }

        // 清除所有与该菜单相关的权限缓存
        userService.clearUserAuthorityInfoByMenuId(id);

        menuService.removeById(id);

        // 同步删除中间关联表
        roleMenuService.remove(new QueryWrapper<RoleMenu>().eq("menu_id", id));
        return Result.succ("");
    }
}
