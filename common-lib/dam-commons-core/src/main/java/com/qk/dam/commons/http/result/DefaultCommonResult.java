package com.qk.dam.commons.http.result;

import com.qk.dam.commons.enums.ResultCodeEnum;

/**
 * 默认返回结果封装
 *
 * @author daomingzhu
 */
public class DefaultCommonResult<T> extends BaseResult<T> {

  private static final long serialVersionUID = 4717403718355790656L;

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

  public static <T> DefaultCommonResult<T> success(T data) {
    return success(ResultCodeEnum.OK, data);
  }

  public static <T> DefaultCommonResult<T> success(ResultCodeEnum codeEnum, String tips) {
    return new DefaultCommonResult<>(codeEnum, tips);
  }

  public static <T> DefaultCommonResult<T> success(ResultCodeEnum codeEnum, T data) {
    return new DefaultCommonResult<>(codeEnum, data);
  }

  public static <T> DefaultCommonResult<T> success(T data, String retCode, String retMsg) {
    DefaultCommonResult<T> baseResult = new DefaultCommonResult<>();
    baseResult.setRetCode(retCode);
    baseResult.setRetMsg(retMsg);
    baseResult.setData(data);
    return baseResult;
  }

  public static <T> DefaultCommonResult<T> fail() {
    return new DefaultCommonResult<>(ResultCodeEnum.BAD_REQUEST);
  }

  public static <T> DefaultCommonResult<T> fail(ResultCodeEnum codeEnum) {
    return fail(codeEnum, codeEnum.getFormattedErrorMessage());
  }

  public static <T> DefaultCommonResult<T> fail(ResultCodeEnum codeEnum, T data) {
    return new DefaultCommonResult<>(codeEnum, data);
  }

  public static <T> DefaultCommonResult<T> fail(ResultCodeEnum codeEnum, String tips) {
    DefaultCommonResult<T> baseResult = new DefaultCommonResult<>();
    baseResult.setRetCode(codeEnum.getCode());
    baseResult.setRetMsg(codeEnum.getFormattedErrorMessage());
    baseResult.setTips(tips);
    return baseResult;
  }
}
