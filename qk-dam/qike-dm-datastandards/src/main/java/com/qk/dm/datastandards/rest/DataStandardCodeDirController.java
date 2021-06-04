package com.qk.dm.datastandards.rest;

import com.qk.commons.enums.ResultCodeEnum;
import com.qk.commons.http.result.DefaultCommonResult;
import com.qk.dm.datastandards.entity.DsdCodeDir;
import com.qk.dm.datastandards.entity.DsdDir;
import com.qk.dm.datastandards.service.DataStandardCodeDirService;
import com.qk.dm.datastandards.vo.DataStandardCodeTreeVO;
import com.qk.dm.datastandards.vo.DataStandardTreeVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wjq
 * @date 20210604
 * @since 1.0.0
 * 数据标准__码表目录接口入口
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
     * @return: com.qk.commons.http.result.DefaultCommonResult
     * 获取数据标准__码表分类目录树
     **/
    @GetMapping("/tree")
    public DefaultCommonResult<List<DataStandardCodeTreeVO>> getDsdDirTree() {
        return new DefaultCommonResult(ResultCodeEnum.OK, dataStandardCodeDirService.getTree());
    }

    /**
     * @Param: dsdDir
     * @return: com.qk.commons.http.result.DefaultCommonResult
     *
     **/
    @PostMapping("/add")
    public DefaultCommonResult addDsdDir(@RequestBody DsdCodeDir dsdCodeDir)  {
        dataStandardCodeDirService.addDsdDir(dsdCodeDir);
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }

    /**
     * @Param: dsdDir
     * @return: com.qk.commons.http.result.DefaultCommonResult
     *
     **/
    @PostMapping("/update")
    public DefaultCommonResult updateDsdDir(@RequestBody DsdCodeDir dsdCodeDir) {
        dataStandardCodeDirService.updateDsdDir(dsdCodeDir);
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }

    /**
     * @Param: id
     * @return: com.qk.commons.http.result.DefaultCommonResult
     * 单叶子节点删除方式
     **/
    @DeleteMapping("/delete")
    public DefaultCommonResult deleteDsdDir(@RequestParam("id") Integer id) {
        dataStandardCodeDirService.deleteDsdDir(id);
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }

    /**
     * @Param: id
     * @return: com.qk.commons.http.result.DefaultCommonResult
     *  支持根节点删除关联删除叶子节点方式
     **/
    @DeleteMapping("/delete/root")
    public DefaultCommonResult deleteDsdDirRoot(@RequestParam("id") Integer id) {
        dataStandardCodeDirService.deleteDsdDirRoot(id);
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }
}

