import java.io.Serializable;
import java.util.ArrayList;

public class UserTemplate implements Serializable{
	
	String uname;
	String pass;
	String accessDb;
	boolean isAdmin;
	
	public UserTemplate(String u, String p, boolean b){
		uname = u;
		pass = p;
		isAdmin = b;
		accessDb = "";
	}
	
	public String returnUserName(){
		return uname;
	}
	
	public String returnPassword(){
		return pass;
	}
	
	public String returnAccessDb(){
		return accessDb;
	}
	
	public boolean checkAdmin(){
		return isAdmin;
	}
	
	public void addDbAccess(String dbNum){
		if(accessDb.length()==0)
			accessDb+=dbNum;
		else
			accessDb+=","+dbNum;
	}
	
	public boolean checkDbAccess(String dbNum){
		boolean check = false;
		String[] splitData = accessDb.split(",");
		for(int i=0;i<splitData.length;i++){
			if(splitData[i].equals(dbNum))
				check = true;
		}
		return check;
	}
	
}
