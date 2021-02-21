package 导出数据;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class 导出全国社会组织信息 {
    public static void main(String[] args) {
        String[] title = {"社会组织名称", "登记管理机关", "业务主管单位", "法定代表人", "成立登记日期", "注册资金", "登记状态",
                "社会组织类型", "证书有效期", "住所", "业务范围", "异常活动名录_序号", "异常活动名录_列入时间", "异常活动名录_列入事由",
                "异常活动名录_移出时间", "异常活动名录_移出事由", "严重违法失信名单_序号", "严重违法失信名单_列入时间",
                "严重违法失信名单_列入事由", "严重违法失信名单_移出时间", "严重违法失信名单_移出事由", "统一社会信用代码"};
        MongoCollection<Document> sourse = new MongoClient("192.168.1.175", 27017).getDatabase("organization_data").getCollection("全国组织信息");
        Document query = new Document("_id",new Document("$gte", ""));
        FindIterable<Document> documents = sourse.find(query).sort(new Document("_id", 1));
        WriteExcel writeExcel = new WriteExcel();
        writeExcel.initWriteExcel(title);
        int countAll = 1;
        for (Document document : documents){
            List<String> stringList = new ArrayList<String>();
            for (Map.Entry<String, Object> strings :document.entrySet()){
                stringList.add(strings.getValue().toString());
            }
            writeExcel.insertRow(stringList);
            System.out.println("共完成：" + countAll++ + "，数据id:" + document.get("_id"));
            if (countAll == 100){
                writeExcel.writeExcel("C:\\Users\\jinghe\\Desktop\\全国社会组织查询\\" , "全国社会组织查询_地方登记");
                break;
            }
        }

    }
}
