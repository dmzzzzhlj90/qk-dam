package com.qk.dm.datastandards.rest;

import com.qk.commons.enums.ResultCodeEnum;
import com.qk.commons.http.result.DefaultCommonResult;
import com.qk.dm.datastandards.entity.DsdTerm;
import com.qk.dm.datastandards.service.DataStandardTermService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * @author wjq
 * @date 20210604
 * @since 1.0.0
 * 数据标准__业务术语接口入口
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
     * @Param: dsdTerm
     * @return: com.qk.commons.http.result.DefaultCommonResult
     **/
    @PostMapping("/query")
    public DefaultCommonResult<Page<DsdTerm>> getDsdTerm(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        return new DefaultCommonResult(ResultCodeEnum.OK, dataStandardTermService.getDsdTerm(page, size));
    }

    /**
     * @Param: dsdTerm
     * @return: com.qk.commons.http.result.DefaultCommonResult
     **/
    @PostMapping("/add")
    public DefaultCommonResult addDsdTerm(@RequestBody DsdTerm dsdTerm) {
        dataStandardTermService.addDsdTerm(dsdTerm);
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }

    /**
     * @Param: dsdTerm
     * @return: com.qk.commons.http.result.DefaultCommonResult
     **/
    @PostMapping("/update")
    public DefaultCommonResult updateDsdTerm(@RequestBody DsdTerm dsdTerm) {
        dataStandardTermService.updateDsdTerm(dsdTerm);
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }

    /**
     * @Param: id
     * @return: com.qk.commons.http.result.DefaultCommonResult
     **/
    @DeleteMapping("/delete")
    public DefaultCommonResult deleteDsdTerm(@RequestParam("id") Integer id) {
        dataStandardTermService.deleteDsdTerm(id);
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }

}

