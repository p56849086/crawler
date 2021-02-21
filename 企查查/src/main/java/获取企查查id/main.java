package 获取企查查id;

import base.GetIp;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;
import 获取企查查id.request.CqqSearch;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Random;

public class main {
    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        MongoCollection<Document> source = new MongoClient("192.168.1.175", 27017).getDatabase("所有省市高新技术企业认定名单").getCollection("企查查_企查查Id");
        GetIp getIp = new GetIp();
        CqqSearch cqqSearch = new CqqSearch(getIp);

        // 按顺序查询，可以从断掉的地方重新开始
        Document query = new Document("_id", new Document("$gte", "北京临空国际技术研究院有限公司"));
        // 获取公司名字
        int num = 1;
        FindIterable<Document> documents = source.find(query).batchSize(10000).sort(new Document("_id",1));
        documents.noCursorTimeout(true);
        for (Document document1: documents){
            if (document1.size()==3)
                continue;
            String name = document1.get("_id").toString();
            String html = null;
            String id = null;
            String companyIs = "";
            while (true) {
                try {
                    // 请求
                    Thread.sleep((long) (Math.random()*2*1000+1000));
                    html = cqqSearch.requestPage(name);
                    id = cqqSearch.dealPage(html);
                }catch (Exception e){
                    if ("公司不存在".equals(e.getMessage())){
                        companyIs = "公司不存在";
                        document1.append("企查查搜索后的名称", "公司不存在");
                        document1.append("qcc_id", "公司不存在");
                        source.updateOne(new BasicDBObject("_id", document1.get("_id")), new BasicDBObject("$set", document1), (new UpdateOptions()).upsert(true));
                        System.out.println("第" + num++ + "：" + name + ", :ID:" + document1.get("qcc_id"));
                        break;
                    }
                    System.out.println("捕获错误，开始重试");
                    continue;
                }
                break;
            }
            if ("公司不存在".equals(companyIs)){
                continue;
            }
            String[] str = id.split("_");
            // 处理页面
            document1.append("企查查搜索后的名称", str[0]);
            document1.append("qcc_id", str[1]);
            source.updateOne(new BasicDBObject("_id", document1.get("_id")), new BasicDBObject("$set", document1), (new UpdateOptions()).upsert(true));
            System.out.println("第" + num++ + "：" + name + ", :ID:" + document1.get("qcc_id"));
        }


    }
}
