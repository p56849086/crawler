package 不需要的代码;

import Entity.GetIp;
import Entity.Yichang;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class CompletePage2{
    private String i;
    private String unifiedCode;
    private CloseableHttpClient httpClient = HttpClients.createDefault();
    private HttpGet httpGet = new HttpGet();
    private CloseableHttpResponse response= null;

    public String getUnifiedCode(){
        return unifiedCode;
    }

    private String Referer_url2;
    private String Referer_url3;

    private GetIp getIp;
    CompletePage2(String i, String unifiedCode, GetIp getIp){
        this.i = i;
        this.unifiedCode = unifiedCode;
        this.getIp = getIp;
        //设置超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(7000).setConnectionRequestTimeout(7000)
                .setSocketTimeout(7000).build();
        this.httpGet.setConfig(requestConfig);
        if (getIp.getIp() != null && !"".equals(getIp.getIp())){
            reProxyIp();
        }
    }


    // 请求第一个表格
    String requestTable1() throws URISyntaxException {

        // 设定url
        String url = "http://www.chinanpo.gov.cn/search/poporg.html?i=" + i + "&u=" + unifiedCode;
        this.Referer_url2 = url;
        // 插入请求头
        for (Headers1 header1 : Headers1.values()){
            httpGet.addHeader(String.valueOf(header1).replace("_","-"), header1.getName());
        }
        httpGet.setURI(new URI(url));

        // 发送请求
        int i = 0;
        int m = 1;
        while (true){
            try {
                response = httpClient.execute(httpGet);
                if (response.getStatusLine().getStatusCode() == 200) {
                    String html = EntityUtils.toString(response.getEntity(), "utf-8");
                    return html;
                }
                else {
                    //不考虑访问超时情况，重新设置代理
                    System.out.println("请求代码为：" + response.getStatusLine().getStatusCode() + "开始重试");
                    if (i++ < 5 && response.getStatusLine().getStatusCode() == 400){
                        System.out.println("请求400重试第" + i + "次");
                        continue;
                    }
                    getIp.requestIp();
                    reProxyIp();
                }
            } catch (Exception e) {
                //不考虑访问超时情况，重新设置代理
                if (e.getMessage()!= null && !"".equals(e.getMessage()) &&"Read timed out".equals(e.getMessage())){
                    System.out.println("请求超时，使用该ip开始重试");
                    continue;
                }
                while (m++<=3){
                    System.out.println("请求第一个表格出错，开始重试：" + m + "次");
                    continue;
                }
                getIp.requestIp();
                reProxyIp();
                System.out.println("请求第一个表格出错，重新请求" + e);
            }

        }
    }

    // 请求第二个页面
    String requestTable2() throws URISyntaxException {
        // 设定url
        String url = "http://www.chinanpo.gov.cn/search/sxxx.html?unifiedCode=" + unifiedCode + "&type=1";
        this.Referer_url3 = url;
        // 首先删除已经存在的请求头
        for (Headers1 header1 : Headers1.values()){
            httpGet.removeHeaders(String.valueOf(header1).replace("_","-"));
        }

        // 添加请求头
        for (Headers2 header2 : Headers2.values()){
            httpGet.addHeader(String.valueOf(header2).replace("_","-"), header2.getName());
        }
        httpGet.setURI(new URI(url));
        // 发送请求
        int i = 0;
        int m = 0;
        while (true){
            try {
                response = httpClient.execute(httpGet);
                if (response.getStatusLine().getStatusCode() == 200) {
                    String html = EntityUtils.toString(response.getEntity());
                    return html;
                }else {
                    //不考虑访问超时情况，重新设置代理
                    System.out.println("请求代码为：" + response.getStatusLine().getStatusCode() + "开始重试");
                    if (i++ < 5 && response.getStatusLine().getStatusCode() == 400){
                        System.out.println("请求400重试第" + i + "次");
                        continue;
                    }
                    getIp.requestIp();
                    reProxyIp();
                }
            } catch (Exception e) {
                if (e.getMessage()!= null && !"".equals(e.getMessage()) &&"Read timed out".equals(e.getMessage())){
                    System.out.println("请求超时，使用该ip开始重试");
                    continue;
                }
                while (m++<=3){
                    System.out.println("请求第一个表格出错，开始重试：" + m + "次");
                    continue;
                }
                getIp.requestIp();
                reProxyIp();
                System.out.println("请求第二个表格出错，重新请求" + e);
            }

        }
    }

    // 请求第三个页面
    String requestTable3() throws URISyntaxException {
        // 设定url
        String url = "http://www.chinanpo.gov.cn/search/showOrgMesg.do?action=showSXXXList&forward=sxxx&type=2&unifiedCode=" + unifiedCode;

        // 删除请求头
        for (Headers2 header2 : Headers2.values()){
            httpGet.removeHeaders(String.valueOf(header2).replace("_","-"));
        }
        // 添加请求头
        for (Headers3 header3 : Headers3.values()){
            httpGet.addHeader(String.valueOf(header3).replace("_","-"), header3.getName());
        }
        httpGet.setURI(new URI(url));
        // 发送请求
        int i = 0;
        int m = 0;
        while (true){
            try {
                response = httpClient.execute(httpGet);
                if (response.getStatusLine().getStatusCode() == 200) {
                    String html = EntityUtils.toString(response.getEntity());
                    return html;
                }else {
                    //不考虑访问超时情况，重新设置代理
                    System.out.println("请求代码为：" + response.getStatusLine().getStatusCode() + "开始重试");
                    if (i++ < 5 && response.getStatusLine().getStatusCode() == 400){
                        System.out.println("请求400重试第" + i + "次");
                        continue;
                    }
                    getIp.requestIp();
                    reProxyIp();
                }
            } catch (Exception e) {
                if (e.getMessage()!= null && !"".equals(e.getMessage()) &&"Read timed out".equals(e.getMessage())){
                    System.out.println("请求超时，使用该ip开始重试");
                    continue;
                }
                while (m++<=3){
                    System.out.println("请求第一个表格出错，开始重试：" + m + "次");
                    continue;
                }
                getIp.requestIp();
                reProxyIp();
                System.out.println("请求第三个表格出错，重新请求" + e);
            }

        }
    }

    // 解析第一个页面
    List<String> dealPage1(String html){
        List<String> listString = new ArrayList<String>();
        Document document = Jsoup.parse(html);

        //获取社会组织名称
        String h3 = document.selectFirst("h3").toString();
        String[] nameString = h3.split(";");
        String name = nameString[0].replace("<h3>", "").replace("&nbsp", "");
        listString.add(name);
        for (Element tr: document.selectFirst("table").select("tr")){
            int i = 0;
            for (Element td : tr.select("td")){
                if (i % 2 != 0){
                    listString.add(td.text());
                }
                i++;
            }
        }
        return listString;
    }

    //解析第二个页面
    List<Yichang> dealPage2(String html){
        List<Yichang> yichangsList = new ArrayList<Yichang>();
        Document document = Jsoup.parse(html);
        Element tbody = document.selectFirst("table").selectFirst("tbody");
        if (!tbody.text().equals("暂无相关数据！"))
            for (Element tr :document.selectFirst("table").selectFirst("tbody").select("tr")){
                Yichang yichang = new Yichang();
                int i = 0;
                for (Element td : tr.select("td")){
                    if (i == 0)
                        yichang.setId(td.text());
                    if (i == 1)
                        yichang.setLierushijian(td.text());
                    if (i == 2)
                        yichang.setLierushiyou(td.text());
                    if (i == 3)
                        yichang.setYichushijian(td.text());
                    if (i == 4)
                        yichang.setYichushiyou(td.text());
                    i++;
                }
                yichangsList.add(yichang);
            }

        return yichangsList;
    }

    //解析第三个页面
    List<Yichang> dealPage3(String html){
        List<Yichang> weifaList = new ArrayList<Yichang>();
        Document document = Jsoup.parse(html);
        Element tbody = document.selectFirst("table").selectFirst("tbody");
        if (!tbody.text().equals("暂无相关数据！"))
            for (Element tr :document.selectFirst("table").selectFirst("tbody").select("tr")){
                Yichang weifa = new Yichang();
                int i = 0;
                for (Element td : tr.select("td")){
                    if (i == 0)
                        weifa.setId(td.text());
                    if (i == 1)
                        weifa.setLierushijian(td.text());
                    if (i == 2)
                        weifa.setLierushiyou(td.text());
                    if (i == 3)
                        weifa.setYichushijian(td.text());
                    if (i == 4)
                        weifa.setYichushiyou(td.text());
                    i++;
                }
                weifaList.add(weifa);
            }

        return weifaList;
    }


    // 请求头
    enum Headers1 {
        Accept("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9"),
        Accept_Encoding("gzip, deflate"),
        Accept_Language("zh-CN,zh;q=0.9"),
        Connection("keep-alive"),
        Content_Type("application/x-www-form-urlencoded"),
        Cookie("Hm_lvt_3adce665674fbfb5552846b40f1c3cbc=1608255763,1608521102,1608543411,1608598070; chinanpojsessionid=0610CB0BCD260B635B655A7C62F5CF43.chinanpo_node2; Hm_lpvt_3adce665674fbfb5552846b40f1c3cbc=1608598085"),
        Host("www.chinanpo.gov.cn"),
        Referer("http://www.chinanpo.gov.cn/search/orgcx.html"),
        Upgrade_Insecure_Requests("1"),
        User_Agent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36");

        private String name;

        Headers1(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    // 请求头
    enum Headers2 {
        Accept("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9"),
        Accept_Encoding("gzip, deflate"),
        Accept_Language("zh-CN,zh;q=0.9"),
        Cookie("Hm_lvt_3adce665674fbfb5552846b40f1c3cbc=1608598070,1608771199,1608771256,1608774151; chinanpojsessionid=55D13B16F066971A3089B4C44CAFC9D3.chinanpo_node8; Hm_lpvt_3adce665674fbfb5552846b40f1c3cbc=1608779781"),
        User_Agent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36");

        private String name;

        Headers2(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    // 请求头
    enum Headers3 {
        Accept("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9"),
        Accept_Encoding("gzip, deflate"),
        Accept_Language("zh-CN,zh;q=0.9"),
        Connection("keep-alive"),
        Cookie("Hm_lvt_3adce665674fbfb5552846b40f1c3cbc=1608255763,1608521102,1608543411,1608598070; Hm_lpvt_3adce665674fbfb5552846b40f1c3cbc=1608598085; chinanpojsessionid=446A3269E48EEDFA0E44B3A93DB562B0.chinanpo_node4"),
        Upgrade_Insecure_Requests("1"),
        User_Agent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36");

        private String name;

        Headers3(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }


    public void reProxyIp(){
        //修改代理设置
        HttpHost proxy = new HttpHost(getIp.getIp(), Integer.parseInt(getIp.getPort()), "http");
        CredentialsProvider provider = new BasicCredentialsProvider();
        //包含账号密码的代理
        provider.setCredentials(new AuthScope(proxy), new UsernamePasswordCredentials("2120072300001201504", "ASySTfjht9VuRUh9"));
        httpClient = HttpClients.custom().setDefaultCredentialsProvider(provider).build();
        RequestConfig config = RequestConfig.custom().setProxy(proxy).setConnectTimeout(7000).setConnectionRequestTimeout(7000)
                .setSocketTimeout(7000).build();
        httpGet.setConfig(config);
    }
}
