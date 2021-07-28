package com.qk.dm.datastandards.service;

import com.qk.dm.datastandards.vo.DsdBasicinfoVO;
import com.qk.dm.datastandards.vo.DsdCodeTermVO;
import com.qk.dm.datastandards.vo.DsdTermVO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

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

    List<DsdCodeTermVO> queryCodeTerms(String codeDirId);

    List<DsdBasicinfoVO> queryBasicInfos(String dirDsdId);

    void basicInfoUpload(MultipartFile file, String dirDsdId) throws IOException;

    void codeTermUpload(MultipartFile file, String codeDirId) throws IOException;

    List<String> findAllDsdDirLevel();

    List<String> findAllDsdCodeDirLevel();

    List<DsdBasicinfoVO> dsdBasicInfoSampleData();

    List<DsdCodeTermVO> dsdCodeTermSampleData();

    void codeValuesDownloadByCodeInfoId(HttpServletResponse response, Long dsdCodeInfoId);

}
