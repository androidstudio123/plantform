package cn.crm.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;

/**
 * @author MYZ
 * @version V1.0
 * @Description:
 * @Package cn.crm.vo
 * @date 2019/3/28 19:04
 */
@Data
public class ModuleVo {
    /**
     * 模块id
     */
    @ApiModelProperty(value = "模块id")
    private Integer id;
    /**
     * 模块名称
     */
    @ApiModelProperty(value = "模块名称")
    private String module_name;
    /**
     * 模块图标地址
     */
    @ApiModelProperty(value = "模块图标地址")
    private String module_image_url;
    /**
     * 超链接
     */
    @ApiModelProperty(value = "超链接")
    private String module_url;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date create_time;
    /**
     * 创建人id
     */
    @ApiModelProperty(value = "创建人id")
    private Integer create_userId;
    /**
     * 创建人名字
     */

    @ApiModelProperty(value = "创建人名字")
    private String create_userName;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private Date update_time;
    /**
     * 修改人id
     */
    @ApiModelProperty(value = "修改人id")
    private Integer update_user_id;
    /**
     * 修改人名字
     */
    @ApiModelProperty(value = "修改人名字")
    private String update_user_name;

    /**
     * 模块状态:0禁用 1启用
     */
    @ApiModelProperty(value = "模块状态")
    private Integer module_status;

    /**
     * 权重
     */
    @ApiModelProperty(value = "权重")
    private Integer module_rank;

    @ApiModelProperty(value = "学校ids")
    private String schoolIds;
}
