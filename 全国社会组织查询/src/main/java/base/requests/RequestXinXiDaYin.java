package base.requests;

import Entity.GetIp;
import base.ReqeustBase;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class RequestXinXiDaYin extends ReqeustBase {


    public RequestXinXiDaYin(GetIp getIp, String type, String url) {
        super(getIp, type, url);
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
    // 设置头
    enum Header1 {
        Accept("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9"),
        Accept_Encoding("gzip, deflate"),
        Accept_Language("zh-CN,zh;q=0.9"),
        Cache_Control("max-age=0"),
        Connection("keep-alive"),
        Content_Type("application/x-www-form-urlencoded"),
        Cookie("Hm_lvt_3adce665674fbfb5552846b40f1c3cbc=1613979834,1613985409,1614043188; chinanpojsessionid=3679D23C3F057575C968C7CFEC50CF49.chinanpo_node6; Hm_lpvt_3adce665674fbfb5552846b40f1c3cbc=1614043192"),
        Host("59.252.100.194"),
        Origin("http://59.252.100.194"),
        Referer("http://59.252.100.194/search/orgcx.html"),
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

    public Object dealPage(String html) {
        Document document = Jsoup.parse(html);
        Elements a = document.select("a");
        String url = "";
        for (Element a1:a){
            if ("信息打印".equals(a1.text())){
                url = a1.attr("href");
            }
        }
        return url;
    }
}
