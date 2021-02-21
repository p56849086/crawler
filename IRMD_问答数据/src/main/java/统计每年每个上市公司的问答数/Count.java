package 统计每年每个上市公司的问答数;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bson.Document;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Count {
    public static String[] chineseNames={"_id","数据来源","公司简称","股票代码","年度","被提问数","回复数"};
    public static void main(String[] args) {
        MongoClient mongoClient = new MongoClient("192.168.1.175", 27017);
        MongoCollection<Document> mongoCollection = mongoClient.getDatabase("IRMB_catchData_ccg").getCollection("用户与公司问答总数据量");
        MongoCollection<Document> mongoCollection1 = mongoClient.getDatabase("IRMB_catchData_ccg").getCollection("公司互动情况统计");
        //读取表格中的数据
        Workbook workbook = null;
        try {
            workbook = new XSSFWorkbook(new FileInputStream("E:\\程存淦_处理程序2020\\施倩芸\\IRMD\\IRMD_问答数据\\统计数据.xlsx"));
            Sheet sheetAt = workbook.getSheetAt(0);
            for (int i = 1; i <= sheetAt.getLastRowNum(); i++) {
                System.out.println("已获取一条信息======================"+i);
                String From = sheetAt.getRow(i).getCell(1).toString();
                String name = sheetAt.getRow(i).getCell(2).toString();
                String Scode = sheetAt.getRow(i).getCell(3).toString();
                String data = sheetAt.getRow(i).getCell(4).toString();
                //System.out.println(data);
                BasicDBList condList = new BasicDBList();//存放查询条件的集合
                BasicDBObject searchQuery = new BasicDBObject();//条件查询的对象
                searchQuery.append("股票代码", Scode);
                condList.add(searchQuery);
                searchQuery.append("From", From);
                condList.add(searchQuery);
                searchQuery.append("提问时间", new BasicDBObject("$regex", data));
                condList.add(searchQuery);
                BasicDBObject condition= new BasicDBObject();//最后在将查询结果放到一个查询对象中去
                condition.put("$and", condList);//多条件查询使用and
                long Qnumb = mongoCollection.count(condition);

                searchQuery.append("上市公司是否回复", "已回复");
                condList.add(searchQuery);
                condition= new BasicDBObject();//最后在将查询结果放到一个查询对象中去
                condition.put("$and", condList);//多条件查询使用and
                long Respnumb = mongoCollection.count(condition);
                System.out.println(From);//数据来源
                System.out.println(name);//公司简称
                System.out.println(Scode);//股票代码
                System.out.println(data);//年度
                System.out.println(Qnumb);//被提问数
                System.out.println(Respnumb);//回复数
                String id=Scode+"_"+From+"_"+data;
                List list=new ArrayList();
                list.add(id);
                list.add(From);
                list.add(name);
                list.add(Scode);
                list.add(data);
                list.add(Qnumb);
                list.add(Respnumb);
                SaveDataToMongoDB.saveData(list, chineseNames, mongoCollection1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
