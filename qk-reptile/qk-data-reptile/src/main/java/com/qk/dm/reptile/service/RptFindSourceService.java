package com.qk.dm.reptile.service;

import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.reptile.params.dto.RptFindSourceDTO;
import com.qk.dm.reptile.params.vo.RptFindSourceVO;

public interface RptFindSourceService {
 void insert(RptFindSourceDTO rptFindSourceDTO);

 void update(Long id,RptFindSourceDTO rptFindSourceDTO);

 void delete(String ids);

 PageResultVO<RptFindSourceVO> list(RptFindSourceDTO rptFindSourceDTO);
}
