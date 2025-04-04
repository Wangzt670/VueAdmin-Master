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
public class Village extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 小区名称
     */
    @NotBlank(message = "小区名称不能为空")
    private String villagename;

    /**
     * 小区地址
     */
    private String keyword;

    /**
     * 坐标纬度
     */
    private Float lng;

    /**
     * 坐标经度
     */
    private Float lat;

    /**
     * 状态(0：禁用，1：正常)
     */
    @NotNull(message = "状态不能为空")
    private Integer statu;

    /**
     * 描述
     */
    private String remark;


}
