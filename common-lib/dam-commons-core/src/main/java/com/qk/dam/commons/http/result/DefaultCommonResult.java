package com.qk.dam.commons.http.result;

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

  DefaultCommonResult(ResultCodeEnum code) {
    setRetCode(code.getCode());
    setRetMsg(code.getFormattedErrorMessage());
  }

  DefaultCommonResult(ResultCodeEnum code, String tips) {
    setRetCode(code.getCode());
    setRetMsg(code.getFormattedErrorMessage());
    setTips(tips);
  }

  DefaultCommonResult(ResultCodeEnum code, T data) {
    setRetCode(code.getCode());
    setRetMsg(code.getFormattedErrorMessage());
    setData(data);
  }

  DefaultCommonResult(T data, String retCode, String retMsg) {
    setRetCode(retCode);
    setRetMsg(retMsg);
    setData(data);
  }

  public static DefaultCommonResult<Object> success() {
    return new DefaultCommonResult<>(ResultCodeEnum.OK);
  }

  public static <T> DefaultCommonResult<T> success(ResultCodeEnum codeEnum, T data, String tips) {
    return new DefaultCommonResult<>(codeEnum, tips);
  }

  public static <T> DefaultCommonResult<T> success(ResultCodeEnum codeEnum, T data) {
    return new DefaultCommonResult<>(codeEnum, data);
  }

  public static <T> DefaultCommonResult<T> success(T data, String retCode, String retMsg) {
    return new DefaultCommonResult(data, retCode, retMsg);
  }

  public static <T> DefaultCommonResult<T> fail() {
    return new DefaultCommonResult<>(ResultCodeEnum.BAD_REQUEST);
  }

  public static <T> DefaultCommonResult<T> fail(ResultCodeEnum codeEnum) {
    return fail(codeEnum, codeEnum.getFormattedErrorMessage());
  }

  public static <T> DefaultCommonResult<T> fail(ResultCodeEnum codeEnum, T data, String tips) {
    return new DefaultCommonResult<>(codeEnum, data);
  }

  public static <T> DefaultCommonResult<T> fail(ResultCodeEnum codeEnum, String tips) {
    return new DefaultCommonResult(codeEnum, tips);
  }
}
