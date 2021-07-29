package com.qk.dm.datastandards.vo.params;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qk.dm.datastandards.vo.Pagination;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DsdCodeInfoParamsVO {

    private Pagination pagination;

    /**
     * 开始时间
     */
    private String beginDay;

    /**
     * 结束时间
     */
    private String endDay;


    /**
     * 所属目录id
     */
    @NotBlank(message = "所属目录id不能为空！")
    private String codeDirId;

    /**
     * 所属目录层级
     */
    @NotBlank(message = "所属目录层级不能为空！")
    private String codeDirLevel;

    /**
     * 基础配置-表名
     */
    private String tableName;

    /**
     * 基础配置-表编码
     */
    private String tableCode;

}
