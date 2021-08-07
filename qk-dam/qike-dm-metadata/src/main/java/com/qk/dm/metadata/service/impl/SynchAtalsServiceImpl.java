package com.qk.dm.metadata.service.impl;

import com.qk.dam.metedata.util.AtlasClassificationUtil;
import com.qk.dm.metadata.AtlasLabelsUtil;
import com.qk.dm.metadata.entity.MtdClassify;
import com.qk.dm.metadata.entity.MtdClassifyAtlas;
import com.qk.dm.metadata.entity.MtdLabelsAtlas;
import com.qk.dm.metadata.repositories.MtdClassifyAtlasRepository;
import com.qk.dm.metadata.repositories.MtdClassifyRepository;
import com.qk.dm.metadata.repositories.MtdLabelsAtlasRepository;
import org.apache.atlas.AtlasServiceException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author spj
 * @date 2021/8/4 11:04 上午
 * @since 1.0.0
 */
@Service
public class SynchAtalsServiceImpl {

    private final MtdLabelsAtlasRepository mtdLabelsAtlasRepository;
    private final MtdClassifyRepository mtdClassifyRepository;
    private final MtdClassifyAtlasRepository mtdClassifyAtlasRepository;

    public SynchAtalsServiceImpl(MtdLabelsAtlasRepository mtdLabelsAtlasRepository,
                                 MtdClassifyRepository mtdClassifyRepository,
                                 MtdClassifyAtlasRepository mtdClassifyAtlasRepository) {
        this.mtdLabelsAtlasRepository = mtdLabelsAtlasRepository;
        this.mtdClassifyRepository = mtdClassifyRepository;
        this.mtdClassifyAtlasRepository = mtdClassifyAtlasRepository;
    }

    /**
     * 刷新标签绑定
     */
    public void synchLabelsAtlas() {
        List<MtdLabelsAtlas> labelAllList = mtdLabelsAtlasRepository
                .findAllBySynchStatusInOrderByGmtCreateAsc(Arrays.asList(-1, 0));
        if (!labelAllList.isEmpty()) {
            List<MtdLabelsAtlas> updateList = labelAllList.stream().filter(i -> i.getSynchStatus() == 0).collect(Collectors.toList());
            this.putAtlasLabels(updateList);
            List<MtdLabelsAtlas> deleteList = labelAllList.stream().filter(i -> i.getSynchStatus() == -1).collect(Collectors.toList());
            this.deleteAtlasLabels(deleteList);
        }
    }

    /**
     * 刷新分类
     */
    public void synchClassify() {
        List<MtdClassify> mtdClassifyList = mtdClassifyRepository.findAllBySynchStatusIsNot(1);
        if (!mtdClassifyList.isEmpty()) {
            List<MtdClassify> addClassList = mtdClassifyList.stream().filter(i -> i.getSynchStatus() == 2).collect(Collectors.toList());
            this.addAlassify(addClassList);
            List<MtdClassify> deleteClassList = mtdClassifyList.stream().filter(i -> i.getSynchStatus() == -1).collect(Collectors.toList());
            this.deleteAlassify(deleteClassList);
        }
    }

    /**
     * 刷新分类绑定
     */
    public void synchClassifyAtlas() {
        List<MtdClassifyAtlas> classifyAtlasList = mtdClassifyAtlasRepository
                .findAllBySynchStatusInOrderByGmtCreateAsc(Arrays.asList(-1, 0));
        if (!classifyAtlasList.isEmpty()) {
            List<MtdClassifyAtlas> updateClassList = classifyAtlasList.stream().filter(i -> i.getSynchStatus() == 0).collect(Collectors.toList());
            this.putAtlasClassify(updateClassList);
            List<MtdClassifyAtlas> deleteClassList = classifyAtlasList.stream().filter(i -> i.getSynchStatus() == -1).collect(Collectors.toList());
            this.deleteAtlasClassify(deleteClassList);
        }
    }

    private void putAtlasLabels(List<MtdLabelsAtlas> updateList){
        if (!updateList.isEmpty()) {
            updateList.forEach(mtdLabelsAtlas -> {
                try {
                    AtlasLabelsUtil.setLabels(mtdLabelsAtlas.getGuid(), mtdLabelsAtlas.getLabels());
                    mtdLabelsAtlas.setSynchStatus(1);
                } catch (AtlasServiceException e) {
                    //降级处理
                    e.printStackTrace();
                }
            });
            mtdLabelsAtlasRepository.saveAll(updateList);
        }
    }

    private void deleteAtlasLabels(List<MtdLabelsAtlas> deleteList) {
        if (!deleteList.isEmpty()) {
            deleteList.forEach(mtdLabelsAtlas -> {
                try {
                    AtlasLabelsUtil.removeLabels(mtdLabelsAtlas.getGuid());
                } catch (AtlasServiceException e) {
                    //降级处理
                    e.printStackTrace();
                }
            });
            mtdLabelsAtlasRepository.deleteAll(deleteList);
        }
    }

    private void addAlassify(List<MtdClassify> addClassList) {
        if (!addClassList.isEmpty()) {
            Map<String, String> addClassMap = addClassList.stream()
                    .collect(Collectors.toMap(MtdClassify::getName, MtdClassify::getDescription));
            try {
                AtlasLabelsUtil.postTypedefs(addClassMap);
            } catch (AtlasServiceException e) {
                //降级处理
                e.printStackTrace();
            }
            mtdClassifyRepository.saveAll(addClassList.stream()
                    .peek(i -> i.setSynchStatus(1)).collect(Collectors.toList()));
        }
    }

    private void deleteAlassify(List<MtdClassify> deleteClassList) {
        if (!deleteClassList.isEmpty()) {
            deleteClassList.forEach(classify -> {
                try {
                    AtlasClassificationUtil.delEntitiesClassis(classify.getName());
                } catch (AtlasServiceException e) {
                    //降级处理
                    e.printStackTrace();
                }
                try {
                    AtlasClassificationUtil.delClassi(classify.getName());
                } catch (AtlasServiceException e) {
                    //降级处理
                    e.printStackTrace();
                }
            });
            mtdClassifyRepository.deleteAll(deleteClassList);
        }
    }

    private void putAtlasClassify(List<MtdClassifyAtlas> updateClassList){
        if (!updateClassList.isEmpty()) {
            updateClassList.forEach(mtdClassifyAtlas -> {
                try {
                    AtlasLabelsUtil.upEntitiesClassis(mtdClassifyAtlas.getGuid(), mtdClassifyAtlas.getClassify());
                    mtdClassifyAtlas.setSynchStatus(1);
                } catch (AtlasServiceException e) {
                    //降级处理
                    e.printStackTrace();
                }
            });
            mtdClassifyAtlasRepository.saveAll(updateClassList);
        }
    }

    private void deleteAtlasClassify(List<MtdClassifyAtlas> deleteClassList){
        if (deleteClassList.isEmpty()) {
            deleteClassList.forEach(mtdClassifyAtlas -> {
                try {
                    AtlasLabelsUtil.delEntitiesClassis(mtdClassifyAtlas.getGuid());
                } catch (AtlasServiceException e) {
                    //降级处理
                    e.printStackTrace();
                }
            });
            mtdClassifyAtlasRepository.deleteAll(deleteClassList);
        }
    }
}
