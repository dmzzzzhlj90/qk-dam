package com.qk.dm.datastandards.service;

import com.qk.dm.datastandards.entity.DsdBasicinfo;
import com.qk.dm.datastandards.entity.DsdTerm;
import com.qk.dm.datastandards.vo.DsdBasicinfoVO;
import com.qk.dm.datastandards.vo.Pagination;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 * @author wjq
 * @date 20210604
 * @since 1.0.0
 * 数据标准标准信息接口
 */
@Service
public interface DataStandardBasicInfoService {

    Page<DsdBasicinfo> getDsdBasicInfo(Pagination pagination);

    void addDsdBasicinfo(DsdBasicinfoVO dsdBasicinfoVO);

    void updateDsdBasicinfo(DsdBasicinfoVO dsdBasicinfoVO);

    void deleteDsdBasicinfo(Integer id);


}
