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
public class Village extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 小区名称
     */
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
     * 描述
     */
    private String remark;


}
