package getData;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.client.MongoCollection;
import dao.GetMongoDBConnect;
import dao.SaveDataToMongoDB;
//import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import util.GetHtml;
import util.GetOneMonthDate;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class 深交所问答信息 {
    public static String [] chineseNames={"股票代码","公司简称","浏览用户/注册用户","用户名","提问时间","提问内容","上市公司是否回复","回复时间","回复内容"};
    public static String [] yearArray={"2019", "2020"};
    public static String [] monthArray={"01","02","03","04","05","06","07","08","09","10","11","12"};
    public static void main(String[] args) throws IOException {
        getData();
    }

    private static void getData() throws IOException {
        GetMongoDBConnect.collectionName = "深交所问答信息数据_2019_2020";
        MongoCollection collection = GetMongoDBConnect.getConnection();
        InputStream input = new FileInputStream( "stockCodes.xlsx");
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook( input );
        XSSFSheet xssfSheet = xssfWorkbook.getSheetAt( 0 );
        int rowCount = xssfSheet.getLastRowNum();
        XSSFRow xssfRow = null;
        for (int i = 1; i <= rowCount; i++) {
            xssfRow = xssfSheet.getRow(i);
            String stockCode = xssfRow.getCell(0).getStringCellValue();//公司代码
            String secid_stockCode = xssfRow.getCell(3).getStringCellValue();//公司代码
//            xssfRow.getCell(4).setCellType(CellType.STRING);
            String flag = xssfRow.getCell(4).getStringCellValue();//判断stockCodes参数是否为空
            System.err.println("正在获取第 -" + i + "- 家公司问答信息！共 -" + rowCount + "- 家公司！当前公司代码：" + stockCode);
            for(int y=0;y<yearArray.length;y++) {
                String year = yearArray[y];
                if (y==0){
                    for (int n = 0; n < monthArray.length; n++) {
                        String month = monthArray[n];
                        String[] dateArray = GetOneMonthDate.getOneMonthDate( year, month );
                        String beginDate = dateArray[0]+" 00:00:00";
                        String endDate = dateArray[1]+" 23:59:59";
                        //当读取stockCodes表格的第四个单元格值为0时，代表stockCodes参数为空keywords参数有值
                        //当读取stockCodes表格的第四个单元格值为1时，代表stockCodes参数有值keywords参数为空
                        if (flag.equals("1")){
                            stockCode="";
                            getAllData( secid_stockCode, stockCode,beginDate, endDate, collection );
                        }else {
                            getAllData( secid_stockCode,stockCode, beginDate, endDate, collection );
                        }

                    }
                }else {
                    for (int n = 0; n < monthArray.length; n++) {
                        String month = monthArray[n];
                        String[] dateArray = GetOneMonthDate.getOneMonthDate( year, month );
                        String beginDate = dateArray[0]+" 00:00:00";
                        String endDate = dateArray[1]+" 23:59:59";
                        //当读取stockCodes表格的第四个单元格值为0时，代表stockCodes参数为空keywords参数有值
                        //当读取stockCodes表格的第四个单元格值为1时，代表stockCodes参数有值keywords参数为空
                        if (flag.equals("1")){
                            stockCode="";
                            getAllData( secid_stockCode, stockCode,beginDate, endDate, collection );
                        }else {
                            getAllData( secid_stockCode,stockCode, beginDate, endDate, collection );
                        }
                    }
                }
            }
        }
    }

    private static void getAllData(String secid_stockCode, String stockCode, String beginDate, String endDate, MongoCollection collection) throws IOException {
        String url="http://irm.cninfo.com.cn/ircs/search/searchResult?stockCodes="+secid_stockCode+"&keywords="+stockCode+"&infoTypes=1%2C11&startDate="+beginDate+"&endDate="+endDate+"&onlyAttentionCompany=2&pageNum=1&pageSize=10";
        String jsonString = GetHtml.getHtml_shen(url);
        JSONObject jsonObj= JSON.parseObject(jsonString);
        int totalPage = jsonObj.getInteger("totalPage");//最新回复的条数
        for (int i = 1; i <=totalPage; i++) {
             url="http://irm.cninfo.com.cn/ircs/search/searchResult?stockCodes="+secid_stockCode+"&keywords="+stockCode+"&infoTypes=1%2C11&startDate="+beginDate+"&endDate="+endDate+"&onlyAttentionCompany=2&pageNum="+i+"&pageSize=10";
            jsonString = GetHtml.getHtml_shen(url);
            jsonObj= JSON.parseObject(jsonString);
            JSONArray jsonArray=jsonObj.getJSONArray("results");
            if (jsonArray.size()>0){
                System.out.println("有数据的时间段："+beginDate+"~"+endDate);
            }
            for(int k=0;k<jsonArray.size();k++){
                JSONObject oneObj=jsonArray.getJSONObject(k);
                List<Object> entityValueList = new ArrayList<Object>();
                String Code = oneObj.getString("stockCode");
                entityValueList.add(Code);
                String Name= oneObj.getString("companyShortName");
                entityValueList.add(Name);
                String userName= oneObj.getString("authorName");
                String visiterOrFormal="";
                if(userName.contains("浏览用户")){
                    visiterOrFormal="浏览用户";
                }else{
                    visiterOrFormal="注册用户";
                }
                entityValueList.add(visiterOrFormal);
                entityValueList.add(userName);
                String pubDate = oneObj.getString("pubDate");
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String questionTime = sdf.format(new Date(Long.parseLong(pubDate)));      // 时间戳转换成时间
                entityValueList.add(questionTime);
                String mainContent = GetHtmlText(oneObj.getString("mainContent"));
                entityValueList.add(mainContent);
                String isRequest="";
                String requestTime="";
                String requestContent="";
                String attachedPubDate="";
                requestContent = oneObj.getString("attachedContent");
                attachedPubDate = oneObj.getString("attachedPubDate");
                if (requestContent==null || attachedPubDate==null){
                    isRequest="未回复";
                    requestTime="";
                    requestContent="";
                }else {
                    isRequest="已回复";
                    sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    requestTime = sdf.format(new Date(Long.parseLong(attachedPubDate)));      // 时间戳转换成时间
                }
                entityValueList.add(isRequest);
                entityValueList.add(requestTime);
                entityValueList.add(requestContent);
                SaveDataToMongoDB.saveData(entityValueList,chineseNames,collection);
            }
        }
    }
    /**
     * 去掉所有的HTML,获取其中的文本信息
     *
     * @param htmlText
     * @return
     */
    public static String GetHtmlText(String htmlText) {
        String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);//启用不区分大小写的匹配
        Matcher m_html = p_html.matcher(htmlText);
        htmlText = m_html.replaceAll(""); // 过滤HTML标签
        return htmlText;
    }
}
