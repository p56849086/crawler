package 合并数据库表格;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bson.Document;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ExcleToMongdb {
    static MongoCollection<Document> sourse = new MongoClient("192.168.1.175", 27017).getDatabase("所有省市高新技术企业认定名单").getCollection("企查查_企查查Id");

    public static void main(String[] args) throws IOException {
        // 读取文件
        String path = "C:\\Users\\jinghe\\Desktop\\企查查公司id.xlsx";
        InputStream inputStream = new FileInputStream(path);
        Workbook workbook = new XSSFWorkbook(inputStream);
        // 从汇总中获取已经完成的公司
        FindIterable<Document> documents;

        //获取第一张表
        Sheet readsheet = workbook.getSheetAt(0);
        int i = 0;
        int num = 1;
        for (Row row : readsheet) {
            if (i == 0) {
                i++;
                continue;
            }
            Cell cell1 = row.getCell(0);//获取一行中的第一列单元格
            Cell cell2 = row.getCell(1);//获取一行中的第一列单元格
            String companyName = cell1.getStringCellValue();
            String qcc_id = cell2.getStringCellValue();
            Document document1 = new Document();
            document1.append("_id", companyName);
            documents = sourse.find(document1);
            if (documents.first()!= null &&!documents.first().isEmpty()){
                document1.append("qcc_id", qcc_id);
                sourse.updateOne(new BasicDBObject("_id", document1.get("_id")), new BasicDBObject("$set", document1), (new UpdateOptions()).upsert(true));
                System.out.println(num++ + ":" + companyName);
            }
        }
    }
}
