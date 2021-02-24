package base.requests;

import Entity.GetIp;
import base.ReqeustBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;

public class RequestDownLoad extends ReqeustBase {
    public RequestDownLoad(GetIp getIp, String type, String url) {
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
    // 设置头
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
