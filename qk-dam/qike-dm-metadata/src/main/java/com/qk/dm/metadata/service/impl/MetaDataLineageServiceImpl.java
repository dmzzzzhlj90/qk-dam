package com.qk.dm.metadata.service.impl;

import com.qk.dam.commons.util.GsonUtil;
import com.qk.dam.metedata.config.AtlasConfig;
import com.qk.dm.metadata.mapstruct.mapper.MtdLineageMapper;
import com.qk.dm.metadata.service.MetaDataLineageService;
import com.qk.dm.metadata.vo.MtdLineageDetailVO;
import com.qk.dm.metadata.vo.MtdLineageParamsVO;
import com.qk.dm.metadata.vo.MtdLineageVO;
import com.qk.dm.metadata.vo.ProcessVO;
import org.apache.atlas.AtlasClientV2;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.model.instance.AtlasEntity;
import org.apache.atlas.model.instance.AtlasEntityHeader;
import org.apache.atlas.model.lineage.AtlasLineageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wangzp
 * @date 2021/10/15 15:16
 * @since 1.0.0
 */
@Service
public class MetaDataLineageServiceImpl implements MetaDataLineageService {
    private static final AtlasClientV2 atlasClientV2 = AtlasConfig.getAtlasClientV2();

    @Override
    public MtdLineageVO getLineageInfo(MtdLineageParamsVO mtdLineageParaVO) {
        MtdLineageVO mtdLineageVO = null;
        try {
            AtlasLineageInfo atlasLineageInfo = atlasClientV2.getLineageInfo(mtdLineageParaVO.getGuid()
                    , getLineageDirectionEnum(mtdLineageParaVO.getDirection())
                    , mtdLineageParaVO.getDepth() == null ? 3 : mtdLineageParaVO.getDepth());

            List<AtlasEntityHeader> atlasEntityHeaderList = new ArrayList<>(atlasLineageInfo.getGuidEntityMap().values());
            List<MtdLineageDetailVO> mtdLineageDetailVOList = MtdLineageMapper.INSTANCE.userMtdLineageDetailVO(atlasEntityHeaderList);
            //获取当前节点
            List<MtdLineageDetailVO> currentNodeList = mtdLineageDetailVOList.stream()
                    .filter(e -> e.getGuid().equals(mtdLineageParaVO.getGuid()))
                    .collect(Collectors.toList());
            List<ProcessVO> processVOList = new ArrayList<>();
            if (!CollectionUtils.isEmpty(mtdLineageDetailVOList)) {
                Set<AtlasLineageInfo.LineageRelation> relationSet = atlasLineageInfo.getRelations();
                for (AtlasLineageInfo.LineageRelation relation : relationSet) {
                    ProcessVO processVO = new ProcessVO();
                    processVO.setGuid(mtdLineageParaVO.getGuid());
                    processVO.setSourceId(relation.getFromEntityId());
                    processVO.setTargetId(relation.getToEntityId());
                    processVOList.add(processVO);
                }
            }
            mtdLineageVO = MtdLineageVO.builder()
                    .currentNode(CollectionUtils.isEmpty(currentNodeList) ? new MtdLineageDetailVO() : currentNodeList.get(0))
                    .nodes(mtdLineageDetailVOList)
                    .edges(processVOList).build();

        } catch (AtlasServiceException e) {
            e.printStackTrace();
        }
        return mtdLineageVO;
    }

    @Override
    public Map<String, Object> getProcess(String guid) {
        Map<String, Object> process = null;
        try {
            AtlasEntity.AtlasEntityWithExtInfo detail = atlasClientV2.getEntityByGuid(guid, true, false);

            process = detail.getEntity().getRelationshipAttributes().entrySet().stream()
                    .filter(r -> r.getKey().equals("outputs") || r.getKey().equals("inputs"))
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        } catch (AtlasServiceException e) {
            e.printStackTrace();
        }
        return process;
    }

    private AtlasLineageInfo.LineageDirection getLineageDirectionEnum(String direction) {
        if (StringUtils.isBlank(direction)) {
            return AtlasLineageInfo.LineageDirection.BOTH;
        }
        for (AtlasLineageInfo.LineageDirection direct : AtlasLineageInfo.LineageDirection.values()) {
            if (String.valueOf(direct).equals(direction.toUpperCase())) {
                return direct;
            }
        }
        return AtlasLineageInfo.LineageDirection.BOTH;
    }

    public static void main(String[] args) {
        MtdLineageParamsVO mtdLineageParaVO = new MtdLineageParamsVO();
        mtdLineageParaVO.setGuid("a16257f8-17ce-4c95-9142-54ed9219864a");
        MtdLineageVO mtdLineageVO = new MetaDataLineageServiceImpl().getLineageInfo(mtdLineageParaVO);
        System.out.println(GsonUtil.toJsonString(mtdLineageVO));
    }

}
