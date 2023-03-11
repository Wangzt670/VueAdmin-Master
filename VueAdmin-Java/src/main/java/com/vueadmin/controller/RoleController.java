package com.vueadmin.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vueadmin.common.lang.Result;
import com.vueadmin.entity.Menu;
import com.vueadmin.entity.Role;
import com.vueadmin.entity.RoleMenu;
import com.vueadmin.entity.RoleUser;
import com.vueadmin.service.RoleMenuService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 计科5班王正霆20194249
 * @since 2023-03-03
 */
@RestController
@RequestMapping("/sys/role")
public class RoleController extends BaseController {

    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('sys:role:list')")
    public Result info(@PathVariable("id") Long id) {

        Role role = roleService.getById(id);

        List<RoleMenu> roleMenu = roleMenuService.list(new QueryWrapper<RoleMenu>().eq("role_id",id));

        List<Long> menuIds = roleMenu.stream().map(p -> p.getMenuId()).collect(Collectors.toList());

        role.setMenuIds(menuIds);

        return Result.succ(role);
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sys:role:list')")
    public Result list(String name) {

        Page<Role> pageData = roleService.page(getPage(),
                new QueryWrapper<Role>()
                        .like(StrUtil.isNotBlank(name),"name",name)
        );

        return Result.succ(pageData);
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sys:role:save')")
    public Result save(@Validated @RequestBody Role role) {

        roleService.save(role);

        return Result.succ(role);
    }

    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sys:role:update')")
    public Result updata(@Validated @RequestBody Role role) {

        roleService.updateById(role);

        //更新缓存
        userService.clearUserAuthorityInfoByRoleId(role.getId());

        return Result.succ(role);
    }

    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('sys:role:delete')")
    @Transactional
    public Result delete(@RequestBody Long[] ids) {

        roleService.removeByIds(Arrays.asList(ids));

        // 删除中间表
        roleUserService.remove(new QueryWrapper<RoleUser>().in("role_id", ids));
        roleMenuService.remove(new QueryWrapper<RoleMenu>().in("role_id", ids));

        // 缓存同步删除
        Arrays.stream(ids).forEach(id -> {
            // 更新缓存
            userService.clearUserAuthorityInfoByRoleId(id);
        });

        return Result.succ("");
    }

    @PostMapping("/perm/{roleId}")
    @Transactional
    public Result perm(@PathVariable("roleId") Long roleId,@RequestBody Long[] menuIds) {

        List<RoleMenu> roleMenus = new ArrayList<>();

        Arrays.stream(menuIds).forEach(menuId -> {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setMenuId(menuId);
            roleMenu.setRoleId(roleId);

            roleMenus.add(roleMenu);
        });

        // 先删除原来的记录，再保存新的
        roleMenuService.remove(new QueryWrapper<RoleMenu>().eq("role_id", roleId));
        roleMenuService.saveBatch(roleMenus);

        // 删除缓存
        userService.clearUserAuthorityInfoByRoleId(roleId);

        return Result.succ(menuIds);
    }

}
