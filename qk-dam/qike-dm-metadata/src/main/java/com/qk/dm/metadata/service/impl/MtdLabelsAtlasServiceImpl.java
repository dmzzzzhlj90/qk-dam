package com.qk.dm.metadata.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dm.metadata.entity.MtdLabelsAtlas;
import com.qk.dm.metadata.entity.QMtdLabelsAtlas;
import com.qk.dm.metadata.mapstruct.mapper.MtdLabelsAtlasMapper;
import com.qk.dm.metadata.repositories.MtdLabelsAtlasRepository;
import com.qk.dm.metadata.service.MtdLabelsAtlasService;
import com.qk.dm.metadata.vo.MtdLabelsAtlasVO;
import com.querydsl.core.types.Predicate;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        MtdLabelsAtlas mtdLabelsAtlas = MtdLabelsAtlasMapper.INSTANCE.useMtdLabelsAtlas(mtdLabelsAtlasVO);
        Predicate predicate = qMtdLabelsAtlas.guid.eq(mtdLabelsAtlasVO.getGuid());
        boolean exists = mtdLabelsAtlasRepository.exists(predicate);
        if (exists) {
            throw new BizException(
                    "当前要绑定标签的元数据为："
                            + mtdLabelsAtlasVO.getGuid()
                            + " 的数据，已存在！！！");
        }
        mtdLabelsAtlasRepository.save(mtdLabelsAtlas);
    }

    @Override
    public void update(MtdLabelsAtlasVO mtdLabelsAtlasVO) {
        Predicate predicate = qMtdLabelsAtlas.guid.eq(mtdLabelsAtlasVO.getGuid());
        Optional<MtdLabelsAtlas> one = mtdLabelsAtlasRepository.findOne(predicate);
        if (!one.isPresent()) {
            throw new BizException(
                    "当前要绑定标签的元数据为："
                            + mtdLabelsAtlasVO.getGuid()
                            + " 的数据不存在！！！");
        }
        if (mtdLabelsAtlasVO.getLabels().isEmpty()) {
            mtdLabelsAtlasRepository.delete(one.get());
        } else {
            one.get().setLabels(mtdLabelsAtlasVO.getLabels());
            mtdLabelsAtlasRepository.saveAndFlush(one.get());
        }
    }

    @Override
    public MtdLabelsAtlasVO getByGuid(String guid) {
        Predicate predicate = qMtdLabelsAtlas.guid.eq(guid);
        Optional<MtdLabelsAtlas> one = mtdLabelsAtlasRepository.findOne(predicate);
        if (one.isPresent()) {
            return MtdLabelsAtlasMapper.INSTANCE.useMtdLabelsAtlasVO(one.get());
        }
        return null;
    }
}
