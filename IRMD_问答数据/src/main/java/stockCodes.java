import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.squareup.okhttp.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *  网址：http://irm.cninfo.com.cn/ircs/index/queryKeyboardInfo   请求方式POST  参数keyWord: 000926
 *  时间：2020年7月2日16:27:24
 *
 */
public class stockCodes {
    public static void main(String[] args) throws IOException {
        OkHttpClient client = new OkHttpClient();
        int sum=1;
        String url="http://irm.cninfo.com.cn/ircs/index/queryKeyboardInfo";
        InputStream input = new FileInputStream( "问答股票代码.xlsx" );
        XSSFWorkbook workbook = new XSSFWorkbook(input);
        XSSFSheet sheet = workbook.getSheetAt(0);
        XSSFWorkbook workbook1 = new XSSFWorkbook();
        XSSFSheet sheet1 = workbook1.createSheet();
        int rowNum = sheet.getLastRowNum();
        for (int i = 1; i <=rowNum; i++) {
            XSSFRow row = sheet.getRow(i);
            String Code = row.getCell(0).getStringCellValue();
            RequestBody body=new FormEncodingBuilder()
                    .add("keyWord",Code)
                    .build();
            Request request = new Request.Builder()
                    .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.97 Safari/537.36")
                    .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                    .url(url)
                    .post(body)
                    .build();
            try {
                System.out.println("正在抓取第"+i+"个");
                Response response = client.newCall(request).execute();
                String html = response.body().string();
                JSONObject jsonObject = JSON.parseObject(html);
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                String stockCode = null;
                String shortName = null;
                String secid = null;
                String secid_stockCode= null;
                XSSFRow row1 = sheet1.createRow(sum);
                if (jsonArray.size()==0){
                     stockCode = Code;
                     shortName = "";
                     secid = "";
                     secid_stockCode="";
                     System.out.println(secid);
                     System.out.println(shortName);
                     System.out.println(stockCode);
                     System.out.println(secid_stockCode);
                     row1.createCell(0).setCellValue(stockCode);
                     row1.createCell(1).setCellValue(shortName);
                     row1.createCell(2).setCellValue(secid);
                     row1.createCell(3).setCellValue(secid_stockCode);
                     row1.createCell(4).setCellValue(0);
                     sum++;
                }else {
                    for (int i1 = 0; i1 < jsonArray.size(); i1++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i1);
                        stockCode = jsonObject1.getString("stockCode");//股票代码
                        shortName = jsonObject1.getString("shortName");//简称
                        secid = jsonObject1.getString("secid");//secid
                        secid_stockCode =secid+"_"+stockCode;
                        System.out.println(secid);
                        System.out.println(shortName);
                        System.out.println(stockCode);
                        System.out.println(secid_stockCode);
                        row1.createCell(0).setCellValue(stockCode);
                        row1.createCell(1).setCellValue(shortName);
                        row1.createCell(2).setCellValue(secid);
                        row1.createCell(3).setCellValue(secid_stockCode);
                        row1.createCell(4).setCellValue(1);
                        sum++;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileOutputStream outputStream = new FileOutputStream("stockCodes.xlsx");
        workbook1.write(outputStream);
        outputStream.close();
    }
}
