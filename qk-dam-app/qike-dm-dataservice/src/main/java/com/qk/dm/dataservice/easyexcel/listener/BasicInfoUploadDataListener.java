package com.qk.dm.dataservice.easyexcel.listener;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.qk.dam.commons.exception.BizException;
import com.qk.dm.dataservice.easyexcel.ExcelBatchSaveFlushDataService;
import com.qk.dm.dataservice.vo.DasApiBasicInfoVO;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * API基础信息 Excel 监听器
 *
 * @author wjq
 * @date 2022/04/18
 * @since 1.0.0
 */
public class BasicInfoUploadDataListener extends AnalysisEventListener<DasApiBasicInfoVO> {
    private static final Log LOG = LogFactory.get("basicInfoUploadDataListener.saveData()");
    private final ExcelBatchSaveFlushDataService excelBatchSaveFlushDataService;

    private final String dirId;

    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 1000;

    List<DasApiBasicInfoVO> list = new ArrayList<>();

    public BasicInfoUploadDataListener(ExcelBatchSaveFlushDataService excelBatchSaveFlushDataService,
                                       String dirId) {
        this.excelBatchSaveFlushDataService = excelBatchSaveFlushDataService;
        this.dirId = dirId;
    }

    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data    one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context
     */
    @Override
    public void invoke(DasApiBasicInfoVO data, AnalysisContext context) {
//        LOG.info("======开始校验excel中的API基础信息数据!======");
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
//        LOG.info("======结束校验excel中的API基础信息!======");
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
     * 存储数据库
     */
    private void saveData() {
        LOG.info("解析excelAPI基础信息数量 【{}】,开始导入", list.size());

        excelBatchSaveFlushDataService.saveFlushBasicInfo(list,dirId);

        LOG.info("解析excelAPI基础信息处理完成!");
    }

}
