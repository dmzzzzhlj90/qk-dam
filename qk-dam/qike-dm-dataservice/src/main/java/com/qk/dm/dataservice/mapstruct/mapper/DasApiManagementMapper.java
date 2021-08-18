package com.qk.dm.dataservice.mapstruct.mapper;

import com.qk.dm.dataservice.entity.DasApplicationManagement;
import com.qk.dm.dataservice.vo.DasApplicationManagementVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * api应用系统vo转mapper
 *
 * @author zys
 * @date 2021/8/17
 * @since 1.0.0
 */
@Mapper
public interface DasApiManagementMapper {
    DasApiManagementMapper INSTANCE = Mappers.getMapper(DasApiManagementMapper.class);
    DasApplicationManagementVO useDasApiManagementVO(DasApplicationManagement dasApplicationManagement);
    DasApplicationManagement useDasApiManagement(DasApplicationManagementVO DasApplicationManagementVO);
}
