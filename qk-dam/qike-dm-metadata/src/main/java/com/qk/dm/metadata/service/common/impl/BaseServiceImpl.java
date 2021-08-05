package com.qk.dm.metadata.service.common.impl;

import com.qk.dam.jpa.base.BaseRepository;
import com.qk.dam.jpa.pojo.Pagination;
import com.qk.dm.metadata.service.common.BaseService;
import com.qk.dm.metadata.service.common.query.Criteria;
import com.qk.dm.metadata.vo.PageResultVO;
import java.util.*;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

/**
 * @author wangzp
 * @date 2021/7/30 17:29
 * @since 1.0.0
 */
public class BaseServiceImpl<M extends BaseRepository<T, Long>, T> implements BaseService<T> {

  @Autowired private M repository;

  @Override
  public void insert(T t) {
    repository.save(t);
  }

  @Override
  public void saveAll(List<T> list) {
    repository.saveAll(list);
  }

  @Override
  public void update(T t) {
    repository.saveAndFlush(t);
  }

  @Override
  public void updateAll(List<T> records) {}

  @Override
  @Transactional
  public void delete(String ids) {
    List<String> idList = Arrays.asList(ids.split(","));
    Iterable<Long> idSet = idList.stream().map(i -> Long.valueOf(i)).collect(Collectors.toList());
    List<T> basicInfoList = repository.findAllById(idSet);
    repository.deleteInBatch(basicInfoList);
  }

  @Override
  public PageResultVO<T> findListByPage(Criteria<T> criteria, Pagination pagination) {
    Page<T> pageList = repository.findAll(criteria, pagination.getPageable());
    return new PageResultVO<T>(
        pageList.getNumber(), pagination.getPage(), pagination.getSize(), pageList.getContent());
  }

  @Override
  public List<T> findList(Criteria<T> criteria) {
    return repository.findAll(criteria);
  }

  @Override
  public List<T> findListBySort(Criteria<T> criteria, Sort sort) {
    return repository.findAll(criteria, sort);
  }

  public M getRepository() {
    return repository;
  }
}
