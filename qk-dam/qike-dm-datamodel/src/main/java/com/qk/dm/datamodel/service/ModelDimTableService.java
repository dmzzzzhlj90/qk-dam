package com.qk.dm.datamodel.service;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.datamodel.params.dto.ModelDimTableDTO;
import com.qk.dm.datamodel.params.dto.ModelDimTableQueryDTO;
import com.qk.dm.datamodel.params.vo.ModelDimTableDetailVO;
import com.qk.dm.datamodel.params.vo.ModelDimTableVO;

import java.util.List;

public interface ModelDimTableService {
    void insert(ModelDimTableDTO modelDimTableDTO);

    ModelDimTableDetailVO detail(Long id);

    void updateDim(Long modelDimId,ModelDimTableDTO modelDimTableDTO);

    void update(ModelDimTableDTO modelDimTableDTO);

    void delete(String ids);

    PageResultVO<ModelDimTableVO> list(ModelDimTableQueryDTO modelDimTableQueryDTO);

    /**
     * 同步
     * @param idList
     */
    void sync(List<Long> idList);

    /**
     * 数据落库
     * @param idList
     */
    void fallLibrary(List<Long> idList);

    /**
     * 预览SQL
     * @param tableId
     * @return
     */
    String previewSql(Long tableId);

}
