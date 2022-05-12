package com.qk.dm.reptile.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.reptile.params.dto.RptFindSourceDTO;
import com.qk.dm.reptile.params.vo.RptFindSourceVO;
import com.qk.dm.reptile.service.RptFindSourceService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
/**
 * 数据找源信息
 * @author wangzp
 * @date 2021/05/12 10:16
 * @since 1.0.0
 */
@RestController
@RequestMapping("/find/source")
public class RptFindSourceController {

    private final RptFindSourceService findSourceService;

    public RptFindSourceController(RptFindSourceService findSourceService) {
        this.findSourceService = findSourceService;
    }

    /**
     * 添加找源信息
     * @param findSourceDTO
     * @return DefaultCommonResult
     */
    @PostMapping("")
    public DefaultCommonResult insert(@RequestBody @Validated RptFindSourceDTO findSourceDTO){
        findSourceService.insert(findSourceDTO);
        return DefaultCommonResult.success();
    }

    /**
     * 修改找源信息
     * @param id 基础信息id
     * @param findSourceDTO
     * @return DefaultCommonResult
     */
    @PutMapping("/{id}")
    public DefaultCommonResult update(
            @PathVariable("id") Long id, @RequestBody @Validated RptFindSourceDTO findSourceDTO) {
        findSourceService.update(id, findSourceDTO);
        return DefaultCommonResult.success();
    }

    /**
     * 删除找源信息
     * @param ids 找源信息id 多个使用英文逗号分割
     * @return DefaultCommonResult
     */
    @DeleteMapping("/{ids}")
    public DefaultCommonResult delete(@PathVariable("ids") String ids){
        findSourceService.delete(ids);
        return DefaultCommonResult.success();
    }

    /**
     * 获取找源信息列表
     * @param rptFindSourceDTO
     * @return DefaultCommonResult<PageResultVO<RptFindSourceVO>>
     */
    @PostMapping("/list")
    public DefaultCommonResult<PageResultVO<RptFindSourceVO>> list(
            @RequestBody RptFindSourceDTO rptFindSourceDTO) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, findSourceService.list(rptFindSourceDTO));
    }

}
