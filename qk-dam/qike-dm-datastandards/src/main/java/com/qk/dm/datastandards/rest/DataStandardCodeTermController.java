package com.qk.dm.datastandards.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.datastandards.entity.DsdCodeTerm;
import com.qk.dm.datastandards.entity.DsdTerm;
import com.qk.dm.datastandards.service.DataStandardCodeTermService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * @author wjq
 * @date 20210604
 * @since 1.0.0
 * 数据标准__标准代码术语接口入口
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
     * @Param: dsdTerm
     * @return: com.qk.commons.http.result.DefaultCommonResult
     **/
    @PostMapping("/query")
    public DefaultCommonResult<Page<DsdTerm>> getDsdCodeTerm(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        return new DefaultCommonResult(ResultCodeEnum.OK, dataStandardCodeTermService.getDsdCodeTerm(page, size));
    }

    /**
     * @Param: dsdTerm
     * @return: com.qk.commons.http.result.DefaultCommonResult
     **/
    @PostMapping("/add")
    public DefaultCommonResult addDsdCodeTerm(@RequestBody DsdCodeTerm dsdCodeTerm) {
        dataStandardCodeTermService.addDsdCodeTerm(dsdCodeTerm);
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }

    /**
     * @Param: dsdTerm
     * @return: com.qk.commons.http.result.DefaultCommonResult
     **/
    @PostMapping("/update")
    public DefaultCommonResult updateDsdCodeTerm(@RequestBody DsdCodeTerm dsdCodeTerm) {
        dataStandardCodeTermService.updateDsdCodeTerm(dsdCodeTerm);
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }

    /**
     * @Param: id
     * @return: com.qk.commons.http.result.DefaultCommonResult
     **/
    @DeleteMapping("/delete")
    public DefaultCommonResult deleteDsdCodeTerm(@RequestParam("id") Integer id) {
        dataStandardCodeTermService.deleteDsdCodeTerm(id);
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }

}

