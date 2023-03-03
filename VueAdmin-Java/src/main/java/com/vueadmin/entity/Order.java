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
public class Order extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 订单编号
     */
    private String ordernum;

    /**
     * 外键车牌号
     */
    private String carnum;

    /**
     * 外键小区名称
     */
    private String villagename;

    /**
     * 外键车位编号
     */
    private String parknum;

    /**
     * 外键出租人
     */
    private String lease;

    /**
     * 外键租借人
     */
    private String rent;

    /**
     * 状态(0：已结束；1：进行中)
     */
    private Integer statu;


}
