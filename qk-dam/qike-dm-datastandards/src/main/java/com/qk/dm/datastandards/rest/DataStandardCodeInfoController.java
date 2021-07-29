package com.qk.dm.datastandards.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.datastandards.service.DataStandardCodeInfoService;
import com.qk.dm.datastandards.vo.DsdCodeInfoExtVO;
import com.qk.dm.datastandards.vo.DsdCodeInfoVO;
import com.qk.dm.datastandards.vo.PageResultVO;
import com.qk.dm.datastandards.vo.params.DsdCodeInfoExtParamsVO;
import com.qk.dm.datastandards.vo.params.DsdCodeInfoParamsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 数据标准__码表管理接口
 *
 * @author wjq
 * @date 20210726
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/code/info")
public class DataStandardCodeInfoController {
    private final DataStandardCodeInfoService dataStandardCodeInfoService;

    @Autowired
    public DataStandardCodeInfoController(DataStandardCodeInfoService dataStandardCodeInfoService) {
        this.dataStandardCodeInfoService = dataStandardCodeInfoService;
    }


    /**
     * 高级筛选_查询码表基础信息列表
     *
     * @param: pagination分页查询参数对象: page,size,sortStr
     * @return: 返回数据标码表列表信息
     */
    @PostMapping(value = "/basic/query")
    public DefaultCommonResult<PageResultVO<DsdCodeInfoVO>> getDsdCodeInfo(@RequestBody DsdCodeInfoParamsVO dsdCodeInfoParamsVO) {
        return new DefaultCommonResult(ResultCodeEnum.OK, dataStandardCodeInfoService.getDsdCodeInfo(dsdCodeInfoParamsVO));
    }

    /**
     * 新增码表基础信息
     *
     * @param: dsdCodeInfoVO 码表信息VO
     * @return: DefaultCommonResult
     */
    @PostMapping("/basic/add")
    public DefaultCommonResult addDsdCodeInfo(@RequestBody @Validated DsdCodeInfoVO dsdCodeInfoVO) {
        dataStandardCodeInfoService.addDsdCodeInfo(dsdCodeInfoVO);
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }


    /**
     * 根据Id获取码表信息列表详情信息
     *
     * @param: pagination分页查询参数对象: page,size,sortStr
     * @return: 返回数据标码表列表信息
     */
    @GetMapping(value = "/basic/query/by/{id}")
    public DefaultCommonResult<DsdCodeInfoVO> getBasicDsdCodeInfoById(@PathVariable("id") String id) {
        return new DefaultCommonResult(ResultCodeEnum.OK, dataStandardCodeInfoService.getDsdCodeInfoById(Long.valueOf(id).longValue()));
    }

    /**
     * 编辑码表信息
     *
     * @param: dsdCodeInfoVO 码表信息VO
     * @return: DefaultCommonResult
     */
    @PutMapping("/basic/update")
    public DefaultCommonResult modifyDsdCodeInfo(@RequestBody @Validated DsdCodeInfoVO dsdCodeInfoVO) {
        dataStandardCodeInfoService.modifyDsdCodeInfo(dsdCodeInfoVO);
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }


    /**
     * 删除码表信息
     *
     * @param: id
     * @return: DefaultCommonResult
     */
    @DeleteMapping("/basic/delete/{id}")
    public DefaultCommonResult deleteDsdCodeInfo(@PathVariable("id") Integer id) {
        dataStandardCodeInfoService.deleteDsdCodeInfo(Long.valueOf(id).longValue());
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }

    /**
     * 批量删除码表信息
     *
     * @param: ids
     * @return: DefaultCommonResult
     */
    @DeleteMapping("/basic/delete/bulk/{ids}")
    public DefaultCommonResult deleteBulkDsdCodeInfo(@PathVariable("ids") String ids) {
        dataStandardCodeInfoService.deleteBulkDsdCodeInfo(ids);
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }

    /**
     * 数据类型下拉列表
     *
     * @return: DefaultCommonResult
     */
    @GetMapping("/data/types")
    public DefaultCommonResult<List<Map<String, String>>> getDataTypes() {
        return new DefaultCommonResult(ResultCodeEnum.OK,dataStandardCodeInfoService.getDataTypes());
    }

    /**
     * 高级筛选_查询码表数值以及扩展信息列表
     *
     * @param: pagination分页查询参数对象: page,size,sortStr
     * @return: 返回数据标码表列表信息
     */
    @PostMapping(value = "/ext/query")
    public DefaultCommonResult<Map<String, Object>> getDsdCodeInfoExt(@RequestBody DsdCodeInfoExtParamsVO dsdCodeInfoExtParamsVO) {
        return new DefaultCommonResult(ResultCodeEnum.OK, dataStandardCodeInfoService.getDsdCodeInfoExt(dsdCodeInfoExtParamsVO));
    }

    /**
     * 新增码表数值信息
     *
     * @param: dsdCodeInfoVO 码表信息VO
     * @return: DefaultCommonResult
     */
    @PostMapping("/ext/add")
    public DefaultCommonResult addDsdCodeInfoExt(@RequestBody @Validated DsdCodeInfoExtVO dsdCodeInfoExtVO) {
        dataStandardCodeInfoService.addDsdCodeInfoExt(dsdCodeInfoExtVO);
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }


    /**
     * 根据Id获取码表数值列表详情信息
     *
     * @param: pagination分页查询参数对象: page,size,sortStr
     * @return: 返回数据标码表列表信息
     */
    @GetMapping(value = "/ext/query/by/{id}")
    public DefaultCommonResult<DsdCodeInfoExtVO> getBasicDsdCodeInfoExtById(@PathVariable("id") String id) {
        return new DefaultCommonResult(ResultCodeEnum.OK, dataStandardCodeInfoService.getBasicDsdCodeInfoExtById(Long.valueOf(id).longValue()));
    }

    /**
     * 编辑码表数值信息
     *
     * @param: dsdCodeInfoVO 码表信息VO
     * @return: DefaultCommonResult
     */
    @PutMapping("/ext/update")
    public DefaultCommonResult modifyDsdCodeInfoExt(@RequestBody @Validated DsdCodeInfoExtVO dsdCodeInfoExtVO) {
        dataStandardCodeInfoService.modifyDsdCodeInfoExt(dsdCodeInfoExtVO);
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }


    /**
     * 删除码表数值信息
     *
     * @param: id
     * @return: DefaultCommonResult
     */
    @DeleteMapping("/ext/delete/{id}")
    public DefaultCommonResult deleteDsdCodeInfoExt(@PathVariable("id") Integer id) {
        dataStandardCodeInfoService.deleteDsdCodeInfoExt(Long.valueOf(id).longValue());
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }

    /**
     * 批量删除码表数值信息
     *
     * @param: ids
     * @return: DefaultCommonResult
     */
    @DeleteMapping("/ext/bulk/delete/{ids}")
    public DefaultCommonResult deleteBulkDsdCodeInfoExt(@PathVariable("ids") String ids) {
        dataStandardCodeInfoService.deleteBulkDsdCodeInfoExt(ids);
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }
}
