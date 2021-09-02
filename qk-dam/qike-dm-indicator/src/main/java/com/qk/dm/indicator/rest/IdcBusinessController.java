package com.qk.dm.indicator.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.indicator.params.dto.IdcBusinessDTO;
import com.qk.dm.indicator.params.dto.IdcBusinessPageDTO;
import com.qk.dm.indicator.params.vo.IdcBusinessVO;
import com.qk.dm.indicator.service.IdcBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * 业务指标相关接口
 *
 * @author wangzp
 * @date 2021/9/2 15:18
 * @since 1.0.0
 */
@Controller
@RequestMapping("/business")
public class IdcBusinessController {

    private final IdcBusinessService idcBusinessService;

    @Autowired
    public IdcBusinessController(IdcBusinessService idcBusinessService){
        this.idcBusinessService = idcBusinessService;
    }

    /**
     * 添加业务指标
     *
     * @param idcBusinessDTO
     * @return DefaultCommonResult
     */
    @PostMapping("")
    public DefaultCommonResult add(@RequestBody @Validated IdcBusinessDTO idcBusinessDTO) {
        idcBusinessService.insert(idcBusinessDTO);
        return DefaultCommonResult.success();
    }

    /**
     * 修改业务指标
     *
     * @param id
     * @param idcBusinessDTO
     * @return DefaultCommonResult
     */
    @PutMapping("/{id}")
    public DefaultCommonResult update(@PathVariable("id") Long id, @RequestBody @Validated IdcBusinessDTO idcBusinessDTO) {
        idcBusinessService.update(id, idcBusinessDTO);
        return DefaultCommonResult.success();
    }

    /**
     * 根据id删除业务指标
     * @param ids
     * @return
     */
    @DeleteMapping("/{ids}")
    public DefaultCommonResult delete(@PathVariable("ids") String ids) {
        idcBusinessService.delete(ids);
        return DefaultCommonResult.success();
    }

    /**
     * 根据id获取业务指标详情
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public DefaultCommonResult<IdcBusinessVO> detail(@PathVariable("id") Long id) {
        IdcBusinessVO idcBusinessVO = idcBusinessService.detail(id);
        return DefaultCommonResult.success(ResultCodeEnum.OK, idcBusinessVO);
    }

    /**
     * 获取指标分页查询列表
     *
     * @param idcBusinessPageDTO
     * @return DefaultCommonResult<PageResultVO < IdcTimeLimitVO>>
     */
    @GetMapping("/page/list")
    public DefaultCommonResult<PageResultVO<IdcBusinessVO>> pageList(@RequestBody @Validated IdcBusinessPageDTO idcBusinessPageDTO) {
        PageResultVO<IdcBusinessVO> list = idcBusinessService.findListPage(idcBusinessPageDTO);
        return DefaultCommonResult.success(ResultCodeEnum.OK, list);
    }
}
