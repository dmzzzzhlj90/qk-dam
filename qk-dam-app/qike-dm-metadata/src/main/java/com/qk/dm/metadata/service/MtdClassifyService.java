package com.qk.dm.metadata.service;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.metadata.vo.MtdClassifyListVO;
import com.qk.dm.metadata.vo.MtdClassifyVO;

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
   */
  void delete(MtdClassifyVO mtdClassifyVO);

  /**
   * 分页查询
   *
   * @param mtdClassifyListVO
   * @return
   */
  PageResultVO<MtdClassifyVO> listByPage(MtdClassifyListVO mtdClassifyListVO);

  /**
   * 列表查询
   *
   * @param mtdClassifyVO
   * @return
   */
  List<MtdClassifyVO> listByAll(MtdClassifyVO mtdClassifyVO);
}
