package com.qk.dam.sqlloader;

import cn.hutool.core.collection.ListUtil;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.*;
import com.qk.dam.sqlloader.constant.LongGovConstant;
import com.qk.dam.sqlloader.repo.PiciTaskAgg;
import com.qk.dam.sqlloader.repo.PiciTaskLogAgg;
import com.qk.dam.sqlloader.vo.PiciTaskLogVO;
import com.qk.dam.sqlloader.vo.PiciTaskVO;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.qk.dam.sqlloader.cos.TxCOSClient.cosClient;
import static com.qk.dam.sqlloader.repo.PiciTaskAgg.longgovTaskAll;
import static com.qk.dam.sqlloader.repo.PiciTaskLogAgg.*;

public class DmSqlLoader {
    final static List<String> cosObjKeys;
    static {
        cosObjKeys = getAllCosTarFile();
    }
    /**
     * 获取cos文件流
     * @param fileName 对象key
     * @return COSObjectInputStream
     */
    public static COSObjectInputStream getCosObjectStream(String fileName) {
        Optional<String> objKey = cosObjKeys.stream().filter(cosObj -> cosObj.endsWith(fileName)).findFirst();
        if (objKey.isPresent()){
            COSObject object = cosClient.getObject(
                    new GetObjectRequest(LongGovConstant.BUCKETNAME, objKey.get()));
            return object.getObjectContent();
        }
       return null;
    }

    /**
     * 获取日志和cos中均存在的批次任务
     * @return List<PiciTaskVO>
     */
    public static List<PiciTaskVO> getCosTableTask(final String tableName) {
        List<PiciTaskLogVO> piciTaskLog = qkLogTableName(tableName);
        return getPiciTaskVOS(piciTaskLog);
    }
    /**
     * 获取日志和cos中均存在的批次任务
     * @return List<PiciTaskVO>
     */
    public static List<PiciTaskVO> getCosPiciTask(final String tableName,final int pici) {
        List<PiciTaskLogVO> piciTaskLog = qkLogPici(pici,tableName);
        return getPiciTaskVOS(piciTaskLog);
    }
    /**
     * 获取日志和cos中均存在的批次任务
     * @return List<PiciTaskVO>
     */
    public static List<PiciTaskVO> getCosPiciTask(final int pici) {
        List<PiciTaskLogVO> piciTaskLog = qkLogPici(pici);
        return getPiciTaskVOS(piciTaskLog);
    }


    /**
     * 获取日志和cos中均存在的批次任务
     * @param updated 更新批次的日期
     * @return List<PiciTaskVO>
     */
    public static List<PiciTaskVO> getCosPiciTask(String updated) {
        List<PiciTaskLogVO> piciTaskLog = qkLogPiciUpdated();
        List<PiciTaskVO> piciTask = longgovTaskAll();
        if (piciTaskLog != null) {
            List<String> pcr = piciTaskLog.stream()
                    .map(piciTaskLogVO -> piciTaskLogVO.getPici() + "_" + piciTaskLogVO.getTableName())
                    .collect(Collectors.toList());

            piciTask = piciTask.stream()
                    .filter(piciTaskVO ->
                            pcr.contains(piciTaskVO.getPici() + "_" + piciTaskVO.getTableName()))
                    .collect(Collectors.toList());
        }
        List<String> cosObjKeys = getCosObjKeys(updated);
        Objects.requireNonNull(cosObjKeys);
        Objects.requireNonNull(piciTask);
        piciTask = piciTask
                .stream()
                .filter(piciTaskVO -> cosObjKeys.contains(getFileNameByOss(piciTaskVO.getOssPath())))
                .collect(Collectors.toList());
        return piciTask;
    }

    /**
     * 获取日志和cos中均存在的批次任务
     * @return List<PiciTaskVO>
     */
    public static List<PiciTaskVO> getCosPiciTask() {
        List<PiciTaskLogVO> piciTaskLog = qkLogPiciUpdated();
        List<PiciTaskVO> piciTask = longgovTaskAll();
        if (piciTaskLog != null) {
            List<String> pcr = piciTaskLog.stream()
                    .map(piciTaskLogVO -> piciTaskLogVO.getPici() + "_" + piciTaskLogVO.getTableName())
                    .collect(Collectors.toList());

            piciTask = piciTask.stream()
                    .filter(piciTaskVO ->
                            pcr.contains(piciTaskVO.getPici() + "_" + piciTaskVO.getTableName()))
                    .collect(Collectors.toList());
        }
        List<String> cosObjKeys = getAllCosTarFile();
        Objects.requireNonNull(cosObjKeys);
        piciTask = piciTask.stream()
                .filter(piciTaskVO -> cosObjKeys.contains(getFileNameByOss(piciTaskVO.getOssPath())))
                .collect(Collectors.toList());
        return piciTask;
    }

    /**
     * 获取任务日志中不存在的批次任务
     * @return List<PiciTaskVO>
     */
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

    public static List<String> getAllCosTarFile(){
        List<String> commonPrefixes = getCommonPrefixes();
        return Objects.requireNonNull(commonPrefixes)
                .stream()
                .map(DmSqlLoader::getCosObjKeys)
                .flatMap(strings -> strings != null ? strings.stream() : null)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
    public static List<String> getCommonPrefixes() {
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest();
        listObjectsRequest.setBucketName(LongGovConstant.BUCKETNAME);
        listObjectsRequest.setDelimiter("/");
        listObjectsRequest.setMaxKeys(1000);
        ObjectListing objectListing = null;
        do {
            try {
                objectListing = cosClient.listObjects(listObjectsRequest);
            } catch (CosServiceException e) {
                e.printStackTrace();
            }
            String nextMarker = objectListing.getNextMarker();
            listObjectsRequest.setMarker(nextMarker);
        } while (objectListing.isTruncated());

        if (objectListing.getCommonPrefixes() != null) {
            return objectListing.getCommonPrefixes();
        }
        return null;
    }
    public static String getFileNameByOss(String ossPath) {
        String[] split = ossPath.split("\\/");
        return split[split.length - 1];
    }

    private static List<PiciTaskVO> getPiciTaskVOS(List<PiciTaskLogVO> piciTaskLog) {
        List<PiciTaskVO> piciTask = longgovTaskAll();
        if (piciTaskLog != null) {
            List<String> pcr = piciTaskLog.stream()
                    .map(piciTaskLogVO -> piciTaskLogVO.getPici() + "_" + piciTaskLogVO.getTableName())
                    .collect(Collectors.toList());

            piciTask = piciTask.stream()
                    .filter(piciTaskVO ->
                            pcr.contains(piciTaskVO.getPici() + "_" + piciTaskVO.getTableName()))
                    .collect(Collectors.toList());
        }
        Objects.requireNonNull(cosObjKeys);
        Objects.requireNonNull(piciTask);

        piciTask = piciTask
                .stream()
                .filter(
                        piciTaskVO -> cosObjKeys.stream()
                                .anyMatch(ojk ->
                                        ojk.endsWith(getFileNameByOss(piciTaskVO.getOssPath()))
                                )
                )
                .collect(Collectors.toList());
        return piciTask;
    }

    private static List<String> getCosObjKeys(final String prefixStr) {
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest();
        listObjectsRequest.setBucketName(LongGovConstant.BUCKETNAME);
        listObjectsRequest.setPrefix(prefixStr);
        listObjectsRequest.setMaxKeys(1000);
        ObjectListing objectListing = null;
        do {
            try {
                objectListing = cosClient.listObjects(listObjectsRequest);
            } catch (CosServiceException e) {
                e.printStackTrace();
            }
            String nextMarker = objectListing.getNextMarker();
            listObjectsRequest.setMarker(nextMarker);
        } while (objectListing.isTruncated());

        if (objectListing.getObjectSummaries() != null) {
            return objectListing.getObjectSummaries().stream()
                    .map(COSObjectSummary::getKey)
                    .filter(cosObjectSummaryKey -> cosObjectSummaryKey.endsWith(".tar.gz"))
                    .collect(Collectors.toList());
        }
        return null;
    }
    public static void main(String[] args) {
        List<PiciTaskVO> aggre_addr = getCosTableTask("aggre_addr");
        System.out.println(aggre_addr);
    }

}
