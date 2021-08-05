package com.qk.dm.metadata.service;

import com.qk.dm.metadata.vo.MtdClassifyVO;
import com.qk.dm.metadata.vo.PageResultVO;
import java.util.List;

/** @author shenpengjie */
public interface MtdClassifyService {
  /**
   * 新增
   *
   * @param mtdClassifyVO
   */
  void insert(MtdClassifyVO mtdClassifyVO);

  /**
   * 修改
   *
   * @param id
   * @param mtdClassifyVO
   */
  void update(Long id, MtdClassifyVO mtdClassifyVO);

  /**
   * 删除
   *
   * @param ids
   */
  void delete(String ids);

  /**
   * 分页查询
   *
   * @param mtdClassifyVO
   * @return
   */
  PageResultVO<MtdClassifyVO> listByPage(MtdClassifyVO mtdClassifyVO);

  /**
   * 列表查询
   *
   * @param mtdClassifyVO
   * @return
   */
  List<MtdClassifyVO> listByAll(MtdClassifyVO mtdClassifyVO);
}
