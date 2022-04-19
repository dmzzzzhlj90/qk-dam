package com.qk.dam.authority.common.keycloak;/*
package com.qk.dm.authority.keycloak;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.alibaba.nacos.common.utils.MapUtils;
import com.qk.dam.commons.exception.BizException;
import com.qk.dm.authority.constant.QxConstant;
import com.qk.dm.authority.service.PowerService;
import com.qk.dm.authority.vo.powervo.EmpowerVO;
import com.qk.dm.authority.vo.powervo.ServiceVO;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.ClientResource;
import org.keycloak.admin.client.resource.ClientsResource;
import org.keycloak.admin.client.resource.RoleResource;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Component;

import java.util.*;

*/
/**
 * 权限管理集成keycloak
 * @author zys
 * @date 2022/2/28 15:03
 * @since 1.0.0
 *//*

@Component
public class keyClocakEmpowerApi {
  private static String TARGET_REALM = "pachong";

  private final Keycloak keycloak;
  private final PowerService powerService;

  public keyClocakEmpowerApi(Keycloak keycloak, PowerService powerService) {
    this.keycloak = keycloak;
    this.powerService = powerService;
  }

  */
/**
   * 修改用户属性
   * @param empowerVO
   *//*

  public void addPower(EmpowerVO empowerVO) {
    //根据授权主体类型的不同选择不同的方式修改主体属性(0表示用户，1表示角色，2表示用户组)
    switch (empowerVO.getEmpoerType()){
      case(QxConstant.TYPE_USER):
        dealUser(empowerVO);
        break;
      case(QxConstant.TYPE_ROLE):
        dealRole(empowerVO);
        break;
      case(QxConstant.TYPE_USER_GROUP):
        dealGroup(empowerVO);
        break;
      default:
        throw new BizException("没有指定类型请查询后在授权");
    }
  }

  */
/**
   * 添加用户组授权
   * @param empowerVO
   *//*

  private void dealGroup(EmpowerVO empowerVO) {
    if (!Objects.isNull(keycloak.realm(TARGET_REALM).groups())&& !Objects.isNull(keycloak.realm(TARGET_REALM).groups().group(empowerVO.getEmpoerId()))){
      GroupRepresentation groupRepresentation = keycloak.realm(TARGET_REALM).groups().group(empowerVO.getEmpoerId()).toRepresentation();
      ServiceVO serviceVO =getServiceSing(empowerVO.getServiceId());
      String key = getKey(serviceVO,empowerVO.getPowerType());
      Map<String,List<String>> attributeMap = groupRepresentation.getAttributes();
      if (MapUtils.isNotEmpty(attributeMap)){
        attributeMap.put(key,getSignList(empowerVO));
        groupRepresentation.setAttributes(attributeMap);
      }else{
        Map<String,List<String>> map = new HashMap<>(){{ put(key,getSignList(empowerVO)); }};
        groupRepresentation.setAttributes(map);
      }
      keycloak.realm(TARGET_REALM).groups().group(empowerVO.getEmpoerId()).update(groupRepresentation);
    }else{
      throw new BizException("用户组为不存在请验证后再操作");
    }
  }

  */
/**
   * 获取服务标识
   * @param serviceId
   * @return
   *//*

  private ServiceVO getServiceSing(Long serviceId) {
    ServiceVO serviceVO = powerService.ServiceDetails(serviceId);
    if (Objects.isNull(serviceVO)){
      throw new BizException("没有指定的授权服务");
    }
    return serviceVO;
  }

  */
/**
   * 添加授权主体为角色
   * @param empowerVO
   *//*

  private void dealRole(EmpowerVO empowerVO) {
    if (!Objects.isNull(keycloak.realm(TARGET_REALM).clients())&& !Objects.isNull(keycloak.realm(TARGET_REALM).clients().get(empowerVO.getEmpoerId()))){
      ClientsResource clients = keycloak.realm(TARGET_REALM).clients();
      ClientResource clientResource = clients.get(empowerVO.getEmpoerId());
      RoleResource roleResource = clientResource.roles().get(empowerVO.getClientName());
      if (Objects.isNull(roleResource)){
        throw  new BizException("授权角色不存在");
      }
      RoleRepresentation roleRepresentation = roleResource.toRepresentation();
      ServiceVO serviceVO =getServiceSing(empowerVO.getServiceId());
      String key = getKey(serviceVO,empowerVO.getPowerType());
      Map<String,List<String>> attributeMap = roleRepresentation.getAttributes();
      if (MapUtils.isNotEmpty(attributeMap)){
        attributeMap.put(key,getSignList(empowerVO));
        roleRepresentation.setAttributes(attributeMap);
      }else{
        Map<String,List<String>> map = new HashMap<>(){{ put(key,getSignList(empowerVO)); }};
        roleRepresentation.setAttributes(map);
      }
      roleResource.update(roleRepresentation);
    }else{
      throw new BizException("客户端不存在请验证后再操作");
    }
  }

  */
/**
   * 根据授权类型生成不同的key（api的属性key为服务标识+api拼接而成）
   * @param serviceVO
   * @param powerType
   * @return
   *//*

  private String getKey(ServiceVO serviceVO, Integer powerType) {
    if (powerType == QxConstant.API_TYPE){
      return  serviceVO.getServiceSign()+"_"+QxConstant.API_KEY;
    }else {
    return serviceVO.getServiceSign();
    }
  }

  */
/**
   * 添加授权主体为用户
   * @param empowerVO
   *//*

  private void dealUser(EmpowerVO empowerVO) {
    if (!Objects.isNull(keycloak.realm(TARGET_REALM).users())&& !Objects.isNull(keycloak.realm(TARGET_REALM).users().get(empowerVO.getEmpoerId()))){
      UserRepresentation userRepresentation = keycloak.realm(TARGET_REALM).users().get(empowerVO.getEmpoerId()).toRepresentation();
      ServiceVO serviceVO =getServiceSing(empowerVO.getServiceId());
      String key = getKey(serviceVO,empowerVO.getPowerType());
      Map<String,List<String>> attributeMap = userRepresentation.getAttributes();
      if (MapUtils.isNotEmpty(attributeMap)){
        attributeMap.put(key,getSignList(empowerVO));
        userRepresentation.setAttributes(attributeMap);
      }else{
        Map<String,List<String>> map = new HashMap<>(){{ put(key,getSignList(empowerVO)); }};
        userRepresentation.setAttributes(map);
      }
      keycloak.realm(TARGET_REALM).users().get(empowerVO.getEmpoerId()).update(userRepresentation);
    }else {
      throw new BizException("不存在当前授权用户");
    }
  }

  private List<String> getSignList(EmpowerVO empowerVO) {
    if (Objects.isNull(empowerVO.getResourceSign())){
      throw  new BizException("授权标识为空");
    }
    String[] strings = empowerVO.getResourceSign().split(",");
    List<String> list = Arrays.asList(strings);
    return list;
  }

  */
/**
   * 删除权限
   * @param empowerVO
   *//*

  public void deletePower(EmpowerVO empowerVO) {
    //根据授权主体类型的不同选择不同的方式修改主体属性(0表示用户，1表示角色，2表示用户组)
    switch (empowerVO.getEmpoerType()){
      case(QxConstant.TYPE_USER):
        deleteUser(empowerVO);
        break;
      case(QxConstant.TYPE_ROLE):
        deleteRole(empowerVO);
        break;
      case(QxConstant.TYPE_USER_GROUP):
        deleteGroup(empowerVO);
        break;
      default:
        throw new BizException("没有指定类型请查询后在授权");
    }
  }

  */
/**
   * 删除授权主体为分组
   * @param empowerVO
   *//*

  private void deleteGroup(EmpowerVO empowerVO) {
    if (!Objects.isNull(keycloak.realm(TARGET_REALM).groups())&& !Objects.isNull(keycloak.realm(TARGET_REALM).groups().group(empowerVO.getEmpoerId()))){
      GroupRepresentation groupRepresentation = keycloak.realm(TARGET_REALM).groups().group(empowerVO.getEmpoerId()).toRepresentation();
      ServiceVO serviceVO =getServiceSing(empowerVO.getServiceId());
      String key = getKey(serviceVO,empowerVO.getPowerType());
      Map<String,List<String>> attributeMap = groupRepresentation.getAttributes();
      if (MapUtils.isNotEmpty(attributeMap) && !Objects.isNull(attributeMap.get(key))){
        attributeMap.remove(key);
        groupRepresentation.setAttributes(attributeMap);
      }
      keycloak.realm(TARGET_REALM).groups().group(empowerVO.getEmpoerId()).update(groupRepresentation);
    }else{
      throw new BizException("用户组为不存在请验证后再操作");
    }
  }

  */
/**
   * 删除授权主体为角色
   * @param empowerVO
   *//*

  private void deleteRole(EmpowerVO empowerVO) {
    if (!Objects.isNull(keycloak.realm(TARGET_REALM).clients()) && !Objects.isNull(keycloak.realm(TARGET_REALM).clients().get(empowerVO.getEmpoerId()))){
      ClientsResource clients = keycloak.realm(TARGET_REALM).clients();
      ClientResource clientResource = clients.get(empowerVO.getEmpoerId());
      RoleResource roleResource = clientResource.roles().get(empowerVO.getClientName());
      RoleRepresentation roleRepresentation = roleResource.toRepresentation();
      ServiceVO serviceVO =getServiceSing(empowerVO.getServiceId());
      String key = getKey(serviceVO,empowerVO.getPowerType());
      Map<String,List<String>> attributeMap = roleRepresentation.getAttributes();
      if (MapUtils.isNotEmpty(attributeMap) && !Objects.isNull(attributeMap.get(key))){
        attributeMap.remove(key);
        roleRepresentation.setAttributes(attributeMap);
      }
      roleResource.update(roleRepresentation);
    }else{
      throw new BizException("客户端为空请验证后再操作");
    }
  }

  */
/**
   * 删除授权主体为用户
   * @param empowerVO
   *//*

  private void deleteUser(EmpowerVO empowerVO) {
    if (!Objects.isNull(keycloak.realm(TARGET_REALM).users())&& !Objects.isNull(keycloak.realm(TARGET_REALM).users().get(empowerVO.getEmpoerId()))){
      UserRepresentation userRepresentation = keycloak.realm(TARGET_REALM).users().get(empowerVO.getEmpoerId()).toRepresentation();
      ServiceVO serviceVO =getServiceSing(empowerVO.getServiceId());
      String key = getKey(serviceVO,empowerVO.getPowerType());
      Map<String,List<String>> attributeMap = userRepresentation.getAttributes();
      if (MapUtils.isNotEmpty(attributeMap)&& CollectionUtils.isNotEmpty(attributeMap.get(key))){
        attributeMap.remove(key);
        userRepresentation.setAttributes(attributeMap);
      }
      keycloak.realm(TARGET_REALM).users().get(empowerVO.getEmpoerId()).update(userRepresentation);
    }else {
      throw new BizException("不存在当前授权用户");
    }
  }
}*/
