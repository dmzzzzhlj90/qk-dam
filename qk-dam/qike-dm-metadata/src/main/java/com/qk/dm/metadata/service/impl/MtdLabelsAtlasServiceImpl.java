package com.qk.dm.metadata.service.impl;

import com.qk.dam.metedata.property.SynchStateProperty;
import com.qk.dm.metadata.entity.MtdLabelsAtlas;
import com.qk.dm.metadata.entity.QMtdLabelsAtlas;
import com.qk.dm.metadata.mapstruct.mapper.MtdLabelsAtlasMapper;
import com.qk.dm.metadata.repositories.MtdLabelsAtlasRepository;
import com.qk.dm.metadata.service.MtdLabelsAtlasService;
import com.qk.dm.metadata.vo.MtdLabelsAtlasBulkVO;
import com.qk.dm.metadata.vo.MtdLabelsAtlasVO;
import com.querydsl.core.types.Predicate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;

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
    update(mtdLabelsAtlasVO);
  }

  @Override
  public void update(MtdLabelsAtlasVO mtdLabelsAtlasVO) {
    MtdLabelsAtlas mtdLabelsAtlas = mtdLabelsAtlasRepository.findByGuid(mtdLabelsAtlasVO.getGuid());
    if (mtdLabelsAtlas != null) {
      if (mtdLabelsAtlasVO.getLabels().isEmpty()) {
        mtdLabelsAtlas.setSynchStatus(SynchStateProperty.LabelsAtlas.DELETE);
      } else {
        mtdLabelsAtlas.setLabels(mtdLabelsAtlasVO.getLabels());
        mtdLabelsAtlas.setSynchStatus(SynchStateProperty.LabelsAtlas.NOT_SYNCH);
      }
    } else {
      mtdLabelsAtlas = MtdLabelsAtlasMapper.INSTANCE.useMtdLabelsAtlas(mtdLabelsAtlasVO);
    }
    mtdLabelsAtlasRepository.saveAndFlush(mtdLabelsAtlas);
  }

  @Override
  public MtdLabelsAtlasVO getByGuid(String guid) {
    Predicate predicate =
        qMtdLabelsAtlas
            .guid
            .eq(guid)
            .and(qMtdLabelsAtlas.synchStatus.ne(SynchStateProperty.LabelsAtlas.DELETE));
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
        byGuid.setSynchStatus(SynchStateProperty.LabelsAtlas.NOT_SYNCH);
        byGuid.setLabels(
            String.join(
                ",",
                Stream.concat(
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

  @Override
  public List<MtdLabelsAtlasVO> getByBulk(List<String> guids) {
    return MtdLabelsAtlasMapper.INSTANCE.useMtdLabelsAtlasListVO(
        mtdLabelsAtlasRepository.findAllByGuidIn(guids));
  }
}
