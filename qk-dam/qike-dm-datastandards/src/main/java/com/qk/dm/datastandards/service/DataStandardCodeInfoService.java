package com.qk.dm.datastandards.service;

import com.qk.dm.datastandards.vo.DsdCodeInfoExtVO;
import com.qk.dm.datastandards.vo.DsdCodeInfoVO;
import com.qk.dm.datastandards.vo.PageResultVO;
import com.qk.dm.datastandards.vo.params.DsdCodeInfoExtParamsVO;
import com.qk.dm.datastandards.vo.params.DsdCodeInfoParamsVO;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author wjq
 * @date 20210726
 * @since 1.0.0 数据标准码表管理接口
 */
@Service
public interface DataStandardCodeInfoService {

    /**
     * 码表基本信息_表
     **/
    PageResultVO<DsdCodeInfoVO> getDsdCodeInfo(DsdCodeInfoParamsVO dsdCodeInfoParamsVO);

    void addDsdCodeInfo(DsdCodeInfoVO dsdCodeInfoVO);

    DsdCodeInfoVO getDsdCodeInfoById(long id);

    void modifyDsdCodeInfo(DsdCodeInfoVO dsdCodeInfoVO);

    void deleteDsdCodeInfo(long id);

    /**
     * 码表扩展信息_码表数值
     *
     * @param dsdCodeInfoExtParamsVO*/
    Map<String, Object> getDsdCodeInfoExt(DsdCodeInfoExtParamsVO dsdCodeInfoExtParamsVO);

    void addDsdCodeInfoExt(DsdCodeInfoExtVO dsdCodeInfoExtVO);

    DsdCodeInfoExtVO getBasicDsdCodeInfoExtById(long id);

    void modifyDsdCodeInfoExt(DsdCodeInfoExtVO dsdCodeInfoExtVO);

    void deleteDsdCodeInfoExt(long id);
}
