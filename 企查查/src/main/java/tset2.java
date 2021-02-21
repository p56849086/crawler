import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class tset2 {
    public static void main(String[] args) throws IOException, AWTException, InterruptedException {
        Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler https://www.qcc.com/firm/l001b4fc0fb2c5739d605c7cda376545.html");
        Robot robot = new Robot();
        // 移动到指定位置
        Thread.sleep(5000);
        robot.mouseMove(974,424);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseMove(1324, 426);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);//释放左键


    }

    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        String s = "当前鼠标坐标:(" + x + ", " + y + ")";
        System.out.println(s);
    }

    public void mouseMove(){
        try {
            Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler https://www.qcc.com/firm/l001b4fc0fb2c5739d605c7cda376545.html");
            Robot robot = new Robot();
            // 移动到指定位置
            robot.mouseMove(10,20);
            Thread.sleep(1000);
            // 按下鼠标左键
            robot.mousePress(InputEvent.BUTTON1_MASK);
            // 拖动
            Thread.sleep(1000);
            robot.mouseMove(100, 200);
            //释放鼠标左键
            Thread.sleep(1000);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);//释放左键
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
