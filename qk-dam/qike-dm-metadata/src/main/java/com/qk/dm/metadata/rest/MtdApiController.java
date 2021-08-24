package com.qk.dm.metadata.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.metadata.service.MtdApiService;
import com.qk.dm.metadata.vo.MtdApiParamsVO;
import com.qk.dm.metadata.vo.MtdApiVO;
import com.qk.dm.metadata.vo.MtdAtlasEntityTypeVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 其它服务调用的元数据接口（数据服务）
 *
 * @author wangzp
 * @date 2021/8/23 19:28
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api")
public class MtdApiController {

    private final MtdApiService mtdApiService;

    public MtdApiController(MtdApiService mtdApiService) {
        this.mtdApiService = mtdApiService;
    }

    /**
     * 获取所有的类型
     * @return DefaultCommonResult<List<MtdAtlasEntityTypeVO>>
     */
    @GetMapping("/all/entity/type")
    public DefaultCommonResult<List<MtdAtlasEntityTypeVO>> getAllEntityType(){
        List<MtdAtlasEntityTypeVO> entityTypeList = mtdApiService.getAllEntityType();
        return DefaultCommonResult.success(ResultCodeEnum.OK,entityTypeList);
    }

    /**
     * 获取元数据详情信息
     * @param mtdApiParamsVO
     * @return DefaultCommonResult<MtdApiVO>
     */
    @PostMapping("/mtd/detail")
    public DefaultCommonResult<MtdApiVO> mtdDetail(@RequestBody @Validated MtdApiParamsVO mtdApiParamsVO){
        MtdApiVO mtdApiVO = mtdApiService.mtdDetail(mtdApiParamsVO.getTypeName(),mtdApiParamsVO.getDbName(),mtdApiParamsVO.getTableName());
        return DefaultCommonResult.success(ResultCodeEnum.OK,mtdApiVO);
    }


}
