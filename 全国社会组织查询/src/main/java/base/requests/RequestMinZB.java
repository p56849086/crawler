package base.requests;

import Entity.GetIp;
import Entity.MinZheng;
import base.ReqeustBase;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

// 子类需要修改请求头
public class RequestMinZB extends ReqeustBase{

    public RequestMinZB(GetIp getIp, String type, String url) {
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
        // 设置请求参数
    }

    // 处理页面
    public Object dealPage(String html){
        // 处理Html页面
        List<MinZheng> minZhengs = new ArrayList<>();
        Document document = Jsoup.parse(html);
        document.getElementsByClass("table-1 mar-top");
        for (int i=1; i<=document.getElementsByClass("table-1 mar-top").get(0).select("tr").size()-1; i++){
            MinZheng minZheng = new MinZheng();
            Element tr = document.getElementsByClass("table-1 mar-top").get(0).select("tr").get(i);
            String organizationName = tr.select("td").get(1).text();
            String unifiedCode = tr.select("td").get(2).text();
            String organizationType = tr.select("td").get(3).text();
            String organizationBoss = tr.select("td").get(4).text();
            String organizationTime = tr.select("td").get(5).text();
            String organizationStatue = tr.select("td").get(6).text();
            String orgId = tr.select("td").get(1).select("a").attr("href").split("\\(")[1].replace(");", "");
            minZheng.setOrganizationName(organizationName);
            minZheng.setUnifiedCode(unifiedCode);
            minZheng.setOrganizationType(organizationType);
            minZheng.setOrganizationBoss(organizationBoss);
            minZheng.setOrganizationTime(organizationTime);
            minZheng.setOrganizationStatue(organizationStatue);
            minZheng.setOrgId(orgId);
            minZhengs.add(minZheng);
        }
        return minZhengs;
    }

    // 设置头
    enum Header1 {
        Accept("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9"),
        Accept_Encoding("gzip, deflate"),
        Accept_Language("zh-CN,zh;q=0.9"),
        Cache_Control("max-age=0"),
        Connection("keep-alive"),
        //Content_Length("163"),
        Content_Type("application/x-www-form-urlencoded"),
        Cookie("Hm_lvt_3adce665674fbfb5552846b40f1c3cbc=1613979834; chinanpojsessionid=949025F5A937E5579BCA7B6744D60514.chinanpo_node3; Hm_lpvt_3adce665674fbfb5552846b40f1c3cbc=1613980193"),
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

}
