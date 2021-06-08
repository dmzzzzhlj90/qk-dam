package com.qk.dm.datastandards.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.datastandards.service.DataStandardTermService;
import com.qk.dm.datastandards.vo.DsdTermVO;
import com.qk.dm.datastandards.vo.PageResultVO;
import com.qk.dm.datastandards.vo.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 数据标准业务术语接口
 *
 * @author wjq
 * @date 20210604
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/dsd/term")
public class DataStandardTermController {
    private final DataStandardTermService dataStandardTermService;

    @Autowired
    public DataStandardTermController(DataStandardTermService dataStandardTermService) {
        this.dataStandardTermService = dataStandardTermService;
    }

    /**
     * 查询业务术语信息
     *
     * @Param: page, size
     * @return: com.qk.dam.commons.http.result.DefaultCommonResult<org.springframework.data.domain.Page < com.qk.dm.datastandards.entity.DsdTerm>>
     **/
    @GetMapping("/query")
    public DefaultCommonResult<PageResultVO<DsdTermVO>> getDsdTerm(@RequestBody Pagination pagination) {
        return new DefaultCommonResult(ResultCodeEnum.OK, dataStandardTermService.getDsdTerm(pagination));
    }

    /**
     * 新增业务术语信息
     *
     * @Param: dsdTermVO
     * @return: com.qk.commons.http.result.DefaultCommonResult
     **/
    @PostMapping("/add")
    public DefaultCommonResult addDsdTerm(@RequestBody @Validated DsdTermVO dsdTermVO) {
        dataStandardTermService.addDsdTerm(dsdTermVO);
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }

    /**
     * 编辑业务术语信息
     *
     * @Param: dsdTermVO
     * @return: com.qk.commons.http.result.DefaultCommonResult
     **/
    @PutMapping("/update")
    public DefaultCommonResult updateDsdTerm(@RequestBody @Validated DsdTermVO dsdTermVO) {
        dataStandardTermService.updateDsdTerm(dsdTermVO);
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }

    /**
     * 删除业务术语信息
     *
     * @Param: id
     * @return: com.qk.commons.http.result.DefaultCommonResult
     **/
    @DeleteMapping("/delete/{id}")
    public DefaultCommonResult deleteDsdTerm(@PathVariable("id") Integer id) {
        dataStandardTermService.deleteDsdTerm(id);
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }

}

