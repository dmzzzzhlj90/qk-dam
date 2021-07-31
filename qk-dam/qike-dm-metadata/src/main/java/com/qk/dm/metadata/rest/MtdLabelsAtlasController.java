package com.qk.dm.metadata.rest;

import com.qk.dm.metadata.respose.ResponseWrapper;
import com.qk.dm.metadata.service.MtdLabelsService;
import com.qk.dm.metadata.vo.MtdLabelsInfoVO;
import com.qk.dm.metadata.vo.MtdLabelsVO;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/labels")
public class MtdLabelsAtlasController {

    private final MtdLabelsService mtdLabelsService;

    public MtdLabelsAtlasController(MtdLabelsService mtdLabelsService) {
        this.mtdLabelsService = mtdLabelsService;
    }

    /**
     * 新增元数据标签
     *
     * @param: mtdLabelsVO 元数据标签VO
     * @return: DefaultCommonResult
     */
    @PostMapping("")
    @ResponseWrapper
    public void insert(@RequestBody @Valid MtdLabelsVO mtdLabelsVO) {
        mtdLabelsService.insert(mtdLabelsVO);
    }

    /**
     * 修改元数据标签
     *
     * @param: mtdLabelsVO 元数据标签VO
     * @return: DefaultCommonResult
     */
    @PutMapping("/{id}")
    @ResponseWrapper
    public void update(@PathVariable("id") Long id, @RequestBody @Valid MtdLabelsVO mtdLabelsVO) {
        mtdLabelsService.update(id, mtdLabelsVO);
    }

    /**
     * 查询元数据标签
     *
     * @param: mtdLabelsVO 元数据标签VO
     * @return: 返回标签列表信息
     */
    @GetMapping("")
    @ResponseWrapper
    public List<MtdLabelsInfoVO> listByAll(MtdLabelsVO mtdLabelsVO) {
        return mtdLabelsService.listByAll(mtdLabelsVO);
    }
}
