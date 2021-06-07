package com.qk.dm.datastandards.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DsdDirVO {


    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 数据标准分类ID
     */
    private Integer dirDsdId;

    /**
     * 数据标准分类名称
     */
    private String dirDsdName;

    /**
     * 父级id
     */
    private Integer parentId;

    /**
     * 描述
     */
    private String description;


}
