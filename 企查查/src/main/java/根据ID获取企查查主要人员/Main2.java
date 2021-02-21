package 根据ID获取企查查主要人员;

import base.GetIp;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import 根据ID获取企查查主要人员.entity.Company;
import 根据ID获取企查查主要人员.request.RequestMember;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main2 {
    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
        MongoClientOptions options = builder.maxConnectionIdleTime(100000).build();
        MongoCollection<Document> source = new MongoClient("192.168.1.175:27017", options).getDatabase("所有省市高新技术企业认定名单").getCollection("企查查_企查查Id");
        MongoCollection<Document> source2 = new MongoClient("192.168.1.175", 27017).getDatabase("所有省市高新技术企业认定名单").getCollection("企查查html");
        // 按顺序查找
        Document query = new Document("_id", new Document("$gte", "山东拓维数控设备有限公司"));
        FindIterable<Document> documents = source.find(query).sort(new Document("_id",1));
        documents.noCursorTimeout(true);
        GetIp getIp = new GetIp();
        RequestMember requestMember = new RequestMember(getIp);
        int i = 1;
        for (Document document1: documents) {
            Thread.sleep((long) (Math.random()*2*1000+1000*13));
            String html = null;
            Company company = null;
            while (true) {
                try {
                    html = requestMember.requestPage(document1.get("qcc_id").toString());
                    company = requestMember.dealPage(html);
                } catch (Exception e) {
                    System.out.println("捕获异常，开始重试");
                    continue;
                }
                break;
            }
            document1.append("股东数量", company.get股东信息());
            document1.append("主要人员数量", company.get主要人员());
            document1.append("对外投资数量", company.get对外投资());
            document1.append("股东信息html", company.getPartnern());
            document1.append("主要人员html", company.getMainmember());
            document1.append("对外投资html", company.getTouzilist());
            source2.updateOne(new BasicDBObject("_id", document1.get("_id")), new BasicDBObject("$set", document1), (new UpdateOptions()).upsert(true));
            System.out.println("第" + i++ + ":" + document1.get("_id"));
        }
    }
}
