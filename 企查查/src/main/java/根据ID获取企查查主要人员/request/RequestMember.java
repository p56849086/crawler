package 根据ID获取企查查主要人员.request;

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
import 根据ID获取企查查主要人员.entity.Company;

import java.awt.*;
import java.awt.event.InputEvent;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;

public class RequestMember extends RequestBase {

    public RequestMember(GetIp getIp) {
        super(getIp);
        httpGet.addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36");
        httpGet.addHeader("cookie", "_uab_collina=161121944583197740120769; QCCSESSID=8gm2ihh8d0in3iilmi5gkd9714; zg_did=%7B%22did%22%3A%20%221772429a48b4b8-0931a75256e806-230346c-1fa400-1772429a48caf2%22%7D; UM_distinctid=1772429a559b73-037aebacabaaf3-230346c-1fa400-1772429a55ba18; acw_tc=70310a3a16123135364192499e762c10eab517ad899ac7bb91376b82de; CNZZDATA1254842228=979756312-1611214385-%7C1612312875; hasShow=1; zg_de1d1a35bfa24ce29bbf2c7eb17e6c4f=%7B%22sid%22%3A%201612313541340%2C%22updated%22%3A%201612313567705%2C%22info%22%3A%201611913328914%2C%22superProperty%22%3A%20%22%7B%7D%22%2C%22platform%22%3A%20%22%7B%7D%22%2C%22utm%22%3A%20%22%7B%7D%22%2C%22referrerDomain%22%3A%20%22www.qcc.com%22%2C%22cuid%22%3A%20%22e5572a1150e267a971012088c4e6b3c4%22%2C%22zs%22%3A%200%2C%22sc%22%3A%200%7D");
    }

    // 控制重试次数
    int retry = 1;
    //方便直接打开页面
    String url;
    public String requestPage(String qccId) throws URISyntaxException, IOException {
        url = "https://www.qcc.com/firm/" + qccId + ".html#base";
        httpGet.setURI(new URI(url));
        // 循环请求保证出错可以重试
        int i = 0;
        while (true){
            if (i>=6)
                continue;
            try {
                response = httpClient.execute(httpGet);
                if(response.getStatusLine().getStatusCode() == 200) {
                    String html = EntityUtils.toString(response.getEntity(),"GB18030");
                    return html;
                }else {
                    i++;
                    System.out.println("请求代码：" + response.getStatusLine().getStatusCode() + "更换Ip开始重试");
                }
            } catch (SocketTimeoutException e){
                System.out.println(e.getMessage() + "，请求超时重试");
                i++;
                continue;
            } catch (Exception e) {
                System.out.println("请求基本信息表出错，开始重试");
                i++;
                continue;
            }finally {
                if (response != null){
                    EntityUtils.consumeQuietly(response.getEntity());
                }
            }
        }
    }

    public Company dealPage(String html) throws Exception {
        Document document = Jsoup.parse(html);
        Company company = new Company();
        if ("公司不存在".equals(document.select("p.error").text())){
            company.set股东信息("信息不存在");
            company.setPartnern("信息不存在");
            company.set主要人员("信息不存在");
            company.setMainmember("信息不存在");
            company.set对外投资("信息不存在");
            company.setTouzilist("信息不存在");
            return company;
        }
        // document.getElementById("qccGraph") == null && document.select(".company-nav-items") == null
        if (document.getElementById("qccGraph") == null && document.select(".company-nav-items").size()==0){
            if (retry >= 5){
                System.out.println("重试此时达到五次，停止重试");
            }
            openHtmlAndClick();
            retry++;
            // 启动浏览器，自动滑块
            System.out.println("程序出错");
            throw new Exception("程序出错，抛出异常");
        }

        // 程序正常，重试次数置为0；
        retry = 0;

        if (document.getElementById("partnern") != null){
            Element partnern = document.getElementById("partnern");
            company.setPartnern(partnern.toString());
            if (partnern.getElementsByClass("tbadge").isEmpty()){
                company.set股东信息(partnern.getElementsByClass("tabtitle active").text().replace("最新公示 ", ""));
            }else {
                company.set股东信息(partnern.getElementsByClass("tbadge").text());
            }
        }else {
            company.set股东信息("信息不存在");
            company.setPartnern("信息不存在");
        }

        if (document.getElementById("Mainmember") != null){
            Element mainmember = document.getElementById("Mainmember");
            company.setMainmember(mainmember.toString());
            if (mainmember.getElementsByClass("tbadge").isEmpty()){
                company.set主要人员(mainmember.getElementsByClass("tabtitle active").text().replace("最新公示 ", ""));
            }else {
                company.set主要人员(mainmember.getElementsByClass("tbadge").text());
            }
        }else {
            company.set主要人员("信息不存在");
            company.setMainmember("信息不存在");
        }

        if (document.getElementById("touzilist") != null){
            Element touzilist = document.getElementById("touzilist");
            company.setTouzilist(touzilist.toString());
            if (touzilist.getElementsByClass("tbadge").isEmpty()){
                company.set主要人员(touzilist.getElementsByClass("tabtitle active").text().replace("最新公示 ", ""));
            }else {
                company.set对外投资(touzilist.getElementsByClass("tbadge").text());
            }
        }else {
            company.set对外投资("信息不存在");
            company.setTouzilist("信息不存在");
        }


        if (company.get股东信息()==null || company.get股东信息().equals("") || company.get主要人员() == null || company.get主要人员().equals("") || company.get对外投资()==null || "".equals(company.get对外投资())){
            System.out.println("程序解析出错");
        }

        return company;
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


    public void openHtmlAndClick(){
        try {
            Thread.sleep(2000);
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
            Robot robot = new Robot();
            // 移动两次到指定位置
            robot.mouseMove(835,406);
            Thread.sleep(2000);
            robot.mouseMove(835,406);
            // 按住滑块
            robot.mousePress(InputEvent.BUTTON1_MASK);
            Thread.sleep(2000);
            // 移动滑块
            robot.mouseMove(1163,403);
            Thread.sleep(2000);
            robot.mouseMove(950, 470);
            Thread.sleep(2000);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);//释放左键
            // 点击验证
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);//释放左键
            // 等待三秒，页面加载
            Thread.sleep(3000);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (AWTException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
