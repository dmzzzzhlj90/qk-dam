package com.qk.dm.reptile.params.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qk.dam.jpa.pojo.Pagination;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RptFindSourceDTO {

    private Pagination pagination;

    /**
     * 标题
     */
    private String title;


    /**
     * 省编码
     */
    private String provinceCode;

    /**
     * 市编码
     */
    private String cityCode;

    /**
     * 信息类型
     */
    private String infoType;

    /**
     * 发布时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date publishTime;

    /**
     * 0未校验 1校验数据已存在 2校验不存在
     */
    private Integer status;

    /**
     * 创建人
     */
    private String createUsername;

    /**
     * 修改人
     */
    private String updateUsername;
    /**
     * 关键字
     */
    private String keywords;
    /**
     * 时间类型
     */
    private String timeType;
    /**
     * 开始时间 当时间为自定义时不可为空
     */
    private Date beginTime;
    /**
     * 结束时间 当时间为自定义时不可为空
     */
    private Date endTime;


}
