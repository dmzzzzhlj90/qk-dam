package com.qk.dm.dataingestion.model;

import lombok.Builder;
import lombok.Data;

/**
 * datax 数据对象
 * @param <T>
 */
@Data
@Builder
public class DataxChannel {
    /**
     * 读取数据的插件名称
     */
    private String name;
    /**
     * 参数
     */
    private Object parameter;

}
