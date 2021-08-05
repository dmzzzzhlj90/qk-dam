package com.qk.dm.metadata.service.common;

import com.qk.dam.jpa.pojo.Pagination;
import com.qk.dm.metadata.service.common.query.Criteria;
import com.qk.dm.metadata.vo.PageResultVO;
import java.util.List;
import org.springframework.data.domain.Sort;

public interface BaseService<T> {
  void insert(T record);

  void saveAll(List<T> records);

  void update(T record);

  void updateAll(List<T> records);

  void delete(String ids);

  PageResultVO<T> findListByPage(Criteria<T> criteria, Pagination pagination);

  List<T> findList(Criteria<T> criteria);

  List<T> findListBySort(Criteria<T> criteria, Sort sort);
}
