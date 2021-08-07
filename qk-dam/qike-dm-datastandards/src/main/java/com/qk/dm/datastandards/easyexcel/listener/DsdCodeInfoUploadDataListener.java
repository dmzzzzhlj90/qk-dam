package com.qk.dm.datastandards.easyexcel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.qk.dam.commons.exception.BizException;
import com.qk.dam.commons.util.GsonUtil;
import com.qk.dm.datastandards.constant.DsdConstant;
import com.qk.dm.datastandards.entity.DsdCodeDir;
import com.qk.dm.datastandards.entity.DsdCodeInfo;
import com.qk.dm.datastandards.mapstruct.mapper.DsdCodeInfoMapper;
import com.qk.dm.datastandards.service.impl.DsdExcelBatchService;
import com.qk.dm.datastandards.vo.DsdCodeInfoVO;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 码表基本信息excel 监听器
 *
 * @author wjq
 * @date 20210730
 * @since 1.0.0
 */
public class DsdCodeInfoUploadDataListener extends AnalysisEventListener<DsdCodeInfoVO> {
    private final DsdExcelBatchService dsdExcelBatchService;
    private final String codeDirId;

    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 1000;

    List<DsdCodeInfoVO> list = new ArrayList<>();

    public DsdCodeInfoUploadDataListener(
            DsdExcelBatchService dsdExcelBatchService, String codeDirId) {
        this.dsdExcelBatchService = dsdExcelBatchService;
        this.codeDirId = codeDirId;
    }

    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data    one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context
     */
    @Override
    public void invoke(DsdCodeInfoVO data, AnalysisContext context) {
        String errMsg;
        try {
            errMsg = EasyExcelValidateHelper.validateEntity(data);
        } catch (NoSuchFieldException e) {
            errMsg = "解析数据出错";
        }
        if (StringUtils.isEmpty(errMsg)) {
            list.add(data);
        } else {
            throw new BizException(errMsg);
        }
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            list.clear();
        }
    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        if (list != null && list.size() > 0) {
            Set<String> tableCodeSet = new HashSet<>();
            Set<String> codeDirLevelSet = new HashSet<>();
            List<DsdCodeInfo> dataList = new ArrayList<>();

            if (!StringUtils.isEmpty(codeDirId)) {
                DsdCodeDir dsdCodeDir = dsdExcelBatchService.getCodeDir(codeDirId);
                Map<String, List<DsdCodeInfoVO>> tableCodeMap =
                        list.stream()
                                .filter(
                                        dsdCodeInfoVO ->
                                                dsdCodeInfoVO.getCodeDirLevel().equals(dsdCodeDir.getCodeDirLevel()))
                                .collect(Collectors.groupingBy(DsdCodeInfoVO::getTableCode));
                getDataListAllByCodeDirId(tableCodeSet, dataList, tableCodeMap);
                dsdExcelBatchService.saveCodeInfosByCodeDirId(dataList, tableCodeSet, dsdCodeDir);
            } else {
                Map<String, List<DsdCodeInfoVO>> tableCodeMap =
                        list.stream().collect(Collectors.groupingBy(DsdCodeInfoVO::getTableCode));
                getDataListAll(tableCodeSet, codeDirLevelSet, dataList, tableCodeMap);
                dsdExcelBatchService.saveCodeInfosAll(dataList, tableCodeSet, codeDirLevelSet);
            }
        }
    }

    private void getDataListAllByCodeDirId(
            Set<String> tableCodeSet,
            List<DsdCodeInfo> dataList,
            Map<String, List<DsdCodeInfoVO>> tableCodeMap) {
        for (String tableCode : tableCodeMap.keySet()) {
            List<DsdCodeInfoVO> dsdCodeInfoVOList = tableCodeMap.get(tableCode);
            DsdCodeInfoVO dsdCodeInfoVO = dsdCodeInfoVOList.get(0);
            DsdCodeInfo dsdCodeInfo = DsdCodeInfoMapper.INSTANCE.useDsdCodeInfo(dsdCodeInfoVO);

            List<Map<String, String>> configFields = new ArrayList();
            setDefaultConfigCodeAndValue(configFields);
            setCodeInfoConfigs(dsdCodeInfoVOList, configFields);

            dsdCodeInfo.setTableConfFields(GsonUtil.toJsonString(configFields));
            dataList.add(dsdCodeInfo);
            tableCodeSet.add(dsdCodeInfoVO.getTableCode());
        }
    }

    private void getDataListAll(
            Set<String> tableCodeSet,
            Set<String> codeDirLevelSet,
            List<DsdCodeInfo> dataList,
            Map<String, List<DsdCodeInfoVO>> tableCodeMap) {
        for (String tableCode : tableCodeMap.keySet()) {
            List<DsdCodeInfoVO> dsdCodeInfoVOList = tableCodeMap.get(tableCode);
            DsdCodeInfoVO dsdCodeInfoVO = dsdCodeInfoVOList.get(0);
            tableCodeSet.add(dsdCodeInfoVO.getTableCode());
            codeDirLevelSet.add(dsdCodeInfoVO.getCodeDirLevel());
            DsdCodeInfo dsdCodeInfo = DsdCodeInfoMapper.INSTANCE.useDsdCodeInfo(dsdCodeInfoVO);

            List<Map<String, String>> configFields = new ArrayList();
            setDefaultConfigCodeAndValue(configFields);
            setCodeInfoConfigs(dsdCodeInfoVOList, configFields);

            dsdCodeInfo.setTableConfFields(GsonUtil.toJsonString(configFields));
            dataList.add(dsdCodeInfo);
        }
    }

    private void setCodeInfoConfigs(
            List<DsdCodeInfoVO> dsdCodeInfoVOList, List<Map<String, String>> configFields) {
        for (DsdCodeInfoVO codeInfoVO : dsdCodeInfoVOList) {
            if (!codeInfoVO.getCodeTableId().equalsIgnoreCase(DsdConstant.CODE_INFO_CODE_EN_NAME)
                    && !codeInfoVO.getCodeTableId().equalsIgnoreCase(DsdConstant.CODE_INFO_VALUE_EN_NAME)) {
                Map<String, String> configFieldMap = new LinkedHashMap<>();
                configFieldMap.put(DsdConstant.CODE_INFO_TABLE_ID, codeInfoVO.getCodeTableId());
                configFieldMap.put(DsdConstant.CODE_INFO_NAME_CH, codeInfoVO.getNameCh());
                configFieldMap.put(DsdConstant.CODE_INFO_NAME_EN, codeInfoVO.getNameEn());
                configFieldMap.put(DsdConstant.CODE_INFO_DATA_TYPE, codeInfoVO.getDataType());
                configFields.add(configFieldMap);
            }
        }
    }

    private void setDefaultConfigCodeAndValue(List<Map<String, String>> configFields) {
        Map<String, String> configFieldCodedMap = new LinkedHashMap<>();
        configFieldCodedMap.put(DsdConstant.CODE_INFO_TABLE_ID, DsdConstant.CODE_INFO_CODE_EN_NAME);
        configFieldCodedMap.put(DsdConstant.CODE_INFO_NAME_CH, DsdConstant.CODE_INFO_CODE_CH_NAME);
        configFieldCodedMap.put(DsdConstant.CODE_INFO_NAME_EN, DsdConstant.CODE_INFO_CODE_EN_NAME);
        configFieldCodedMap.put(DsdConstant.CODE_INFO_DATA_TYPE, DsdConstant.CODE_INFO_CODE_TYPE);
        configFields.add(configFieldCodedMap);

        Map<String, String> configFieldValueMap = new LinkedHashMap<>();
        configFieldValueMap.put(DsdConstant.CODE_INFO_TABLE_ID, DsdConstant.CODE_INFO_VALUE_EN_NAME);
        configFieldValueMap.put(DsdConstant.CODE_INFO_NAME_CH, DsdConstant.CODE_INFO_VALUE_CH_NAME);
        configFieldValueMap.put(DsdConstant.CODE_INFO_NAME_EN, DsdConstant.CODE_INFO_VALUE_EN_NAME);
        configFieldValueMap.put(DsdConstant.CODE_INFO_DATA_TYPE, DsdConstant.CODE_INFO_VALUE_TYPE);
        configFields.add(configFieldValueMap);
    }
}
