package com.qk.dm.dataquery.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.qk.dm.dataquery.domain.Mapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MapperUtil {
    public MapperUtil(){
    }
    public static String getMapperXmlStr(Mapper mapper){
        XmlMapper xmlMapper = new XmlMapper();

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
            throw new RuntimeException(e);
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
        return xmlContent.replaceAll("&lt;if","<if").replaceAll("&lt;/if","</if");
    }
    public static String escapeWhere(String xmlContent) {
        return xmlContent.replaceAll("&lt;where","<where").replaceAll("&lt;/where","</where");
    }
    public static String escapeChoose(String xmlContent) {
        return xmlContent.replaceAll("&lt;choose","<choose").replaceAll("&lt;/choose","</choose");
    }
    public static String escapeForeach(String xmlContent) {
        return xmlContent.replaceAll("&lt;foreach","<foreach").replaceAll("&lt;/foreach","</foreach");
    }
    public static String escapeTrim(String xmlContent) {
        return xmlContent.replaceAll("&lt;trim","<trim").replaceAll("&lt;/trim","</trim");
    }
}
