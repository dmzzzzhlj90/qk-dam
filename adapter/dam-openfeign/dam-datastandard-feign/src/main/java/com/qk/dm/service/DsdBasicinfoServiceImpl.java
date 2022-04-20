package com.qk.dm.service;

import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.entity.DataStandardTreeVO;
import com.qk.dam.entity.DsdBasicInfoParamsDTO;
import com.qk.dam.entity.DsdBasicInfoVO;
import com.qk.dam.entity.DsdBasicinfoParamsVO;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.feign.DatastandardsFeign;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhudaoming
 */
@Service
public class DsdBasicinfoServiceImpl implements DsdBasicinfoService{
    private final DatastandardsFeign datastandardsFeign;

    public DsdBasicinfoServiceImpl(DatastandardsFeign datastandardsFeign) {
        this.datastandardsFeign = datastandardsFeign;
    }

    @Override
    public PageResultVO<DsdBasicinfoParamsVO> getStandard(DsdBasicInfoParamsDTO dsdBasicInfoParamsDTO){
        PageResultVO<DsdBasicinfoParamsVO> resultPag = new PageResultVO<>();
        PageResultVO<DsdBasicInfoVO> dsdBasicInfoVOPageResultVO = null;
        DefaultCommonResult<PageResultVO<DsdBasicInfoVO>> pageResultVODefaultCommonResult = datastandardsFeign.searchList(dsdBasicInfoParamsDTO);
        if (pageResultVODefaultCommonResult != null) {
            dsdBasicInfoVOPageResultVO = pageResultVODefaultCommonResult.getData();
        }
        if (dsdBasicInfoVOPageResultVO !=null && !CollectionUtils.isEmpty(dsdBasicInfoVOPageResultVO.getList())){
            List<DsdBasicinfoParamsVO> collect = dsdBasicInfoVOPageResultVO.getList()
                    .stream().map(dsdBasicInfoVO -> {
                        DsdBasicinfoParamsVO dsdBasicinfoParamsVO = new DsdBasicinfoParamsVO();
                        dsdBasicinfoParamsVO.setId(dsdBasicInfoVO.getId());
                        dsdBasicinfoParamsVO.setDsdCode(dsdBasicInfoVO.getDsdCode());
                        dsdBasicinfoParamsVO.setDsdName(dsdBasicInfoVO.getDsdName());
                        return dsdBasicinfoParamsVO;
                    }).collect(Collectors.toList());
            BeanUtils.copyProperties(dsdBasicInfoVOPageResultVO,resultPag);
            resultPag.setList(collect);
        }
        return resultPag;
    }

    @Override
    public List<DataStandardTreeVO> getTree() {
        return datastandardsFeign.searchList().getData();
    }


}
