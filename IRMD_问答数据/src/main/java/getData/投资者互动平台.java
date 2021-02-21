package getData;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.MongoCollection;
import dao.GetMongoDBConnect;
import dao.SaveDataToMongoDB;
import entity.ProblemInfo;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import util.GetHtml_Post;
import util.GetOneMonthDate;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 公司：上海经禾信息技术有限公司
 * 作者：程存淦
 * 功能：获取投资者关系平台问答信息
 * 时间：2020年7月1日15:47:03
 * 抓取完成后，对所有字段进行去重处理
 */
public class 投资者互动平台 {
    public static int rowNumber = 0;
    public static String[] chineseNames = {"股票代码", "公司简称", "浏览用户/注册用户", "用户名", "提问时间", "提问内容", "上市公司是否回复", "回复时间", "回复内容"};
//    public static String[] yearArray = {"2018"};
//    public static String [] monthArray={"01"};
    public static String [] yearArray={"2019", "2020"};
    public static String [] monthArray={"01","02","03","04","05","06","07","08","09","10","11","12"};
    public static void main(String[] args) throws IOException {
        getData();
    }
//   正在获取第 -3479- 家公司问答信息！共 -3948- 家公司！当前公司代码：688222
    private static void getData() throws IOException {
        GetMongoDBConnect.collectionName = "投资者互动平台数据_2019_2020";
        MongoCollection collection = GetMongoDBConnect.getConnection();
        InputStream input = new FileInputStream( "问答股票代码.xlsx");
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook( input );
        XSSFSheet xssfSheet = xssfWorkbook.getSheetAt( 0 );
        int rowCount = xssfSheet.getLastRowNum();
        XSSFRow xssfRow = null;
        for (int i = 1; i <= rowCount; i++) {
            xssfRow = xssfSheet.getRow(i);
            String stockCode = xssfRow.getCell( 0 ).getStringCellValue();//公司代码
            String pid = xssfRow.getCell( 2 ).getStringCellValue();//pid
            System.err.println( "正在获取第 -" + i + "- 家公司问答信息！共 -" + rowCount+ "- 家公司！当前公司代码：" + stockCode );
            for (int j = 0; j < yearArray.length; j++) {
                String year = yearArray[j];
                for (int n = 0; n < monthArray.length; n++) {
                    String month = monthArray[n];
                    String[] dateArray = GetOneMonthDate.getOneMonthDate( year, month );
                    String beginDate = dateArray[0];
                    String endDate = dateArray[1];
                    int query = 0;
                    getProblemData(stockCode, beginDate, endDate, query, collection, pid);
                    query = 1;
                    getProblemData( stockCode, beginDate, endDate, query, collection, pid );
                }

            }
        }
    }

    private static void getProblemData(String stockCode,String beginDate,String endDate,int query,MongoCollection collection,String pid) {
        String url="";
        int rowNumber;
        if(query==0){
            url="http://rs.p5w.net/interaction/getNewR.shtml";
            rowNumber = 10;
        }else{
            url="http://rs.p5w.net/interaction/getNewQ.shtml";
            rowNumber = 20;
        }
        String jsonString= GetHtml_Post.getHtml_platform(url, pid, beginDate, endDate,"0", String.valueOf(rowNumber));
        JSONObject jsonObj= JSON.parseObject(jsonString);
        int total = jsonObj.getInteger("total");//最新回复的条数
        int page = (total / 10) + 1;//多少页
        for (int i = 0; i <page; i++) {
            jsonString= GetHtml_Post.getHtml_platform(url, pid, beginDate, endDate, String.valueOf(i), String.valueOf(rowNumber));
            jsonObj= JSON.parseObject(jsonString);
            JSONArray jsonArray=jsonObj.getJSONArray("rows");
            if (jsonArray.size()>0){
                System.out.println("有数据的时间段："+beginDate+"~"+endDate);
            }
            for(int k=0;k<jsonArray.size();k++){
                JSONObject oneObj=jsonArray.getJSONObject(k);
                rowNumber++;
                ProblemInfo entityObj=new ProblemInfo();
                List<Object> entityValueList = new ArrayList<Object>();
                entityValueList.add(stockCode);
                String Name=oneObj.getString("companyShortname");
                entityValueList.add(Name);
                String userName=oneObj.getString("nickname");
                String visiterOrFormal="";
                if(userName.contains("浏览用户")){
                    visiterOrFormal="浏览用户";
                }else{
                    visiterOrFormal="注册用户";
                }
                entityValueList.add(visiterOrFormal);
                entityValueList.add(userName);
                String questionTime=oneObj.getString("questionerTimeStr");
                entityValueList.add(questionTime);
                String questionContent=oneObj.getString("content");
                entityValueList.add(questionContent);
                String isRequest="未回复";
                String requestTime="";
                String requestContent="";
                if(query==0){
                    isRequest="已回复";
                    requestTime=oneObj.getString("replyerTimeStr");
                    requestContent=oneObj.getString("replyContent");
//                if(requestContent.contains("(来自：")){
//                    continue;
//                }
                }
                entityValueList.add(isRequest);
                entityValueList.add(requestTime);
                entityValueList.add(requestContent);
                SaveDataToMongoDB.saveData(entityValueList,chineseNames,collection);
            }
        }
    }
}
