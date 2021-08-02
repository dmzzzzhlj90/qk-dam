package com.qk.dm.metadata.rest;

import com.qk.dm.metadata.respose.ResponseWrapper;
import com.qk.dm.metadata.service.MtdClassifyAtlasService;
import com.qk.dm.metadata.vo.MtdClassifyAtlasVO;
import com.qk.dm.metadata.vo.PageResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author wangzp
 * @date 2021/7/31 12:21
 * @since 1.0.0
 */
@RestController
@RequestMapping("/classifyAtlas")
@ResponseWrapper
public class MtdClassifyAtlasController {


    private final MtdClassifyAtlasService mtdClassifyAtlasService;

    @Autowired
    public MtdClassifyAtlasController(MtdClassifyAtlasService mtdClassifyAtlasService) {
        this.mtdClassifyAtlasService = mtdClassifyAtlasService;
    }
    /**
     * 新增元数据分类
     *
     * @param: mtdClassifyAtlasVO 元数据分类VO
     * @return: DefaultCommonResult
     */
    @PostMapping("")
    public void insert(@RequestBody @Valid MtdClassifyAtlasVO mtdClassifyAtlasVO) {
        mtdClassifyAtlasService.insert(mtdClassifyAtlasVO);
    }

    /**
     * 修改元数据标签
     *
     * @param: mtdClassifyAtlasVO 元数据分类VO
     * @return: DefaultCommonResult
     */
    @PutMapping("/{id}")
    public void update(@PathVariable("id") Long id, @RequestBody @Valid MtdClassifyAtlasVO mtdClassifyAtlasVO) {
        mtdClassifyAtlasVO.setId(id);
        mtdClassifyAtlasService.update(mtdClassifyAtlasVO);
    }

    /**
     * 删除元数据标签
     *
     * @param: mtdClassifyAtlasVO 元数据分类VO
     * @return: DefaultCommonResult
     */
    @DeleteMapping("/{ids}")
    public void delete(@PathVariable("ids") String ids) {
        mtdClassifyAtlasService.delete(ids);
    }

    /**
     * 查询元数据标签
     *
     * @param: pagination分页查询参数对象: page,size,sortStr
     * @return: 返回分类列表信息
     */
    @GetMapping("/page")
    public PageResultVO<MtdClassifyAtlasVO> listByPage(MtdClassifyAtlasVO mtdClassifyAtlasVO) {
        return mtdClassifyAtlasService.listByPage(mtdClassifyAtlasVO);
    }

    /**
     * 查询元数据标签
     *
     * @param: pagination分页查询参数对象: page,size,sortStr
     * @return: 返回分类列表信息
     */
    @GetMapping("")
    public List<MtdClassifyAtlasVO> listByAll(MtdClassifyAtlasVO mtdClassifyAtlasVO) {
        return mtdClassifyAtlasService.listByAll(mtdClassifyAtlasVO);
    }
}
