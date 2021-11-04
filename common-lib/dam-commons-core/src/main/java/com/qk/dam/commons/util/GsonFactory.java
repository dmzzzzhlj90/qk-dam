package com.qk.dam.commons.util;

import com.google.gson.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GsonFactory {
  private static final GsonFactory INSTANCE = new GsonFactory();
  private final GsonBuilder builder = (new GsonBuilder()).serializeNulls().disableHtmlEscaping();

  public static GsonFactory geInstance() {
    return INSTANCE;
  }

  private GsonFactory() {
    this.builder
        .registerTypeAdapter(
            LocalDateTime.class,
            (JsonSerializer<LocalDateTime>)
                (src, typeOfSrc, context) ->
                    new JsonPrimitive(
                        src.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))))
        .registerTypeAdapter(
            LocalDate.class,
            (JsonSerializer<LocalDate>)
                (src, typeOfSrc, context) ->
                    new JsonPrimitive(src.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))))
        .registerTypeAdapter(
            LocalDateTime.class,
            (JsonDeserializer<LocalDateTime>)
                (json, typeOfT, context) -> {
                  String datetime = json.getAsJsonPrimitive().getAsString();
                  return LocalDateTime.parse(
                      datetime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                })
        .registerTypeAdapter(
            LocalDate.class,
            (JsonDeserializer<LocalDate>)
                (json, typeOfT, context) -> {
                  String datetime = json.getAsJsonPrimitive().getAsString();
                  return LocalDate.parse(datetime, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                });
  }

  public GsonFactory registry(Class<?> type, Object adapter) {
    this.builder.registerTypeAdapter(type, adapter);
    return this;
  }

  public Gson create() {
    return this.builder.create();
  }
}
