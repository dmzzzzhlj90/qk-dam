package com.qk.dm.dataquery.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Builder;
import lombok.Data;

/**
 * mybatis 缓存
 *
 * @author zhudaoming
 */
@Data
@Builder
public class Cache {
    @JacksonXmlProperty(localName = "type",isAttribute = true)
    private String type;
    @JacksonXmlProperty(localName = "eviction",isAttribute = true)
    private String eviction;
    @JacksonXmlProperty(localName = "flushInterval",isAttribute = true)
    private String flushInterval;
    @JacksonXmlProperty(localName = "size",isAttribute = true)
    private String size;
}
