package com.qk.dm.datamodel.params.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ModelDTO implements Serializable {

    /**
     * 模型名称
     */
    private String modelName;

    /**
     * 数仓分层名称
     */
    private String layeredName;

    /**
     * 描述
     */
    private String description;

    /**
     * 1逻辑模型 2 物理模型
     */
    private Integer modelType;

}
