package com.qk.dm.dataservice.service.imp;


import com.qk.dm.dataservice.service.DasFrontPageInfoService;
import com.qk.dm.dataservice.vo.DasFrontPageTrendInfoDataVO;
import com.qk.dm.dataservice.vo.DasReleaseTrendParamsVO;
import org.springframework.stereotype.Service;

/**
 * 数据服务_首页信息
 *
 * @author wjq
 * @date 2022/03/11
 * @since 1.0.0
 */
@Service
public class DasFrontPageInfoServiceImpl implements DasFrontPageInfoService {

    @Override
    public DasFrontPageTrendInfoDataVO releaseTrend(DasReleaseTrendParamsVO dasReleaseTrendParamsVO) {
        //TODO 日期频次时间范围

        //根据频次范围进行数据查询

        //构建曲线信息

        //构建饼状图信息

        return null;
    }

}
