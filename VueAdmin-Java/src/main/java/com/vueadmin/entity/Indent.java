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
 * @since 2023-03-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Indent extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 订单开始时间
     */
    @NotBlank(message = "订单开始时间不能为空")
    private String orderstart;

    /**
     * 订单结束时间
     */
    private String orderend;

    /**
     * 外键车牌号
     */
    @NotBlank(message = "车牌号不能为空")
    private String carnum;

    /**
     * 外键小区名称
     */
    @NotBlank(message = "小区名称不能为空")
    private String villagename;

    /**
     * 外键车位编号
     */
    @NotBlank(message = "车位编号不能为空")
    private String parknum;

    /**
     * 外键出租人
     */
    @NotBlank(message = "出租人不能为空")
    private String lease;

    /**
     * 外键租借人
     */
    @NotBlank(message = "租借人不能为空")
    private String rent;

    /**
     * 状态(0：已结束；1：进行中)
     */
    @NotNull(message = "状态不能为空")
    private Integer statu;

    /**
     * 订单价格
     */
    private Integer cost;

}
