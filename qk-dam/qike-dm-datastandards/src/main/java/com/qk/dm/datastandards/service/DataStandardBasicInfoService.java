package com.qk.dm.datastandards.service;

import com.qk.dm.datastandards.entity.DsdBasicinfo;
import com.qk.dm.datastandards.entity.DsdTerm;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * @author wjq
 * @date 20210604
 * @since 1.0.0
 * 数据标准__标准信息接口
 */
@Service
public interface DataStandardBasicInfoService {

    Page<DsdBasicinfo> getDsdBasicInfo(Integer page, Integer size);

    void addDsdBasicinfo(DsdBasicinfo dsdBasicinfo);

    void updateDsdBasicinfo(DsdBasicinfo dsdBasicinfo);

    void deleteDsdBasicinfo(Integer id);


}
