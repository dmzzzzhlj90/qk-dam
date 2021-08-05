package com.qk.dm.metadata.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MtdLabelsAtlasVO implements Serializable {

    /**
     * 元数据标识
     */
    @NotBlank(message = "元数据标识不能为空！")
    private String guid;

    /**
     * 元数据标签
     */
    private String labels;

}
