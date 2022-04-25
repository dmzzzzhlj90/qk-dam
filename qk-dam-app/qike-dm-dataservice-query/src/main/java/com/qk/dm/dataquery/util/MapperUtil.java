package com.qk.dm.dataquery.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.qk.dam.commons.exception.MybatisMapperException;
import com.qk.dm.dataquery.model.Mapper;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhudaoming
 */
@Slf4j
public class MapperUtil {
     MapperUtil(){
        throw new IllegalStateException("Utility class");
    }
    public static String getMapperXmlStr(Mapper mapper){
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        StringBuilder headXml = new StringBuilder("<?xml version='1.0' encoding='UTF-8'?>");
        headXml.append("<!DOCTYPE mapper\n" +
                "        PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\"\n" +
                "        \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">");
        try {
            String xmlContent = headXml.append(xmlMapper.writeValueAsString(mapper)).toString();
            xmlContent = getEscapeContent(xmlContent);
            log.info("生成mybatis mapper xml 【{}】",xmlContent);
            return xmlContent;
        } catch (JsonProcessingException e) {
            throw new MybatisMapperException("生成mapper xml错误");
        }
    }

    public static String getEscapeContent(String xmlContent) {
        xmlContent = escapeIf(xmlContent);
        xmlContent = escapeWhere(xmlContent);
        xmlContent = escapeChoose(xmlContent);
        xmlContent = escapeForeach(xmlContent);
        xmlContent = escapeTrim(xmlContent);
        return xmlContent;
    }

    public static String escapeIf(String xmlContent) {
        return xmlContent.replace("&lt;if","<if").replace("&lt;/if","</if");
    }
    public static String escapeWhere(String xmlContent) {
        return xmlContent.replace("&lt;where","<where").replace("&lt;/where","</where");
    }
    public static String escapeChoose(String xmlContent) {
        return xmlContent.replace("&lt;choose","<choose").replace("&lt;/choose","</choose");
    }
    public static String escapeForeach(String xmlContent) {
        return xmlContent.replace("&lt;foreach","<foreach").replace("&lt;/foreach","</foreach");
    }
    public static String escapeTrim(String xmlContent) {
        return xmlContent.replace("&lt;trim","<trim").replace("&lt;/trim","</trim");
    }
}
