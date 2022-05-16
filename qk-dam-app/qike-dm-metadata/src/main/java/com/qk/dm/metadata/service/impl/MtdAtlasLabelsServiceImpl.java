package com.qk.dm.metadata.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dam.metedata.util.AtlasLabelsUtil;
import com.qk.dm.metadata.service.MtdLabelsAtlasService;
import com.qk.dm.metadata.vo.MtdLabelsAtlasBulkVO;
import com.qk.dm.metadata.vo.MtdLabelsAtlasVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.atlas.AtlasServiceException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author spj
 * @date 2021/7/31 3:04 下午
 * @since 1.0.0
 */
@Service
@Slf4j
public class MtdAtlasLabelsServiceImpl implements MtdLabelsAtlasService {

    @Override
    public void insert(MtdLabelsAtlasVO mtdLabelsAtlasVO) {
        try {
            AtlasLabelsUtil.setLabels(mtdLabelsAtlasVO.getGuid(), mtdLabelsAtlasVO.getLabels());
        } catch (AtlasServiceException e) {
            log.error("guid[{}]绑定标签失败:[{}]", mtdLabelsAtlasVO.getGuid(), e.getMessage());
            throw new BizException("绑定失败");
        }
    }

    @Override
    public void update(MtdLabelsAtlasVO mtdLabelsAtlasVO) {
        try {
            AtlasLabelsUtil.setLabels(mtdLabelsAtlasVO.getGuid(), mtdLabelsAtlasVO.getLabels());
        } catch (AtlasServiceException e) {
            log.error("guid[{}]绑定标签失败:[{}]", mtdLabelsAtlasVO.getGuid(), e.getMessage());
            throw new BizException("绑定失败");
        }
    }

    @Override
    public void delete(MtdLabelsAtlasVO mtdLabelsVO) {
        try {
            Set<String> labels = AtlasLabelsUtil.getLabels(mtdLabelsVO.getGuid());
            List<String> newLabels = List.of(mtdLabelsVO.getLabels().split(","));
            labels.removeAll(newLabels);
            AtlasLabelsUtil.setLabels(mtdLabelsVO.getGuid(), String.join(",", labels));
        } catch (AtlasServiceException e) {
            log.error("guid[{}]删除标签失败:[{}]", mtdLabelsVO.getGuid(), e.getMessage());
            throw new BizException("删除失败");
        }
    }

    @Override
    public MtdLabelsAtlasVO getByGuid(String guid) {
        try {
            Set<String> labels = AtlasLabelsUtil.getLabels(guid);
            return new MtdLabelsAtlasVO(guid, String.join(",", labels));
        } catch (AtlasServiceException e) {
            log.error("guid[{}]查询标签失败:[{}]", guid, e.getMessage());
            throw new BizException("查询失败");
        }
    }

    @Override
    public void bulk(MtdLabelsAtlasBulkVO mtdLabelsVO) {
        List<String> guidList = Arrays.asList(mtdLabelsVO.getGuids());
        guidList.forEach(guid -> {
            try {
                Set<String> labels = AtlasLabelsUtil.getLabels(guid);
                List<String> newLabels = List.of(mtdLabelsVO.getLabels().split(","));
                labels.addAll(newLabels);
                AtlasLabelsUtil.setLabels(guid, String.join(",", labels));
            } catch (AtlasServiceException e) {
                log.error("guid[{}]批量绑定标签失败:[{}]", guid, e.getMessage());
                throw new BizException("批量绑定失败");
            }
        });
    }

    @Override
    public List<MtdLabelsAtlasVO> getByBulk(List<String> guids) {
        return guids.stream().map(guid -> {
            try {
                Set<String> labels = AtlasLabelsUtil.getLabels(guid);
                return new MtdLabelsAtlasVO(guid, String.join(",", labels));
            } catch (AtlasServiceException e) {
                log.error("guid[{}]查询标签失败:[{}]", guid, e.getMessage());
                throw new BizException("查询失败");
            }
        }).collect(Collectors.toList());
    }
}
