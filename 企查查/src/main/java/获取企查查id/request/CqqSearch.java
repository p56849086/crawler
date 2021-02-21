package 获取企查查id.request;

import base.GetIp;
import base.RequestBase;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

public class CqqSearch extends RequestBase {

    public CqqSearch(GetIp getIp) {
        super(getIp);
        // 设置请求头
        httpGet.addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36");
        httpGet.addHeader("cookie", "QCCSESSID=usi6fse3g522a2aqem4kr33vr4; UM_distinctid=176b28d193a87a-03302930b87393-c791039-1fa400-176b28d193bbd5; zg_did=%7B%22did%22%3A%20%22176b28d1a021be-0c8e5684533322-c791039-1fa400-176b28d1a0372a%22%7D; _uab_collina=160931336058429460648508; hasShow=1; acw_tc=b4a37a1816098131782331815e5b614b20c70d4ccd26f3a780419a58b7; CNZZDATA1254842228=1321360919-1609308066-https%253A%252F%252Fwww.baidu.com%252F%7C1609810293; MQCCSESSID=63cm66344746gdad9rnd5ctf65; zg_de1d1a35bfa24ce29bbf2c7eb17e6c4f=%7B%22sid%22%3A%201609813181079%2C%22updated%22%3A%201609813779669%2C%22info%22%3A%201609313360392%2C%22superProperty%22%3A%20%22%7B%7D%22%2C%22platform%22%3A%20%22%7B%7D%22%2C%22utm%22%3A%20%22%7B%7D%22%2C%22referrerDomain%22%3A%20%22www.qcc.com%22%2C%22cuid%22%3A%20%2240366e6369b1742fe64d4388c6221ba8%22%2C%22zs%22%3A%200%2C%22sc%22%3A%200%7D");
    }

    public String requestPage(String name) throws URISyntaxException, IOException {
        String companyName = URLEncoder.encode(name, "utf-8");
        String url = "https://www.qcc.com/web/search?key=" + companyName;
        httpGet.setURI(new URI(url));

        // 循环请求保证出错可以重试
        while (true){
            try {
                response = httpClient.execute(httpGet);
                if(response.getStatusLine().getStatusCode() == 200) {
                    String html = EntityUtils.toString(response.getEntity(),"GB18030");
                    return html;
                }else {
                    System.out.println("请求代码：" + response.getStatusLine().getStatusCode() + "更换Ip开始重试");
                    Thread.sleep(1000*10);
                }
            } catch (SocketTimeoutException e){
                System.out.println(e.getMessage() + "，请求超时重试");
                try {
                    Thread.sleep(10*1000);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                continue;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("请求基本信息表出错，开始重试");
                try {
                    Thread.sleep(10*1000);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                continue;
            }finally {
                if (response != null){
                    EntityUtils.consumeQuietly(response.getEntity());
                }
            }
        }
    }

    // 处理页面
    public String dealPage(String html) throws Exception {
        Document document = Jsoup.parse(html);
        if (document.selectFirst("table[class=ntable ntable-list]") == null) {
            if ("没有找到相关数据 我不甘心，还想试一试 >".equals(document.getElementsByClass("text-gray").get(0).text())){
                throw new Exception("公司不存在");
            }
            System.out.println("解析页面出错");
            throw new Exception("抛出异常");
        }
        Element tr = document.selectFirst("table[class=ntable ntable-list]").selectFirst("tr");
        // 获取地址
        if ( tr.selectFirst("a") == null)
            throw new Exception("公司不存在");
        String a = tr.selectFirst("a").attr("href");
        // 解析，只需要id
        String[] str = a.split("/");
        String id = str[str.length-1].replace(".html", "");
        // 获取搜素的公司名称
        String name = tr.selectFirst("a").text();
        return name + "_" + id;
    }

    public void reProxyIp() {
        //修改代理设置
        HttpHost proxy = new HttpHost(getIp.getIp(), Integer.parseInt(getIp.getPort()), "http");
        CredentialsProvider provider = new BasicCredentialsProvider();
        //包含账号密码的代理
        provider.setCredentials(new AuthScope(proxy), new UsernamePasswordCredentials("2120072300001201504", "ASySTfjht9VuRUh9"));
        httpClient = HttpClients.custom().setDefaultCredentialsProvider(provider).build();
        RequestConfig config = RequestConfig.custom().setProxy(proxy).setConnectTimeout(5000).setConnectionRequestTimeout(1000)
                .setSocketTimeout(5000).build();
        httpGet.setConfig(config);
    }
}
