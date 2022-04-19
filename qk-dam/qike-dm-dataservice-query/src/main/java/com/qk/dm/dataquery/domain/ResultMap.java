package com.qk.dm.dataquery.domain;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResultMap {
    @JacksonXmlProperty(isAttribute = true)
    private String id;
    @JacksonXmlProperty(isAttribute = true)
    private Boolean autoMapping;
    @JacksonXmlProperty(isAttribute = true)
    private String type;
    @JacksonXmlElementWrapper(localName = "result",useWrapping = false)
    private List<Result> result;

    @Data
    @AllArgsConstructor
    public static class Result{
        @JacksonXmlProperty(isAttribute = true)
        private String column;
        @JacksonXmlProperty(isAttribute = true)
        private String property;
        @JacksonXmlProperty(isAttribute = true)
        private String javaType;

    }
}
