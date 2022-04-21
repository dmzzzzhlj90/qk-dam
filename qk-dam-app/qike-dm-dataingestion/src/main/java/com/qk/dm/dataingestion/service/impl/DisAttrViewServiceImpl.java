package com.qk.dm.dataingestion.service.impl;

import com.qk.dm.dataingestion.enums.HiveFileType;
import com.qk.dm.dataingestion.mapstruct.mapper.DisAttrViewMapper;
import com.qk.dm.dataingestion.repositories.DisColumnViewRepository;
import com.qk.dm.dataingestion.service.DisAttrViewService;
import com.qk.dm.dataingestion.vo.DisAttrViewVO;
import com.qk.dm.dataingestion.vo.DisViewParamsVO;
import com.qk.dm.dataingestion.vo.disAttrVO;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class DisAttrViewServiceImpl implements DisAttrViewService {
    private static final String HIVE = "hive";
    private static final String SOURCE_FILE_TYPE = "sourceFileType";
    private static final String TARGET_FILE_TYPE = "targetFileType";
    private static final String SOURCE = "source";
    private static final String TARGET = "target";

    private final DisColumnViewRepository disColumnViewRepository;

    public DisAttrViewServiceImpl(DisColumnViewRepository disColumnViewRepository) {
        this.disColumnViewRepository = disColumnViewRepository;
    }

    @Override
    public disAttrVO list() {
        List<DisAttrViewVO> list = DisAttrViewMapper.INSTANCE.of(disColumnViewRepository.findAll());
        Map<String, List<DisAttrViewVO>> map = list.stream().collect(Collectors.groupingBy(DisAttrViewVO::getType));
        List<DisAttrViewVO> sourceList = map.get(SOURCE);
        List<DisAttrViewVO> targetList = map.get(SOURCE);
        return disAttrVO.builder().source(
                getSelectValue(sourceList).stream().collect(Collectors.groupingBy(DisAttrViewVO::getConnectType)))
                .target(getSelectValue(targetList)
                   .stream().collect(Collectors.groupingBy(DisAttrViewVO::getConnectType))).build();
    }

    private List<DisAttrViewVO> getSelectValue(List<DisAttrViewVO> list){
        if(CollectionUtils.isEmpty(list)){
            return List.of();
        }
        list.forEach(e->{
                if(Objects.equals(e.getConnectType(),HIVE)&&(Objects.equals(e.getDataIndex(),SOURCE_FILE_TYPE)||
                        Objects.equals(e.getDataIndex(),TARGET_FILE_TYPE))){
                    e.setValueEnum(HiveFileType.getMap());
                }
            });
        return list;
    }
}
