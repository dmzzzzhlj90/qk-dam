package com.qk.dm.metadata.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.metedata.entity.MtdApi;
import com.qk.dam.metedata.entity.MtdApiParams;
import com.qk.dm.metadata.service.MtdApiService;
import com.qk.dam.metedata.entity.MtdAtlasEntityType;
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
    public DefaultCommonResult<List<MtdAtlasEntityType>> getAllEntityType(){
        List<MtdAtlasEntityType> entityTypeList = mtdApiService.getAllEntityType();
        return DefaultCommonResult.success(ResultCodeEnum.OK,entityTypeList);
    }

    /**
     * 获取元数据详情信息
     * @param mtdApiParams
     * @return DefaultCommonResult<MtdApiVO>
     */
    @PostMapping("/mtd/detail")
    public DefaultCommonResult<MtdApi> mtdDetail(@RequestBody @Validated MtdApiParams mtdApiParams){
        MtdApi mtdApi = mtdApiService.mtdDetail(mtdApiParams.getTypeName(),mtdApiParams.getDbName(),mtdApiParams.getTableName(),mtdApiParams.getServer());
        return DefaultCommonResult.success(ResultCodeEnum.OK,mtdApi);
    }


}
