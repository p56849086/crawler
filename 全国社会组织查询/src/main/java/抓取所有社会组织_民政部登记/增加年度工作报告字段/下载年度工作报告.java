package 抓取所有社会组织_民政部登记.增加年度工作报告字段;

import Entity.GetIp;
import base.ReqeustBase;
import base.requests.RequestDownLoad;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.io.File;
import java.io.IOException;

import static org.apache.commons.io.FileUtils.writeByteArrayToFile;

public class 下载年度工作报告 {
    public static void main(String[] args){
        MongoCollection<Document> sourse = new MongoClient("192.168.1.175", 27017).getDatabase("organization_data").getCollection("民政部登记_社会组织信息");
        Document qurey = new Document("_id", new Document("$gte", ""));
        FindIterable<Document> documents = sourse.find(qurey).sort(new Document("_id", 1));
        ReqeustBase reqeustBase = new ReqeustBase(new GetIp(), "post", "http://59.252.100.194/viewbgs.html");
        for (Header1 header : Header1.values()){
            reqeustBase.httpPost.addHeader(String.valueOf(header).replace("_","-"), header.getName());
        }
        int count = 1;
        for (Document document: documents){
            if (document.get("年度工作报告")==null){
                for (int i=1; i<50; i++){
                    if (document.get("年度工作报告_" + i)==null)
                        continue;
                    Document document1 = (Document) document.get("年度工作报告_" + i);
                    String url = document1.getString("url");
                    String[] data1 = url.split("\\?");
                    String[] data2 = data1[1].split("&");
                    String[] data3 = new String[4];
                    for (int i1=0; i1<data2.length; i1++){
                        data3[i1] = data2[i1].substring(data2[i1].indexOf("=")+1, data2[i1].length());
                    }
                    String data = "id=" + data3[0] + "&dictionid=" + data3[1] + "&websitId=" + data3[2] + "&netTypeId=" + data3[3] + "&topid=";
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    byte[] html = reqeustBase.requestGetPage2(data);
                    try {
                        writeByteArrayToFile(new File("C:\\Users\\jinghe\\Desktop\\新建文件夹\\" + document1.getString("年检报告书名称").replace("*", "#").replace(":","：") + ".html"), html);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("共完成" + count++ + ", 正在进行:" + document.get("_id"));

                }
            }
        }
    }
    enum Header1 {
        Accept("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9"),
        Accept_Encoding("gzip, deflate"),
        Accept_Language("zh-CN,zh;q=0.9"),
        Cache_Control("max-age=0"),
        Connection("keep-alive"),
        Content_Type("application/x-www-form-urlencoded"),
        Cookie("Hm_lvt_3adce665674fbfb5552846b40f1c3cbc=1614056851,1614064771,1614064936,1614129363; chinanpojsessionid=E6EED2D0655343CC2CC4A0CD8D3F6DD0.chinanpo_node4; Hm_lpvt_3adce665674fbfb5552846b40f1c3cbc=1614129465"),
        Host("59.252.100.194"),
        Origin("http://59.252.100.194"),
        Upgrade_Insecure_Requests("1"),
        User_Agent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36");
        private String name;
        Header1(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }
    }
}
