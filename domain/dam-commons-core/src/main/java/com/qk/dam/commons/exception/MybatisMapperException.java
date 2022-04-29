package com.qk.dam.commons.exception;

import com.qk.dam.commons.enums.ResultCodeEnum;

/**
 * 系统业务异常
 *
 * @author daomingzhu
 */
public class MybatisMapperException extends RuntimeException {
  final ResultCodeEnum resultCode;

  public MybatisMapperException() {
    this(ResultCodeEnum.BAD_REQUEST);
  }

  public MybatisMapperException(ResultCodeEnum resultCode) {
    super(resultCode.getFormattedErrorMessage());
    this.resultCode = resultCode;
  }

  public MybatisMapperException(String msg) {
    super(msg);
    this.resultCode = ResultCodeEnum.BAD_REQUEST;
  }

  public MybatisMapperException(String message, Throwable cause) {
    super(message, cause);
    this.resultCode = ResultCodeEnum.BAD_REQUEST;
  }

  public MybatisMapperException(Throwable cause) {
    super(cause);
    this.resultCode = ResultCodeEnum.BAD_REQUEST;
  }

  public MybatisMapperException(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
    this.resultCode = ResultCodeEnum.BAD_REQUEST;
  }

  public MybatisMapperException(final ResultCodeEnum errorCode, Throwable cause) {
    super(errorCode.getFormattedErrorMessage(), cause);
    this.resultCode = ResultCodeEnum.BAD_REQUEST;
  }
}
