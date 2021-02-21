import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;

import java.io.File;
import java.util.HashMap;

public class 修正cipin {
    static String[] Databases = {"aitexts_担保事项", "aitexts_政策影响", "aitexts_澄清公告", "aitexts_股份增减持", "aitexts_股权激励",
            "aitexts_资产重组", "aitexts_资金投向", "aitexts_违规违纪", "aitexts_风险提示", "aitexts_高管变动", };
    static String[] Collection = {"cipin"};

    public static void main(String[] args) throws InterruptedException {
        // 读取Excel文件，获取需要的键值对，通过键值对匹配公司
        File file = new File("上市公司年度信息.xlsx");
        HashMap<String, String> hashMap = ReadExcel.readerExcel(file);

        MongoClient client = new MongoClient("192.168.1.171", 30000);
        for (String Database : Databases) {
            for (String Data : Collection){
                MongoCollection<Document> collection = client.getDatabase(Database).getCollection(Data);
                FindIterable<Document> documents = collection.find();
                for (Document document : documents) {
                    System.out.println("正在进行的数据库：" + Database + "-" + Data + "; 正在处理的文档_id：" + document.get("_id"));
                    if (!"".equals(document.getString("Stockcode")))
                        continue;
                    String year = document.getString("Year");
                    String conpany = document.getString("Stockname");
                    String key = year + "_" + conpany;
                    String code = hashMap.get(key);
                    if (code != null){
                        document.append("Stockcode", code);
                        collection.updateOne(new BasicDBObject("_id", document.get("_id")), new BasicDBObject("$set", document), (new UpdateOptions()).upsert(true));
                    }else {
                        collection.deleteOne(document);
                    }
                }
            }

        }
    }
}
