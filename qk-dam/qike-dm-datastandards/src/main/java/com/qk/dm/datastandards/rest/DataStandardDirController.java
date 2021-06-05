package com.qk.dm.datastandards.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.datastandards.entity.DsdDir;
import com.qk.dm.datastandards.service.DataStandardDirService;
import com.qk.dm.datastandards.vo.DataStandardTreeVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据标准目录接口入口
 *
 * @author wjq
 * @date 20210603
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/dsd/dir")
public class DataStandardDirController {
    private final DataStandardDirService dataStandardDirService;

    @Autowired
    public DataStandardDirController(DataStandardDirService dataStandardDirService) {
        this.dataStandardDirService = dataStandardDirService;

    }

    /**
     * 获取数据标准分类目录树
     *
     * @return: com.qk.dam.commons.http.result.DefaultCommonResult<java.util.List < com.qk.dm.datastandards.vo.DataStandardTreeVO>>
     **/
    @GetMapping("/tree")
    public DefaultCommonResult<List<DataStandardTreeVO>> getDsdDirTree() {
        return new DefaultCommonResult(ResultCodeEnum.OK, dataStandardDirService.getTree());
    }

    /**
     * 新增数据标准分类目录
     *
     * @Param: dsdDir
     * @return: com.qk.commons.http.result.DefaultCommonResult
     **/
    @PostMapping("/add")
    public DefaultCommonResult addDsdDir(@RequestBody DsdDir dsdDir) {
        dataStandardDirService.addDsdDir(dsdDir);
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }

    /**
     * 编辑数据标准分类目录
     *
     * @Param: dsdDir
     * @return: com.qk.commons.http.result.DefaultCommonResult
     **/
    @PostMapping("/update")
    public DefaultCommonResult updateDsdDir(@RequestBody DsdDir dsdDir) {
        dataStandardDirService.updateDsdDir(dsdDir);
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }

    /**
     * 标准目录单子节点删除方式
     *
     * @Param: id
     * @return: com.qk.commons.http.result.DefaultCommonResult
     **/
    @DeleteMapping("/delete")
    public DefaultCommonResult deleteDsdDir(@RequestParam("id") Integer id) {
        dataStandardDirService.deleteDsdDir(id);
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }

    /**
     * 标准目录支持根节点关联删除子节点方式
     *
     * @Param: id
     * @return: com.qk.commons.http.result.DefaultCommonResult
     **/
    @DeleteMapping("/delete/root")
    public DefaultCommonResult deleteDsdDirRoot(@RequestParam("id") Integer id) {
        dataStandardDirService.deleteDsdDirRoot(id);
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }
}

