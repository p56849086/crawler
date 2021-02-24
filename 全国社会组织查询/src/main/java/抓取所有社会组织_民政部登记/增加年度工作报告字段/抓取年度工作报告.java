package 抓取所有社会组织_民政部登记.增加年度工作报告字段;

import Entity.GetIp;
import Entity.年度工作报告;
import base.requests.RequestNianDuGongZuo;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import org.apache.poi.ss.formula.functions.T;
import org.bson.Document;

import java.util.List;

public class 抓取年度工作报告 {
    public static void main(String[] args){
        MongoCollection<Document> sourse = new MongoClient("192.168.1.175", 27017).getDatabase("organization_data").getCollection("民政部登记_社会组织信息");
        Document qurey = new Document("_id", new Document("$gte", ""));
        FindIterable<Document> documents = sourse.find(qurey).sort(new Document("_id", 1));
        RequestNianDuGongZuo requestNianDuGongZuo = new RequestNianDuGongZuo(new GetIp(), "get", "http://59.252.100.194/search/showOrgMesg.do");
        int count = 1;
        for (Document document: documents){
            String orgId = document.getString("OrgId");
            String Data = "?action=showNJJGList&forward=njjg&orgId=" + orgId + "&typename=njbg";
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            List<年度工作报告> list = (List<年度工作报告>) requestNianDuGongZuo.dealPage(requestNianDuGongZuo.requestGetPage(Data));
            if (list.isEmpty()){
                document.append("年度工作报告", "暂无相关数据！");
            }else {
                for (年度工作报告 a1: list){
                    Document document1 = new Document();
                    document1.append("序号", a1.get序号());
                    document1.append("年检报告书名称", a1.get年检报告书名称());
                    document1.append("发布时间", a1.get发布时间());
                    document1.append("url", a1.getUrl());
                    document.append("年度工作报告_" + document1.get("序号"), document1);
                }
            }
            sourse.updateOne(new BasicDBObject("_id", document.get("_id").toString()), new BasicDBObject("$set", document), (new UpdateOptions()).upsert(true));
            System.out.println("第" + count++ + "条数据，正在进行_id:" + document.get("_id"));
        }
    }
}
