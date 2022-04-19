package com.qk.dam.metedata.property;

/**
 * 查询元数据，判断是否存在表或数据
 * @author wangzp
 * @date 2021/12/04 11:08
 * @since 1.0.0
 */
public class SearchResultProperty {
    /**
     * 不存在
     */
    public static final int NO_EXIST = 0;
    /**
     * 存在但没有数据
     */
    public static final int EXIST_NO_DATA = 1;
    /**
     * 存在且有数据
     */
    public static final int EXIST_DATA = 2;
}
