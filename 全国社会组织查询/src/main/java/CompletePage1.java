import Entity.GetIp;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CompletePage1 {

    private String page;

    // 设置代理
    GetIp getIp;

    CloseableHttpClient httpClient = HttpClients.createDefault();
    HttpPost httpPost = new HttpPost();

    public CompletePage1(String page,  GetIp getIp) {
        this.page = page;
        this.getIp = getIp;
        //设置超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(7000).setConnectionRequestTimeout(7000)
                .setSocketTimeout(7000).build();
        this.httpPost.setConfig(requestConfig);
        if (getIp.getIp() != null && !"".equals(getIp.getIp())){
            reProxyIp();
        }
    }

    // 请求页面
    public String requestPage() throws URISyntaxException {
        httpPost.setURI(new URI("http://www.chinanpo.gov.cn/search/orgcx.html"));

        // 加入请求头
        for (Headers header : Headers.values()){
            httpPost.addHeader(String.valueOf(header).replace("_","-"), header.getName());
        }
        // 加入请求的数据
        String payload="t=3&legalName=&orgName=&regName=%25E6%25B0%2591%25E6%2594%25BF&supOrgName=&corporateType=" +
                "&managerDeptCode=&registrationNo=&unifiedCode=&orgAddNo=&ifCharity=&ifCollect=&status=-1&regNumB=" +
                "&regNumD=&tabIndex=2&regNum=-1&regDate=&regDateEnd=&isZyfw=&isHyxh=&page_flag=true" +
                "&pagesize_key=usciList&goto_page=next&current_page=" + this.page + "&total_count=876431&page_size=50&to_page=";
        StringEntity stringEntity = new StringEntity(payload, ContentType.APPLICATION_JSON);		//推荐的方法
        //设置表单的Entity对象到Post请求中
        httpPost.setEntity(stringEntity);


        // 设置表单的Entity对象到Post请求中
        CloseableHttpResponse response= null;
        int i = 0;
        while (true){
            try {
                response = httpClient.execute(httpPost);
                if (response.getStatusLine().getStatusCode() == 200) {
                    String html = EntityUtils.toString(response.getEntity());
                    return html;
                } else {
                    //修改代理设置
                    System.out.println("请求代码为：" + response.getStatusLine().getStatusCode() + "开始重试");
                    if (i++ < 5 && response.getStatusLine().getStatusCode() == 400){
                        System.out.println("请求400重试第" + i + "次");
                        continue;
                    }
                    getIp.requestIp();
                    reProxyIp();
                    continue;
                }
            }catch (Exception e){
                if (e.getMessage()!= null && !"".equals(e.getMessage()) &&"Read timed out".equals(e.getMessage())){
                    System.out.println("请求超时，使用该ip开始重试");
                    continue;
                }
                System.out.println("第一个请求出错，开始重试");
                //修改代理设置
                getIp.requestIp();
                reProxyIp();
                continue;
            }
        }
    }

    // 处理页面信息
    List<String> dealPage(String html){
        List<String> listString = new ArrayList<String>();
        Document document = Jsoup.parse(html);
        Element table = document.selectFirst("table.table-1");
        for (int i = 0; i < table.select("tr").size(); i++){
            if (i > 0){
                Elements td = table.select("tr").get(i).select("td");
                String[] href = td.get(1).select("a[href]").toString().split(";");
                String linkString = new String();
                linkString = href[1].replace("&quot", "") + "_" + href[3].replace("&quot", "");
                listString.add(linkString + "##" + td.get(1).text());
            }
        }
        return listString;
    }


    // 请求头
    enum Headers {
        Accept("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9"),
        Accept_Encoding("gzip, deflate"),
        Accept_Language("zh-CN,zh;q=0.9"),
        Cache_Control("max-age=0"),
        Connection("keep-alive"),
        //Content_Length("163"),
        Content_Type("application/x-www-form-urlencoded"),
        Cookie("Hm_lvt_3adce665674fbfb5552846b40f1c3cbc=1608255763,1608521102,1608543411,1608598070; chinanpojsessionid=8F4C71DE51043A740F8254528C5F8CB9.chinanpo_node1; Hm_lpvt_3adce665674fbfb5552846b40f1c3cbc=1608598074"),
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
    public void reProxyIp(){
        HttpHost proxy = new HttpHost(getIp.getIp(), Integer.parseInt(getIp.getPort()), "http");
        CredentialsProvider provider = new BasicCredentialsProvider();
        //包含账号密码的代理
        provider.setCredentials(new AuthScope(proxy), new UsernamePasswordCredentials("2120072300001201504", "ASySTfjht9VuRUh9"));
        httpClient = HttpClients.custom().setDefaultCredentialsProvider(provider).build();
        RequestConfig config = RequestConfig.custom().setProxy(proxy).setConnectTimeout(7000).setConnectionRequestTimeout(7000)
                .setSocketTimeout(7000).build();
        httpPost.setConfig(config);
    }
}
