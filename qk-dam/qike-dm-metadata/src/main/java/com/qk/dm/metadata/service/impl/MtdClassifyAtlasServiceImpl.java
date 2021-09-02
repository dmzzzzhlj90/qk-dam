package com.qk.dm.metadata.service.impl;

import com.qk.dam.commons.exception.BizException;
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
    Predicate predicate =
        qMtdClassifyAtlas
            .guid
            .eq(mtdClassifyAtlasVO.getGuid())
            .and(qMtdClassifyAtlas.synchStatus.ne(SynchStateProperty.ClassifyAtlas.DELETE));
    if (mtdClassifyAtlasRepository.exists(predicate)) {
      throw new BizException("当前要绑定标签的元数据为：" + mtdClassifyAtlasVO.getGuid() + " 的数据已存在！！！");
    }
    MtdClassifyAtlas mtdClassifyAtlas =
        MtdClassifyAtlasMapper.INSTANCE.useMtdClassifyAtlas(mtdClassifyAtlasVO);
    mtdClassifyAtlasRepository.save(mtdClassifyAtlas);
  }

  @Override
  public void update(MtdClassifyAtlasVO mtdClassifyAtlasVO) {
    Predicate predicate =
        qMtdClassifyAtlas
            .guid
            .eq(mtdClassifyAtlasVO.getGuid())
            .and(qMtdClassifyAtlas.synchStatus.ne(SynchStateProperty.ClassifyAtlas.DELETE));
    MtdClassifyAtlas classifyAtlas = mtdClassifyAtlasRepository.findOne(predicate).orElse(null);
    if (classifyAtlas == null) {
      throw new BizException("当前要绑定分类的元数据为：" + mtdClassifyAtlasVO.getGuid() + " 的数据不存在！！！");
    }
    if (mtdClassifyAtlasVO.getClassify().isEmpty()) {
      classifyAtlas.setSynchStatus(SynchStateProperty.ClassifyAtlas.DELETE);
    } else {
      classifyAtlas.setClassify(mtdClassifyAtlasVO.getClassify());
      classifyAtlas.setSynchStatus(SynchStateProperty.ClassifyAtlas.NOT_SYNCH);
    }
    mtdClassifyAtlasRepository.saveAndFlush(classifyAtlas);
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
}
