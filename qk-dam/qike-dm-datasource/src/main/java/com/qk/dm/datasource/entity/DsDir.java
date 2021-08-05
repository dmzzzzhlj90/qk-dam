package com.qk.dm.datasource.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "qk_ds_dir")
public class DsDir implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 目录名称
     */
    @Column(name = "dic_name", nullable = false)
    private String dicName;

    /**
     * 父级id(一级目录是0，二级目录是上级的id)
     */
    @Column(name = "parent_id", nullable = false)
    private Integer parentId;

    /**
     * 创建时间(后期设计为非空)
     */
    @Column(name = "gmt_create")
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @Column(name = "gmt_modified")
    private Date gmtModified;

    /**
     * 创建人(后期设计为非空)
     */
    @Column(name = "create_userid")
    private String createUserid;

    /**
     * 修改人
     */
    @Column(name = "update_userid")
    private String updateUserid;

    /**
     * 删除标识(0-保留 1-删除，后期设置为非空，默认是0)
     */
    @Column(name = "del_flag")
    private Integer delFlag;

    /**
     * 多租户标识
     */
    @Column(name = "version_consumer")
    private String versionConsumer;
    /**
     * 目录编码
     */
    @Column(name = "ds_dir_code", nullable = false)
    private String dsDirCode;

}
