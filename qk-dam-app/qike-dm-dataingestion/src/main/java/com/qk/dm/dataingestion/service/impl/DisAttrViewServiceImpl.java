package com.qk.dm.dataingestion.service.impl;

import com.qk.dm.dataingestion.enums.HiveFileType;
import com.qk.dm.dataingestion.mapstruct.mapper.DisAttrViewMapper;
import com.qk.dm.dataingestion.repositories.DisColumnViewRepository;
import com.qk.dm.dataingestion.service.DisAttrViewService;
import com.qk.dm.dataingestion.vo.DisAttrViewVO;
import com.qk.dm.dataingestion.vo.DisViewParamsVO;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;

@Service
public class DisAttrViewServiceImpl implements DisAttrViewService {
    private static final String HIVE = "hive";
    private static final String SOURCE_FILE_TYPE = "sourceFileType";
    private static final String TARGET_FILE_TYPE = "targetFileType";

    private final DisColumnViewRepository disColumnViewRepository;

    public DisAttrViewServiceImpl(DisColumnViewRepository disColumnViewRepository) {
        this.disColumnViewRepository = disColumnViewRepository;
    }

    @Override
    public List<DisAttrViewVO> list(DisViewParamsVO vo) {
        List<DisAttrViewVO> list = DisAttrViewMapper.INSTANCE.of(disColumnViewRepository.findAllByTypeAndConnectType(vo.getType(),
                vo.getConnectType()));
        if(Objects.equals(vo.getConnectType(),HIVE)){
            list.forEach(e->{
                if(Objects.equals(e.getDataIndex(),SOURCE_FILE_TYPE)||
                        Objects.equals(e.getDataIndex(),TARGET_FILE_TYPE)){
                    e.setValueEnum(HiveFileType.getMap());
                }
            });
        }
        return list;
    }
}
