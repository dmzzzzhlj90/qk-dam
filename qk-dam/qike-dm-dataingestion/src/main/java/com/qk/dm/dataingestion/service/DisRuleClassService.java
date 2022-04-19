package com.qk.dm.dataingestion.service;

import com.qk.dm.dataingestion.vo.DisRuleClassVO;

import java.util.List;

public interface DisRuleClassService {
    List<DisRuleClassVO> list();

    void  insert(DisRuleClassVO vo);

    void update(DisRuleClassVO vo);

    void delete(String id);
}
