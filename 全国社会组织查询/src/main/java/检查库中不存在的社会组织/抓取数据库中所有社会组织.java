package 检查库中不存在的社会组织;

import Entity.GetIp;
import Entity.Yichang;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class 抓取数据库中所有社会组织 {
    public static void main(String[] args) {
        MongoCollection<Document> sourse = new MongoClient("192.168.1.175", 27017).getDatabase("organization_data").getCollection("所有社会组织");
        Document query = new Document("_id", new Document("$gte","1016259_51360881MJD1364964"));
        FindIterable<Document> documents = sourse.find(query).sort(new Document("_id",1));
        GetIp getIp = new GetIp();
        int count = 1;
        for (Document document: documents){
            String[] strings = document.get("_id").toString().split("_");
            CompletePage2 completePage2 = new CompletePage2(strings[0], strings[1], getIp);
            List<String> finallyData1;
            List<Yichang> yichangList = new ArrayList<Yichang>();
            List<Yichang> weifaList = new ArrayList<Yichang>();
            while (true) {
                try {
                    Thread.sleep(2000);
                    // 请求第一个页面
                    String html1 = completePage2.requestTable1();
                    finallyData1 = completePage2.dealPage1(html1);
                    break;
                }catch (Exception e){
                    System.out.println("页面解析错误，重试");
                    continue;
                }
            }
            while (true) {
                try {
                    //请求第二个页面
                    String html2 = completePage2.requestTable2();
                    yichangList = completePage2.dealPage2(html2);
                    break;
                }catch (Exception e){
                    System.out.println("页面解析错误，重试");
                    continue;
                }
            }

            while (true) {
                try {
                    //请求第三个页面
                    String html3 = completePage2.requestTable3();
                    weifaList = completePage2.dealPage3(html3);
                    break;
                }catch (Exception e){
                    System.out.println("页面解析错误，重试");
                    continue;
                }
            }
            // 将获得的数据插入到数据库中
            InsertMogdb insertMogdb = new InsertMogdb(finallyData1, yichangList, weifaList, completePage2.getUnifiedCode());
            System.out.println("共完成数据" + count++ + ":" + document.get("_id"));
        }
    }
}
