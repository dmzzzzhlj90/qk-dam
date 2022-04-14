package com.qk.dm.feign;

import com.qk.dam.commons.http.result.DefaultCommonResult;
import com.qk.dam.metedata.entity.*;
import com.qk.dam.metedata.vo.MtdColumnSearchVO;
import com.qk.dam.metedata.vo.MtdDbSearchVO;
import com.qk.dam.metedata.vo.MtdTableSearchVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "dm-metadata-test")
@Component
public interface MetaDataFeign {

  /**
   * 获取所有的类型
   *
   * @return DefaultCommonResult<List < MtdAtlasEntityType>>
   */
  @GetMapping("/mtd/entity/types")
  DefaultCommonResult<List<MtdAtlasEntityType>> getAllEntityType();

  /**
   * 获取元数据详情信息
   *
   * @param mtdApiParams 元数据参数
   * @return DefaultCommonResult<MtdApi>
   */
  @PostMapping("/mtd/entity/detail")
  DefaultCommonResult<MtdApi> mtdDetail(@RequestBody MtdApiParams mtdApiParams);

  /**
   * 获取数据库列表
   * @param mtdDbSearchVO 库对象
   * @return List<MtdApiDb>
   */
  @PostMapping("/mtd/database/list")
  DefaultCommonResult<List<MtdApiDb>> getDataBaseList(@RequestBody MtdDbSearchVO mtdDbSearchVO);

  /**
   * 获取数据表列表
   * @param mtdTableSearchVO 表对象
   * @return List<MtdTables>
   */
  @PostMapping("/mtd/table/list")
  DefaultCommonResult<List<MtdTables>> getTableList(@RequestBody MtdTableSearchVO mtdTableSearchVO);

  /**
   * 获取数据列列表
   * @param mtdColumnSearchVO 列对象
   * @return MtdTables
   */
  @PostMapping("/mtd/column/list")
  DefaultCommonResult<List<MtdAttributes>> getColumnList(@RequestBody MtdColumnSearchVO mtdColumnSearchVO);

  /**
   * 根据数据库类型和属性获取数据库信息
   * @param typeName 类型名称
   * @param attrValue 属性值
   * @return 元数据API
   */
  @GetMapping("/mtd/dbs/{typeName}/{attrValue}")
  DefaultCommonResult<MtdApi> getDbs(@PathVariable("typeName") String typeName, @PathVariable("attrValue") String attrValue);

  /**
   * 查询元数据是否存在该表和表中是否存在数据
   * @param mtdApiParams
   * @return
   */
  @PostMapping("mtd/entity/exist/data")
  DefaultCommonResult<Integer> getExistData(@RequestBody @Validated MtdApiParams mtdApiParams);

  /**
   * 根据表的guid获取表的字段信息
   * @param guid
   * @return
   */
  @GetMapping("mtd/column/list/{guid}")
  DefaultCommonResult< List<MtdAttributes>> getColumnListByTableGuid(@PathVariable("guid") String guid);

}
