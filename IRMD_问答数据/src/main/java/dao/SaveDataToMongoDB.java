package dao;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.List;

/**
 * 
 * 公司：上海经禾
 * 作者：程存淦
 * 功能：将信息存入数据库
 * 时间：2020年7月1日16:42:55
 *
 */
public class SaveDataToMongoDB {

	public synchronized static void saveData(List<Object> dataList,String[] chineseNames,MongoCollection mongoConnection){
		Document document=new Document();
		for(int i=0;i<dataList.size();i++){
			document.append(chineseNames[i],dataList.get(i));
		}
		mongoConnection.insertOne(document);
	}
}
