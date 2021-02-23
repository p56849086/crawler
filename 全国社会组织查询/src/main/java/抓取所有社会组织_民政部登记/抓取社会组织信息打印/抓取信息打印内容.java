package 抓取所有社会组织_民政部登记.抓取社会组织信息打印;

import Entity.GetIp;
import Entity.信息公示实体类.ListAll;
import base.requests.RequestXinXiDaYinGongShi;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class 抓取信息打印内容 {
    public static void main(String[] args){
        MongoCollection<Document> sourse = new MongoClient("192.168.1.175", 27017).getDatabase("organization_data").getCollection("民政部登记_所有社会组织");
        Document qurey = new Document("_id", new Document("$gte", ""));
        FindIterable<Document> documents = sourse.find(qurey).sort(new Document("_id", 1));
        RequestXinXiDaYinGongShi requestXinXiDaYinGongShi = new RequestXinXiDaYinGongShi(new GetIp(), "get", "http://59.252.100.194/search/");
        int count=1;
        for (Document document: documents){
            String url = document.getString("信息打印URL");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ListAll listAll = (ListAll) requestXinXiDaYinGongShi.dealPage(requestXinXiDaYinGongShi.requestGetPage(url));
            listAll.getOrganization().set统一社会信用代码(document.getString("统一社会信用代码"));
            listAll.getOrganization().setOrgId(document.getString("OrgId"));
            InsertMongodb.InsertMongodb(listAll);
            System.out.println("第" + count++ + "条数据，正在进行_id:" + document.get("_id"));
        }
    }

}
