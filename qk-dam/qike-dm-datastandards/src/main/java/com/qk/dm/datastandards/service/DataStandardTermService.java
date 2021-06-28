package com.qk.dm.datastandards.service;

import com.qk.dm.datastandards.vo.DsdTermVO;
import com.qk.dm.datastandards.vo.PageResultVO;
import com.qk.dm.datastandards.vo.Pagination;
import org.springframework.stereotype.Service;

/**
 * @author wjq
 * @date 20210604
 * @since 1.0.0 数据标准业务术语接口
 */
@Service
public interface DataStandardTermService {

  PageResultVO<DsdTermVO> getDsdTerm(Pagination pagination);

  void addDsdTerm(DsdTermVO dsdTermVO);

  void updateDsdTerm(DsdTermVO dsdTermVO);

  void deleteDsdTerm(Integer id);
}
