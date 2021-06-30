package com.qk.dam.sqlloader.cos;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.region.Region;
import com.qk.dam.sqlloader.constant.LongGovConstant;

public class TxCOSClient {
  public static COSClient cosClient;

  static {
    cosClient = getCosClient();
  }

  /**
   * 创建COSClient
   *
   * @return: com.qcloud.cos.COSClient
   */
  private static COSClient getCosClient() {
    // 1.初始化用户身份信息（secretId, secretKey）
    String secretId = LongGovConstant.COS_SECRET_ID;
    String secretKey = LongGovConstant.COS_SECRET_KEY;
    COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
    // 2.设置bucket的地域
    Region region = new Region(LongGovConstant.COS_REGION);
    ClientConfig clientConfig = new ClientConfig(region);
    clientConfig.setHttpProtocol(HttpProtocol.https);
    clientConfig.setConnectionTimeout(LongGovConstant.COS_CONNECTION_TIMEOUT);
    clientConfig.setSocketTimeout(LongGovConstant.COS_SOCKET_TIMEOUT);
    // 3.生成COS客户端
    return new COSClient(cred, clientConfig);
  }
}
