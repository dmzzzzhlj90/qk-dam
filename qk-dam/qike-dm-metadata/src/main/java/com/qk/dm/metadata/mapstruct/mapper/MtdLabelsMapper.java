package com.qk.dm.metadata.mapstruct.mapper;

import com.qk.dm.metadata.entity.MtdLabels;
import com.qk.dm.metadata.vo.MtdLabelsInfoVO;
import com.qk.dm.metadata.vo.MtdLabelsListVO;
import com.qk.dm.metadata.vo.MtdLabelsVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MtdLabelsMapper {
  MtdLabelsMapper INSTANCE = Mappers.getMapper(MtdLabelsMapper.class);

  MtdLabels useMtdLabels(MtdLabelsVO mtdLabelsVO);

  MtdLabelsInfoVO useMtdLabelsInfoVO(MtdLabels mtdLabels);

  MtdLabelsListVO useMtdLabelsListVO(MtdLabelsVO mtdLabelsVO);

  /** 将MtdLabelsVO对象中非null的属性更新到MtdLabels的对象 */
  void updateMtdLabelsVO(MtdLabelsVO mtdLabelsVO, @MappingTarget MtdLabels mtdLabels);
}
