package com.qk.dm.datastandards.rest;

import com.qk.commons.enums.ResultCodeEnum;
import com.qk.commons.http.result.DefaultCommonResult;
import com.qk.dm.datastandards.entity.DsdDir;
import com.qk.dm.datastandards.service.DataStandardService;
import com.qk.dm.datastandards.vo.DataStandardTreeResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wjq
 * @date 20210603
 * 数据标准接口入口
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/dsd")
public class DataStandardController {
    private final DataStandardService dataStandardService;

    @Autowired
    public DataStandardController(DataStandardService dataStandardService) {
        this.dataStandardService = dataStandardService;

    }

    /**
     * @return: com.qk.commons.http.result.DefaultCommonResult
     * 获取数据标准分类目录树
     **/
    @GetMapping("/tree/dir")
    public DefaultCommonResult<List<DataStandardTreeResp>> getDsdDirTree() {
        return new DefaultCommonResult(ResultCodeEnum.OK, dataStandardService.getTree());
    }

    /**
     * @Param: dsdDir
     * @return: com.qk.commons.http.result.DefaultCommonResult
     *
     **/
    @PostMapping("/add/dir")
    public DefaultCommonResult addDsdDir(@RequestBody DsdDir dsdDir)  {
        dataStandardService.addDsdDir(dsdDir);
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }

    /**
     * @Param: dsdDir
     * @return: com.qk.commons.http.result.DefaultCommonResult
     *
     **/
    @PostMapping("/update/dir")
    public DefaultCommonResult updateDsdDir(@RequestBody DsdDir dsdDir) {
        dataStandardService.updateDsdDir(dsdDir);
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }

    /**
     * @Param: id
     * @return: com.qk.commons.http.result.DefaultCommonResult
     * 单叶子节点删除方式
     **/
    @DeleteMapping("/delete/dir")
    public DefaultCommonResult deleteDsdDir(@RequestParam("id") Integer id) {
        dataStandardService.deleteDsdDir(id);
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }

    /**
     * @Param: id
     * @return: com.qk.commons.http.result.DefaultCommonResult
     *  支持根节点删除关联删除叶子节点方式
     **/
    @DeleteMapping("/delete/dir/root")
    public DefaultCommonResult deleteDsdDirRoot(@RequestParam("id") Integer id) {
        dataStandardService.deleteDsdDirRoot(id);
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }
}

