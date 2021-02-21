import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.squareup.okhttp.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileOutputStream;
import java.io.IOException;
public class 上证e互动股票代码 {
    public static void main(String[] args) throws IOException {
        int sum=1;
        OkHttpClient client = new OkHttpClient();
        String url="http://sns.sseinfo.com/allcompany.do";
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();
        for (int i = 1; i <=59 ; i++) {
            RequestBody body=new FormEncodingBuilder()
                    .add("code", "0")
                    .add("order", "0")
                    .add("areaId", "0")
                    .add("page", String.valueOf(i))
                    .build();
            Request request = new Request.Builder()
                    .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.75 Safari/537.36")
                    .url(url)
                    .post(body)
                    .build();
                System.out.println("正在抓取第"+i+"页");
                Response response = client.newCall(request).execute();
                String htmlStr = response.body().string();
                JSONObject jsonObj = JSON.parseObject(htmlStr);
                String html = jsonObj.getString("content").replace("\t", "").trim();
                Document doc = Jsoup.parse(html);
                Elements divElem = doc.select(".companyBox");
                for (int n = 0; n < divElem.size(); n++) {
                    Element divEle = divElem.get(n);
                    String stockCode = divEle.select("a+div").first().text().trim();
                    String uid = divEle.select("a").attr("uid");
                    String stockName = divEle.select("a").attr("title");
                    System.out.println(stockCode);
                    System.out.println(uid);
                    System.out.println(stockName);
                    XSSFRow row = sheet.createRow(sum);
                    row.createCell(0).setCellValue(stockCode);
                    row.createCell(1).setCellValue(uid);
                    row.createCell(2).setCellValue(stockName);
                    sum++;
                }
        }
        FileOutputStream outputStream = new FileOutputStream("上证e互动股票代码.xlsx");
        workbook.write(outputStream);
    }
}
