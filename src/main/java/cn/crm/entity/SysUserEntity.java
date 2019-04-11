package cn.crm.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.crm.controller.wx.WXUtils.WXResult_A;
import cn.crm.entity.repair.RepairRoleEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 用户表
 * 2019
 */

@Data
@Table(name = "sys_user")
@ApiModel(description = "用户实体类")
public class SysUserEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "JDBC")
    /**
     * 用户id
     */
    @Column(name = "user_id")
    @ApiModelProperty(value = "用户id")

    private Integer user_id;
    /**
     * 用户学号/工号
     */
    @Column(name = "user_number")
    @ApiModelProperty(value = "用户学号/工号")
    private Integer user_number;

    /**
     * 用户昵称
     */
    @Column(name = "user_nick")
    @ApiModelProperty(value = "微信名称")
    @Excel(name = "微信名称", width = 20, height = 10)
    private String user_nick;
    /**
     * 用户名称
     */
    @Column(name = "user_name")
    @ApiModelProperty(value = "用户名称")
    @Excel(name = "用户名称", width = 20, height = 10)
    private String user_name;
    /**
     * 手机号码
     */
    @Column(name = "user_phone")
    @ApiModelProperty(value = "手机号码")
    @Excel(name = "手机号码", width = 20, height = 10)
    private String user_phone;
    /**
     * 注册时间
     */
    @Column(name = "user_createTime")
    @ApiModelProperty(value = "注册时间")
    @Excel(name = "注册时间", width = 50, height = 10, format = "yyyy-MM-dd")
    private java.util.Date user_createTime;
    /**
     * 地址
     */
    @Column(name = "user_address")
    @ApiModelProperty(value = "地址")
    @Excel(name = "地址", width = 40, height = 10)
    private String user_address;

    /**
     * 用户组id
     */
    @Column(name = "user_group_id")
    @ApiModelProperty(value = "用户组id")
    private Integer userGroup_id;
    /**
     * 用户头像
     */
    @Column(name = "user_avatar")
    @ApiModelProperty(value = "用户头像")
    private String user_avatar;
    /**
     * 微信openid
     */
    @Column(name = "user_openId")
    @ApiModelProperty(value = "微信openid")
    private String user_openId;

    /**
     * 用户所拥有的出库角色ID
     */
    @Column(name="export_role_id")
    @ApiModelProperty(value = "用户所拥有的出库角色ID")
    private Integer export_role_id;




    /**
     * 用户所拥有的报修角色
     */
    @Column(name = "role_id")
    @ApiModelProperty(value = "用户所拥有的报修角色")
    private Integer role_id;
    /**
     * 学校id
     */
    @Column(name = "school_id")
    @ApiModelProperty(value = "学校id")
    private Integer school_id;

    /**
     * access_token字段
     */
    @Transient
    private String wxResult_a;

    @Transient
    private String ticket;

    @ApiModelProperty(value = "用户所拥有的角色")
    private List<RepairRoleEntity> list;

    public SysUserEntity() {

    }

    public SysUserEntity(String user_openId) {
        this.user_openId = user_openId;
    }

    public SysUserEntity(String user_name, String user_nick, String user_phone,
                         String user_address, Integer userGroup_id, Date user_createTime,
                         String user_avatar, String user_openId, Integer role_id) {
        this.user_name = user_name;
        this.user_nick = user_nick;
        this.user_phone = user_phone;
        this.user_address = user_address;
        this.userGroup_id = userGroup_id;
        this.user_createTime = user_createTime;
        this.user_avatar = user_avatar;
        this.user_openId = user_openId;
        this.role_id = role_id;
    }
}
