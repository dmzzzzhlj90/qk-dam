package com.qk.dm.dataquality.mapstruct.mapper;

import com.qk.dm.dataquality.constant.NotifyStateEnum;
import com.qk.dm.dataquality.entity.DqcSchedulerBasicInfo;
import com.qk.dm.dataquality.vo.DqcSchedulerBasicInfoVO;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * 数据质量_规则调度_基础信息VO转换mapper
 *
 * @author wjq
 * @date 2021/11/09
 * @since 1.0.0
 */
@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,imports = {NotifyStateEnum.class})
public interface DqcSchedulerBasicInfoMapper {
    DqcSchedulerBasicInfoMapper INSTANCE = Mappers.getMapper(DqcSchedulerBasicInfoMapper.class);

    @Mappings({@Mapping(target = "notifyState", expression = "java(NotifyStateEnum.conversionType(dqcSchedulerBasicInfo.getNotifyState()))")})
    DqcSchedulerBasicInfoVO userDqcSchedulerBasicInfoVO(DqcSchedulerBasicInfo dqcSchedulerBasicInfo);

    @Mappings({@Mapping(target = "notifyState", expression = "java(NotifyStateEnum.conversionType(dqcSchedulerBasicInfoVO.getNotifyState()))")})
    DqcSchedulerBasicInfo userDqcSchedulerBasicInfo(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO);

    @Mappings({@Mapping(target = "notifyState", expression = "java(NotifyStateEnum.conversionType(dqcSchedulerBasicInfoVO.getNotifyState()))")})
    void toDqcSchedulerBasicInfo(DqcSchedulerBasicInfoVO dqcSchedulerBasicInfoVO, @MappingTarget DqcSchedulerBasicInfo dqcSchedulerBasicInfo);

}
