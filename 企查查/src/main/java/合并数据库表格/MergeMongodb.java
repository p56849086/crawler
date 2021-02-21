package 合并数据库表格;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;

public class MergeMongodb {
    //static MongoCollection<Document> sourse1 = new MongoClient("192.168.1.175", 27017).getDatabase("企查查_王佳艺").getCollection("企查查_无此公司");
    static MongoCollection<Document> sourse2 = new MongoClient("192.168.1.175", 27017).getDatabase("企查查_章萍").getCollection("企查查_股东信息");
    static MongoCollection<Document> sourse3 = new MongoClient("192.168.1.175", 27017).getDatabase("企查查_汇总").getCollection("企查查_股东信息");

    public static void main(String[] args) {
        Document query = new Document("_id", new Document("$gte", ""));
        //FindIterable<Document> documents1 = sourse1.find(query).sort(new Document("_id", 1));
        FindIterable<Document> documents2 = sourse2.find(query).sort(new Document("_id", 1));

        /*for (Document document1: documents1){
            sourse3.updateOne(new BasicDBObject("_id", document1.get("_id")), new BasicDBObject("$set", document1), (new UpdateOptions()).upsert(true));
            System.out.println(document1.get("_id"));
        }*/
        for (Document document1: documents2){
            sourse3.updateOne(new BasicDBObject("_id", document1.get("_id")), new BasicDBObject("$set", document1), (new UpdateOptions()).upsert(true));
            System.out.println(document1.get("_id"));
        }

    }
}
