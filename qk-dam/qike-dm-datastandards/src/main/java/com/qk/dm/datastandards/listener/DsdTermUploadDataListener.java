package com.qk.dm.datastandards.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.qk.dm.datastandards.entity.DsdTerm;
import com.qk.dm.datastandards.mapstruct.mapper.DsdTermMapper;
import com.qk.dm.datastandards.repositories.DsdTermRepository;
import com.qk.dm.datastandards.vo.DsdTermVO;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据标准业务术语excel 监听器
 *
 * @author wjq
 * @date 2021/6/5
 * @since 1.0.0
 */
public class DsdTermUploadDataListener extends AnalysisEventListener<DsdTermVO> {
    private final DsdTermRepository dsdTermRepository;


    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 1000;
    List<DsdTermVO> list = new ArrayList<DsdTermVO>();

    public DsdTermUploadDataListener(DsdTermRepository dsdTermRepository) {
        this.dsdTermRepository = dsdTermRepository;
    }


    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data    one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context
     */
    @Override
    public void invoke(DsdTermVO data, AnalysisContext context) {
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
        //VO转换
        List<DsdTerm> dsdTermList = new ArrayList<>();
        list.forEach(dsdTermVO -> {
            DsdTerm dsdTerm = DsdTermMapper.INSTANCE.useDsdTerm(dsdTermVO);
            dsdTermList.add(dsdTerm);
        });
        dsdTermRepository.saveAll(dsdTermList);
    }
}
