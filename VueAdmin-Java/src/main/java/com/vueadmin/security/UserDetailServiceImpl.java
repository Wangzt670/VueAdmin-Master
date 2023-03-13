package com.vueadmin.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vueadmin.entity.Role;
import com.vueadmin.entity.RoleUser;
import com.vueadmin.entity.User;
import com.vueadmin.service.RoleService;
import com.vueadmin.service.RoleUserService;
import com.vueadmin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    RoleUserService roleUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userService.getByUsername(username);

        RoleUser roleUser = roleUserService.getOne(new QueryWrapper<RoleUser>().in("user_id", user.getId()));

        Role role = roleService.getOne(new QueryWrapper<Role>().in("id", roleUser.getRoleId()));

        if (user == null) {
            throw new UsernameNotFoundException("用户名或密码不正确");
        }

        boolean enabled = true;
        if(user.getStatu() == 0){
            enabled = false;
        }

        boolean accountNonLocked = true;
        if(role.getStatu() == 0){
            accountNonLocked = false;
        }

        AccountUser accountUser = new AccountUser(
                user.getId(),user.getUsername(),user.getPassword(),
                enabled,true,true,accountNonLocked,
                getUserAuthority(user.getId()));

        return accountUser;
    }


    /**
     * 获取用户权限信息（角色、菜单权限）
     * @param userId
     * @return
     */
    public List<GrantedAuthority> getUserAuthority(Long userId){
        // 角色(ROLE_admin)、菜单操作权限 sys:user:list
        String authority = userService.getUserAuthorityInfo(userId);

        return AuthorityUtils.commaSeparatedStringToAuthorityList(authority);
    }
}
