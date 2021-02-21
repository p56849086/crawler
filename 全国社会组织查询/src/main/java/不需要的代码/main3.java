package 不需要的代码;

import Entity.GetIp;
import Entity.Yichang;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class main3 {
    public static void main(String[] args) throws IOException, URISyntaxException {
        GetIp getIp1 = new GetIp();
        getIp1.requestIp();
        for (int page=2035; page < 3000; page++) {
            List<String> linkString = null;
            CompletePage1 completePage1 = new CompletePage1(String.valueOf(page), getIp1);
            // 请求第一个页面
            while (true) {
                try {
                    String html = completePage1.requestPage();
                    linkString = completePage1.dealPage(html);
                    break;
                } catch (Exception e) {
                    System.out.println("第一个页面解析出错，重试");
                    continue;
                }
            }


            // 使用线程池启动线程
            ExecutorService executor = Executors.newFixedThreadPool(1);
            // 划分linkString
            int i = 0;
            for (String string : linkString) {
                String[] splits = string.split("_");
                executor.execute(new task3(splits[0], splits[1], getIp1, i, page));
                i++;
            }
            executor.shutdown();
            try {
                executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
            System.out.println("-----完成第：" + page + "页");
        }
    }
}

class task3 implements Runnable{
    private String id1;
    private String id2;
    private int i;
    private int page;

    task3(String id1, String id2, GetIp getIp,int i, int page){
        this.id1 = id1;
        this.id2 = id2;
        this.i = i;
        this.page = page;
        this.getIp = getIp;
    }
    GetIp getIp;
    @SneakyThrows
    public void run() {

        CompletePage2 completePage2 = new CompletePage2(this.id1, this.id2, getIp);
        List<String> finallyData1;
        List<Yichang> yichangList;
        List<Yichang> weifaList;
        while (true) {
            try {
                // 请求第一个页面
                String html1 = completePage2.requestTable1();
                finallyData1 = completePage2.dealPage1(html1);
                break;
            }catch (Exception e){
                System.out.println("页面解析错误，重试");
                continue;
            }
        }

        while (true) {
            try {
                //请求第二个页面
                String html2 = completePage2.requestTable2();
                yichangList = completePage2.dealPage2(html2);
                break;
            }catch (Exception e){
                System.out.println("页面解析错误，重试");
                continue;
            }
        }
        while (true) {
            try {
                //请求第三个页面
                String html3 = completePage2.requestTable3();
                weifaList = completePage2.dealPage3(html3);
                break;
            }catch (Exception e){
                System.out.println("页面解析错误，重试");
                continue;
            }
        }

        // 将获得的数据插入到数据库中
        InsertMogdb insertMogdb = new InsertMogdb(finallyData1, yichangList, weifaList, completePage2.getUnifiedCode());
        System.out.println("第 " + page + "页完成第："  + this.i  + "----");
    }
}

/*
    CompletePage2 completePage2 = new CompletePage2(splits[1], splits[2]);
    // 请求第一个页面
    String html1 = completePage2.requestTable1();
    List<String> finallyData1 = completePage2.dealPage1(html1);

    //请求第二个页面
    String html2 = completePage2.requestTable2();
    List<Yichang> yichangList = completePage2.dealPage2(html2);

    //请求第三个页面
    String html3 = completePage2.requestTable3();
    List<Yichang> weifaList = completePage2.dealPage3(html3);

    // 将获得的数据插入到数据库中
    InsertMogdb insertMogdb = new InsertMogdb(finallyData1, yichangList, weifaList, completePage2.getUnifiedCode());*/
