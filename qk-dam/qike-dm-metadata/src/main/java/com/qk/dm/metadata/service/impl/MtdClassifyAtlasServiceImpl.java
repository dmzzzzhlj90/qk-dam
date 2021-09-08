package com.qk.dm.metadata.service.impl;

import com.qk.dam.metedata.property.SynchStateProperty;
import com.qk.dm.metadata.entity.MtdClassifyAtlas;
import com.qk.dm.metadata.entity.QMtdClassifyAtlas;
import com.qk.dm.metadata.mapstruct.mapper.MtdClassifyAtlasMapper;
import com.qk.dm.metadata.repositories.MtdClassifyAtlasRepository;
import com.qk.dm.metadata.service.MtdClassifyAtlasService;
import com.qk.dm.metadata.vo.MtdClassifyAtlasVO;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author wangzp
 * @date 2021/7/31 11:37
 * @since 1.0.0
 */
@Service
public class MtdClassifyAtlasServiceImpl implements MtdClassifyAtlasService {
  private final QMtdClassifyAtlas qMtdClassifyAtlas = QMtdClassifyAtlas.mtdClassifyAtlas;
  private final MtdClassifyAtlasRepository mtdClassifyAtlasRepository;

  @Autowired
  public MtdClassifyAtlasServiceImpl(MtdClassifyAtlasRepository mtdClassifyAtlasRepository) {
    this.mtdClassifyAtlasRepository = mtdClassifyAtlasRepository;
  }

  @Override
  public void insert(MtdClassifyAtlasVO mtdClassifyAtlasVO) {
    update(mtdClassifyAtlasVO);
  }

  @Override
  public void update(MtdClassifyAtlasVO mtdClassifyAtlasVO) {
    MtdClassifyAtlas mtdClassifyAtlas =
        mtdClassifyAtlasRepository.findByGuid(mtdClassifyAtlasVO.getGuid());
    if (mtdClassifyAtlas != null) {
      if (mtdClassifyAtlasVO.getClassify().isEmpty()) {
        mtdClassifyAtlas.setSynchStatus(SynchStateProperty.ClassifyAtlas.DELETE);
      } else {
        mtdClassifyAtlas.setClassify(mtdClassifyAtlasVO.getClassify());
        mtdClassifyAtlas.setSynchStatus(SynchStateProperty.ClassifyAtlas.NOT_SYNCH);
      }
    } else {
      mtdClassifyAtlas = MtdClassifyAtlasMapper.INSTANCE.useMtdClassifyAtlas(mtdClassifyAtlasVO);
    }
    mtdClassifyAtlasRepository.saveAndFlush(mtdClassifyAtlas);
  }

  @Override
  public MtdClassifyAtlasVO getByGuid(String guid) {
    Predicate predicate =
        qMtdClassifyAtlas
            .guid
            .eq(guid)
            .and(qMtdClassifyAtlas.synchStatus.ne(SynchStateProperty.ClassifyAtlas.DELETE));
    Optional<MtdClassifyAtlas> one = mtdClassifyAtlasRepository.findOne(predicate);
    return one.map(MtdClassifyAtlasMapper.INSTANCE::useMtdClassifyAtlasVO).orElse(null);
  }

  @Override
  public List<MtdClassifyAtlasVO> getByBulk(List<String> guids) {
    return MtdClassifyAtlasMapper.INSTANCE.useMtdClassifyAtlasListVO(
        mtdClassifyAtlasRepository.findAllByGuidIn(guids));
  }
}
