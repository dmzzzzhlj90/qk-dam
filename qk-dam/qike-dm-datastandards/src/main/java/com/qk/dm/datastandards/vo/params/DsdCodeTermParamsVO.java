package com.qk.dm.datastandards.vo.params;

import com.qk.dm.datastandards.vo.Pagination;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DsdCodeTermParamsVO {

    private Pagination pagination;

    /**
     * 码表分类编码
     */
    private String codeDirId;

    /**
     * 码表编码
     */
    private String codeId;

    /**
     * 码表名称
     */
    private String codeName;

//    /** 对应表编码 */
//    private Integer codeTableId;
//
//    /** 对应表名称 */
//    private String codeTableName;

//    /** 数据类型编码 */
//    private Integer termId;

    /**
     * 开始时间
     */
    private String beginDay;

    /**
     * 结束时间
     */
    private String endDay;
}
