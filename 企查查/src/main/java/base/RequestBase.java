package base;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class RequestBase {
    public HttpPost httpPost = new HttpPost();
    public HttpGet httpGet = new HttpGet();
    public CloseableHttpResponse response= null;
    public CloseableHttpClient httpClient = HttpClients.createDefault();
    public GetIp getIp;

    public RequestBase(GetIp getIp) {
        // 设置httpPost,HttpGet初始化
        this.getIp = getIp;
        if (getIp.getIp() != null && !"".equals(getIp.getIp())) {
            reProxyIp();
        } else {
            //设置超时时间
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(5000).setConnectionRequestTimeout(5000)
                    .setSocketTimeout(5000).build();
            // 设置HttpPost
            this.httpPost.setConfig(requestConfig);
            //  设置Httpget
            this.httpGet.setConfig(requestConfig);
        }
    }

    // 请求页面
    public String requestPage(){
        return null;
    }


    // 处理页面
    public Object dealPage() {
        return null;
    }



    protected void reProxyIp() {
        //修改HttpPost代理设置
        HttpHost proxy = new HttpHost(getIp.getIp(), Integer.parseInt(getIp.getPort()), "http");
        CredentialsProvider provider = new BasicCredentialsProvider();
        //包含账号密码的代理
        provider.setCredentials(new AuthScope(proxy), new UsernamePasswordCredentials("2120072300001201504", "ASySTfjht9VuRUh9"));
        httpClient = HttpClients.custom().setDefaultCredentialsProvider(provider).build();
        RequestConfig config = RequestConfig.custom().setProxy(proxy).setConnectTimeout(5000).setConnectionRequestTimeout(1000)
                .setSocketTimeout(5000).build();
        // 修改HttpPost代理设置
        httpPost.setConfig(config);
        // 修改HttpGet代理设置
        httpGet.setConfig(config);
    }
}
