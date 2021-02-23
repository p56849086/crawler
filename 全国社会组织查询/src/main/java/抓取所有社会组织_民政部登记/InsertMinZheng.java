package 抓取所有社会组织_民政部登记;

import Entity.MinZheng;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;

import java.util.List;

public class InsertMinZheng {
    static MongoCollection<Document> sourse = new MongoClient("192.168.1.175", 27017).getDatabase("organization_data").getCollection("民政部登记_所有社会组织");
    static void InsertMongodb(List<MinZheng> minZhengs){
        for (MinZheng minZheng: minZhengs){
            Document document = new Document();
            document.append("_id", minZheng.getOrganizationName() + "_" + minZheng.getUnifiedCode());
            document.append("社会组织名称", minZheng.getOrganizationName());
            document.append("统一社会信用代码", minZheng.getUnifiedCode());
            document.append("社会组织类型", minZheng.getOrganizationType());
            document.append("成立登记日期", minZheng.getOrganizationTime());
            document.append("状态", minZheng.getOrganizationStatue());
            document.append("OrgId", minZheng.getOrgId());
            sourse.updateOne(new BasicDBObject("_id", document.get("_id").toString()), new BasicDBObject("$set", document), (new UpdateOptions()).upsert(true));

        }
    }

}
