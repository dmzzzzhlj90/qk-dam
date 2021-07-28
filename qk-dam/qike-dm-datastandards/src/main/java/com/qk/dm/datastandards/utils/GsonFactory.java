//package com.qk.dm.datastandards.utils;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.google.gson.JsonDeserializationContext;
//import com.google.gson.JsonDeserializer;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonPrimitive;
//import com.google.gson.JsonSerializationContext;
//import com.google.gson.JsonSerializer;
//import java.lang.reflect.Type;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//public class GsonFactory {
//    private static final GsonFactory INSTANCE = new GsonFactory();
//    private final GsonBuilder builder = (new GsonBuilder()).serializeNulls().disableHtmlEscaping();
//
//    public static GsonFactory geInstance() {
//        return INSTANCE;
//    }
//
//    private GsonFactory() {
//        this.builder.registerTypeAdapter(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
//            public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
//                return new JsonPrimitive(src.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
//            }
//        }).registerTypeAdapter(LocalDate.class, new JsonSerializer<LocalDate>() {
//            public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
//                return new JsonPrimitive(src.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//            }
//        }).registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
//            public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
//                String datetime = json.getAsJsonPrimitive().getAsString();
//                return LocalDateTime.parse(datetime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//            }
//        }).registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
//            public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
//                String datetime = json.getAsJsonPrimitive().getAsString();
//                return LocalDate.parse(datetime, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//            }
//        });
//    }
//
//    public GsonFactory registry(Class<?> type, Object adapter) {
//        this.builder.registerTypeAdapter(type, adapter);
//        return this;
//    }
//
//    public Gson create() {
//        return this.builder.create();
//    }
//}