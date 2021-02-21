package utils;

import Entity.GetIp;
import base.ReqeustBase;

// 子类需要修改请求头
public class RequestPage extends ReqeustBase{

    public RequestPage(GetIp getIp, String type, String url) {
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

    // 换页

    enum Header1 {
        Accept("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9"),
        Accept_Encoding("gzip, deflate"),
        Accept_Language("zh-CN,zh;q=0.9"),
        Cache_Control("max-age=0"),
        Connection("keep-alive"),
        //Content_Length("163"),
        Content_Type("application/x-www-form-urlencoded"),
        Cookie("Hm_lvt_3adce665674fbfb5552846b40f1c3cbc=1611642101,1611824504,1611886766,1611899518; chinanpojsessionid=2CD80C30CB266B5E2621F56C2D5EF878.chinanpo_node7; Hm_lpvt_3adce665674fbfb5552846b40f1c3cbc=1611899520"),
        Host("www.chinanpo.gov.cn"),
        Origin("http://www.chinanpo.gov.cn"),
        Referer("http://www.chinanpo.gov.cn/search/orgcx.html"),
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
