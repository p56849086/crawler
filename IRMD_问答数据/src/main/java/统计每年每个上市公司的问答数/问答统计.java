package 统计每年每个上市公司的问答数;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
public class 问答统计 {
    public static String[] chineseNames={"_id","From","name","Scode","data"};
    public static void main(String[] args)  {
        MongoClient mongoClient = new MongoClient("192.168.1.175", 27017);
        MongoCollection<Document> mongoCollection = mongoClient.getDatabase("IRMB_catchData_ccg").getCollection("用户与公司问答总数据量");
        MongoCollection<Document> mongoCollection1= mongoClient.getDatabase("IRMB_catchData_ccg").getCollection("ccg");
        FindIterable<Document> findIterable = mongoCollection.find(); //iterator——迭代
        MongoCursor<Document> mongoCursor = findIterable.iterator();  //游标
        while (mongoCursor.hasNext()) {
            Document d = mongoCursor.next(); //遍历每一条数据
            String Scode=d.getString("股票代码");
            String Name=d.getString("公司简称");
            String From=d.getString("From");
            String Year=d.getString("提问时间").substring(0,4);
            System.out.println(Scode);
            System.out.println(Name);
            System.out.println(Year);
            String id=Scode+"_"+From+"_"+Year;
            List list=new ArrayList();
            list.add(id);
            list.add(From);
            list.add(Name);
            list.add(Scode);
            list.add(Year);
            boolean exit=SaveDataToMongoDB.JudgeExist(id,mongoCollection1);
            if(!exit) {
                System.out.println("插入数据============================"+id);
                SaveDataToMongoDB.saveData(list, chineseNames, mongoCollection1);
            }else {
                System.err.println("数据已存在==========="+id);
            }
            //mongoCollection1.updateOne(new Document("Scode", Scode).append("From",From).append("Name", Name), new Document("$set", new Document("Date", Date)), new UpdateOptions().upsert(true));
        }

    }
}
