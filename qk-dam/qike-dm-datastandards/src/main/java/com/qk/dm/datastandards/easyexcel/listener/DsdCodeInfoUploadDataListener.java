package com.qk.dm.datastandards.easyexcel.listener;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.qk.dm.datastandards.entity.DsdCodeInfo;
import com.qk.dm.datastandards.mapstruct.mapper.DsdCodeInfoMapper;
import com.qk.dm.datastandards.service.impl.DsdExcelBatchService;
import com.qk.dm.datastandards.utils.GsonUtil;
import com.qk.dm.datastandards.vo.DsdCodeInfoVO;

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

    public DsdCodeInfoUploadDataListener(DsdExcelBatchService dsdExcelBatchService, String codeDirId) {
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
        list.add(data);
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
            Map<String, List<DsdCodeInfoVO>> tableCodeMap = list.stream().collect(Collectors.groupingBy(DsdCodeInfoVO::getTableCode));
            for (String tableCode : tableCodeMap.keySet()) {
                List<DsdCodeInfoVO> dsdCodeInfoVOList = tableCodeMap.get(tableCode);
                DsdCodeInfoVO dsdCodeInfoVO = dsdCodeInfoVOList.get(0);
                DsdCodeInfo dsdCodeInfo = DsdCodeInfoMapper.INSTANCE.useDsdCodeInfo(dsdCodeInfoVO);

                List<Map<String, String>> configFields = new ArrayList();
                for (DsdCodeInfoVO codeInfoVO : dsdCodeInfoVOList) {
                    Map<String, String> configFieldMap = new LinkedHashMap<>();
                    configFieldMap.put("code_table_id", codeInfoVO.getCodeTableId());
                    configFieldMap.put("name_ch", codeInfoVO.getNameCh());
                    configFieldMap.put("name_en", codeInfoVO.getNameEn());
                    configFieldMap.put("data_type", codeInfoVO.getDataType());
                    configFields.add(configFieldMap);
                }
                dsdCodeInfo.setTableConfFields(GsonUtil.toJsonString(configFields));
                dataList.add(dsdCodeInfo);
                tableCodeSet.add(dsdCodeInfoVO.getTableCode());
                codeDirLevelSet.add(dsdCodeInfoVO.getCodeDirLevel());
            }
            dsdExcelBatchService.saveCodeInfos(dataList, tableCodeSet, codeDirLevelSet,codeDirId);
        }
    }
}

