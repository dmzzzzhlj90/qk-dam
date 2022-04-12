package com.qk.dm.dataingestion.model.hive;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class HiveBasePara {
    /**
     * 要读取的文件路径，如果要读取多个文件，可以使用正则表达式"*"
     */
    private String path;
    /**
     * Hadoop hdfs文件系统namenode节点地址
     */
    private String defaultFS;

    /**
     * 文件的类型，目前只支持用户配置为"text"、"orc"、"rc"、"seq"、"csv"
     */
    private String fileType;
    /**
     * 读取的字段分隔符
     */
    private String fieldDelimiter;
    /**
     * 字段信息
     */
    private List<Column> column;

    @Data
    @Builder
    public static class Column{
        private String name;
        private String type;
    }

}
