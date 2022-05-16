package com.qk.dm.metadata.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dam.metedata.util.AtlasLabelsUtil;
import com.qk.dm.metadata.entity.QMtdClassifyAtlas;
import com.qk.dm.metadata.repositories.MtdClassifyAtlasRepository;
import com.qk.dm.metadata.service.MtdClassifyAtlasService;
import com.qk.dm.metadata.vo.MtdClassifyAtlasVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.atlas.AtlasServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wangzp
 * @date 2021/7/31 11:37
 * @since 1.0.0
 */
@Service
@Slf4j
public class MtdAtlasClassifyServiceImpl implements MtdClassifyAtlasService {

    @Override
    public void insert(MtdClassifyAtlasVO mtdClassifyAtlasVO) {
        try {
            List<String> classifys = List.of(mtdClassifyAtlasVO.getClassify().split(","));
            AtlasLabelsUtil.addEntitiesClassis(mtdClassifyAtlasVO.getGuid(), classifys);
        } catch (AtlasServiceException e) {
            log.error("guid[{}]绑定分类失败:[{}]", mtdClassifyAtlasVO.getGuid(), e.getMessage());
            throw new BizException("绑定失败");
        }
    }

    @Override
    public void update(MtdClassifyAtlasVO mtdClassifyAtlasVO) {
        try {
            AtlasLabelsUtil.upEntitiesClassis(mtdClassifyAtlasVO.getGuid(), mtdClassifyAtlasVO.getClassify());
        } catch (AtlasServiceException e) {
            log.error("guid[{}]修改绑定分类失败:[{}]", mtdClassifyAtlasVO.getGuid(), e.getMessage());
            throw new BizException("绑定失败");
        }
    }

    @Override
    public MtdClassifyAtlasVO getByGuid(String guid) {
        try {
            List<String> entitiesClassis = AtlasLabelsUtil.getEntitiesClassis(guid);
            return new MtdClassifyAtlasVO(guid, String.join(",", entitiesClassis));
        } catch (AtlasServiceException e) {
            log.error("guid[{}]查询绑定分类失败:[{}]", guid, e.getMessage());
            throw new BizException("查询失败");
        }
    }

    @Override
    public List<MtdClassifyAtlasVO> getByBulk(List<String> guids) {
        return guids.stream().map(guid -> {
            try {
                List<String> entitiesClassis = AtlasLabelsUtil.getEntitiesClassis(guid);
                return new MtdClassifyAtlasVO(guid, String.join(",", entitiesClassis));
            } catch (AtlasServiceException e) {
                log.error("guid[{}]查询绑定分类失败:[{}]", guid, e.getMessage());
                throw new BizException("查询失败");
            }
        }).collect(Collectors.toList());
    }
}
