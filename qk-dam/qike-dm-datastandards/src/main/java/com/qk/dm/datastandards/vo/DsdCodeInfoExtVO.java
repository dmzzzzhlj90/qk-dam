package com.qk.dm.datastandards.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DsdCodeInfoExtVO {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 关键码表信息表
     */
    private Long dsdCodeInfoId;

    /**
     * 建表配置编码
     */
    private String tableConfCode;

    /**
     * 建表配置值
     */
    private String tableConfValue;

    /**
     * 修改时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtModified;

    /**
     * 建表配合扩展字段数值
     */
    private Map<String, String> codeTableFieldExtValues;

}
