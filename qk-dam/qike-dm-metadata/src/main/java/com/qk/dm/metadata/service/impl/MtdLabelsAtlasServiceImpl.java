package com.qk.dm.metadata.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dm.metadata.entity.MtdLabelsAtlas;
import com.qk.dm.metadata.entity.QMtdLabelsAtlas;
import com.qk.dm.metadata.mapstruct.mapper.MtdLabelsAtlasMapper;
import com.qk.dm.metadata.repositories.MtdLabelsAtlasRepository;
import com.qk.dm.metadata.service.MtdLabelsAtlasService;
import com.qk.dm.metadata.vo.MtdLabelsAtlasBulkVO;
import com.qk.dm.metadata.vo.MtdLabelsAtlasVO;
import com.querydsl.core.types.Predicate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author spj
 * @date 2021/7/31 3:04 下午
 * @since 1.0.0
 */
@Service
public class MtdLabelsAtlasServiceImpl implements MtdLabelsAtlasService {
  private final QMtdLabelsAtlas qMtdLabelsAtlas = QMtdLabelsAtlas.mtdLabelsAtlas;
  private final MtdLabelsAtlasRepository mtdLabelsAtlasRepository;

  public MtdLabelsAtlasServiceImpl(MtdLabelsAtlasRepository mtdLabelsAtlasRepository) {
    this.mtdLabelsAtlasRepository = mtdLabelsAtlasRepository;
  }

  @Override
  public void insert(MtdLabelsAtlasVO mtdLabelsAtlasVO) {
    Predicate predicate =
        qMtdLabelsAtlas.guid.eq(mtdLabelsAtlasVO.getGuid()).and(qMtdLabelsAtlas.synchStatus.ne(-1));
    if (mtdLabelsAtlasRepository.exists(predicate)) {
      throw new BizException("当前要绑定标签的元数据为：" + mtdLabelsAtlasVO.getGuid() + " 的数据，已存在！！！");
    }
    MtdLabelsAtlas mtdLabelsAtlas =
        MtdLabelsAtlasMapper.INSTANCE.useMtdLabelsAtlas(mtdLabelsAtlasVO);
    mtdLabelsAtlasRepository.save(mtdLabelsAtlas);
  }

  @Override
  public void update(Long id, MtdLabelsAtlasVO mtdLabelsAtlasVO) {
    Predicate predicate =
        qMtdLabelsAtlas
            .id
            .eq(id)
            .and(qMtdLabelsAtlas.guid.eq(mtdLabelsAtlasVO.getGuid()))
            .and(qMtdLabelsAtlas.synchStatus.ne(-1));
    MtdLabelsAtlas mtdLabelsAtlas = mtdLabelsAtlasRepository.findOne(predicate).orElse(null);
    if (mtdLabelsAtlas == null) {
      throw new BizException("当前要绑定标签的元数据为：" + mtdLabelsAtlasVO.getGuid() + " 的数据不存在！！！");
    }
    if (mtdLabelsAtlasVO.getLabels().isEmpty()) {
      mtdLabelsAtlas.setSynchStatus(-1);
    } else {
      mtdLabelsAtlas.setLabels(mtdLabelsAtlasVO.getLabels());
      mtdLabelsAtlas.setSynchStatus(0);
    }
    mtdLabelsAtlasRepository.saveAndFlush(mtdLabelsAtlas);
  }

  @Override
  public MtdLabelsAtlasVO getByGuid(String guid) {
    Predicate predicate = qMtdLabelsAtlas.guid.eq(guid).and(qMtdLabelsAtlas.synchStatus.ne(-1));
    Optional<MtdLabelsAtlas> one = mtdLabelsAtlasRepository.findOne(predicate);
    return one.map(MtdLabelsAtlasMapper.INSTANCE::useMtdLabelsAtlasVO).orElse(null);
  }

  @Override
  public void bulk(MtdLabelsAtlasBulkVO mtdLabelsVO) {
    List<MtdLabelsAtlas> labelsAtlasList = new ArrayList<>();
    List<String> guidList = Arrays.asList(mtdLabelsVO.getGuids());
    Map<String, MtdLabelsAtlas> labelsAtlasMap =
        mtdLabelsAtlasRepository.findAllByGuidIn(guidList).stream()
            .collect(Collectors.toMap(MtdLabelsAtlas::getGuid, MtdLabelsAtlas -> MtdLabelsAtlas));
    for (String guid : guidList) {
      MtdLabelsAtlas byGuid;
      if (labelsAtlasMap.get(guid) != null) {
        byGuid = labelsAtlasMap.get(guid);
        byGuid.setSynchStatus(0);
        byGuid.setLabels(
                String.join(",", Stream.concat(
                                Arrays.stream(byGuid.getLabels().split(",")),
                                Arrays.stream(mtdLabelsVO.getLabels().split(",")))
                        .collect(Collectors.toSet())));
      } else {
        byGuid = new MtdLabelsAtlas();
        byGuid.setGuid(guid);
        byGuid.setLabels(mtdLabelsVO.getLabels());
      }
      labelsAtlasList.add(byGuid);
    }
    mtdLabelsAtlasRepository.saveAll(labelsAtlasList);
  }
}
