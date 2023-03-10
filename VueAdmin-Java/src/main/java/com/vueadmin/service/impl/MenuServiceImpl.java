package com.vueadmin.service.impl;

import cn.hutool.json.JSONUtil;
import com.vueadmin.common.dto.MenuDto;
import com.vueadmin.entity.Menu;
import com.vueadmin.entity.User;
import com.vueadmin.mapper.MenuMapper;
import com.vueadmin.mapper.UserMapper;
import com.vueadmin.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vueadmin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 计科5班王正霆20194249
 * @since 2023-03-03
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    UserService userService;

    @Autowired
    UserMapper userMapper;

    @Override
    public List<MenuDto> getCurrentUserNav() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getByUsername(username);

        List<Long> menuIds = userMapper.getNavMenuIds(user.getId());
        List<Menu> menus = this.listByIds(menuIds);

        // 转树状结构
        List<Menu> menuTree = buildTreeMenu(menus);

        // 实体转DTO
        return convert(menuTree);
    }


    private List<Menu> buildTreeMenu(List<Menu> menus) {

        List<Menu> finalMenus = new ArrayList<>();

        // 先各自寻找到各自的孩子
        for (Menu menu : menus) {

            for (Menu e : menus) {
                if (menu.getId() == e.getParentId()) {
                    menu.getChildren().add(e);
                }
            }

            // 提取出父节点
            if (menu.getParentId() == 0L) {
                finalMenus.add(menu);
            }
        }

//        System.out.println(JSONUtil.toJsonStr(finalMenus));
        return finalMenus;
    }


    private List<MenuDto> convert(List<Menu> menuTree) {
        List<MenuDto> menuDtos = new ArrayList<>();

        menuTree.forEach(m -> {
            MenuDto dto = new MenuDto();

            dto.setId(m.getId());
            dto.setName(m.getPerms());
            dto.setTitle(m.getName());
            dto.setComponent(m.getComponent());
            dto.setPath(m.getPath());

            if (m.getChildren().size() > 0) {

                // 子节点调用当前方法进行再次转换
                dto.setChildren(convert(m.getChildren()));
            }

            menuDtos.add(dto);
        });

        return menuDtos;
    }
}
