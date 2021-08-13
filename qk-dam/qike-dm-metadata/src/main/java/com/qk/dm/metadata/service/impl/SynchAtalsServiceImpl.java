package com.qk.dm.metadata.service.impl;

import com.qk.dam.metedata.util.AtlasClassificationUtil;
import com.qk.dam.metedata.util.AtlasLabelsUtil;
import com.qk.dm.metadata.entity.MtdClassify;
import com.qk.dm.metadata.entity.MtdClassifyAtlas;
import com.qk.dm.metadata.entity.MtdLabelsAtlas;
import com.qk.dm.metadata.repositories.MtdClassifyAtlasRepository;
import com.qk.dm.metadata.repositories.MtdClassifyRepository;
import com.qk.dm.metadata.repositories.MtdLabelsAtlasRepository;
import com.qk.dm.metadata.service.SynchAtalsService;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.atlas.AtlasServiceException;
import org.springframework.stereotype.Service;

/**
 * @author spj
 * @date 2021/8/4 11:04 上午
 * @since 1.0.0
 */
@Service
public class SynchAtalsServiceImpl implements SynchAtalsService {

  private final MtdLabelsAtlasRepository mtdLabelsAtlasRepository;
  private final MtdClassifyRepository mtdClassifyRepository;
  private final MtdClassifyAtlasRepository mtdClassifyAtlasRepository;

  public SynchAtalsServiceImpl(
      MtdLabelsAtlasRepository mtdLabelsAtlasRepository,
      MtdClassifyRepository mtdClassifyRepository,
      MtdClassifyAtlasRepository mtdClassifyAtlasRepository) {
    this.mtdLabelsAtlasRepository = mtdLabelsAtlasRepository;
    this.mtdClassifyRepository = mtdClassifyRepository;
    this.mtdClassifyAtlasRepository = mtdClassifyAtlasRepository;
  }

  /** 刷新标签绑定 */
  public void synchLabelsAtlas() {
    List<MtdLabelsAtlas> labelAllList =
        mtdLabelsAtlasRepository.findAllBySynchStatusInOrderByGmtCreateAsc(Arrays.asList(-1, 0));
    if (!labelAllList.isEmpty()) {
      List<MtdLabelsAtlas> updateList =
          labelAllList.stream().filter(i -> i.getSynchStatus() == 0).collect(Collectors.toList());
      this.putAtlasLabels(updateList);
      List<MtdLabelsAtlas> deleteList =
          labelAllList.stream().filter(i -> i.getSynchStatus() == -1).collect(Collectors.toList());
      this.delAtlasLabels(deleteList);
    }
  }

  /** 刷新分类 */
  public void synchClassify() {
    List<MtdClassify> mtdClassifyList = mtdClassifyRepository.findAllBySynchStatusIsNot(1);
    if (!mtdClassifyList.isEmpty()) {
      List<MtdClassify> addClassList =
          mtdClassifyList.stream()
              .filter(i -> i.getSynchStatus() == 2)
              .collect(Collectors.toList());
      this.addClassify(addClassList);
      List<MtdClassify> deleteClassList =
          mtdClassifyList.stream()
              .filter(i -> i.getSynchStatus() == -1)
              .collect(Collectors.toList());
      this.delClassify(deleteClassList);
    }
  }

  /** 刷新分类绑定 */
  public void synchClassifyAtlas() {
    List<MtdClassifyAtlas> classifyAtlasList =
        mtdClassifyAtlasRepository.findAllBySynchStatusInOrderByGmtCreateAsc(Arrays.asList(-1, 0));
    if (!classifyAtlasList.isEmpty()) {
      List<MtdClassifyAtlas> updateClassList =
          classifyAtlasList.stream()
              .filter(i -> i.getSynchStatus() == 0)
              .collect(Collectors.toList());
      this.putAtlasClassify(updateClassList);
      List<MtdClassifyAtlas> deleteClassList =
          classifyAtlasList.stream()
              .filter(i -> i.getSynchStatus() == -1)
              .collect(Collectors.toList());
      this.delAtlasClassify(deleteClassList);
    }
  }

  private void putAtlasLabels(List<MtdLabelsAtlas> updateList) {
    if (!updateList.isEmpty()) {
      updateList.forEach(
          mtdLabelsAtlas -> {
            try {
              AtlasLabelsUtil.setLabels(mtdLabelsAtlas.getGuid(), mtdLabelsAtlas.getLabels());
              mtdLabelsAtlas.setSynchStatus(1);
            } catch (AtlasServiceException e) {
              // 降级处理
              e.printStackTrace();
            }
          });
      mtdLabelsAtlasRepository.saveAll(updateList);
    }
  }

  private void delAtlasLabels(List<MtdLabelsAtlas> deleteList) {
    if (!deleteList.isEmpty()) {
      Iterator<MtdLabelsAtlas> itr = deleteList.iterator();
      while (itr.hasNext()) {
        try {
          AtlasLabelsUtil.removeLabels(itr.next().getGuid());
        } catch (AtlasServiceException e) {
          e.printStackTrace();
          // todo 降级处理
          itr.remove();
        }
      }
      mtdLabelsAtlasRepository.deleteAll(deleteList);
    }
  }

  private void addClassify(List<MtdClassify> addClassList) {
    if (!addClassList.isEmpty()) {
      try {
        Map<String, String> addClassMap =
            addClassList.stream()
                .collect(Collectors.toMap(MtdClassify::getName, MtdClassify::getDescription));
        AtlasLabelsUtil.addTypedefs(addClassMap);
        mtdClassifyRepository.saveAll(
            addClassList.stream().peek(i -> i.setSynchStatus(1)).collect(Collectors.toList()));
      } catch (AtlasServiceException e) {
        // 降级处理
        e.printStackTrace();
      }
    }
  }

  private void delClassify(List<MtdClassify> deleteList) {
    if (!deleteList.isEmpty()) {
      Iterator<MtdClassify> itr = deleteList.iterator();
      while (itr.hasNext()) {
        String name = itr.next().getName();
        try {
          AtlasClassificationUtil.delEntitiesClassis(name);
          AtlasClassificationUtil.delClassi(name);
        } catch (AtlasServiceException e) {
          e.printStackTrace();
          // todo 降级处理
          itr.remove();
        }
      }
      mtdClassifyRepository.deleteAll(deleteList);
    }
  }

  private void putAtlasClassify(List<MtdClassifyAtlas> updateClassList) {
    if (!updateClassList.isEmpty()) {
      updateClassList.forEach(
          mtdClassifyAtlas -> {
            try {
              AtlasLabelsUtil.upEntitiesClassis(
                  mtdClassifyAtlas.getGuid(), mtdClassifyAtlas.getClassify());
              mtdClassifyAtlas.setSynchStatus(1);
            } catch (AtlasServiceException e) {
              // 降级处理
              e.printStackTrace();
            }
          });
      mtdClassifyAtlasRepository.saveAll(updateClassList);
    }
  }

  private void delAtlasClassify(List<MtdClassifyAtlas> deleteList) {
    if (!deleteList.isEmpty()) {
      Iterator<MtdClassifyAtlas> itr = deleteList.iterator();
      while (itr.hasNext()) {
        try {
          AtlasLabelsUtil.delEntitiesClassis(itr.next().getGuid());
        } catch (AtlasServiceException e) {
          e.printStackTrace();
          // todo 降级处理
          itr.remove();
        }
      }
      mtdClassifyAtlasRepository.deleteAll(deleteList);
    }
  }
}
