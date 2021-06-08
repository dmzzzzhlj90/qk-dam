package com.qk.dm.datastandards.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.datastandards.entity.DsdTerm;
import com.qk.dm.datastandards.service.DataStandardBasicInfoService;
import com.qk.dm.datastandards.vo.DsdBasicinfoVO;
import com.qk.dm.datastandards.vo.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
     *
     * @Param: page, size
     * @return: com.qk.dam.commons.http.result.DefaultCommonResult<org.springframework.data.domain.Page < com.qk.dm.datastandards.entity.DsdTerm>>
     **/
    @GetMapping("/query")
    public DefaultCommonResult<Page<DsdTerm>> getDsdBasicInfo(@RequestBody Pagination pagination) {
        return new DefaultCommonResult(ResultCodeEnum.OK, dataStandardBasicInfoService.getDsdBasicInfo(pagination));
    }

    /**
     * 新增标准信息
     *
     * @Param: dsdBasicinfoVO
     * @return: com.qk.commons.http.result.DefaultCommonResult
     **/
    @PostMapping("/add")
    public DefaultCommonResult addDsdBasicinfo(@RequestBody DsdBasicinfoVO dsdBasicinfoVO) {
        dataStandardBasicInfoService.addDsdBasicinfo(dsdBasicinfoVO);
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }

    /**
     * 编辑标准信息
     *
     * @Param: dsdBasicinfoVO
     * @return: com.qk.commons.http.result.DefaultCommonResult
     **/
    @PutMapping("/update")
    public DefaultCommonResult updateDsdTerm(@RequestBody DsdBasicinfoVO dsdBasicinfoVO) {
        dataStandardBasicInfoService.updateDsdBasicinfo(dsdBasicinfoVO);
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }

    /**
     * 删除标准信息
     *
     * @Param: id
     * @return: com.qk.commons.http.result.DefaultCommonResult
     **/
    @DeleteMapping("/delete/{id}")
    public DefaultCommonResult deleteDsdTerm(@PathVariable("id") Integer id) {
        dataStandardBasicInfoService.deleteDsdBasicinfo(id);
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }

}

