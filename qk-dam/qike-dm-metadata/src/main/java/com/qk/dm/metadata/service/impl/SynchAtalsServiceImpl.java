package com.qk.dm.metadata.service.impl;

import com.qk.dam.metedata.util.AtlasClassificationUtil;
import com.qk.dm.metadata.AtlasLabelsUtil;
import com.qk.dm.metadata.entity.MtdClassify;
import com.qk.dm.metadata.entity.MtdClassifyAtlas;
import com.qk.dm.metadata.entity.MtdLabelsAtlas;
import com.qk.dm.metadata.repositories.MtdClassifyAtlasRepository;
import com.qk.dm.metadata.repositories.MtdClassifyRepository;
import com.qk.dm.metadata.repositories.MtdLabelsAtlasRepository;
import com.qk.dm.metadata.repositories.MtdLabelsRepository;
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
    private final MtdLabelsRepository mtdLabelsRepository;
    private final MtdClassifyRepository mtdClassifyRepository;
    private final MtdClassifyAtlasRepository mtdClassifyAtlasRepository;

    public SynchAtalsServiceImpl(MtdLabelsAtlasRepository mtdLabelsAtlasRepository,
                                 MtdLabelsRepository mtdLabelsRepository,
                                 MtdClassifyRepository mtdClassifyRepository,
                                 MtdClassifyAtlasRepository mtdClassifyAtlasRepository) {
        this.mtdLabelsAtlasRepository = mtdLabelsAtlasRepository;
        this.mtdLabelsRepository = mtdLabelsRepository;
        this.mtdClassifyRepository = mtdClassifyRepository;
        this.mtdClassifyAtlasRepository = mtdClassifyAtlasRepository;
    }

//    @Transactional(rollbackFor = Exception.class)
//    public void synchLabels() {
//        List<MtdLabels> labelAllList = mtdLabelsRepository.findAllBySynchStatus(-1);
//        if (!labelAllList.isEmpty()) {
//            //todo 可考虑优化 提前放到缓存中，维护缓存
//            List<MtdLabelsAtlas> labelsAtlases = mtdLabelsAtlasRepository.findAllBySynchStatusIsNot(-1);
//            List<MtdLabelsAtlas> excludeList = getExcludeList(labelsAtlases, labelAllList);
//            //批量更新本地
//            mtdLabelsAtlasRepository.saveAll(excludeList);
//            //批量删除
//            mtdLabelsRepository.deleteAll(labelAllList);
//        }
//    }
//
//    private List<MtdLabelsAtlas> getExcludeList(List<MtdLabelsAtlas> labelsAtlases, List<MtdLabels> labelAllList) {
//        List<String> labelsNameList = labelAllList.stream().map(MtdLabels::getName).collect(Collectors.toList());
//        return labelsAtlases.stream()
//                .filter(i -> CollectionUtils.containsAny(labelsNameList, Stream.of(i.getLabels()).collect(Collectors.toList())))
//                .peek(mla -> {
//                    String labels = Stream.of(mla.getLabels()).filter(y -> !labelsNameList.contains(y)).collect(Collectors.joining(","));
//                    mla.setLabels(labels.isEmpty() ? mla.getLabels() : labels);
//                    mla.setSynchStatus(labels.isEmpty() ? -1 : 0);
//                }).collect(Collectors.toList());
//    }

    public void synchLabelsAtlas() {
        List<MtdLabelsAtlas> labelAllList = mtdLabelsAtlasRepository
                .findAllBySynchStatusInOrderByGmtCreateAsc(Arrays.asList(-1, 0));
        if (!labelAllList.isEmpty()) {
            List<MtdLabelsAtlas> deleteList = labelAllList.stream().filter(i -> i.getSynchStatus() == -1).collect(Collectors.toList());
            List<MtdLabelsAtlas> updateList = labelAllList.stream().filter(i -> i.getSynchStatus() == 0).collect(Collectors.toList());
            disposeDataByLabelsAtlas(deleteList, updateList, true);
        }
    }

    private void disposeDataByLabelsAtlas(List<MtdLabelsAtlas> deleteList, List<MtdLabelsAtlas> updateList, Boolean judge) {
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


    /**************************分类**************************************************/

    public void synchClassify() {
        List<MtdClassify> mtdClassifyList = mtdClassifyRepository.findAllBySynchStatusIsNot(1);
        if (!mtdClassifyList.isEmpty()) {
            List<MtdClassify> deleteClassList = mtdClassifyList.stream().filter(i -> i.getSynchStatus() == -1).collect(Collectors.toList());
            List<MtdClassify> addClassList = mtdClassifyList.stream().filter(i -> i.getSynchStatus() == 2).collect(Collectors.toList());
            disposeDataByClassify(deleteClassList, addClassList);
        }
    }

    private void disposeDataByClassify(List<MtdClassify> deleteClassList, List<MtdClassify> addClassList) {
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

    public void synchClassifyAtlas() {
        List<MtdClassifyAtlas> classifyAtlasList = mtdClassifyAtlasRepository
                .findAllBySynchStatusInOrderByGmtCreateAsc(Arrays.asList(-1, 0));
        if (!classifyAtlasList.isEmpty()) {
            List<MtdClassifyAtlas> deleteClassList = classifyAtlasList.stream().filter(i -> i.getSynchStatus() == -1).collect(Collectors.toList());
            List<MtdClassifyAtlas> updateClassList = classifyAtlasList.stream().filter(i -> i.getSynchStatus() == 0).collect(Collectors.toList());
            disposeDataByAtlas(deleteClassList, updateClassList);
        }
    }

    private void disposeDataByAtlas(List<MtdClassifyAtlas> deleteClassList, List<MtdClassifyAtlas> updateClassList) {
        if (deleteClassList.isEmpty()) {
            for (MtdClassifyAtlas mtdClassifyAtlas : deleteClassList) {
                try {
                    AtlasLabelsUtil.delEntitiesClassis(mtdClassifyAtlas.getGuid());
                } catch (AtlasServiceException e) {
                    //降级处理
                    e.printStackTrace();
                }
            }
            mtdClassifyAtlasRepository.deleteAll(deleteClassList);
        }
        if (!updateClassList.isEmpty()) {
            for (MtdClassifyAtlas mtdClassifyAtlas : updateClassList) {
                //查询出实体类中的全部，再跟本地的比较，哪些没有删除哪些
                try {
                    AtlasLabelsUtil.upEntitiesClassis(mtdClassifyAtlas.getGuid(), mtdClassifyAtlas.getClassify());
                    mtdClassifyAtlas.setSynchStatus(1);
                } catch (AtlasServiceException e) {
                    //降级处理
                    e.printStackTrace();
                }
            }
            mtdClassifyAtlasRepository.saveAll(updateClassList);
        }
    }

}
