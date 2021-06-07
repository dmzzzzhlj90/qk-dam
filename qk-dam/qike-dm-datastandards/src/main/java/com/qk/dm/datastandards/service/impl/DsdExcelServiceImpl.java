package com.qk.dm.datastandards.service.impl;

import com.alibaba.excel.EasyExcel;
import com.qk.dm.datastandards.entity.DsdBasicinfo;
import com.qk.dm.datastandards.entity.DsdCodeTerm;
import com.qk.dm.datastandards.entity.DsdTerm;
import com.qk.dm.datastandards.listener.DsdBasicInfoUploadDataListener;
import com.qk.dm.datastandards.listener.DsdCodeTermUploadDataListener;
import com.qk.dm.datastandards.listener.DsdTermUploadDataListener;
import com.qk.dm.datastandards.mapstruct.mapper.DsdBasicInfoMapper;
import com.qk.dm.datastandards.mapstruct.mapper.DsdCodeTermMapper;
import com.qk.dm.datastandards.mapstruct.mapper.DsdTermMapper;
import com.qk.dm.datastandards.repositories.DsdBasicinfoRepository;
import com.qk.dm.datastandards.repositories.DsdCodeTermRepository;
import com.qk.dm.datastandards.repositories.DsdTermRepository;
import com.qk.dm.datastandards.service.DsdExcelService;
import com.qk.dm.datastandards.vo.DsdBasicinfoVO;
import com.qk.dm.datastandards.vo.DsdCodeTermVO;
import com.qk.dm.datastandards.vo.DsdTermVO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据标准excel导入导出
 *
 * @author wjq
 * @date 2021/6/5 17:43
 * @since 1.0.0
 */
@Service
public class DsdExcelServiceImpl implements DsdExcelService {
    private final DsdTermRepository dsdTermRepository;
    private final DsdBasicinfoRepository dsdBasicinfoRepository;
    private final DsdCodeTermRepository dsdCodeTermRepository;

    public DsdExcelServiceImpl(DsdTermRepository dsdTermRepository,
                               DsdBasicinfoRepository dsdBasicinfoRepository,
                               DsdCodeTermRepository dsdCodeTermRepository) {
        this.dsdTermRepository = dsdTermRepository;
        this.dsdBasicinfoRepository = dsdBasicinfoRepository;
        this.dsdCodeTermRepository = dsdCodeTermRepository;
    }

    @Override
    public List<DsdTermVO> queryAllTerm() {
        List<DsdTermVO> dsdTermVOList = new ArrayList<DsdTermVO>();

        List<DsdTerm> dsdTermList = dsdTermRepository.findAll();
        dsdTermList.forEach(dsdTerm -> {
            DsdTermVO dsdTermVO = DsdTermMapper.INSTANCE.useDsdTermVO(dsdTerm);
            dsdTermVOList.add(dsdTermVO);
        });
        return dsdTermVOList;
    }

    @Override
    public void termUpload(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), DsdTermVO.class, new DsdTermUploadDataListener(dsdTermRepository)).sheet().doRead();
    }

    @Override
    public List<DsdBasicinfoVO> queryAllBasicInfo() {
        List<DsdBasicinfoVO> dsdBasicInfoVOList = new ArrayList<DsdBasicinfoVO>();

        List<DsdBasicinfo> dsdTermList = dsdBasicinfoRepository.findAll();
        dsdTermList.forEach(dsdBasicInfo -> {
            DsdBasicinfoVO dsdBasicinfoVO = DsdBasicInfoMapper.INSTANCE.useDsdBasicInfoVO(dsdBasicInfo);
            dsdBasicInfoVOList.add(dsdBasicinfoVO);
        });
        return dsdBasicInfoVOList;
    }

    @Override
    public void basicInfoUpload(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), DsdBasicinfoVO.class, new DsdBasicInfoUploadDataListener(dsdBasicinfoRepository)).sheet().doRead();
    }

    @Override
    public List<DsdCodeTermVO> queryAllCodeTerm() {
        List<DsdCodeTermVO> codeTermVOList = new ArrayList<DsdCodeTermVO>();

        List<DsdCodeTerm> dsdTermList = dsdCodeTermRepository.findAll();
        dsdTermList.forEach(dsdCodeTerm -> {
            DsdCodeTermVO dsdCodeTermVO = DsdCodeTermMapper.INSTANCE.usDsdCodeTermVO(dsdCodeTerm);
            codeTermVOList.add(dsdCodeTermVO);
        });
        return codeTermVOList;
    }

    @Override
    public void codeTermUpload(MultipartFile file) throws IOException {
        EasyExcel.read(file.getInputStream(), DsdCodeTermVO.class, new DsdCodeTermUploadDataListener(dsdCodeTermRepository)).sheet().doRead();
    }
}
