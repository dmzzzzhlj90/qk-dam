package com.qk.dam.sqlloader;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.qk.dam.sqlloader.constant.LongGovConstant;
import com.qk.dam.sqlloader.util.TargzUtils;

import java.io.File;
import java.util.Map;
import java.util.Objects;

import static com.qk.dam.sqlloader.repo.QkUpdatedAgg.qkUpdatedBatchSql;

public class DmSqlLoader {
    private static final Log LOG = LogFactory.get("sql载入程序");
    public static void qkUpdatedBatch(String fileName){
        String filePath = LongGovConstant.LOCAL_FILES_PATH + fileName;
        LOG.info("载入tar.gz包！");
        Map<String, String> sqlMap = TargzUtils.readTarbgzContent(new File(filePath));
        Objects.requireNonNull(sqlMap);
        LOG.info("载入成功！");
        sqlMap.forEach((key, sqlValue) -> {
            LOG.info("获取执行的sql文件【{}】！", key);
            qkUpdatedBatchSql(sqlValue);
        });
    }
}
