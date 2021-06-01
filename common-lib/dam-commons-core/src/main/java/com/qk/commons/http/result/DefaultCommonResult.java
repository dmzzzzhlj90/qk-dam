package com.qk.commons.http.result;

import com.qk.commons.enums.ResultCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 默认返回结果封装
 * @author daomingzhu
 */
public class DefaultCommonResult<T> extends  BaseResult<T> {

    public DefaultCommonResult(){}
    public DefaultCommonResult(ResultCodeEnum code){
        setRetCode(code.getHttpStatus().value());
        setRetMsg(code.getFormattedErrorMessage());
    }
    public DefaultCommonResult(ResultCodeEnum code , T data){
        setRetCode(code.getHttpStatus().value());
        setRetMsg(code.getFormattedErrorMessage());
        setData(data);
    }
    public static <T> DefaultCommonResult<T> success(T data) {
        return success(ResultCodeEnum.OK, data);
    }

    public static <T> DefaultCommonResult<T> success(ResultCodeEnum codeEnum, T data) {
        return new DefaultCommonResult<>(codeEnum, data);
    }
    public static <T> DefaultCommonResult<T> success(T data,int retCode, String retMsg) {
        DefaultCommonResult<T> baseResult = new DefaultCommonResult<>();
        baseResult.setRetCode(retCode);
        baseResult.setRetMsg(retMsg);
        baseResult.setData(data);
        return baseResult;
    }

    public static <T> DefaultCommonResult<T> error() {
        return new DefaultCommonResult<>(ResultCodeEnum.BAD_REQUEST);
    }


    public static <T> DefaultCommonResult<T> error(ResultCodeEnum codeEnum) {
        return error(codeEnum,null);
    }

    public static <T> DefaultCommonResult<T> error(ResultCodeEnum codeEnum, T data) {
        return new DefaultCommonResult<>(codeEnum, data);
    }

    public static <T> DefaultCommonResult<T> error(String traceId, int retCode, String retMsg) {
        DefaultCommonResult<T> baseResult = new DefaultCommonResult<>();
        baseResult.setRetCode(retCode);
        baseResult.setRetMsg(retMsg);
        return baseResult;
    }
}
