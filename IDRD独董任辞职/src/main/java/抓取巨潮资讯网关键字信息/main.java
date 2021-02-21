package 抓取巨潮资讯网关键字信息;

import Request.GetIp;
import Request.entity.EntityJC;
import Request.请求巨潮资讯网.RequestJC;

import java.util.List;

// 主程序入口
public class main {
    public static void main(String[] args){
        RequestJC requestJC = new RequestJC(new GetIp(), "get", "http://www.cninfo.com.cn/new/fulltextSearch/full");
        // 请求关键字
        String[] keys = {"独董辞职", "独董离职"};

        // 循环所有关键字
        for (String key:keys){
            int totalPages = 10;
            for (int i=1; i<=totalPages; i++){
                String Data = "?searchkey=" + key + "&sdate=2019-01-01&edate=2019-12-31&isfulltext=false&sortName=pubdate&sortType=desc&pageNum=" + i;
                if (i==1){
                    // 首先请求一次页面，确定所有的页数
                    totalPages = requestJC.getPages(requestJC.requestGetPage(Data));
                }
                // 解析页面信息
                List<EntityJC> listData = (List<EntityJC>) requestJC.dealPage(requestJC.requestGetPage(Data));
                // 插入到数据库中
                InsertMongodb.Insert(listData);
                System.out.println("-----正在进行的关键字:" + key + "-----完成第" + i + "页");
            }
        }

    }
}
