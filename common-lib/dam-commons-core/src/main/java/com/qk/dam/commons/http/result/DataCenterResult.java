package com.qk.dam.commons.http.result;

import com.qk.dam.commons.enums.DataCenterResultCodeEnum;
import lombok.Data;

/**
 * 数据中心默认返回结果封装
 *
 * @author wjq
 */
@Data
public class DataCenterResult<T> {

    /**
     * 返回码
     */
    private String code;
    /**
     * 返回说明
     */
    private String error;

    /**
     * 返回数据
     */
    private T data;

    public DataCenterResult() {
        throw new IllegalStateException("Utility class");
    }

    DataCenterResult(DataCenterResultCodeEnum code, T data, String error) {
        setCode(code.getCode());
        setData(data);
        setError(error);
    }

    public static <T> DataCenterResult<T> success(DataCenterResultCodeEnum codeEnum, T data) {
        return success(codeEnum, data, null);
    }

    public static <T> DataCenterResult<T> success(DataCenterResultCodeEnum codeEnum, String error) {
        return success(codeEnum, (T) null, error);
    }

    public static <T> DataCenterResult<T> success(DataCenterResultCodeEnum codeEnum, T data, String error) {
        return new DataCenterResult<T>(codeEnum, data, error);
    }

    public static <T> DataCenterResult<T> fail() {
        return fail(DataCenterResultCodeEnum.BAD_REQUEST);
    }

    public static <T> DataCenterResult<T> fail(DataCenterResultCodeEnum codeEnum) {
        return fail(codeEnum, null);
    }

    public static <T> DataCenterResult<T> fail(DataCenterResultCodeEnum codeEnum, String tips) {
        return fail(codeEnum, null, tips);
    }

    public static <T> DataCenterResult<T> fail(DataCenterResultCodeEnum codeEnum, T data, String tips) {
        return new DataCenterResult<T>(codeEnum, data, tips);
    }
}
