package com.qk.dm.dataingestion.rest;

import com.qk.dam.commons.enums.ResultCodeEnum;
import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.jpa.pojo.PageResultVO;
import com.qk.datacenter.client.ApiException;
import com.qk.dm.dataingestion.service.DataMigrationService;
import com.qk.dm.dataingestion.vo.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 离线数据引入
 * @author wangzp
 * @date 2022/04/08 15:31
 * @since 1.0.0
 */
@RestController
@RequestMapping("/migration")
public class DataMigrationController {
    private final DataMigrationService dataMigrationService;

    public DataMigrationController(DataMigrationService dataMigrationService) {

        this.dataMigrationService = dataMigrationService;
    }

    /**
     * 添加作业信息
     * @param dataMigrationVO 作业实体信息
     * @return DefaultCommonResult
     */
    @PostMapping("")
    public DefaultCommonResult add(@RequestBody @Validated DataMigrationVO dataMigrationVO) throws ApiException {
        dataMigrationService.insert(dataMigrationVO);
        return DefaultCommonResult.success();
    }

    /**
     * 修改作业信息
     * @param dataMigrationVO 作业实体信息
     * @return DefaultCommonResult
     */
    @PutMapping("")
    public DefaultCommonResult update(@RequestBody @Validated DataMigrationVO dataMigrationVO)throws ApiException {
        dataMigrationService.update(dataMigrationVO);
        return DefaultCommonResult.success();
    }

    /**
     * 查询作业详情信息
     * @param id 作业id
     * @return DefaultCommonResult<DataMigrationVO>
     */
    @GetMapping("/detail/{id}")
    public DefaultCommonResult<DataMigrationVO> detail(@PathVariable("id") Long id){
        return DefaultCommonResult.success(ResultCodeEnum.OK,dataMigrationService.detail(id));
    }
    /**
     * 查询json详情
     * @param id 作业id
     * @return DefaultCommonResult<Map<String,Object>>
     */
    @GetMapping("/json/detail/{id}")
    public DefaultCommonResult<Map<String,Object>> jsonDetail(@PathVariable("id") Long id){
        return DefaultCommonResult.success(ResultCodeEnum.OK,dataMigrationService.jsonDetail(id));
    }

    /**
     * 批量删除作业
     * @param ids 作业id，如果多个使用英文逗号分割
     * @return DefaultCommonResult
     */
    @DeleteMapping("/{ids}")
    public DefaultCommonResult batchDelete(@PathVariable("ids") String ids){
        dataMigrationService.delete(ids);
        return DefaultCommonResult.success();
    }

    /**
     * 作业列表
     * @param paramsVO 查询参数
     * @return DefaultCommonResult<PageResultVO<DdgMigrationBaseInfoVO>
     */
    @PostMapping("/list")
    public DefaultCommonResult<PageResultVO<DisMigrationBaseInfoVO>> list(@RequestBody @Validated DisParamsVO paramsVO){
        return DefaultCommonResult.success(ResultCodeEnum.OK,dataMigrationService.pageList(paramsVO));
    }

    /**
     * 获取作业字段列表
     * @param vo 参数信息
     * @return DefaultCommonResult<ColumnVO>
     */
    @PostMapping("/column/list")
    public DefaultCommonResult<ColumnVO> getColumnList(@RequestBody @Validated DisMigrationBaseInfoVO vo){

        return DefaultCommonResult.success(ResultCodeEnum.OK,dataMigrationService.getColumnList(vo));
    }

    /**
     * 运行
     * @param ids 作业id，多个使用英文逗号分割
     * @return DefaultCommonResult
     */
    @PostMapping("/executors/start")
    public DefaultCommonResult processRunning(@RequestParam("ids") String ids){
        dataMigrationService.processRunning(ids);
        return DefaultCommonResult.success();
    }

    /**
     * 修改datax Json
     * @param disJsonParamsVO 参数
     * @return DefaultCommonResult
     */
    @PutMapping("/datax/json")
    public DefaultCommonResult updateDataxJson(@RequestBody @Validated
                                                           DisJsonParamsVO disJsonParamsVO){
        dataMigrationService.updateDataxJson(disJsonParamsVO);
        return DefaultCommonResult.success();
    }


}
