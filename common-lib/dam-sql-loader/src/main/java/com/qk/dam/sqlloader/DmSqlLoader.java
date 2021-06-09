package com.qk.dam.sqlloader;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.qk.dam.sqlloader.constant.LongGovConstant;
import com.qk.dam.sqlloader.repo.PiciTaskAgg;
import com.qk.dam.sqlloader.repo.PiciTaskLogAgg;
import com.qk.dam.sqlloader.util.TargzUtils;
import com.qk.dam.sqlloader.vo.PiciTaskLogVO;
import com.qk.dam.sqlloader.vo.PiciTaskVO;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.qk.dam.sqlloader.repo.QkUpdatedAgg.qkUpdatedBatchSql;
import static com.qk.dam.sqlloader.util.DownloadFile.listLocalFileNames;

public class DmSqlLoader {
    private static final Log LOG = LogFactory.get("sql载入程序");

    public static List<PiciTaskVO> getPiciTask() {
        List<PiciTaskVO> piciTasks = PiciTaskAgg.longgovTaskAll();
        List<PiciTaskLogVO> piciTaskLogs = PiciTaskLogAgg.qkLogPiciAll();
        Objects.requireNonNull(piciTasks);
        if (piciTaskLogs != null) {
            List<String> pcr = piciTaskLogs.stream()
                    .map(piciTaskLogVO -> piciTaskLogVO.getPici() + "_" + piciTaskLogVO.getTableName())
                    .collect(Collectors.toList());
            piciTasks = piciTasks.stream()
                    .filter(piciTaskVO ->
                            !pcr.contains(piciTaskVO.getPici() + "_" + piciTaskVO.getTableName()))
                    .collect(Collectors.toList());
        }

        return piciTasks;
    }
    public static List<PiciTaskVO> getPiciTaskNoLocalFile() {
        List<PiciTaskVO> piciTasks = getPiciTask();
        List<String> fileNames = listLocalFileNames();
        if (fileNames != null) {
            piciTasks = piciTasks.stream()
                    .filter(piciTaskVO -> !fileNames.contains(getFileNameByOss(piciTaskVO.getOssPath())))
                    .collect(Collectors.toList());
        }
        return piciTasks;
    }

    private static String getFileNameByOss(String ossPath){
        String[] split = ossPath.split("\\/");
        return split[split.length - 1];
    }
    public static void qkUpdatedBatch(String fileName) {
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
