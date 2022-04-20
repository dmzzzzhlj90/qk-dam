package com.qk.dm.dataservice.mapstruct.mapper;

import com.qk.dm.dataservice.entity.DasApiAppManageInfo;
import com.qk.dm.dataservice.entity.DasApiLimitBindInfo;
import com.qk.dm.dataservice.entity.DasApiLimitInfo;
import com.qk.dm.dataservice.vo.DasApiAppManageInfoVO;
import com.qk.dm.dataservice.vo.DasApiLimitBindInfoVO;
import com.qk.dm.dataservice.vo.DasApiLimitInfoVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * API应用管理VO转换mapper
 *
 * @author wjq
 * @date 2022/03/21
 * @since 1.0.0
 */
@Mapper
public interface DasApiAppManageInfoMapper {
  DasApiAppManageInfoMapper INSTANCE = Mappers.getMapper(DasApiAppManageInfoMapper.class);

  /**
   * 实体转VO
   * @param dasApiAppManageInfo
   * @return
   */
  DasApiAppManageInfoVO useDasApiAppManageInfoVO(DasApiAppManageInfo dasApiAppManageInfo);

  /**
   * VO转实体
   *
   * @param dasApiAppManageInfoVO
   * @return
   */
  DasApiAppManageInfo useDasApiAppManageInfo(DasApiAppManageInfoVO dasApiAppManageInfoVO);

}
