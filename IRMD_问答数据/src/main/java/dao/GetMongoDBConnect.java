package dao;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * 
 * 公司：上海经禾
 * 作者：程存淦
 * 功能：单例获取175数据库连接
 * 时间：2020年7月1日16:42:18
 * 懒汉式：线程不安全
 */
public class GetMongoDBConnect {
	private static MongoDatabase mongoDB=null;
	private static MongoCollection mongoConnection=null;
	private static MongoClient mongoClient =null;
	private static final String DB_NAME = "IRMD_2021_2_21";
	public static String collectionName="";
	private GetMongoDBConnect(){};//构造器私有化
	//运行时加载对象
	//由于该模式是在运行时加载对象的，所以加载类比较快，但是对象的获取速度相对较慢，且线程不安全。如果想要线程安全的话可以加上synchronized关键字，但是这样会付出惨重的效率代价
	public synchronized static MongoCollection getConnection(){
		mongoClient=new MongoClient("192.168.1.175");
		mongoDB=mongoClient.getDatabase(DB_NAME);
		mongoConnection=mongoDB.getCollection(collectionName);
		return mongoConnection;
	}

	public synchronized static void closeConnect(){
		if(mongoClient!=null){
			mongoClient.close();
		}
	}
}
