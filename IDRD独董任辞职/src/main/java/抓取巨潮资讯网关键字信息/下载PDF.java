package 抓取巨潮资讯网关键字信息;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import lombok.SneakyThrows;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.bson.Document;

import java.io.File;
import java.net.URI;

import static org.apache.commons.io.FileUtils.writeByteArrayToFile;

public class 下载PDF {
    @SneakyThrows
    public static void main(String[] args){
        HttpGet httpGet = new HttpGet();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response;

        MongoCollection<Document> sourse = new MongoClient("192.168.1.175", 27017).getDatabase("IDRD_JC").getCollection("巨潮资讯网_独董离职_独董辞职_2019.1_2019.12");
        Document query = new Document("_id", new Document("$gte",""));
        FindIterable<Document> documents = sourse.find(query).sort(new Document("_id",1));
        int count=1;
        for (Document document: documents){
            String[] index = document.getString("下载Id").split("_");
            httpGet.setURI(new URI("http://www.cninfo.com.cn/new/announcement/download?bulletinId=" + index[0] + "&announceTime=" + index[1]));
            response = httpClient.execute(httpGet);
            byte[] html = EntityUtils.toByteArray(response.getEntity());
            // 保存
            writeByteArrayToFile(new File("C:\\Users\\jinghe\\Desktop\\新建文件夹\\" + document.getString("_id").replace("*", "#").replace(":","：") + ".PDF"), html);
            System.out.println("共完成" + count++ + ", 正在进行:" + document.get("_id"));
        }
    }
}
