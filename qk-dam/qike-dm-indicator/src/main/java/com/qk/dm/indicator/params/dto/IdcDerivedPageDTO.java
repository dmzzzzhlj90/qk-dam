package com.qk.dm.indicator.params.dto;

import com.qk.dam.jpa.pojo.Pagination;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IdcDerivedPageDTO {

    private Pagination pagination;

    /**
     * 衍生指标名称
     */
    private String derivedIndicatorName;

    /**
     * 创建时间
     */
    private Date gmtCreate;
}
