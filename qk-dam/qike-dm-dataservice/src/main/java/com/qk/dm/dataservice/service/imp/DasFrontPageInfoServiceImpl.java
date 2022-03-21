package com.qk.dm.dataservice.service.imp;


import com.google.common.collect.Maps;
import com.qk.dm.dataservice.constant.DateFrequencyEnum;
import com.qk.dm.dataservice.constant.SyncStatusEnum;
import com.qk.dm.dataservice.entity.DasApiBasicInfo;
import com.qk.dm.dataservice.entity.QDasApiBasicInfo;
import com.qk.dm.dataservice.service.DasFrontPageInfoService;
import com.qk.dm.dataservice.utils.DateUtil;
import com.qk.dm.dataservice.vo.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.qk.dm.dataservice.utils.DateUtil.*;

/**
 * 数据服务_首页信息
 *
 * @author wjq
 * @date 2022/03/11
 * @since 1.0.0
 */
@Service
public class DasFrontPageInfoServiceImpl implements DasFrontPageInfoService {
    private static final QDasApiBasicInfo qDasApiBasicInfo = QDasApiBasicInfo.dasApiBasicInfo;
    public static final String CAL_GROUP_NUM = "groupNum";
    public static final String CAL_INTERVAL = "interval";
    public static final String FILTER_BEGIN_TIME = "FILTER_BEGIN_TIME";
    public static final String FILTER_END_TIME = "FILTER_END_TIME";
    public static final String FILTER_FIRST_TIME = "FILTER_FIRST_TIME";

    private final JPAQueryFactory jpaQueryFactory;

    @Autowired
    public DasFrontPageInfoServiceImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }


    @Override
    public Map<String, String> getDateFrequency() {
        return DateFrequencyEnum.getAllValue();
    }

    @Override
    public DasFrontPageTrendInfoDataVO releaseTrend(DasReleaseTrendParamsVO dasReleaseTrendParamsVO) {
        DasFrontPageTrendInfoDataVO.DasFrontPageTrendInfoDataVOBuilder dasFrontPageTrendInfoDataBuilder = DasFrontPageTrendInfoDataVO.builder();
        //日期频次时间范围
        String dateFrequency = dasReleaseTrendParamsVO.getDateFrequency();

        //查询时间范围
        searchDataTimeRange(dasReleaseTrendParamsVO, dateFrequency);
        //根据时间范围进行数据查询
        List<DasApiBasicInfo> apiBasicInfoList = queryApiBasicInfoByTime(dasReleaseTrendParamsVO);

        //构建曲线信息
        buildDasTrendCurveInfoVO(apiBasicInfoList, dasFrontPageTrendInfoDataBuilder, dasReleaseTrendParamsVO);
        //构建饼状图信息
        buildDasTrendPieChartInfoVO(apiBasicInfoList, dasFrontPageTrendInfoDataBuilder, dasReleaseTrendParamsVO);

        return dasFrontPageTrendInfoDataBuilder.build();
    }

    /**
     * 构建曲线信息
     *
     * @param apiBasicInfoList
     * @param dasFrontPageTrendInfoDataBuilder
     * @param dasReleaseTrendParamsVO
     */
    private void buildDasTrendCurveInfoVO(List<DasApiBasicInfo> apiBasicInfoList,
                                          DasFrontPageTrendInfoDataVO.DasFrontPageTrendInfoDataVOBuilder dasFrontPageTrendInfoDataBuilder,
                                          DasReleaseTrendParamsVO dasReleaseTrendParamsVO) {
        //日期频次
        String dateFrequency = dasReleaseTrendParamsVO.getDateFrequency();
        //首次开始时间
        String firstTime = dasReleaseTrendParamsVO.getBeginDate();

        //获取时间计算分组,间隔参数
        Map<String, Object> calGroupParams = getCalGroupParams(dateFrequency);

        //构建趋势图值信息
        DasTrendCurveInfoVO dasTrendCurveInfoVO =
                setDasTrendCurveVal(
                        apiBasicInfoList,
                        (Integer) calGroupParams.get(CAL_GROUP_NUM),
                        (Long) calGroupParams.get(CAL_INTERVAL),
                        firstTime,
                        dateFrequency);
        dasFrontPageTrendInfoDataBuilder.dasTrendCurveInfoVO(dasTrendCurveInfoVO);
    }

    /**
     * 查询时间范围
     *
     * @param dasReleaseTrendParamsVO
     * @param dateFrequency
     */
    private void searchDataTimeRange(DasReleaseTrendParamsVO dasReleaseTrendParamsVO, String dateFrequency) {
        String beginTime = "";
        String endTime = "";

        DateFrequencyEnum dateFrequencyEnum = DateFrequencyEnum.getVal(dateFrequency);

        switch (Objects.requireNonNull(dateFrequencyEnum)) {
            case DAY:
                beginTime = DateUtil.beginTime(new Date());
                endTime = DateUtil.endTime(new Date());
                break;
            case WEEK:
                beginTime = DateUtil.beginTime(DateUtil.monday());
                endTime = DateUtil.endTime(DateUtil.sunday());
                break;
            case MONTH:
                beginTime = DateUtil.beginTime(DateUtil.monthStart());
                endTime = DateUtil.endTime(DateUtil.monthEnd());
                break;
            case YEAR:
                beginTime = DateUtil.beginTime(DateUtil.yearStart());
                endTime = DateUtil.endTime(DateUtil.yearEnd());
                break;
            default:
                break;
        }
        dasReleaseTrendParamsVO.setBeginDate(beginTime);
        dasReleaseTrendParamsVO.setEndDate(endTime);
    }

    /**
     * 构建趋势图值信息
     *
     * @param apiBasicInfoList
     * @param groupNum
     * @param interval
     * @param firstTime
     * @param dateFrequency
     * @return
     */
    private DasTrendCurveInfoVO setDasTrendCurveVal(List<DasApiBasicInfo> apiBasicInfoList,
                                                    int groupNum,
                                                    long interval,
                                                    String firstTime,
                                                    String dateFrequency) {
        List<DasTrendCurveValVO> dasTrendCurveValVOList = new LinkedList<>();
        // 计数器
        AtomicInteger index = new AtomicInteger(0);
        for (int i = 0; i < groupNum + 1; i++) {
            // 根据推算的时间进行数据过滤
            Map<String, Object> filterTimeMap = getFilterTimeMap(interval, firstTime, dateFrequency);
            Date filterBeginTime = (Date) filterTimeMap.get(FILTER_BEGIN_TIME);
            Date filterEndTime = (Date) filterTimeMap.get(FILTER_END_TIME);
            firstTime = (String) filterTimeMap.get(FILTER_FIRST_TIME);

            // 根据时间过滤数据
            List<DasApiBasicInfo> apiBasicInfos =
                    apiBasicInfoList.stream().filter(o ->
                            (o.getGmtModified().after(filterBeginTime)) && (o.getGmtModified().before(filterEndTime)))
                            .collect(Collectors.toList());

            // 根据时间频次设置趋势曲线图显示KEY值
            String valKey = getTrendCurveValKey(
                    dateToStr(filterBeginTime), dateFrequency, groupNum, index.get());

            //构建趋势曲线值对象信息
            DasTrendCurveValVO dasTrendCurveValVO = DasTrendCurveValVO.builder()
                    .xVal(valKey)
                    .yVal(apiBasicInfos.size())
                    .build();
            dasTrendCurveValVOList.add(dasTrendCurveValVO);
            index.getAndIncrement();
        }
        return DasTrendCurveInfoVO.builder().dasTrendCurveValVOS(dasTrendCurveValVOList).build();
    }

    /**
     * 根据推算的时间进行数据过滤
     *
     * @return
     */
    private Map<String, Object> getFilterTimeMap(long interval,
                                                 String firstTime,
                                                 String dateFrequency) {
        Map<String, Object> filterTimeMap = Maps.newHashMap();
        //开始时间
        Date beginTime;
        //结束时间计算
        Date endTime;
        if (DateFrequencyEnum.YEAR.getCode().equalsIgnoreCase(dateFrequency)) {
            //年份单独处理
            beginTime = DateUtil.getMonthStart(firstTime);
            endTime = DateUtil.getMonthEnd(firstTime);
            //设置下一次时间
            firstTime = DateUtil.dateToStr(DateUtil.getNextMonthStart(beginTime));
        } else {
            beginTime = DateUtil.strToDate(firstTime);
            endTime = new Date(beginTime.getTime() + interval);
            //设置下一次时间
            firstTime = DateUtil.dateToStr(endTime);
        }
        filterTimeMap.put(FILTER_BEGIN_TIME, beginTime);
        filterTimeMap.put(FILTER_END_TIME, endTime);
        filterTimeMap.put(FILTER_FIRST_TIME, firstTime);
        return filterTimeMap;
    }

    /**
     * 构建饼状图信息
     *
     * @param apiBasicInfoList
     * @param dasFrontPageTrendInfoDataBuilder
     * @param dasReleaseTrendParamsVO
     */
    private void buildDasTrendPieChartInfoVO(List<DasApiBasicInfo> apiBasicInfoList,
                                             DasFrontPageTrendInfoDataVO.DasFrontPageTrendInfoDataVOBuilder dasFrontPageTrendInfoDataBuilder,
                                             DasReleaseTrendParamsVO dasReleaseTrendParamsVO) {
        //新建未发布
        List<DasApiBasicInfo> createNoUpload = apiBasicInfoList.stream().
                filter(o -> SyncStatusEnum.CREATE_NO_UPLOAD.getCode().equalsIgnoreCase(o.getStatus()))
                .collect(Collectors.toList());
        //新建发布失败
        List<DasApiBasicInfo> createFailUpload = apiBasicInfoList.stream().
                filter(o -> SyncStatusEnum.CREATE_FAIL_UPLOAD.getCode().equalsIgnoreCase(o.getStatus()))
                .collect(Collectors.toList());
        //新建发布成功
        List<DasApiBasicInfo> createSuccessUpload = apiBasicInfoList.stream().
                filter(o -> SyncStatusEnum.CREATE_SUCCESS_UPLOAD.getCode().equalsIgnoreCase(o.getStatus()))
                .collect(Collectors.toList());

        //构建饼状图信息
        DasTrendPieChartInfoVO dasTrendPieChartInfoVO = DasTrendPieChartInfoVO.builder()
                .apiSumCount(apiBasicInfoList.size())
                .createNoUploadCount(createNoUpload.size())
                .createFailUploadCount(createFailUpload.size())
                .createSuccessUploadCount(createSuccessUpload.size())
                .beginDay(dasReleaseTrendParamsVO.getBeginDate().substring(0, 10))
                .endDay(dasReleaseTrendParamsVO.getEndDate().substring(0, 10))
                .build();

        dasFrontPageTrendInfoDataBuilder.dasTrendPieChartInfoVO(dasTrendPieChartInfoVO);
    }

    /**
     * 获取时间计算分组,间隔参数
     *
     * @param dateFrequency
     * @return
     */
    private Map<String, Object> getCalGroupParams(String dateFrequency) {
        Map<String, Object> calGroupParams = Maps.newHashMap();
        // 分组次数
        int groupNum = 0;
        //间隔
        long interval = 0;
        DateFrequencyEnum dateFrequencyEnum = DateFrequencyEnum.getVal(dateFrequency);
        switch (Objects.requireNonNull(dateFrequencyEnum)) {
            case DAY:
                // 分组次数 node/4h
                groupNum = DAY_GROUP_NUM;
                //间隔
                interval = DAY_INTERVAL;
                calGroupParams.put(CAL_GROUP_NUM, groupNum);
                calGroupParams.put(CAL_INTERVAL, interval);
                break;
            case WEEK:
                // 分组次数 node/1d
                groupNum = WEEK_GROUP_NUM;
                //间隔
                interval = WEEK_INTERVAL;
                calGroupParams.put(CAL_GROUP_NUM, groupNum);
                calGroupParams.put(CAL_INTERVAL, interval);
                break;
            case MONTH:
                // 分组次数 node/5d
                int monthDayCount = DateUtil.getCurrentMonthLastDay();
                groupNum = MONTH_GROUP_NUM;
                if (monthDayCount > MONTH_DAY_COUNT) {
                    groupNum++;
                }
                //间隔
                interval = MONTH_INTERVAL;
                calGroupParams.put(CAL_GROUP_NUM, groupNum);
                calGroupParams.put(CAL_INTERVAL, interval);
                break;
            case YEAR:
                // 分组次数 node/1mon
                groupNum = YEAR_GROUP_NUM;
                //间隔
                interval = YEAR_INTERVAL;
                calGroupParams.put(CAL_GROUP_NUM, groupNum);
                calGroupParams.put(CAL_INTERVAL, interval);
                break;
            default:
                break;
        }
        return calGroupParams;
    }

    /**
     * 根据时间频次设置趋势曲线图显示KEY值
     *
     * @param beginTimeStr
     * @param dateFrequency
     * @param groupNum
     * @param index
     * @return
     */
    private String getTrendCurveValKey(String beginTimeStr, String dateFrequency, int groupNum, int index) {
        String valKey = "";
        DateFrequencyEnum dateFrequencyEnum = DateFrequencyEnum.getVal(dateFrequency);
        switch (Objects.requireNonNull(dateFrequencyEnum)) {
            case DAY:
                valKey = getDayTrendCurveValKey(beginTimeStr, groupNum, index);
                break;
            case YEAR:
                valKey = beginTimeStr.substring(0, 7);
                break;
            default:
                valKey = beginTimeStr.substring(0, 10);
                break;
        }
        return valKey;
    }

    /**
     * DAY 时间范围结束日期00时转为24时
     *
     * @param beginTimeStr
     * @param groupNum
     * @param index
     * @return
     */
    private String getDayTrendCurveValKey(String beginTimeStr, int groupNum, int index) {
        String valKey;
        if (groupNum == index) {
            valKey = "24时";
        } else {
            valKey = beginTimeStr.substring(11, 13) + "时";
        }
        return valKey;
    }

    /**
     * 根据时间范围进行数据查询
     *
     * @param dasReleaseTrendParamsVO
     * @return
     */
    public List<DasApiBasicInfo> queryApiBasicInfoByTime(DasReleaseTrendParamsVO dasReleaseTrendParamsVO) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        checkCondition(booleanBuilder, qDasApiBasicInfo, dasReleaseTrendParamsVO);
        return jpaQueryFactory
                .select(qDasApiBasicInfo)
                .from(qDasApiBasicInfo)
                .where(booleanBuilder)
                .fetch();
    }

    public void checkCondition(BooleanBuilder booleanBuilder,
                               QDasApiBasicInfo qDasApiBasicinfo,
                               DasReleaseTrendParamsVO dasReleaseTrendParamsVO) {
        if (!ObjectUtils.isEmpty(dasReleaseTrendParamsVO.getBeginDate()) && !StringUtils.isEmpty(dasReleaseTrendParamsVO.getEndDate())) {
            StringTemplate dateExpr =
                    Expressions.stringTemplate(
                            "DATE_FORMAT({0},'%Y-%m-%d %H:%i:%S')", qDasApiBasicinfo.gmtModified);
            booleanBuilder.and(
                    dateExpr.between(dasReleaseTrendParamsVO.getBeginDate(), dasReleaseTrendParamsVO.getEndDate()));
        }
    }

}
