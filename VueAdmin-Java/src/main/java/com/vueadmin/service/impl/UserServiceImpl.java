package com.vueadmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.vueadmin.entity.Menu;
import com.vueadmin.entity.Role;
import com.vueadmin.entity.User;
import com.vueadmin.mapper.UserMapper;
import com.vueadmin.service.MenuService;
import com.vueadmin.service.RoleService;
import com.vueadmin.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vueadmin.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 计科5班王正霆20194249
 * @since 2023-03-03
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    RoleService roleService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    MenuService menuService;

    @Autowired
    RedisUtil redisUtil;


    @Override
    public User getByUsername(String username) {

        return getOne(new QueryWrapper<User>().eq("username",username));
    }

    @Override
    public String getUserAuthorityInfo(Long userId) {

        User user = userMapper.selectById(userId);

        String authority="";


        //判断是否缓存了权限信息，缓存过则直接取出，否则获取权限并存入redis
        if (redisUtil.hasKey("GrantedAuthority:" + user.getUsername())) {
//            System.out.println("已缓存");
            authority = (String) redisUtil.get("GrantedAuthority:" + user.getUsername());

        }else{
//            System.out.println("未缓存");
            //获取角色
            List<Role> roles = roleService.list(new QueryWrapper<Role>()
                    .inSql("id", "select role_id from role_user where user_id = " + userId));

            if (roles.size() > 0) {
                String roleCodes = roles.stream().map(r -> "ROLE_" + r.getCode()).collect(Collectors.joining(","));

                authority = roleCodes.concat(",");
            }

            //获取草单操作权限编码
            List<Long> menuIds = userMapper.getNavMenuIds(userId);
            if (menuIds.size() > 0) {

                List<Menu> menus = menuService.listByIds(menuIds);
                String menuPerms = menus.stream().map(m -> m.getPerms()).collect(Collectors.joining(","));

                authority = authority.concat(menuPerms);
            }

            //存入redis
            redisUtil.set("GrantedAuthority:" + user.getUsername(), authority, 60 * 60);
        }


//        System.out.println("未缓存");
//        //获取角色
//        List<Role> roles = roleService.list(new QueryWrapper<Role>()
//                .inSql("id", "select role_id from role_user where user_id = " + userId));
//
//        if (roles.size() > 0) {
//            String roleCodes = roles.stream().map(r -> "ROLE_" + r.getCode()).collect(Collectors.joining(","));
//
//            authority = roleCodes.concat(",");
//        }
//
//        //获取草单操作权限编码
//        List<Long> menuIds = userMapper.getNavMenuIds(userId);
//        if (menuIds.size() > 0) {
//
//            List<Menu> menus = menuService.listByIds(menuIds);
//            String menuPerms = menus.stream().map(m -> m.getPerms()).collect(Collectors.joining(","));
//
//            authority = authority.concat(menuPerms);
//        }
//
//        //存入redis
//        redisUtil.set("GrantedAuthority:" + user.getUsername(), authority, 60 * 60);


        return authority;
    }

    @Override
    public void clearUserAuthorityInfo(String username) {
        redisUtil.del("GrantedAuthority:" + username);
    }

    @Override
    public void clearUserAuthorityInfoByRoleId(Long roleId) {

        List<User> Users = this.list(new QueryWrapper<User>()
                .inSql("id", "select user_id from role_user where role_id = " + roleId));

        Users.forEach(u -> {
            this.clearUserAuthorityInfo(u.getUsername());
        });
    }

    @Override
    public void clearUserAuthorityInfoByMenuId(Long menuId) {
        List<User> Users = userMapper.listByMenuId(menuId);

        Users.forEach(u -> {
            this.clearUserAuthorityInfo(u.getUsername());
        });
    }
}
