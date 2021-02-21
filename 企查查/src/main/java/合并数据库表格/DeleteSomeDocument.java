package 合并数据库表格;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.bson.conversions.Bson;

public class DeleteSomeDocument {
    static MongoCollection<Document> sourse1 = new MongoClient("192.168.1.175", 27017).getDatabase("企查查_汇总").getCollection("企查查_无此公司");
    static MongoCollection<Document> sourse2 = new MongoClient("192.168.1.175", 27017).getDatabase("企查查_未完成").getCollection("企查查_股东信息");

    public static void main(String[] args) {
        FindIterable<Document> document1s = sourse1.find();
        for (Document document1: document1s){
            Bson filter = Filters.eq("_id", document1.get("_id"));
            DeleteResult deleteResult = sourse2.deleteOne(filter);
            if (deleteResult.getDeletedCount()>0){
                System.out.println(1);
            }
            System.out.println(document1.get("_id"));
        }
    }
}
