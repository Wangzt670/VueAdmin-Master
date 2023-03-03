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
public class Park extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 车位编号
     */
    private String parknum;

    /**
     * 外键小区名称
     */
    private String villagename;

    /**
     * 外键用户名称
     */
    private String username;

    /**
     * 状态(0：禁用；1：空闲；2：占用)
     */
    private Integer statu;

    /**
     * 价格
     */
    private Integer price;

    /**
     * 描述
     */
    private String remark;


}
