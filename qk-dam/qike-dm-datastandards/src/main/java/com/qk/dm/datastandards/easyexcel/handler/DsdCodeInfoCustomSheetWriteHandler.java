package com.qk.dm.datastandards.easyexcel.handler;

import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import java.util.Map;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;

/**
 * 数据标准码表基本信息 自定义拦截器 设置excel属性值
 *
 * @author wjq
 */
public class DsdCodeInfoCustomSheetWriteHandler implements SheetWriteHandler {

  /** 下拉框内容map Integer数据所在列数，string[]下拉数据列表 */
  private Map<Integer, String[]> spinnerMap;
  /** 导出数据的list大小 */
  private int dataSize;

  public DsdCodeInfoCustomSheetWriteHandler(Map<Integer, String[]> spinnerMap, int dataSize) {
    this.spinnerMap = spinnerMap;
    this.dataSize = dataSize;
  }

  @Override
  public void beforeSheetCreate(
      WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {}

  @Override
  public void afterSheetCreate(
      WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
    // 获取一个workbook
    Sheet sheet = writeSheetHolder.getSheet();
    // 设置下拉框
    DataValidationHelper helper = sheet.getDataValidationHelper();
    // 定义sheet的名称
    String hiddenName = "hidden";
    // 1.创建一个隐藏的sheet 名称为 hidden
    Workbook workbook = writeWorkbookHolder.getWorkbook();
    Sheet hidden = workbook.createSheet(hiddenName);
    Name category1Name = workbook.createName();
    category1Name.setNameName(hiddenName);
    for (Map.Entry<Integer, String[]> entry : spinnerMap.entrySet()) {
      // 下拉框的起始行,结束行,起始列,结束列
      CellRangeAddressList addressList =
          new CellRangeAddressList(1, dataSize + 500, entry.getKey(), entry.getKey());
      // 获取excel列名
      String excelLine = getExcelLine(entry.getKey());

      // 2.循环赋值
      String[] values = entry.getValue();
      for (int i = 0, length = values.length; i < length; i++) {
        // 3:表示你开始的行数  3表示 你开始的列数
        Row row = hidden.getRow(i);
        if (row == null) {
          row = hidden.createRow(i);
        }
        row.createCell(entry.getKey()).setCellValue(values[i]);
      }

      // 4.  =hidden!$H:$1:$H$50  sheet为hidden的 H1列开始H50行数据获取下拉数组
      String refers =
          "=" + hiddenName + "!$" + excelLine + "$1:$" + excelLine + "$" + (values.length);
      // 5 将刚才设置的sheet引用到你的下拉列表中
      DataValidationConstraint constraint = helper.createFormulaListConstraint(refers);
      DataValidation dataValidation = helper.createValidation(constraint, addressList);
      writeSheetHolder.getSheet().addValidationData(dataValidation);
    }
    // 设置列为隐藏
    int hiddenIndex = workbook.getSheetIndex("hidden");
    if (!workbook.isSheetHidden(hiddenIndex)) {
      workbook.setSheetHidden(hiddenIndex, true);
    }
  }

  /**
   * //返回excel列标A-Z-AA-ZZ
   *
   * @param num 列数
   * @return
   */
  public static String getExcelLine(int num) {
    String line = "";
    int first = num / 26;
    int second = num % 26;
    if (first > 0) {
      line = (char) ('A' + first - 1) + "";
    }
    line += (char) ('A' + second) + "";
    return line;
  }

  //  private static final Logger LOGGER =
  //      LoggerFactory.getLogger(DsdCodeInfoCustomSheetWriteHandler.class);
  //  private final DsdExcelService dsdExcelService;
  //
  //  public DsdCodeInfoCustomSheetWriteHandler(DsdExcelService dsdExcelService) {
  //    this.dsdExcelService = dsdExcelService;
  //  }
  //
  //  @Override
  //  public void beforeSheetCreate(
  //      WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {}
  //
  //  @Override
  //  public void afterSheetCreate(
  //      WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
  //    LOGGER.info("第{}个Sheet写入成功。", writeSheetHolder.getSheetNo());
  //
  //    // 获取码表目录
  //    List<String> dsdCodeDirLevelList = dsdExcelService.findAllDsdCodeDirLevel();
  //
  //    // 区间设置 第一列第一行和第二行的数据。由于第一行是头，所以第一、二行的数据实际上是第二三行
  //    CellRangeAddressList cellRangeAddressList = new CellRangeAddressList(1, 100000, 0, 0);
  //    DataValidationHelper helper = writeSheetHolder.getSheet().getDataValidationHelper();
  //    DataValidationConstraint constraint =
  //        helper.createExplicitListConstraint(
  //            dsdCodeDirLevelList.toArray(new String[dsdCodeDirLevelList.size()]));
  //    DataValidation dataValidation = helper.createValidation(constraint, cellRangeAddressList);
  //    writeSheetHolder.getSheet().addValidationData(dataValidation);
  //  }
}
