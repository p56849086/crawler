package 解析html中数据;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import org.bson.Document;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class 处理主要人员信息 {
    static MongoCollection<Document> source2 = new MongoClient("192.168.1.175", 27017).getDatabase("所有省市高新技术企业认定名单").getCollection("企查查html");
    static MongoCollection<Document> source3 = new MongoClient("192.168.1.175", 27017).getDatabase("所有省市高新技术企业认定名单").getCollection("企查查_主要人员");

    public static void main(String[] args) {
        Document query = new Document("_id", new Document("$gte", ""));
        FindIterable<Document> documents = source2.find(query).sort(new Document("_id",1));
        for (Document document : documents){
            Document insertDocument = new Document();
            insertDocument.append("_id", document.get("_id"));
            insertDocument.append("企查查搜索后的名称", document.get("企查查搜索后的名称"));
            insertDocument.append("qcc_id", document.get("qcc_id"));
            insertDocument.append("主要人员数量", document.get("主要人员数量"));
            if (document.get("主要人员html") != null && !"信息不存在".equals(document.get("主要人员html").toString())) {
                org.jsoup.nodes.Document document3 = Jsoup.parse(document.get("主要人员html").toString());
                Elements tr = document3.getElementsByClass("ntable").select("tr");
                int i = 0;
                List<String> strings = new ArrayList<String>();
                Document document1 = new Document();
                for (Element td: tr) {
                    Document document2 = new Document();
                    if (i == 0){
                        for (Element th: td.select("th")){
                            strings.add(th.text());
                        }
                    }
                    if (i%2==1){
                        if (td.selectFirst("td") == null){
                            break;
                        }
                        String id = td.selectFirst("td").text();
                        String name = td.select("span").size()==1?td.select("span").get(0).text():td.select("span").get(1).text();
                        document2.append(strings.get(0), id);
                        document2.append(strings.get(1), name);
                        td.selectFirst("td").remove();
                        td.select("td").get(0).remove();
                        int m = 2;
                        for (Element elements: td.select("td")){
                            document2.append(strings.get(m++), elements.text());
                        }
                        document1.append(id, document2);
                    }
                    i++;
                }
                insertDocument.append("主要人员", document1);
                source3.updateOne(new BasicDBObject("_id", insertDocument.get("_id")), new BasicDBObject("$set", insertDocument), (new UpdateOptions()).upsert(true));
                if ( Integer.valueOf(insertDocument.get("主要人员数量").toString()) != ((Document)insertDocument.get("主要人员")).size())
                    System.out.println("数量不等，出错");
                System.out.println(insertDocument.get("_id") + ": 主要人员数-" + insertDocument.get("主要人员数量") +
                        ", 读取的主要人员数-" + ((Document)insertDocument.get("主要人员")).size());
            }
        }
    }
}
