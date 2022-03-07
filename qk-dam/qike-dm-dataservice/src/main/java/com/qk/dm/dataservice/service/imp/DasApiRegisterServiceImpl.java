package com.qk.dm.dataservice.service.imp;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.google.gson.reflect.TypeToken;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dm.dataservice.biz.BulkProcessApiDataService;
import com.qk.dm.dataservice.config.ApiSixConnectInfo;
import com.qk.dm.dataservice.constant.ApiTypeEnum;
import com.qk.dm.dataservice.constant.RegisterBackendParamHeaderInfoEnum;
import com.qk.dm.dataservice.constant.RegisterConstantParamHeaderInfoEnum;
import com.qk.dm.dataservice.dto.ApiRegisterDTO;
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

import java.util.*;
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
    private final BulkProcessApiDataService bulkProcessApiDataService;

    @Autowired
    public DasApiRegisterServiceImpl(
            DasApiBasicInfoService dasApiBasicInfoService,
            DasApiBasicInfoRepository dasApiBasicinfoRepository,
            DasApiRegisterRepository dasApiRegisterRepository,
            ApiSixConnectInfo apiSixConnectInfo,
            BulkProcessApiDataService bulkProcessApiDataService) {
        this.dasApiBasicInfoService = dasApiBasicInfoService;
        this.dasApiRegisterRepository = dasApiRegisterRepository;
        this.dasApiBasicinfoRepository = dasApiBasicinfoRepository;
        this.apiSixConnectInfo = apiSixConnectInfo;
        this.bulkProcessApiDataService = bulkProcessApiDataService;
    }

    @Override
    public DasApiRegisterVO detail(String apiId) {
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
            DasApiBasicInfoVO dasApiBasicInfoVO =
                    setDasApiBasicInfoDelInputParam(onDasApiBasicInfo.get());
            return DasApiRegisterVO.builder().apiBasicInfoVO(dasApiBasicInfoVO).build();
        }
        DasApiRegisterVO.DasApiRegisterVOBuilder dasApiRegisterVOBuilder = DasApiRegisterVO.builder();
        // API基础信息,设置入参定义VO转换对象
        DasApiBasicInfoVO dasApiBasicInfoVO = setDasApiBasicInfoDelInputParam(onDasApiBasicInfo.get());
        dasApiRegisterVOBuilder.apiBasicInfoVO(dasApiBasicInfoVO);
        // 注册API信息
        DasApiRegister dasApiRegister = onDasApiRegister.get();
        DasApiRegisterDefinitionVO dasApiRegisterDefinitionVO = transformToRegisterDefinitionVO(dasApiRegister);
        // 注册API配置信息,设置后端参数VO转换对象
        setRegisterVOBackendAndConstantsParams(dasApiRegister, dasApiRegisterDefinitionVO);
        dasApiRegisterVOBuilder.apiRegisterDefinitionVO(dasApiRegisterDefinitionVO);
        return dasApiRegisterVOBuilder.build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(DasApiRegisterVO dasApiRegisterVO) {
        String apiId = UUID.randomUUID().toString().replaceAll("-", "");
        // 保存API基础信息
        DasApiBasicInfoVO dasApiBasicInfoVO = dasApiRegisterVO.getApiBasicInfoVO();
        if (dasApiBasicInfoVO == null) {
            throw new BizException("当前新增的API所对应的基础信息为空!!!");
        }
        dasApiBasicInfoVO.setApiId(apiId);
        dasApiBasicInfoVO.setApiType(ApiTypeEnum.REGISTER_API.getCode());
        dasApiBasicInfoService.insert(dasApiBasicInfoVO);
        dasApiRegisterRepository.save(buildSaveApiRegister(dasApiRegisterVO, apiId));
    }

    /**
     * 注册API_批量操作转换DTO(保存)
     *
     * @param dasApiRegisterVO
     */
    public ApiRegisterDTO buildSaveApiRegisterDTO(DasApiRegisterVO dasApiRegisterVO) {
        String apiId = UUID.randomUUID().toString().replaceAll("-", "");
        // API基础信息
        DasApiBasicInfoVO dasApiBasicInfoVO = dasApiRegisterVO.getApiBasicInfoVO();
        if (dasApiBasicInfoVO == null) {
            throw new BizException("当前新增的API所对应的基础信息为空!!!");
        }
        dasApiBasicInfoVO.setApiId(apiId);
        dasApiBasicInfoVO.setApiType(ApiTypeEnum.REGISTER_API.getCode());
        DasApiBasicInfo dasApiBasicInfo = dasApiBasicInfoService.buildBulkSaveApiBasicInfo(dasApiBasicInfoVO);
        // 注册API信息
        DasApiRegister dasApiRegister = buildSaveApiRegister(dasApiRegisterVO, apiId);
        //构建ApiRegisterDTO
        return ApiRegisterDTO.builder().dasApiBasicInfo(dasApiBasicInfo).dasApiRegister(dasApiRegister).build();
    }

    /**
     * 构建保存注册Api信息
     *
     * @param dasApiRegisterVO
     * @param apiId
     * @return
     */
    private DasApiRegister buildSaveApiRegister(DasApiRegisterVO dasApiRegisterVO, String apiId) {
        // 保存注册API信息
        DasApiRegisterDefinitionVO dasApiRegisterDefinitionVO = dasApiRegisterVO.getApiRegisterDefinitionVO();
        DasApiRegister dasApiRegister = transformToRegisterEntity(dasApiRegisterDefinitionVO);
        setBackendRequestParaJson(dasApiRegisterDefinitionVO, dasApiRegister);
        setBackendConstantJson(dasApiRegisterDefinitionVO, dasApiRegister);
        dasApiRegister.setApiId(apiId);
        dasApiRegister.setBackendTimeout(String.valueOf(apiSixConnectInfo.getUpstreamConnectTimeOut()));

        dasApiRegister.setGmtCreate(new Date());
        dasApiRegister.setGmtModified(new Date());
        dasApiRegister.setCreateUserid("admin");
        dasApiRegister.setDelFlag(0);
        return dasApiRegister;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(DasApiRegisterVO dasApiRegisterVO) {
        // 更新API基础信息
        DasApiBasicInfoVO dasApiBasicInfoVO = dasApiRegisterVO.getApiBasicInfoVO();
        dasApiBasicInfoVO.setApiType(ApiTypeEnum.REGISTER_API.getCode());
        dasApiBasicInfoService.update(dasApiBasicInfoVO);
        // 更新注册API
        DasApiRegister dasApiRegister = buildUpdateDasApiRegister(dasApiRegisterVO);

        Predicate predicate = qDasApiRegister.apiId.eq(dasApiRegister.getApiId());
        boolean exists = dasApiRegisterRepository.exists(predicate);
        if (exists) {
            dasApiRegisterRepository.saveAndFlush(dasApiRegister);
        } else {
            throw new BizException("当前要更新的注册API名称为:" + dasApiBasicInfoVO.getApiName() + " 数据的配置信息，不存在！！！");
        }
    }

    /**
     * 注册API_批量操作转换DTO(更新)
     *
     * @param dasApiRegisterVO
     * @return
     */
    public ApiRegisterDTO buildUpdateApiRegisterDTO(DasApiRegisterVO dasApiRegisterVO) {
        // 更新API基础信息
        DasApiBasicInfoVO dasApiBasicInfoVO = dasApiRegisterVO.getApiBasicInfoVO();
        dasApiBasicInfoVO.setApiType(ApiTypeEnum.REGISTER_API.getCode());
        DasApiBasicInfo dasApiBasicInfo = dasApiBasicInfoService.buildUpdateApiBasicInfo(dasApiBasicInfoVO);

        //更新注册API
        DasApiRegister dasApiRegister = buildUpdateDasApiRegister(dasApiRegisterVO);

        Predicate predicate = qDasApiRegister.apiId.eq(dasApiRegister.getApiId());
        boolean exists = dasApiRegisterRepository.exists(predicate);
        if (!exists) {
            throw new BizException("当前要更新的注册API名称为:" + dasApiBasicInfoVO.getApiName() + " 数据的配置信息，不存在！！！");
        }

        //构建ApiRegisterDTO
        return ApiRegisterDTO.builder().dasApiBasicInfo(dasApiBasicInfo).dasApiRegister(dasApiRegister).build();
    }

    /**
     * 构建注册API更新对象信息
     *
     * @param dasApiRegisterVO
     * @return
     */
    private DasApiRegister buildUpdateDasApiRegister(DasApiRegisterVO dasApiRegisterVO) {
        // 更新注册API
        DasApiRegisterDefinitionVO dasApiRegisterDefinitionVO = dasApiRegisterVO.getApiRegisterDefinitionVO();
        //校验更新ID参数
        checkUpdateParams(dasApiRegisterDefinitionVO);
        DasApiRegister dasApiRegister = transformToRegisterEntity(dasApiRegisterDefinitionVO);
        //请求参数转换
        setBackendRequestParaJson(dasApiRegisterDefinitionVO, dasApiRegister);
        setBackendConstantJson(dasApiRegisterDefinitionVO, dasApiRegister);

        dasApiRegister.setBackendTimeout(String.valueOf(apiSixConnectInfo.getUpstreamConnectTimeOut()));

        dasApiRegister.setGmtModified(new Date());
        dasApiRegister.setCreateUserid("admin");
        dasApiRegister.setDelFlag(0);
        return dasApiRegister;
    }

    @Override
    public LinkedList<Map<String, Object>> getRegisterBackendParaHeaderInfo() {
        return RegisterBackendParamHeaderInfoEnum.getAllValue();
    }

    @Override
    public LinkedList<Map<String, Object>> getRegisterConstantParaHeaderInfo() {
        return RegisterConstantParamHeaderInfoEnum.getAllValue();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void bulkAddDasApiRegister(List<DasApiRegisterVO> dasApiRegisterVOList) {
        //新增
        List<ApiRegisterDTO> saveDataList = new ArrayList<>();
        //更新
        List<ApiRegisterDTO> updateDataList = new ArrayList<>();

        if (!ObjectUtils.isEmpty(dasApiRegisterVOList)) {
            try {
                for (DasApiRegisterVO dasApiRegisterVO : dasApiRegisterVOList) {
                    //API基础信息
                    DasApiBasicInfoVO dasApiBasicInfoVO = dasApiRegisterVO.getApiBasicInfoVO();
                    //检查是否存在要同步的API信息
                    Optional<DasApiBasicInfo> optionalDasApiBasicInfo = dasApiBasicInfoService.checkExistApiBasicInfo(dasApiBasicInfoVO);
                    if (optionalDasApiBasicInfo.isPresent()) {
                        DasApiBasicInfo dasApiBasicInfo = optionalDasApiBasicInfo.get();
                        Long id = dasApiBasicInfo.getId();
                        String apiId = dasApiBasicInfo.getApiId();
                        Optional<DasApiRegister> optionalDasApiRegister = dasApiRegisterRepository.findOne(qDasApiRegister.apiId.eq(apiId));
                        if (optionalDasApiRegister.isPresent()) {
                            // API基础信息
                            dasApiBasicInfoVO.setId(id);
                            dasApiBasicInfoVO.setApiId(apiId);
                            dasApiRegisterVO.setApiBasicInfoVO(dasApiBasicInfoVO);
                            // 注册API信息
                            DasApiRegisterDefinitionVO dasApiRegisterDefinitionVO = dasApiRegisterVO.getApiRegisterDefinitionVO();
                            dasApiRegisterDefinitionVO.setId(optionalDasApiRegister.get().getId());
                            dasApiRegisterDefinitionVO.setApiId(apiId);
                            //更新操作
                            updateDataList.add(buildUpdateApiRegisterDTO(dasApiRegisterVO));
                        } else {
                            //TODO 更新基础信息,新增注册API,只有不直接操作数据库就不应该需要处理
//                            buildSaveApiRegister(dasApiRegisterVO, dasApiBasicInfo.getApiId());
                        }
                    } else {
                        //保存新增
                        saveDataList.add(buildSaveApiRegisterDTO(dasApiRegisterVO));
                    }
                }
                //执行新增OR更新操作
                bulkProcessApiDataService.bulkSaveRegisterApi(saveDataList);
                bulkProcessApiDataService.bulkModifySRegisterApi(updateDataList);
            } catch (Exception e) {
                e.printStackTrace();
                throw new BizException(e.getMessage());
            }
        }
        LOG.info("同步OpenApi,新增注册API个数为: 【{}】", saveDataList.size());
        LOG.info("同步OpenApi,更新注册API个数为: 【{}】", updateDataList.size());
    }

    @Override
    public List<DasApiRegisterVO> findAll() {
        List<DasApiRegisterVO> dasApiRegisterVOList = new ArrayList<>();
        List<DasApiRegister> dasApiRegisterList = dasApiRegisterRepository.findAll();
        List<DasApiBasicInfoVO> dasApiBasicInfoVOList = dasApiBasicInfoService.findAllByApiType(ApiTypeEnum.REGISTER_API.getCode());
        Map<String, List<DasApiBasicInfoVO>> apiBasicMap =
                dasApiBasicInfoVOList.stream().collect(Collectors.groupingBy(DasApiBasicInfoVO::getApiId));

        dasApiRegisterList.forEach(
                dasApiRegister -> {
                    DasApiRegisterVO.DasApiRegisterVOBuilder dasApiRegisterVOBuilder = DasApiRegisterVO.builder();
                    // API基础信息
                    DasApiBasicInfoVO dasApiBasicInfoVO = apiBasicMap.get(dasApiRegister.getApiId()).get(0);
                    dasApiRegisterVOBuilder.apiBasicInfoVO(dasApiBasicInfoVO);
                    //注册API信息
                    DasApiRegisterDefinitionVO dasApiRegisterDefinitionVO = transformToRegisterDefinitionVO(dasApiRegister);
                    setRegisterVOBackendAndConstantsParams(dasApiRegister, dasApiRegisterDefinitionVO);
                    dasApiRegisterVOBuilder.apiRegisterDefinitionVO(dasApiRegisterDefinitionVO);

                    dasApiRegisterVOList.add(dasApiRegisterVOBuilder.build());
                });
        return dasApiRegisterVOList;
    }

    // =====================transformToVO=================================
    private DasApiRegisterDefinitionVO transformToRegisterDefinitionVO(DasApiRegister dasApiRegister) {
        return DasApiRegisterMapper.INSTANCE.useDasApiRegisterDefinitioVO(dasApiRegister);
    }

    private void setRegisterVOBackendAndConstantsParams(DasApiRegister dasApiRegister, DasApiRegisterDefinitionVO dasApiRegisterDefinitionVO) {
        if (null != dasApiRegister.getBackendRequestParas() && dasApiRegister.getBackendRequestParas().length() > 0) {
            dasApiRegisterDefinitionVO.setDasApiRegisterBackendParaVO(
                    GsonUtil.fromJsonString(dasApiRegister.getBackendRequestParas(),
                            new TypeToken<List<DasApiRegisterBackendParaVO>>() {
                            }.getType()));
        }

        if (null != dasApiRegister.getBackendConstants() && dasApiRegister.getBackendConstants().length() > 0) {
            dasApiRegisterDefinitionVO.setDasApiRegisterConstantParaVO(
                    GsonUtil.fromJsonString(dasApiRegister.getBackendConstants(),
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

    // =====================transformToEntity=================================
    private DasApiRegister transformToRegisterEntity(DasApiRegisterDefinitionVO dasApiRegisterDefinitionVO) {
        return DasApiRegisterMapper.INSTANCE.useDasApiRegister(dasApiRegisterDefinitionVO);
    }

    private void setBackendRequestParaJson(DasApiRegisterDefinitionVO dasApiRegisterDefinitionVO, DasApiRegister dasApiRegister) {
        if (!ObjectUtils.isEmpty(dasApiRegisterDefinitionVO.getDasApiRegisterBackendParaVO())) {
            dasApiRegister.setBackendRequestParas(
                    GsonUtil.toJsonString(dasApiRegisterDefinitionVO.getDasApiRegisterBackendParaVO()));
        }
    }

    private void setBackendConstantJson(DasApiRegisterDefinitionVO dasApiRegisterDefinitionVO, DasApiRegister dasApiRegister) {
        if (!ObjectUtils.isEmpty(dasApiRegisterDefinitionVO.getDasApiRegisterConstantParaVO())) {
            dasApiRegister.setBackendConstants(
                    GsonUtil.toJsonString(dasApiRegisterDefinitionVO.getDasApiRegisterConstantParaVO()));
        }
    }

    /**
     * 校验更新ID参数
     *
     * @param dasApiRegisterDefinitionVO
     */
    private void checkUpdateParams(DasApiRegisterDefinitionVO dasApiRegisterDefinitionVO) {
        if (ObjectUtils.isEmpty(dasApiRegisterDefinitionVO.getId())) {
            throw new BizException("注册API信息,ID参数为空,编辑失败!!!");
        }

        if (ObjectUtils.isEmpty(dasApiRegisterDefinitionVO.getApiId())) {
            throw new BizException("注册API信息,apiId参数为空,编辑失败!!!");
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
    //                List<DasApiBasicInfoRequestParasVO> requestParasVOList =
    // dasApiBasicInfoVO.getDasApiBasicInfoRequestParasVO();
    //                if (ObjectUtils.isEmpty(requestParasVOList)) {
    //                    optionalDasApiBasicInfo =
    // dasApiBasicInfoService.searchApiBasicInfoByDelParamIsEmpty(dasApiBasicInfoVO);
    //                } else {
    //                    List<DasApiBasicInfo> dasApiBasicInfoList =
    // dasApiBasicInfoService.checkExistApiBasicInfo(dasApiRegisterVO.getDasApiBasicInfoVO());
    //                    delInputParamJsonList =
    // dasApiBasicInfoList.stream().map(DasApiBasicInfo::getDefInputParam).collect(Collectors.toList());
    //                    searchMap =
    // dasApiBasicInfoList.stream().collect(Collectors.groupingBy(DasApiBasicInfo::getId));
    //                }
    //                Map<String, Object> checkExistApiMap = checkExistApi(optionalDasApiBasicInfo,
    // requestParasVOList, delInputParamJsonList, searchMap);
    //                boolean flag = (boolean) checkExistApiMap.get(EXIST_FLAG);
    //                if (flag) {
    //                    dasApiBasicInfoService.deleteDasApiBasicInfo((Long)
    // checkExistApiMap.get(EXIST_ID));
    //                    updateDasApiRegister(dasApiRegisterVO);
    //                }
    //                addDasApiRegister(dasApiRegisterVO);
    //            }
    //        }
    //    }
    //
    //    private Map<String, Object> checkExistApi(Optional<DasApiBasicInfo> optionalDasApiBasicInfo,
    // List<DasApiBasicInfoRequestParasVO> requestParasVOList, List<String> delInputParamJsonList,
    // Map<Long, List<DasApiBasicInfo>> searchMap) {
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
    //            flag = sameRequestParams(requestParasVOList, delInputParamJsonList, searchMap,
    // checkExistApiMap, flag);
    //        }
    //        checkExistApiMap.put(EXIST_FLAG, flag);
    //        return checkExistApiMap;
    //    }
    //
    //    private boolean sameRequestParams(List<DasApiBasicInfoRequestParasVO> requestParasVOList,
    // List<String> delInputParamJsonList, Map<Long, List<DasApiBasicInfo>> searchMap, Map<String,
    // Object> checkExistApiMap, boolean flag) {
    //        if (!ObjectUtils.isEmpty(delInputParamJsonList)) {
    //            //查询到的参数不为空
    //            List<String> paraNameList = requestParasVOList.stream()
    //
    // .map(DasApiBasicInfoRequestParasVO::getParaName).collect(Collectors.toList());
    //            for (Map.Entry<Long, List<DasApiBasicInfo>> entry : searchMap.entrySet()) {
    //                DasApiBasicInfo dasApiBasicInfo = entry.getValue().get(0);
    //                String defInputParamJson = dasApiBasicInfo.getDefInputParam();
    //                List<DasApiBasicInfoRequestParasVO> basicInfoRequestParasVOList =
    //                        GsonUtil.fromJsonString(defInputParamJson, new
    // TypeToken<List<DasApiBasicInfoRequestParasVO>>() {
    //                        }.getType());
    //                List<DasApiBasicInfoRequestParasVO> compareList =
    // basicInfoRequestParasVOList.stream()
    //                        .filter(dasApiBasicInfoRequestParasVO ->
    // !paraNameList.contains(dasApiBasicInfoRequestParasVO.getParaName()))
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
