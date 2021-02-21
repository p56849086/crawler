package 导出数据;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
/*
 * 作者:何博豪
 * 功能:将数据写入到Excel中
 * 时间:2021.1.8
 * */
public class WriteExcel {
    Workbook workbook = new SXSSFWorkbook(10000);
    Sheet sheet = workbook.createSheet();
    // 第一种插入格式，行计数的方式
    // 插入一行的计数
    int rowNum1 = 1;
    // 第二种插入格式，行计数的方式
    // 每次插入某一行的一列
    int rowNum2 = 0;
    Row initRows;
    // 设置表格的第一栏标题
    public void initWriteExcel(String[] top) {
        Row row = sheet.createRow(0);
        int index = 0;
        for (String s: top){
            row.createCell(index++).setCellValue(s);
        }
    }
    // 每次插入一行，stringList:已经排序的一行数据
    public void insertRow(List<String> stringList){
        Row row1 = sheet.createRow(rowNum1++);
        int cellnum = 0;
        for (String s: stringList){
            row1.createCell(cellnum++).setCellValue(s);
        }
    }

    // 创建一行
    public void initRow(){
        initRows = sheet.createRow(rowNum2);
    }

    // 每次插入某一行的一列
    public void insertRow(String data, int cellNum){
        initRows.createCell(cellNum).setCellValue(data);
    }
    // 需要跨列跨列
    public void spanCell(int spanRow1, int spanRow2, int spanCell1, int spanCell2) {
        sheet.addMergedRegion(new CellRangeAddress(spanRow1, spanRow2, spanCell1, spanCell2));
    }

    // 若保存在idea默认位置中，path="", 若指定位置：path的末尾应该有//
    public void writeExcel(String path, String fileName)  {
        try {
            workbook.write(new FileOutputStream(path + fileName + ".xlsx"));
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
