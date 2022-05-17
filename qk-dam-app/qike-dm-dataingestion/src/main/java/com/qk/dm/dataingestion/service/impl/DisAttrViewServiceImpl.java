package com.qk.dm.dataingestion.service.impl;

import com.qk.dm.dataingestion.entity.DisAttrView;
import com.qk.dm.dataingestion.enums.HiveFileType;
import com.qk.dm.dataingestion.mapstruct.mapper.DisAttrViewMapper;
import com.qk.dm.dataingestion.repositories.DisColumnViewRepository;
import com.qk.dm.dataingestion.service.DisAttrViewService;
import com.qk.dm.dataingestion.vo.DisAttrViewVO;
import com.qk.dm.dataingestion.vo.disAttrVO;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 作业高级属性接口
 * @author wangzp
 * @date 2022/04/19 20:07
 * @since 1.0.0
 */
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
        List<DisAttrView> list = disColumnViewRepository.findAll();
        Map<String, List<DisAttrView>> map = list.stream().collect(Collectors.groupingBy(DisAttrView::getType));
        List<DisAttrView> sourceList = map.get(SOURCE);
        List<DisAttrView> targetList = map.get(TARGET);

        return disAttrVO.builder()
                .source(
                        getSelectValue(sourceList)
                        .stream()
                        .collect(Collectors.groupingBy(DisAttrViewVO::getConnectType))
                ).target(
                        getSelectValue(targetList)
                        .stream()
                        .collect(Collectors.groupingBy(DisAttrViewVO::getConnectType))
                ).build();
    }

    private List<DisAttrViewVO> getSelectValue(List<DisAttrView> list){

        return Optional.ofNullable(list).orElse(List.of()).stream().map(e->{
                DisAttrViewVO attr = DisAttrViewMapper.INSTANCE.of(e);
                if(Objects.equals(e.getConnectType(),HIVE)&&(Objects.equals(e.getDataIndex(),SOURCE_FILE_TYPE)||
                        Objects.equals(e.getDataIndex(),TARGET_FILE_TYPE))){
                    attr.setValueEnum(HiveFileType.getMap());
                }
               return attr;
            }).collect(Collectors.toList());

    }
}
