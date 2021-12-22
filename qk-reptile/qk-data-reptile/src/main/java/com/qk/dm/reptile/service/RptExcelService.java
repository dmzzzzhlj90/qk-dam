package com.qk.dm.reptile.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public interface RptExcelService {
  void basicInfoDownloadTemplate(HttpServletResponse response)
      throws IOException;

  void basicInfoUpload(MultipartFile file);
}
