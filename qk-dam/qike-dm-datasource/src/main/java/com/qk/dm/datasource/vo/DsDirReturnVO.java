package com.qk.dm.datasource.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 目录返回值VO
 * @author zys
 * @date 2021/8/27 11:24
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DsDirReturnVO {
    /** 主键ID */
    private Integer key;

    /**
     * 目录名称
     */
    private String title;

    /**
     * 父级id
     */
    private Integer parentId;

    /** 目录编码 */
    private String dsDirCode;

    /**
     *子类目录
     */
    private List<DsDirReturnVO> children;

}