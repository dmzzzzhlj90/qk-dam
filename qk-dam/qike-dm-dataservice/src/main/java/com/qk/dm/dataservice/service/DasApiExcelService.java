package com.qk.dm.dataservice.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 数据服务__Excel数据迁移功能
 *
 * @author wjq
 * @date 2021/04/18
 * @since 1.0.0
 */
@Service
public interface DasApiExcelService {

  void apiDataUpload(MultipartFile file, String dirId);

  void apiDataDownload(String dirId) throws IOException;

}
