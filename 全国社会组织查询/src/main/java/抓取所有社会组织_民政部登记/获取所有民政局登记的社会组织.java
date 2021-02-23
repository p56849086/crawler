package 抓取所有社会组织_民政部登记;

import Entity.GetIp;
import Entity.MinZheng;
import base.requests.RequestMinZB;

import java.util.List;

public class 获取所有民政局登记的社会组织 {
    public static void main(String[] args) {
        GetIp getIp = new GetIp();
        RequestMinZB requestMinZB = new RequestMinZB(getIp,"post", "http://59.252.100.194/search/orgcx.html");
        /*
        * 1.抓取最外层的统一社会信用代码和连接中的数据，为下一步请求做准备
        * 2.进入第二层，抓取打印信息的连接中的数据
        * 3.抓取要下载的内容
        * */
        for (int i=44; i<=48; i++){

            String data = "t=2&legalName=&orgName=&regName=%25E6%25B0%2591%25E6%2594%25BF&supOrgName=&corporateType=" +
                    "&managerDeptCode=&registrationNo=&unifiedCode=&orgAddNo=&ifCharity=&ifCollect=&status=2&regNumB=" +
                    "&regNumD=&tabIndex=1&regNum=-1&regDate=&regDateEnd=&isZyfw=&isHyxh=&page_flag=true&pagesize_key=macList" +
                    "&goto_page=next&current_page=" + i + "&total_count=2292&page_size=50&to_page=";

            List<MinZheng> list = (List<MinZheng>) requestMinZB.dealPage(requestMinZB.requestGetPage(data));
            // 插入到数据库中
            InsertMinZheng.InsertMongodb(list);
            System.out.println("------完成第" + i + "页-----" );
        }


    }
}
