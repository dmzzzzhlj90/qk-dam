package com.qk.dm.reptile.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 站点找源表
 */
@Data
@Entity
@Table(name = "qk_rpt_find_source")
public class RptFindSource implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 标题
     */
    @Column(name = "title")
    private String title;

    /**
     * 0未校验 1校验数据已存在 2校验不存在
     */
    @Column(name = "status")
    private Integer status;

    /**
     * 省编码
     */
    @Column(name = "province_code")
    private String provinceCode;

    /**
     * 市编码
     */
    @Column(name = "city_code")
    private String cityCode;

    /**
     * 信息类型
     */
    @Column(name = "info_type")
    private String infoType;

    /**
     * 发布时间
     */
    @Column(name = "publish_time")
    private Date publishTime;

    /**
     * 创建人
     */
    @Column(name = "create_username")
    private String createUsername;

    /**
     * 修改人
     */
    @Column(name = "update_username")
    private String updateUsername;

    /**
     * 创建时间
     */
    @Column(name = "gmt_create")
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @Column(name = "gmt_modified")
    private Date gmtModified;

    /**
     * 0未删除 1已删除
     */
    @Column(name = "del_flag", nullable = false)
    private Integer delFlag = 0;

}
