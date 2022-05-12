package com.qk.dm.reptile.params.dto;

import com.qk.dam.jpa.pojo.Pagination;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RptFindSourceDTO {

    private Pagination pagination;

    /**
     * 标题
     */
    @NotBlank(message = "标题不能为空")
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


}
