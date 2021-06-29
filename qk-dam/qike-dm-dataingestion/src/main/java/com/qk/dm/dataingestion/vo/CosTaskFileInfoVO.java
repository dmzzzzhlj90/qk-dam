package com.qk.dm.dataingestion.vo;

import lombok.*;

/**
 * @author wjq
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class CosTaskFileInfoVO {

    /**
     * 表名称
     */
    private String tableName;

    /**
     * 批次
     */
    private String pici;

    /**
     * 文件下载路径地址
     */
    private String filePath;

}
