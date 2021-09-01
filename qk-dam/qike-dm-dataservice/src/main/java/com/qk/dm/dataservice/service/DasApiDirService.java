package com.qk.dm.dataservice.service;

import com.qk.dm.dataservice.vo.DasApiDirTreeVO;
import com.qk.dm.dataservice.vo.DasApiDirVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author wjq
 * @date 20210816
 * @since 1.0.0
 */
@Service
public interface DasApiDirService {

    List<DasApiDirTreeVO> getTree();

    void addDasApiDir(DasApiDirVO daasApiDirVO);

    void updateDasApiDir(DasApiDirVO daasApiDirVO);

    void deleteDasApiDir(Long delId);

    void deleteDasApiDirRoot(Long delId);

    void getApiDirId(Set<String> apiDirIdSet, String apiDirId);

    List<DasApiDirVO> getDasApiDirByDirName(String title);

}
