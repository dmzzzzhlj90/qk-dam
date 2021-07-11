package com.qk.dm.dataingestion.service.impl;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.COSObjectSummary;
import com.qcloud.cos.model.ListObjectsRequest;
import com.qcloud.cos.model.ObjectListing;
import com.qcloud.cos.model.ObjectMetadata;
import com.qk.dam.sqlloader.constant.LongGovConstant;
import com.qk.dam.sqlloader.cos.TxCOSClient;
import com.qk.dam.sqlloader.repo.PiciTaskAgg;
import com.qk.dam.sqlloader.repo.PiciTaskLogAgg;
import com.qk.dam.sqlloader.vo.PiciTaskLogVO;
import com.qk.dam.sqlloader.vo.PiciTaskVO;
import com.qk.dm.dataingestion.service.CosTaskFilesService;
import com.qk.dm.dataingestion.vo.CosTaskFileInfoVO;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * @author wjq
 * @date 2021/6/28 19:43
 * @since 1.0.0
 */
@Service
public class CosTaskFilesServiceImpl implements CosTaskFilesService {
  private static final Log LOG = LogFactory.get("同步获取COS客户端文件信息");

  @Override
  public List<CosTaskFileInfoVO> getCosTaskFilesInfo(String dataDay) {
    LOG.info("开始获取COS客户端文件,日期为:【{}】", dataDay);

    List<CosTaskFileInfoVO> result = new ArrayList<>(16);
    try {
      COSClient cosClient = TxCOSClient.cosClient;
      LOG.info("成功连接到COS客户端!");
      ListObjectsRequest listObjectsRequest = new ListObjectsRequest();
      listObjectsRequest.setBucketName(LongGovConstant.BUCKETNAME);
      listObjectsRequest.setPrefix(dataDay + "/");
      listObjectsRequest.setMaxKeys(1000);

      ObjectListing objectListing = cosClient.listObjects(listObjectsRequest);
      List<COSObjectSummary> cosObjectSummaryList = objectListing.getObjectSummaries();

      LOG.info("获取到COS客户端所有文件信息!");
      for (COSObjectSummary cosObjectSummary : cosObjectSummaryList) {
        ObjectMetadata objectMetadata =
            cosClient.getObjectMetadata(LongGovConstant.BUCKETNAME, cosObjectSummary.getKey());
        Map<String, String> userMetadata = objectMetadata.getUserMetadata();
        if (userMetadata.size() != 0) {
          CosTaskFileInfoVO cosTaskFileInfoVO =
              CosTaskFileInfoVO.builder()
                  .tableName(
                      objectMetadata
                          .getUserMetadata()
                          .get(LongGovConstant.GET_COS_META_HEADER_TABLE))
                  .pici(
                      objectMetadata
                          .getUserMetadata()
                          .get(LongGovConstant.GET_COS_META_HEADER_PICI))
                  .filePath(cosObjectSummary.getKey())
                  .build();
          result.add(cosTaskFileInfoVO);
          if (result.size() > 0) {
            List<PiciTaskLogVO> piciIsHiveAllList = PiciTaskLogAgg.qkLogPiciIsHiveAll();
            List<String> tableNamePiciList =
                piciIsHiveAllList.stream()
                    .map(ptlv -> ptlv.getTableName() + "_" + ptlv.getPici())
                    .collect(Collectors.toList());
            result =
                result.stream()
                    .filter(
                        ctfiv ->
                            tableNamePiciList.contains(
                                ctfiv.getTableName() + "_" + ctfiv.getPici()))
                    .collect(Collectors.toList());
          }
        }
      }

      if (result.size() > 0) {
        List<PiciTaskLogVO> piciIsHiveAllList = PiciTaskLogAgg.qkLogPiciIsHiveAll();
        List<String> tableNamePiciList =
            piciIsHiveAllList.stream()
                .map(ptlv -> ptlv.getTableName() + "_" + ptlv.getPici())
                .collect(Collectors.toList());
        result =
            result.stream()
                .filter(
                    ctfiv ->
                        tableNamePiciList.contains(ctfiv.getTableName() + "_" + ctfiv.getPici()))
                .collect(Collectors.toList());
      }
    } catch (CosServiceException e) {
      e.printStackTrace();
    }
    LOG.info("成功获取到COS客户端文件,文件个数为:【{}】", result.size());
    return result;
  }

  @Override
  public void setFiLesMetaDataHearderInfo(String dataDay) {
    COSClient cosClient = TxCOSClient.cosClient;
    LOG.info("成功连接到COS客户端!");
    ListObjectsRequest listObjectsRequest = new ListObjectsRequest();
    listObjectsRequest.setBucketName(LongGovConstant.BUCKETNAME);
    listObjectsRequest.setPrefix(dataDay + "/");
    listObjectsRequest.setMaxKeys(1000);

    ObjectListing objectListing = cosClient.listObjects(listObjectsRequest);
    List<COSObjectSummary> cosObjectSummaryList = objectListing.getObjectSummaries();

    List<PiciTaskVO> piciTaskVOS = PiciTaskAgg.longgovTaskAllPath();
    Map<String, PiciTaskVO> piciTaskVOMap =
        piciTaskVOS.stream()
            .collect(Collectors.toMap(PiciTaskVO::getOssPath, PiciTaskVO -> PiciTaskVO));
    int index = 0;
    for (COSObjectSummary cosObjectSummary : cosObjectSummaryList) {
      if (index != 0) {
        String key = cosObjectSummary.getKey();
        String fileName = key.split("/")[1];
        PiciTaskVO piciTaskVO = piciTaskVOMap.get(fileName);

        ObjectMetadata objectMetadata =
            cosClient.getObjectMetadata(LongGovConstant.BUCKETNAME, key);
        objectMetadata.setContentType(LongGovConstant.COS_META_CONTENTTYPE);
        objectMetadata.setHeader(LongGovConstant.COS_META_HEADER_TABLE, piciTaskVO.getTableName());
        objectMetadata.setHeader(LongGovConstant.COS_META_HEADER_PICI, piciTaskVO.getPici());

        cosClient.updateObjectMetaData(LongGovConstant.BUCKETNAME, key, objectMetadata);
        LOG.info(
            "成功设置tableName:【{}】,pici:【{}】的COS任务文件_objectMetadata_Header信息!",
            piciTaskVO.getTableName(),
            piciTaskVO.getPici());
      }
      index++;
    }
  }
}
