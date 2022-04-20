package com.qk.dm.dataservice.mapstruct.mapper;

import com.qk.dm.dataservice.entity.DasApiBasicInfo;
import com.qk.dm.dataservice.entity.DasApiLimitBindInfo;
import com.qk.dm.dataservice.entity.DasApiLimitInfo;
import com.qk.dm.dataservice.vo.DasApiBasicInfoVO;
import com.qk.dm.dataservice.vo.DasApiLimitBindInfoVO;
import com.qk.dm.dataservice.vo.DasApiLimitInfoVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 服务流控管理VO转换mapper
 *
 * @author wjq
 * @date 2022/03/17
 * @since 1.0.0
 */
@Mapper
public interface DasApiLimitInfoMapper {
  DasApiLimitInfoMapper INSTANCE = Mappers.getMapper(DasApiLimitInfoMapper.class);

  /**
   * 实体转VO
   * @param dasApiLimitInfo
   * @return
   */
  DasApiLimitInfoVO useDasApiLimitInfoVO(DasApiLimitInfo dasApiLimitInfo);

  /**
   * VO转实体
   *
   * @param dasApiLimitInfoVO
   * @return
   */
  DasApiLimitInfo useDasApiLimitInfo(DasApiLimitInfoVO dasApiLimitInfoVO);

  /**
   * 绑定实体转VO
   *
   * @param bindInfo
   * @return
   */
  DasApiLimitBindInfoVO useDasApiLimitBindInfoVO(DasApiLimitBindInfo bindInfo);

}
