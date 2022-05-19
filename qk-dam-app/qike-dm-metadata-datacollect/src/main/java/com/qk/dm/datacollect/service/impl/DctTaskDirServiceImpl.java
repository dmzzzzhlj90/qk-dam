package com.qk.dm.datacollect.service.impl;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dam.jpa.pojo.Pagination;
import com.qk.dm.datacollect.entity.DctTaskDir;
import com.qk.dm.datacollect.entity.QDctTaskDir;
import com.qk.dm.datacollect.mapstruct.DctTaskDirTreeMapper;
import com.qk.dm.datacollect.repositories.DctTaskDirRepository;
import com.qk.dm.datacollect.service.DctDolphinService;
import com.qk.dm.datacollect.service.DctTaskDirService;
import com.qk.dm.datacollect.vo.DctSchedulerInfoParamsVO;
import com.qk.dm.datacollect.vo.DctSchedulerInfoVO;
import com.qk.dm.datacollect.vo.DctTaskDirTreeVO;
import com.qk.dm.datacollect.vo.DctTaskDirVO;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zys
 * @date 2022/4/27 15:34
 * @since 1.0.0
 */
@Service
public class DctTaskDirServiceImpl implements DctTaskDirService {
  private final DctDolphinService dctDolphinService;
  private final QDctTaskDir qdctTaskDir = QDctTaskDir.dctTaskDir;
  private final DctTaskDirRepository dctTaskDirRepository;

  public DctTaskDirServiceImpl(DctDolphinService dctDolphinService,
      DctTaskDirRepository dctTaskDirRepository) {
    this.dctDolphinService = dctDolphinService;
    this.dctTaskDirRepository = dctTaskDirRepository;
  }

  @Override
  public void insert(DctTaskDirVO dctTaskDirVO) {
    DctTaskDir dctTaskDir = DctTaskDirTreeMapper.INSTANCE.useDqcRuleDir(dctTaskDirVO);
    dctTaskDir.setGmtCreate(new Date());
    dctTaskDir.setGmtModified(new Date());
    dctTaskDir.setRuleDirId(UUID.randomUUID().toString().replaceAll("-", ""));
    dctTaskDir.setDelFlag(0);

    Predicate predicate = qdctTaskDir.ruleDirName.eq(dctTaskDirVO.getTitle()).and(qdctTaskDir.parentId.eq(dctTaskDirVO.getParentId()));
    boolean exists = dctTaskDirRepository.exists(predicate);
    if (exists) {
      throw new BizException("当前要新增的任务目录名称为:" + dctTaskDirVO.getTitle() + " 的数据，在本层级下已存在！！！");
    }
    dctTaskDirRepository.save(dctTaskDir);

  }

  @Override
  public void update(DctTaskDirVO dctTaskDirVO) {
    //校验目录dirId是否与ParentId相等
    checkDirIdIsEqualParentId(dctTaskDirVO);

    //校验目录父节点是否放到其子节点层级下
    checkParentNodeAndChildNode(dctTaskDirVO);

    DctTaskDir dctTaskDir = DctTaskDirTreeMapper.INSTANCE.useDqcRuleDir(dctTaskDirVO);
    dctTaskDir.setGmtModified(new Date());
    dctTaskDir.setDelFlag(0);

    Optional<DctTaskDir> dsdDirOptional = dctTaskDirRepository.findById(dctTaskDirVO.getId());
    if (dsdDirOptional.isPresent()) {
      dctTaskDirRepository.saveAndFlush(dctTaskDir);
      //TODO 修改目录后影响的列表信息
      //            dsdBasicinfoRepository.updateDirLevelByDirId(dsdDirVO.getDsdDirLevel(), dsdDirVO.getDirDsdId());
    } else {
      throw new BizException("当前要编辑的规则分类目录名称为:" + dctTaskDirVO.getTitle() + " 的数据，不存在！！！");
    }
  }

  @Override
  public void delete(String id) {
    ArrayList<Long> ids = new ArrayList<>();
    List<DctTaskDir> childDirList = new ArrayList<>();
    List<DctTaskDir> dirIsExistRulesList = new ArrayList<>();

    Long delId = Long.valueOf(id);
    // 删除父级ID
    Predicate predicate = QDctTaskDir.dctTaskDir.delFlag.eq(0);
    List<DctTaskDir> dqcRuleDirList = (List<DctTaskDir>) dctTaskDirRepository.findAll(predicate);
    List<DctTaskDir> dsdDirIsExist = dqcRuleDirList.stream().filter(dqcRuleDir -> dqcRuleDir.getId().equals(delId)).collect(Collectors.toList());
    if (dsdDirIsExist.size() < 1) {
      throw new BizException("参数有误,当前要删除的节点不存在！！！");
    }

    //级联所以子节点
    DctTaskDir dqcRuleDir = dsdDirIsExist.get(0);
    ids.add(delId);
    getDirIdsByParentId(dqcRuleDirList, dqcRuleDir.getRuleDirId(), childDirList);
    childDirList.forEach((ruleDir) -> ids.add(ruleDir.getId()));

    //校验是否存在规则模板数据
    Iterable<DctTaskDir> delDirList = dctTaskDirRepository.findAll(qdctTaskDir.id.in(ids));
    for (DctTaskDir dctTaskDir : delDirList) {
      dirIsExistRulesList.add(dctTaskDir);
    }
    //todo 校验当前删除的目录是服存在任务，如果存在就提示不让删除
    deleteCheckIsRules(dirIsExistRulesList);
    // 级联删除
    dctTaskDirRepository.deleteAll(delDirList);
  }
  //检验当前删除的目录是否存在任务或任务监控
  private void deleteCheckIsRules(List<DctTaskDir> dctTaskDirList) {
    if (CollectionUtils.isNotEmpty(dctTaskDirList)){
      DctSchedulerInfoParamsVO dctSchedulerInfoParamsVO = new DctSchedulerInfoParamsVO();
      Pagination pagination = new Pagination();
      pagination.setPage(1);
      pagination.setSize(10);
      pagination.setSortField("1rex5r");
      dctSchedulerInfoParamsVO.setPagination(pagination);
      dctTaskDirList.forEach(dctTaskDir->{
        dctSchedulerInfoParamsVO.setDirId(String.valueOf(dctTaskDir.getRuleDirId()));
        PageResultVO<DctSchedulerInfoVO> dctSchedulerInfoVOPageResultVO = dctDolphinService.searchPageList(dctSchedulerInfoParamsVO);
        if (CollectionUtils.isNotEmpty(dctSchedulerInfoVOPageResultVO.getList())){
          throw  new BizException("当前删除的目录或其子目录下存在任务流程,请删除任务流程后再进行删除操作");
        }
      });
    }
  }

  @Override
  public void deleteBulk(String ids) {
    List<String> idList = Arrays.asList(ids.split(","));
    Set<Long> idSet = new HashSet<>();
    idList.forEach(id -> idSet.add(Long.valueOf(id)));
    List<DctTaskDir> dctTaskDirList = dctTaskDirRepository.findAllById(idSet);
    //todo 校验当前删除的目录是服存在任务，如果存在就提示不让删除
    deleteCheckIsRules(dctTaskDirList);
    // 批量删除
    dctTaskDirRepository.deleteAll(dctTaskDirList);
  }

  @Override
  public List<DctTaskDirTreeVO> searchList() {
    Predicate predicate = QDctTaskDir.dctTaskDir.delFlag.eq(0);
    List<DctTaskDir> dqcRuleDirList = (List<DctTaskDir>) dctTaskDirRepository.findAll(predicate);
    List<DctTaskDirTreeVO> respList = new ArrayList<>();
    for (DctTaskDir dqcRuleDir : dqcRuleDirList) {
      DctTaskDirTreeVO dirTreeVO = DctTaskDirTreeMapper.INSTANCE.useDqcRuleDirTreeVO(dqcRuleDir);
      respList.add(dirTreeVO);
    }
    return buildByRecursive(respList);
  }

  private List<DctTaskDirTreeVO> buildByRecursive(List<DctTaskDirTreeVO> respList) {
    DctTaskDirTreeVO topParent = DctTaskDirTreeVO.builder().dirId("-1").title("任务").value("任务").parentId("-1").build();
    List<DctTaskDirTreeVO> trees = new ArrayList<>();
    trees.add(findChildren(topParent, respList));
    return trees;
  }

  private DctTaskDirTreeVO findChildren(DctTaskDirTreeVO topParent, List<DctTaskDirTreeVO> respList) {
    topParent.setChildren(new ArrayList<>());
    for (DctTaskDirTreeVO dqcRuleDirTreeVO : respList) {
      if (topParent.getDirId().equals(dqcRuleDirTreeVO.getParentId())) {
        if (topParent.getChildren() == null) {
          topParent.setChildren(new ArrayList<>());
        }
        topParent.getChildren().add(findChildren(dqcRuleDirTreeVO, respList));
      }
    }
    return topParent;
  }

  private void checkParentNodeAndChildNode(DctTaskDirVO dctTaskDirVO) {
    BooleanExpression predicate = QDctTaskDir.dctTaskDir.delFlag.eq(0);
    List<DctTaskDir> dctTaskDirList = (List<DctTaskDir>) dctTaskDirRepository.findAll(predicate);
    List<DctTaskDir> childDirList = new ArrayList<>();
    getDirIdsByParentId(dctTaskDirList, dctTaskDirVO.getDirId(), childDirList);
    List<String> childDirIds = childDirList.stream().map(DctTaskDir::getRuleDirId).collect(
        Collectors.toList());
    if (childDirIds.contains(dctTaskDirVO.getParentId())) {
      throw new BizException("当前要编辑的规则分类目录,不能选择自身子节点作为父节点！！！");
    }
  }

  private void getDirIdsByParentId(List<DctTaskDir> dctTaskDirList, String dirId, List<DctTaskDir> childDirList) {
    for (DctTaskDir dctTaskDir : dctTaskDirList) {
      if (dctTaskDir.getParentId() != null) {
        if (dctTaskDir.getParentId().equals(dirId)) {
          getDirIdsByParentId(dctTaskDirList, dctTaskDir.getRuleDirId(), childDirList);
          childDirList.add(dctTaskDir);
        }
      }
    }
  }

  private void checkDirIdIsEqualParentId(DctTaskDirVO dctTaskDirVO) {
    String dirId = dctTaskDirVO.getDirId();
    String parentId = dctTaskDirVO.getParentId();
    if (dirId.equals(parentId)) {
      throw new BizException("当前要编辑的规则分类目录,不能选择本节点作为父节点！！！");
    }
  }
}