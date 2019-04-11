package cn.crm.entity;

import javax.persistence.*;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 管理员对应角色
 * MYZ
 */

@Data
@Table(name = "sys_admin_role")
@ApiModel(description = "用户角色关联实体类")
public class SysAdminRoleEntity {

    /**
     * 后台人员id
     */
    @Column(name = "admin_id")
    @ApiModelProperty(value = "用户id")
    private Integer adminId;
    /**
     * 角色id
     */
    @Column(name = "role_id")
    @ApiModelProperty(value = "角色id")
    private Integer roleId;


}
