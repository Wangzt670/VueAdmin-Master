package com.vueadmin.service;

import com.vueadmin.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 计科5班王正霆20194249
 * @since 2023-03-03
 */
public interface UserService extends IService<User> {



    User getByUsername(String username);

    String getUserAuthorityInfo(Long userId);


    void clearUserAuthorityInfo(String username);

    void clearUserAuthorityInfoByRoleId(Long roleId);

    void clearUserAuthorityInfoByMenuId(Long menuId);



}
