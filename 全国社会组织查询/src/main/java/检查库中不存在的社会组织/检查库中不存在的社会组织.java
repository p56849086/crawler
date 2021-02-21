package 检查库中不存在的社会组织;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;

public class 检查库中不存在的社会组织 {
    public static void main(String[] args) {
        MongoCollection<Document> sourse1 = new MongoClient("192.168.1.175", 27017).getDatabase("organization_data").getCollection("所有社会组织");
        MongoCollection<Document> sourse2 = new MongoClient("192.168.1.175", 27017).getDatabase("organization_data").getCollection("全国组织信息");
        MongoCollection<Document> sourse3 = new MongoClient("192.168.1.175", 27017).getDatabase("organization_data").getCollection("缺少的社会组织");
        Document query = new Document("_id", new Document("$gte",""));
        FindIterable<Document> documents = sourse1.find(query).sort(new Document("_id",1));
        boolean flag = false;
        int count = 1;
        for (Document document: documents){
            String rid = document.get("_id").toString().split("_")[1];
            Document query2 = new Document("统一社会信用代码", rid);
            FindIterable<Document> documents2 = sourse2.find(query2).sort(new Document("_id",1));
            for (Document document1: documents2){
                flag = true;
                break;
            }
            if (flag){
                flag = false;
            }else {
                sourse3.updateOne(new BasicDBObject("_id", document.get("_id")), new BasicDBObject("$set", document), (new UpdateOptions()).upsert(true));
            }
            System.out.println("第" + count++ + "条数据：" + document.get("_id"));
        }
    }
}
