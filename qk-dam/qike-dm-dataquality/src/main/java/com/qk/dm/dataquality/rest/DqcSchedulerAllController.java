//package com.qk.dm.dataquality.rest;
//
//import com.qk.dam.commons.http.result.DefaultCommonResult;
//import com.qk.dm.dataquality.service.DqcSchedulerAllService;
//import com.qk.dm.dataquality.vo.DqcDispatchVo;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
///**
// * 数据质量_规则调度统一接口
// *
// * @author wjq
// * @date 2021/11/08
// * @since 1.0.0
// */
//@Slf4j
//@RestController
//@RequestMapping("/scheduler/all/")
//public class DqcSchedulerAllController {
//    private final DqcSchedulerAllService dqcSchedulerAllService;
//
//    @Autowired
//    public DqcSchedulerAllController(DqcSchedulerAllService dqcSchedulerAllService) {
//        this.dqcSchedulerAllService = dqcSchedulerAllService;
//    }
//
//    @PostMapping("")
//    //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.CREATE)
//    public DefaultCommonResult insert(@RequestBody @Validated DqcDispatchVo dqcDispatchVo) {
//        dqcSchedulerAllService.insert(dqcDispatchVo);
//        return DefaultCommonResult.success();
//    }
//
//    @PutMapping("")
//    //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.UPDATE)
//    public DefaultCommonResult update(@RequestBody @Validated DqcDispatchVo dqcDispatchVo) {
//        dqcSchedulerAllService.update(dqcDispatchVo);
//        return DefaultCommonResult.success();
//    }
//
//    @DeleteMapping("/{id}")
//    //  @Auth(bizType = BizResource.DSD_DIR, actionType = RestActionType.DELETE)
//    public DefaultCommonResult delete(@PathVariable("id") Integer id) {
//        dqcSchedulerAllService.delete(id);
//        return DefaultCommonResult.success();
//    }
//}
