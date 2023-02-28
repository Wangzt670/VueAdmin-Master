package com.vueadmin.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author 王正霆201942429
 * @since 2023-02-28
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
     * 状态(0：占用；1：空闲)
     */
    private Integer statu;

    /**
     * 价格
     */
    private String price;

    /**
     * 描述
     */
    private String remark;


}
