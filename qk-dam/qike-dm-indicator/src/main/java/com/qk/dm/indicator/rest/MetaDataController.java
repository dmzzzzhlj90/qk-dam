package com.qk.dm.indicator.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.metedata.entity.MtdTableApiParams;
import com.qk.dam.metedata.entity.MtdTables;
import com.qk.dm.indicator.service.MetaDataService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mtd")
public class MetaDataController {
    private final MetaDataService metaDataService;

    public MetaDataController(MetaDataService metaDataService){
        this.metaDataService = metaDataService;
    }
    @PostMapping("/tables")
    public DefaultCommonResult getTables(@RequestBody MtdTableApiParams mtdTableApiParams){
        List<MtdTables> mtdTablesList = metaDataService.getTables(mtdTableApiParams);
        return DefaultCommonResult.success(ResultCodeEnum.OK, mtdTablesList);
    }
    /**
     * 获取表的字段信息
     * @param guid
     * @return
     */
    @GetMapping("/columns/{guid}")
    public DefaultCommonResult<List<Map<String, Object>>> getColumns(@PathVariable("guid") String guid){
        List<Map<String, Object>> columnList = metaDataService.getColumns(guid);
        return DefaultCommonResult.success(ResultCodeEnum.OK,columnList);
    }
}
