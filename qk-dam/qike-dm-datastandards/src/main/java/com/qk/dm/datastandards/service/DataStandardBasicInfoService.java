package com.qk.dm.datastandards.service;

import com.qk.dm.datastandards.vo.DsdBasicinfoVO;
import com.qk.dm.datastandards.vo.PageResultVO;
import com.qk.dm.datastandards.vo.params.DsdBasicinfoParamsVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author wjq
 * @date 20210604
 * @since 1.0.0 数据标准标准信息接口
 */
@Service
public interface DataStandardBasicInfoService {

    PageResultVO<DsdBasicinfoVO> getDsdBasicInfo(DsdBasicinfoParamsVO dsdBasicinfoParamsVO);

    void addDsdBasicinfo(DsdBasicinfoVO dsdBasicinfoVO);

    void updateDsdBasicinfo(DsdBasicinfoVO dsdBasicinfoVO);

    void deleteDsdBasicinfo(Integer id);

    List<String> getDataCapacityByDataType(String dataType);

    void bulkDeleteDsdBasicInfo(String ids);

}
