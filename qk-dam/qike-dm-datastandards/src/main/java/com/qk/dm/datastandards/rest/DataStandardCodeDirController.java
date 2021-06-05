package com.qk.dm.datastandards.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.datastandards.entity.DsdCodeDir;
import com.qk.dm.datastandards.service.DataStandardCodeDirService;
import com.qk.dm.datastandards.vo.DataStandardCodeTreeVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据标准码表目录接口入口
 *
 * @author wjq
 * @date 20210604
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/dsd/code/dir")
public class DataStandardCodeDirController {
    private final DataStandardCodeDirService dataStandardCodeDirService;

    @Autowired
    public DataStandardCodeDirController(DataStandardCodeDirService dataStandardCodeDirService) {
        this.dataStandardCodeDirService = dataStandardCodeDirService;

    }

    /**
     * 获取数据标准码表分类目录树
     *
     * @return: com.qk.commons.http.result.DefaultCommonResult
     **/
    @GetMapping("/tree")
    public DefaultCommonResult<List<DataStandardCodeTreeVO>> getDsdDirTree() {
        return new DefaultCommonResult(ResultCodeEnum.OK, dataStandardCodeDirService.getTree());
    }

    /**
     * 新增码表分类目录
     *
     * @Param: dsdDir
     * @return: com.qk.commons.http.result.DefaultCommonResult
     **/
    @PostMapping("/add")
    public DefaultCommonResult addDsdDir(@RequestBody DsdCodeDir dsdCodeDir) {
        dataStandardCodeDirService.addDsdDir(dsdCodeDir);
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }

    /**
     * 编辑码表分类目录
     *
     * @Param: dsdDir
     * @return: com.qk.commons.http.result.DefaultCommonResult
     **/
    @PostMapping("/update")
    public DefaultCommonResult updateDsdDir(@RequestBody DsdCodeDir dsdCodeDir) {
        dataStandardCodeDirService.updateDsdDir(dsdCodeDir);
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }

    /**
     * 码表目录单叶子节点删除方式
     *
     * @Param: id
     * @return: com.qk.commons.http.result.DefaultCommonResult
     **/
    @DeleteMapping("/delete")
    public DefaultCommonResult deleteDsdDir(@RequestParam("id") Integer id) {
        dataStandardCodeDirService.deleteDsdDir(id);
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }

    /**
     * 码表目录支持根节点删除关联删除叶子节点方式
     *
     * @Param: id
     * @return: com.qk.commons.http.result.DefaultCommonResult
     **/
    @DeleteMapping("/delete/root")
    public DefaultCommonResult deleteDsdDirRoot(@RequestParam("id") Integer id) {
        dataStandardCodeDirService.deleteDsdDirRoot(id);
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }
}
