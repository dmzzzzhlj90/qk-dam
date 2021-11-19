package com.qk.dm.dataquality.mapstruct.mapper;

import com.qk.dm.dataquality.entity.DqcSchedulerBasicInfo;
import com.qk.dm.dataquality.vo.DqcSchedulerBasicInfoVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

/**
 * 数据质量_规则调度_基础信息VO转换mapper
 *
 * @author wjq
 * @date 2021/11/09
 * @since 1.0.0
 */
@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface DqcSchedulerBasicInfoMapper {
    DqcSchedulerBasicInfoMapper INSTANCE = Mappers.getMapper(DqcSchedulerBasicInfoMapper.class);

    DqcSchedulerBasicInfoVO userDqcSchedulerBasicInfoVO(DqcSchedulerBasicInfo dqcSchedulerBasicInfo);

    DqcSchedulerBasicInfo userDqcSchedulerBasicInfo(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO);

    void toDqcSchedulerBasicInfo(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO, @MappingTarget DqcSchedulerBasicInfo dqcSchedulerBasicInfo);

}
