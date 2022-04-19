package com.qk.dm.dataservice.service.imp;

import com.qk.dam.commons.exception.BizException;
import com.qk.dm.dataservice.entity.DasApiRoute;
import com.qk.dm.dataservice.entity.QDasApiRoute;
import com.qk.dm.dataservice.mapstruct.mapper.DasApiRouteMapper;
import com.qk.dm.dataservice.repositories.DasApiRouteRepository;
import com.qk.dm.dataservice.service.DasApiRouteService;
import com.qk.dm.dataservice.vo.DasApiRouteVO;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * 数据服务_API路由Route匹配
 *
 * @author wjq
 * @date 20210907
 * @since 1.0.0
 */
@Service
public class DasApiRouteServiceImpl implements DasApiRouteService {
  private static final QDasApiRoute qDasApiRoute = QDasApiRoute.dasApiRoute;
  private final DasApiRouteRepository dasApiRouteRepository;

  @Autowired
  public DasApiRouteServiceImpl(DasApiRouteRepository dasApiRouteRepository) {
    this.dasApiRouteRepository = dasApiRouteRepository;
  }

  @Override
  public List<DasApiRouteVO> searchList() {
    List<DasApiRouteVO> dasApiRouteVOList = new ArrayList<>();

    List<DasApiRoute> dasApiRouteList = dasApiRouteRepository.findAll();
    for (DasApiRoute dasApiRoute : dasApiRouteList) {
      DasApiRouteVO dasApiRouteVO = DasApiRouteMapper.INSTANCE.useDasApiRouteVO(dasApiRoute);
      dasApiRouteVOList.add(dasApiRouteVO);
    }
    return dasApiRouteVOList;
  }

  @Override
  public void insert(DasApiRouteVO dasApiRouteVO) {
    String apiRoutePath = dasApiRouteVO.getApiRoutePath();
    Optional<DasApiRoute> apiRouteOptional =
        dasApiRouteRepository.findOne(qDasApiRoute.apiRoutePath.eq(apiRoutePath));
    if (apiRouteOptional.isPresent()) {
      throw new BizException("已经存在路径为: " + apiRoutePath + "的路由信息!");
    }
    String routeId = UUID.randomUUID().toString().replaceAll("-", "");
    dasApiRouteVO.setApiRouteId(routeId);
    DasApiRoute dasApiRoute = DasApiRouteMapper.INSTANCE.useDasApiRoute(dasApiRouteVO);
    dasApiRoute.setGmtCreate(new Date());
    dasApiRoute.setGmtModified(new Date());
    dasApiRoute.setDelFlag(0);
    dasApiRouteRepository.save(dasApiRoute);
  }

  @Override
  public void update(DasApiRouteVO dasApiRouteVO) {
    String apiRouteId = dasApiRouteVO.getApiRouteId();
    Optional<DasApiRoute> apiRouteOptional =
        dasApiRouteRepository.findOne(qDasApiRoute.apiRouteId.eq(apiRouteId));
    if (apiRouteOptional.isEmpty()) {
      throw new BizException("不存在路径为: " + dasApiRouteVO.getApiRoutePath() + "的路由信息!");
    }

    DasApiRoute dasApiRoute = DasApiRouteMapper.INSTANCE.useDasApiRoute(dasApiRouteVO);
    dasApiRoute.setGmtCreate(new Date());
    dasApiRoute.setDelFlag(0);
    dasApiRouteRepository.save(dasApiRoute);
  }

  @Override
  public void deleteBulk(List<String> ids) {
    if (!ObjectUtils.isEmpty(ids)) {
      Set<Long> idSet = new HashSet<>();
      ids.forEach(id -> idSet.add(Long.valueOf(id)));
      List<DasApiRoute> apiRouteList = dasApiRouteRepository.findAllById(idSet);
      dasApiRouteRepository.deleteInBatch(apiRouteList);
    }
  }
}
