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
