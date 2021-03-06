package com.qk.dm.authority.service;

import com.qk.dm.authority.vo.user.AtyUserDownVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface EmpExcelService {
  void resourceDownloadTemplate(HttpServletResponse response)
      throws IOException;

  void apiDownloadTemplate(HttpServletResponse response) throws IOException;

  Boolean resourceInfoUpload(MultipartFile file);

  Boolean apiResourceInfoUpload(MultipartFile file);

  void resourcesAllDownload(String serviceId, HttpServletResponse response) throws IOException;

  void apiAllDownload(String serviceId, HttpServletResponse response)
      throws IOException;

  void UserAllDownload(String realm, String search,
      HttpServletResponse response) throws IOException;

  void UserAllDownload(AtyUserDownVO atyUserDownVO, HttpServletResponse response) throws IOException;

  void userDownloadTemplate(HttpServletResponse response) throws IOException;

  Boolean userResourceInfoUpload(MultipartFile file, String realm);
}
