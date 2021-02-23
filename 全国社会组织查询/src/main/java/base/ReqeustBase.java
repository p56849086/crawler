package base;

import Entity.GetIp;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
import org.apache.poi.ss.formula.functions.T;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;

public class ReqeustBase {
    public HttpPost httpPost;
    public HttpGet httpGet;
    GetIp getIp;
    CloseableHttpClient httpClient = HttpClients.createDefault();
    CloseableHttpResponse response;
    // 用于确定传入请求的类型
    String Type;
    // 请求地址
    String url;

    // 设置初始配置
    public ReqeustBase(GetIp getIp, String type, String url) {
        this.Type = type;
        // 设置httpPost,HttpGet初始化
        this.getIp = getIp;
        this.url = url;
        if (getIp.getIp() != null && !"".equals(getIp.getIp())) {
            reProxyIp();
        } else {
            //设置超时时间
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(5000).setConnectionRequestTimeout(5000)
                    .setSocketTimeout(5000).build();
            if ("get".equals(this.Type)){
                httpGet = new HttpGet();
                httpGet.setConfig(requestConfig);
            }else{
                httpPost = new HttpPost();
                httpPost.setConfig(requestConfig);
            }

        }
    }

    public String requestGetPage(String Data){

        // 设置url
        try {
            StringEntity stringEntity;
            switch (this.Type) {
                // 传入的Data为?后的数据
                case "get":
                    httpGet.setURI(new URI(this.url + Data));
                    break;
                case "post":
                    stringEntity = new StringEntity(Data, ContentType.MULTIPART_FORM_DATA);		//推荐的方法
                    httpPost.setURI(new URI(this.url));
                    httpPost.setEntity(stringEntity);
                    break;
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        // 循环请求保证出错可以重试
        while (true){
            try {
                if ("get".equals(this.Type)) {
                    response = httpClient.execute(httpGet);
                }else {
                    response = httpClient.execute(httpPost);
                }
                if(response.getStatusLine().getStatusCode() == 200) {
                    String html = EntityUtils.toString(response.getEntity(),"GB18030");
                    return html;
                }else {
                    System.out.println("请求代码：" + response.getStatusLine().getStatusCode() + "更换Ip开始重试");
                    getIp.requestIp();
                    reProxyIp();
                }
            } catch (SocketTimeoutException e){
                getIp.requestIp();
                reProxyIp();
                continue;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("请求网页出错，重试");
                getIp.requestIp();
                reProxyIp();
                continue;
            }finally {
                if (response != null){
                    EntityUtils.consumeQuietly(response.getEntity());
                }
            }
        }
    }

    // 处理页面
    public Object dealPage(String html){
        //  处理Json数据
        JsonObject asJsonObject = new JsonParser().parse(html).getAsJsonObject();
        // 处理Html页面
        Document document = Jsoup.parse(html);
        return null;
    }


    public void reProxyIp(){
        HttpHost proxy = new HttpHost(getIp.getIp(), Integer.parseInt(getIp.getPort()), "http");
        CredentialsProvider provider = new BasicCredentialsProvider();
        //包含账号密码的代理
        provider.setCredentials(new AuthScope(proxy), new UsernamePasswordCredentials("2120072300001201504", "ASySTfjht9VuRUh9"));
        httpClient = HttpClients.custom().setDefaultCredentialsProvider(provider).build();
        RequestConfig config = RequestConfig.custom().setProxy(proxy).setConnectTimeout(7000).setConnectionRequestTimeout(7000)
                .setSocketTimeout(7000).build();
        if ("Get".equals(this.Type)){
            httpGet.setConfig(config);
        }else{
            httpPost.setConfig(config);
        }


    }
}
