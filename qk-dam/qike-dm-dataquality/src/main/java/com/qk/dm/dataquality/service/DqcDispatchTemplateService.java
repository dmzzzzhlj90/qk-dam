package com.qk.dm.dataquality.service;

import com.qk.dam.jpa.pojo.Pagination;
import com.qk.dm.dataquality.vo.DqcDispatchTemplateListVo;
import com.qk.dm.dataquality.vo.DqcDispatchTemplateVo;
import com.qk.dm.dataquality.vo.PageResultVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author shenpj
 * @date 2021/11/9 10:32 上午
 * @since 1.0.0
 */
public interface DqcDispatchTemplateService {

    List<DqcDispatchTemplateListVo> searchList();

    PageResultVO<DqcDispatchTemplateListVo> searchPageList(Pagination pagination);

    void insert(DqcDispatchTemplateVo dqcDispatchTemplateVo);

    void update(DqcDispatchTemplateVo dqcDispatchTemplateVo);

    void delete(Integer delId);

    void deleteBulk(Integer delId);
}
