package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DateChangeUtil {
    public synchronized static long dateChange(String oldDateStr){
        String[] infoArray=getFormatRegex(oldDateStr);
        long time=0;
        try {
            SimpleDateFormat format=new SimpleDateFormat(infoArray[0]);
            Date date=format.parse(infoArray[1]);
            time=date.getTime()/1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    public static String[] getFormatRegex(String oldDateStr){
        String[] infoArray=new String[2];
        String formatRegex="";
        String newDateSte="";
        Pattern pattern= Pattern.compile("\\d{4}-\\d{2}-\\d{2}\\s+\\d{2}:\\d{2}:\\d{2}");
        Pattern pattern1= Pattern.compile("\\d{4}-\\d{2}-\\d{2}\\s+\\d{2}:\\d{2}");
        Pattern pattern2= Pattern.compile("\\d{4}-\\d{2}-\\d{2}\\s+\\d{1}:\\d{2}:\\d{2}");
        Pattern pattern3= Pattern.compile("\\d{4}-\\d{2}-\\d{2}\\s+\\d{1}:\\d{2}");
        Pattern pattern4= Pattern.compile("\\d{4}年\\d{2}月\\d{2}日\\s+\\d{2}:\\d{2}:\\d{2}");
        Pattern pattern5= Pattern.compile("\\d{4}年\\d{2}月\\d{2}日\\s+\\d{2}:\\d{2}");
        Pattern pattern6= Pattern.compile("\\d{4}年\\d{2}月\\d{2}日\\s+\\d{1}:\\d{2}:\\d{2}");
        Pattern pattern7= Pattern.compile("\\d{4}年\\d{2}月\\d{2}日\\s+\\d{1}:\\d{2}");
        Pattern pattern8= Pattern.compile("\\d{4}/\\d{2}/\\d{2}\\s+\\d{2}:\\d{2}:\\d{2}");
        Pattern pattern9= Pattern.compile("\\d{4}/\\d{2}/\\d{2}\\s+\\d{2}:\\d{2}");
        Pattern pattern10= Pattern.compile("\\d{4}/\\d{2}/\\d{2}\\s+\\d{1}:\\d{2}:\\d{2}");
        Pattern pattern11= Pattern.compile("\\d{4}/\\d{2}/\\d{2}\\s+\\d{1}:\\d{2}");
        Pattern pattern12= Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
        Pattern pattern13= Pattern.compile("\\d{4}年\\d{2}月\\d{2}日");
        Pattern pattern14= Pattern.compile("\\d{4}/\\d{2}/\\d{2}");
        Pattern pattern15= Pattern.compile("\\d{4}-\\d{1}-\\d{1}\\s+\\d{2}:\\d{2}:\\d{2}");
        Pattern pattern16= Pattern.compile("\\d{4}-\\d{1}-\\d{1}\\s+\\d{2}:\\d{2}");
        Pattern pattern17= Pattern.compile("\\d{4}-\\d{1}-\\d{1}\\s+\\d{1}:\\d{2}:\\d{2}");
        Pattern pattern18= Pattern.compile("\\d{4}-\\d{1}-\\d{1}\\s+\\d{1}:\\d{2}");
        Pattern pattern19= Pattern.compile("\\d{4}-\\d{1}-\\d{2}\\s+\\d{2}:\\d{2}:\\d{2}");
        Pattern pattern20= Pattern.compile("\\d{4}-\\d{1}-\\d{2}\\s+\\d{2}:\\d{2}");
        Pattern pattern21= Pattern.compile("\\d{4}-\\d{1}-\\d{2}\\s+\\d{1}:\\d{2}:\\d{2}");
        Pattern pattern22= Pattern.compile("\\d{4}-\\d{1}-\\d{2}\\s+\\d{1}:\\d{2}");
        Pattern pattern23= Pattern.compile("\\d{4}-\\d{2}-\\d{1}\\s+\\d{2}:\\d{2}:\\d{2}");
        Pattern pattern24= Pattern.compile("\\d{4}-\\d{2}-\\d{1}\\s+\\d{2}:\\d{2}");
        Pattern pattern25= Pattern.compile("\\d{4}-\\d{2}-\\d{1}\\s+\\d{1}:\\d{2}:\\d{2}");
        Pattern pattern26= Pattern.compile("\\d{4}-\\d{2}-\\d{1}\\s+\\d{1}:\\d{2}");
        Pattern pattern27= Pattern.compile("\\d{4}年\\d{1}月\\d{1}日\\s+\\d{2}:\\d{2}:\\d{2}");
        Pattern pattern28= Pattern.compile("\\d{4}年\\d{1}月\\d{1}日\\s+\\d{2}:\\d{2}");
        Pattern pattern29= Pattern.compile("\\d{4}年\\d{1}月\\d{1}日\\s+\\d{1}:\\d{2}:\\d{2}");
        Pattern pattern30= Pattern.compile("\\d{4}年\\d{1}月\\d{1}日\\s+\\d{1}:\\d{2}");
        Pattern pattern31= Pattern.compile("\\d{4}年\\d{1}月\\d{2}日\\s+\\d{2}:\\d{2}:\\d{2}");
        Pattern pattern32= Pattern.compile("\\d{4}年\\d{1}月\\d{2}日\\s+\\d{2}:\\d{2}");
        Pattern pattern33= Pattern.compile("\\d{4}年\\d{1}月\\d{2}日\\s+\\d{1}:\\d{2}:\\d{2}");
        Pattern pattern34= Pattern.compile("\\d{4}年\\d{1}月\\d{2}日\\s+\\d{1}:\\d{2}");
        Pattern pattern35= Pattern.compile("\\d{4}年\\d{2}月\\d{1}日\\s+\\d{2}:\\d{2}:\\d{2}");
        Pattern pattern36= Pattern.compile("\\d{4}年\\d{2}月\\d{1}日\\s+\\d{2}:\\d{2}");
        Pattern pattern37= Pattern.compile("\\d{4}年\\d{2}月\\d{1}日\\s+\\d{1}:\\d{2}:\\d{2}");
        Pattern pattern38= Pattern.compile("\\d{4}年\\d{2}月\\d{1}日\\s+\\d{1}:\\d{2}");
        Pattern pattern39= Pattern.compile("\\d{4}/\\d{1}/\\d{1}\\s+\\d{2}:\\d{2}:\\d{2}");
        Pattern pattern40= Pattern.compile("\\d{4}/\\d{1}/\\d{1}\\s+\\d{2}:\\d{2}");
        Pattern pattern41= Pattern.compile("\\d{4}/\\d{1}/\\d{1}\\s+\\d{1}:\\d{2}:\\d{2}");
        Pattern pattern42= Pattern.compile("\\d{4}/\\d{1}/\\d{1}\\s+\\d{1}:\\d{2}");
        Pattern pattern43= Pattern.compile("\\d{4}/\\d{1}/\\d{2}\\s+\\d{2}:\\d{2}:\\d{2}");
        Pattern pattern44= Pattern.compile("\\d{4}/\\d{1}/\\d{2}\\s+\\d{2}:\\d{2}");
        Pattern pattern45= Pattern.compile("\\d{4}/\\d{1}/\\d{2}\\s+\\d{1}:\\d{2}:\\d{2}");
        Pattern pattern46= Pattern.compile("\\d{4}/\\d{1}/\\d{2}\\s+\\d{1}:\\d{2}");
        Pattern pattern47= Pattern.compile("\\d{4}/\\d{2}/\\d{1}\\s+\\d{2}:\\d{2}:\\d{2}");
        Pattern pattern48= Pattern.compile("\\d{4}/\\d{2}/\\d{1}\\s+\\d{2}:\\d{2}");
        Pattern pattern49= Pattern.compile("\\d{4}/\\d{2}/\\d{1}\\s+\\d{1}:\\d{2}:\\d{2}");
        Pattern pattern50= Pattern.compile("\\d{4}/\\d{2}/\\d{1}\\s+\\d{1}:\\d{2}");
        Pattern pattern51= Pattern.compile("\\d{4}-\\d{1}-\\d{1}");
        Pattern pattern52= Pattern.compile("\\d{4}-\\d{1}-\\d{2}");
        Pattern pattern53= Pattern.compile("\\d{4}-\\d{2}-\\d{1}");
        Pattern pattern54= Pattern.compile("\\d{4}年\\d{1}月\\d{1}日");
        Pattern pattern55= Pattern.compile("\\d{4}年\\d{1}月\\d{2}日");
        Pattern pattern56= Pattern.compile("\\d{4}年\\d{2}月\\d{1}日");
        Pattern pattern57= Pattern.compile("\\d{4}/\\d{1}/\\d{1}");
        Pattern pattern58= Pattern.compile("\\d{4}/\\d{1}/\\d{2}");
        Pattern pattern59= Pattern.compile("\\d{4}/\\d{2}/\\d{1}");
        Pattern pattern60= Pattern.compile("\\d{2}-\\d{2}-\\d{2}\\s+\\d{2}:\\d{2}:\\d{2}");
        Pattern pattern61= Pattern.compile("\\d{2}-\\d{2}-\\d{2}\\s+\\d{2}:\\d{2}");
        Pattern pattern62= Pattern.compile("\\d{2}-\\d{2}-\\d{2}\\s+\\d{1}:\\d{2}:\\d{2}");
        Pattern pattern63= Pattern.compile("\\d{2}-\\d{2}-\\d{2}\\s+\\d{1}:\\d{2}");
        Pattern pattern64= Pattern.compile("\\d{2}-\\d{1}-\\d{2}\\s+\\d{2}:\\d{2}:\\d{2}");
        Pattern pattern65= Pattern.compile("\\d{2}-\\d{1}-\\d{2}\\s+\\d{2}:\\d{2}");
        Pattern pattern66= Pattern.compile("\\d{2}-\\d{1}-\\d{2}\\s+\\d{1}:\\d{2}:\\d{2}");
        Pattern pattern67= Pattern.compile("\\d{2}-\\d{1}-\\d{2}\\s+\\d{1}:\\d{2}");
        Pattern pattern68= Pattern.compile("\\d{2}-\\d{2}-\\d{1}\\s+\\d{2}:\\d{2}:\\d{2}");
        Pattern pattern69= Pattern.compile("\\d{2}-\\d{2}-\\d{1}\\s+\\d{2}:\\d{2}");
        Pattern pattern70= Pattern.compile("\\d{2}-\\d{2}-\\d{1}\\s+\\d{1}:\\d{2}:\\d{2}");
        Pattern pattern71= Pattern.compile("\\d{2}-\\d{2}-\\d{1}\\s+\\d{1}:\\d{2}");
        Pattern pattern72= Pattern.compile("\\d{4}-\\d{2}-\\d{2}\\s+\\d{2}:\\d{2}:\\d{2}");
        Pattern pattern73= Pattern.compile("\\d{4}\\s+\\d{2}/\\d{2}\\s+\\d{2}:\\d{2}");

        Matcher matcher=pattern.matcher(oldDateStr);
        Matcher matcher1=pattern1.matcher(oldDateStr);
        Matcher matcher2=pattern2.matcher(oldDateStr);
        Matcher matcher3=pattern3.matcher(oldDateStr);
        Matcher matcher4=pattern4.matcher(oldDateStr);
        Matcher matcher5=pattern5.matcher(oldDateStr);
        Matcher matcher6=pattern6.matcher(oldDateStr);
        Matcher matcher7=pattern7.matcher(oldDateStr);
        Matcher matcher8=pattern8.matcher(oldDateStr);
        Matcher matcher9=pattern9.matcher(oldDateStr);
        Matcher matcher10=pattern10.matcher(oldDateStr);
        Matcher matcher11=pattern11.matcher(oldDateStr);
        Matcher matcher12=pattern12.matcher(oldDateStr);
        Matcher matcher13=pattern13.matcher(oldDateStr);
        Matcher matcher14=pattern14.matcher(oldDateStr);
        Matcher matcher15=pattern15.matcher(oldDateStr);
        Matcher matcher16=pattern16.matcher(oldDateStr);
        Matcher matcher17=pattern17.matcher(oldDateStr);
        Matcher matcher18=pattern18.matcher(oldDateStr);
        Matcher matcher19=pattern19.matcher(oldDateStr);
        Matcher matcher20=pattern20.matcher(oldDateStr);
        Matcher matcher21=pattern21.matcher(oldDateStr);
        Matcher matcher22=pattern22.matcher(oldDateStr);
        Matcher matcher23=pattern23.matcher(oldDateStr);
        Matcher matcher24=pattern24.matcher(oldDateStr);
        Matcher matcher25=pattern25.matcher(oldDateStr);
        Matcher matcher26=pattern26.matcher(oldDateStr);
        Matcher matcher27=pattern27.matcher(oldDateStr);
        Matcher matcher28=pattern28.matcher(oldDateStr);
        Matcher matcher29=pattern29.matcher(oldDateStr);
        Matcher matcher30=pattern30.matcher(oldDateStr);
        Matcher matcher31=pattern31.matcher(oldDateStr);
        Matcher matcher32=pattern32.matcher(oldDateStr);
        Matcher matcher33=pattern33.matcher(oldDateStr);
        Matcher matcher34=pattern34.matcher(oldDateStr);
        Matcher matcher35=pattern35.matcher(oldDateStr);
        Matcher matcher36=pattern36.matcher(oldDateStr);
        Matcher matcher37=pattern37.matcher(oldDateStr);
        Matcher matcher38=pattern38.matcher(oldDateStr);
        Matcher matcher39=pattern39.matcher(oldDateStr);
        Matcher matcher40=pattern40.matcher(oldDateStr);
        Matcher matcher41=pattern41.matcher(oldDateStr);
        Matcher matcher42=pattern42.matcher(oldDateStr);
        Matcher matcher43=pattern43.matcher(oldDateStr);
        Matcher matcher44=pattern44.matcher(oldDateStr);
        Matcher matcher45=pattern45.matcher(oldDateStr);
        Matcher matcher46=pattern46.matcher(oldDateStr);
        Matcher matcher47=pattern47.matcher(oldDateStr);
        Matcher matcher48=pattern48.matcher(oldDateStr);
        Matcher matcher49=pattern49.matcher(oldDateStr);
        Matcher matcher50=pattern50.matcher(oldDateStr);
        Matcher matcher51=pattern51.matcher(oldDateStr);
        Matcher matcher52=pattern52.matcher(oldDateStr);
        Matcher matcher53=pattern53.matcher(oldDateStr);
        Matcher matcher54=pattern54.matcher(oldDateStr);
        Matcher matcher55=pattern55.matcher(oldDateStr);
        Matcher matcher56=pattern56.matcher(oldDateStr);
        Matcher matcher57=pattern57.matcher(oldDateStr);
        Matcher matcher58=pattern58.matcher(oldDateStr);
        Matcher matcher59=pattern59.matcher(oldDateStr);
        Matcher matcher60=pattern60.matcher(oldDateStr);
        Matcher matcher61=pattern61.matcher(oldDateStr);
        Matcher matcher62=pattern62.matcher(oldDateStr);
        Matcher matcher63=pattern63.matcher(oldDateStr);
        Matcher matcher64=pattern64.matcher(oldDateStr);
        Matcher matcher65=pattern65.matcher(oldDateStr);
        Matcher matcher66=pattern66.matcher(oldDateStr);
        Matcher matcher67=pattern67.matcher(oldDateStr);
        Matcher matcher68=pattern68.matcher(oldDateStr);
        Matcher matcher69=pattern69.matcher(oldDateStr);
        Matcher matcher70=pattern70.matcher(oldDateStr);
        Matcher matcher71=pattern71.matcher(oldDateStr);
        Matcher matcher72=pattern72.matcher(oldDateStr);
        Matcher matcher73=pattern73.matcher(oldDateStr);

        if(matcher.find()){
            formatRegex="yyyy-MM-dd HH:mm:ss";
            newDateSte=matcher.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }
        if(matcher1.find()){
            formatRegex="yyyy-MM-dd HH:mm";
            newDateSte=matcher1.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }
        if(matcher2.find()){
            formatRegex="yyyy-MM-dd hh:mm:ss";
            newDateSte=matcher2.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }
        if(matcher3.find()){
            formatRegex="yyyy-MM-dd hh:mm";
            newDateSte=matcher3.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }
        if(matcher4.find()){
            formatRegex="yyyy年MM月dd日 HH:mm:ss";
            newDateSte=matcher4.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }
        if(matcher5.find()){
            formatRegex="yyyy年MM月dd日 HH:mm";
            newDateSte=matcher5.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }
        if(matcher6.find()){
            formatRegex="yyyy年MM月dd日 hh:mm:ss";
            newDateSte=matcher6.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }
        if(matcher7.find()){
            formatRegex="yyyy年MM月dd日 hh:mm";
            newDateSte=matcher7.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }
        if(matcher8.find()){
            formatRegex="yyyy/MM/dd HH:mm:ss";
            newDateSte=matcher8.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }
        if(matcher9.find()){
            formatRegex="yyyy/MM/dd HH:mm";
            newDateSte=matcher9.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }
        if(matcher10.find()){
            formatRegex="yyyy/MM/dd hh:mm:ss";
            newDateSte=matcher10.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }
        if(matcher11.find()){
            formatRegex="yyyy/MM/dd hh:mm";
            newDateSte=matcher11.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }
        if(matcher12.find()){
            formatRegex="yyyy-MM-dd";
            newDateSte=matcher12.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }
        if(matcher13.find()){
            formatRegex="yyyy年MM月dd日";
            newDateSte=matcher13.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }
        if(matcher14.find()){
            formatRegex="yyyy/MM/dd";
            newDateSte=matcher14.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }
        if(matcher15.find()){
            formatRegex="yyyy-MM-dd HH:mm:ss";
            newDateSte=matcher15.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }
        if(matcher16.find()){
            formatRegex="yyyy-MM-dd HH:mm";
            newDateSte=matcher16.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }
        if(matcher17.find()){
            formatRegex="yyyy-MM-dd hh:mm:ss";
            newDateSte=matcher17.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }
        if(matcher18.find()){
            formatRegex="yyyy-MM-dd hh:mm";
            newDateSte=matcher18.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }
        if(matcher19.find()){
            formatRegex="yyyy-MM-dd HH:mm:ss";
            newDateSte=matcher19.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }
        if(matcher20.find()){
            formatRegex="yyyy-MM-dd HH:mm";
            newDateSte=matcher20.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }
        if(matcher21.find()){
            formatRegex="yyyy-MM-dd hh:mm:ss";
            newDateSte=matcher21.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }
        if(matcher22.find()){
            formatRegex="yyyy-MM-dd hh:mm";
            newDateSte=matcher22.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }
        if(matcher23.find()){
            formatRegex="yyyy-MM-dd HH:mm:ss";
            newDateSte=matcher23.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }
        if(matcher24.find()){
            formatRegex="yyyy-MM-dd HH:mm";
            newDateSte=matcher24.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }
        if(matcher25.find()){
            formatRegex="yyyy-MM-dd hh:mm:ss";
            newDateSte=matcher25.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }
        if(matcher26.find()){
            formatRegex="yyyy-MM-dd hh:mm";
            newDateSte=matcher26.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }
        if(matcher27.find()){
            formatRegex="yyyy年MM月dd日 HH:mm:ss";
            newDateSte=matcher27.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }
        if(matcher28.find()){
            formatRegex="yyyy年MM月dd日 HH:mm";
            newDateSte=matcher28.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }
        if(matcher29.find()){
            formatRegex="yyyy年MM月dd日 hh:mm:ss";
            newDateSte=matcher29.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }
        if(matcher30.find()){
            formatRegex="yyyy年MM月dd日 hh:mm";
            newDateSte=matcher30.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }
        if(matcher31.find()){
            formatRegex="yyyy年MM月dd日 HH:mm:ss";
            newDateSte=matcher31.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }
        if(matcher32.find()){
            formatRegex="yyyy年MM月dd日 HH:mm";
            newDateSte=matcher32.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }
        if(matcher33.find()){
            formatRegex="yyyy年MM月dd日 hh:mm:ss";
            newDateSte=matcher33.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }
        if(matcher34.find()){
            formatRegex="yyyy年MM月dd日 hh:mm";
            newDateSte=matcher34.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }
        if(matcher35.find()){
            formatRegex="yyyy年MM月dd日 HH:mm:ss";
            newDateSte=matcher35.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }
        if(matcher36.find()){
            formatRegex="yyyy年MM月dd日 HH:mm";
            newDateSte=matcher36.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }
        if(matcher37.find()){
            formatRegex="yyyy年MM月dd日 hh:mm:ss";
            newDateSte=matcher37.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }
        if(matcher38.find()){
            formatRegex="yyyy年MM月dd日 hh:mm";
            newDateSte=matcher38.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }
        if(matcher39.find()){
            formatRegex="yyyy/MM/dd HH:mm:ss";
            newDateSte=matcher39.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }
        if(matcher40.find()){
            formatRegex="yyyy/MM/dd HH:mm";
            newDateSte=matcher40.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }
        if(matcher41.find()){
            formatRegex="yyyy/MM/dd hh:mm:ss";
            newDateSte=matcher41.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }
        if(matcher42.find()){
            formatRegex="yyyy/MM/dd hh:mm";
            newDateSte=matcher42.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }
        if(matcher43.find()){
            formatRegex="yyyy/MM/dd HH:mm:ss";
            newDateSte=matcher43.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }
        if(matcher44.find()){
            formatRegex="yyyy/MM/dd HH:mm";
            newDateSte=matcher44.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }
        if(matcher45.find()){
            formatRegex="yyyy/MM/dd hh:mm:ss";
            newDateSte=matcher45.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }
        if(matcher46.find()){
            formatRegex="yyyy/MM/dd hh:mm";
            newDateSte=matcher46.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }
        if(matcher47.find()){
            formatRegex="yyyy/MM/dd HH:mm:ss";
            newDateSte=matcher47.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }
        if(matcher48.find()){
            formatRegex="yyyy/MM/dd HH:mm";
            newDateSte=matcher48.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }
        if(matcher49.find()){
            formatRegex="yyyy/MM/dd hh:mm:ss";
            newDateSte=matcher49.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }
        if(matcher50.find()){
            formatRegex="yyyy/MM/dd hh:mm";
            newDateSte=matcher50.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }
        if(matcher51.find()){
            formatRegex="yyyy-MM-dd";
            newDateSte=matcher51.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }
        if(matcher52.find()){
            formatRegex="yyyy-MM-dd";
            newDateSte=matcher52.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }
        if(matcher53.find()){
            formatRegex="yyyy-MM-dd";
            newDateSte=matcher53.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }
        if(matcher54.find()){
            formatRegex="yyyy年MM月dd日";
            newDateSte=matcher54.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }
        if(matcher55.find()){
            formatRegex="yyyy年MM月dd日";
            newDateSte=matcher55.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }
        if(matcher56.find()){
            formatRegex="yyyy年MM月dd日";
            newDateSte=matcher56.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }
        if(matcher57.find()){
            formatRegex="yyyy/MM/dd";
            newDateSte=matcher57.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }
        if(matcher58.find()){
            formatRegex="yyyy/MM/dd";
            newDateSte=matcher58.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }
        if(matcher59.find()){
            formatRegex="yyyy/MM/dd";
            newDateSte=matcher59.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }

        if(matcher60.find()){
            formatRegex="yy-MM-dd HH:mm:ss";
            newDateSte=matcher60.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }

        if(matcher61.find()){
            formatRegex="yy-MM-dd HH:mm";
            newDateSte=matcher61.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }

        if(matcher62.find()){
            formatRegex="yy-MM-dd hh:mm:ss";
            newDateSte=matcher62.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }

        if(matcher63.find()){
            formatRegex="yy-MM-dd hh:mm";
            newDateSte=matcher63.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }

        if(matcher64.find()){
            formatRegex="yy/-MM-dd HH:mm:ss";
            newDateSte=matcher64.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }

        if(matcher65.find()){
            formatRegex="yy-MM-dd HH:mm";
            newDateSte=matcher65.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }

        if(matcher66.find()){
            formatRegex="yy-MM-dd hh:mm:ss";
            newDateSte=matcher66.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }

        if(matcher67.find()){
            formatRegex="yy-MM-dd hh:mm";
            newDateSte=matcher67.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }

        if(matcher68.find()){
            formatRegex="yy-MM-dd HH:mm:ss";
            newDateSte=matcher68.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }

        if(matcher69.find()){
            formatRegex="yy-MM-dd HH:mm";
            newDateSte=matcher69.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }

        if(matcher70.find()){
            formatRegex="yy-MM-dd hh:mm:ss";
            newDateSte=matcher70.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }

        if(matcher71.find()){
            formatRegex="yy-MM-dd hh:mm";
            newDateSte=matcher71.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }

        if(matcher72.find()){
            formatRegex="yyyy-MM-dd hh:mm:ss";
            newDateSte=matcher72.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }


        if(matcher73.find()){
            formatRegex="yyyy MM/dd hh:mm";
            newDateSte=matcher73.group(0);
            infoArray[0]=formatRegex;
            infoArray[1]=newDateSte;
            return infoArray;
        }
        return infoArray;


    }

    public static long getFeachTime(){
        long feachTime=(new Date().getTime())/1000;
        return feachTime;
    }

}
