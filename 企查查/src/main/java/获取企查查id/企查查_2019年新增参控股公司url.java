package 获取企查查id;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bson.Document;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URLEncoder;
import java.util.*;

public class 企查查_2019年新增参控股公司url {
    public static Map<String, String> map = new LinkedHashMap<String, String>();

    public static void main(String[] args) {
        //获取公司名称
        String path = "E:\\2020年爬虫\\章萍\\CPCD\\企查查20200721\\src\\main\\java\\2019年新增参控股公司.xlsx";
        List<String> companyName = getCompanyName(path);
        //将公司名称及对应的url保存
        Map<String, String> company_Url = getCompanyUrl(companyName);
    }

    private static Map<String,String> getCompanyUrl(List<String> company_Name) {
        MongoClient mongoClient = new MongoClient("192.168.1.175", 27017);
        MongoCollection<Document> collection = mongoClient.getDatabase("企查查_章萍").getCollection("企查查");
        MongoCollection<Document> collection1 = mongoClient.getDatabase("企查查_章萍").getCollection("企查查_无此公司");
        OkHttpClient client = new OkHttpClient();
        int sum = 18269;
        try {
            ArrayList<String> encodeURLList = new ArrayList<String>();
            Map<String, String> companyUrl = new LinkedHashMap<String, String>();
            int flag=0;
            for (String companyName : company_Name) {
                //将中文编码解码为url码
                String url = URLEncoder.encode(companyName, "utf-8");
                encodeURLList.add(url);
                map.put(url, companyName);
            }
            for (String encodeURL : encodeURLList) {
                //设置一个随机休眠的时间，以防被检测到
                Random random = new Random();
                int random_time = random.nextInt(30);//平时30
                Thread.sleep(random_time + 10 * 900);//平时35
                //设置cookie。如果被封或者需要滑动滑块，这里的cookie需要变动
                String cookie = "QCCSESSID=ptsh2ele6mv4r9ln8g9p9kkgl5; UM_distinctid=17369dd1eee298-060928101d3062-7a1b34-1fa400-17369dd1eefb2f; zg_did=%7B%22did%22%3A%20%2217369dd1f80384-01b7868b5a3a45-7a1b34-1fa400-17369dd1f81bb1%22%7D; _uab_collina=159520896605086835391022; Hm_lvt_78f134d5a9ac3f92524914d0247e70cb=1595208966,1595211847; acw_tc=65e21c2a15955576634045065e5b6c7ed24634befe3462b919c00ab63e; CNZZDATA1254842228=2041573327-1595206928-https%253A%252F%252Fwww.baidu.com%252F%7C1595555086; hasShow=1; zg_de1d1a35bfa24ce29bbf2c7eb17e6c4f=%7B%22sid%22%3A%201595558120664%2C%22updated%22%3A%201595558137472%2C%22info%22%3A%201595208966025%2C%22superProperty%22%3A%20%22%7B%7D%22%2C%22platform%22%3A%20%22%7B%7D%22%2C%22utm%22%3A%20%22%7B%7D%22%2C%22referrerDomain%22%3A%20%22%22%2C%22cuid%22%3A%20%22089132baa48194586f056a6fea1d5129%22%2C%22zs%22%3A%200%2C%22sc%22%3A%200%7D; Hm_lpvt_78f134d5a9ac3f92524914d0247e70cb=1595558138";
                Request request = new Request.Builder().url("https://www.qichacha.com/search?key=" + encodeURL)
                        .addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.75 Safari/537.36")
                        .addHeader("cookie", cookie)
                        .build();
                Response response = client.newCall(request).execute();
                String url = null;
                String company = null;
                String path = "https://www.qcc.com/";
                sum++;
                String html = response.body().string();
                Document doc11 = new Document();
                org.jsoup.nodes.Document doc1 = Jsoup.parse(html);
                Elements elements = doc1.select("#countOld span");
                if (!elements.text().equals("0")) {
                    elements = doc1.select(".m_srchList tbody tr");
                    if (elements.size() == 0) {
                         System.out.println("出现您的操作过于频繁，验证后再操作或者被封账号了");
                    }
                    for (Element element : elements) {
                        //获取第一个公司名称作为匹配到的公司，并保存下它的名称及网址
                        url = element.getElementsByIndexEquals(0).select("a").first().attr("href");
                        System.out.println("正在获取第__" + sum + "__条数据-" + map.get(encodeURL) + "__URL--->" + path + url + "#base");
                        company = element.getElementsByIndexEquals(0).select("a").first().text();
                        companyUrl.put(company, path + url + "#base");
                        doc11.append("_id",map.get(encodeURL));
                        doc11.append("new_title",company);
                        doc11.append("基础信息_url",path + url + "#base");
                        doc11.append("法律诉讼_url",path + url + "#susong");
                        //再次请求获取基本信息首页html(因为含有工商信息、股东信息、主要人员、对外投资)
                        Request request1 = new Request.Builder().url(path + url + "#base")
                                .addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.75 Safari/537.36")
                                .addHeader("cookie", cookie)
                                .build();
                        Response response1 = client.newCall(request1).execute();
                        String html1 = response1.body().string();
                        doc11.append("基础信息_html",html1.replace("\"",""));
                        collection.insertOne(doc11);
                        break;
                    }
                } else {
                    //如果没被找到，则返回原来的公司，以及无此公司的标志
                    flag++;
                    companyUrl.put(String.valueOf(flag),"无此公司");
                    System.out.println("正在获取第__"+sum+"__条数据-"+map.get(encodeURL)+"--->"+path+url);
                    doc11.append("_id",map.get(encodeURL));
                    doc11.append("new_title","无此公司");
                    collection1.insertOne(doc11);
                }

            }
            System.out.println("公司URL获取完毕");
            return companyUrl;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static List<String> getCompanyName(String path) {
        //获取公司列表
        try {
            InputStream inputStream = new FileInputStream(path);
            XSSFWorkbook wwb = new XSSFWorkbook(inputStream);
            List<String> companyNameList = new ArrayList<String>();
            XSSFSheet xssfSheet = wwb.getSheetAt(0);
            for (int i = 18269; i <= xssfSheet.getLastRowNum(); i++) {
                //出现操作频繁，验证后再操作，需要滑一下，相等于出现了重定向：在数据库查询db.getCollection('企查查').find({"基础信息_html":/<script>window.location.href/})删除即可，然后从这条数据开始接着跑
                //如果账号被封，需要切换账号
                companyNameList.add(xssfSheet.getRow(i).getCell(0) + "".trim());
            }
            System.out.println(companyNameList.size());
            return companyNameList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
