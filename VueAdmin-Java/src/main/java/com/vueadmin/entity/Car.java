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
public class Car extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 车牌号
     */
    private String carnum;

    /**
     * 外键用户
     */
    private String username;

    /**
     * 备注
     */
    private String remark;


}
