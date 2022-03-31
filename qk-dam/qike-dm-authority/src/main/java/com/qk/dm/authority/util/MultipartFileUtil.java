package com.qk.dm.authority.util;

import com.alibaba.nacos.common.utils.StringUtils;
import com.qk.dam.authority.common.vo.user.AtyUserInfoVO;
import com.qk.dam.authority.common.vo.user.AtyUserInputExceVO;
import com.qk.dam.commons.exception.BizException;
import com.qk.dm.authority.constant.QxConstant;
import com.qk.dm.authority.entity.QkQxResourcesApi;
import com.qk.dm.authority.entity.QkQxResourcesMenu;
import com.qk.dm.authority.vo.powervo.ResourceApiVO;
import com.qk.dm.authority.vo.powervo.ResourceMenuExcelVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author zys
 * @date 2022/1/5 18:36
 * @since 1.0.0
 */
public class MultipartFileUtil {
  /**
   * @param len  文件长度
   * @param size 限制大小
   * @param unit 限制单位（B,K,M,G）
   * @描述 判断文件大小
   */
  public static boolean checkFileSize(Long len, int size, String unit) {
    double fileSize = 0;
    if ("B".equalsIgnoreCase(unit)) {
      fileSize = (double) len;
    } else if ("K".equalsIgnoreCase(unit)) {
      fileSize = (double) len / 1024;
    } else if ("M".equalsIgnoreCase(unit)) {
      fileSize = (double) len / 1048576;
    } else if ("G".equalsIgnoreCase(unit)) {
      fileSize = (double) len / 1073741824;
    }
    return !(fileSize > size);
  }

  //文件上传调用
  public static void checkFile(MultipartFile file) {
    //校验文件是否为空，且文件名称是否包含待配列基本信息
    if (StringUtils.isEmpty(file.getOriginalFilename())|| !file.getOriginalFilename().contains(
        QxConstant.EXCEL_NAME)){
      throw  new BizException("文件名不规范,请修改后重新上传");
    }
    boolean flag = checkFileSize(file.getSize(), QxConstant.FILE_SIZE, QxConstant.FILE_UNIT);
    if (!flag) {
      throw new BizException("上传文件大小超出限制,当前最大文件接受为:"+QxConstant.FILE_SIZE+QxConstant.FILE_UNIT);
    }
  }

  /**
   * guava根据字段去重(根据资源名称、服务id、资源页面)
   * @param qQxResources
   * @return
   */
  public static String removeDuplicate(QkQxResourcesMenu qQxResources){
    String key = String.join("->",qQxResources.getName(),qQxResources.getServiceId(),qQxResources.getComponent());
    return key;
  }

  /**
   * 根据父级名称、服务id、页面类型拼接key
   * @param qxResources
   * @return
   */
  public static String getKey(ResourceMenuExcelVO qxResources) {
    String key = String.join("->",qxResources.getPidName(),qxResources.getServiceId(),qxResources.getComponent());
    return key;
  }

  /**
   * 创建资源导入key(资源名称、资源服务名称、资源页面)
   * @param qxResources
   * @return
   */
  public static String getExcelKey(ResourceMenuExcelVO qxResources) {
    String key = String.join("->",qxResources.getName(),qxResources.getServiceId(),qxResources.getComponent());
    return key;
  }

  /**
   * 创建添加后资源key（名称、服务id、页面）
   * @param resourceExcelVO
   * @return
   */
  public static String createKey(ResourceMenuExcelVO resourceExcelVO) {
    String key = String.join("->",resourceExcelVO.getName(),resourceExcelVO.getServiceId(),resourceExcelVO.getComponent());
    return key;
  }

  /**
   * 创建api资源key
   * @param resourceVO
   * @return
   */
  public static String getApiExcelKey(ResourceApiVO resourceVO) {
    String key = String.join("->",resourceVO.getName(),resourceVO.getServiceId());
    return key;
  }

  /**
   * 检验导入用户文件
   * @param file
   */
  public static void checkUserFile(MultipartFile file) {
    //校验文件是否为空，且文件名称是否包含待配列基本信息
    if (StringUtils.isEmpty(file.getOriginalFilename())|| !file.getOriginalFilename().contains(
        QxConstant.USER_EXCEL_NAME)){
      throw  new BizException("文件名不规范,请修改后重新上传");
    }
    boolean flag = checkFileSize(file.getSize(), QxConstant.FILE_SIZE, QxConstant.FILE_UNIT);
    if (!flag) {
      throw new BizException("上传文件大小超出限制,当前最大文件接受为:"+QxConstant.FILE_SIZE+QxConstant.FILE_UNIT);
    }
  }

  /**
   * 创建用户存储guava中的key（用户名+域名）
   *
   * @param k 域名称
   * @param atyUserInfoVO
   * @return
   */
  public static String createUserKey(String k, AtyUserInfoVO atyUserInfoVO) {
    String key = String.join("->",k,atyUserInfoVO.getUsername());
    return key;
  }

  /**
   * 根据导入用户信息创建key（用户名+域名）
   * @param atyUserInputExceVO
   * @param relame
   * @return
   */
  public static String getUserExcelKey(AtyUserInputExceVO atyUserInputExceVO,
                                       String relame) {
    String key = String.join("->",relame,atyUserInputExceVO.getUsername());
    return key;
  }

  /**
   * 创建api存储guava中的key（名称、服务id）
   * @param qkQxResourcesApi
   * @return
   */
  public static String createRsApiKey(QkQxResourcesApi qkQxResourcesApi) {
    String key = String.join("->",qkQxResourcesApi.getName(),qkQxResourcesApi.getServiceId());
    return key;
  }
}