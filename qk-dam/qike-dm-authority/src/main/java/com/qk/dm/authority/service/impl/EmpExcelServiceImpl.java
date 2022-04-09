package com.qk.dm.authority.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.qk.dam.authority.common.vo.user.AtyUserInputExceVO;
import com.qk.dam.commons.exception.BizException;
import com.qk.dm.authority.constant.QxConstant;
import com.qk.dm.authority.entity.QkQxResourcesApi;
import com.qk.dm.authority.entity.QkQxResourcesMenu;
import com.qk.dm.authority.listener.ApiRsUploadDataListener;
import com.qk.dm.authority.listener.RsUploadDataListener;
import com.qk.dm.authority.listener.UserUploadDataListener;
import com.qk.dm.authority.mapstruct.AtyUserMapper;
import com.qk.dm.authority.mapstruct.QxResourcesApiMapper;
import com.qk.dm.authority.mapstruct.QxResourcesMenuMapper;
import com.qk.dm.authority.repositories.QkQxResourcesApiRepository;
import com.qk.dm.authority.repositories.QkQxResourcesMenuRepository;
import com.qk.dm.authority.service.AtyUserService;
import com.qk.dm.authority.service.EmpExcelService;
import com.qk.dm.authority.util.MultipartFileUtil;
import com.qk.dm.authority.vo.powervo.ResourceApiVO;
import com.qk.dm.authority.vo.powervo.ResourceMenuExcelVO;
import com.qk.dm.authority.vo.user.AtyUserDownVO;
import com.qk.dm.authority.vo.user.AtyUserExcelVO;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 资源导入导出
 * @author zys
 * @date 2022/3/1 15:26
 * @since 1.0.0
 */
@Service
public class EmpExcelServiceImpl implements EmpExcelService {
  private static final Log LOG = LogFactory.getLog("资源数据导入导出");
  private final ResourceExcelBatchService resourceExcelBatchService;
  private final ApiRsExcelBatchService apiResourceExcelBatchService;
  private final UserExcelBatchService userExcelBatchService;
  private final QkQxResourcesApiRepository qkQxResourcesApiRepository;
  private final QkQxResourcesMenuRepository qkQxResourcesMenuRepository;
  private final AtyUserService atyUserService;

  @Autowired
  public EmpExcelServiceImpl(ResourceExcelBatchService resourceExcelBatchService,
      ApiRsExcelBatchService apiResourceExcelBatchService,
      UserExcelBatchService userExcelBatchService,
      QkQxResourcesApiRepository qkQxResourcesApiRepository,
      QkQxResourcesMenuRepository qkQxResourcesMenuRepository, AtyUserService atyUserService) {
    this.resourceExcelBatchService = resourceExcelBatchService;
    this.apiResourceExcelBatchService = apiResourceExcelBatchService;
    this.userExcelBatchService = userExcelBatchService;
    this.qkQxResourcesApiRepository = qkQxResourcesApiRepository;
    this.qkQxResourcesMenuRepository = qkQxResourcesMenuRepository;
    this.atyUserService = atyUserService;
  }


  @Override
  public void resourceDownloadTemplate(HttpServletResponse response)
      throws IOException {
    List<ResourceMenuExcelVO> rptBaseInfoVOList = prtBaseInfoSampleData();
    response.setContentType("application/json;charset=utf-8");
    String fileName = URLEncoder.encode("资源基本信息导入模板", "UTF-8").replaceAll("\\+", "%20");
    response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
    EasyExcel.write(response.getOutputStream(), ResourceMenuExcelVO.class)
        .sheet("资源基本信息导入模板")
        .doWrite(rptBaseInfoVOList);
  }

  private List<ResourceMenuExcelVO> prtBaseInfoSampleData() {
    List<ResourceMenuExcelVO> list = new ArrayList<>();
    for (int i = 0; i < 2; i++) {
      ResourceMenuExcelVO resourceMenuExcelVO =
          ResourceMenuExcelVO.builder()
              .name("资源名称" + i)
              .path("网址路径" + i)
              .description("描述" + i)
              .createName("创建人名称" + i)
              .pidName("父级名称"+i)
              .serviceId(("服务UUID")+i)
              .component("页面"+i)
              .icon("显示icon"+i)
              .redirect("重定向"+i)
              .hideInMenu(true)
              .hideInBreadcrumb(true)
              .exact(true)
              .build();
      list.add(resourceMenuExcelVO);
    }
    return list;

  }

  @Override
  public void apiDownloadTemplate(HttpServletResponse response)
      throws IOException {
    List<ResourceApiVO> rptBaseInfoVOList = getApiDownloadTemplate();
    response.setContentType("application/json;charset=utf-8");
    String fileName = URLEncoder.encode("API资源基本信息导入模板", "UTF-8").replaceAll("\\+", "%20");
    response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
    EasyExcel.write(response.getOutputStream(), ResourceApiVO.class)
        .sheet("API资源基本信息导入模板")
        .doWrite(rptBaseInfoVOList);
  }

  private List<ResourceApiVO> getApiDownloadTemplate() {
    List<ResourceApiVO> list = new ArrayList<>();
    for (int i = 0; i < 2; i++) {
      ResourceApiVO resourceApiVO =
          ResourceApiVO.builder()
              .name("资源名称" + i)
              .path("网址路径" + i)
              .description("描述" + i)
              .createName("创建人名称" + i)
              .serviceId(("服务UUID")+i)
              .build();
      list.add(resourceApiVO);
    }
    return list;
  }

  /**
   * 资源导入
   * @param file
   * @return
   */
  @Override
  public Boolean resourceInfoUpload(MultipartFile file) {
    MultipartFileUtil.checkFile(file);
    List<ResourceMenuExcelVO> list = new ArrayList<>();
    LOG.info("======开始导入资源数据!======");
    try {
      RsUploadDataListener resourceBasicInfoUploadDataListener = new RsUploadDataListener(resourceExcelBatchService);
      EasyExcel.read(
          file.getInputStream(),
          ResourceMenuExcelVO.class,
          resourceBasicInfoUploadDataListener)
          .sheet()
          .doRead();
      list = resourceBasicInfoUploadDataListener.getList();
    } catch (Exception e) {
      LOG.info("======导入资源数据失败!======");
      throw new BizException("导入失败,请检验数据格式后再导入," + e.getCause().getMessage());
    }
    LOG.info("======成功导入资源数据!======");

    if (CollectionUtils.isNotEmpty(list)){
      return true;
    }
    return false;
  }

  /**
   * api资源导入
   * @param file
   * @return
   */
  @Override
  public Boolean apiResourceInfoUpload(MultipartFile file) {
    MultipartFileUtil.checkFile(file);
    List<ResourceApiVO> list = new ArrayList<>();
    LOG.info("======开始导入api资源数据!======");
    try {
      ApiRsUploadDataListener apiResourceBasicInfoUploadDataListener = new ApiRsUploadDataListener(apiResourceExcelBatchService);
      EasyExcel.read(
          file.getInputStream(),
          ResourceApiVO.class,
          apiResourceBasicInfoUploadDataListener)
          .sheet()
          .doRead();
      list = apiResourceBasicInfoUploadDataListener.getList();
    } catch (Exception e) {
      LOG.info("======导入api资源数据失败!======");
      throw new BizException("导入失败,请检验数据格式后再导入," + e.getCause().getMessage());
    }
    LOG.info("======成功api导入资源数据!======");

    if (CollectionUtils.isNotEmpty(list)){
      return true;
    }
    return false;
  }

  /**
   * 资源导出
   * @param serviceId
   * @param response
   */
  @Override
  public void resourcesAllDownload(String serviceId, HttpServletResponse response)
      throws IOException {
    List<ResourceMenuExcelVO> dsdCodeInfoVOList = GetResourcesAll(serviceId);
    response.setContentType("application/json;charset=utf-8");
    String fileName = URLEncoder.encode("资源基本信息", "UTF-8").replaceAll("\\+", "%20");
    response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
    EasyExcel.write(response.getOutputStream(), ResourceMenuExcelVO.class)
        .sheet("资源基本信息")
        .doWrite(dsdCodeInfoVOList);
  }

  /**
   * api资源导出
   * @param serviceId
   * @param response
   */
  @Override
  public void apiAllDownload(String serviceId, HttpServletResponse response)
      throws IOException {
    List<ResourceApiVO> apiResourcesList = GetApiAll(serviceId);
    response.setContentType("application/json;charset=utf-8");
    String fileName = URLEncoder.encode("api资源基本信息", "UTF-8").replaceAll("\\+", "%20");
    response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
    EasyExcel.write(response.getOutputStream(), ResourceApiVO.class)
        .sheet("api资源基本信息")
        .doWrite(apiResourcesList);
  }

  /**
   * 用户列表导出
   * @param realm
   * @param search
   * @param response
   */
  @Override
  public void UserAllDownload(String realm, String search,
      HttpServletResponse response) throws IOException {
    List<AtyUserExcelVO> atyUserExcelVOList = atyUserService.getUsers(realm, search).stream().map(atyUserInfoVO -> {
          AtyUserExcelVO atyUserExcelVO = AtyUserMapper.INSTANCE.userExcel(atyUserInfoVO);
          return atyUserExcelVO;
        }).collect(Collectors.toList());
    response.setContentType("application/json;charset=utf-8");
    String fileName = URLEncoder.encode("用户列表基本信息", "UTF-8").replaceAll("\\+", "%20");
    response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
    EasyExcel.write(response.getOutputStream(), AtyUserExcelVO.class)
        .sheet("用户列表基本信息")
        .doWrite(atyUserExcelVOList);

  }

  @Override
  public void UserAllDownload(AtyUserDownVO atyUserDownVO, HttpServletResponse response) throws IOException {
    List<AtyUserExcelVO> atyUserExcelVOList = atyUserDownVO.getUserIds().stream().map(userId ->
            AtyUserMapper.INSTANCE.userExcel(atyUserService.getUser(atyUserDownVO.getRealm(), userId))
    ).collect(Collectors.toList());
    response.setContentType("application/json;charset=utf-8");
    String fileName = URLEncoder.encode("用户列表基本信息", "UTF-8").replaceAll("\\+", "%20");
    response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
    EasyExcel.write(response.getOutputStream(), AtyUserExcelVO.class)
            .sheet("用户列表基本信息")
            .doWrite(atyUserExcelVOList);
  }

  /**
   * 用户导出模板
   * @param response
   */
  @Override
  public void userDownloadTemplate(HttpServletResponse response)
      throws IOException {
    List<AtyUserInputExceVO> rptBaseInfoVOList = userBaseInfoSampleData();
    response.setContentType("application/json;charset=utf-8");
    String fileName = URLEncoder.encode("用户基本信息导入模板", "UTF-8").replaceAll("\\+", "%20");
    response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
    EasyExcel.write(response.getOutputStream(), AtyUserInputExceVO.class)
        .sheet("用户基本信息导入模板")
        .doWrite(rptBaseInfoVOList);
  }

  private List<AtyUserInputExceVO> userBaseInfoSampleData() {
    List<AtyUserInputExceVO> list = new ArrayList<>();
    for (int i = 0; i < 2; i++) {
      AtyUserInputExceVO atyUserInputExceVO =
          AtyUserInputExceVO.builder()
              .username("用户名称"+i)
              .password("密码"+i)
              .enabled(true)
              .firstName("用户名"+i)
              .lastName("用户姓"+i)
              .email("用户email"+i)
              .build();
      list.add(atyUserInputExceVO);
    }
    return list;

  }

  /**
   * 导入用户信息
   * @param file
   * @param realm
   * @return
   */
  @Override
  public Boolean userResourceInfoUpload(MultipartFile file, String realm) {
    MultipartFileUtil.checkUserFile(file);
    List<AtyUserInputExceVO> list = new ArrayList<>();
    LOG.info("======开始导入用户数据!======");
    try {
      UserUploadDataListener userBasicInfoUploadDataListener = new UserUploadDataListener(userExcelBatchService);
      userBasicInfoUploadDataListener.setRelame(realm);
      EasyExcel.read(
          file.getInputStream(),
          AtyUserInputExceVO.class,
          userBasicInfoUploadDataListener)
          .sheet()
          .doRead();
      list = userBasicInfoUploadDataListener.getList();
    } catch (Exception e) {
      LOG.info("======导入用户数据失败!======");
      throw new BizException("导入失败,请检验数据格式后再导入," + e.getCause().getMessage());
    }
    LOG.info("======成功导入用户数据!======");

    if (CollectionUtils.isNotEmpty(list)){
      return true;
    }
    return false;
  }


  /**
   * 获取api资源信息
   * @param serviceId
   * @return
   */
  private List<ResourceApiVO> GetApiAll(String serviceId) {
    List<ResourceApiVO> resourceVOList = new ArrayList<>();
    List<QkQxResourcesApi> resourceApiList = qkQxResourcesApiRepository
        .findByServiceId(serviceId);
    if (CollectionUtils.isNotEmpty(resourceApiList)){
      resourceVOList = QxResourcesApiMapper.INSTANCE.qxApiResourcesOf(resourceApiList);
    }
    return resourceVOList;
  }

  /**
   * 获取对应服务的资源数据
   * @param serviceId
   * @return
   */
  private List<ResourceMenuExcelVO> GetResourcesAll(String serviceId) {
    List<ResourceMenuExcelVO> resourceOutVOList = new ArrayList<>();
    List<QkQxResourcesMenu> qkQxResourcesMenuList = qkQxResourcesMenuRepository.findByServiceId(serviceId);
    List<QkQxResourcesMenu> qxResourcesMenuList = qkQxResourcesMenuList.stream().filter(
        qkQxResourcesMenu -> qkQxResourcesMenu.getPid() != QxConstant.PID)
        .collect(Collectors.toList());
    Map<Long,String> map = dealResources(qxResourcesMenuList);
    if (CollectionUtils.isNotEmpty(qxResourcesMenuList)){
      resourceOutVOList = QxResourcesMenuMapper.INSTANCE.ofResourceExcelVO(qxResourcesMenuList);
      resourceOutVOList.forEach(resourceExcelVO -> {
        resourceExcelVO.setPidName(map.get(resourceExcelVO.getId()));
      });
    }
    return resourceOutVOList;
  }

  /**
   * 处理资源，将资源的id作为key，将资源的父类名称作为value，如果没有父类则为空
   * @param list
   * @return
   */
  private Map<Long,String> dealResources(List<QkQxResourcesMenu> list) {
    Map<Long,String> map  = new HashMap<>();
    if (CollectionUtils.isNotEmpty(list)){
      list.forEach(qkQxResourcesMenu -> {
        map.put(qkQxResourcesMenu.getId(),getValue(qkQxResourcesMenu.getPid(),list));
      });
    }
    return map;
  }

  private String getValue(Long pid, List<QkQxResourcesMenu> list) {
    Optional<QkQxResourcesMenu> resourcesMenu = list.stream()
        .filter(qkQxResourcesMenu -> qkQxResourcesMenu.getId().equals(pid))
        .findFirst();
    if (resourcesMenu.isPresent()){
      return resourcesMenu.get().getName();
    }
    return QxConstant.PID_NAME;
  }
}