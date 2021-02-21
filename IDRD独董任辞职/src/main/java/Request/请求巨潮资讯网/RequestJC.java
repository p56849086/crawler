package Request.请求巨潮资讯网;

import Request.GetIp;
import Request.ReqeustBase;
import Request.entity.EntityJC;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

public class RequestJC extends ReqeustBase {
    public RequestJC(GetIp getIp, String type, String url) {
        // 设置代理，请求类型，网页地址
        super(getIp, type, url);
        // 设置请求头
        switch (type){
            case "get":
                for (Header1 header : Header1.values()){
                    super.httpGet.addHeader(String.valueOf(header).replace("_","-"), header.getName());
                }
                break;
            case "post":
                for (Header1 header : Header1.values()){
                    super.httpPost.addHeader(String.valueOf(header).replace("_","-"), header.getName());
                }
                break;
        }
    }
    enum Header1 {
        Accept("application/json, text/javascript, */*; q=0.01"),
        Accept_Encoding("gzip, deflate"),
        Accept_Language("zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2"),
        Connection("keep-alive"),
        Cookie("JSESSIONID=58B6CBC1A5B45B21EA475A1BCF220726; insert_cookie=45380249; _sp_id.2141=db8bd9b3-e365-4359-b7d7-30339fc3cd06.1613874292.1.1613874918.1613874292.0602a761-365c-4e09-ac5f-3f3d969bde4f; routeId=.uc2"),
        Host("www.cninfo.com.cn"),
        //Referer("http://www.cninfo.com.cn/new/fulltextSearch?notautosubmit=&keyWord=%E7%8B%AC%E8%91%A3%E7%A6%BB%E8%81%8C"),
        User_Agent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:85.0) Gecko/20100101 Firefox/85.0");
        private String name;
        Header1(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }
    }

    // 处理JSON数据
    public Object dealPage(String html){
        List<EntityJC> list = new ArrayList<>();
        //  处理Json数据
        JsonObject asJsonObject = new JsonParser().parse(html).getAsJsonObject();
        for (JsonElement element: asJsonObject.getAsJsonArray("announcements")){
            EntityJC entityJC = new EntityJC();
            // 公司股票代码
            String code = element.getAsJsonObject().get("secCode").getAsString();
            // 报告名
            String announcementTitle = element.getAsJsonObject().get("announcementTitle").getAsString().replace("<em>","").replace("</em>", "");
            // 获取报告的类型
            String adjunctType = element.getAsJsonObject().get("adjunctType").getAsString();
            // 解析出需要的Id和时间
            String adjunctUrl = element.getAsJsonObject().get("adjunctUrl").getAsString();
            String[] index = adjunctUrl.split("/");
            // 时间
            String Date = index[1];
            // 需要的Id
            String Id = index[2].replace("."+adjunctType, "");
            entityJC.setCode(code);
            entityJC.setAnnouncementTitle(announcementTitle);
            entityJC.setAdjunctType(adjunctType);
            entityJC.setDate(Date);
            entityJC.setId(Id);
            list.add(entityJC);
        }
        return list;
    }
    public int getPages(String html){
        //  处理Json数据
        JsonObject asJsonObject = new JsonParser().parse(html).getAsJsonObject();
        int totalRecordNum = asJsonObject.get("totalRecordNum").getAsInt();
        int totalpages = asJsonObject.get("totalpages").getAsInt();
        if (totalRecordNum%2==0)
            return totalpages;
        else
            return totalpages+1;
    }
}
