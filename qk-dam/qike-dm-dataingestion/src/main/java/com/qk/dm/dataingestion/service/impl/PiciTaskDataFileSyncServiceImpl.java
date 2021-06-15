package com.qk.dm.dataingestion.service.impl;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectResult;
import com.qk.dam.sqlloader.constant.LongGovConstant;
import com.qk.dam.sqlloader.cos.TxCOSClient;
import com.qk.dam.sqlloader.repo.PiciTaskAgg;
import com.qk.dam.sqlloader.repo.PiciTaskLogAgg;
import com.qk.dam.sqlloader.vo.PiciTaskLogVO;
import com.qk.dam.sqlloader.vo.PiciTaskVO;
import com.qk.dm.dataingestion.service.PiciTaskDataFileSyncService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import static com.qk.dam.sqlloader.DmSqlLoader.getPiciTask;
import static com.qk.dam.sqlloader.util.DownloadFile.downFileByteArray;

/**
 * 日志文件数据同步
 *
 * @author wjq
 * @date 2021/6/8
 * @since 1.0.0
 */
@Slf4j
@Service
public class PiciTaskDataFileSyncServiceImpl implements PiciTaskDataFileSyncService {
    private static final Log LOG = LogFactory.get("批次数据文件同步");

    @Override
    public int syncPiciTaskFilesData(String dataDay, String frontTabNamePatter, String batchNum, String bucketName) {
        int rtState = LongGovConstant.RESULT_SUCCESS_EMPTY;
        //获取cloud.tencent连接Client
        COSClient cosClient = TxCOSClient.cosClient;
        LOG.info("成功连接到桶名称为:【{}】的腾讯云COS客户端!");
//        String bucketName = createBucket(cosClient);
        createFileDir(cosClient, bucketName, dataDay);
        LOG.info("创建腾讯云COS文件夹成功!使用桶名称为:【{}】,创建的文件夹名称为:【{}】;", dataDay, bucketName);

        //获取文件信息
        List<PiciTaskVO> piciTasks;
        if (StringUtils.isEmpty(frontTabNamePatter) || StringUtils.isEmpty(batchNum)) {
            piciTasks = getPiciTask();
        } else {
            piciTasks = PiciTaskAgg.longgovFrontTaskrizhi(frontTabNamePatter + "%", Integer.parseInt(batchNum));
        }

        LOG.info("查询需要同步的批次数量为:【{}】个;", piciTasks.size());
        for (PiciTaskVO piciTaskVO : piciTasks) {
            try {
                //获取原始数据文件
                LOG.info("准备下载,表名称:【{}】,批次:【{}】的阿里云文件", piciTaskVO.getTableName(), piciTaskVO.getPici());
                byte[] bytes = downFileByteArray(LongGovConstant.HOST_ALIYUN_OSS + piciTaskVO.getOssPath());
                LOG.info("成功下载,表名称【{}】,批次【{}】的阿里云文件", piciTaskVO.getTableName(), piciTaskVO.getPici());

                //同步数据文件到COS
                LOG.info("准备上传,表名称:【{}】,批次:【{}】的文件到腾讯云COS!", piciTaskVO.getTableName(), piciTaskVO.getPici());
                uploadFileToCloudTencent(piciTaskVO, bytes, cosClient, bucketName, dataDay);
                LOG.info("成功上传,表名称:【{}】,批次:【{}】的文件到腾讯云COS!", piciTaskVO.getTableName(), piciTaskVO.getPici());

                //更新日志表状态信息
                LOG.info("准备更新,表名称:【{}】,批次:【{}】的批次日志表状态信息!", piciTaskVO.getTableName(), piciTaskVO.getPici());
                PiciTaskLogAgg.saveQkLogPici(new PiciTaskLogVO(piciTaskVO.getPici(), piciTaskVO.getTableName(), 0, new Date()));
                LOG.info("成功更新,表名称:【{}】,批次:【{}】的批次日志表状态信息!", piciTaskVO.getTableName(), piciTaskVO.getPici());
                rtState = LongGovConstant.RESULT_SUCCESS_EXIST;
            } catch (Exception e) {
                LOG.info("同步失败,表名称:【{}】,批次:【{}】;", piciTaskVO.getTableName(), piciTaskVO.getPici());
                e.printStackTrace();
                rtState = LongGovConstant.RESULT_ERROR;
                return rtState;
            }
        }
        return rtState;
    }

    /**
     * 创建文件目录
     *
     * @Param: cosClient, bucketName
     * @return: void
     **/
    public void createFileDir(COSClient cosClient, String bucketName, String dataDay) {
        try {
            //命名要创建的目录，一定要以 '/' 结尾
            String key = dataDay + "/";
            InputStream emptyContent = new ByteArrayInputStream(new byte[0]);
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(0);
            PutObjectResult putObjectResult = cosClient.putObject(bucketName, key, emptyContent, metadata);
            String etag = putObjectResult.getETag();
            // 关闭输入流...
            emptyContent.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传文件
     *
     * @Param: rizhi, bytes, cosClient
     * @return: void
     **/
    public void uploadFileToCloudTencent(PiciTaskVO piciTaskVO, byte[] bytes, COSClient cosClient, String bucketName, String dataDay) {
        try {
            String[] split = piciTaskVO.getOssPath().split("/");
            String key = dataDay + "/" + split[split.length - 1];

            InputStream inputStream = new ByteArrayInputStream(bytes);
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(bytes.length);
            objectMetadata.setContentType(LongGovConstant.COS_META_CONTENTTYPE);
            objectMetadata.setHeader(LongGovConstant.COS_META_HEADER_TABLE, piciTaskVO.getTableName());
            objectMetadata.setHeader(LongGovConstant.COS_META_HEADER_PICI, piciTaskVO.getPici());

            PutObjectResult putObjectResult = cosClient.putObject(bucketName, key, inputStream, objectMetadata);
            // 关闭输入流...
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //    /**
//     * 创建Bucket
//     *
//     * @Param: cosClient
//     * @return: java.lang.String
//     **/
//    public String createBucket(COSClient cosClient, String bucketName) {
//        CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
//        //设置 bucket 的权限为 Private(私有读写)、其他可选有 PublicRead（公有读私有写）、PublicReadWrite（公有读写）
//        createBucketRequest.setCannedAcl(CannedAccessControlList.Private);
//        try {
//            Bucket bucketResult = cosClient.createBucket(createBucketRequest);
//        } catch (CosServiceException serverException) {
//            serverException.printStackTrace();
//        } catch (CosClientException clientException) {
//            clientException.printStackTrace();
//        }
//        return bucketName;
//        批量执行数据同步
//        List<FutureTask<Integer>> futureTasks = batchTask(piciTasks, (piciTaskVO) -> {
//            //获取原始数据文件
//            byte[] bytes = downFileByteArray(LongGovConstant.HOST_ALIYUN_OSS + piciTaskVO.getOssPath());
//            //同步数据文件到COS
//            uploadFileToCloudTencent(piciTaskVO, bytes, cosClient, bucketName, dataDay);
//            //更新日志表状态信息
//            PiciTaskLogAgg.saveQkLogPici(new PiciTaskLogVO(piciTaskVO.getPici(), piciTaskVO.getTableName(), 0, new Date()));
//            return 1;
//        });
//        doneFureTasks(futureTasks);
//    }

    //    private void doneFureTasks(List<FutureTask<Integer>> futureTasks) throws InterruptedException, ExecutionException {
//        int sum = futureTasks.stream().mapToInt(t -> {
//            try {
//                return t.get();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return 0;
//        }).sum();
//
//        if (sum == futureTasks.size()) {
//            LOG.info("批次【{}】所有上传COS完毕", sum);
//        }
//        for (FutureTask<Integer> futureTask : futureTasks) {
//            Integer state = futureTask.get();
//        }
//    }

}
