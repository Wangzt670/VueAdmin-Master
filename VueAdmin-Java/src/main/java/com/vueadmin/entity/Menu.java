package com.vueadmin.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;

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
    private Long parentId;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 权限编码(多个用逗号分隔，如：user:list,user:create)
     */
    private String perms;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 菜单类型(0：目录   1：菜单   2：按钮)
     */
    private Integer type;

    /**
     * 菜单URL
     */
    private String path;

    /**
     * 菜单组件
     */
    private String component;


}
