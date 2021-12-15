package com.qk.dm.metadata.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.metedata.entity.*;
import com.qk.dm.metadata.service.MtdSearchService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 元数据查询
 * @author wangzp
 * @date 2021/12/15 16:43
 * @since 1.0.0
 */
@RestController
public class MtdSearchController {

    private final MtdSearchService mtdSearchService;

    public MtdSearchController(MtdSearchService mtdSearchService){
        this.mtdSearchService = mtdSearchService;
    }

    /**
     * 获取数据库列表
     * @param mtdApiParams
     * @return
     */
    @PostMapping("/database/list")
    public DefaultCommonResult<List<MtdApiDb>> getDataBaseList(@RequestBody @Validated MtdApiParams mtdApiParams){
        return DefaultCommonResult.success(ResultCodeEnum.OK,mtdSearchService.getDataBaseList(mtdApiParams));
    }

    /**
     * 获取数据库下的表
     * @param mtdApiParams
     * @return
     */
    @PostMapping("/table/list")
    public DefaultCommonResult<List<MtdTables>> getTableList(@RequestBody @Validated MtdApiParams mtdApiParams){
        return DefaultCommonResult.success(ResultCodeEnum.OK,mtdSearchService.getTableList(mtdApiParams));
    }

    /**
     * 获取表下的字段
     * @param mtdApiParams
     * @return
     */
    @PostMapping("/column/list")
    public DefaultCommonResult<List<MtdAttributes>> getColumnList(@RequestBody @Validated MtdApiParams mtdApiParams){
        return DefaultCommonResult.success(ResultCodeEnum.OK,mtdSearchService.getColumnList(mtdApiParams));
    }

    /**
     * 通过属性字段查询数据库信息
     * @param mtdApiAttrParams
     * @return
     */
    @PostMapping("/db/attr/list")
    public DefaultCommonResult<List<MtdApiDb>> getDataBaseListByAttr(@RequestBody @Validated MtdApiAttrParams mtdApiAttrParams){
        return DefaultCommonResult.success(ResultCodeEnum.OK,mtdSearchService.getDataBaseListByAttr(mtdApiAttrParams));
    }

}
