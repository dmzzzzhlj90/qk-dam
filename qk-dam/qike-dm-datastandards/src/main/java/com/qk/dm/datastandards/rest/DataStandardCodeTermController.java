package com.qk.dm.datastandards.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.datastandards.entity.DsdCodeTerm;
import com.qk.dm.datastandards.entity.DsdTerm;
import com.qk.dm.datastandards.service.DataStandardCodeTermService;
import com.qk.dm.datastandards.vo.DsdCodeTermVO;
import com.qk.dm.datastandards.vo.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * 数据标准标准代码术语接口
 *
 * @author wjq
 * @date 20210604
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/dsd/code/term")
public class DataStandardCodeTermController {
    private final DataStandardCodeTermService dataStandardCodeTermService;

    @Autowired
    public DataStandardCodeTermController(DataStandardCodeTermService dataStandardCodeTermService) {
        this.dataStandardCodeTermService = dataStandardCodeTermService;
    }


    /**
     * 查询业务术语信息
     *
     * @Param: page, size
     * @return: com.qk.dam.commons.http.result.DefaultCommonResult<org.springframework.data.domain.Page < com.qk.dm.datastandards.entity.DsdTerm>>
     **/
    @GetMapping("/query")
    public DefaultCommonResult<Page<DsdTerm>> getDsdCodeTerm(@RequestBody Pagination pagination) {
        return new DefaultCommonResult(ResultCodeEnum.OK, dataStandardCodeTermService.getDsdCodeTerm(pagination));
    }

    /**
     * 新增业务术语信息
     *
     * @Param: dsdTerm
     * @return: com.qk.commons.http.result.DefaultCommonResult
     **/
    @PostMapping("/add")
    public DefaultCommonResult addDsdCodeTerm(@RequestBody DsdCodeTermVO dsdCodeTermVO) {
        dataStandardCodeTermService.addDsdCodeTerm(dsdCodeTermVO);
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }

    /**
     * 编辑业务术语信息
     *
     * @Param: dsdTerm
     * @return: com.qk.commons.http.result.DefaultCommonResult
     **/
    @PutMapping("/update")
    public DefaultCommonResult updateDsdCodeTerm(@RequestBody DsdCodeTermVO dsdCodeTermVO) {
        dataStandardCodeTermService.updateDsdCodeTerm(dsdCodeTermVO);
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }

    /**
     * 删除业务术语信息
     *
     * @Param: id
     * @return: com.qk.commons.http.result.DefaultCommonResult
     **/
    @DeleteMapping("/delete/{id}")
    public DefaultCommonResult deleteDsdCodeTerm(@PathVariable("id") Integer id) {
        dataStandardCodeTermService.deleteDsdCodeTerm(id);
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }

}

