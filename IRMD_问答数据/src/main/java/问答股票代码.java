import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.squareup.okhttp.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *  网址：http://rs.p5w.net/company/  选择A股上市公司
 *  时间：2020年6月29日21:54:54
 *  total:3946
 */
public class 问答股票代码 {
    public static void main(String[] args) throws IOException {
        OkHttpClient client = new OkHttpClient();
        int sum=1;
        String url="http://ir.p5w.net/company/getCompanys.shtml";
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();
        for (int i = 0; i <354 ; i++) {
            RequestBody body=new FormEncodingBuilder()
                    .add("companyType","1")
                    .add("page", String.valueOf(i))
                    .add("rows","12")
                    .build();
            Request request = new Request.Builder()
                    .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like" +
                            " Gecko) Chrome/83.0.4103.97 Safari/537.36")
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .url(url)
                    .post(body)
                    .build();
            try {
                System.out.println("正在抓取第"+i+"页");
                Response response = client.newCall(request).execute();
                String html = response.body().string();
                JSONObject jsonObject = JSON.parseObject(html);
                JSONArray jsonArray = jsonObject.getJSONArray("obj");
                for (int i1 = 0; i1 < jsonArray.size(); i1++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i1);
                    String companyCode = jsonObject1.getString("companyCode");//股票代码
                    String companyShortname = jsonObject1.getString("companyShortname");//简称
                    String pid = jsonObject1.getString("pid");//pid
                    System.out.println(companyCode);
                    System.out.println(companyShortname);
                    System.out.println(pid);
                    XSSFRow row = sheet.createRow(sum);
                    row.createCell(0).setCellValue(companyCode);
                    row.createCell(1).setCellValue(companyShortname);
                    row.createCell(2).setCellValue(pid);
                    sum++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileOutputStream outputStream = new FileOutputStream("问答股票代码.xlsx");
        workbook.write(outputStream);
        outputStream.close();
    }
}
