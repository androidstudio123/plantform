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
@ApiModel(description = "管理员用户组组合实体类")
public class SysUsergroupVo {

    /**
     * 管理员id
     */
    @Column(name = "admin_id")
    @ApiModelProperty(value = "管理员id")
    private Integer admin_id;

    /**
     * 用户组id
     */
    @Column(name = "userGroup_id")
    @ApiModelProperty(value = "用户组id1")
    private Integer group_id;
    /**
     * 用户组id
     */
    @Column(name = "userGroup_id")
    @ApiModelProperty(value = "用户组id")
    private Integer userGroup_id;

    @Column(name = "userGroup_name")
    @ApiModelProperty(value = "用户组名称")
    private String userGroup_name;
    /**
     * 描述
     */
    @Column(name = "userGroup_desc")
    @ApiModelProperty(value = "描述")
    private String userGroup_desc;
    /**
     * 用户组状态 0 不启用 1 启用
     */
    @Column(name = "userGroup_state")
    @ApiModelProperty(value = "用户组状态 0 不启用 1 启用")
    private Integer userGroup_state;
    /**
     * userGroup_createTime
     */
    @Column(name = "userGroup_createTime")
    @ApiModelProperty(value = "创建时间")
    private java.util.Date userGroup_createTime;
    /**
     * 创建人id
     */
    @Column(name = "userGroup_createId")
    @ApiModelProperty(value = "创建人id")
    private Integer userGroup_createId;
    /**
     * userGroup_updateTime
     */
    @Column(name = "userGroup_updateTime")
    @ApiModelProperty(value = "修改时间")
    private java.util.Date userGroup_updateTime;
    /**
     * 修改人id
     */
    @Column(name = "userGroup_updateId")
    @ApiModelProperty(value = "修改人id")
    private Integer userGroup_updateId;
    /**
     * 父级id
     */
    @Column(name = "userGroup_parentId")
    @ApiModelProperty(value = "父级id")
    private Integer userGroup_parentId;
    /**
     * 学校id
     */
    @Column(name = "school_id")
    @ApiModelProperty(value = "学校id")
    private Integer school_id;
}
