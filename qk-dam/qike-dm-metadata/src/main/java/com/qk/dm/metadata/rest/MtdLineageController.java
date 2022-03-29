package com.qk.dm.metadata.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.dm.metadata.lineage.LineageService;
import com.qk.dm.metadata.service.MetaDataLineageService;
import com.qk.dm.metadata.vo.LineageSearchVO;
import com.qk.dm.metadata.vo.MtdLineageParamsVO;
import com.qk.dm.metadata.vo.MtdLineageVO;

import java.io.ByteArrayInputStream;
import java.util.Map;

import org.apache.atlas.AtlasServiceException;
import org.apache.atlas.model.instance.AtlasEntityHeader;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 元数据血缘
 *
 * @author wangzp
 * @date 2021/10/18 10:36
 * @since 1.0.0
 */
@RestController
public class MtdLineageController {

  private final MetaDataLineageService metaDataLineageService;
  private final LineageService lineageService;

  public MtdLineageController(MetaDataLineageService metaDataLineageService, LineageService lineageService) {
    this.metaDataLineageService = metaDataLineageService;
    this.lineageService = lineageService;
  }

  /**
   * 获取元数据血缘,展示血缘关系图
   *
   * @param mtdLineageParamsVO
   * @return DefaultCommonResult<AtlasLineageInfo>
   */
  @PostMapping("/lineage")
  public DefaultCommonResult<MtdLineageVO> getLineageInfo(
      @RequestBody @Validated MtdLineageParamsVO mtdLineageParamsVO) {
    return DefaultCommonResult.success(
        ResultCodeEnum.OK, metaDataLineageService.getLineageInfo(mtdLineageParamsVO));
  }

  /**
   * 获取元数据过程 input output
   *
   * @param guid
   * @return DefaultCommonResult<RelationVO>
   */
  @GetMapping("/relation/{guid}")
  public DefaultCommonResult<Map<String, Object>> relationShip(@PathVariable("guid") String guid) {
    return DefaultCommonResult.success(
        ResultCodeEnum.OK, metaDataLineageService.relationShip(guid));
  }

  /**
   * 血缘分页查询
   *
   * @param lineageSearchVO 血缘查询条件
   * @return DefaultCommonResult
   */
  @PostMapping("/lineage/process")
  @ResponseBody
  public DefaultCommonResult<PageResultVO<AtlasEntityHeader>> lineageProcess(@RequestBody @Validated
                                                                                       LineageSearchVO lineageSearchVO) throws AtlasServiceException {
    return DefaultCommonResult.success(ResultCodeEnum.OK,
            lineageService.lineageProcess(lineageSearchVO));
  }

  /**
   * 血缘创建
   *
   * @param file excel temp
   * @return DefaultCommonResult
   */
  @PostMapping("/lineage/template/import")
  @ResponseBody
  public DefaultCommonResult lineageImport(@RequestParam MultipartFile file) throws Exception {
    lineageService.lineageImport(new ByteArrayInputStream(file.getBytes()));
    return DefaultCommonResult.success();
  }

  /**
   * 血缘删除清理
   * @param file 文件模板
   * @param deleteFl 是否真删除
   * @return DefaultCommonResult
   * @throws Exception 异常
   */
  @DeleteMapping("/lineage/template/import")
  @ResponseBody
  public DefaultCommonResult lineageClear(@RequestParam MultipartFile file,
                                          @RequestParam boolean deleteFl) throws Exception {
    lineageService.lineageClear(new ByteArrayInputStream(file.getBytes()));
    if (deleteFl){
      lineageService.realCleanEntities();
    }
    return DefaultCommonResult.success();
  }

}
