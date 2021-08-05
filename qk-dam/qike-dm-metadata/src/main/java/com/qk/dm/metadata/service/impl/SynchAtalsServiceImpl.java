package com.qk.dm.metadata.service.impl;

import com.qk.dm.metadata.entity.MtdLabelsAtlas;
import com.qk.dm.metadata.repositories.MtdLabelsAtlasRepository;
import com.qk.dm.metadata.service.MtdAtlasService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author spj
 * @date 2021/8/4 11:04 上午
 * @since 1.0.0
 */
@Service
public class SynchAtalsServiceImpl {

    private final MtdLabelsAtlasRepository mtdLabelsAtlasRepository;
    private final MtdAtlasService mtdAtlasService;


    public SynchAtalsServiceImpl(MtdLabelsAtlasRepository mtdLabelsAtlasRepository, MtdAtlasService mtdAtlasService) {
        this.mtdLabelsAtlasRepository = mtdLabelsAtlasRepository;
        this.mtdAtlasService = mtdAtlasService;
    }

    public void synchLabels() {
        List<MtdLabelsAtlas> labelAllList = mtdLabelsAtlasRepository
                .findAllBySynchStatusInOrderByGmtCreateAsc(Stream.of(-1, 0).collect(Collectors.toList()));
        if (!labelAllList.isEmpty()) {
            List<MtdLabelsAtlas> deleteList = new ArrayList<>();
            List<MtdLabelsAtlas> updateList = new ArrayList<>();
            labelAllList.forEach(mtdLabelsAtlas -> {

                switch (mtdLabelsAtlas.getSynchStatus()) {
                    case -1:
                        mtdAtlasService.removeLabels(mtdLabelsAtlas.getGuid(), mtdLabelsAtlas.getLabels());
                        deleteList.add(mtdLabelsAtlas);
                        break;
                    case 0:
                        mtdAtlasService.setLabels(mtdLabelsAtlas.getGuid(), mtdLabelsAtlas.getLabels());
                        mtdLabelsAtlas.setSynchStatus(1);
                        updateList.add(mtdLabelsAtlas);
                        break;
                    default:
                        break;
                }
            });
            if (!deleteList.isEmpty()) {
                mtdLabelsAtlasRepository.deleteAll(deleteList);
            }
            if (!updateList.isEmpty()) {
                mtdLabelsAtlasRepository.saveAll(updateList);
            }
        }

    }
}
