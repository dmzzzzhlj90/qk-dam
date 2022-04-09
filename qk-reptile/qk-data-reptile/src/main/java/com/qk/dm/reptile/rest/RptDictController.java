package com.qk.dm.reptile.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.reptile.params.vo.RptDictVO;
import com.qk.dm.reptile.service.RptDictService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 字典信息（省市区）
 * @author wangzp
 * @date 2021/12/24 10:28
 * @since 1.0.0
 */
@RestController
@RequestMapping("/dict")
public class RptDictController {

    private final RptDictService rptDictService;

    public RptDictController(RptDictService rptDictService){
        this.rptDictService = rptDictService;
    }

    /**
     * 获取省市区级联列表
     * @param pid 父id,初次请求为0
     * @return DefaultCommonResult<List<RptDictVO>>
     */
    @GetMapping("/list/{pid}")
    public DefaultCommonResult<List<RptDictVO>> getDictList(@PathVariable("pid") Long pid){
        return DefaultCommonResult.success(ResultCodeEnum.OK,rptDictService.getDictList(pid));
    }
}
