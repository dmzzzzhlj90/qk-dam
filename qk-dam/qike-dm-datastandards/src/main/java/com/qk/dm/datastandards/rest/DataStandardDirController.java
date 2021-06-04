package com.qk.dm.datastandards.rest;

import com.qk.commons.enums.ResultCodeEnum;
import com.qk.commons.http.result.DefaultCommonResult;
import com.qk.dm.datastandards.entity.DsdDir;
import com.qk.dm.datastandards.service.DataStandardDirService;
import com.qk.dm.datastandards.vo.DataStandardTreeVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wjq
 * @date 20210603
 * @since 1.0.0
 * 数据标准__目录接口入口
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
     * @return: com.qk.commons.http.result.DefaultCommonResult
     * 获取数据标准分类目录树
     **/
    @GetMapping("/tree")
    public DefaultCommonResult<List<DataStandardTreeVO>> getDsdDirTree() {
        return new DefaultCommonResult(ResultCodeEnum.OK, dataStandardDirService.getTree());
    }

    /**
     * @Param: dsdDir
     * @return: com.qk.commons.http.result.DefaultCommonResult
     *
     **/
    @PostMapping("/add")
    public DefaultCommonResult addDsdDir(@RequestBody DsdDir dsdDir)  {
        dataStandardDirService.addDsdDir(dsdDir);
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }

    /**
     * @Param: dsdDir
     * @return: com.qk.commons.http.result.DefaultCommonResult
     *
     **/
    @PostMapping("/update")
    public DefaultCommonResult updateDsdDir(@RequestBody DsdDir dsdDir) {
        dataStandardDirService.updateDsdDir(dsdDir);
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }

    /**
     * @Param: id
     * @return: com.qk.commons.http.result.DefaultCommonResult
     * 单叶子节点删除方式
     **/
    @DeleteMapping("/delete")
    public DefaultCommonResult deleteDsdDir(@RequestParam("id") Integer id) {
        dataStandardDirService.deleteDsdDir(id);
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }

    /**
     * @Param: id
     * @return: com.qk.commons.http.result.DefaultCommonResult
     *  支持根节点删除关联删除叶子节点方式
     **/
    @DeleteMapping("/delete/root")
    public DefaultCommonResult deleteDsdDirRoot(@RequestParam("id") Integer id) {
        dataStandardDirService.deleteDsdDirRoot(id);
        return new DefaultCommonResult(ResultCodeEnum.OK);
    }
}

