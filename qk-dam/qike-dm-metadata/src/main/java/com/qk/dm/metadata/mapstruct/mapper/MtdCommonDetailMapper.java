package com.qk.dm.metadata.mapstruct.mapper;

import com.qk.dm.metadata.vo.MtdColumnVO;
import com.qk.dm.metadata.vo.MtdCommonDetailVO;
import com.qk.dm.metadata.vo.MtdDbDetailVO;
import com.qk.dm.metadata.vo.MtdTableDetailVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MtdCommonDetailMapper {
    MtdCommonDetailMapper INSTANCE = Mappers.getMapper(MtdCommonDetailMapper.class);

    MtdCommonDetailVO userMtdCommonDetail(MtdDbDetailVO mtdDbDetailVO);

    MtdCommonDetailVO userMtdCommonDetail(MtdTableDetailVO mtdTableDetailVO);

    MtdCommonDetailVO userMtdCommonDetail(MtdColumnVO mtdColumnVO);
}
