package util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 * 公司：上海经禾信息技术有限公司
 * 作者：程存淦
 * 功能：获取一个月的开始时间和结束时间
 * 时间：2020年7月1日16:25:48
 */
public class GetOneMonthDate {
    public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public static String [] getOneMonthDate(String year,String month){
        String [] dateArray={"",""};
        try {
            String monthBeginDateStr=year+"-"+month+"-"+"01";
            Date monthBeginDate = dateFormat.parse(monthBeginDateStr);
            Calendar monthBeginCal = Calendar.getInstance();
            monthBeginCal.setTime(monthBeginDate);
            monthBeginCal.set(Calendar.DAY_OF_MONTH, monthBeginCal.getActualMaximum(Calendar.DAY_OF_MONTH));
            String monthEndDateStr=dateFormat.format(monthBeginCal.getTime());
            dateArray[0]=monthBeginDateStr;
            dateArray[1]=monthEndDateStr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateArray;
    }
}
