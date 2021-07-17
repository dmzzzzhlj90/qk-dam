package com.qk.dm.datastandards.service;

import com.qk.dm.datastandards.vo.DsdCodeTermVO;
import com.qk.dm.datastandards.vo.PageResultVO;
import com.qk.dm.datastandards.vo.Pagination;
import com.qk.dm.datastandards.vo.params.DsdCodeTermParamsVO;
import org.springframework.stereotype.Service;

/**
 * @author wjq
 * @date 20210604
 * @since 1.0.0 数据标准标准代码术语接口
 */
@Service
public interface DataStandardCodeTermService {

  PageResultVO<DsdCodeTermVO> getDsdCodeTerm(DsdCodeTermParamsVO dsdCodeTermParamsVO);

  void addDsdCodeTerm(DsdCodeTermVO dsdCodeTermVO);

  void updateDsdCodeTerm(DsdCodeTermVO dsdCodeTermVO);

  void deleteDsdCodeTerm(Integer id);

  void bulkDeleteDsdTerm(String ids);

}
