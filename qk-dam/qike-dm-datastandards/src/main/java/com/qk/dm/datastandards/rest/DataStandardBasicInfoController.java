package com.qk.dm.datastandards.rest;

import com.qk.commons.enums.ResultCodeEnum;
import com.qk.commons.http.result.DefaultCommonResult;
import com.qk.dm.datastandards.entity.DsdBasicinfo;
import com.qk.dm.datastandards.entity.DsdTerm;
import com.qk.dm.datastandards.service.DataStandardBasicInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * @author wjq
 * @date 20210604
 * @since 1.0.0
 * 数据标准__标准信息接口入口
 */
@Slf4j
@RestController
@RequestMapping("/dsd/basic/info")
public class DataStandardBasicInfoController {
    private final DataStandardBasicInfoService dataStandardBasicInfoService;

    @Autowired
    public DataStandardBasicInfoController(DataStandardBasicInfoService dataStandardBasicInfoService) {
        this.dataStandardBasicInfoService = dataStandardBasicInfoService;
    }

    /**
     * @Param: dsdTerm
     * @return: com.qk.commons.http.result.DefaultCommonResult
     **/
    @PostMapping("/query")
    public DefaultCommonResult<Page<DsdTerm>> getDsdBasicInfo(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        return new DefaultCommonResult(ResultCodeEnum.OK, dataStandardBasicInfoService.getDsdBasicInfo(page, size));
    }

    /**
     * @Param: dsdTerm
     * @return: com.qk.commons.http.result.DefaultCommonResult
     **/
    @PostMapping("/add")
    public DefaultCommonResult addDsdBasicinfo(@RequestBody DsdBasicinfo dsdBasicinfo) {
        dataStandardBasicInfoService.addDsdBasicinfo(dsdBasicinfo);
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }

    /**
     * @Param: dsdTerm
     * @return: com.qk.commons.http.result.DefaultCommonResult
     **/
    @PostMapping("/update")
    public DefaultCommonResult updateDsdTerm(@RequestBody DsdBasicinfo dsdBasicinfo) {
        dataStandardBasicInfoService.updateDsdBasicinfo(dsdBasicinfo);
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }

    /**
     * @Param: id
     * @return: com.qk.commons.http.result.DefaultCommonResult
     **/
    @DeleteMapping("/delete")
    public DefaultCommonResult deleteDsdTerm(@RequestParam("id") Integer id) {
        dataStandardBasicInfoService.deleteDsdBasicinfo(id);
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }

}

