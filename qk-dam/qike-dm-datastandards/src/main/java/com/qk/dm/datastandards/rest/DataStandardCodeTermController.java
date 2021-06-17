package com.qk.dm.datastandards.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.datastandards.service.DataStandardCodeTermService;
import com.qk.dm.datastandards.vo.DsdCodeTermVO;
import com.qk.dm.datastandards.vo.PageResultVO;
import com.qk.dm.datastandards.vo.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 数据标准码表术语接口
 *
 * @author wjq
 * @date 20210604
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/code/term")
public class DataStandardCodeTermController {
    private final DataStandardCodeTermService dataStandardCodeTermService;

    @Autowired
    public DataStandardCodeTermController(DataStandardCodeTermService dataStandardCodeTermService) {
        this.dataStandardCodeTermService = dataStandardCodeTermService;
    }


    /**
     * 查询数据标准码表术语信息
     *
     * @param: pagination分页查询参数对象: page,size,sortStr
     * @return: 返回数据标准码表术语列表信息
     **/
    @GetMapping("/query")
    public DefaultCommonResult<PageResultVO<DsdCodeTermVO>> getDsdCodeTerm(@RequestBody Pagination pagination) {
        return new DefaultCommonResult(ResultCodeEnum.OK, dataStandardCodeTermService.getDsdCodeTerm(pagination));
    }

    /**
     * 新增数据标准码表术语信息
     *
     * @param: dsdCodeTermVO 数据标准码表术语VO
     * @return: DefaultCommonResult
     **/
    @PostMapping("/add")
    public DefaultCommonResult addDsdCodeTerm(@RequestBody @Validated DsdCodeTermVO dsdCodeTermVO) {
        dataStandardCodeTermService.addDsdCodeTerm(dsdCodeTermVO);
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }

    /**
     * 编辑数据标准码表术语信息
     *
     * @param: dsdCodeTermVO 数据标准码表术语VO
     * @return: DefaultCommonResult
     **/
    @PutMapping("/update")
    public DefaultCommonResult updateDsdCodeTerm(@RequestBody @Validated DsdCodeTermVO dsdCodeTermVO) {
        dataStandardCodeTermService.updateDsdCodeTerm(dsdCodeTermVO);
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }

    /**
     * 删除数据标准码表术语信息
     *
     * @param: id
     * @return: DefaultCommonResult
     **/
    @DeleteMapping("/delete/{id}")
    public DefaultCommonResult deleteDsdCodeTerm(@PathVariable("id") Integer id) {
        dataStandardCodeTermService.deleteDsdCodeTerm(id);
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }

}

