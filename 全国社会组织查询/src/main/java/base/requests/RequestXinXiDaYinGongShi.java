package base.requests;

import Entity.GetIp;
import Entity.信息公示实体类.*;
import base.ReqeustBase;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class RequestXinXiDaYinGongShi extends ReqeustBase {


    public RequestXinXiDaYinGongShi(GetIp getIp, String type, String url) {
        super(getIp, type, url);
        switch (type){
            case "get":
                for (Header1 header : Header1.values()){
                    super.httpGet.addHeader(String.valueOf(header).replace("_","-"), header.getName());
                }
                break;
            case "post":
                for (Header1 header : Header1.values()){
                    super.httpPost.addHeader(String.valueOf(header).replace("_","-"), header.getName());
                }
                break;
        }
    }
    // 设置头
    enum Header1 {
        Accept("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9"),
        Accept_Encoding("gzip, deflate"),
        Accept_Language("zh-CN,zh;q=0.9"),
        Cache_Control("max-age=0"),
        Connection("keep-alive"),
        Cookie("Hm_lvt_3adce665674fbfb5552846b40f1c3cbc=1613979834,1613985409,1614043188; Hm_lpvt_3adce665674fbfb5552846b40f1c3cbc=1614043371; chinanpojsessionid=80A6DC574C7B78A16672034BE90DE035.chinanpo_node2"),
        Host("59.252.100.194"),
        Referer("http://59.252.100.194/search/orgcx.html"),
        Upgrade_Insecure_Requests("1"),
        User_Agent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36");
        private String name;
        Header1(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }
    }

    public Object dealPage(String html) {
        Document document = Jsoup.parse(html);
        Elements tables = document.select("table");
        社会组织基本信息 organization = new 社会组织基本信息();
        ListAll listAll = new ListAll();
        for (int i=0; i<tables.size(); i++){
            Elements tds1 = tables.get(i).select("td");
            Elements trs1 = tables.get(i).select("tr");
            // 获取社会组织名称和报告时间
            if (i==0){
                for (int i1=0; i1<tds1.size(); i1++){
                    if (i1==1)
                        organization.set社会组织名称(tds1.get(i1).text());
                    if (i1==3)
                        organization.set报告生成时间(tds1.get(i1).text());
                }
            }
            // 获取社会组织基本信息
            else if (i==1){
                for (int i1=0; i1<tds1.size(); i1++){
                    if (i1==1)
                        organization.set登记管理机关(tds1.get(i1).text());
                    if (i1==3)
                        organization.set业务主管单位(tds1.get(i1).text());
                    if (i1==5)
                        organization.set法定代表人(tds1.get(i1).text());
                    if (i1==7)
                        organization.set成立登记日期(tds1.get(i1).text());
                    if (i1==9)
                        organization.set注册资金(tds1.get(i1).text());
                    if (i1==11)
                        organization.set登记状态(tds1.get(i1).text());
                    if (i1==13)
                        organization.set登记证号(tds1.get(i1).text());
                    if (i1==15)
                        organization.set社会组织类型(tds1.get(i1).text());
                    if (i1==17)
                        organization.set住所(tds1.get(i1).text());
                }
                listAll.setOrganization(organization);
            }
            // 获取成立公示
            else if (i==2){
                List<成立公示> list1 = new ArrayList<>();
                if (tds1.size()==1)
                    listAll.setList1(list1);
                else {
                    for (int i1=1; i1<trs1.size(); i1++){
                        Elements tds = trs1.get(i1).select("td");
                        成立公示 a1 = new 成立公示();
                        for (int i2=0; i2<tds.size(); i2++){
                            if (i2==0)
                                a1.set序号(tds.get(i2).text());
                            if (i2==1)
                                a1.set业务类型(tds.get(i2).text());
                            if (i2==2)
                                a1.set办结日期(tds.get(i2).text());
                        }
                        list1.add(a1);
                    }
                    listAll.setList1(list1);
                }
            }
            // 获取变更公示
            else if (i==3){
                List<变更公示> list2 = new ArrayList<>();
                if (tds1.size()==1)
                    listAll.setList2(list2);
                else {
                    for (int i1=1; i1<trs1.size(); i1++){
                        Elements tds = trs1.get(i1).select("td");
                        变更公示 a1 = new 变更公示();
                        for (int i2=0; i2<tds.size(); i2++){
                            if (i2==0)
                                a1.set序号(tds.get(i2).text());
                            if (i2==1)
                                a1.set业务类型(tds.get(i2).text());
                            if (i2==2)
                                a1.set变更前(tds.get(i2).text());
                            if (i2==3)
                                a1.set变更后(tds.get(i2).text());
                            if (i2==4)
                                a1.set办结日期(tds.get(i2).text());

                        }
                        list2.add(a1);
                    }
                    listAll.setList2(list2);
                }
            }
            // 获取注销公示
            else if (i==4){
                List<注销公示> list3 = new ArrayList<>();
                if (tds1.size()==1)
                    listAll.setList3(list3);
                else {
                    for (int i1=1; i1<trs1.size(); i1++){
                        Elements tds = trs1.get(i1).select("td");
                        注销公示 a1 = new 注销公示();
                        for (int i2=0; i2<tds.size(); i2++){
                            if (i2==0)
                                a1.set序号(tds.get(i2).text());
                            if (i2==1)
                                a1.set业务类型(tds.get(i2).text());
                            if (i2==2)
                                a1.set办结日期(tds.get(i2).text());
                        }
                        list3.add(a1);
                    }
                    listAll.setList3(list3);
                }
            }
            // 获取年检结果
            else if (i==5){
                List<年检结果> list4 = new ArrayList<>();
                if (tds1.size()==1)
                    listAll.setList4(list4);
                else {
                    for (int i1=1; i1<trs1.size(); i1++){
                        Elements tds = trs1.get(i1).select("td");
                        年检结果 a1 = new 年检结果();
                        for (int i2=0; i2<tds.size(); i2++){
                            if (i2==0)
                                a1.set序号(tds.get(i2).text());
                            if (i2==1)
                                a1.set年度(tds.get(i2).text());
                            if (i2==2)
                                a1.set年检结果(tds.get(i2).text());
                        }
                        list4.add(a1);
                    }
                    listAll.setList4(list4);
                }
            }
            // 获取评估信息
            else if (i==6){
                List<评估信息> list5 = new ArrayList<>();
                if (tds1.size()==1)
                    listAll.setList5(list5);
                else {
                    for (int i1=1; i1<trs1.size(); i1++){
                        Elements tds = trs1.get(i1).select("td");
                        评估信息 a1 = new 评估信息();
                        for (int i2=0; i2<tds.size(); i2++){
                            if (i2==0)
                                a1.set序号(tds.get(i2).text());
                            if (i2==1)
                                a1.set评估等级(tds.get(i2).text());
                            if (i2==2)
                                a1.set等级有效期(tds.get(i2).text());
                        }
                        list5.add(a1);
                    }
                    listAll.setList5(list5);
                }
            }
            // 获取表彰信息
            else if (i==7){
                List<表彰信息> list6 = new ArrayList<>();
                if (tds1.size()==1)
                    listAll.setList6(list6);
                else {
                    for (int i1=1; i1<trs1.size(); i1++){
                        Elements tds = trs1.get(i1).select("td");
                        表彰信息 a1 = new 表彰信息();
                        for (int i2=0; i2<tds.size(); i2++){
                            if (i2==0)
                                a1.set序号(tds.get(i2).text());
                            if (i2==1)
                                a1.set荣誉名称(tds.get(i2).text());
                            if (i2==2)
                                a1.set日期(tds.get(i2).text());
                        }
                        list6.add(a1);
                    }
                    listAll.setList6(list6);
                }
            }
            // 获取中央财政支持项目信息
            else if (i==8){
                List<中央财政支持项目> list7 = new ArrayList<>();
                if (tds1.size()==1)
                    listAll.setList7(list7);
                else {
                    for (int i1=1; i1<trs1.size(); i1++){
                        Elements tds = trs1.get(i1).select("td");
                        中央财政支持项目 a1 = new 中央财政支持项目();
                        for (int i2=0; i2<tds.size(); i2++){
                            if (i2==0)
                                a1.set序号(tds.get(i2).text());
                            if (i2==1)
                                a1.set年度(tds.get(i2).text());
                            if (i2==2)
                                a1.set项目编号(tds.get(i2).text());
                            if (i2==3)
                                a1.set项目名称(tds.get(i2).text());
                            if (i2==4)
                                a1.set立项资金_万元(tds.get(i2).text());
                        }
                        list7.add(a1);
                    }
                    listAll.setList7(list7);
                }
            }
            // 获取行政处罚信息
            else if (i==9){
                List<行政处罚> list8 = new ArrayList<>();
                if (tds1.size()==1)
                    listAll.setList8(list8);
                else {
                    for (int i1=1; i1<trs1.size(); i1++){
                        Elements tds = trs1.get(i1).select("td");
                        行政处罚 a1 = new 行政处罚();
                        for (int i2=0; i2<tds.size(); i2++){
                            if (i2==0)
                                a1.set序号(tds.get(i2).text());
                            if (i2==1)
                                a1.set决定书文号(tds.get(i2).text());
                            if (i2==2)
                                a1.set处罚事由(tds.get(i2).text());
                            if (i2==3)
                                a1.set处罚依据(tds.get(i2).text());
                            if (i2==4)
                                a1.set处罚结果(tds.get(i2).text());
                            if (i2==5)
                                a1.set处罚决定日期(tds.get(i2).text());
                        }
                        list8.add(a1);
                    }
                    listAll.setList8(list8);
                }
            }
            // 获取异常活动名录信息
            else if (i==10){
                List<异常活动名录信息> list9 = new ArrayList<>();
                if (tds1.size()==1)
                    listAll.setList9(list9);
                else {
                    for (int i1=1; i1<trs1.size(); i1++){
                        Elements tds = trs1.get(i1).select("td");
                        异常活动名录信息 a1 = new 异常活动名录信息();
                        for (int i2=0; i2<tds.size(); i2++){
                            if (i2==0)
                                a1.set序号(tds.get(i2).text());
                            if (i2==1)
                                a1.set列入时间(tds.get(i2).text());
                            if (i2==2)
                                a1.set列入事由(tds.get(i2).text());
                            if (i2==3)
                                a1.set移出时间(tds.get(i2).text());
                            if (i2==4)
                                a1.set移出事由(tds.get(i2).text());
                        }
                        list9.add(a1);
                    }
                    listAll.setList9(list9);
                }
            }
            // 获取严重违法失信名单信息
            else if (i==11){
                List<严重违法失信名单> list10 = new ArrayList<>();
                if (tds1.size()==1)
                    listAll.setList10(list10);
                else {
                    for (int i1=1; i1<trs1.size(); i1++){
                        Elements tds = trs1.get(i1).select("td");
                        严重违法失信名单 a1 = new 严重违法失信名单();
                        for (int i2=0; i2<tds.size(); i2++){
                            if (i2==0)
                                a1.set序号(tds.get(i2).text());
                            if (i2==1)
                                a1.set列入时间(tds.get(i2).text());
                            if (i2==2)
                                a1.set列入事由(tds.get(i2).text());
                            if (i2==3)
                                a1.set移出时间(tds.get(i2).text());
                            if (i2==4)
                                a1.set移出事由(tds.get(i2).text());
                        }
                        list10.add(a1);
                    }
                    listAll.setList10(list10);
                }
            }
        }
        return listAll;
    }
}
