package com.qk.dam.openapi;

import lombok.*;
import org.openapi4j.core.model.v3.OAI3Context;
import org.openapi4j.parser.model.v3.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Builder
@EqualsAndHashCode(callSuper = false)
@Data
public class OpenapiBuilder {
    public static final String MEDIA_CONTENT_FORM = "application/x-www-form-urlencoded";
    public static final String MEDIA_CONTENT_JSON = "application/json; charset=utf-8";
    public static final String MEDIA_CONTENT_ALL = "*/*";
    private final OpenApi3 openApi3 = new OpenApi3();
    public OpenapiBuilder(){
        try {
            openApi3.setOpenapi("3.0.3");
            openApi3.setContext(new OAI3Context(new URL("http://172.20.0.8:18910/")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public OpenApi3 getOpenApi3() {
        return openApi3;
    }

    public OpenapiBuilder info(String title, String description, String version) {
        openApi3.setInfo(new Info()
                .setTitle(title)
                .setDescription(description)
                .setVersion(version));
        return this;
    }

    public OpenapiBuilder components(String name,
                                     List<ComponentField> componentFields) {

        Components components = openApi3.getComponents();
        if (components == null) {
            components = new Components();
        }
        Map<String, Schema> componentsSchemas = components.getSchemas();
        if (componentsSchemas == null) {
            componentsSchemas = new HashMap<>();
        }

        Map<String, Schema> properties = new HashMap<>();
        componentFields
                .forEach(componentField ->
                        properties.put(componentField.getFieldName(),
                                new Schema()
                                        .setTitle(componentField.getFieldName())
                                        .setType(componentField.getType())
                                        .setDescription(componentField.getDesc())));

        List<String> requiredFields = componentFields.stream()
                .filter(ComponentField::isRequired).map(ComponentField::getFieldName)
                .collect(Collectors.toList());


        componentsSchemas.put(name, new Schema()
                .setProperties(properties)
                .setTitle(name)
                .setRequiredFields(requiredFields));
        components.setSchemas(componentsSchemas);
        openApi3.setComponents(components);
        return this;
    }

    public OpenapiBuilder tag(String name, String desc) {
        List<Tag> tags = openApi3.getTags();
        if (tags == null) {
            tags = new ArrayList<>();
        }
        tags.add(new Tag().setName(name).setDescription(desc));
        openApi3.setTags(tags);
        return this;
    }

    public OpenapiBuilder path(String pathName,
                               String httpMethod,
                               String summary) {
        openApi3.setPath(pathName, new Path()
                .setOperation(httpMethod, new Operation()
                        .setSummary(summary)));
        return this;
    }

    public OpenapiBuilder requestBody(String pathName,
                                      String httpMethod,
                                      boolean required,
                                      String mediaType,
                                      String refValue) {

        Path path = openApi3.getPath(pathName);
        Operation operation = path.getOperation(httpMethod);
        RequestBody requestBody = operation.getRequestBody();
        if (requestBody == null) {
            requestBody = new RequestBody();
        }
        Map<String, MediaType> contentMediaTypes = new HashMap<>();
        MediaType mt = new MediaType();
        Schema schema = new Schema();
        schema.setReference(openApi3.getContext(),openApi3.getContext().getBaseUrl(), "#/components/schemas/"+refValue);
        mt.setSchema(schema);
        contentMediaTypes.put(mediaType.endsWith("json") ? MEDIA_CONTENT_JSON : MEDIA_CONTENT_FORM, mt);
        requestBody.setRequired(required).setContentMediaTypes(contentMediaTypes);
        operation.setRequestBody(requestBody);
        path.setOperation(httpMethod, operation);
        return this;
    }

    /**
     * @param in       path or query
     * @param name     field
     * @param required true false
     * @param type     lx
     */
    public OpenapiBuilder parameter(
            String pathName,
            String in,
            String name,
            boolean required,
            String type) {
        Path path = openApi3.getPath(pathName);
        List<Parameter> parameters = path.getParameters();
        if (parameters == null) {
            parameters = new ArrayList<>();
        }
        Parameter parameter = new Parameter();
        parameter.setIn(in)
                .setName(name)
                .setRequired(required)
                .setSchema(new Schema()
                        .setType(type)
                        .setFormat(type.startsWith("int") ? "int64" : null));
        parameters.add(parameter);
        path.setParameters(parameters);
        return this;
    }

    public OpenapiBuilder response(String pathName,
                                   String httpMethod,
                                   String responseCode,
                                   String responseDesc) {
        Path path = openApi3.getPath(pathName);
        Operation operation = path.getOperation(httpMethod);

        Map<String, Response> responses = operation.getResponses();
        if (responses==null){
            responses = new HashMap<>();
        }

        responses.put(responseCode, new Response().setDescription(responseDesc));
        operation.setResponses(responses);
        return this;
    }
    public OpenapiBuilder response(String pathName,
                                   String httpMethod,
                                   String responseCode,
                                   String responseDesc,
                                   String refValue) {
        Path path = openApi3.getPath(pathName);
        Operation operation = path.getOperation(httpMethod);

        Map<String, Response> responses = operation.getResponses();

        MediaType mediaType = new MediaType();
        Schema schema = new Schema();
        schema.setReference(openApi3.getContext(), openApi3.getContext().getBaseUrl(), "#/components/schemas/"+refValue);
        mediaType.setSchema(schema);

        responses.put(responseCode, new Response().setDescription(responseDesc).setContentMediaType("*/*", mediaType));
        operation.setResponses(responses);
        return this;
    }
}
