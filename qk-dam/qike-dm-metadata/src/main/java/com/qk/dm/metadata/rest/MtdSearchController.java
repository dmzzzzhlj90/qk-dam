package com.qk.dm.metadata.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.metedata.entity.*;
import com.qk.dam.metedata.vo.MtdColumnSearchVO;
import com.qk.dam.metedata.vo.MtdDbSearchVO;
import com.qk.dam.metedata.vo.MtdTableSearchVO;
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
     * @param mtdDbSearchVO db
     * @return List<MtdApiDb>
     */
    @PostMapping("/database/list")
    public DefaultCommonResult<List<MtdApiDb>> getDataBaseList(@RequestBody @Validated MtdDbSearchVO mtdDbSearchVO){
        return DefaultCommonResult.success(ResultCodeEnum.OK,mtdSearchService.getDataBaseList(mtdDbSearchVO));
    }

    /**
     * 获取数据库下的表
     * @param mtdTableSearchVO tvb
     * @return List<MtdTables>
     */
    @PostMapping("/table/list")
    public DefaultCommonResult<List<MtdTables>> getTableList(@RequestBody @Validated MtdTableSearchVO mtdTableSearchVO){
        return DefaultCommonResult.success(ResultCodeEnum.OK,mtdSearchService.getTableList(mtdTableSearchVO));
    }

    /**
     * 获取表下的字段
     * @param mtdColumnSearchVO col
     * @return List<MtdAttributes>
     */
    @PostMapping("/column/list")
    public DefaultCommonResult<List<MtdAttributes>> getColumnList(@RequestBody @Validated MtdColumnSearchVO mtdColumnSearchVO){
        return DefaultCommonResult.success(ResultCodeEnum.OK,mtdSearchService.getColumnList(mtdColumnSearchVO));
    }

    /**
     * 通过属性字段查询数据库信息
     * @param mtdApiAttrParams attr
     * @return List<MtdApiDb>
     */
    @PostMapping("/db/attr/list")
    public DefaultCommonResult<List<MtdApiDb>> getDataBaseListByAttr(@RequestBody @Validated MtdApiAttrParams mtdApiAttrParams){
        return DefaultCommonResult.success(ResultCodeEnum.OK,mtdSearchService.getDataBaseListByAttr(mtdApiAttrParams));
    }

}
