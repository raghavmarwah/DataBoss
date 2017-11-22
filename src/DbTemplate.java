import java.io.Serializable;
import java.util.ArrayList;

public class DbTemplate implements Serializable{
	
	String dbName;
	String tables;
	
	public DbTemplate(String n){
		dbName = n;
		tables = "";
	}
	
	public void addTableToDb(String tableName){
		if(tables.length()==0)
			tables+=tableName;
		else
			tables+=","+tableName;
	}
	
	public boolean checkTableExists(String tableName){
		boolean check = false;
		String[] splitData = tables.split(",");
		for(int i=0;i<splitData.length;i++){
			if(splitData[i].equals(tableName))
				check = true;
		}
		return check;
	}
	
	public String returnDbName(){
		return dbName;
	}
	
	public String displayTables(){
		return tables;
	}
	
}
