package com.qk.dm.metadata.rest;

import com.qk.dm.metadata.respose.ResponseWrapper;
import com.qk.dm.metadata.service.MtdLabelsAtlasService;
import com.qk.dm.metadata.vo.MtdLabelsAtlasVO;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/labels/atlas")
public class MtdLabelsAtlasController {

    private final MtdLabelsAtlasService mtdLabelsAtlasService;

    public MtdLabelsAtlasController(MtdLabelsAtlasService mtdLabelsAtlasService) {
        this.mtdLabelsAtlasService = mtdLabelsAtlasService;
    }


    /**
     * 新增元数据标签
     *
     * @param: mtdLabelsVO 元数据标签VO
     * @return: DefaultCommonResult
     */
    @PostMapping("")
    @ResponseWrapper
    public void insert(@RequestBody @Valid MtdLabelsAtlasVO mtdLabelsVO) {
        mtdLabelsAtlasService.insert(mtdLabelsVO);
    }

    /**
     * 修改元数据标签
     *
     * @param: mtdLabelsVO 元数据标签VO
     * @return: DefaultCommonResult
     */
    @PutMapping("/")
    @ResponseWrapper
    public void update(@RequestBody @Valid MtdLabelsAtlasVO mtdLabelsVO) {
        mtdLabelsAtlasService.update(mtdLabelsVO);
    }

    /**
     * 查询元数据标签
     *
     * @param: mtdLabelsVO 元数据标签VO
     * @return: 返回标签列表信息
     */
    @GetMapping("/{guid}")
    @ResponseWrapper
    public MtdLabelsAtlasVO getByGuid(@PathVariable("guid") String guid) {
        return mtdLabelsAtlasService.getByGuid(guid);
    }
}
