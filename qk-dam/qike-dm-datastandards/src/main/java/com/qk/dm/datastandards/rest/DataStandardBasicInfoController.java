package com.qk.dm.datastandards.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.datastandards.service.DataStandardBasicInfoService;
import com.qk.dm.datastandards.vo.DsdBasicinfoVO;
import com.qk.dm.datastandards.vo.PageResultVO;
import com.qk.dm.datastandards.vo.params.DsdBasicinfoParamsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据标准标准信息接口
 *
 * @author wjq
 * @date 20210604
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/basic/info")
public class DataStandardBasicInfoController {
    private final DataStandardBasicInfoService dataStandardBasicInfoService;

    @Autowired
    public DataStandardBasicInfoController(
            DataStandardBasicInfoService dataStandardBasicInfoService) {
        this.dataStandardBasicInfoService = dataStandardBasicInfoService;
    }

    /**
     * 统一查询标准信息入口
     *
     * @param: dsdBasicinfoParamsVO:查询对象
     * @return: 返回标准列表信息
     */
    @PostMapping(value = "/query")
    public DefaultCommonResult<PageResultVO<DsdBasicinfoVO>> getDsdBasicInfoByDsdLevelId(@RequestBody DsdBasicinfoParamsVO basicinfoParamsVO) {
        return new DefaultCommonResult(ResultCodeEnum.OK, dataStandardBasicInfoService.getDsdBasicInfo(basicinfoParamsVO));
    }

    /**
     * 新增标准信息
     *
     * @param: dsdBasicinfoVO 数据标准信息VO
     * @return: DefaultCommonResult
     */
    @PostMapping("/add")
    public DefaultCommonResult addDsdBasicinfo(
            @RequestBody @Validated DsdBasicinfoVO dsdBasicinfoVO) {
        dataStandardBasicInfoService.addDsdBasicinfo(dsdBasicinfoVO);
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }

    /**
     * 编辑标准信息
     *
     * @param: dsdBasicinfoVO 数据标准信息VO
     * @return: DefaultCommonResult
     */
    @PutMapping("/update")
    public DefaultCommonResult updateDsdTerm(@RequestBody @Validated DsdBasicinfoVO dsdBasicinfoVO) {
        dataStandardBasicInfoService.updateDsdBasicinfo(dsdBasicinfoVO);
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }

    /**
     * 删除标准信息
     *
     * @param: id
     * @return: DefaultCommonResult
     */
    @DeleteMapping("/delete/{id}")
    public DefaultCommonResult deleteDsdTerm(@PathVariable("id") Integer id) {
        dataStandardBasicInfoService.deleteDsdBasicinfo(id);
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }

    /**
     * 根据数据类型返回对应数据容量列表
     *
     * @param: dsdBasicinfoParamsVO:查询对象
     * @return: 返回标准列表信息
     */
    @GetMapping(value = "/query/dataCapacity/by/dataType/{dataType}")
    public DefaultCommonResult<List<String>> getDataCapacityByDataType(
            @PathVariable(value = "dataType") String dataType) {
        return new DefaultCommonResult(ResultCodeEnum.OK, dataStandardBasicInfoService.getDataCapacityByDataType(dataType));
    }
}
