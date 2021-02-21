package 抓取巨潮资讯网关键字信息;

import Request.entity.EntityJC;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;

import java.util.List;

public class InsertMongodb {
    static MongoCollection<Document> sourse = new MongoClient("192.168.1.175", 27017).getDatabase("IDRD_JC").getCollection("巨潮资讯网_独董离职_独董辞职_2019.1_2019.12");
    public static void Insert(List<EntityJC> jcList){
        for (EntityJC entityJC: jcList){
            Document document = new Document();
            document.append("_id", entityJC.getId() + "_" + entityJC.getAnnouncementTitle() + "_" + entityJC.getDate());
            document.append("报告类型", entityJC.getAdjunctType());
            document.append("下载Id", entityJC.getId() + "_" + entityJC.getDate());
            sourse.updateOne(new BasicDBObject("_id", document.get("_id").toString()), new BasicDBObject("$set", document), (new UpdateOptions()).upsert(true));
        }
    }
}
