package com.qk.dm.datastandards.service;

import com.qk.dm.datastandards.vo.DsdBasicinfoVO;
import com.qk.dm.datastandards.vo.DsdTermVO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 数据标准excel导入导出
 *
 * @author wjq
 * @date 2021/6/5 17:42
 * @since 1.0.0
 */
@Service
public interface DsdExcelService {

    List<DsdTermVO> queryAllTerm();

    void termUpload(MultipartFile file) throws IOException;

    List<DsdBasicinfoVO> queryBasicInfos(String dirDsdId);

    void basicInfoUpload(MultipartFile file, String dirDsdId) throws IOException;

    List<String> findAllDsdDirLevel();

    List<String> findAllDsdCodeDirLevel();

    List<DsdBasicinfoVO> dsdBasicInfoSampleData();

    void codeValuesDownloadByCodeInfoId(HttpServletResponse response, Long dsdCodeInfoId);

    void codeValuesUploadByCodeInfoId(MultipartFile file, long dsdCodeInfoId);
}
