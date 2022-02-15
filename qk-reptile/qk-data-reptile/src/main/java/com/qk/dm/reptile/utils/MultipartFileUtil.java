package com.qk.dm.reptile.utils;

import com.alibaba.nacos.common.utils.StringUtils;
import com.qk.dam.commons.exception.BizException;
import com.qk.dm.reptile.constant.RptConstant;
import com.qk.dm.reptile.entity.RptBaseInfo;
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
        RptConstant.EXCEL_NAME)){
      throw  new BizException("文件名不规范,请修改后重新上传");
    }
    boolean flag = checkFileSize(file.getSize(), RptConstant.FILE_SIZE, RptConstant.FILE_UNIT);
    if (!flag) {
      throw new BizException("上传文件大小超出限制,当前最大文件接受为:"+RptConstant.FILE_SIZE+RptConstant.FILE_UNIT);
    }
  }

  /**
   * 根据字段去重
   * @param rptBaseInfo
   * @return
   */
  public static String removeDuplicate(RptBaseInfo rptBaseInfo){
    String key = String.join("->",rptBaseInfo.getWebsiteUrl(),rptBaseInfo.getSecondSiteType(),rptBaseInfo.getListPageAddress());
    if(StringUtils.isNotBlank(rptBaseInfo.getWebsiteNameCorrection())){
      key = String.join("->",key, rptBaseInfo.getWebsiteNameCorrection());
    }
    if(StringUtils.isNotBlank(rptBaseInfo.getWebsiteUrlCorrection())){
      key = String.join("->",key, rptBaseInfo.getWebsiteUrlCorrection());
    }
    return key;
  }
}