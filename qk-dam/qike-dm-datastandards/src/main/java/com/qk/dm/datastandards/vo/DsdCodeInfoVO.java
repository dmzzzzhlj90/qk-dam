package com.qk.dm.datastandards.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DsdCodeInfoVO {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 所属目录id
     */
    @NotNull(message = "所属目录id不能为空！")
    private String codeDirId;

    /**
     * 所属目录层级
     */
    @NotBlank(message = "所属目录层级不能为空！")
    private String codeDirLevel;

    /**
     * 基础配置-表名
     */
    @NotBlank(message = "表名不能为空！")
    private String tableName;

    /**
     * 基础配置-表编码
     */
    @NotBlank(message = "表名不能为空！")
    private String tableCode;

    /**
     * 基础配置-描述
     */
    private String tableDesc;

    /**
     * 修改时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtModified;

    /**
     * 建表配置字段
     */
    private List<CodeTableFieldsVO> codeTableFieldsList;

}


