package util;
import com.squareup.okhttp.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;

/**
 * 作者：程存淦
 * 功能：获取get请求的html
 * 2020年7月6日09:36:10
 */
public class GetHtml {
    static String qiyunLink = "http://tunnel-api.apeyun.com/q?id=2120072300001201504&secret=ASySTfjht9VuRUh9&limit=1&format=txt&auth_mode=hand";
    // 代理隧道验证信息
    final static String proxyUser = "2120072300001201504";
    final static String proxyPass = "ASySTfjht9VuRUh9";
    //深交所问答信息
    public static String getHtml_shen(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.97 Safari/537.36")
                .addHeader("Host", "irm.cninfo.com.cn")
                .addHeader("Accept", "application/json, text/javascript, */*; q=0.01")
                .addHeader("Cookie", "ctx=/ircs; route=5b5a1bf6e97e3db6d9fdd6c70ad993f1; JSESSIONID=94fea1a4-aeb9-4fa4-ac7f-01991500dfb0")
                .build();
        boolean flag = false;
        try {
            Response response = getQiyunIP.client.newCall(request).execute();
            if (response.isSuccessful()) {
                flag = true;
                String html = response.body().string();
                return html;
            }
        } catch (IOException e) {
            while (flag == false) {
                try {
                    Response response = getQiyunIP.client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        flag = true;
                        String html = response.body().string();
                        return html;
                    }
                } catch (IOException e1) {
                    flag = false;
                }
            }
        }
        return null;
    }

    /**
     * 访问链接获取Html
     */
    public synchronized static String getHtml(String url) {
        while (true) {
            Request request = new Request.Builder().url(url)
                    .build();
            Call call = getQiyunIP.client.newCall(request);
            Response response = null;
            try {
                response = call.execute();
                String string = response.body().string();
                if (string == null || "".equals(string)) {
                    String qiyunIP = null;
                    try {
                        qiyunIP = getQiyunIP.getIP(qiyunLink);
                    } catch (Exception exception) {
                        continue;
                    }
                    String ip = qiyunIP.split(":")[0];
                    String host = qiyunIP.split(":")[1];
                    Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("" + ip + "", Integer.parseInt(host)));
                    getQiyunIP.client= getQiyunIP.client.setProxy(proxy).setAuthenticator(new Authenticator() {
                        public Request authenticateProxy(Proxy proxy, Response response) throws IOException {
                            return null;
                        }
                        public Request authenticate(Proxy proxy, Response response) throws IOException {
                            System.out.println("Authenticating for response: " + response);
                            System.out.println("Challenges: " + response.challenges());
                            String credential = Credentials.basic(proxyUser, proxyPass);
                            return response.request().newBuilder()
                                    .header("Authorization", credential)
                                    .build();
                        }
                    });
                    continue;
                } else {
                    return string;
                }
            } catch (IOException e) {
                e.printStackTrace();
                String qiyunIP = null;
                try {
                    qiyunIP = getQiyunIP.getIP(qiyunLink);
                } catch (Exception exception) {
                    continue;
                }
                String ip = qiyunIP.split(":")[0];
                String host = qiyunIP.split(":")[1];
                Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("" + ip + "", Integer.parseInt(host)));
                getQiyunIP.client= getQiyunIP.client.setProxy(proxy).setAuthenticator(new Authenticator() {
                    public Request authenticateProxy(Proxy proxy, Response response) throws IOException {
                        return null;
                    }
                    public Request authenticate(Proxy proxy, Response response) throws IOException {
                        System.out.println("Authenticating for response: " + response);
                        System.out.println("Challenges: " + response.challenges());
                        String credential = Credentials.basic(proxyUser, proxyPass);
                        return response.request().newBuilder()
                                .header("Authorization", credential)
                                .build();
                    }
                });
                continue;
            }
        }
    }

}
