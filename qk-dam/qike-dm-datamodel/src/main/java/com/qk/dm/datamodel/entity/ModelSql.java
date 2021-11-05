package com.qk.dm.datamodel.entity;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "qk_model_sql")
@Where(clause = "del_flag = 0 ")
public class ModelSql implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 对应的表id
     */
    @Column(name = "table_id", nullable = false)
    private Long tableId;

    /**
     * 1,逻辑表2物理表 3 维度表 4 汇总表
     */
    @Column(name = "type", nullable = false)
    private Integer type;

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
    private Integer delFlag;

    /**
     * sql语句
     */
    @Column(name = "sql_sentence", nullable = false)
    private String sqlSentence;

}
