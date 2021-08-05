package com.qk.dm.datastandards.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.datastandards.service.DataStandardDirService;
import com.qk.dm.datastandards.vo.DataStandardTreeVO;
import com.qk.dm.datastandards.vo.DsdDirVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据标准__目录接口
 *
 * @author wjq
 * @date 20210603
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/dir")
public class DataStandardDirController {
    private final DataStandardDirService dataStandardDirService;

    @Autowired
    public DataStandardDirController(DataStandardDirService dataStandardDirService) {
        this.dataStandardDirService = dataStandardDirService;
    }

    /**
     * 获取数据标准分类目录树
     *
     * @return DefaultCommonResult<List<DataStandardTreeVO>>
     */
    @GetMapping("/tree")
    public DefaultCommonResult<List<DataStandardTreeVO>> getDsdDirTree() {
        return DefaultCommonResult.success(ResultCodeEnum.OK, dataStandardDirService.getTree());
    }

    /**
     * 新增数据标准分类目录
     *
     * @param dsdDirVO
     * @return DefaultCommonResult
     */
    @PostMapping("/add")
    public DefaultCommonResult addDsdDir(@RequestBody DsdDirVO dsdDirVO) {
        dataStandardDirService.addDsdDir(dsdDirVO);
        return DefaultCommonResult.success();
    }

    /**
     * 编辑数据标准分类目录
     *
     * @param dsdDirVO
     * @return DefaultCommonResult
     */
    @PutMapping("/update")
    public DefaultCommonResult updateDsdDir(@RequestBody DsdDirVO dsdDirVO) {
        dataStandardDirService.updateDsdDir(dsdDirVO);
        return DefaultCommonResult.success();
    }

    /**
     * 标准目录单子节点删除方式
     *
     * @param id
     * @return DefaultCommonResult
     */
    @DeleteMapping("/delete/{id}")
    public DefaultCommonResult deleteDsdDir(@PathVariable("id") Integer id) {
        dataStandardDirService.deleteDsdDir(id);
        return DefaultCommonResult.success();
    }

    /**
     * 标准目录支持根节点关联删除子节点方式
     *
     * @param id
     * @return DefaultCommonResult
     */
    @DeleteMapping("/delete/root/{id}")
    public DefaultCommonResult deleteDsdDirRoot(@PathVariable("id") Integer id) {
        dataStandardDirService.deleteDsdDirRoot(id);
        return DefaultCommonResult.success();
    }
}
