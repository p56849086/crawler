import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

public class ReadExcel {
    public static HashMap<String, String> readerExcel(File file) {
        FileInputStream inputStream = null;
        Workbook workbook = null;
        // 获取Excel文件后缀
        String houzhui = file.getName().substring(file.getName().lastIndexOf(".")+1);
        if ("xlsx".equals(houzhui)){
            try {
                inputStream = new FileInputStream(file);
                workbook = new XSSFWorkbook(inputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            try {
                inputStream = new FileInputStream(file);
                workbook = new HSSFWorkbook(inputStream);
            } catch (Exception e) {
                System.out.println("HSSFWorkbook读取出错转换：XSSFWorkbook");
                try {
                    inputStream = new FileInputStream(file);
                    workbook = new XSSFWorkbook(inputStream);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
        Sheet readsheet = workbook.getSheetAt(0);
        HashMap<String, String> hashMap = new HashMap<>();
        int count = 0;
        for (Row row : readsheet) {
            if (count++ < 2)
                continue;
            Cell cell1 = row.getCell(0);//获取一行中的第一列单元格
            Cell cell2 = row.getCell(1);//获取一行中的第二列单元格
            Cell cell3 = row.getCell(2);//获取一行中的第三列单元格

            String string1 = cell1.getStringCellValue();
            String string2 = cell2.getStringCellValue();
            String string3 = cell3.getStringCellValue();
            String key = string2 + "_" + string3;
            hashMap.put(key, string1);

        }
        return hashMap;
    }
}
