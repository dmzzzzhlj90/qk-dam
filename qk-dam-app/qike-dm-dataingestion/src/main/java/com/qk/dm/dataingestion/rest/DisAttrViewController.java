package com.qk.dm.dataingestion.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.dataingestion.service.DisAttrViewService;
import com.qk.dm.dataingestion.vo.disAttrVO;
import org.springframework.web.bind.annotation.*;


/**
 * 高级属性前端展示接口
 * @author wangzp
 * @date 2022/04/18 16:11
 * @since 1.0.0
 */
@RestController
@RequestMapping("/attr")
public class DisAttrViewController {
    private final DisAttrViewService disColumnViewService;

    public DisAttrViewController(DisAttrViewService disColumnViewService) {
        this.disColumnViewService = disColumnViewService;
    }

    /**
     * 获取高级属性展示列表
     * @return DefaultCommonResult<disAttrVO>
     */
    @PostMapping("/list")
    public DefaultCommonResult<disAttrVO> list(){
       return DefaultCommonResult.success(ResultCodeEnum.OK,disColumnViewService.list());
    }

}
