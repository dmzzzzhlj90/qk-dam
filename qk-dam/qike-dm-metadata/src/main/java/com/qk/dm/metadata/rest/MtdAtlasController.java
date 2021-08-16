package com.qk.dm.metadata.rest;


import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.metadata.service.AtlasMetaDataService;
import com.qk.dm.metadata.vo.MtdAtlasBaseDetailVO;
import com.qk.dm.metadata.vo.MtdAtlasBaseVO;
import com.qk.dm.metadata.vo.MtdAtlasEntityTypeVO;
import com.qk.dm.metadata.vo.MtdAtlasParamsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 元数据查询相关接口（元数据列表查询、元数据详情、元数据类型等接口）
 * @author wangzp
 * @date 2021/8/06 15:05
 * @since 1.0.0
 */
@RestController
@RequestMapping("/metadata")
public class MtdAtlasController {

    private final AtlasMetaDataService atlasMetaDataService;

    @Autowired
    public MtdAtlasController(AtlasMetaDataService atlasMetaDataService){
        this.atlasMetaDataService = atlasMetaDataService;
    }

    /**
     * 获取所有的基础类型
     * @return DefaultCommonResult<List<MtdAtlasEntityTypeVO>>
     */
    @GetMapping("/allEntityType")
    public DefaultCommonResult<List<MtdAtlasEntityTypeVO>> getAllEntityType(){
        List<MtdAtlasEntityTypeVO> mtdAtlasEntityTypeVOList = atlasMetaDataService.getAllEntityType();
        return DefaultCommonResult.success(ResultCodeEnum.OK,mtdAtlasEntityTypeVOList);
    }

    /**
     * 查询元数据列表
     * @param mtdAtlasParamsVO
     * @return DefaultCommonResult<List<MtdAtlasBaseVO>>
     */
    @PostMapping("/List")
    public DefaultCommonResult<List<MtdAtlasBaseVO>> searchList(@RequestBody @Validated MtdAtlasParamsVO mtdAtlasParamsVO){
        List<MtdAtlasBaseVO> atlasBaseMainDataVOList = atlasMetaDataService.searchList(mtdAtlasParamsVO.getQuery(), mtdAtlasParamsVO.getTypeName(), mtdAtlasParamsVO.getClassification(), mtdAtlasParamsVO.getLimit(), mtdAtlasParamsVO.getOffse());
        return DefaultCommonResult.success(ResultCodeEnum.OK,atlasBaseMainDataVOList);
    }

    /**
     * 根据guid获取元数据详情
     * @param guid 元数据唯一id
     * @return DefaultCommonResult<AtlasBaseMainDataDetailVO>
     */
    @GetMapping("/entity/{guid}")
    public DefaultCommonResult<MtdAtlasBaseDetailVO> getEntityByGuid(@PathVariable("guid") String guid){
        MtdAtlasBaseDetailVO atlasBaseMainDataDetailVO = atlasMetaDataService.getEntityByGuid(guid);
        return DefaultCommonResult.success(ResultCodeEnum.OK,atlasBaseMainDataDetailVO);
    }

    /**
     * 根据guid删除元数据,多个guid 使用英文逗号分割
     * @param guids
     * @return
     */
    @DeleteMapping("/entity/{guids}")
    public DefaultCommonResult deleteEntitiesByGuids(@PathVariable("guids") String guids){
        atlasMetaDataService.deleteEntitiesByGuids(guids);
        return DefaultCommonResult.success(ResultCodeEnum.OK,null);
    }
}
