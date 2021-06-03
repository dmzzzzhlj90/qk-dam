package com.qk.commons.exception;

import com.qk.commons.enums.ResultCodeEnum;

/**
 * 系统业务异常
 * @author daomingzhu
 */
public class BizException extends RuntimeException {
    private final ResultCodeEnum resultCode;

    public BizException(){
        this(ResultCodeEnum.BAD_REQUEST);
    }
    public BizException(ResultCodeEnum resultCode) {
        super(resultCode.getFormattedErrorMessage());
        this.resultCode = resultCode;
    }
    public BizException(String msg) {
        super(msg);
        this.resultCode = ResultCodeEnum.BAD_REQUEST;
    }
    public BizException(String message, Throwable cause) {
        super(message, cause);
        this.resultCode = ResultCodeEnum.BAD_REQUEST;
    }
    public BizException(Throwable cause) {
        super(cause);
        this.resultCode = ResultCodeEnum.BAD_REQUEST;
    }

    public BizException(String message, Throwable cause, boolean enableSuppression,
                          boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.resultCode = ResultCodeEnum.BAD_REQUEST;
    }

    public BizException(final ResultCodeEnum errorCode, Throwable cause) {
        super(errorCode.getFormattedErrorMessage(), cause);
        this.resultCode = ResultCodeEnum.BAD_REQUEST;
    }
}