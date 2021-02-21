package 不需要的代码;

import org.bson.Document;

public class test2 {
    public static void main(String[] args){
        Document document = new Document();
        document.append("test","1");
        document.put("test","2");
        document.put("test","3");
        document.put("test","4");
        document.put("test","5");
    }
}
