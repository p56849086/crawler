package 字数词数统计;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class zishuCishu {
    public static void main(String[] args) {
        MongoClient mongoClient = new MongoClient("192.168.1.175", 27017);
        MongoCollection<Document> sourse = mongoClient.getDatabase("IRMB_catchData_ccg").getCollection("深交所问答信息数据_20200706");
        MongoCollection<Document> target = mongoClient.getDatabase("IRMB_catchData_ccg").getCollection("用户与公司问答");
        FindIterable<Document> documents = sourse.find();
        documents.noCursorTimeout(true);
        int sum = 0;
        for (Document document : documents) {
            String reply=document.getString("回复内容");
            int WordCount=0;
            int zishu=0;
            if(reply==null||reply.equals("")){
                WordCount=0;
                zishu=0;
            }else{
                WordCount=reply.length();
                String zs =reply.replaceAll("\\p{P}","");
                zishu=zs.length();
            }
            sum++;
            target.insertOne(document.append("字数",zishu).append("词数",WordCount).append("数据来源","证交所"));
            System.out.println("已插入数据库-------"+sum+"条数据");
        }
    }
}
