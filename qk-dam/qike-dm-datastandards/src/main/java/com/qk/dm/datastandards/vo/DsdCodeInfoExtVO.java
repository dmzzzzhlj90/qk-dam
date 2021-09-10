package com.qk.dm.datastandards.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.LinkedHashMap;

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
    @NotBlank(message = "码表Id不能为空！")
    private String dsdCodeInfoId;

    /**
     * 建表配置查询编码
     */
    @Column(name = "search_code")
    private String searchCode;

    /**
     * 建表配置查询值
     */
    @Column(name = "search_value")
    private String searchValue;

    /**
     * 修改时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtModified;

    /**
     * 建表配合扩展字段数值
     */
    private LinkedHashMap<String, String> codeTableFieldExtValues;
}
