import java.io.Serializable;
import java.util.ArrayList;

public class DbTemplate implements Serializable{
	
	String dbName;
	ArrayList<String> tables;
	
	public DbTemplate(String n){
		dbName = n;
		tables = new ArrayList<String>();
	}
	
	public void addTableToDb(String tableName){
		tables.add(tableName);
	}
	
	public boolean checkTableExists(String tableName){
		return (tables.contains(tableName));
	}
	
	public String returnDbName(){
		return dbName;
	}
	
}
