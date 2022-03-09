package com.qk.dm.authority.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.qk.dam.commons.exception.BizException;
import com.qk.dm.authority.constant.QxConstant;
import com.qk.dm.authority.entity.QxResources;
import com.qk.dm.authority.listener.ApiResourceBasicInfoUploadDataListener;
import com.qk.dm.authority.listener.ResourceBasicInfoUploadDataListener;
import com.qk.dm.authority.listener.UserBasicInfoUploadDataListener;
import com.qk.dm.authority.mapstruct.AtyUserMapper;
import com.qk.dm.authority.mapstruct.QxResourcesMapper;
import com.qk.dm.authority.repositories.QkQxResourcesRepository;
import com.qk.dm.authority.service.AtyUserService;
import com.qk.dm.authority.service.QxExcelService;
import com.qk.dm.authority.util.MultipartFileUtil;
import com.qk.dm.authority.vo.powervo.ResourceExcelVO;
import com.qk.dm.authority.vo.powervo.ResourceVO;
import com.qk.dm.authority.vo.user.AtyUserExcelVO;
import com.qk.dm.authority.vo.user.AtyUserInfoVO;
import com.qk.dm.authority.vo.user.AtyUserInputExceVO;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 资源导入导出
 * @author zys
 * @date 2022/3/1 15:26
 * @since 1.0.0
 */
@Service
public class QxExcelServiceImpl implements QxExcelService {
  private static final Log LOG = LogFactory.getLog("资源数据导入导出");
  private final ResourceExcelBatchService resourceExcelBatchService;
  private final ApiResourceExcelBatchService apiResourceExcelBatchService;
  private final UserExcelBatchService userExcelBatchService;
  private final QkQxResourcesRepository qkQxResourcesRepository;
  private final AtyUserService atyUserService;
  SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //设置格式

  @Autowired
  public QxExcelServiceImpl(ResourceExcelBatchService resourceExcelBatchService,
      ApiResourceExcelBatchService apiResourceExcelBatchService,
      UserExcelBatchService userExcelBatchService, QkQxResourcesRepository qkQxResourcesRepository,
      AtyUserService atyUserService) {
    this.resourceExcelBatchService = resourceExcelBatchService;
    this.apiResourceExcelBatchService = apiResourceExcelBatchService;
    this.userExcelBatchService = userExcelBatchService;
    this.qkQxResourcesRepository = qkQxResourcesRepository;
    this.atyUserService = atyUserService;
  }


  @Override
  public void resourceDownloadTemplate(HttpServletResponse response)
      throws IOException {
    List<ResourceExcelVO> rptBaseInfoVOList = prtBaseInfoSampleData();
    response.setContentType("application/json;charset=utf-8");
    String fileName = URLEncoder.encode("资源基本信息导入模板", "UTF-8").replaceAll("\\+", "%20");
    response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
    EasyExcel.write(response.getOutputStream(), ResourceExcelVO.class)
        .sheet("资源基本信息导入模板")
        .doWrite(rptBaseInfoVOList);
  }

  private List<ResourceExcelVO> prtBaseInfoSampleData() {
    List<ResourceExcelVO> list = new ArrayList<>();
    for (int i = 0; i < 2; i++) {
      ResourceExcelVO resourceExcelVO =
          ResourceExcelVO.builder()
              .name("资源名称" + i)
              .path("网址路径" + i)
              .description("描述" + i)
              .createName("创建人名称" + i)
              .pidName("父级名称"+i)
              .serviceId(("服务UUID")+i)
              .type(1)
              .build();
      list.add(resourceExcelVO);
    }
    return list;

  }

  @Override
  public void apiDownloadTemplate(HttpServletResponse response)
      throws IOException {
    List<ResourceVO> rptBaseInfoVOList = getApiDownloadTemplate();
    response.setContentType("application/json;charset=utf-8");
    String fileName = URLEncoder.encode("API资源基本信息导入模板", "UTF-8").replaceAll("\\+", "%20");
    response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
    EasyExcel.write(response.getOutputStream(), ResourceVO.class)
        .sheet("API资源基本信息导入模板")
        .doWrite(rptBaseInfoVOList);
  }

  private List<ResourceVO> getApiDownloadTemplate() {
    List<ResourceVO> list = new ArrayList<>();
    for (int i = 0; i < 2; i++) {
      ResourceVO resourceVO =
          ResourceVO.builder()
              .name("资源名称" + i)
              .path("网址路径" + i)
              .description("描述" + i)
              .createName("创建人名称" + i)
              .pid(-1l)
              .serviceId(("服务UUID")+i)
              .type(0)
              .build();
      list.add(resourceVO);
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
    List<ResourceExcelVO> list = new ArrayList<>();
    LOG.info("======开始导入资源数据!======");
    try {
      ResourceBasicInfoUploadDataListener resourceBasicInfoUploadDataListener = new ResourceBasicInfoUploadDataListener(resourceExcelBatchService);
      EasyExcel.read(
          file.getInputStream(),
          ResourceExcelVO.class,
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
    List<ResourceVO> list = new ArrayList<>();
    LOG.info("======开始导入api资源数据!======");
    try {
      ApiResourceBasicInfoUploadDataListener apiResourceBasicInfoUploadDataListener = new ApiResourceBasicInfoUploadDataListener(apiResourceExcelBatchService);
      EasyExcel.read(
          file.getInputStream(),
          ResourceVO.class,
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
    List<ResourceExcelVO> dsdCodeInfoVOList = GetResourcesAll(serviceId);
    response.setContentType("application/json;charset=utf-8");
    String fileName = URLEncoder.encode("资源基本信息", "UTF-8").replaceAll("\\+", "%20");
    response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
    EasyExcel.write(response.getOutputStream(), ResourceExcelVO.class)
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
    List<ResourceVO> apiResourcesList = GetApiAll(serviceId);
    response.setContentType("application/json;charset=utf-8");
    String fileName = URLEncoder.encode("api资源基本信息", "UTF-8").replaceAll("\\+", "%20");
    response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
    EasyExcel.write(response.getOutputStream(), ResourceVO.class)
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
    List<AtyUserInfoVO> usersList = atyUserService.getUsers(realm, search);
    List<AtyUserExcelVO> atyUserExcelVOList= new ArrayList<>();
    usersList.forEach(atyUserInfoVO -> {
      AtyUserExcelVO atyUserExcelVO = new AtyUserExcelVO();
      AtyUserMapper.INSTANCE.userExcel( atyUserInfoVO,atyUserExcelVO);
      atyUserExcelVO.setCreatedTime(getTime(atyUserInfoVO.getCreatedTimestamp()));
      atyUserExcelVOList.add(atyUserExcelVO);
    });
    response.setContentType("application/json;charset=utf-8");
    String fileName = URLEncoder.encode("用户列表基本信息", "UTF-8").replaceAll("\\+", "%20");
    response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
    EasyExcel.write(response.getOutputStream(), AtyUserExcelVO.class)
        .sheet("用户列表基本信息")
        .doWrite(atyUserExcelVOList);

  }

  private Date getTime(String createdTimestamp) {
    Date parse = null;
    try {
      parse = this.format.parse(createdTimestamp);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return parse;
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
      UserBasicInfoUploadDataListener userBasicInfoUploadDataListener = new UserBasicInfoUploadDataListener(userExcelBatchService);
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
  private List<ResourceVO> GetApiAll(String serviceId) {
    List<ResourceVO> resourceVOList = new ArrayList<>();
    List<QxResources> qxResourcesList = qkQxResourcesRepository.findByServiceId(serviceId);
    //筛选资源数据
    List<QxResources> list = qxResourcesList.stream().filter(qxResources -> qxResources.getPid() == QxConstant.PID
    ).collect(Collectors.toList());
    if (CollectionUtils.isNotEmpty(list)){
      resourceVOList = QxResourcesMapper.INSTANCE.qxResourcesOf(list);
    }
    return resourceVOList;
  }

  /**
   * 获取对应服务的资源数据
   * @param serviceId
   * @return
   */
  private List<ResourceExcelVO> GetResourcesAll(String serviceId) {
    List<ResourceExcelVO> resourceOutVOList = new ArrayList<>();
    List<QxResources> qxResourcesList = qkQxResourcesRepository.findByServiceId(serviceId);
    //筛选资源数据
    List<QxResources> list = qxResourcesList.stream().filter(qxResources -> qxResources.getPid()!= QxConstant.PID
    ).collect(Collectors.toList());
    Map<Long,String> map = dealResources(list);
    if (CollectionUtils.isNotEmpty(list)){
      resourceOutVOList = QxResourcesMapper.INSTANCE.ofExcelVO(list);
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
  private Map<Long,String> dealResources(List<QxResources> list) {
    Map<Long,String> map  = new HashMap<>();
    if (CollectionUtils.isNotEmpty(list)){
      list.forEach(qxResources -> {
        map.put(qxResources.getId(),getValue(qxResources.getPid(),list));
      });
    }
    return map;
  }

  private String getValue(Long pid, List<QxResources> list) {
    Optional<QxResources> qxResourcesOptional = list.stream().filter(qxResources -> qxResources.getId().equals(pid)).findFirst();
    if (qxResourcesOptional.isPresent()){
      return qxResourcesOptional.get().getName();
    }
    return QxConstant.PID_NAME;
  }
}