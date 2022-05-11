package com.qk.dm.dataservice.controller;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.dataservice.service.DasDataQueryInfoService;
import com.qk.dm.dataservice.vo.DataQueryInfoVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 数据查询管理
 *
 * @author zhudaoming
 * @since 1.5.0
 */
@RestController
@RequestMapping("/data/query/info")
public class DataQueryInfoController {
    private final DasDataQueryInfoService dasDataQueryInfoService;

    public DataQueryInfoController(DasDataQueryInfoService dasDataQueryInfoService) {
        this.dasDataQueryInfoService = dasDataQueryInfoService;
    }

    /**
     * 查询所有新建API 高级SQL配置信息
     *
     * @return DefaultCommonResult<List < DataQueryInfoVO>>
     */
    @PostMapping("/all")
    public DefaultCommonResult<List<DataQueryInfoVO>> dataQueryInfo() {
        return DefaultCommonResult.success(ResultCodeEnum.OK,
                dasDataQueryInfoService.dataQueryInfo());
    }

    /**
     * 根据最近修改时间查询新建API 高级SQL配置信息
     *
     * @param time
     * @return DefaultCommonResult<List < DataQueryInfoVO>>
     */
    @PostMapping("/last/{time}")
    public DefaultCommonResult<List<DataQueryInfoVO>> dataQueryInfoLast(@PathVariable Long time) {
        return DefaultCommonResult.success(ResultCodeEnum.OK,
                dasDataQueryInfoService.dataQueryInfoLast(time));
    }

    /**
     * 新增
     *
     * @param dataQueryInfoVO
     * @return DefaultCommonResult
     */
    @PostMapping("")
    public DefaultCommonResult insert(@RequestBody @Validated DataQueryInfoVO dataQueryInfoVO) {
        dasDataQueryInfoService.insert(dataQueryInfoVO);
        return DefaultCommonResult.success();
    }

    /**
     * 编辑
     *
     * @param dataQueryInfoVO
     * @return DefaultCommonResult
     */
    @PutMapping("")
    public DefaultCommonResult update(@RequestBody @Validated DataQueryInfoVO dataQueryInfoVO) {
        dasDataQueryInfoService.update(dataQueryInfoVO);
        return DefaultCommonResult.success();
    }

    /**
     * 根据SQL生成返回参数
     *
     * @return DefaultCommonResult
     */
    @GetMapping("/generate/response/params")
    public DefaultCommonResult<Object> generateResponseParam(@RequestParam("sqlPara") String sqlPara) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dasDataQueryInfoService.generateResponseParam(sqlPara));
    }

    /**
     * 参数设置: 表头信息
     *
     * @return DefaultCommonResult
     */
    @GetMapping("/param/header/info")
    public DefaultCommonResult<LinkedList<Map<String, Object>>> paramHeaderInfo() {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dasDataQueryInfoService.paramHeaderInfo());
    }

    /**
     * 返回参数: 表头信息
     *
     * @return DefaultCommonResult
     */
    @GetMapping("/response/param/header/infos")
    public DefaultCommonResult<LinkedList<Map<String, Object>>> responseParamHeaderInfo() {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dasDataQueryInfoService.responseParamHeaderInfo());
    }

    /**
     * 排序参数: 表头信息
     *
     * @return DefaultCommonResult
     */
    @GetMapping("/order/param/header/info")
    public DefaultCommonResult<LinkedList<Map<String, Object>>> orderParamHeaderInfo() {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dasDataQueryInfoService.orderParamHeaderInfo());
    }

    /**
     * 是否开启分页
     *
     * @return DefaultCommonResult
     */
    @GetMapping("/page/flags")
    public DefaultCommonResult<Map<Boolean, String>> pageFlags() {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dasDataQueryInfoService.pageFlags());
    }

    /**
     * 是否开启缓存
     *
     * @return DefaultCommonResult
     */
    @GetMapping("/cache/levels")
    public DefaultCommonResult<Map<Integer, String>> cacheLevels() {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dasDataQueryInfoService.cacheLevels());
    }

}
