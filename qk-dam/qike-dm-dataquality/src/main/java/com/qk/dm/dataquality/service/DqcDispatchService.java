package com.qk.dm.dataquality.service;

import com.qk.dm.dataquality.vo.DqcDispatchVo;
import org.springframework.stereotype.Service;

/**
 * @author shenpj
 * @date 2021/11/9 10:42 上午
 * @since 1.0.0
 */
@Service
public interface DqcDispatchService {

    void insert(DqcDispatchVo dqcDispatchVo);

    void update(DqcDispatchVo dqcDispatchVo);

    void delete(Integer delId);
}
