package com.qk.dm.metadata.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dm.metadata.entity.MtdClassifyAtlas;
import com.qk.dm.metadata.entity.QMtdClassifyAtlas;
import com.qk.dm.metadata.mapstruct.mapper.MtdClassifyAtlasMapper;
import com.qk.dm.metadata.repositories.MtdClassifyAtlasRepository;
import com.qk.dm.metadata.service.MtdClassifyAtlasService;
import com.qk.dm.metadata.vo.MtdClassifyAtlasVO;
import com.querydsl.core.types.Predicate;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            .and(qMtdClassifyAtlas.synchStatus.ne(-1));
    if (mtdClassifyAtlasRepository.exists(predicate)) {
      throw new BizException("当前要绑定标签的元数据为：" + mtdClassifyAtlasVO.getGuid() + " 的数据，已存在！！！");
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
            .and(qMtdClassifyAtlas.synchStatus.ne(-1));
    Optional<MtdClassifyAtlas> mtdClassifyAtlas = mtdClassifyAtlasRepository.findOne(predicate);
    if (mtdClassifyAtlas.isEmpty()) {
      throw new BizException("当前要绑定分类的元数据为：" + mtdClassifyAtlasVO.getGuid() + " 的数据不存在！！！");
    }
    MtdClassifyAtlas classifyAtlas = mtdClassifyAtlas.get();
    if (mtdClassifyAtlasVO.getClassify().isEmpty()) {
      classifyAtlas.setSynchStatus(-1);
    } else {
      classifyAtlas.setClassify(mtdClassifyAtlasVO.getClassify());
      classifyAtlas.setSynchStatus(0);
    }
    mtdClassifyAtlasRepository.saveAndFlush(classifyAtlas);
  }

  @Override
  public MtdClassifyAtlasVO getByGuid(String guid) {
    Predicate predicate = qMtdClassifyAtlas.guid.eq(guid).and(qMtdClassifyAtlas.synchStatus.ne(-1));
    Optional<MtdClassifyAtlas> one = mtdClassifyAtlasRepository.findOne(predicate);
    return one.map(MtdClassifyAtlasMapper.INSTANCE::useMtdClassifyAtlasVO).orElse(null);
  }
}
