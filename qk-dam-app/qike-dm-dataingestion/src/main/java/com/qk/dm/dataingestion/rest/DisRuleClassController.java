package com.qk.dm.dataingestion.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.dataingestion.service.DisRuleClassService;
import com.qk.dm.dataingestion.vo.DisRuleClassVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 规则分类
 * @author wangzp
 * @date 2022/04/09 16:46
 * @since 1.0.0
 */
@RestController
@RequestMapping("/rule/class")
public class DisRuleClassController {

    public final DisRuleClassService DisRuleClassService;

    public DisRuleClassController(DisRuleClassService disRuleClassService) {
        DisRuleClassService = disRuleClassService;
    }


    /**
     * 获取规则列表
     * @return DefaultCommonResult<List<DisRuleClassVO>>
     */
    @GetMapping("/list")
    public DefaultCommonResult<List<DisRuleClassVO>> list(){
        return  DefaultCommonResult.success(ResultCodeEnum.OK,DisRuleClassService.list());
    }

    /**
     * 添加规则数据
     * @param disRuleClassVO 规则实体参数
     * @return DefaultCommonResult
     */
    @PostMapping("")
    public DefaultCommonResult insert(@RequestBody @Validated DisRuleClassVO disRuleClassVO) {
        DisRuleClassService.insert(disRuleClassVO);
        return DefaultCommonResult.success();
    }
    /**
     * 修改规则数据
     * @param disRuleClassVO 规则实体参数
     * @return DefaultCommonResult
     */
    @PutMapping("")
    public DefaultCommonResult update(@RequestBody @Validated DisRuleClassVO disRuleClassVO) {
        DisRuleClassService.update(disRuleClassVO);
        return DefaultCommonResult.success();
    }

    /**
     * 根据id级联删除（包括下面的子节点）
     * @param ids 规则分类ids
     * @return DefaultCommonResult
     */
    @DeleteMapping("/{ids}")
    public DefaultCommonResult cascadeDelete(@PathVariable("ids") String ids) {
        DisRuleClassService.delete(ids);
        return DefaultCommonResult.success();
    }
}
