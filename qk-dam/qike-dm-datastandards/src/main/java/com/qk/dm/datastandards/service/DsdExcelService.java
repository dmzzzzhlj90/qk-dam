package com.qk.dm.datastandards.service;

import com.qk.dm.datastandards.vo.DsdBasicinfoVO;
import com.qk.dm.datastandards.vo.DsdCodeTermVO;
import com.qk.dm.datastandards.vo.DsdTermVO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    List<DsdBasicinfoVO> queryAllBasicInfo(String dsdLevelId);

    void basicInfoUpload(MultipartFile file, String dsdLevelId) throws IOException;

    List<DsdCodeTermVO> queryAllCodeTerm(String codeDirId);

    void codeTermUpload(MultipartFile file, String codeDirId) throws IOException;

}
