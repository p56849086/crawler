package 统计每年每个上市公司的问答数;

import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.List;

/**
 * 
 * 公司：上海经禾
 * 作者：程存淦
 * 功能：将信息存入数据库
 * 时间：2020年8月19日10:16:16
 *
 */
public class SaveDataToMongoDB {

	public static void saveData(List<Object> dataList, String[] chineseNames, MongoCollection mongoConnection){
		Document document=new Document();
		for(int i=0;i<dataList.size();i++){
			document.append(chineseNames[i],dataList.get(i));
		}
		mongoConnection.insertOne(document);
	}

	public static boolean JudgeExist(String id, MongoCollection mongoConnection){
		boolean exist=false;
		Document document=new Document("_id",id);
		int count=(int)mongoConnection.count(document);
		if(count>0){
			exist=true;
		}
		return exist;
	}
}
