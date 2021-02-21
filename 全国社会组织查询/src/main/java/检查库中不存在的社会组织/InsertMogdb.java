package 检查库中不存在的社会组织;

import Entity.Yichang;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class InsertMogdb {
    static MongoCollection<Document> sourse = new MongoClient("192.168.1.175", 27017).getDatabase("organization_data").getCollection("全国组织信息_改1");
    String[] s = {"_id", "登记管理机关", "业务主管单位", "法定代表人", "成立登记日期", "注册资金",
            "登记状态", "社会组织类型", "证书有效期", "住所", "业务范围"};
    InsertMogdb(List<String> finallyData1, List<Yichang> yichangList, List<Yichang> weifaList, String unifiedCode){
        Document documentInsert = new Document();

        for (int i = 0; i < finallyData1.size(); i++){
            if (i == 0)
                documentInsert.append(s[i], finallyData1.get(i) + "_" + unifiedCode);
            else
                documentInsert.append(s[i], finallyData1.get(i));
        }

        if (!yichangList.isEmpty()){
            for (Yichang yichang : yichangList){
                Document document11 = new Document();
                document11.append("序号", yichang.getId());
                document11.append("列入时间", yichang.getLierushijian());
                document11.append("列入事由", yichang.getLierushiyou());
                document11.append("移出时间", yichang.getYichushijian());
                document11.append("移出事由", yichang.getYichushiyou());
                documentInsert.append("异常活动名录_"+yichang.getId(), document11);
            }
        }else {
            documentInsert.append("异常活动名录_1", "");
        }

        if (!weifaList.isEmpty()){
            for (Yichang weifa : weifaList){
                Document document11 = new Document();
                document11.append("序号", weifa.getId());
                document11.append("列入时间", weifa.getLierushijian());
                document11.append("列入事由", weifa.getLierushiyou());
                document11.append("移出时间", weifa.getYichushijian());
                document11.append("移出事由", weifa.getYichushiyou());
                documentInsert.append("严重违法失信名单_"+weifa.getId(), document11);
            }
        }else {
            documentInsert.append("严重违法失信名单_1", "");
        }
        
        // 加入"统一社会信用代码"
        documentInsert.append("统一社会信用代码", unifiedCode);
        sourse.updateOne(new BasicDBObject("_id", documentInsert.get("_id").toString()), new BasicDBObject("$set", documentInsert), (new UpdateOptions()).upsert(true));
    }

    public static boolean IsExistence(String s){
        FindIterable<Document> documents = sourse.find(new Document("_id", s + "_1"));
        for (Document d:documents){
            return false;
        }
        return true;
    }
}
