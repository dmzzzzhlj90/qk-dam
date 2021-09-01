package com.qk.dm.indicator.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "qk_idc_business")
public class IdcBusiness implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 业务指标名称
     */
    @Column(name = "b_indicator_name", nullable = false)
    private String bIndicatorName;

    /**
     * 指标编码
     */
    @Column(name = "b_indicator_code", nullable = false)
    private String bIndicatorCode;

    /**
     * 指标别名
     */
    @Column(name = "b_indicator_alias")
    private String bIndicatorAlias;

    /**
     * 设置目的
     */
    @Column(name = "set_purpose")
    private String setPurpose;

    /**
     * 指标定义
     */
    @Column(name = "indicator_definition")
    private String indicatorDefinition;

    /**
     * 备注
     */
    @Column(name = "remarks")
    private String remarks;

    /**
     * 计算公式
     */
    @Column(name = "calculation_formula")
    private String calculationFormula;

    /**
     * 统计周期
     */
    @Column(name = "statistical_cycle")
    private String statisticalCycle;

    /**
     * 统计维度
     */
    @Column(name = "dim_statistical")
    private String dimStatistical;

    /**
     * 指标状态 0草稿 1已上线 2已下线
     */
    @Column(name = "indicator_status")
    private Integer indicatorStatus;

    /**
     * 口径或修饰词
     */
    @Column(name = "caliber_modifier")
    private String caliberModifier;

    /**
     * 应用场景
     */
    @Column(name = "application_scenario")
    private String applicationScenario;

    /**
     * 技术指标
     */
    @Column(name = "tech_indicator")
    private String techIndicator;

    /**
     * 度量对象
     */
    @Column(name = "measure_object")
    private String measureObject;

    /**
     * 度量单位
     */
    @Column(name = "measure_unit")
    private String measureUnit;

    /**
     * 指标管理部门
     */
    @Column(name = "indicator_depart")
    private String indicatorDepart;

    /**
     * 指标负责人
     */
    @Column(name = "indicator_person_liable")
    private String indicatorPersonLiable;

    /**
     * 创建时间
     */
    @Column(name = "gmt_create")
    @CreationTimestamp
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @Column(name = "gmt_modified")
    @UpdateTimestamp
    private Date gmtModified;

    /**
     * 删除标识 0未删除 1已删除
     */
    @Column(name = "del_flag")
    private Integer delFlag = 0;

}
