package com.qk.dm.datastandards.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dm.datastandards.constant.DsdConstant;
import com.qk.dm.datastandards.entity.DsdBasicinfo;
import com.qk.dm.datastandards.entity.QDsdBasicinfo;
import com.qk.dm.datastandards.mapstruct.mapper.DsdBasicInfoMapper;
import com.qk.dm.datastandards.repositories.DsdBasicinfoRepository;
import com.qk.dm.datastandards.service.DataStandardBasicInfoService;
import com.qk.dm.datastandards.vo.DsdBasicinfoVO;
import com.qk.dm.datastandards.vo.PageResultVO;
import com.qk.dm.datastandards.vo.params.DsdBasicinfoParamsVO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author wjq
 * @date 20210604
 * @since 1.0.0 数据标准标准信息接口实现类
 */
@Service
public class DataStandardBasicInfoServiceImpl implements DataStandardBasicInfoService {
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final DsdBasicinfoRepository dsdBasicinfoRepository;
    private final EntityManager entityManager;

    private JPAQueryFactory jpaQueryFactory;

    public DataStandardBasicInfoServiceImpl(DsdBasicinfoRepository dsdBasicinfoRepository, EntityManager entityManager) {
        this.dsdBasicinfoRepository = dsdBasicinfoRepository;
        this.entityManager = entityManager;
    }

    @PostConstruct
    public void initFactory() {
        jpaQueryFactory = new JPAQueryFactory(entityManager);
    }


    @Override
    public PageResultVO<DsdBasicinfoVO> getDsdBasicInfo(DsdBasicinfoParamsVO dsdBasicinfoParamsVO) {
        List<DsdBasicinfoVO> dsdBasicinfoVOList = new ArrayList<DsdBasicinfoVO>();
        Map<String, Object> map = null;
        try {
            map = queryClientByRequire(dsdBasicinfoParamsVO);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new BizException("查询失败!!!");
        }
        List<DsdBasicinfo> list = (List<DsdBasicinfo>) map.get("list");
        long total = (long) map.get("total");

        list.forEach(dsd -> {
            DsdBasicinfoVO dsdBasicinfoVO = DsdBasicInfoMapper.INSTANCE.useDsdBasicInfoVO(dsd);
            dsdBasicinfoVOList.add(dsdBasicinfoVO);
        });
        return new PageResultVO<>(total, dsdBasicinfoParamsVO.getPagination().getPage(),
                dsdBasicinfoParamsVO.getPagination().getSize(), dsdBasicinfoVOList);
    }

    private DsdBasicinfo setDsdBasicinfoSearchInfo(DsdBasicinfoParamsVO dsdBasicinfoParamsVO) {
        DsdBasicinfo dsdBasicinfo = new DsdBasicinfo();
        if (!StringUtils.isEmpty(dsdBasicinfoParamsVO.getDsdLevelId())) {
            dsdBasicinfo.setDsdLevelId(dsdBasicinfoParamsVO.getDsdLevelId());
        }

        if (!StringUtils.isEmpty(dsdBasicinfoParamsVO.getDsdName())) {
            dsdBasicinfo.setDsdName(dsdBasicinfoParamsVO.getDsdName());
        }

        if (!StringUtils.isEmpty(dsdBasicinfoParamsVO.getDsdCode())) {
            dsdBasicinfo.setDsdCode(dsdBasicinfoParamsVO.getDsdCode());
        }

        return dsdBasicinfo;
    }

    @Override
    public void addDsdBasicinfo(DsdBasicinfoVO dsdBasicinfoVO) {
        DsdBasicinfo dsdBasicinfo = DsdBasicInfoMapper.INSTANCE.useDsdBasicInfo(dsdBasicinfoVO);
        dsdBasicinfo.setGmtCreate(new Date());
        dsdBasicinfo.setGmtModified(new Date());

        Predicate predicate = QDsdBasicinfo.dsdBasicinfo.dsdCode.eq(dsdBasicinfo.getDsdCode());
        boolean exists = dsdBasicinfoRepository.exists(predicate);
        if (exists) {
            throw new BizException(
                    "当前要新增的标准代码ID为："
                            + dsdBasicinfo.getDsdCode()
                            + "标准名称为:"
                            + dsdBasicinfo.getDsdName()
                            + " 的数据，已存在！！！");
        }
        dsdBasicinfoRepository.save(dsdBasicinfo);
    }

    @Override
    public void updateDsdBasicinfo(DsdBasicinfoVO dsdBasicinfoVO) {
        DsdBasicinfo dsdBasicinfo = DsdBasicInfoMapper.INSTANCE.useDsdBasicInfo(dsdBasicinfoVO);
        dsdBasicinfo.setGmtModified(new Date());
        Predicate predicate = QDsdBasicinfo.dsdBasicinfo.dsdCode.eq(dsdBasicinfo.getDsdCode());
        boolean exists = dsdBasicinfoRepository.exists(predicate);
        if (exists) {
            dsdBasicinfoRepository.saveAndFlush(dsdBasicinfo);
        } else {
            throw new BizException(
                    "当前要更新的标准代码ID为："
                            + dsdBasicinfo.getDsdCode()
                            + "标准名称为:"
                            + dsdBasicinfo.getDsdName()
                            + " 的数据，不存在！！！");
        }
    }

    @Override
    public void deleteDsdBasicinfo(Integer delId) {
        boolean exists = dsdBasicinfoRepository.exists(QDsdBasicinfo.dsdBasicinfo.id.eq(delId));
        if (exists) {
            dsdBasicinfoRepository.deleteById(delId);
        }
    }

    @Override
    public List getDataCapacityByDataType(String dataType) {
        if (DsdConstant.DATA_TYPE_CAPACITY_STRING.equalsIgnoreCase(dataType))
            return DsdConstant.getListString();
        if (DsdConstant.DATA_TYPE_CAPACITY_DOUBLE.equalsIgnoreCase(dataType))
            return DsdConstant.getListDouble();
        if (DsdConstant.DATA_TYPE_CAPACITY_BOOLEAN.equalsIgnoreCase(dataType))
            return DsdConstant.getListBoolean();
        if (DsdConstant.DATA_TYPE_CAPACITY_DECIMAL.equalsIgnoreCase(dataType))
            return DsdConstant.getListDecimal();
        if (DsdConstant.DATA_TYPE_CAPACITY_DATE.equalsIgnoreCase(dataType))
            return DsdConstant.getListDate();
        if (DsdConstant.DATA_TYPE_CAPACITY_TIMESTAMP.equalsIgnoreCase(dataType))
            return DsdConstant.getListTimeStamp();
        return null;
    }


    public Map<String, Object> queryClientByRequire(DsdBasicinfoParamsVO dsdBasicinfoParamsVO) throws ParseException {
        QDsdBasicinfo qDsdBasicinfo = QDsdBasicinfo.dsdBasicinfo;
        HashMap<String, Object> result = new HashMap<>();
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        checkCondition(booleanBuilder, qDsdBasicinfo, dsdBasicinfoParamsVO);
        long count = jpaQueryFactory.select(qDsdBasicinfo.count()).from(qDsdBasicinfo).
                where(booleanBuilder).fetchOne();
        List<DsdBasicinfo> clientList = jpaQueryFactory.select(qDsdBasicinfo).
                from(qDsdBasicinfo).
                where(booleanBuilder).
                orderBy(qDsdBasicinfo.dsdCode.asc()).
                offset((dsdBasicinfoParamsVO.getPagination().getPage() - 1) * dsdBasicinfoParamsVO.getPagination().getSize()).
                limit(dsdBasicinfoParamsVO.getPagination().getSize()).
                fetch();
        result.put("list", clientList);
        result.put("total", count);
        return result;
    }

    public void checkCondition(BooleanBuilder booleanBuilder, QDsdBasicinfo qDsdBasicinfo, DsdBasicinfoParamsVO dsdBasicinfoParamsVO) throws ParseException {
        if (!StringUtils.isEmpty(dsdBasicinfoParamsVO.getDsdLevelId())) {
            booleanBuilder.and(qDsdBasicinfo.dsdLevelId.contains(dsdBasicinfoParamsVO.getDsdLevelId()));
        }
        if (!StringUtils.isEmpty(dsdBasicinfoParamsVO.getDsdName())) {
            booleanBuilder.and(qDsdBasicinfo.dsdName.contains(dsdBasicinfoParamsVO.getDsdName()));
        }
        if (!StringUtils.isEmpty(dsdBasicinfoParamsVO.getDsdCode())) {
            booleanBuilder.and(qDsdBasicinfo.dsdCode.contains(dsdBasicinfoParamsVO.getDsdCode()));
        }
        if (!StringUtils.isEmpty(dsdBasicinfoParamsVO.getBeginDay()) && !StringUtils.isEmpty(dsdBasicinfoParamsVO.getEndDay())) {
            StringTemplate dateExpr = Expressions.stringTemplate("DATE_FORMAT({0},'%Y-%m-%d %H:%i:%S')", qDsdBasicinfo.gmtModified);
            booleanBuilder.and(dateExpr.between(dsdBasicinfoParamsVO.getBeginDay(), dsdBasicinfoParamsVO.getEndDay()));
        }
    }
}
