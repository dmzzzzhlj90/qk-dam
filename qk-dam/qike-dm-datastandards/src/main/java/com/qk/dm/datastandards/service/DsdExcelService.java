package com.qk.dm.datastandards.service;

import com.qk.dm.datastandards.vo.DsdBasicinfoVO;
import com.qk.dm.datastandards.vo.DsdCodeInfoVO;
import java.util.List;
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

  List<DsdBasicinfoVO> queryBasicInfos(String dirDsdId);

  void basicInfoUpload(MultipartFile file, String dirDsdId);

  List<String> findAllDsdDirLevel();

  List<String> findAllDsdCodeDirLevel();

  List<DsdBasicinfoVO> dsdBasicInfoSampleData();

  void codeValuesDownloadByCodeInfoId(HttpServletResponse response, Long dsdCodeInfoId);

  void codeValuesUploadByCodeInfoId(MultipartFile file, long dsdCodeInfoId);

  void codeValuesDownloadTemplate(HttpServletResponse response, long dsdCodeInfoId);

  List<DsdCodeInfoVO> codeInfoAllDownload(String codeDirId);

  void codeInfoAllUpload(MultipartFile file, String codeDirId);
}
