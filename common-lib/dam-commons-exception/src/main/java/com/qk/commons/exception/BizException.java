package com.qk.commons.exception;

import com.qk.commons.enums.ResultCodeEnum;

/**
 * 系统业务异常
 * @author daomingzhu
 */
public class BizException extends RuntimeException {
    private final ResultCodeEnum resultCode;

    public BizException(){
        this(ResultCodeEnum.SYS_RUNTIME_ERROR);
    }
    public BizException(ResultCodeEnum resultCode) {
        super(resultCode.getFormattedErrorMessage());
        this.resultCode = resultCode;
    }
    public BizException(String msg) {
        super(msg);
        this.resultCode = ResultCodeEnum.SYS_RUNTIME_ERROR;
    }
    public BizException(String message, Throwable cause) {
        super(message, cause);
        this.resultCode = ResultCodeEnum.SYS_RUNTIME_ERROR;
    }
    public BizException(Throwable cause) {
        super(cause);
        this.resultCode = ResultCodeEnum.SYS_RUNTIME_ERROR;
    }

    public BizException(String message, Throwable cause, boolean enableSuppression,
                          boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.resultCode = ResultCodeEnum.SYS_RUNTIME_ERROR;
    }

    public BizException(final ResultCodeEnum errorCode, Throwable cause) {
        super(errorCode.getFormattedErrorMessage(), cause);
        this.resultCode = ResultCodeEnum.SYS_RUNTIME_ERROR;
    }
}