package com.qk.dam.openapi;

import com.google.common.collect.Lists;
import org.openapi4j.core.exception.EncodeException;
import org.openapi4j.core.exception.ResolutionException;
import org.openapi4j.core.validation.ValidationException;
import org.openapi4j.parser.model.SerializationFlag;
import org.openapi4j.parser.model.v3.*;

import java.net.MalformedURLException;
import java.util.EnumSet;
import java.util.List;

public class DamOpenApiApplication {

    public static String test1() throws EncodeException {
        List<ComponentField> componentFields = Lists.newArrayList(
                new ComponentField("key","测key","string",true),
                new ComponentField("ctype","cccctype","string",false));

        OpenApi3 openApi3 = OpenapiBuilder
                .builder()
                .build()
                .info("test","测试一下","3.0.3")
                .components("GetentidPayload",componentFields)
                .path("/api/v2/getentid/","post","这个接口啊啊啊")
                .requestBody("/api/v2/getentid/","post",true,OpenapiBuilder.MEDIA_CONTENT_FORM,"GetentidPayload")
                .response("/api/v2/getentid/","post","200","ok")
                .getOpenApi3();
        EnumSet<SerializationFlag> enumSet = EnumSet.of(SerializationFlag.OUT_AS_JSON);
        return openApi3.toString(enumSet);
    }
    public static void main(String[] args) throws MalformedURLException, ResolutionException, ValidationException, EncodeException {
        System.out.println(test1());

    }
}
