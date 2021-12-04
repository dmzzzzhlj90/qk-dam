package com.qk.dm.dataquality.service.impl;

import com.qk.dm.dataquality.service.DqcRuleSqlBuilderService;
import com.qk.dm.dataquality.utils.GenerateSqlUtil;
import org.springframework.stereotype.Service;

/**
 * 生成执行sql
 *
 * @author wjq
 * @date 2021/12/4
 * @since 1.0.0
 */
@Service
public class DqcRuleSqlBuilderServiceImpl implements DqcRuleSqlBuilderService {

    @Override
    public String getExecuteSql(String tempSQL, String condition, String value) {
        String executeSql = GenerateSqlUtil.matchReplaceWithCondition(tempSQL, condition, value);
        return executeSql;
    }

}
