package 抓取所有社会组织_民政部登记.抓取社会组织信息打印网址;

import Entity.GetIp;
import base.ReqeustBase;
import base.requests.RequestXinXiDaYin;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import org.apache.poi.ss.formula.functions.T;
import org.bson.Document;

import javax.xml.crypto.Data;

public class 信息打印 {
    public static void main(String[] args){
        // 获取数据库中所有社会组织信息
        MongoCollection<Document> sourse = new MongoClient("192.168.1.175", 27017).getDatabase("organization_data").getCollection("民政部登记_所有社会组织");
        Document qurey = new Document("_id", new Document("$gte", ""));
        FindIterable<Document> documents = sourse.find(qurey).sort(new Document("_id", 1));
        RequestXinXiDaYin requestXinXiDaYin = new RequestXinXiDaYin(new GetIp(), "post", "http://59.252.100.194/search/vieworg.html");
        int count = 1;
        for (Document document: documents){
            String orgid = document.getString("OrgId");
            String Data = "orgId=" + orgid;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String url = (String) requestXinXiDaYin.dealPage(requestXinXiDaYin.requestGetPage(Data));
            document.append("信息打印URL", url);
            sourse.updateOne(new BasicDBObject("_id", document.get("_id").toString()), new BasicDBObject("$set", document), (new UpdateOptions()).upsert(true));
            System.out.println("第" + count + "条数据，正在进行的_id：" + document.get("_id"));
        }

    }
}
