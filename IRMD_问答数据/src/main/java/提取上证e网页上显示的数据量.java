import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import util.GetHtml_Post;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class 提取上证e网页上显示的数据量 {

    public static String [] yearArray={"2013","2014","2015","2016","2017","2018","2019"};
    public static void main(String[] args) {
        getData();
    }
    private static void getData() {
        int sum1=1;
        XSSFWorkbook workbook1 = new XSSFWorkbook();
        XSSFSheet sheet1 = workbook1.createSheet();
        String countUrl = "http://sns.sseinfo.com/getNewDataCount.do";
        try {
            InputStream input = new FileInputStream( "E:\\程存淦_处理程序2020\\施倩芸\\IRMD\\IRMD_问答数据\\上证e互动股票代码.xlsx" );
            XSSFWorkbook workbook = new XSSFWorkbook(input);
            XSSFSheet sheet = workbook.getSheetAt(0);
            int rowNum = sheet.getLastRowNum();
            for (int i = 1; i <=rowNum; i++) {
                XSSFRow row = sheet.getRow(i);
                row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                String stockCode = row.getCell(0).getStringCellValue();
                String uid = row.getCell(1).getStringCellValue();
                String stockName = row.getCell(2).getStringCellValue();
                System.err.println("正在获取第 -" + i + "- 家公司问答信息！共 -" + rowNum + "- 家公司！当前公司代码+uid：" + stockCode + "+" + uid);
                int sum=0;
                for (int j = 0; j < yearArray.length; j++) {
                    String year = yearArray[j];
                    String beginDate =year+"-01-01";
                    String endDate=year+"-12-31";
                    String itemCountStr = GetHtml_Post.getHtml_getNewDataCount(countUrl, uid, beginDate, endDate);
                    while (itemCountStr == null) {
                        itemCountStr = GetHtml_Post.getHtml_getNewDataCount(countUrl, uid, beginDate, endDate);
                    }
                    int itemCount = Integer.parseInt(itemCountStr);
                    sum=sum+itemCount;
                }
                System.out.println(sum);
                XSSFRow row1 = sheet1.createRow(sum1);
                row1.createCell(0).setCellValue(stockCode);
                row1.createCell(1).setCellValue(uid);
                row1.createCell(2).setCellValue(stockName);
                row1.createCell(3).setCellValue(sum);
                sum1++;
            }
            FileOutputStream outputStream = new FileOutputStream("E:\\程存淦_处理程序2020\\施倩芸\\IRMD\\IRMD_问答数据\\提取上证e网页上显示的数据量.xlsx");
            workbook1.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
