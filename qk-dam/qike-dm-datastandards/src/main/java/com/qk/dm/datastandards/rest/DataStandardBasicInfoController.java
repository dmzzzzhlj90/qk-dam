package com.qk.dm.datastandards.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.datastandards.service.DataStandardBasicInfoService;
import com.qk.dm.datastandards.vo.DsdBasicinfoVO;
import com.qk.dm.datastandards.vo.PageResultVO;
import com.qk.dm.datastandards.vo.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public DataStandardBasicInfoController(DataStandardBasicInfoService dataStandardBasicInfoService) {
        this.dataStandardBasicInfoService = dataStandardBasicInfoService;
    }

    /**
     * 查询标准信息
     *
     * @param: pagination分页查询参数对象: page,size,sortStr
     * @return: 返回标准列表信息
     **/
    @GetMapping(value = {"/query/{dirDsdId}", "/query"})
    public DefaultCommonResult<PageResultVO<DsdBasicinfoVO>> getDsdBasicInfo(Pagination pagination,
                                                                             @PathVariable(value = "dirDsdId", required = false) String dirDsdId) {
        return new DefaultCommonResult(ResultCodeEnum.OK, dataStandardBasicInfoService.getDsdBasicInfo(pagination, dirDsdId));
    }

    /**
     * 新增标准信息
     *
     * @param: dsdBasicinfoVO 数据标准信息VO
     * @return: DefaultCommonResult
     **/
    @PostMapping("/add")
    public DefaultCommonResult addDsdBasicinfo(@RequestBody @Validated DsdBasicinfoVO dsdBasicinfoVO) {
        dataStandardBasicInfoService.addDsdBasicinfo(dsdBasicinfoVO);
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }

    /**
     * 编辑标准信息
     *
     * @param: dsdBasicinfoVO 数据标准信息VO
     * @return: DefaultCommonResult
     **/
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
     **/
    @DeleteMapping("/delete/{id}")
    public DefaultCommonResult deleteDsdTerm(@PathVariable("id") Integer id) {
        dataStandardBasicInfoService.deleteDsdBasicinfo(id);
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }

}

