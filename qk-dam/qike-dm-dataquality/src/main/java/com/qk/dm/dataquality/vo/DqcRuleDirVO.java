package com.qk.dm.dataquality.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 数据质量__规则目录VO
 *
 * @author wjq
 * @date 20211108
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DqcRuleDirVO {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 规则目录ID
     */
    private String ruleDirId;

    /**
     * 规则目录名称
     */
    private String dirDsdName;

    /**
     * 目录父级ID
     */
    private String parentId;

    /**
     * 描述
     */
    private String description;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;

    /**
     * 是否删除；0逻辑删除，1物理删除；
     */
    private Integer delFlag;

}
