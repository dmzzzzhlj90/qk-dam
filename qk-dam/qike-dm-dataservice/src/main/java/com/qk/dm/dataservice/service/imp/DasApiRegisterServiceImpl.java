package com.qk.dm.dataservice.service.imp;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.google.gson.reflect.TypeToken;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dm.dataservice.config.ApiSixConnectInfo;
import com.qk.dm.dataservice.constant.DasConstant;
import com.qk.dm.dataservice.constant.SyncStatusEnum;
import com.qk.dm.dataservice.entity.DasApiBasicInfo;
import com.qk.dm.dataservice.entity.DasApiRegister;
import com.qk.dm.dataservice.entity.QDasApiBasicInfo;
import com.qk.dm.dataservice.entity.QDasApiRegister;
import com.qk.dm.dataservice.mapstruct.mapper.DasApiBasicInfoMapper;
import com.qk.dm.dataservice.mapstruct.mapper.DasApiRegisterMapper;
import com.qk.dm.dataservice.repositories.DasApiBasicInfoRepository;
import com.qk.dm.dataservice.repositories.DasApiRegisterRepository;
import com.qk.dm.dataservice.service.DasApiBasicInfoService;
import com.qk.dm.dataservice.service.DasApiRegisterService;
import com.qk.dm.dataservice.vo.*;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 数据服务_注册API
 *
 * @author wjq
 * @date 20210817
 * @since 1.0.0
 */
@Service
public class DasApiRegisterServiceImpl implements DasApiRegisterService {
    private static final Log LOG = LogFactory.get("数据服务_注册API操作");

    private static final QDasApiBasicInfo qDasApiBasicInfo = QDasApiBasicInfo.dasApiBasicInfo;
    private static final QDasApiRegister qDasApiRegister = QDasApiRegister.dasApiRegister;

    private final DasApiBasicInfoService dasApiBasicInfoService;
    private final DasApiBasicInfoRepository dasApiBasicinfoRepository;
    private final DasApiRegisterRepository dasApiRegisterRepository;
    private final ApiSixConnectInfo apiSixConnectInfo;


    @Autowired
    public DasApiRegisterServiceImpl(
            DasApiBasicInfoService dasApiBasicInfoService,
            DasApiBasicInfoRepository dasApiBasicinfoRepository,
            DasApiRegisterRepository dasApiRegisterRepository,
            EntityManager entityManager, ApiSixConnectInfo apiSixConnectInfo) {
        this.dasApiBasicInfoService = dasApiBasicInfoService;
        this.dasApiRegisterRepository = dasApiRegisterRepository;
        this.dasApiBasicinfoRepository = dasApiBasicinfoRepository;
        this.apiSixConnectInfo = apiSixConnectInfo;
    }


    @Override
    public DasApiRegisterVO getDasApiRegisterInfoByApiId(String apiId) {
        // 获取API基础信息
        Optional<DasApiBasicInfo> onDasApiBasicInfo =
                dasApiBasicinfoRepository.findOne(qDasApiBasicInfo.apiId.eq(apiId));
        if (onDasApiBasicInfo.isEmpty()) {
            throw new BizException("查询不到对应的API基础信息!!!");
        }
        // 获取注册API信息
        Optional<DasApiRegister> onDasApiRegister =
                dasApiRegisterRepository.findOne(qDasApiRegister.apiId.eq(apiId));
        if (onDasApiRegister.isEmpty()) {
            DasApiBasicInfoVO dasApiBasicInfoVO = setDasApiBasicInfoDelInputParam(onDasApiBasicInfo.get());
            return DasApiRegisterVO.builder().dasApiBasicInfoVO(dasApiBasicInfoVO).build();
        }
        DasApiRegister dasApiRegister = onDasApiRegister.get();
        DasApiRegisterVO dasApiRegisterVO = transformToRegisterVO(dasApiRegister);
        // API基础信息,设置入参定义VO转换对象
        DasApiBasicInfoVO dasApiBasicInfoVO = setDasApiBasicInfoDelInputParam(onDasApiBasicInfo.get());
        dasApiRegisterVO.setDasApiBasicInfoVO(dasApiBasicInfoVO);
        // 注册API配置信息,设置后端参数VO转换对象
        setRegisterVOBackendAndConstantsParams(dasApiRegister, dasApiRegisterVO);
        return dasApiRegisterVO;
    }


    @Transactional
    @Override
    public void addDasApiRegister(DasApiRegisterVO dasApiRegisterVO) {
        String apiId = UUID.randomUUID().toString().replaceAll("-", "");
        // 保存API基础信息
        DasApiBasicInfoVO dasApiBasicInfoVO = dasApiRegisterVO.getDasApiBasicInfoVO();
        if (dasApiBasicInfoVO == null) {
            throw new BizException("当前新增的API所对应的基础信息为空!!!");
        }
        dasApiBasicInfoVO.setApiId(apiId);
        dasApiBasicInfoService.addDasApiBasicInfo(dasApiBasicInfoVO);
        singleUpdateApiRegister(dasApiRegisterVO, apiId);

    }

    private void singleUpdateApiRegister(DasApiRegisterVO dasApiRegisterVO, String apiId) {
        // 保存注册API信息
        DasApiRegister dasApiRegister = transformToRegisterEntity(dasApiRegisterVO);
        setBackendRequestParaJson(dasApiRegisterVO, dasApiRegister);
        setBackendConstantJson(dasApiRegisterVO, dasApiRegister);
        dasApiRegister.setApiId(apiId);
        dasApiRegister.setBackendTimeout(String.valueOf(apiSixConnectInfo.getUpstreamConnectTimeOut()));
        dasApiRegister.setStatus(SyncStatusEnum.CREATE_NO_SYNC.getCode());
        dasApiRegister.setGmtCreate(new Date());
        dasApiRegister.setGmtModified(new Date());
        dasApiRegister.setDelFlag(0);
        dasApiRegisterRepository.save(dasApiRegister);
    }

    @Transactional
    @Override
    public void updateDasApiRegister(DasApiRegisterVO dasApiRegisterVO) {
        // 更新API基础信息
        DasApiBasicInfoVO dasApiBasicInfoVO = dasApiRegisterVO.getDasApiBasicInfoVO();
        dasApiBasicInfoService.updateDasApiBasicInfo(dasApiBasicInfoVO);
        // 更新注册API
        DasApiRegister dasApiRegister = transformToRegisterEntity(dasApiRegisterVO);
        setBackendRequestParaJson(dasApiRegisterVO, dasApiRegister);
        setBackendConstantJson(dasApiRegisterVO, dasApiRegister);
        dasApiRegister.setBackendTimeout(String.valueOf(apiSixConnectInfo.getUpstreamConnectTimeOut()));
        dasApiRegister.setStatus(SyncStatusEnum.CREATE_NO_SYNC.getCode());
        dasApiRegister.setGmtModified(new Date());
        dasApiRegister.setDelFlag(0);
        Predicate predicate = qDasApiRegister.apiId.eq(dasApiRegister.getApiId());
        boolean exists = dasApiRegisterRepository.exists(predicate);
        if (exists) {
            dasApiRegisterRepository.saveAndFlush(dasApiRegister);
        } else {
            throw new BizException(
                    "当前要新增的注册API名称为:" + dasApiBasicInfoVO.getApiName() + " 数据的配置信息，不存在！！！");
        }
    }

    @Override
    public Map<String, String> getRegisterBackendParaHeaderInfo() {
        return DasConstant.getRegisterBackendParaHeaderInfo();
    }

    @Override
    public Map<String, String> getRegisterConstantParaHeaderInfo() {
        return DasConstant.getRegisterConstantParaHeaderInfo();
    }

    @Transactional
    @Override
    public void bulkAddDasApiRegister(List<DasApiRegisterVO> dasApiRegisterVOList) {
        AtomicInteger saveCount = new AtomicInteger(0);
        AtomicInteger updateCount = new AtomicInteger(0);
        if (!ObjectUtils.isEmpty(dasApiRegisterVOList)) {
            try {
                for (DasApiRegisterVO dasApiRegisterVO : dasApiRegisterVOList) {
                    DasApiBasicInfoVO dasApiBasicInfoVO = dasApiRegisterVO.getDasApiBasicInfoVO();
                    Optional<DasApiBasicInfo> optionalDasApiBasicInfo = dasApiBasicInfoService.checkExistApiBasicInfo(dasApiBasicInfoVO);
                    if (optionalDasApiBasicInfo.isPresent()) {
                        DasApiBasicInfo dasApiBasicInfo = optionalDasApiBasicInfo.get();
                        Long id = dasApiBasicInfo.getId();
                        String apiId = dasApiBasicInfo.getApiId();
                        Optional<DasApiRegister> optionalDasApiRegister = dasApiRegisterRepository.findOne(qDasApiRegister.apiId.eq(apiId));
                        if (optionalDasApiRegister.isPresent()) {
                            dasApiBasicInfoVO.setId(id);
                            dasApiBasicInfoVO.setApiId(apiId);
                            dasApiRegisterVO.setDasApiBasicInfoVO(dasApiBasicInfoVO);
                            dasApiRegisterVO.setId(optionalDasApiRegister.get().getId());
                            dasApiRegisterVO.setApiId(apiId);
                            updateDasApiRegister(dasApiRegisterVO);
                        } else {
                            singleUpdateApiRegister(dasApiRegisterVO, dasApiBasicInfo.getApiId());
                        }
                        updateCount.getAndIncrement();
                    } else {
                        addDasApiRegister(dasApiRegisterVO);
                        saveCount.getAndIncrement();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new BizException(e.getMessage());
            }
        }
        LOG.info("同步OpenApi,新增注册API个数为: 【{}】", saveCount);
        LOG.info("同步OpenApi,更新注册API个数为: 【{}】", updateCount);

    }

    @Override
    public List<DasApiRegisterVO> findAll() {
        List<DasApiRegisterVO> dasApiRegisterVOList = new ArrayList<>();
        List<DasApiRegister> dasApiRegisterList = dasApiRegisterRepository.findAll();
        List<DasApiBasicInfoVO> dasApiBasicInfoVOList = dasApiBasicInfoService.findAllByApiType(DasConstant.REGISTER_API_CODE);
        Map<String, List<DasApiBasicInfoVO>> apiBasicMap = dasApiBasicInfoVOList.stream().collect(Collectors.groupingBy(DasApiBasicInfoVO::getApiId));

        dasApiRegisterList.forEach(dasApiRegister -> {
            DasApiRegisterVO dasApiRegisterVO = transformToRegisterVO(dasApiRegister);
            DasApiBasicInfoVO dasApiBasicInfoVO = apiBasicMap.get(dasApiRegister.getApiId()).get(0);
            dasApiRegisterVO.setDasApiBasicInfoVO(dasApiBasicInfoVO);
            setRegisterVOBackendAndConstantsParams(dasApiRegister, dasApiRegisterVO);
            dasApiRegisterVOList.add(dasApiRegisterVO);
        });
        return dasApiRegisterVOList;
    }

    //=====================transformToVO=================================
    private DasApiRegisterVO transformToRegisterVO(DasApiRegister dasApiRegister) {
        return DasApiRegisterMapper.INSTANCE.useDasApiRegisterVO(dasApiRegister);
    }

    private void setRegisterVOBackendAndConstantsParams(DasApiRegister dasApiRegister, DasApiRegisterVO dasApiRegisterVO) {
        if (null != dasApiRegister.getBackendRequestParas()
                && dasApiRegister.getBackendRequestParas().length() > 0) {
            dasApiRegisterVO.setDasApiRegisterBackendParaVO(
                    GsonUtil.fromJsonString(
                            dasApiRegister.getBackendRequestParas(),
                            new TypeToken<List<DasApiRegisterBackendParaVO>>() {
                            }.getType()));
        }

        if (null != dasApiRegister.getBackendConstants()
                && dasApiRegister.getBackendConstants().length() > 0) {

            dasApiRegisterVO.setDasApiRegisterConstantParaVO(
                    GsonUtil.fromJsonString(
                            dasApiRegister.getBackendConstants(),
                            new TypeToken<List<DasApiRegisterConstantParaVO>>() {
                            }.getType()));
        }
    }

    private DasApiBasicInfoVO setDasApiBasicInfoDelInputParam(DasApiBasicInfo dasApiBasicInfo) {
        DasApiBasicInfoVO dasApiBasicInfoVO = transformToBasicEntity(dasApiBasicInfo);
        String defInputParam = dasApiBasicInfo.getDefInputParam();
        if (defInputParam != null && defInputParam.length() != 0) {
            dasApiBasicInfoVO.setDasApiBasicInfoRequestParasVO(
                    GsonUtil.fromJsonString(
                            dasApiBasicInfo.getDefInputParam(),
                            new TypeToken<List<DasApiBasicInfoRequestParasVO>>() {
                            }.getType()));
        }
        return dasApiBasicInfoVO;
    }

    private DasApiBasicInfoVO transformToBasicEntity(DasApiBasicInfo dasApiBasicInfo) {
        return DasApiBasicInfoMapper.INSTANCE.useDasApiBasicInfoVO(dasApiBasicInfo);
    }

    //=====================transformToEntity=================================
    private DasApiRegister transformToRegisterEntity(DasApiRegisterVO dasApiRegisterVO) {
        return DasApiRegisterMapper.INSTANCE.useDasApiRegister(dasApiRegisterVO);
    }

    private void setBackendRequestParaJson(DasApiRegisterVO dasApiRegisterVO, DasApiRegister dasApiRegister) {
        if (!ObjectUtils.isEmpty(dasApiRegisterVO.getDasApiRegisterBackendParaVO())) {
            dasApiRegister.setBackendRequestParas(
                    GsonUtil.toJsonString(dasApiRegisterVO.getDasApiRegisterBackendParaVO()));
        }
    }

    private void setBackendConstantJson(DasApiRegisterVO dasApiRegisterVO, DasApiRegister dasApiRegister) {
        if (!ObjectUtils.isEmpty(dasApiRegisterVO.getDasApiRegisterConstantParaVO())) {
            dasApiRegister.setBackendConstants(
                    GsonUtil.toJsonString(dasApiRegisterVO.getDasApiRegisterConstantParaVO()));
        }
    }

//    @Transactional
//    @Override
//    public void bulkAddDasApiRegister(List<DasApiRegisterVO> dasApiRegisterVOList) {
//        Optional<DasApiBasicInfo> optionalDasApiBasicInfo = null;
//        List<String> delInputParamJsonList = null;
//        Map<Long, List<DasApiBasicInfo>> searchMap = null;
//
//        if (null != dasApiRegisterVOList && dasApiRegisterVOList.size() > 0) {
//            for (DasApiRegisterVO dasApiRegisterVO : dasApiRegisterVOList) {
//                DasApiBasicInfoVO dasApiBasicInfoVO = dasApiRegisterVO.getDasApiBasicInfoVO();
//                List<DasApiBasicInfoRequestParasVO> requestParasVOList = dasApiBasicInfoVO.getDasApiBasicInfoRequestParasVO();
//                if (ObjectUtils.isEmpty(requestParasVOList)) {
//                    optionalDasApiBasicInfo = dasApiBasicInfoService.searchApiBasicInfoByDelParamIsEmpty(dasApiBasicInfoVO);
//                } else {
//                    List<DasApiBasicInfo> dasApiBasicInfoList = dasApiBasicInfoService.checkExistApiBasicInfo(dasApiRegisterVO.getDasApiBasicInfoVO());
//                    delInputParamJsonList = dasApiBasicInfoList.stream().map(DasApiBasicInfo::getDefInputParam).collect(Collectors.toList());
//                    searchMap = dasApiBasicInfoList.stream().collect(Collectors.groupingBy(DasApiBasicInfo::getId));
//                }
//                Map<String, Object> checkExistApiMap = checkExistApi(optionalDasApiBasicInfo, requestParasVOList, delInputParamJsonList, searchMap);
//                boolean flag = (boolean) checkExistApiMap.get(EXIST_FLAG);
//                if (flag) {
//                    dasApiBasicInfoService.deleteDasApiBasicInfo((Long) checkExistApiMap.get(EXIST_ID));
//                    updateDasApiRegister(dasApiRegisterVO);
//                }
//                addDasApiRegister(dasApiRegisterVO);
//            }
//        }
//    }
//
//    private Map<String, Object> checkExistApi(Optional<DasApiBasicInfo> optionalDasApiBasicInfo, List<DasApiBasicInfoRequestParasVO> requestParasVOList, List<String> delInputParamJsonList, Map<Long, List<DasApiBasicInfo>> searchMap) {
//        Map<String, Object> checkExistApiMap = new HashMap<>();
//        boolean flag = false;
//        if (ObjectUtils.isEmpty(requestParasVOList)) {
//            //参数为空
//            if (optionalDasApiBasicInfo.isPresent()) {
//                //存在
//                checkExistApiMap.put(EXIST_ID, optionalDasApiBasicInfo.get().getId());
//                flag = true;
//            }
//        } else {
//            //是否存在相同的入参
//            flag = sameRequestParams(requestParasVOList, delInputParamJsonList, searchMap, checkExistApiMap, flag);
//        }
//        checkExistApiMap.put(EXIST_FLAG, flag);
//        return checkExistApiMap;
//    }
//
//    private boolean sameRequestParams(List<DasApiBasicInfoRequestParasVO> requestParasVOList, List<String> delInputParamJsonList, Map<Long, List<DasApiBasicInfo>> searchMap, Map<String, Object> checkExistApiMap, boolean flag) {
//        if (!ObjectUtils.isEmpty(delInputParamJsonList)) {
//            //查询到的参数不为空
//            List<String> paraNameList = requestParasVOList.stream()
//                    .map(DasApiBasicInfoRequestParasVO::getParaName).collect(Collectors.toList());
//            for (Map.Entry<Long, List<DasApiBasicInfo>> entry : searchMap.entrySet()) {
//                DasApiBasicInfo dasApiBasicInfo = entry.getValue().get(0);
//                String defInputParamJson = dasApiBasicInfo.getDefInputParam();
//                List<DasApiBasicInfoRequestParasVO> basicInfoRequestParasVOList =
//                        GsonUtil.fromJsonString(defInputParamJson, new TypeToken<List<DasApiBasicInfoRequestParasVO>>() {
//                        }.getType());
//                List<DasApiBasicInfoRequestParasVO> compareList = basicInfoRequestParasVOList.stream()
//                        .filter(dasApiBasicInfoRequestParasVO -> !paraNameList.contains(dasApiBasicInfoRequestParasVO.getParaName()))
//                        .collect(Collectors.toList());
//                if (ObjectUtils.isEmpty(compareList)) {
//                    //参数相同
//                    checkExistApiMap.put(EXIST_ID, dasApiBasicInfo.getId());
//                    flag = true;
//                    break;
//                }
//            }
//        }
//        return flag;
//    }

}
