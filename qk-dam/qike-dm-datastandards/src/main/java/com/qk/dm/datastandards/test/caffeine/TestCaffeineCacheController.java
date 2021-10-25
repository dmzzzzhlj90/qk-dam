package com.qk.dm.datastandards.test.caffeine;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dm.datastandards.entity.DsdCodeInfoExt;
import com.qk.dm.datastandards.test.caffeine.service.TestCaffeineCacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 测试CaffeineCache缓存机制
 *
 * @author wjq
 * @date 2021/10/22
 * @since 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/test/caffeine/cache/")
public class TestCaffeineCacheController {

    private final TestCaffeineCacheService testCaffeineCacheService;

    @Autowired
    public TestCaffeineCacheController(TestCaffeineCacheService testCaffeineCacheService) {
        this.testCaffeineCacheService = testCaffeineCacheService;
    }


    /**
     * @param id
     * @return DefaultCommonResult<Map < String, Object>>
     */
    @GetMapping(value = "/query")
    public DefaultCommonResult<DsdCodeInfoExt> queryData(Long id) {
        return DefaultCommonResult.success(ResultCodeEnum.OK, testCaffeineCacheService.queryData(id));
    }

    /**
     * @param dsdCodeInfoExt
     * @return DefaultCommonResult<Map < String, Object>>
     */
    @PostMapping(value = "/add")
    public DefaultCommonResult addData(@RequestBody DsdCodeInfoExt dsdCodeInfoExt) {
        testCaffeineCacheService.addData(dsdCodeInfoExt);
        return DefaultCommonResult.success();
    }

    /**
     * @param dsdCodeInfoExt
     * @return DefaultCommonResult<Map < String, Object>>
     */
    @PutMapping(value = "/update")
    public DefaultCommonResult updateData(@RequestBody DsdCodeInfoExt dsdCodeInfoExt) {
        testCaffeineCacheService.updateData(dsdCodeInfoExt);
        return DefaultCommonResult.success();
    }

    /**
     * @param id
     * @return DefaultCommonResult<Map < String, Object>>
     */
    @DeleteMapping(value = "/delete")
    public DefaultCommonResult deleteData(Long id) {
        testCaffeineCacheService.deleteData(id);
        return DefaultCommonResult.success();
    }
}
