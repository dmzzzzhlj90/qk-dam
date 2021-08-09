package com.qk.dm.metadata.service;

import com.qk.dm.metadata.vo.MtdClassifyInfoVO;
import com.qk.dm.metadata.vo.MtdClassifyListVO;
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
   * @param mtdClassifyListVO
   * @return
   */
  PageResultVO<MtdClassifyInfoVO> listByPage(MtdClassifyListVO mtdClassifyListVO);

  /**
   * 列表查询
   *
   * @param mtdClassifyVO
   * @return
   */
  List<MtdClassifyInfoVO> listByAll(MtdClassifyVO mtdClassifyVO);
}
