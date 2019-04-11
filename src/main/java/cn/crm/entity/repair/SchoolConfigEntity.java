package cn.crm.entity.repair;

import javax.persistence.*;

import lombok.Data;


@Data
@Table(name = "school_config")
public class SchoolConfigEntity {


    /**
     * 主键ID
     */
    @Id
    @Column(name = "school_id")
    @GeneratedValue(generator = "JDBC")
    private Integer school_id;
    /**
     * 学校名称
     */
    @Column(name = "school_name")
    private String school_name;
    /**
     * 所属城市
     */
    @Column(name = "school_city")
    private String school_city;
    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;
    /**
     * 学校状态  0禁用 1启用
     */
    @Column(name = "school_state")
    private Integer school_state;
    /**
     * 最小纬度值
     */
    @Column(name = "minLat")
    private double minLat;
    /**
     * 最大纬度值
     */
    @Column(name = "maxLat")
    private double maxLat;
    /**
     * 最小经度值
     */
    @Column(name = "minLon")
    private double minLon;
    /**
     * 最大经度值
     */
    @Column(name = "maxLon")
    private double maxLon;

//    /**
//     * 纬度
//     */
//    @Transient
//    private Double lat;
//    /**
//     * 经度
//     */
//    @Transient
//    private Double lon;
//    /**
//     * 半径
//     */
//    @Transient
//    private Integer raidus;


}
