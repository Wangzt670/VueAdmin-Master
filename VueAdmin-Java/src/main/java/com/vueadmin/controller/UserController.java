package com.vueadmin.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vueadmin.common.dto.PassDto;
import com.vueadmin.common.lang.Const;
import com.vueadmin.common.lang.Result;
import com.vueadmin.entity.Role;
import com.vueadmin.entity.RoleMenu;
import com.vueadmin.entity.RoleUser;
import com.vueadmin.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
@RequestMapping("/sys/user")
public class UserController extends BaseController {

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('sys:user:list')")
    public Result info(@PathVariable("id") Long id) {

        User user = userService.getById(id);

        return Result.succ(user);
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sys:user:list')")
    public Result list(String username) {

        Page<User> pageData = userService.page(getPage(),
                new QueryWrapper<User>()
                        .like(StrUtil.isNotBlank(username),"username",username)
        );

        return Result.succ(pageData);
    }

    @GetMapping("/getrolelist")
    public Result getrolelist() {

        Page<Role> pageData = roleService.page(getPage(),
                new QueryWrapper<Role>()
                        .in("statu",1)
        );

        return Result.succ(pageData);
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sys:user:save')")
    @Transactional
    public Result save(@Validated @RequestBody User user) {

        // 设置默认密码
        String password = passwordEncoder.encode(Const.DEFULT_PASSWORD);
        user.setPassword(password);

        userService.save(user);

        Role userrole = roleService.getOne(new QueryWrapper<Role>().in("name", user.getRole()));
        RoleUser roleUser = new RoleUser();
        roleUser.setRoleId(userrole.getId());
        roleUser.setUserId(user.getId());
        roleUserService.save(roleUser);

        return Result.succ(user);
    }

    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sys:user:update')")
    @Transactional
    public Result updata(@Validated @RequestBody User user) {

        roleUserService.remove(new QueryWrapper<RoleUser>().in("user_id", user.getId()));

        Role userrole = roleService.getOne(new QueryWrapper<Role>().in("name", user.getRole()));
        RoleUser roleUser = new RoleUser();
        roleUser.setRoleId(userrole.getId());
        roleUser.setUserId(user.getId());
        roleUserService.save(roleUser);

        userService.updateById(user);

        return Result.succ(user);
    }

    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('sys:user:delete')")
    @Transactional
    public Result delete(@RequestBody Long[] ids) {

        userService.removeByIds(Arrays.asList(ids));

        roleUserService.remove(new QueryWrapper<RoleUser>().in("user_id", ids));

        return Result.succ("");
    }

    @PostMapping("/updatePass")
    public Result updatePass(@Validated @RequestBody PassDto passDto, Principal principal) {

        User user = userService.getByUsername(principal.getName());

        boolean matches = passwordEncoder.matches(passDto.getCurrentPass(), user.getPassword());
        if (!matches) {
            return Result.fail("旧密码不正确");
        }

        user.setPassword(passwordEncoder.encode(passDto.getPassword()));

        userService.updateById(user);
        return Result.succ("");
    }
}
