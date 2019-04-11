package cn.crm.entity.terrace;

import javax.persistence.*;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


import java.util.Date;

/**
 * TODO 在此加入类描述 模块实体类
 *
 * @author MYZ
 * @version 2019-03-25 10:00:12
 * @copyright
 */

@Data
@Table(name = "repair_module")
@ApiModel(description = "模块类")
public class RepairModuleEntity {


    /**
     * 模块id
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    @ApiModelProperty(value = "模块id")
    private Integer id;
    /**
     * 模块名称
     */
    @Column(name = "module_name")
    @ApiModelProperty(value = "模块名称")
    private String module_name;
    /**
     * 模块图标地址
     */
    @Column(name = "module_image_url")
    @ApiModelProperty(value = "模块图标地址")
    private String module_image_url;
    /**
     * 超链接
     */
    @Column(name = "module_url")
    @ApiModelProperty(value = "超链接")
    private String module_url;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @ApiModelProperty(value = "创建时间")
    private Date create_time;
    /**
     * 创建人id
     */
    @Column(name = "create_userId")
    @ApiModelProperty(value = "创建人id")
    private Integer create_userId;
    /**
     * 创建人名字
     */
    @Column(name = "create_userName")
    @ApiModelProperty(value = "创建人名字")
    private String create_userName;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    @ApiModelProperty(value = "修改时间")
    private Date update_time;
    /**
     * 修改人id
     */
    @Column(name = "update_user_id")
    @ApiModelProperty(value = "修改人id")
    private Integer update_user_id;
    /**
     * 修改人名字
     */
    @Column(name = "update_user_name")
    @ApiModelProperty(value = "修改人名字")
    private String update_user_name;

    /**
     * 模块状态:0禁用 1启用
     */
    @Column(name = "module_status")
    @ApiModelProperty(value = "模块状态")
    private Integer module_status;

    /**
     * 权重
     */
    @Column(name = "module_rank")
    @ApiModelProperty(value = "权重")
    private Integer module_rank;


    @Transient
    @ApiModelProperty(value = "学校ids")
    private String  schoolIds;

}
