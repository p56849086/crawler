package 不需要的代码;

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
    static MongoCollection<Document> sourse = new MongoClient("192.168.1.175", 27017).getDatabase("organization_data").getCollection("全国组织信息");
    String[] s = {"_id", "登记管理机关", "业务主管单位", "法定代表人", "成立登记日期", "注册资金",
            "登记状态", "社会组织类型", "证书有效期", "住所", "业务范围", "异常活动名录_序号",
            "异常活动名录_列入时间", "异常活动名录_列入事由", "异常活动名录_移出时间", "异常活动名录_移出事由", "严重违法失信名单_序号",
            "严重违法失信名单_列入时间", "严重违法失信名单_列入事由", "严重违法失信名单_移出时间", "严重违法失信名单_移出事由"};
    InsertMogdb(List<String> finallyData1,  List<Yichang> yichangList, List<Yichang> weifaList, String unifiedCode){
        List<Document> documentInsert = new ArrayList<Document>();

        Document document = new Document();
        for (int i = 0; i < finallyData1.size(); i++){
            if (i == 0)
                document.append(s[i], finallyData1.get(i) + "_1");
            else
                document.append(s[i], finallyData1.get(i));
        }
        documentInsert.add(document);

        if (!yichangList.isEmpty()){
            // 如果有数据，首先循环外层表格
            for (int i = 0; i < yichangList.size(); i++){
                // 是一个实体类，直接插入
                if (i == 0) {
                    documentInsert.get(0).append(s[11], yichangList.get(i).getId());
                    documentInsert.get(0).append(s[12], yichangList.get(i).getLierushijian());
                    documentInsert.get(0).append(s[13], yichangList.get(i).getLierushiyou());
                    documentInsert.get(0).append(s[14], yichangList.get(i).getYichushijian());
                    documentInsert.get(0).append(s[15], yichangList.get(i).getYichushiyou());
                }
                else{
                    Document document1 = new Document(document);
                    document1.put("_id",document.get("_id").toString().replace("_1","_" + (i+1)));
                    document1.append(s[11], yichangList.get(i).getId());
                    document1.append(s[12], yichangList.get(i).getLierushijian());
                    document1.append(s[13], yichangList.get(i).getLierushiyou());
                    document1.append(s[14], yichangList.get(i).getYichushijian());
                    document1.append(s[15], yichangList.get(i).getYichushiyou());
                    documentInsert.add(document1);
                }
            }
        }else {
            for (int i = 11,j=0; j < 5; i++,j++){
                document.append(s[i], "");
                documentInsert.get(0).append(s[i], "");
            }
        }

        if (!weifaList.isEmpty()){
            for (int i = 0; i < weifaList.size(); i++){
                // 是一个实体类，直接插入
                if (i == 0) {
                    documentInsert.get(0).append(s[16], weifaList.get(i).getId());
                    documentInsert.get(0).append(s[17], weifaList.get(i).getLierushijian());
                    documentInsert.get(0).append(s[18], weifaList.get(i).getLierushiyou());
                    documentInsert.get(0).append(s[19], weifaList.get(i).getYichushijian());
                    documentInsert.get(0).append(s[20], weifaList.get(i).getYichushiyou());
                }
                else{
                    if (documentInsert.size() < i + 1) {
                        Document document1 = new Document(document);
                        document1.put("_id",document.get("_id").toString().replace("_1","_" + (i+1)));
                        document1.append(s[16], weifaList.get(i).getId());
                        document1.append(s[17], weifaList.get(i).getLierushijian());
                        document1.append(s[18], weifaList.get(i).getLierushiyou());
                        document1.append(s[19], weifaList.get(i).getYichushijian());
                        document1.append(s[20], weifaList.get(i).getYichushiyou());
                        documentInsert.add(document1);
                    }else{
                        documentInsert.get(i).append(s[16], weifaList.get(i).getId());
                        documentInsert.get(i).append(s[17], weifaList.get(i).getLierushijian());
                        documentInsert.get(i).append(s[18], weifaList.get(i).getLierushiyou());
                        documentInsert.get(i).append(s[19], weifaList.get(i).getYichushijian());
                        documentInsert.get(i).append(s[20], weifaList.get(i).getYichushiyou());
                    }
                }
            }
        }else {
            for (int size = 0; size < documentInsert.size(); size++){
                for (int i = 16,j=0; j < 5; i++,j++){
                    documentInsert.get(size).append(s[i], "");
                }
            }
        }
        
        // 加入"统一社会信用代码"
        for (int size = 0; size < documentInsert.size(); size++){
            documentInsert.get(size).append("统一社会信用代码", unifiedCode);
            sourse.updateOne(new BasicDBObject("_id", documentInsert.get(size).get("_id").toString()), new BasicDBObject("$set", documentInsert.get(size)), (new UpdateOptions()).upsert(true));
        }
    }

    public static boolean IsExistence(String s){
        FindIterable<Document> documents = sourse.find(new Document("_id", s + "_1"));
        for (Document d:documents){
            return false;
        }
        return true;
    }
}
