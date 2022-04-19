package com.qk.dm.metadata.lineage;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.metadata.vo.LineageSearchVO;
import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.model.instance.AtlasEntityHeader;

import java.io.InputStream;
import java.util.List;

public interface LineageService {
    PageResultVO<AtlasEntityHeader> lineageProcess(LineageSearchVO lineageSearchVO) throws AtlasServiceException;

    void lineageImport(InputStream excelFile) throws AtlasServiceException;

    void lineageClear(InputStream excelFile) throws AtlasServiceException;

    void deleteEntitiesByGuids(List<String> guids) throws AtlasServiceException;

    void realCleanEntities() throws AtlasServiceException;
}
