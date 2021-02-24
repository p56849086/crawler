package util;


import com.squareup.okhttp.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;

/**
 * 作者：程存淦
 * 功能：获取post请求的html
 * 2020年7月6日09:36:10
 */
public class GetHtml_Post {
    static String qiyunLink = "http://tunnel-api.apeyun.com/q?id=2120072300001201504&secret=ASySTfjht9VuRUh9&limit=1&format=txt&auth_mode=hand";
    final static String proxyUser = "2120072300001201504";
    final static String proxyPass = "ASySTfjht9VuRUh9";
    //投资者关系互动平台最新回复
    public static String getHtml_platform(String url, String pid, String beginDate, String endDate, String page, String row){
        RequestBody body=new FormEncodingBuilder()
                .add( "companyBaseinfoId",pid )
                .add("questionerTimeBegin",beginDate)
                .add("questionerTimeEnd",endDate)
                .add("isPagination","1")
                .add("rows",row)
                .add("page",page)
                .build();
        while (true){
            Request request=new Request.Builder()
                    .post( body )
                    .url( url )
                    .build();
            Call call = getQiyunIP.client.newCall(request);
            Response response=null;
            try {
                response = call.execute();
                String string = response.body().string();
                if (string==null || "".equals(string)){
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
                }else {
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


    //上证e互动获取时间段问答总数
    public static String getHtml_getNewDataCount(String url,String uid,String beginDate,String endDate){
        RequestBody body=new FormEncodingBuilder()
                .add("sdate",beginDate)
                .add("edate",endDate)
                .add("keyword", "")
                .add("type", "1")
                .add("comId", uid)
                .build();
        while (true){
            Request request=new Request.Builder()
                    .post( body )
                    .url( url )
                    .build();
            Call call = getQiyunIP.client.newCall(request);
            Response response=null;
            try {
                response = call.execute();
                String string = response.body().string();
                if (string==null || "".equals(string)){
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
                }else {
                    return string;
                }
            } catch (IOException e) {
                e.printStackTrace();
                if ("timeout".equals(e.getMessage()))
                    continue;
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

    //上证e互动获取时间段问答信息
    public static String getHtml_getNewData(String url, String page,String uid,String beginDate,String endDate){
        RequestBody body=new FormEncodingBuilder()
                .add("sdate",beginDate)
                .add("edate",endDate)
                .add("keyword", "")
                .add("type", "1")
                .add("comId", uid)
                .add("page",page)
                .build();
        while (true){
            Request request=new Request.Builder()
                    .post( body )
                    .url( url )
                    .build();
            Call call = getQiyunIP.client.newCall(request);
            Response response=null;
            try {
                response = call.execute();
                String string = response.body().string();
                if (string==null || "".equals(string)){
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
                }else {
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
