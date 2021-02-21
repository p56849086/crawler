package 统计每年每个上市公司的问答数;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bson.Document;

import java.io.*;

public class 导出Count {
    public static void main(String[] args) throws IOException {
        MongoClient client=new MongoClient("192.168.1.175",27017);
        MongoCollection<Document> collection=client.getDatabase("IRMB_catchData_ccg").getCollection("公司互动情况统计");
        FindIterable<Document> cursor = collection.find();
        int x=0;
        int i=0;
        int j=0;
        int y=1;
        File excelFile1=new File("E:\\程存淦_处理程序2020\\施倩芸\\IRMD\\IRMD_问答数据\\公司互动情况统计.xlsx");//创建文件对象
        Workbook workbook1=new XSSFWorkbook();
        Sheet sheet1=workbook1.createSheet();
        int rowCount=sheet1.getPhysicalNumberOfRows();
        Row row0=sheet1.createRow(rowCount);
        Cell con=row0.createCell(0);
        con.setCellValue("_id");
        Cell con1=row0.createCell(1);
        con1.setCellValue("数据来源");
        Cell con2=row0.createCell(2);
        con2.setCellValue("公司简称");
        Cell con3=row0.createCell(3);
        con3.setCellValue("股票代码");
        Cell con4=row0.createCell(4);
        con4.setCellValue("年度");
        Cell con5=row0.createCell(5);
        con5.setCellValue("被提问数");
        Cell con6=row0.createCell(6);
        con6.setCellValue("回复数");
        rowCount++;
        for (Document document:cursor) {
            row0=sheet1.createRow(rowCount);
            String id = document.getString( "_id" );
            String 数据来源 = document.getString( "数据来源" );
            String 公司简称 = document.getString("公司简称");
            String 股票代码 = document.getString( "股票代码" );
            String 年度 = document.getString( "年度" );
            Long 被提问数 = document.getLong( "被提问数" );
            Long 回复数 = document.getLong( "回复数" );
            con=row0.createCell(0);
            con.setCellValue(id);
            con1=row0.createCell(1);
            con1.setCellValue(数据来源);
            con2=row0.createCell(2);
            con2.setCellValue(公司简称);
            con3=row0.createCell(3);
            con3.setCellValue(股票代码);
            con4=row0.createCell(4);
            con4.setCellValue(年度);
            con5=row0.createCell(5);
            con5.setCellValue(被提问数);
            con6=row0.createCell(6);
            con6.setCellValue(回复数);
            rowCount++;
            x++;
            System.out.println(x);
            if(x==43262) {
                File writename = new File( "ID记录.txt" ); // 相对路径，如果没有则要建立一个新的output。txt文件\
                if(!writename.canRead()) {//如果指定的文件存在的路径和文件允许应用程序读取
                    writename.createNewFile();
                }// 创建新文件
                BufferedWriter out = new BufferedWriter( new FileWriter( writename, true ) );
                x=0;
                workbook1.write(new FileOutputStream(excelFile1));
                System.out.println(excelFile1);
                i++;
                excelFile1 = new File("E:\\程存淦_处理程序2020\\施倩芸\\IRMD\\IRMD_问答数据\\公司互动情况统计.xlsx"); //创建文件对象
                workbook1 = new SXSSFWorkbook();
                sheet1= workbook1.createSheet();
                rowCount = sheet1.getPhysicalNumberOfRows();
                row0=sheet1.createRow(rowCount);
                con=row0.createCell(0);
                con.setCellValue("_id");
                con1=row0.createCell(1);
                con1.setCellValue("数据来源");
                con2=row0.createCell(2);
                con2.setCellValue("公司简称");
                con3=row0.createCell(3);
                con3.setCellValue("股票代码");
                con4=row0.createCell(4);
                con4.setCellValue("年度");
                con5=row0.createCell(5);
                con5.setCellValue("被提问数");
                con6=row0.createCell(6);
                con6.setCellValue("回复数");
                rowCount++;
                out.flush();//刷新该流中的缓冲。将缓冲数据写到目的文件中去
                out.close();//关闭此流，再关闭前刷新它。
                continue;
            }
        }
    }
}
