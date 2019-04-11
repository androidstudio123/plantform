package cn.crm.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;

@Data
@ApiModel(description = "报修管理组合实体类")
public class RepairRepertoryAdminConfigVO {

    @Column(name = "repertory_config_id")
    private Integer repertory_config_id;

    @Column(name = "repertory_name_id")
    private Integer repertory_name_id;

    @Column(name = "repertory_type_id")
    private Integer repertory_type_id;

    @Column(name = "group_no")
    private String group_no;

    @Column(name = "repertory_name")
    private String repertory_name;

    @Column(name = "repertory_type_name")
    private String  repertory_type_name;

    @Column(name = "admin_namse")
    private String admin_names;
}
