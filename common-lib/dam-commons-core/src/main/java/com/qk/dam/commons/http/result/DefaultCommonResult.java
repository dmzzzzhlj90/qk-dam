package com.qk.dam.commons.http.result;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.qk.dam.commons.enums.ResultCodeEnum;

/**
 * 默认返回结果封装
 *
 * @author daomingzhu
 */
public class DefaultCommonResult<T> extends BaseResult<T> {

  public DefaultCommonResult() {
    throw new IllegalStateException("Utility class");
  }

  DefaultCommonResult(ResultCodeEnum code, T data, String tips) {
    setRetCode(code.getCode());
    setRetMsg(code.getFormattedErrorMessage());
    setData(data);
    setTips(tips);
  }

  DefaultCommonResult(T data, String retCode, String retMsg) {
    setRetCode(retCode);
    setRetMsg(retMsg);
    setData(data);
  }
  @JsonCreator
  public static DefaultCommonResult<Object> success() {
    return success(ResultCodeEnum.OK, null);
  }

  public static <T> DefaultCommonResult<T> success(ResultCodeEnum codeEnum, T data) {
    return success(codeEnum, data, null);
  }

  public static <T> DefaultCommonResult<T> success(ResultCodeEnum codeEnum, String tips) {
    return success(codeEnum, (T) null, tips);
  }

  public static <T> DefaultCommonResult<T> success(ResultCodeEnum codeEnum, T data, String tips) {
    return new DefaultCommonResult<>(codeEnum, data, tips);
  }

  public static <T> DefaultCommonResult<T> success(T data, String retCode, String retMsg) {
    return new DefaultCommonResult(data, retCode, retMsg);
  }

  public static <T> DefaultCommonResult<T> fail() {
    return fail(ResultCodeEnum.BAD_REQUEST);
  }

  public static <T> DefaultCommonResult<T> fail(ResultCodeEnum codeEnum) {
    return fail(codeEnum, null);
  }

  public static <T> DefaultCommonResult<T> fail(ResultCodeEnum codeEnum, String tips) {
    return fail(codeEnum, null, tips);
  }

  public static <T> DefaultCommonResult<T> fail(ResultCodeEnum codeEnum, T data, String tips) {
    return new DefaultCommonResult<>(codeEnum, data, tips);
  }
}
