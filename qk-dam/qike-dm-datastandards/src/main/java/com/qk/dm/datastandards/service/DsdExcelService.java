package com.qk.dm.datastandards.service;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * 数据标准excel导入导出
 *
 * @author wjq
 * @date 2021/6/5 17:42
 * @since 1.0.0
 */
@Service
public interface DsdExcelService {

  //  ================================basicInfo===============================================
  void basicInfoUpload(MultipartFile file, String dirDsdId);

  void basicInfoDownloadAll(HttpServletResponse response) throws IOException;

  void basicInfoDownloadByDirDsdId(String dirDsdId, HttpServletResponse response)
      throws IOException;

  void basicInfoDownloadTemplate(HttpServletResponse response) throws IOException;

  //  ================================codeInfo===============================================
  void codeInfoAllUpload(MultipartFile file, String codeDirId);

  void codeInfoAllDownload(String codeDirId, HttpServletResponse response) throws IOException;

  void codeInfoDownloadTemplate(HttpServletResponse response) throws IOException;

  //  ================================codeValues===============================================
  void codeValuesUploadByCodeInfoId(MultipartFile file, long dsdCodeInfoId);

  void codeValuesDownloadByCodeInfoId(HttpServletResponse response, Long dsdCodeInfoId);

  void codeValuesDownloadTemplate(HttpServletResponse response, long dsdCodeInfoId);
}
