package com.qk.dm.datastandards.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.datastandards.service.DataStandardCodeDirService;
import com.qk.dm.datastandards.vo.DataStandardCodeTreeVO;
import com.qk.dm.datastandards.vo.DsdCodeDirVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据标准__码表目录接口
 *
 * @author wjq
 * @date 20210604
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/code/dir")
public class DataStandardCodeDirController {
    private final DataStandardCodeDirService dataStandardCodeDirService;

    @Autowired
    public DataStandardCodeDirController(DataStandardCodeDirService dataStandardCodeDirService) {
        this.dataStandardCodeDirService = dataStandardCodeDirService;
    }

    /**
     * 获取数据标准码表分类目录树
     *
     * @return DefaultCommonResult<List<DataStandardCodeTreeVO>>
     */
    @GetMapping("/tree")
    public DefaultCommonResult<List<DataStandardCodeTreeVO>> getDsdDirTree() {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dataStandardCodeDirService.getTree());
    }

    /**
     * 新增码表分类目录
     *
     * @param dsdCodeDirVO 数据标准码表分类目录VO
     * @return DefaultCommonResult
     */
    @PostMapping("/add")
    public DefaultCommonResult addDsdDir(@RequestBody DsdCodeDirVO dsdCodeDirVO) {
        dataStandardCodeDirService.addDsdDir(dsdCodeDirVO);
        return DefaultCommonResult.success();
    }

    /**
     * 编辑码表分类目录
     *
     * @param dsdCodeDirVO
     * @return DefaultCommonResult
     */
    @PutMapping("/update")
    public DefaultCommonResult updateDsdDir(@RequestBody DsdCodeDirVO dsdCodeDirVO) {
        dataStandardCodeDirService.updateDsdDir(dsdCodeDirVO);
        return DefaultCommonResult.success();
    }

    /**
     * 码表目录单叶子节点删除方式
     *
     * @param id
     * @return DefaultCommonResult
     */
    @DeleteMapping("/delete/{id}")
    public DefaultCommonResult deleteDsdDir(@PathVariable("id") Integer id) {
        dataStandardCodeDirService.deleteDsdDir(id);
        return DefaultCommonResult.success();
    }

    /**
     * 码表目录支持根节点删除关联删除叶子节点方式
     *
     * @param id
     * @return DefaultCommonResult
     */
    @DeleteMapping("/delete/root/{id}")
    public DefaultCommonResult deleteDsdDirRoot(@PathVariable("id") Integer id) {
        dataStandardCodeDirService.deleteDsdDirRoot(id);
        return DefaultCommonResult.success();
    }
}
