package com.qk.dm.metadata.service;

import com.qk.dam.metedata.entity.*;

import java.util.List;

public interface MtdSearchService {
    /**
     * 获取数据库
     * @param mtdApiParams
     * @return
     */
    List<MtdApiDb> getDataBaseList(MtdApiParams mtdApiParams);

    /**
     * 获取表
     * @param mtdApiParams
     * @return
     */
    List<MtdTables> getTableList(MtdApiParams mtdApiParams);

    /**
     * 获取字段
     * @param mtdApiParams
     * @return
     */
    List<MtdAttributes> getColumnList(MtdApiParams mtdApiParams);

    /**
     * 根据属性值获取数据库列表
     * @param mtdApiAttrParams
     * @return
     */
    List<MtdApiDb> getDataBaseListByAttr(MtdApiAttrParams mtdApiAttrParams);
}
