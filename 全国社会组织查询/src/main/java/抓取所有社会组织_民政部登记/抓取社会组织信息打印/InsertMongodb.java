package 抓取所有社会组织_民政部登记.抓取社会组织信息打印;

import Entity.MinZheng;
import Entity.信息公示实体类.*;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;

import java.util.List;

public class InsertMongodb {
    static MongoCollection<Document> sourse = new MongoClient("192.168.1.175", 27017).getDatabase("organization_data").getCollection("民政部登记_社会组织信息");
    static void InsertMongodb(ListAll listAll){
        Document documentInsert = new Document();
        // 插入社会组织基本信息
        documentInsert.append("_id", listAll.getOrganization().get社会组织名称() + "_" + listAll.getOrganization().get统一社会信用代码());
        documentInsert.append("社会组织名称", listAll.getOrganization().get社会组织名称());
        documentInsert.append("报告生成时间", listAll.getOrganization().get报告生成时间());
        documentInsert.append("登记管理机关", listAll.getOrganization().get登记管理机关());
        documentInsert.append("业务主管单位", listAll.getOrganization().get业务主管单位());
        documentInsert.append("成立登记日期", listAll.getOrganization().get成立登记日期());
        documentInsert.append("注册资金", listAll.getOrganization().get注册资金());
        documentInsert.append("登记状态", listAll.getOrganization().get登记状态());
        documentInsert.append("登记证号", listAll.getOrganization().get登记证号());
        documentInsert.append("社会组织类型", listAll.getOrganization().get社会组织类型());
        documentInsert.append("住所", listAll.getOrganization().get住所());
        documentInsert.append("统一社会信用代码", listAll.getOrganization().get统一社会信用代码());
        documentInsert.append("OrgId", listAll.getOrganization().getOrgId());
        if (listAll.getList1().size()==0)
            documentInsert.append("成立公示", "暂无成立公示！");
        else {
            for (成立公示 a1:listAll.getList1()){
                Document document = new Document();
                document.append("序号", a1.get序号());
                document.append("业务类型", a1.get业务类型());
                document.append("办结日期", a1.get办结日期());
                documentInsert.append("成立公示_" + document.get("序号"), document);
            }
        }
        if (listAll.getList2().size()==0)
            documentInsert.append("变更公示", "暂无变更公示！");
        else {
            for (变更公示 a1:listAll.getList2()){
                Document document = new Document();
                document.append("序号", a1.get序号());
                document.append("业务类型", a1.get业务类型());
                document.append("变更前", a1.get变更前());
                document.append("变更后", a1.get变更后());
                document.append("办结日期", a1.get办结日期());
                documentInsert.append("变更公示_" + document.get("序号"), document);
            }
        }

        if (listAll.getList3().size()==0)
            documentInsert.append("注销公示", "暂无注销公示！");
        else {
            for (注销公示 a1:listAll.getList3()){
                Document document = new Document();
                document.append("序号", a1.get序号());
                document.append("业务类型", a1.get业务类型());
                document.append("办结日期", a1.get办结日期());
                documentInsert.append("注销公示_" + document.get("序号"), document);
            }
        }
        if (listAll.getList4().size()==0)
            documentInsert.append("年检结果", "暂无年检结果！");
        else {
            for (年检结果 a1:listAll.getList4()){
                Document document = new Document();
                document.append("序号", a1.get序号());
                document.append("业务类型", a1.get年度());
                document.append("办结日期", a1.get年检结果());
                documentInsert.append("年检结果_" + document.get("序号"), document);
            }
        }
        if (listAll.getList5().size()==0)
            documentInsert.append("评估信息", "暂无评估信息！");
        else {
            for (评估信息 a1:listAll.getList5()){
                Document document = new Document();
                document.append("序号", a1.get序号());
                document.append("业务类型", a1.get评估等级());
                document.append("办结日期", a1.get等级有效期());
                documentInsert.append("评估信息_" + document.get("序号"), document);
            }
        }
        if (listAll.getList6().size()==0)
            documentInsert.append("表彰信息", "暂无表彰信息！");
        else {
            for (表彰信息 a1:listAll.getList6()){
                Document document = new Document();
                document.append("序号", a1.get序号());
                document.append("业务类型", a1.get荣誉名称());
                document.append("办结日期", a1.get日期());
                documentInsert.append("表彰信息_" + document.get("序号"), document);
            }
        }
        if (listAll.getList7().size()==0)
            documentInsert.append("中央财政支持项目", "暂无中央财政支持项目！");
        else {
            for (中央财政支持项目 a1:listAll.getList7()){
                Document document = new Document();
                document.append("序号", a1.get序号());
                document.append("业务类型", a1.get年度());
                document.append("办结日期", a1.get项目编号());
                document.append("办结日期", a1.get项目名称());
                document.append("办结日期", a1.get立项资金_万元());
                documentInsert.append("中央财政支持项目_" + document.get("序号"), document);
            }
        }
        if (listAll.getList8().size()==0)
            documentInsert.append("行政处罚", "暂无行政处罚！");
        else {
            for (行政处罚 a1:listAll.getList8()){
                Document document = new Document();
                document.append("序号", a1.get序号());
                document.append("业务类型", a1.get决定书文号());
                document.append("办结日期", a1.get处罚事由());
                document.append("办结日期", a1.get处罚依据());
                document.append("办结日期", a1.get处罚结果());
                document.append("办结日期", a1.get处罚决定日期());
                documentInsert.append("行政处罚_" + document.get("序号"), document);
            }
        }
        if (listAll.getList9().size()==0)
            documentInsert.append("异常活动名录信息", "暂无异常活动名录信息！");
        else {
            for (异常活动名录信息 a1:listAll.getList9()){
                Document document = new Document();
                document.append("序号", a1.get序号());
                document.append("业务类型", a1.get列入时间());
                document.append("办结日期", a1.get列入事由());
                document.append("办结日期", a1.get移出时间());
                document.append("办结日期", a1.get移出事由());
                documentInsert.append("异常活动名录信息_" + document.get("序号"), document);
            }
        }
        if (listAll.getList10().size()==0)
            documentInsert.append("严重违法失信名单", "暂无严重违法失信名单信息！");
        else {
            for (严重违法失信名单 a1:listAll.getList10()){
                Document document = new Document();
                document.append("序号", a1.get序号());
                document.append("业务类型", a1.get列入时间());
                document.append("办结日期", a1.get列入事由());
                document.append("办结日期", a1.get移出时间());
                document.append("办结日期", a1.get移出事由());
                documentInsert.append("严重违法失信名单_" + document.get("序号"), document);
            }
        }
        sourse.updateOne(new BasicDBObject("_id", documentInsert.get("_id").toString()), new BasicDBObject("$set", documentInsert), (new UpdateOptions()).upsert(true));
    }
}
