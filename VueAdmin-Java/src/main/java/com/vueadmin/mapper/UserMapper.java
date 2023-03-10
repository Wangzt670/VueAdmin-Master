package com.vueadmin.mapper;

import com.vueadmin.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 计科5班王正霆20194249
 * @since 2023-03-03
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    List<Long> getNavMenuIds(Long userId);

    List<User> listByMenuId(Long menuId);
}
