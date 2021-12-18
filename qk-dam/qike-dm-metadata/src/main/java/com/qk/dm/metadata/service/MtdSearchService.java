package com.qk.dm.metadata.service;

import com.qk.dam.metedata.entity.*;
import com.qk.dam.metedata.vo.MtdColumnSearchVO;
import com.qk.dam.metedata.vo.MtdDbSearchVO;
import com.qk.dam.metedata.vo.MtdTableSearchVO;

import java.util.List;

public interface MtdSearchService {
    /**
     * 获取数据库
     * @param mtdDbSearchVO 数据db查询对象
     * @return  List<MtdApiDb>
     */
    List<MtdApiDb> getDataBaseList(MtdDbSearchVO mtdDbSearchVO);

    /**
     * 获取表
     * @param mtdTableSearchVO table OBJ
     * @return List<MtdTables>
     */
    List<MtdTables> getTableList(MtdTableSearchVO mtdTableSearchVO);

    /**
     * 获取字段
     * @param mtdColumnSearchVO col Obj
     * @return List<MtdAttributes>
     */
    List<MtdAttributes> getColumnList(MtdColumnSearchVO mtdColumnSearchVO);

    /**
     * 根据属性值获取数据库列表
     * @param mtdApiAttrParams
     * @return
     */
    List<MtdApiDb> getDataBaseListByAttr(MtdApiAttrParams mtdApiAttrParams);
}
