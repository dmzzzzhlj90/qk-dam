package com.qk.dm.metadata.rest;


import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.metadata.service.AtlasMetaDataService;
import com.qk.dm.metadata.vo.MtdAtlasBaseDetailVO;
import com.qk.dm.metadata.vo.MtdAtlasBaseVO;
import com.qk.dm.metadata.vo.MtdAtlasEntityTypeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wangzp
 * @date 2021/8/06 15:05
 * @since 1.0.0
 */
@RestController
@RequestMapping("/mtdAtlas")
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
    @GetMapping("/getAllEntityType")
    public DefaultCommonResult<List<MtdAtlasEntityTypeVO>> getAllEntityType(){
        List<MtdAtlasEntityTypeVO> mtdAtlasEntityTypeVOList = atlasMetaDataService.getAllEntityType();
        return DefaultCommonResult.success(ResultCodeEnum.OK,mtdAtlasEntityTypeVOList);
    }

    /**
     * 查询元数据列表
     * @param query  查找关键字
     * @param typeName 类型名称 如mysql_table、hive_table等
     * @param classification 分类名称
     * @param limit 一页多少条
     * @param offse 开始位置
     * @return  DefaultCommonResult<List<AtlasBaseMainDataVO>>
     */
    @GetMapping("/searchList")
    public DefaultCommonResult<List<MtdAtlasBaseVO>> searchList(@PathVariable("query") String query, @PathVariable("typeName") String typeName,
                                                                @PathVariable("classification") String classification,
                                                                @PathVariable("limit") int limit, @PathVariable("offse") int offse){
        List<MtdAtlasBaseVO> atlasBaseMainDataVOList = atlasMetaDataService.searchList(query, typeName, classification, limit, offse);
        return DefaultCommonResult.success(ResultCodeEnum.OK,atlasBaseMainDataVOList);
    }

    /**
     * 根据guid获取元数据详情
     * @param guid 元数据唯一id
     * @return DefaultCommonResult<AtlasBaseMainDataDetailVO>
     */
    @GetMapping("/getEntityByGuid/{guid}")
    public DefaultCommonResult<MtdAtlasBaseDetailVO> getEntityByGuid(@PathVariable("guid") String guid){
        MtdAtlasBaseDetailVO atlasBaseMainDataDetailVO = atlasMetaDataService.getEntityByGuid(guid);
        return DefaultCommonResult.success(ResultCodeEnum.OK,atlasBaseMainDataDetailVO);
    }

}
