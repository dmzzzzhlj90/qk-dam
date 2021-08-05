package com.qk.dm.metadata.service.impl;

import com.qk.dm.metadata.entity.MtdLabels;
import com.qk.dm.metadata.entity.MtdLabelsAtlas;
import com.qk.dm.metadata.repositories.MtdLabelsAtlasRepository;
import com.qk.dm.metadata.repositories.MtdLabelsRepository;
import com.qk.dm.metadata.service.MtdAtlasService;
import com.qk.dm.metadata.vo.MtdLabelsAtlasVO;
import org.apache.atlas.AtlasServiceException;
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
    private final MtdLabelsRepository mtdLabelsRepository;
    private final MtdAtlasService mtdAtlasService;


    public SynchAtalsServiceImpl(MtdLabelsAtlasRepository mtdLabelsAtlasRepository, MtdLabelsRepository mtdLabelsRepository, MtdAtlasService mtdAtlasService) {
        this.mtdLabelsAtlasRepository = mtdLabelsAtlasRepository;
        this.mtdLabelsRepository = mtdLabelsRepository;
        this.mtdAtlasService = mtdAtlasService;
    }

    public void synchLabels() {
        List<MtdLabels> labelAllList = mtdLabelsRepository.findAllBySynchStatus(-1);
        if (!labelAllList.isEmpty()) {
            //todo 可考虑优化 提前放到缓存中，维护缓存
            List<MtdLabelsAtlas> labelsAtlases = mtdLabelsAtlasRepository.findAllBySynchStatusIsNot(-1);
            doLabels(labelAllList, labelsAtlases);
        }
    }

    public void synchLabelsAtlas() {
        List<MtdLabelsAtlas> labelAllList = mtdLabelsAtlasRepository
                .findAllBySynchStatusInOrderByGmtCreateAsc(Stream.of(-1, 0).collect(Collectors.toList()));
        if (!labelAllList.isEmpty()) {
            doLabels(labelAllList);
        }
    }

    private void doLabels(List<MtdLabels> labelsBySynchStatus, List<MtdLabelsAtlas> labelsAtlases) {
        List<MtdLabelsAtlas> deleteList = new ArrayList<>();
        List<MtdLabelsAtlas> updateList = new ArrayList<>();
        List<MtdLabelsAtlasVO> deleteAtlasList = new ArrayList<>();
        labelsBySynchStatus.forEach(label -> {
            List<MtdLabelsAtlas> mtdLabelsAtlasList = getMtdLabelsAtlases(labelsAtlases, label.getName());
            extractedData(mtdLabelsAtlasList, deleteList, updateList, deleteAtlasList, label.getName());
        });
        disposeData(labelsBySynchStatus, deleteList, updateList, deleteAtlasList);
    }

    private void doLabels(List<MtdLabelsAtlas> labelAllList) {
        List<MtdLabelsAtlas> deleteList = new ArrayList<>();
        List<MtdLabelsAtlas> updateList = new ArrayList<>();
        extracteData(labelAllList,deleteList,updateList);
        disposeData(deleteList, updateList);
    }

    public void extractedData(List<MtdLabelsAtlas> mtdLabelsAtlasList,
                              List<MtdLabelsAtlas> deleteList,
                              List<MtdLabelsAtlas> updateList,
                              List<MtdLabelsAtlasVO> deleteAtlasList,
                              String labelName) {
        mtdLabelsAtlasList.forEach(labelsAtlas -> {
            deleteAtlasList.add(new MtdLabelsAtlasVO(labelsAtlas.getGuid(), labelName));
            String labels = getLabels(labelName, labelsAtlas);
            if (labels.isEmpty()) {
                deleteList.add(labelsAtlas);
            } else {
                labelsAtlas.setLabels(labels);
                updateList.add(labelsAtlas);
            }
        });
    }

    private void extracteData(List<MtdLabelsAtlas> mtdLabelsAtlasList,
                              List<MtdLabelsAtlas> deleteList,
                              List<MtdLabelsAtlas> updateList) {
        deleteList.addAll(mtdLabelsAtlasList.stream()
                .filter(i -> i.getSynchStatus() == -1).collect(Collectors.toList()));
        updateList.addAll(mtdLabelsAtlasList.stream()
                .filter(i -> i.getSynchStatus() == 0).collect(Collectors.toList()));
    }

    private void disposeData(List<MtdLabels> labelsBySynchStatus, List<MtdLabelsAtlas> deleteList, List<MtdLabelsAtlas> updateList, List<MtdLabelsAtlasVO> deleteAtlasList) {
        deleteAtlasList.forEach(mtdLabelsAtlas -> {
            try {
                mtdAtlasService.removeLabels(mtdLabelsAtlas.getGuid(), mtdLabelsAtlas.getLabels());
            } catch (AtlasServiceException e) {
                //降级处理
                e.printStackTrace();
            }
        });
        if (!deleteList.isEmpty()) {
            //批量更新本地
            mtdLabelsAtlasRepository.deleteAll(deleteList);
        }
        if (!updateList.isEmpty()) {
            //批量更新本地
            mtdLabelsAtlasRepository.saveAll(updateList);
        }
        //批量删除
        mtdLabelsRepository.deleteAll(labelsBySynchStatus);
    }

    private void disposeData(List<MtdLabelsAtlas> deleteList, List<MtdLabelsAtlas> updateList) {
        if (!deleteList.isEmpty()) {
            deleteList.forEach(mtdLabelsAtlas -> {
                try {
                    mtdAtlasService.removeLabels(mtdLabelsAtlas.getGuid(), mtdLabelsAtlas.getLabels());
                } catch (AtlasServiceException e) {
                    //降级处理
                    e.printStackTrace();
                }
            });
            mtdLabelsAtlasRepository.deleteAll(deleteList);
        }
        if (!updateList.isEmpty()) {
            updateList.forEach(mtdLabelsAtlas -> {
                try {
                    mtdAtlasService.setLabels(mtdLabelsAtlas.getGuid(), mtdLabelsAtlas.getLabels());
                } catch (AtlasServiceException e) {
                    //降级处理
                    e.printStackTrace();
                }
            });
            mtdLabelsAtlasRepository.saveAll(updateList);
        }
    }

    private List<MtdLabelsAtlas> getMtdLabelsAtlases(List<MtdLabelsAtlas> labelsAtlases, String labelName) {
        return labelsAtlases.stream().filter(i ->
                        Stream.of(i.getLabels()).collect(Collectors.toList()).contains(labelName))
                .collect(Collectors.toList());
    }

    private String getLabels(String labelName, MtdLabelsAtlas labelsAtlas) {
        return Stream.of(labelsAtlas.getLabels())
                .filter(i -> !i.equals(labelName)).collect(Collectors.joining(","));
    }
}
