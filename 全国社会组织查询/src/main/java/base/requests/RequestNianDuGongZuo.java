package base.requests;

import Entity.GetIp;
import Entity.年度工作报告;
import base.ReqeustBase;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class RequestNianDuGongZuo extends ReqeustBase {
    public RequestNianDuGongZuo(GetIp getIp, String type, String url) {
        super(getIp, type, url);
        // 设置请求头,请求参数
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
        Accept("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9"),
        Accept_Encoding("gzip, deflate"),
        Accept_Language("zh-CN,zh;q=0.9"),
        Connection("keep-alive"),
        Content_Type("application/x-www-form-urlencoded"),
        Cookie("Hm_lvt_3adce665674fbfb5552846b40f1c3cbc=1614043188,1614056851,1614064771,1614064936; Hm_lpvt_3adce665674fbfb5552846b40f1c3cbc=1614064936; chinanpojsessionid=56E494354B6B16D2545FBF07E895EA3C.chinanpo_node1"),
        Host("59.252.100.194"),
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

    @Override
    public Object dealPage(String html) {
        List<年度工作报告> list = new ArrayList<>();
        // 处理Html页面
        Document document = Jsoup.parse(html);
        Elements trs = document.select("table").get(0).select("tr");
        for (int i=1; i<trs.size(); i++){
            年度工作报告 a1 = new 年度工作报告();
            Elements tds = trs.get(i).select("td");
            if (!"暂无相关数据！".equals(trs.get(i).text())){
                // 获取年度报告书地址
                String url = tds.get(1).selectFirst("a").attr("href");
                a1.set序号(String.valueOf(i));
                a1.set年检报告书名称(tds.get(1).text());
                a1.set发布时间(tds.get(2).text());
                a1.setUrl(url);
            }
            list.add(a1);
        }
        return list;
    }
}
