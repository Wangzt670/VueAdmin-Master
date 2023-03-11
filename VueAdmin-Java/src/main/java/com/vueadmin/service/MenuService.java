package com.vueadmin.service;

import com.vueadmin.common.dto.MenuDto;
import com.vueadmin.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 计科5班王正霆20194249
 * @since 2023-03-03
 */
public interface MenuService extends IService<Menu> {

    List<MenuDto> getCurrentUserNav();

    List<Menu> tree();
}
