package com.qk.dm.datastandards.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DsdCodeDirVO {


    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 码表标准分类ID
     */
    private Integer codeDirId;

    /**
     * 码表标准分类名称
     */
    private String codeDirName;

    /**
     * 父级ID
     */
    private Integer parentId;

    /**
     * 描述
     */
    private String description;


}
