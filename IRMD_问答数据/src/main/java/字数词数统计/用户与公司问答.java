package 字数词数统计;

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
public class 用户与公司问答 {
    public static void main(String[] args) throws IOException {
        MongoClient client=new MongoClient("192.168.1.175",27017);
        MongoCollection<Document> collection=client.getDatabase("IRMB_catchData_ccg").getCollection("用户与公司问答");
        FindIterable<Document> cursor = collection.find();
        int x=0;
        int i=0;
        int j=0;
        int y=1;
        File excelFile1=new File("D:\\geren_ccl\\用户与公司问答\\用户与公司问答"+i+".xlsx");//创建文件对象
        Workbook workbook1=new XSSFWorkbook();
        Sheet sheet1=workbook1.createSheet();
        int rowCount=sheet1.getPhysicalNumberOfRows();
        Row row0=sheet1.createRow(rowCount);
        Cell con=row0.createCell(0);
        con.setCellValue("股票代码");
        Cell con1=row0.createCell(1);
        con1.setCellValue("公司简称");
        Cell con2=row0.createCell(2);
        con2.setCellValue("浏览用户/注册用户");
        Cell con3=row0.createCell(3);
        con3.setCellValue("用户名");
        Cell con4=row0.createCell(4);
        con4.setCellValue("提问时间");
        Cell con5=row0.createCell(5);
        con5.setCellValue("提问内容");
        Cell con6=row0.createCell(6);
        con6.setCellValue("上市公司是否回复");
        Cell con7=row0.createCell(7);
        con7.setCellValue("回复时间");
        Cell con8=row0.createCell(8);
        con8.setCellValue("回复内容");
        Cell con9=row0.createCell(9);
        con9.setCellValue("字数");
        Cell con10=row0.createCell(10);
        con10.setCellValue("词数");
        Cell con11=row0.createCell(11);
        con11.setCellValue("数据来源");
        rowCount++;
        for (Document document:cursor) {
            row0=sheet1.createRow(rowCount);
            String 股票代码  = document.getString( "股票代码" );
            String 公司简称 = document.getString("公司简称");
            String 浏览用户 = document.getString( "浏览用户/注册用户" );
            String 用户名 = document.getString( "用户名" );
            String 提问时间 = document.getString( "提问时间" );
            String 提问内容 = document.getString("提问内容");
            String 上市公司是否回复 = document.getString( "上市公司是否回复" );
            String 回复时间 = document.getString( "回复时间" );
            String 回复内容 = document.getString( "回复内容" );
            int 字数 = document.getInteger("字数");
            int 词数 = document.getInteger( "词数" );
            String 数据来源 = document.getString( "数据来源" );
            con=row0.createCell(0);
            con.setCellValue(股票代码);
            con1=row0.createCell(1);
            con1.setCellValue(公司简称);
            con2=row0.createCell(2);
            con2.setCellValue(浏览用户);
            con3=row0.createCell(3);
            con3.setCellValue(用户名);
            con4=row0.createCell(4);
            con4.setCellValue(提问时间);
            con5=row0.createCell(5);
            con5.setCellValue(提问内容);
            con6=row0.createCell(6);
            con6.setCellValue(上市公司是否回复);
            con7=row0.createCell(7);
            con7.setCellValue(回复时间);
            con8=row0.createCell(8);
            con8.setCellValue(回复内容);
            con9=row0.createCell(9);
            con9.setCellValue(字数);
            con10=row0.createCell(10);
            con10.setCellValue(词数);
            con11=row0.createCell(11);
            con11.setCellValue(数据来源);
            rowCount++;
            x++;
            System.out.println(x);
            if(x==500000) {
                File writename = new File( "ID记录.txt" ); // 相对路径，如果没有则要建立一个新的output。txt文件\
                if(!writename.canRead()) {//如果指定的文件存在的路径和文件允许应用程序读取
                    writename.createNewFile();
                }// 创建新文件
                BufferedWriter out = new BufferedWriter( new FileWriter( writename, true ) );
                x=0;
                workbook1.write(new FileOutputStream(excelFile1));
                System.out.println(excelFile1);
                i++;
                excelFile1 = new File("D:\\geren_ccl\\用户与公司问答\\用户与公司问答"+i+".xlsx"); //创建文件对象
                workbook1 = new SXSSFWorkbook();
                sheet1= workbook1.createSheet();
                rowCount = sheet1.getPhysicalNumberOfRows();
                row0=sheet1.createRow(rowCount);
                con=row0.createCell(0);
                con.setCellValue("股票代码");
                con1=row0.createCell(1);
                con1.setCellValue("公司简称");
                con2=row0.createCell(2);
                con2.setCellValue("浏览用户/注册用户");
                con3=row0.createCell(3);
                con3.setCellValue("用户名");
                con4=row0.createCell(4);
                con4.setCellValue("提问时间");
                con5=row0.createCell(5);
                con5.setCellValue("提问内容");
                con6=row0.createCell(6);
                con6.setCellValue("上市公司是否回复");
                con7=row0.createCell(7);
                con7.setCellValue("回复时间");
                con8=row0.createCell(8);
                con8.setCellValue("回复内容");
                con9=row0.createCell(9);
                con9.setCellValue("字数");
                con10=row0.createCell(10);
                con10.setCellValue("词数");
                con11=row0.createCell(11);
                con11.setCellValue("数据来源");
                rowCount++;
                out.flush();//刷新该流中的缓冲。将缓冲数据写到目的文件中去
                out.close();//关闭此流，再关闭前刷新它。
                continue;
            }
            if(i==(5499662/500000)&&x==((5499662%500000))){
                x=0;
                workbook1.write(new FileOutputStream(excelFile1));
                System.out.println(excelFile1);
            }
        }
    }
}
