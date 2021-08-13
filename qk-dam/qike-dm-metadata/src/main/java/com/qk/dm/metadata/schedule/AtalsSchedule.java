package com.qk.dm.metadata.schedule;

import com.qk.dm.metadata.service.SynchAtalsService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class AtalsSchedule {

  private final SynchAtalsService synchAtalsService;

  public AtalsSchedule(SynchAtalsService synchAtalsService) {
    this.synchAtalsService = synchAtalsService;
  }

  //    @Scheduled(fixedDelay = 60000)
  public void synchLabelsAtlas() {
    synchAtalsService.synchLabelsAtlas();
  }

  //    @Scheduled(fixedDelay = 60000)
  public void synchClassify() {
    synchAtalsService.synchClassify();
    synchAtalsService.synchClassifyAtlas();
  }
}
