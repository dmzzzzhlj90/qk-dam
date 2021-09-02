package com.qk.dm.indicator.mapstruct.mapper;


import com.qk.dm.indicator.entity.IdcTimeLimit;
import com.qk.dm.indicator.params.dto.IdcTimeLimitDTO;
import com.qk.dm.indicator.params.vo.IdcTimeLimitVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author wangzp
 * @date 2021/9/1 14:32
 * @since 1.0.0
 */
@Mapper
public interface IdcTimeLimitMapper {
    IdcTimeLimitMapper INSTANCE = Mappers.getMapper(IdcTimeLimitMapper.class);

    IdcTimeLimit useIdcTimeLimit(IdcTimeLimitDTO idcTimeLimitDTO);

    IdcTimeLimitVO useIdcTimeLimitVO(IdcTimeLimit idcTimeLimit);

    List<IdcTimeLimitVO> userIdcTimeLimitListVO(List<IdcTimeLimit> idcTimeLimitList);

}
