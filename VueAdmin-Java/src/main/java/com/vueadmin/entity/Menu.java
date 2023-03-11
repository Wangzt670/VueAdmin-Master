package com.vueadmin.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author 计科5班王正霆20194249
 * @since 2023-03-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Menu extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 父菜单ID(一级菜单为0)
     */
    @NotNull(message = "上级菜单不能为空")
    private Long parentId;

    /**
     * 菜单名称
     */
    @NotBlank(message = "菜单名称不能为空")
    private String name;

    /**
     * 权限编码(多个用逗号分隔，如：user:list,user:create)
     */
    @NotBlank(message = "菜单权限编码不能为空")
    private String perms;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 菜单类型(0：目录   1：菜单   2：按钮)
     */
    @NotNull(message = "菜单类型不能为空")
    private Integer type;

    /**
     * 菜单URL
     */
    private String path;

    /**
     * 菜单组件
     */
    private String component;

    @TableField(exist = false)
    private List<Menu> children = new ArrayList<>();


}
