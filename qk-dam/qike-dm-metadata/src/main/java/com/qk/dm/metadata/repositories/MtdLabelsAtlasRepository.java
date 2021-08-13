package com.qk.dm.metadata.repositories;

import com.qk.dam.jpa.base.BaseRepository;
import com.qk.dm.metadata.entity.MtdLabelsAtlas;
import java.util.List;

public interface MtdLabelsAtlasRepository extends BaseRepository<MtdLabelsAtlas, Long> {

  List<MtdLabelsAtlas> findAllBySynchStatusInOrderByGmtCreateAsc(List<Integer> synchStatus);

  List<MtdLabelsAtlas> findAllByLabelsIsLikeAndSynchStatusIsNot(String labels, Integer synchStatus);

  List<MtdLabelsAtlas> findAllBySynchStatusIsNot(Integer synchStatus);
}
