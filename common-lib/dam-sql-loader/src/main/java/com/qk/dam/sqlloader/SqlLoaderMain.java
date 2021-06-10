package com.qk.dam.sqlloader;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.*;
import com.qk.dam.sqlloader.constant.LongGovConstant;
import com.qk.dam.sqlloader.repo.PiciTaskLogAgg;
import com.qk.dam.sqlloader.repo.QkUpdatedAgg;
import com.qk.dam.sqlloader.util.TargzUtils;
import com.qk.dam.sqlloader.vo.PiciTaskLogVO;
import com.qk.dam.sqlloader.vo.PiciTaskVO;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.qk.dam.sqlloader.DmSqlLoader.*;

public class SqlLoaderMain {
    private static final Log LOG = LogFactory.get("sql执行");

    public static int executeTask(final String updated) {
        LOG.info("执行的任务参数",updated);
        final String objKeyDate = updated.replace("-","");

        List<PiciTaskVO> executePiciTask = getCosPiciTask(updated);
        LOG.info("桶中有【{}】个任务",executePiciTask.size());
        for (PiciTaskVO piciTaskVO : executePiciTask) {
            COSObjectInputStream cosObjectStream = getCosObjectStream(objKeyDate + "/" + getFileNameByOss(piciTaskVO.getOssPath()));
            Map<String, String> sqlMap = TargzUtils.readTarbgzContent(cosObjectStream);
            for (Map.Entry<String, String> stringStringEntry : sqlMap.entrySet()) {
                String key = stringStringEntry.getKey();
                String sql = stringStringEntry.getValue();
                LOG.info("准备执行sql文件【{}】",key);
                try {
                    QkUpdatedAgg.qkUpdatedBatchSql(sql);
                    PiciTaskLogAgg.saveQkLogPici(new PiciTaskLogVO(piciTaskVO.getPici(), piciTaskVO.getTableName(), 1, new Date()));
                    LOG.info("sql文件【{}】执行成功,批次【{}】表名【{}】",key,piciTaskVO.getPici(),piciTaskVO.getTableName());
                } catch (SQLException throwables) {
                    LOG.info("执行sql文件失败【{}】,批次【{}】表名【{}】",key,piciTaskVO.getPici(),piciTaskVO.getTableName());
                    throwables.printStackTrace();
                    return 0;
                }
            }
        }
        return 1;
    }



}