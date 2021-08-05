package com.qk.dm.metadata.service.impl;

import com.qk.dm.metadata.entity.MtdLabels;
import com.qk.dm.metadata.entity.MtdLabelsAtlas;
import com.qk.dm.metadata.repositories.MtdLabelsAtlasRepository;
import com.qk.dm.metadata.repositories.MtdLabelsRepository;
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
  private final MtdLabelsRepository mtdLabelsRepository;
  private final MtdAtlasService mtdAtlasService;


  public SynchAtalsServiceImpl(MtdLabelsAtlasRepository mtdLabelsAtlasRepository, MtdLabelsRepository mtdLabelsRepository, MtdAtlasService mtdAtlasService) {
    this.mtdLabelsAtlasRepository = mtdLabelsAtlasRepository;
    this.mtdLabelsRepository = mtdLabelsRepository;
    this.mtdAtlasService = mtdAtlasService;
  }

  public void synchLabels() {
    List<MtdLabels> labelsBySynchStatus = mtdLabelsRepository.findAllBySynchStatus(-1);
    if (!labelsBySynchStatus.isEmpty()) {
      //todo 可考虑优化 提前放到缓存中，维护缓存
      List<MtdLabelsAtlas> labelsAtlases = mtdLabelsAtlasRepository
              .findAllBySynchStatusIsNot(-1);
      List<MtdLabelsAtlas> deleteList = new ArrayList<>();
      List<MtdLabelsAtlas> updateList = new ArrayList<>();
      labelsBySynchStatus.forEach(label -> {
        //查询哪些元数据绑定的标签
        List<MtdLabelsAtlas> mtdLabelsAtlasList = labelsAtlases.stream().filter(i ->
                        Stream.of(i.getLabels()).collect(Collectors.toList()).contains(label.getName()))
                .collect(Collectors.toList());
        //处理数据
        mtdLabelsAtlasList.forEach(labelsAtlas -> {
          String labels = Stream.of(labelsAtlas.getLabels())
                  .filter(i -> !i.equals(label.getName()))
                  .collect(Collectors.joining(","));
          //修改atlas中元数据绑定
//                    mtdAtlasService.setLabels(labelsAtlas.getGuid(), labels);
          //删除给定实体的给定标签
          mtdAtlasService.removeLabels(labelsAtlas.getGuid(), label.getName());
          if (labels.isEmpty()) {
            deleteList.add(labelsAtlas);
          } else {
            labelsAtlas.setLabels(labels);
            updateList.add(labelsAtlas);
          }
        });
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
  }


  public void synchLabelsAtlas() {
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
