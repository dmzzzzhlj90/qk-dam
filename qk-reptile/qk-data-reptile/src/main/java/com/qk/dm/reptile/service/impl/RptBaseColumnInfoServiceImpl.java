package com.qk.dm.reptile.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dm.reptile.entity.QRptBaseColumnInfo;
import com.qk.dm.reptile.entity.RptBaseColumnInfo;
import com.qk.dm.reptile.mapstruct.mapper.RptBaseColumnInfoMapper;
import com.qk.dm.reptile.params.dto.RptBaseColumnInfoDTO;
import com.qk.dm.reptile.params.vo.RptBaseColumnInfoVO;
import com.qk.dm.reptile.repositories.RptBaseColumnInfoRepository;
import com.qk.dm.reptile.service.RptBaseColumnInfoService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 爬虫数据采集选择器
 * @author wangzp
 * @date 2021/12/8 16:44
 * @since 1.0.0
 */
@Service
public class RptBaseColumnInfoServiceImpl implements RptBaseColumnInfoService {
    private final RptBaseColumnInfoRepository rptBaseColumnInfoRepository;
    private final QRptBaseColumnInfo qRptBaseColumnInfo = QRptBaseColumnInfo.rptBaseColumnInfo;

    public RptBaseColumnInfoServiceImpl(RptBaseColumnInfoRepository rptBaseColumnInfoRepository,
                                        EntityManager entityManager){
        this.rptBaseColumnInfoRepository = rptBaseColumnInfoRepository;
    }

    @Override
    public void insert(RptBaseColumnInfoDTO rptBaseColumnInfoDTO) {
        rptBaseColumnInfoRepository.save(RptBaseColumnInfoMapper.INSTANCE.userRptBaseColumnInfo(rptBaseColumnInfoDTO));
    }

    @Override
    public void batchInset(Long baseInfoId,List<RptBaseColumnInfoDTO> rptBaseColumnInfoDTOList) {
        Iterable<RptBaseColumnInfo> rptBaseColumnInfos = rptBaseColumnInfoRepository.findAll(qRptBaseColumnInfo.baseInfoId.eq(baseInfoId));
        rptBaseColumnInfoRepository.deleteAllInBatch(rptBaseColumnInfos);
        List<RptBaseColumnInfo> rptBaseColumnInfoList = RptBaseColumnInfoMapper.INSTANCE.of(rptBaseColumnInfoDTOList);
        rptBaseColumnInfoList.forEach(e->e.setBaseInfoId(baseInfoId));
        rptBaseColumnInfoRepository.saveAll(rptBaseColumnInfoList);
    }

    @Override
    public void update(Long id, RptBaseColumnInfoDTO rptBaseColumnInfoDTO) {
        RptBaseColumnInfo rptBaseColumnInfo = rptBaseColumnInfoRepository.findById(id).orElse(null);
        if (Objects.isNull(rptBaseColumnInfo)) {
            throw new BizException("当前要修改的选择器信息id为：" + id + " 的数据不存在！！！");
        }
        RptBaseColumnInfoMapper.INSTANCE.of(rptBaseColumnInfoDTO, rptBaseColumnInfo);
        rptBaseColumnInfoRepository.saveAndFlush(rptBaseColumnInfo);
    }

    @Override
    public RptBaseColumnInfoVO detail(Long id) {
        Optional<RptBaseColumnInfo> rptBaseColumnInfo = rptBaseColumnInfoRepository.findById(id);
        if(rptBaseColumnInfo.isEmpty()){
            throw new BizException("当前要查询的选择器信息id为：" + id + " 的数据不存在！！！");
        }
       return RptBaseColumnInfoMapper.INSTANCE.userRptBaseColumnInfoVO(rptBaseColumnInfo.get());
    }

    @Override
    public void delete(Long id) {
        rptBaseColumnInfoRepository.deleteById(id);
    }

    @Override
    public List<RptBaseColumnInfoVO> list(Long baseInfoId) {
        List<RptBaseColumnInfo> list = rptBaseColumnInfoRepository.findAllByBaseInfoId(baseInfoId);
        if(!CollectionUtils.isEmpty(list)){
           return RptBaseColumnInfoMapper.INSTANCE.ofVO(list);
        }
        return null;
    }

    @Override
    public void deleteByBaseInfoId(Long baseInfoId) {
        Iterable<RptBaseColumnInfo> rptBaseColumnInfos = rptBaseColumnInfoRepository.findAll(qRptBaseColumnInfo.baseInfoId.in(baseInfoId));
        rptBaseColumnInfoRepository.deleteAll(rptBaseColumnInfos);
    }
}
