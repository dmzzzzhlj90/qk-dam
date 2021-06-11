package com.qk.dam.sqlloader;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.*;
import com.qk.dam.sqlloader.constant.LongGovConstant;
import com.qk.dam.sqlloader.repo.PiciTaskLogAgg;
import com.qk.dam.sqlloader.repo.QkUpdated1Agg;
import com.qk.dam.sqlloader.repo.QkUpdatedAgg;
import com.qk.dam.sqlloader.util.TargzUtils;
import com.qk.dam.sqlloader.vo.PiciTaskLogVO;
import com.qk.dam.sqlloader.vo.PiciTaskVO;

import java.io.File;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.qk.dam.sqlloader.DmSqlLoader.*;
import static com.qk.dam.sqlloader.constant.LongGovConstant.LOCAL_FILES_PATH;

public class SqlLoaderMain {
    private static final Log LOG = LogFactory.get("sql执行");

    public static int writeDestTask(final String updated) {
        LOG.info("执行的任务参数",updated);
        final String objKeyDate = updated.replace("-","");

        List<PiciTaskVO> executePiciTask = getCosPiciTask(updated);
        LOG.info("桶中有【{}】个任务",executePiciTask.size());

        for (PiciTaskVO piciTaskVO : executePiciTask) {
            COSObjectInputStream cosObjectStream = getCosObjectStream(  objKeyDate + "/" + getFileNameByOss(piciTaskVO.getOssPath()));
            try {
                TargzUtils.writeRtFile(cosObjectStream,LOCAL_FILES_PATH+piciTaskVO.getTableName()+"-"+piciTaskVO.getPici()+".sql");
                LOG.info("sql文件下载成功,批次【{}】表名【{}】",piciTaskVO.getPici(),piciTaskVO.getTableName());
            } catch (Exception e) {
                LOG.info("sql文件下载失败,批次【{}】表名【{}】",piciTaskVO.getPici(),piciTaskVO.getTableName());
                e.printStackTrace();
            }
        }
        return 1;
    }

    public static int executeTask(final String updated) {
        LOG.info("执行的任务参数",updated);
        final String objKeyDate = updated.replace("-","");

        List<PiciTaskVO> executePiciTask = getCosPiciTask(updated);
        LOG.info("桶中有【{}】个任务",executePiciTask.size());
        for (PiciTaskVO piciTaskVO : executePiciTask) {
            COSObjectInputStream cosObjectStream = getCosObjectStream(objKeyDate + "/" + getFileNameByOss(piciTaskVO.getOssPath()));

            try {
                List<String> sqls = TargzUtils.readTarbgzContent(cosObjectStream);
                LOG.info("SQL条数；【{}】,批次【{}】表名【{}】",sqls.size(),piciTaskVO.getPici(),piciTaskVO.getTableName());
                QkUpdatedAgg.qkUpdatedBatchSql(sqls);
                PiciTaskLogAgg.saveQkLogPici(new PiciTaskLogVO(piciTaskVO.getPici(), piciTaskVO.getTableName(), 1, new Date()));
                LOG.info("执行sql文件成功,批次【{}】表名【{}】",piciTaskVO.getPici(),piciTaskVO.getTableName());
                QkUpdated1Agg.qkUpdatedBatchSql();
            } catch (SQLException throwables) {
                LOG.info("执行sql文件失败,批次【{}】表名【{}】",piciTaskVO.getPici(),piciTaskVO.getTableName());
                throwables.printStackTrace();
                return 0;
            }
        }
        return 1;
    }



}