package com.qk.dm.metadata.rest;

import com.qk.dm.metadata.respose.ResponseWrapper;
import com.qk.dm.metadata.service.MtdClassifyService;
import com.qk.dm.metadata.vo.MtdClassifyVO;
import com.qk.dm.metadata.vo.PageResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author wangzp
 * @date 2021/7/31 14:21
 * @since 1.0.0
 */
@RestController
@RequestMapping("/classify")
@ResponseWrapper
public class MtdClassifyController {
    private final MtdClassifyService mtdClassifyService;

    @Autowired
    public MtdClassifyController(MtdClassifyService mtdClassifyService) {
        this.mtdClassifyService = mtdClassifyService;
    }

    /**
     * 新增元数据分类
     *
     * @param: mtdClassifyAtlasVO 元数据分类VO
     * @return: DefaultCommonResult
     */
    @PostMapping("")
    public void insert(@RequestBody @Valid MtdClassifyVO mtdClassifyVO) {
        mtdClassifyService.insert(mtdClassifyVO);
    }

    /**
     * 修改元数据标签
     *
     * @param: mtdClassifyAtlasVO 元数据分类VO
     * @return: DefaultCommonResult
     */
    @PutMapping("/{id}")
    public void update(@PathVariable("id") Long id, @RequestBody @Valid MtdClassifyVO mtdClassifyVO) {
        mtdClassifyService.update(id, mtdClassifyVO);
    }

    /**
     * 删除元数据标签
     *
     * @param: mtdClassifyAtlasVO 元数据分类VO
     * @return: DefaultCommonResult
     */
    @DeleteMapping("/{ids}")
    public void delete(@PathVariable("ids") String ids) {
        mtdClassifyService.delete(ids);
    }

    /**
     * 查询元数据标签
     *
     * @param: pagination分页查询参数对象: page,size,sortStr
     * @return: 返回分类列表信息
     */
    @GetMapping("/page")
    public PageResultVO<MtdClassifyVO> listByPage(MtdClassifyVO mtdClassifyVO) {
        return mtdClassifyService.listByPage(mtdClassifyVO);
    }

    /**
     * 查询元数据标签
     *
     * @param: pagination分页查询参数对象: page,size,sortStr
     * @return: 返回分类列表信息
     */
    @GetMapping("")
    public List<MtdClassifyVO> listByAll(MtdClassifyVO mtdClassifyVO) {
        return mtdClassifyService.listByAll(mtdClassifyVO);
    }
}
