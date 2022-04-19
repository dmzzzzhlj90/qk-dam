package com.qk.dm.dataservice.service.imp;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.qk.dam.commons.exception.BizException;
import com.qk.dm.dataservice.constant.DasConstant;
import com.qk.dm.dataservice.easyexcel.ExcelBatchSaveFlushDataService;
import com.qk.dm.dataservice.easyexcel.listener.BasicInfoUploadDataListener;
import com.qk.dm.dataservice.easyexcel.listener.CreateConfigUploadDataListener;
import com.qk.dm.dataservice.easyexcel.listener.CreateSqlScriptUploadDataListener;
import com.qk.dm.dataservice.easyexcel.listener.RegisterUploadDataListener;
import com.qk.dm.dataservice.entity.DasApiDir;
import com.qk.dm.dataservice.entity.QDasApiDir;
import com.qk.dm.dataservice.repositories.DasApiDirRepository;
import com.qk.dm.dataservice.service.*;
import com.qk.dm.dataservice.vo.DasApiBasicInfoVO;
import com.qk.dm.dataservice.vo.DasApiCreateConfigDefinitionVO;
import com.qk.dm.dataservice.vo.DasApiCreateSqlScriptDefinitionVO;
import com.qk.dm.dataservice.vo.DasApiRegisterDefinitionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 数据服务__Excel数据迁移功能
 *
 * @author wjq
 * @date 2021/04/18
 * @since 1.0.0
 */
@Service
public class DasApiExcelServiceImpl implements DasApiExcelService {
    private static final Log LOG = LogFactory.get("数据标准excel导入导出");

    private final DasApiBasicInfoService dasApiBasicInfoService;
    private final DasApiCreateConfigService dasApiCreateConfigService;
    private final DasApiCreateSqlScriptService dasApiCreateSqlScriptService;
    private final DasApiRegisterService dasApiRegisterService;
    private final ExcelBatchSaveFlushDataService excelBatchSaveFlushDataService;
    private final DasApiDirRepository dasApiDirRepository;

    @Autowired
    public DasApiExcelServiceImpl(DasApiBasicInfoService dasApiBasicInfoService,
                                  DasApiCreateConfigService dasApiCreateConfigService,
                                  DasApiCreateSqlScriptService dasApiCreateSqlScriptService,
                                  DasApiRegisterService dasApiRegisterService,
                                  ExcelBatchSaveFlushDataService excelBatchSaveFlushDataService, DasApiDirRepository dasApiDirRepository) {
        this.dasApiBasicInfoService = dasApiBasicInfoService;
        this.dasApiCreateConfigService = dasApiCreateConfigService;
        this.dasApiCreateSqlScriptService = dasApiCreateSqlScriptService;
        this.dasApiRegisterService = dasApiRegisterService;
        this.excelBatchSaveFlushDataService = excelBatchSaveFlushDataService;
        this.dasApiDirRepository = dasApiDirRepository;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void apiDataUpload(MultipartFile file, String dirId) {
        LOG.info("============================开始导入API!============================");
        try {
            // API基础信息
            EasyExcel.read(file.getInputStream(), DasApiBasicInfoVO.class,
                    new BasicInfoUploadDataListener(excelBatchSaveFlushDataService, dirId))
                    .sheet(0)
                    .doRead();
            LOG.info("================API基础信息导入数据完成================");

            // 新建API_配置方式
            EasyExcel.read(file.getInputStream(), DasApiCreateConfigDefinitionVO.class,
                    new CreateConfigUploadDataListener(excelBatchSaveFlushDataService))
                    .sheet(1)
                    .doRead();
            LOG.info("================新建API_配置方式导入数据完成================");

            // 新建API_取数SQL方式
            EasyExcel.read(file.getInputStream(), DasApiCreateSqlScriptDefinitionVO.class,
                    new CreateSqlScriptUploadDataListener(excelBatchSaveFlushDataService))
                    .sheet(2)
                    .doRead();
            LOG.info("================新建API_取数SQL方式导入数据完成================");

            // 注册API
            EasyExcel.read(file.getInputStream(), DasApiRegisterDefinitionVO.class,
                    new RegisterUploadDataListener(excelBatchSaveFlushDataService))
                    .sheet(3)
                    .doRead();
            LOG.info("================注册API导入数据完成================");

        } catch (Exception e) {
            LOG.info("============================导入API失败!============================");
            throw new BizException("导入失败: " + e.getMessage());
        }
        LOG.info("============================成功导入API!============================");
    }

    @Override
    public void apiDataDownload(String dirId,HttpServletResponse response) throws IOException {
        // 根据目录设置文件名称
        String dirName = searchDirName(dirId);
        String name = "目录为" + dirName + "的API接口信息";
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode(name, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

        // API基础信息
        List<DasApiBasicInfoVO> apiBasicInfoVOList = searchBasicInfoDataByDirId(dirId);
        ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).build();
        WriteSheet basicInfoSheet = EasyExcel.writerSheet(0, "API基础信息")
                .head(DasApiBasicInfoVO.class)
                .build();
        excelWriter.write(apiBasicInfoVOList, basicInfoSheet);

        //获取apiID
        List<String> apiIds = apiBasicInfoVOList.stream().map(DasApiBasicInfoVO::getApiId).collect(Collectors.toList());

        // 新建API_配置方式
        List<DasApiCreateConfigDefinitionVO> configDefinitionVOList = searchCreateConfigByApiId(apiIds);
        WriteSheet createConfigSheet = EasyExcel.writerSheet(1, "新建API_配置方式")
                .head(DasApiCreateConfigDefinitionVO.class)
                .build();
        excelWriter.write(configDefinitionVOList, createConfigSheet);

        // 新建API_取数SQL方式
        List<DasApiCreateSqlScriptDefinitionVO> createSqlScriptDefinitionVOList = searchCreateSqlScriptByApiId(apiIds);
        WriteSheet createSqlScriptSheet = EasyExcel.writerSheet(2, "新建API_取数SQL方式")
                .head(DasApiCreateSqlScriptDefinitionVO.class)
                .build();
        excelWriter.write(createSqlScriptDefinitionVOList, createSqlScriptSheet);

        // 注册API
        List<DasApiRegisterDefinitionVO> registerDefinitionVOList = searchRegisterByApiId(apiIds);
        WriteSheet registerSheet = EasyExcel.writerSheet(3, "注册API")
                .head(DasApiRegisterDefinitionVO.class)
                .build();
        excelWriter.write(registerDefinitionVOList, registerSheet);

        excelWriter.finish();
    }


    /**
     * 根据目录ID查询API基础信息
     *
     * @param dirId
     * @return
     */
    private List<DasApiBasicInfoVO> searchBasicInfoDataByDirId(String dirId) {
        return dasApiBasicInfoService.searchBasicInfoDataByDirId(dirId);
    }

    /**
     * 根据apiId,查询新建API_配置方式信息
     *
     * @param apiIds
     * @return
     */
    private List<DasApiCreateConfigDefinitionVO> searchCreateConfigByApiId(List<String> apiIds) {
        return dasApiCreateConfigService.searchCreateConfigByApiId(apiIds);
    }

    /**
     * 根据apiId,查询新建API_取数SQL方式信息
     *
     * @param apiIds
     * @return
     */
    private List<DasApiCreateSqlScriptDefinitionVO> searchCreateSqlScriptByApiId(List<String> apiIds) {
        return dasApiCreateSqlScriptService.searchCreateSqlScriptByApiId(apiIds);
    }

    /**
     * 根据apiId,注册API信息
     *
     * @param apiIds
     * @return
     */
    private List<DasApiRegisterDefinitionVO> searchRegisterByApiId(List<String> apiIds) {
        return dasApiRegisterService.searchRegisterByApiId(apiIds);
    }

    /**
     * 根据目录设置文件名称
     *
     * @param dirId
     */
    private String searchDirName(String dirId) {
        String realDirName;
        Optional<DasApiDir> dasApiDirOptional = dasApiDirRepository.findOne(QDasApiDir.dasApiDir.dirId.eq(dirId));
        if (dasApiDirOptional.isPresent()) {
            realDirName = dasApiDirOptional.get().getDirName();
        } else {
            realDirName = DasConstant.TREE_DIR_TOP_PARENT_NAME;
        }
        return realDirName;
    }

//    private List<DasApiBasicInfoVO> getData1() {
//        List<DasApiBasicInfoVO> data1 = Lists.newArrayList();
//
//        for (int i = 0; i < 11; i++) {
//            DasApiBasicInfoVO dasApiBasicInfoVO = DasApiBasicInfoVO.builder()
//                    .apiId("apiID" + i).apiName("apiName" + i).dirName("dirName" + i)
//                    .dirId("apiID" + i).apiPath("apiID" + i).apiType("apiID" + i)
////                    .apiBasicInfoRequestParasVOS(new ArrayList<>())
//                    .protocolType("protocolType" + i).status("status" + i)
//                    .description("description" + 1)
//                    .build();
//            data1.add(dasApiBasicInfoVO);
//        }
//        return data1;
//    }

}
