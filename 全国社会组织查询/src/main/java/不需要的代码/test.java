package 不需要的代码;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;


public class test{
    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("http://www.chinanpo.gov.cn/search/orgcx.html");
        // 加入请求头
        for (Headers header :Headers.values()){
            httpPost.addHeader(String.valueOf(header).replace("_","-"), header.getName());
        }
        String payload="{\"t\":\"3\", \"regName\":\"%E6%B0%91%E6%94%BF\", \"status\":\"-1\", \"tabIndex\":\"2\", \"regNum\":\"-1\", " +
                "\"page_flag\":\"true\", \"pagesize_key\":\"usciList\", \"goto_page\":\"next\", \"current_page\":\"2\", " +
                "\"total_count\":\"876382\", \"page_size\":\"20\"}";
        StringEntity stringEntity = new StringEntity(payload, ContentType.APPLICATION_JSON);		//推荐的方法
        //设置表单的Entity对象到Post请求中
        httpPost.setEntity(stringEntity);

        CloseableHttpResponse response= null;
        CloseableHttpResponse response_get= null;
        String url="";
        while (true){
            response = httpClient.execute(httpPost);
            String html = EntityUtils.toString(response.getEntity());
            System.out.println(html);
        }
    }

}


enum Headers{
    Accept("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9"),
    Accept_Encoding("gzip, deflate"),
    Accept_Language("zh-CN,zh;q=0.9"),
    Cache_Control("max-age=0"),
    Connection("keep-alive"),
    //Content_Length("163"),
    Content_Type("application/x-www-form-urlencoded"),
    Cookie("Hm_lvt_3adce665674fbfb5552846b40f1c3cbc=1608025387,1608079771,1608096155,1608255763; chinanpojsessionid=3F9F9944E0945A162EA52DD5D1418089.chinanpo_node3; Hm_lpvt_3adce665674fbfb5552846b40f1c3cbc=1608263891"),
    Host("www.chinanpo.gov.cn"),
    Origin("http://www.chinanpo.gov.cn"),
    Referer("http://www.chinanpo.gov.cn/search/orgcx.html"),
    Upgrade_Insecure_Requests("1"),
    User_Agent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36");

    private String name;

    Headers(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
