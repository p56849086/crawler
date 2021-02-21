import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.Set;

public class test {
    public static void main(String[] args) throws InterruptedException {
        getDocument("https://www.qcc.com/");
    }
    /**
     * 打开浏览器，不能向下滑动
     * @param url
     * @return
     */
    public static Document getDocument(String url) throws InterruptedException {
        Document doc = null;
        //可使用的浏览器有：IE浏览器（webdriver.ie.driver）
        //火狐浏览器  (webdriver.gecko.driver)
        //谷歌浏览器 (webdriver.chrome.driver)
        //                  是使用那个浏览器                                   chromedriver所在的位置
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
        // InternetExplorerDriver()   浏览器
        // FirefoxDriver()            火狐浏览器
        //谷歌浏览器
        WebDriver driver = new ChromeDriver();
        ((JavascriptExecutor) driver).executeScript("Object.defineProperties(navigator,{ webdriver:{ get: () => false } })");
        driver.get(url);
        driver.findElement(By.className("navi-btn")).click();
        driver.findElement(By.id("normalLogin")).click();

        driver.findElement(By.id("nameNormal")).sendKeys("15800961460");
        driver.findElement(By.id("pwdNormal")).sendKeys("12345678");

        WebElement nc_2_n1z = driver.findElement(By.id("nc_2_n1z"));

        Set<Cookie> coo =driver.manage().getCookies();
        Actions actions = new Actions(driver);
        new Actions(driver).clickAndHold(nc_2_n1z).perform();
        int[] m = {1,2,100,200,300};
        for (int i : m) {
            actions.moveByOffset(i, 0).perform();
        }
        Thread.sleep(500);
        //释放
        actions.release(nc_2_n1z).perform();

        //等待几秒
        try {
            //((JavascriptExecutor)driver).executeScript("scrollTo(0,10000)");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        doc = Jsoup.parse(driver.getPageSource());

        //关闭浏览器
        driver.close();
        driver.quit();

        return doc;
    }
}
