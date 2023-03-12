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
public class Car extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 车牌号
     */
    @NotBlank(message = "车牌号不能为空")
    private String carnum;

    /**
     * 外键用户
     */
    @NotBlank(message = "用户名称不能为空")
    private String username;

    /**
     * 状态(0：禁用，1：正常)
     */
    @NotNull(message = "状态不能为空")
    private Integer statu;

    /**
     * 备注
     */
    private String remark;


}
