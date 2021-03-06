package com.qk.dm.dataservice.service.imp;

import com.qk.dam.commons.exception.BizException;
import com.qk.dm.dataservice.entity.DasApiBasicInfo;
import com.qk.dm.dataservice.entity.DasApiDir;
import com.qk.dm.dataservice.entity.QDasApiBasicInfo;
import com.qk.dm.dataservice.entity.QDasApiDir;
import com.qk.dm.dataservice.mapstruct.mapper.DasApiDirTreeMapper;
import com.qk.dm.dataservice.repositories.DasApiBasicInfoRepository;
import com.qk.dm.dataservice.repositories.DasApiDirRepository;
import com.qk.dm.dataservice.service.DasApiDirService;
import com.qk.dm.dataservice.vo.DasApiDirTreeVO;
import com.qk.dm.dataservice.vo.DasApiDirVO;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wjq
 * @date 20210816
 * @since 1.0.0
 */
@Service
public class DasApiDirServiceImpl implements DasApiDirService {
    private static final QDasApiDir qDasApiDir = QDasApiDir.dasApiDir;
  private static final QDasApiBasicInfo qDasApiBasicInfo = QDasApiBasicInfo.dasApiBasicInfo;

    private final DasApiDirRepository dasApiDirRepository;
    private final DasApiBasicInfoRepository dasApiBasicInfoRepository;

    @Autowired
    public DasApiDirServiceImpl(
            DasApiDirRepository dasApiDirRepository,
            DasApiBasicInfoRepository dasApiBasicInfoRepository) {
        this.dasApiDirRepository = dasApiDirRepository;
        this.dasApiBasicInfoRepository = dasApiBasicInfoRepository;
    }

    @Override
    public List<DasApiDirTreeVO> searchList() {
        Predicate predicate = qDasApiDir.delFlag.eq(0);
        List<DasApiDir> dasApiDirList = (List<DasApiDir>) dasApiDirRepository.findAll(predicate, Sort.by("dirName"));
        List<DasApiDirTreeVO> respList = new ArrayList<>();
        for (DasApiDir dasApiDir : dasApiDirList) {
            DasApiDirTreeVO dirTreeVO = DasApiDirTreeMapper.INSTANCE.useDasApiDirTreeVO(dasApiDir);
            respList.add(dirTreeVO);
        }
        return buildByRecursive(respList);
    }

    public static List<DasApiDirTreeVO> buildByRecursive(List<DasApiDirTreeVO> respList) {
        DasApiDirTreeVO topParent = DasApiDirTreeVO.builder().dirId("-1").title("?????????").value("?????????").parentId("-1").build();
        List<DasApiDirTreeVO> trees = new ArrayList<>();
        trees.add(findChildren(topParent, respList));

        return trees;
    }

    /**
     * ?????????????????????
     *
     * @param treeNode,respList
     * @return DasApiDirTreeVO
     */
    public static DasApiDirTreeVO findChildren(DasApiDirTreeVO treeNode, List<DasApiDirTreeVO> respList) {
        treeNode.setChildren(new ArrayList<>());
        for (DasApiDirTreeVO DasApiDirTreeVO : respList) {
            if (treeNode.getDirId().equals(DasApiDirTreeVO.getParentId())) {
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<>());
                }
                treeNode.getChildren().add(findChildren(DasApiDirTreeVO, respList));
            }
        }
        return treeNode;
    }

    @Override
    public void insert(DasApiDirVO dasApiDirVO) {
        DasApiDir dasApiDir = DasApiDirTreeMapper.INSTANCE.useDasApiDir(dasApiDirVO);
        dasApiDir.setDirId(UUID.randomUUID().toString().replaceAll("-", ""));

        dasApiDir.setGmtCreate(new Date());
        dasApiDir.setGmtModified(new Date());
        dasApiDir.setCreateUserid("admin");
        dasApiDir.setDelFlag(0);

        Predicate predicate = qDasApiDir.dirName.eq(dasApiDirVO.getTitle()).and(qDasApiDir.parentId.eq(dasApiDirVO.getParentId()));
        boolean exists = dasApiDirRepository.exists(predicate);
        if (exists) {
            throw new BizException("??????????????????API?????????????????????:" + dasApiDirVO.getTitle() + " ?????????????????????????????????????????????");
        }
        dasApiDirRepository.save(dasApiDir);
    }

    @Override
    public void update(DasApiDirVO dasApiDirVO) {
        //????????????dirId?????????ParentId??????
        checkDirIdIsEqualParentId(dasApiDirVO);

        //??????????????????????????????????????????????????????
        checkParentNodeAndChildNode(dasApiDirVO);

        DasApiDir dasApiDir = DasApiDirTreeMapper.INSTANCE.useDasApiDir(dasApiDirVO);
        dasApiDir.setGmtModified(new Date());
        dasApiDir.setCreateUserid("admin");
        dasApiDir.setDelFlag(0);

        Optional<DasApiDir> dsdDirOptional = dasApiDirRepository.findById(dasApiDirVO.getId());
        if (dsdDirOptional.isPresent()) {
            dasApiDirRepository.saveAndFlush(dasApiDir);
            //TODO ????????????????????????????????????
//            dsdBasicinfoRepository.updateDirLevelByDirId(dsdDirVO.getDsdDirLevel(), dsdDirVO.getDirDsdId());
        } else {
            throw new BizException("??????????????????API?????????????????????:" + dasApiDirVO.getTitle() + " ??????????????????????????????");
        }
    }

    private void checkDirIdIsEqualParentId(DasApiDirVO dasApiDirVO) {
        String dirId = dasApiDirVO.getDirId();
        String parentId = dasApiDirVO.getParentId();
        if (dirId.equals(parentId)) {
            throw new BizException("????????????????????????????????????,?????????????????????????????????????????????");
        }
    }

    private void checkParentNodeAndChildNode(DasApiDirVO dasApiDirVO) {
        Predicate predicate = qDasApiDir.delFlag.eq(0);
        List<DasApiDir> dasApiDirList = (List<DasApiDir>) dasApiDirRepository.findAll(predicate);
        List<DasApiDir> childDirList = new ArrayList<>();

        getDirIdsByParentId(dasApiDirList, dasApiDirVO.getDirId(), childDirList);

        List<String> childDirIds = childDirList.stream().map(DasApiDir::getDirId).collect(Collectors.toList());
        if (childDirIds.contains(dasApiDirVO.getParentId())) {
            throw new BizException("????????????????????????????????????,???????????????????????????????????????????????????");
        }
    }

    private void getDirIdsByParentId(List<DasApiDir> dasApiDirList, String dirId, List<DasApiDir> childDirIds) {
        for (DasApiDir dasApiDir : dasApiDirList) {
            if (dasApiDir.getParentId() != null) {
                if (dasApiDir.getParentId().equals(dirId)) {
                    getDirIdsByParentId(dasApiDirList, dasApiDir.getDirId(), childDirIds);
                    childDirIds.add(dasApiDir);
                }
            }
        }
    }

    @Override
    public void delete(String id) {
        ArrayList<Long> ids = new ArrayList<>();
        List<DasApiDir> childDirList = new ArrayList<>();
        List<DasApiDir> dirIsExistRulesList = new ArrayList<>();

        Long delId = Long.valueOf(id);
        // ????????????ID
        Predicate predicate = qDasApiDir.delFlag.eq(0);
        List<DasApiDir> dasApiDirList = (List<DasApiDir>) dasApiDirRepository.findAll(predicate);
        List<DasApiDir> dsdDirIsExist = dasApiDirList.stream().filter(dasApiDir -> dasApiDir.getId().equals(delId)).collect(Collectors.toList());
        if (dsdDirIsExist.size() < 1) {
            throw new BizException("????????????,??????????????????????????????????????????");
        }

        //?????????????????????
        DasApiDir dasApiDir = dsdDirIsExist.get(0);
        ids.add(delId);
        getDirIdsByParentId(dasApiDirList, dasApiDir.getDirId(), childDirList);
        childDirList.forEach((ruleDir) -> ids.add(ruleDir.getId()));

        //????????????????????????????????????
        Iterable<DasApiDir> delDirList = dasApiDirRepository.findAll(qDasApiDir.id.in(ids));
        for (DasApiDir ruleDir : delDirList) {
            dirIsExistRulesList.add(ruleDir);
        }
        deleteCheckIsRules(dirIsExistRulesList);
        // ????????????
        dasApiDirRepository.deleteAll(delDirList);
    }

    @Override
    public void deleteBulk(String ids) {
        List<String> idList = Arrays.asList(ids.split(","));
        Set<Long> idSet = new HashSet<>();
        idList.forEach(id -> idSet.add(Long.valueOf(id)));
        List<DasApiDir> dasApiDirList = dasApiDirRepository.findAllById(idSet);

        //????????????????????????????????????
        deleteCheckIsRules(dasApiDirList);
        // ????????????
        dasApiDirRepository.deleteAll(dasApiDirList);

    }

    private void deleteCheckIsRules(List<DasApiDir> dirIsExistRulesList) {
        List<String> dirIdList = dirIsExistRulesList.stream().map(DasApiDir::getDirId).collect(Collectors.toList());
        Iterable<DasApiBasicInfo> ruleTemplates = dasApiBasicInfoRepository.findAll(qDasApiBasicInfo.apiId.in(dirIdList));

        if (ruleTemplates.iterator().hasNext()) {
            throw new BizException("??????????????????API????????????,??????API?????????????????????");
        }
    }

    /** ??????API??????id??????????????????????????????id */
    @Override
    public void getApiDirId(Set<String> apiDirIdSet, String dirId) {
        Optional<DasApiDir> parentDir = dasApiDirRepository.findOne(qDasApiDir.dirId.eq(dirId));
        if (parentDir.isPresent()) {
            apiDirIdSet.add(parentDir.get().getDirId());
            Iterable<DasApiDir> sonDirList =
                    dasApiDirRepository.findAll(qDasApiDir.parentId.eq(parentDir.get().getDirId()));
            for (DasApiDir dasApiDir : sonDirList) {
                apiDirIdSet.add(dasApiDir.getDirId());
                this.getApiDirId(apiDirIdSet, dasApiDir.getDirId());
            }
        }
    }

    @Override
    public List<DasApiDirVO> getDasApiDirByDirName(String title) {
        List<DasApiDirVO> dasApiDirVOList = new ArrayList<>();
        Predicate predicate = qDasApiDir.dirName.eq(title);
        Iterable<DasApiDir> dasApiDirs = dasApiDirRepository.findAll(predicate);
        for (DasApiDir dasApiDir : dasApiDirs) {
            DasApiDirVO dasApiDirVO = DasApiDirTreeMapper.INSTANCE.useDasApiDirVO(dasApiDir);
            dasApiDirVOList.add(dasApiDirVO);
        }
        return dasApiDirVOList;
    }

    @Override
    public DasApiDir searchApiDirInfoByDirId(String dirId) {
        Optional<DasApiDir> dasApiDirOptional = dasApiDirRepository.findOne(qDasApiDir.dirId.eq(dirId));
        return dasApiDirOptional.orElse(null);
    }

}
