package base;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

public class GetIp {
    private String Ip;
    private String Port;

    public void setIp(String ip) {
        Ip = ip;
    }

    public void setPort(String port) {
        Port = port;
    }

    public String getIp() {
        return Ip;
    }

    public String getPort() {
        return Port;
    }

    public void requestIp() throws URISyntaxException, IOException {
        String id = "2120072300001201504";
        String secret = "ASySTfjht9VuRUh9";
        String uri = "http://tunnel-api.apeyun.com/q";

        CloseableHttpClient httpclient = HttpClients.createDefault();

        URIBuilder builder = new URIBuilder(uri);
        builder
                .setParameter("id", id)
                .setParameter("secret", secret)
                .setParameter("format", "json")
                .setParameter("auth_mode", "auto")
                .setParameter("limit", "1");

        HttpGet httpGet = new HttpGet(builder.build());

        CloseableHttpResponse response = httpclient.execute(httpGet);

        HttpEntity httpEntity = response.getEntity();
        ProxyResult proxyResult;
        try {
            Reader reader = new InputStreamReader(httpEntity.getContent());
            proxyResult = new Gson().fromJson(reader, ProxyResult.class);
        } finally {
            response.close();
        }

        if ( proxyResult == null || proxyResult.getCode() != 200 ) {
            System.out.println("no proxies");
            return;
        }
        List<String> list = Arrays.asList(proxyResult.getData().toString().split(","));
        this.Ip = list.get(1).split("=")[1];
        this.Port = list.get(2).split("=")[1].replace(".0","");
        System.out.println("换取ip:" + Ip + Port);
    }
}

class ProxyResult {
    private int code;
    private String error;
    private List data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data= data;
    }
}