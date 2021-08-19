package com.qk.dm.metadata.schedule;

import com.qk.dm.metadata.service.SynchAtlasService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class AtalsSchedule {

  private final SynchAtlasService synchAtalsService;

  public AtalsSchedule(SynchAtlasService synchAtalsService) {
    this.synchAtalsService = synchAtalsService;
  }

  @Scheduled(fixedDelay = 60000)
  public void synchLabelsAtlas() {
    synchAtalsService.synchLabelsAtlas();
  }

  @Scheduled(fixedDelay = 60000)
  public void synchClassify() {
    synchAtalsService.synchClassify();
    synchAtalsService.synchClassifyAtlas();
  }
}
