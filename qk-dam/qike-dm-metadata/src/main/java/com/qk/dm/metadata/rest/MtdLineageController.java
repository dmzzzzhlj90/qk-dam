package com.qk.dm.metadata.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.metadata.service.MetaDataLineageService;
import com.qk.dm.metadata.vo.MtdLineageParamsVO;
import com.qk.dm.metadata.vo.MtdLineageVO;
import com.qk.dm.metadata.vo.RelationVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 元数据血缘
 * @author wangzp
 * @date 2021/10/18 10:36
 * @since 1.0.0
 */
@RestController
public class MtdLineageController {

    private final MetaDataLineageService metaDataLineageService;

    public MtdLineageController(MetaDataLineageService metaDataLineageService){
        this.metaDataLineageService = metaDataLineageService;
    }

    /**
     * 获取元数据血缘,展示血缘关系图
     * @param mtdLineageParamsVO
     * @return DefaultCommonResult<AtlasLineageInfo>
     */
    @PostMapping("/lineage")
    public DefaultCommonResult<MtdLineageVO> getLineageInfo(@RequestBody @Validated MtdLineageParamsVO mtdLineageParamsVO){
       return DefaultCommonResult.success(ResultCodeEnum.OK,metaDataLineageService.getLineageInfo(mtdLineageParamsVO));
    }

    /**
     * 获取元数据过程 input output
     * @param guid
     * @return DefaultCommonResult<RelationVO>
     */
    @GetMapping("/relation/{guid}")
    public DefaultCommonResult<Map<String,Object>> relationShip(@PathVariable("guid") String guid){
        return DefaultCommonResult.success(ResultCodeEnum.OK,metaDataLineageService.relationShip(guid));
    }


}
