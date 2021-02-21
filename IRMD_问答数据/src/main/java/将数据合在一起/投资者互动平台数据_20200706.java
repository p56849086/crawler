package 将数据合在一起;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;

public class 投资者互动平台数据_20200706 {
    public static void main(String[] args)  {
        MongoClient mongoClient = new MongoClient("192.168.1.175", 27017);
        MongoCollection<Document> mongoCollection = mongoClient.getDatabase("IRMB_catchData_ccg").getCollection("投资者互动平台数据_20200706");
        MongoCollection<Document> mongoCollection1= mongoClient.getDatabase("IRMB_catchData_ccg").getCollection("用户与公司问答总数据量");
        FindIterable<Document> findIterable = mongoCollection.find().batchSize(1000); //iterator——迭代
        MongoCursor<Document> mongoCursor = findIterable.iterator();  //游标
        int sum = 0;
        while (mongoCursor.hasNext()) {
            sum++;
            String From="投资者互动平台数据";
            Document d = mongoCursor.next(); //遍历每一条数据
            mongoCollection1.insertOne(d.append("From",From));
            System.out.println("已插入数据库-------"+sum+"条数据");
        }
    }
}
