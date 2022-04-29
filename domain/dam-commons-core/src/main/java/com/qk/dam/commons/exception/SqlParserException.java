package com.qk.dam.commons.exception;

import com.qk.dam.commons.enums.ResultCodeEnum;

/**
 * sql 解析异常
 * 
 * @author zhudaoming
 */
public class SqlParserException extends RuntimeException {
    final ResultCodeEnum resultCode;

    public SqlParserException() {
        this(ResultCodeEnum.BAD_REQUEST);
    }

    public SqlParserException(ResultCodeEnum resultCode) {
        super(resultCode.getFormattedErrorMessage());
        this.resultCode = resultCode;
    }

    public SqlParserException(String msg) {
        super(msg);
        this.resultCode = ResultCodeEnum.BAD_REQUEST;
    }

    public SqlParserException(String message, Throwable cause) {
        super(message, cause);
        this.resultCode = ResultCodeEnum.BAD_REQUEST;
    }

    public SqlParserException(Throwable cause) {
        super(cause);
        this.resultCode = ResultCodeEnum.BAD_REQUEST;
    }

    public SqlParserException(
            String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.resultCode = ResultCodeEnum.BAD_REQUEST;
    }

    public SqlParserException(final ResultCodeEnum errorCode, Throwable cause) {
        super(errorCode.getFormattedErrorMessage(), cause);
        this.resultCode = ResultCodeEnum.BAD_REQUEST;
    }
}
