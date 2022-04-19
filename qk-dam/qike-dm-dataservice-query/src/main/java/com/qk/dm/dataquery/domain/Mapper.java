package com.qk.dm.dataquery.domain;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonRootName("mapper")
@JacksonXmlRootElement(localName = "mapper")
public class Mapper {
    @JacksonXmlProperty(localName = "namespace",isAttribute = true)
    private String namespace;

    @JacksonXmlElementWrapper(localName = "resultMap",useWrapping = false)
    private List<ResultMap> resultMap;

    @JacksonXmlElementWrapper(localName = "select",useWrapping = false)
    private List<MapperSelect> select;
}
