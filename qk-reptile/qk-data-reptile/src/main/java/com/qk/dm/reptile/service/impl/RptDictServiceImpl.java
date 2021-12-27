package com.qk.dm.reptile.service.impl;

import com.qk.dm.reptile.mapstruct.mapper.RptDictMapper;
import com.qk.dm.reptile.params.vo.RptDictVO;
import com.qk.dm.reptile.repositories.RptDictRepository;
import com.qk.dm.reptile.service.RptDictService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 字典信息（省市区）
 *
 * @author wangzp
 * @date 2021/12/24 10:16
 * @since 1.0.0
 */
@Service
public class RptDictServiceImpl implements RptDictService {

  private final RptDictRepository rptDictRepository;

  public RptDictServiceImpl(RptDictRepository rptDictRepository) {
    this.rptDictRepository = rptDictRepository;
  }

  @Override
  public List<RptDictVO> getDictList(Long pid) {
    List<RptDictVO> list = RptDictMapper.INSTANCE.of(rptDictRepository.findAll());

    return list.stream()
        .filter(dict -> dict.getPid() == 0)
        .peek(dict -> dict.setChildren(getChildren(dict, list)))
        .collect(Collectors.toList());
  }

  /**
   * 递归查询子节点
   *
   * @param vo 父节点
   * @param all 所有节点
   * @return 根节点信息
   */
  private List<RptDictVO> getChildren(RptDictVO vo, List<RptDictVO> all) {
    return all.stream()
        .filter(e -> Objects.equals(e.getPid(), vo.getId()))
        .peek((e) -> e.setChildren(getChildren(e, all)))
        .collect(Collectors.toList());
  }
}
