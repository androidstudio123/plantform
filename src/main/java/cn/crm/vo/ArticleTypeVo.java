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
 * @date 2019/3/28 19:45
 */
@Data
public class ArticleTypeVo {

    /**
     * 文章类型id
     */
    @ApiModelProperty(value = "文章类型id")
    private Integer id;
    /**
     * 文章类型名称
     */
    @ApiModelProperty(value = "文章类型名称")
    private String articleType_title;

    /**
     * 文章类型创建时间
     */
    @ApiModelProperty(value = "文章类型创建时间")
    private Date create_time;
    /**
     * 文章类型创建人id
     */
    @ApiModelProperty(value = "文章类型创建人id")
    private Integer create_userId;
    /**
     * 文章类型创建人名字
     */
    @ApiModelProperty(value = "文章类型创建人名字")
    private String create_userName;

    /**
     * 文章修改时间
     */
    @ApiModelProperty(value = "文章修改时间")
    private Date update_time;
    /**
     * 文章修改人id
     */
    @ApiModelProperty(value = "文章修改人id")
    private Integer update_userId;
    /**
     * 修改人名字
     */
    @ApiModelProperty(value = "修改人名字")
    private String update_userName;

    /**
     * 文章类型状态:0 禁用 1启用
     */
    @ApiModelProperty(value = "文章类型状态:0 禁用 1启用")
    private Integer articleType_state;
    /**
     * 学校ids
     */
    @ApiModelProperty(value = "学校ids")
    private  String schoolIds;
}
