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
public class RoleUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * role表单主键
     */
    private Long roleId;

    /**
     * user表单主键
     */
    private Long userId;


}
