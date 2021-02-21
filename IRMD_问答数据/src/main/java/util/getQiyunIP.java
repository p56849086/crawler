package util;

import com.squareup.okhttp.*;

import java.io.IOException;
import java.net.Proxy;

public class getQiyunIP {
    // 代理隧道验证信息
    final static String proxyUser = "2120072300001201504";
    final static String proxyPass = "ASySTfjht9VuRUh9";
    public static OkHttpClient client=new OkHttpClient();
    /**
     * 使用旗云代理更换ip
     * @param zhimaLink
     * @return
     */
    //如果出现js或者验证码的时候，进行切换ip
    public static String getIP(String zhimaLink) {
        System.out.println("切换ip");
        //获取IP的程序
        String data = "";
        try {
            Request request = new Request.Builder().url(zhimaLink).build();
            Response response = client.newCall(request).execute();
            client.setAuthenticator(new Authenticator() {
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
            if (response.isSuccessful()) {
                data = response.body().string().replace("\r\n", "");
                System.out.println(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}
