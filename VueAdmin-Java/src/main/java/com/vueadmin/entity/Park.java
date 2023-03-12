package com.vueadmin.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
    @NotBlank(message = "车位编号不能为空")
    private String parknum;

    /**
     * 外键小区名称
     */
    @NotBlank(message = "小区名称不能为空")
    private String villagename;

    /**
     * 外键用户名称
     */
    @NotBlank(message = "用户名称不能为空")
    private String username;

    /**
     * 状态(0：禁用；1：空闲；2：占用)
     */
    @NotNull(message = "状态不能为空")
    private Integer statu;

    /**
     * 价格
     */
    @NotNull(message = "价格不能为空")
    private Integer price;

    /**
     * 描述
     */
    private String remark;


}
