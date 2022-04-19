package com.qk.dm.dataservice.easyexcel;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.google.common.collect.Maps;
import com.qk.dm.dataservice.constant.DasConstant;
import com.qk.dm.dataservice.entity.*;
import com.qk.dm.dataservice.mapstruct.mapper.DasApiBasicInfoMapper;
import com.qk.dm.dataservice.mapstruct.mapper.DasApiCreateConfigMapper;
import com.qk.dm.dataservice.mapstruct.mapper.DasApiCreateSqlScriptMapper;
import com.qk.dm.dataservice.mapstruct.mapper.DasApiRegisterMapper;
import com.qk.dm.dataservice.repositories.*;
import com.qk.dm.dataservice.vo.DasApiBasicInfoVO;
import com.qk.dm.dataservice.vo.DasApiCreateConfigDefinitionVO;
import com.qk.dm.dataservice.vo.DasApiCreateSqlScriptDefinitionVO;
import com.qk.dm.dataservice.vo.DasApiRegisterDefinitionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 数据服务excel 批量导入导出
 *
 * @author wjq
 * @date 2022/4/19
 * @since 1.0.0
 */
@Service
public class ExcelBatchSaveFlushDataService {
    private static final Log LOG = LogFactory.get("ExcelBatchSaveFlushDataService");

    @PersistenceContext
    private EntityManager entityManager;

    private final DasApiDirRepository dasApiDirRepository;
    private final DasApiBasicInfoRepository dasApiBasicInfoRepository;
    private final DasApiCreateConfigRepository dasApiCreateConfigRepository;
    private final DasApiCreateSqlScriptRepository dasApiCreateSqlScriptRepository;
    private final DasApiRegisterRepository dasApiRegisterRepository;

    @Autowired
    public ExcelBatchSaveFlushDataService(DasApiDirRepository dasApiDirRepository,
                                          DasApiBasicInfoRepository dasApiBasicInfoRepository,
                                          DasApiCreateConfigRepository dasApiCreateConfigRepository,
                                          DasApiCreateSqlScriptRepository dasApiCreateSqlScriptRepository,
                                          DasApiRegisterRepository dasApiRegisterRepository) {
        this.dasApiDirRepository = dasApiDirRepository;
        this.dasApiBasicInfoRepository = dasApiBasicInfoRepository;
        this.dasApiCreateConfigRepository = dasApiCreateConfigRepository;
        this.dasApiCreateSqlScriptRepository = dasApiCreateSqlScriptRepository;
        this.dasApiRegisterRepository = dasApiRegisterRepository;
    }


    @Transactional(rollbackFor = Exception.class)
    public void saveFlushBasicInfo(List<DasApiBasicInfoVO> dataList, String dirId) {
        AtomicInteger createIndex = new AtomicInteger(0);
        AtomicInteger updateIndex = new AtomicInteger(0);
        // 目录设置
        String realDirId;
        String realDirName;
        Optional<DasApiDir> dasApiDirOptional = dasApiDirRepository.findOne(QDasApiDir.dasApiDir.dirId.eq(dirId));
        if (dasApiDirOptional.isPresent()) {
            realDirId = dasApiDirOptional.get().getDirId();
            realDirName = dasApiDirOptional.get().getDirName();
        } else {
            realDirId = DasConstant.TREE_DIR_TOP_PARENT_ID;
            realDirName = DasConstant.TREE_DIR_TOP_PARENT_NAME;
        }

        // 根据apiId查询API基础信息
        List<String> apiIds = dataList.stream().map(DasApiBasicInfoVO::getApiId).collect(Collectors.toList());

        Iterable<DasApiBasicInfo> dasApiBasicInfos = dasApiBasicInfoRepository.findAll(QDasApiBasicInfo.dasApiBasicInfo.apiId.in(apiIds));
        HashMap<String, Long> existApiIdMap = Maps.newHashMap();
        dasApiBasicInfos.forEach(apiBasicInfo -> existApiIdMap.put(apiBasicInfo.getApiId(), apiBasicInfo.getId()));
        Set<String> existApiIds = existApiIdMap.keySet();

        for (DasApiBasicInfoVO apiBasicInfoVO : dataList) {
            apiBasicInfoVO.setDirId(realDirId);
            apiBasicInfoVO.setDirName(realDirName);
            DasApiBasicInfo dasApiBasicInfo = DasApiBasicInfoMapper.INSTANCE.useDasApiBasicInfo(apiBasicInfoVO);
            // 设置入参定义JSON
            dasApiBasicInfo.setDefInputParam(apiBasicInfoVO.getRequestParasJSON());

            dasApiBasicInfo.setCreateUserid("admin");
            dasApiBasicInfo.setDelFlag(0);
            if (existApiIds.contains(apiBasicInfoVO.getApiId())) {
                // update 更新操作
                dasApiBasicInfo.setId(existApiIdMap.get(apiBasicInfoVO.getApiId()));
                dasApiBasicInfo.setGmtModified(new Date());
                entityManager.merge(dasApiBasicInfo);
                updateIndex.incrementAndGet();
            } else {
                // insert 更新操作
                dasApiBasicInfo.setGmtCreate(new Date());
                entityManager.persist(dasApiBasicInfo);
                createIndex.incrementAndGet();
            }
        }
        entityManager.flush();
        entityManager.clear();
        LOG.info("新增 API基础信息数量 【{}】", createIndex.get());
        LOG.info("更新 API基础信息数量 【{}】", updateIndex.get());
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveFlushCreateConfig(List<DasApiCreateConfigDefinitionVO> dataList) {
        AtomicInteger createIndex = new AtomicInteger(0);
        AtomicInteger updateIndex = new AtomicInteger(0);
        // 根据apiId查询新建API配置信息
        List<String> apiIds = dataList.stream().map(DasApiCreateConfigDefinitionVO::getApiId).collect(Collectors.toList());

        Iterable<DasApiCreateConfig> apiCreateConfigs = dasApiCreateConfigRepository.findAll(QDasApiCreateConfig.dasApiCreateConfig.apiId.in(apiIds));
        HashMap<String, Long> existApiIdMap = Maps.newHashMap();
        apiCreateConfigs.forEach(createConfig -> existApiIdMap.put(createConfig.getApiId(), createConfig.getId()));
        Set<String> existApiIds = existApiIdMap.keySet();

        for (DasApiCreateConfigDefinitionVO configDefinitionVO : dataList) {
            DasApiCreateConfig apiCreateConfig = DasApiCreateConfigMapper.INSTANCE.useDasApiCreateConfig(configDefinitionVO);

            // 设置请求参数JSON
            apiCreateConfig.setApiRequestParas(configDefinitionVO.getCreateRequestParasJson());
            // 设置响应参数JSON
            apiCreateConfig.setApiResponseParas(configDefinitionVO.getCreateResponseParasJson());
            // 设置排序参数JSON
            apiCreateConfig.setApiOrderParas(configDefinitionVO.getCreateOrderParasJson());

            apiCreateConfig.setDelFlag(0);
            if (existApiIds.contains(configDefinitionVO.getApiId())) {
                // update 更新操作
                apiCreateConfig.setId(existApiIdMap.get(configDefinitionVO.getApiId()));
                apiCreateConfig.setGmtModified(new Date());
                entityManager.merge(apiCreateConfig);
                updateIndex.incrementAndGet();
            } else {
                // insert 更新操作
                apiCreateConfig.setGmtCreate(new Date());
                entityManager.persist(apiCreateConfig);
                createIndex.incrementAndGet();
            }
        }
        entityManager.flush();
        entityManager.clear();
        LOG.info("新增 新建API配置方式信息数量 【{}】", createIndex.get());
        LOG.info("更新 新建API配置方式信息数量 【{}】", updateIndex.get());
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveFlushCreateSqlScript(List<DasApiCreateSqlScriptDefinitionVO> dataList) {
        AtomicInteger createIndex = new AtomicInteger(0);
        AtomicInteger updateIndex = new AtomicInteger(0);
        // 根据apiId查询新建API配置信息
        List<String> apiIds = dataList.stream().map(DasApiCreateSqlScriptDefinitionVO::getApiId).collect(Collectors.toList());

        Iterable<DasApiCreateSqlScript> apiCreateSqlScripts = dasApiCreateSqlScriptRepository.findAll(QDasApiCreateSqlScript.dasApiCreateSqlScript.apiId.in(apiIds));

        HashMap<String, Long> existApiIdMap = Maps.newHashMap();
        apiCreateSqlScripts.forEach(createSqlScript -> existApiIdMap.put(createSqlScript.getApiId(), createSqlScript.getId()));
        Set<String> existApiIds = existApiIdMap.keySet();

        for (DasApiCreateSqlScriptDefinitionVO createSqlScriptDefinitionVO : dataList) {
            DasApiCreateSqlScript apiCreateSqlScript = DasApiCreateSqlScriptMapper.INSTANCE.useDasApiCreateSqlScript(createSqlScriptDefinitionVO);
            // 设置请求参数JSON
            apiCreateSqlScript.setApiRequestParas(createSqlScriptDefinitionVO.getCreateSqlRequestParasJson());
            // 设置响应参数JSON
//            apiCreateSqlScript.setApiResponseParas(createSqlScriptDefinitionVO.getCreateSqlRequestParasJson());
            // 设置排序参数JSON
            apiCreateSqlScript.setApiOrderParas(createSqlScriptDefinitionVO.getCreateOrderParasJson());

            apiCreateSqlScript.setDelFlag(0);
            if (existApiIds.contains(createSqlScriptDefinitionVO.getApiId())) {
                // update 更新操作
                apiCreateSqlScript.setId(existApiIdMap.get(createSqlScriptDefinitionVO.getApiId()));
                apiCreateSqlScript.setGmtModified(new Date());
                entityManager.merge(apiCreateSqlScript);
                updateIndex.incrementAndGet();
            } else {
                // insert 更新操作
                apiCreateSqlScript.setGmtCreate(new Date());
                entityManager.persist(apiCreateSqlScript);
                createIndex.incrementAndGet();
            }
        }
        entityManager.flush();
        entityManager.clear();
        LOG.info("新增 新建API SQL脚本信息数量 【{}】", createIndex.get());
        LOG.info("更新 新建API SQL脚本信息数量 【{}】", updateIndex.get());
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveFlushRegister(List<DasApiRegisterDefinitionVO> dataList) {
        AtomicInteger createIndex = new AtomicInteger(0);
        AtomicInteger updateIndex = new AtomicInteger(0);
        // 根据apiId查询新建API配置信息
        List<String> apiIds = dataList.stream().map(DasApiRegisterDefinitionVO::getApiId).collect(Collectors.toList());

        Iterable<DasApiRegister> apiRegisters = dasApiRegisterRepository.findAll(QDasApiRegister.dasApiRegister.apiId.in(apiIds));

        HashMap<String, Long> existApiIdMap = Maps.newHashMap();
        apiRegisters.forEach(apiRegister -> existApiIdMap.put(apiRegister.getApiId(), apiRegister.getId()));
        Set<String> existApiIds = existApiIdMap.keySet();

        for (DasApiRegisterDefinitionVO apiRegisterDefinitionVO : dataList) {
            DasApiRegister apiRegister = DasApiRegisterMapper.INSTANCE.useDasApiRegister(apiRegisterDefinitionVO);
            // 设置后端服务参数JSON
            apiRegister.setBackendRequestParas(apiRegisterDefinitionVO.getRegisterBackendParasJson());
            // 设置常量参数JSON
            apiRegister.setBackendConstants(apiRegisterDefinitionVO.getRegisterConstantParasJson());

            apiRegister.setCreateUserid("admin");
            apiRegister.setDelFlag(0);
            if (existApiIds.contains(apiRegisterDefinitionVO.getApiId())) {
                // update 更新操作
                apiRegister.setId(existApiIdMap.get(apiRegisterDefinitionVO.getApiId()));
                apiRegister.setGmtModified(new Date());
                entityManager.merge(apiRegister);
                updateIndex.incrementAndGet();
            } else {
                // insert 更新操作
                apiRegister.setGmtCreate(new Date());
                entityManager.persist(apiRegister);
                createIndex.incrementAndGet();
            }
        }
        entityManager.flush();
        entityManager.clear();
        LOG.info("新增 注册API信息数量 【{}】", createIndex.get());
        LOG.info("更新 注册API信息数量 【{}】", updateIndex.get());
    }
}
