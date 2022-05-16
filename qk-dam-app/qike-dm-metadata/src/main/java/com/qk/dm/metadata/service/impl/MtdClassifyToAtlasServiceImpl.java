package com.qk.dm.metadata.service.impl;

import com.qk.dam.commons.exception.BizException;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dam.metedata.util.AtlasLabelsUtil;
import com.qk.dm.metadata.service.MtdClassifyService;
import com.qk.dm.metadata.vo.MtdClassifyListVO;
import com.qk.dm.metadata.vo.MtdClassifyVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.model.typedef.AtlasClassificationDef;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wangzp
 * @date 2021/7/31 13:10
 * @since 1.0.0
 */
@Service
@Slf4j
public class MtdClassifyToAtlasServiceImpl implements MtdClassifyService {

    @Override
    public void insert(MtdClassifyVO mtdClassifyVO) {
        try {
            AtlasLabelsUtil.addTypedefs(mtdClassifyVO.getName(), mtdClassifyVO.getDescription());
        } catch (AtlasServiceException e) {
            log.error("新增分类失败,名称[{}]，描述[{}] 失败原因[{}]", mtdClassifyVO.getName(), mtdClassifyVO.getDescription(), e.getMessage());
            throw new BizException("操作失败");
        }
    }

    @Override
    public void update(Long id, MtdClassifyVO mtdClassifyVO) {
        throw new BizException("操作失败，不能修改");
    }

    @Override
    public void delete(MtdClassifyVO mtdClassifyVO) {
        try {
            AtlasLabelsUtil.delTypedefs(mtdClassifyVO.getName());
        } catch (AtlasServiceException e) {
            log.error("删除分类失败,名称[{}]，失败原因[{}]", mtdClassifyVO.getName(), e.getMessage());
            throw new BizException("操作失败");
        }
    }

    @Override
    public PageResultVO<MtdClassifyVO> listByPage(MtdClassifyListVO mtdClassifyListVO) {
        try {
            List<AtlasClassificationDef> atlasClassificationDefs = AtlasLabelsUtil.getClassis(null);
            List<MtdClassifyVO> list = atlasClassificationDefs
                    .stream()
                    .map(clazz -> new MtdClassifyVO(clazz.getName(), clazz.getDescription()))
                    .collect(Collectors.toList());
            return new PageResultVO<>(
                    list.size(),
                    mtdClassifyListVO.getPagination().getPage(),
                    mtdClassifyListVO.getPagination().getSize(),
                    list);
        } catch (AtlasServiceException e) {
            log.error("查询分类失败,失败原因[{}]", e.getMessage());
            throw new BizException("查询失败");
        }
    }

    @Override
    public List<MtdClassifyVO> listByAll(MtdClassifyVO mtdClassifyVO) {
        try {
            List<AtlasClassificationDef> atlasClassificationDefs = AtlasLabelsUtil.getClassis(mtdClassifyVO.getName());
            return atlasClassificationDefs
                    .stream()
                    .map(clazz -> new MtdClassifyVO(clazz.getName(), clazz.getDescription()))
                    .collect(Collectors.toList());
        } catch (AtlasServiceException e) {
            log.error("查询分类失败,失败原因[{}]", e.getMessage());
            throw new BizException("查询失败");
        }
    }
}
