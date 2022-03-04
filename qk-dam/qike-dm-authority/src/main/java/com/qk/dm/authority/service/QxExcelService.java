package com.qk.dm.authority.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface QxExcelService {
  void resourceDownloadTemplate(HttpServletResponse response)
      throws IOException;

  void apiDownloadTemplate(HttpServletResponse response) throws IOException;

  Boolean resourceInfoUpload(MultipartFile file);

  Boolean apiResourceInfoUpload(MultipartFile file);

  void resourcesAllDownload(Long serviceId, HttpServletResponse response) throws IOException;

  void apiAllDownload(Long serviceId, HttpServletResponse response)
      throws IOException;

  void UserAllDownload(String realm, String search,
      HttpServletResponse response) throws IOException;

  void userDownloadTemplate(HttpServletResponse response) throws IOException;

  Boolean userResourceInfoUpload(MultipartFile file, String realm);
}
