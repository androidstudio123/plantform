package cn.crm.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;

/**
 * @ClassName {NAME}
 * @Description TODO
 * @Author MYZ
 * @Date 2019/3/15 0015 10:19
 */
@Data
@ApiModel(description = "用户管理组合实体类")
public class UserRoleGroupSchoolVo {

    /**
     * 管理员id
     */
    @Column(name = "user_id")
    @ApiModelProperty(value = "用户id")
    private Integer user_id;

    /**
     * 用户组id
     */
    @Column(name = "user_name")
    @ApiModelProperty(value = "用户名称")
    private String user_name;
    /**
     * 用户组id
     */
    @Column(name = "user_nick")
    @ApiModelProperty(value = "用户昵称")
    private String user_nick;

    @Column(name = "user_phone")
    @ApiModelProperty(value = "用户手机号")
    private String user_phone;
    /**
     * 描述
     */
    @Column(name = "user_address")
    @ApiModelProperty(value = "用户地址")
    private String user_address;
    /**
     * 用户组状态 0 不启用 1 启用
     */
    @Column(name = "user_group_id")
    @ApiModelProperty(value = "用户组id")
    private Integer user_group_id;
    /**
     * userGroup_createTime
     */
    @Column(name = "user_createTime")
    @ApiModelProperty(value = "创建时间")
    private java.util.Date user_createTime;
    /**
     * 创建人id
     */
    @Column(name = "user_avater")
    @ApiModelProperty(value = "用户头像")
    private String user_avater;
    /**
     * userGroup_updateTime
     */
    @Column(name = "user_openId")
    @ApiModelProperty(value = "修改时间")
    private String 用户openid;
    /**
     * 修改人id
     */
    @Column(name = "flag")
    @ApiModelProperty(value = "启用禁用")
    private Integer flag;
    /**
     * 父级id
     */
    @Column(name = "role_id")
    @ApiModelProperty(value = "角色id")
    private Integer role_id;

    /**
     * 父级id
     */
    @Column(name = "role_name")
    @ApiModelProperty(value = "角色名称")
    private String role_name;

    /**
     * 父级id
     */
    @Column(name = "school_id")
    @ApiModelProperty(value = "学校id")
    private Integer school_id;

    /**
     * 父级id
     */
    @Column(name = "userGroup_name")
    @ApiModelProperty(value = "分组名称")
    private String userGroup_name;

    /**
     * 父级id
     */
    @Column(name = "userGroup_state")
    @ApiModelProperty(value = "分组启用禁用")
    private Integer userGroup_state;
}
