import Entity.GetIp;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 地方登记的社会组织 
public class 抓取所有社会组织_地方登记 {
    public static void main(String[] args) {
        GetIp getIp1 = new GetIp();
        for (int page=0; page < 17604; page++) {
            List<String> linkString = null;
            CompletePage1 completePage1 = new CompletePage1(String.valueOf(page), getIp1);
            // 请求第一个页面
            while (true) {
                try {
                    String html = completePage1.requestPage();
                    linkString = completePage1.dealPage(html);
                    break;
                } catch (Exception e) {
                    System.out.println("第一个页面解析出错，重试");
                    continue;
                }
            }
            insertCompanyMongodb.insertMongodb(linkString);
            System.out.println("第 " + page + "页完成第："  + page  + "----");
        }
    }
}

class insertCompanyMongodb{
    static MongoCollection<Document> sourse1 = new MongoClient("192.168.1.175", 27017).getDatabase("organization_data").getCollection("所有社会组织");
    public static void insertMongodb(List<String> linkString){
        for (String s: linkString){
            String[] split = s.split("##");
            Document document = new Document();
            document.append("_id", split[0]);
            document.append("name", split[1]);
            sourse1.updateOne(new BasicDBObject("_id", document.get("_id").toString()), new BasicDBObject("$set", document), (new UpdateOptions()).upsert(true));
        }
    }

}
