package com.qk.dm.datastandards.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.datastandards.entity.DsdBasicinfo;
import com.qk.dm.datastandards.entity.DsdTerm;
import com.qk.dm.datastandards.service.DataStandardBasicInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
@RequestMapping("/dsd/basic/info")
public class DataStandardBasicInfoController {
    private final DataStandardBasicInfoService dataStandardBasicInfoService;

    @Autowired
    public DataStandardBasicInfoController(DataStandardBasicInfoService dataStandardBasicInfoService) {
        this.dataStandardBasicInfoService = dataStandardBasicInfoService;
    }

    /**
     * 查询标准信息
     * @Param: page, size
     * @return: com.qk.dam.commons.http.result.DefaultCommonResult<org.springframework.data.domain.Page < com.qk.dm.datastandards.entity.DsdTerm>>
     **/
    @PostMapping("/query")
    public DefaultCommonResult<Page<DsdTerm>> getDsdBasicInfo(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        return new DefaultCommonResult(ResultCodeEnum.OK, dataStandardBasicInfoService.getDsdBasicInfo(page, size));
    }

    /**
     * 新增标准信息
     *
     * @Param: dsdBasicinfo
     * @return: com.qk.commons.http.result.DefaultCommonResult
     **/
    @PostMapping("/add")
    public DefaultCommonResult addDsdBasicinfo(@RequestBody DsdBasicinfo dsdBasicinfo) {
        dataStandardBasicInfoService.addDsdBasicinfo(dsdBasicinfo);
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }

    /**
     * 编辑标准信息
     *
     * @Param: dsdBasicinfo
     * @return: com.qk.commons.http.result.DefaultCommonResult
     **/
    @PostMapping("/update")
    public DefaultCommonResult updateDsdTerm(@RequestBody DsdBasicinfo dsdBasicinfo) {
        dataStandardBasicInfoService.updateDsdBasicinfo(dsdBasicinfo);
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }

    /**
     * 删除标准信息
     *
     * @Param: id
     * @return: com.qk.commons.http.result.DefaultCommonResult
     **/
    @DeleteMapping("/delete")
    public DefaultCommonResult deleteDsdTerm(@RequestParam("id") Integer id) {
        dataStandardBasicInfoService.deleteDsdBasicinfo(id);
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }

}

