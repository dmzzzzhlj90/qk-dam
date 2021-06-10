package com.qk.dm.dataingestion.service.impl;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import com.qk.dam.sqlloader.constant.LongGovConstant;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

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
    private static final Log LOG = LogFactory.get("文件下载");

    @Override
    public void syncPiciTaskFilesData(String frontTabNamePatter, String batchNum, String bucketName) throws ExecutionException, InterruptedException {
        String dataDay = DateTimeFormatter.ofPattern("yyyyMMdd").format(LocalDateTime.now());

        //获取cloud.tencent连接Client
        COSClient cosClient = getCosClient();
//        String bucketName = createBucket(cosClient);
        createFileDir(cosClient, bucketName, dataDay);

        //获取文件信息
        List<PiciTaskVO> piciTasks;
        if (StringUtils.isEmpty(frontTabNamePatter) || StringUtils.isEmpty(batchNum)) {
            piciTasks = getPiciTask();
        } else {
            piciTasks = PiciTaskAgg.longgovFrontTaskrizhi(frontTabNamePatter + "%", Integer.parseInt(batchNum));
        }


        //批量执行数据同步
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

        piciTasks.forEach(piciTaskVO -> {
            //获取原始数据文件
            byte[] bytes = downFileByteArray(LongGovConstant.HOST_ALIYUN_OSS + piciTaskVO.getOssPath());
            //同步数据文件到COS
            uploadFileToCloudTencent(piciTaskVO, bytes, cosClient, bucketName, dataDay);
            //更新日志表状态信息
            PiciTaskLogAgg.saveQkLogPici(new PiciTaskLogVO(piciTaskVO.getPici(), piciTaskVO.getTableName(), 0, new Date()));
        });

    }

    /**
     * 创建COSClient
     *
     * @return: com.qcloud.cos.COSClient
     **/
    public COSClient getCosClient() {
        //1.初始化用户身份信息（secretId, secretKey）
        String secretId = LongGovConstant.COS_SECRET_ID;
        String secretKey = LongGovConstant.COS_SECRET_KEY;
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        //2.设置bucket的地域
        Region region = new Region(LongGovConstant.COS_REGION);
        ClientConfig clientConfig = new ClientConfig(region);
        clientConfig.setHttpProtocol(HttpProtocol.https);
        //3.生成COS客户端
        return new COSClient(cred, clientConfig);
    }

    /**
     * 创建Bucket
     *
     * @Param: cosClient
     * @return: java.lang.String
     **/
    public String createBucket(COSClient cosClient) {
        //存储桶名称，格式：BucketName-APPID
        String bucketName = "data-center-files-1306026405";
        CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
        //设置 bucket 的权限为 Private(私有读写)、其他可选有 PublicRead（公有读私有写）、PublicReadWrite（公有读写）
        createBucketRequest.setCannedAcl(CannedAccessControlList.Private);
        try {
            Bucket bucketResult = cosClient.createBucket(createBucketRequest);
        } catch (CosServiceException serverException) {
            serverException.printStackTrace();
        } catch (CosClientException clientException) {
            clientException.printStackTrace();
        }
        return bucketName;
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
            objectMetadata.setContentType("application/octet-stream");
            PutObjectResult putObjectResult = cosClient.putObject(bucketName, key, inputStream, objectMetadata);
            // 关闭输入流...
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
