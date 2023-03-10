package com.vueadmin.controller;


import cn.hutool.core.map.MapUtil;
import com.vueadmin.common.dto.MenuDto;
import org.springframework.util.StringUtils;
import com.vueadmin.common.lang.Result;
import com.vueadmin.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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

}
