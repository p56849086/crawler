package getData;
import com.mongodb.client.MongoCollection;
import dao.GetMongoDBConnect;
import dao.SaveDataToMongoDB;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import util.GetHtml;
import util.GetHtml_Post;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 公司：上海经禾信息技术有限公司
 * 作者：程存淦
 * 功能：获取上证e互动问答信息
 * 时间：2020年7月7日09:06:21
 */
public class 上证e互动问答信息 {
    public static String[] chineseNames = {"股票代码", "公司简称", "浏览用户/注册用户", "用户名", "提问时间", "提问内容", "上市公司是否回复", "回复时间", "回复内容"};
    public static String noResponseHtml = "";
    public static String haveResponseHtml = "";
    public static String newDataHtml = "";
    public static String [] yearArray={"2019", "2020"};
    public static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    public static void main(String[] args) {
        getData();
    }

    private static void getData() {
        GetMongoDBConnect.collectionName = "上证e互动问答信息_2019_2020";
        MongoCollection collection = GetMongoDBConnect.getConnection();
        String newDataUrl = "http://sns.sseinfo.com/getNewData.do";
        String countUrl = "http://sns.sseinfo.com/getNewDataCount.do";
        try {
            InputStream input = new FileInputStream( "上证e互动股票代码.xlsx" );
            XSSFWorkbook workbook = new XSSFWorkbook(input);
            XSSFSheet sheet = workbook.getSheetAt(0);
            int rowNum = sheet.getLastRowNum();
            for (int i = 1; i <=rowNum; i++) {
                XSSFRow row = sheet.getRow(i);
                row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                String stockCode = row.getCell(0).getStringCellValue();
                String uid = row.getCell(1).getStringCellValue();
                String stockName = row.getCell(2).getStringCellValue();
                System.err.println("正在获取第 -" + i + "- 家公司问答信息！共 -" + rowNum + "- 家公司！当前公司代码+uid：" + stockCode + "+" + uid);
                for (int j = 0; j < yearArray.length; j++) {
                    String year = yearArray[j];
                    String beginDate =year+"-01-01";
                    String endDate=year+"-12-31";
                    String itemCountStr = GetHtml_Post.getHtml_getNewDataCount(countUrl, uid, beginDate, endDate);
                    while (itemCountStr == null) {
                        itemCountStr = GetHtml_Post.getHtml_getNewDataCount(countUrl, uid, beginDate, endDate);
                    }
                    int itemCount = Integer.parseInt(itemCountStr);
                    int pageCount = (itemCount - 1) / 10 + 1;
                    for (int x = 1; x <= pageCount; x++) {
                        newDataHtml = GetHtml_Post.getHtml_getNewData(newDataUrl, x + "", uid, beginDate, endDate);
                        if (newDataHtml.contains("访问出错") || newDataHtml.contains("暂时没有问答内容")) {
                            System.out.println(year+"----访问出错---或者---暂时没有问答内容"+"第"+x+"页");
                            continue;
                        }
                        while (newDataHtml == null) {
                            newDataHtml = GetHtml_Post.getHtml_getNewData(newDataUrl, x + "", uid, beginDate, endDate);
                        }
                        getQuestionData(newDataHtml, stockCode, stockName, year, collection,uid);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getQuestionData(String html, String stockCode, String stockName, String year, MongoCollection collection,String uid) {
        Document doc = Jsoup.parse(html);
        Elements itemElem = doc.select(".m_feed_item");//会出现500错误，导致itemElem.size等于0
        if (itemElem.size()>0){
            for (int i = 0; i < itemElem.size(); i++) {
                Element itemEle = itemElem.get(i);
                String itemId = itemEle.attr("id");
                List<Object> dataValueList = new ArrayList<Object>();
                dataValueList.add(stockCode);
                dataValueList.add(stockName);
                Element askInfoEle = itemEle.select(".m_feed_detail").get(0);
                String userName = askInfoEle.select(".m_feed_face").text().trim();
                String visiterOrFormal = "";
                if (userName.contains("浏览用户")) {
                    visiterOrFormal = "浏览用户";
                } else {
                    visiterOrFormal = "注册用户";
                }
                dataValueList.add(visiterOrFormal);
                dataValueList.add(userName);
                int haveResponseSize = itemEle.select(".m_qa_detail").size();
                int resFlag = 0;//代表没有回复
                if (haveResponseSize > 0) {
                    resFlag = 1;
                }
                String questionTime = getTime(itemId, year, resFlag,uid);//提问时间
                String questionAText = askInfoEle.select(".m_feed_cnt .m_feed_txt a").text();//被问人：如:美凯龙(601828)
                String questionContent = "";
                String s="";
                try {
                    s = questionTime.split("-")[0]+"-"+questionTime.split("-")[1]+"-"+questionTime.split("-")[2];
                    questionTime=s;
                } catch (Exception e) {
                    questionTime = questionTime.split("-")[0]+"-".replace("-", "年");
                }
                if (questionAText.contains("#")) {//被问人带有#
                    questionContent = askInfoEle.select(".m_feed_cnt .m_feed_txt").text().trim();
                } else {
                    questionContent = askInfoEle.select(".m_feed_cnt .m_feed_txt").text().replace(questionAText, "").trim();//提问内容
                }
                String isRequest = "未回复";
                String requestTime = "";
                String requestContent = "";
                if (haveResponseSize > 0) {
                    isRequest = "已回复";
                    Element responseInfoEle = itemEle.select(".m_feed_detail.m_qa").get(0);
                    String requestTimeStr = responseInfoEle.select(".m_feed_from span").text();//回复时间
                    requestTime = getDate(requestTimeStr, year);
                    if (questionTime.length() > 5 && requestTime.length() > 5) {
                        Boolean bool = compareDate(questionTime, requestTime);
                        if (bool) {
                            questionTime = questionTime.replace(year, (Integer.parseInt(year) - 1) + "");
                        }
                    }
                    requestContent = responseInfoEle.select(".m_feed_cnt .m_feed_txt").text().trim();//回复内容
                }

                dataValueList.add(questionTime);
                dataValueList.add(questionContent);
                dataValueList.add(isRequest);
                dataValueList.add(requestTime);
                dataValueList.add(requestContent);
                SaveDataToMongoDB.saveData(dataValueList, chineseNames, collection);
            }
        }

    }

    private static String getNoResponseHtml(String uid,int i) {//无回复
        String noResponseUrl = "http://sns.sseinfo.com/ajax/userfeeds.do?type=10&uid=" + uid + "&pageSize=500&typeCode=company&page="+i+"";
        String noResponseHtml = GetHtml.getHtml(noResponseUrl);
        while (noResponseHtml == null) {
            noResponseHtml = GetHtml.getHtml(noResponseUrl);
        }
        return noResponseHtml;
    }


    private static String getHaveResponseHtml(String uid,int i) {//无回复
        String noResponseUrl = "http://sns.sseinfo.com/ajax/userfeeds.do?type=11&uid=" + uid + "&pageSize=500&typeCode=company&page="+i+"";
        String noResponseHtml = GetHtml.getHtml(noResponseUrl);
        while (noResponseHtml == null) {
            noResponseHtml = GetHtml.getHtml(noResponseUrl);
        }
        return noResponseHtml;
    }


    public static String getTime(String itemId, String year, int resFlag,String uid) {
        String questionTime = "";
        String questionTimeStr = "";
        if (resFlag == 0) {
            int i = 1;
            while (true){
                noResponseHtml = getNoResponseHtml(uid,i);
                Document doc = Jsoup.parse(noResponseHtml);
                Elements selectItemElem = doc.select("#" + itemId);
                if(selectItemElem.size()>0){
                    questionTimeStr = selectItemElem.get( 0 ).select( ".m_feed_from span" ).text();
                    questionTime = getDate(questionTimeStr, year);
                    return questionTime;
                }else{
                    if(noResponseHtml.contains("暂时没有问答")){
                        return year;
                    }else{
                        i++;
                    }
                }
            }
        } else {
            int i = 1;
            while (true){
                haveResponseHtml = getHaveResponseHtml(uid,i);
                Document doc = Jsoup.parse(haveResponseHtml);
                Elements selectItemElem = doc.select("#" + itemId);
                if(selectItemElem.size()>0){
                    questionTimeStr = selectItemElem.get( 0 ).select( ".m_feed_detail" ).get( 0 ).select( ".m_feed_from span" ).text();
                    questionTime = getDate(questionTimeStr, year);
                    return questionTime;
                }else{
                    if(haveResponseHtml.contains("暂时没有问答")){
                        return year;
                    }else{
                        i++;
                    }
                }
            }
        }
    }

    public static String getThisDate() {
        Date date = new Date();
        String nowDateStr = format.format(date);
        return nowDateStr;
    }

    public static Boolean compareDate(String questionTime, String requestTime) {//比对提问时间和回复时间的年份是否一致，且必须提问时间小于回复时间
        Boolean bool = false;
        try {
            Date quesDate = format.parse(questionTime);
            Date requestDate = format.parse(requestTime);
            Calendar quesCal = Calendar.getInstance();//Calendar.getInstance()获取当天指定点上的时间
            quesCal.setTime(quesDate);
            Calendar requestCal = Calendar.getInstance();
            requestCal.setTime(requestDate);
            int quesDayOfYear = quesCal.get(quesCal.DAY_OF_YEAR);//DAY_OF_YEAR用来获得这一天在是这个年的第多少天。
            int requestDayOfYear = requestCal.get(requestCal.DAY_OF_YEAR);//DAY_OF_YEAR用来获得这一天在是这个年的第多少天。
            if (quesDayOfYear > requestDayOfYear) {
                bool = true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return bool;
    }

    public static String getThisTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date();
        String nowDateStr = dateFormat.format(date);
        return nowDateStr;
    }

    public static String getDate(String questionTimeStr, String year) {
        String questionTime = "";
        if (questionTimeStr.contains("分钟前")) {
            int timeInt = Integer.parseInt(questionTimeStr.replace("分钟前", "").trim());
            questionTime = getMinuteTime(timeInt);
        } else if (questionTimeStr.contains("小时前")) {
            int timeInt = Integer.parseInt(questionTimeStr.replace("小时前", "").trim());
            questionTime = getHoursTime(timeInt);
        } else if (questionTimeStr.contains("秒前")) {
            questionTime = getThisTime();
        } else if (questionTimeStr.contains("今天")) {
            questionTime = getThisDate() + questionTimeStr.replace("今天", "");
        } else if (questionTimeStr.contains("昨天")) {
            questionTime = getOtherDate(1) + questionTimeStr.replace("昨天", "");
        } else if (questionTimeStr.contains("前天")) {
            questionTime = getOtherDate(2) + questionTimeStr.replace("前天", "");
            ;
        } else {
            questionTime = year + "-" + questionTimeStr.replace("月", "-").replace("日", "");
        }
        return questionTime;
    }

    public static String getMinuteTime(int timeInt) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Calendar nowCal = Calendar.getInstance();
        nowCal.setTime(date);
        int minuteInt = nowCal.get(nowCal.MINUTE);
        nowCal.set(nowCal.MINUTE, minuteInt - timeInt);
        String questionTime = dateFormat.format(nowCal.getTime());
        return questionTime;
    }

    public static String getHoursTime(int timeInt) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Calendar nowCal = Calendar.getInstance();
        nowCal.setTime(date);
        int minuteInt = nowCal.get(nowCal.HOUR);
        nowCal.set(nowCal.HOUR, minuteInt - timeInt);
        String questionTime = dateFormat.format(nowCal.getTime());
        return questionTime;
    }

    public static String getOtherDate(int dayNum) {
        String questionTime = "";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            String nowDate = getThisDate();
            Date date = dateFormat.parse(nowDate);
            Calendar nowCal = Calendar.getInstance();
            nowCal.setTime(date);
            int dayOfYear = nowCal.get(nowCal.DAY_OF_YEAR);
            nowCal.set(nowCal.DAY_OF_YEAR, dayOfYear - dayNum);
            questionTime = dateFormat.format(nowCal.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return questionTime;
    }
}
