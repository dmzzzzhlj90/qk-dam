package com.qk.dam.commons.util;

import com.google.gson.*;
import java.lang.reflect.Type;
import java.util.Map;

public final class GsonUtil {
  private static final Gson gson = GsonFactory.geInstance().create();

  private GsonUtil() {
    throw new AssertionError();
  }

  public static String toJsonString(Object object) {
    return gson.toJson(object);
  }

  public static String toJsonString(Object object, Type typeOfT) {
    return gson.toJson(object, typeOfT);
  }

  public static Object fromJsonString(String json) {
    return gson.fromJson(json, Object.class);
  }

  public static <T> T fromJsonString(String json, Type typeOfT) {
    return gson.fromJson(json, typeOfT);
  }

  public static <T> T fromMap(Map<String, Object> map, Class<T> type) {
    return gson.fromJson(gson.toJson(map), type);
  }

  public static Map<String, Object> toMap(Object object) {
    return (Map) gson.fromJson(gson.toJson(object), Map.class);
  }

  public static JsonObject toJsonObject(String jsonStringObject) {
    return JsonParser.parseString(jsonStringObject).getAsJsonObject();
  }

  public static JsonArray toJsonArray(String jsonStringArray) {
    return JsonParser.parseString(jsonStringArray).getAsJsonArray();
  }

  public static JsonElement toJsonElement(String jsonStringElement) {
    return JsonParser.parseString(jsonStringElement);
  }

  public static JsonElement jsonElementFrom(String jsonString) {
    return JsonParser.parseString(jsonString);
  }

  public static boolean isJsonObject(String jsonStringObject) {
    try {
      return toJsonObject(jsonStringObject).isJsonObject();
    } catch (IllegalStateException var2) {
      return false;
    }
  }

  public static boolean isJsonArray(String jsonStringArray) {
    try {
      return toJsonArray(jsonStringArray).isJsonArray();
    } catch (IllegalStateException var2) {
      return false;
    }
  }
}
