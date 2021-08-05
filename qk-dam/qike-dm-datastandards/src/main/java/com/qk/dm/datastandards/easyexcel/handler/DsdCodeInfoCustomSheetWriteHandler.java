package com.qk.dm.datastandards.easyexcel.handler;

import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import com.qk.dm.datastandards.service.DsdExcelService;
import java.util.List;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数据标准码表基本信息 自定义拦截器 设置excel属性值
 *
 * @author wjq
 */
public class DsdCodeInfoCustomSheetWriteHandler implements SheetWriteHandler {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(DsdCodeInfoCustomSheetWriteHandler.class);
  private final DsdExcelService dsdExcelService;

  public DsdCodeInfoCustomSheetWriteHandler(DsdExcelService dsdExcelService) {
    this.dsdExcelService = dsdExcelService;
  }

  @Override
  public void beforeSheetCreate(
      WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {}

  @Override
  public void afterSheetCreate(
      WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
    LOGGER.info("第{}个Sheet写入成功。", writeSheetHolder.getSheetNo());

    // 获取码表目录
    List<String> dsdCodeDirLevelList = dsdExcelService.findAllDsdCodeDirLevel();

    // 区间设置 第一列第一行和第二行的数据。由于第一行是头，所以第一、二行的数据实际上是第二三行
    CellRangeAddressList cellRangeAddressList = new CellRangeAddressList(1, 100000, 0, 0);
    DataValidationHelper helper = writeSheetHolder.getSheet().getDataValidationHelper();
    DataValidationConstraint constraint =
        helper.createExplicitListConstraint(
            dsdCodeDirLevelList.toArray(new String[dsdCodeDirLevelList.size()]));
    DataValidation dataValidation = helper.createValidation(constraint, cellRangeAddressList);
    writeSheetHolder.getSheet().addValidationData(dataValidation);
  }
}
