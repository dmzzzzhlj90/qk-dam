package com.qk.dm.datastandards.service;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.datastandards.vo.CodeTableFieldsVO;
import com.qk.dm.datastandards.vo.DsdBasicInfoParamsVO;
import com.qk.dm.datastandards.vo.DsdBasicInfoVO;

import java.util.List;
import org.springframework.stereotype.Service;

/**
 * @author wjq
 * @date 20210604
 * @since 1.0.0 数据标准标准信息接口
 */
@Service
public interface DataStandardBasicInfoService {

  PageResultVO<DsdBasicInfoVO> getDsdBasicInfo(DsdBasicInfoParamsVO basicInfoParamsVO);

  void addDsdBasicinfo(DsdBasicInfoVO dsdBasicinfoVO);

  void updateDsdBasicinfo(DsdBasicInfoVO dsdBasicinfoVO);

  void deleteDsdBasicinfo(Integer id);

  void bulkDeleteDsdBasicInfo(String ids);

  List<CodeTableFieldsVO> getCodeFieldByCodeDirId(String codeDirId);
}
