package com.qk.dm.metadata.mapstruct.mapper;

import com.qk.dm.metadata.entity.MtdClassify;
import com.qk.dm.metadata.vo.MtdClassifyInfoVO;
import com.qk.dm.metadata.vo.MtdClassifyListVO;
import com.qk.dm.metadata.vo.MtdClassifyVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

/**
 * @author wangzp
 * @date 2021/7/31 13:03
 * @since 1.0.0
 */
@Mapper
public interface MtdClassifyMapper {
  MtdClassifyMapper INSTANCE = Mappers.getMapper(MtdClassifyMapper.class);

  MtdClassify useMtdClassify(MtdClassifyVO mtdClassifyVO);

  MtdClassifyVO useMtdClassifyVO(MtdClassify mtdClassify);

  MtdClassifyInfoVO useMtdClassifyInfoVO(MtdClassify mtdClassify);

  MtdClassifyListVO useMtdClassifyListVO(MtdClassifyVO mtdClassifyVO);

  void updateMtdClassifyVO(MtdClassifyVO mtdClassifyVO, @MappingTarget MtdClassify mtdClassify);
}
