import java.io.Serializable;
import java.util.ArrayList;

public class UserTemplate implements Serializable{
	
	String uname;
	String pass;
	ArrayList<String> accessDb;
	boolean isAdmin;
	
	public UserTemplate(String u, String p, boolean b){
		uname = u;
		pass = p;
		isAdmin = b;
		accessDb = new ArrayList<String>();
	}
	
	public String returnUserName(){
		return uname;
	}
	
	public String returnPassword(){
		return pass;
	}
	
	public void printAccessibleDb(){
		for(int i=0;i<accessDb.size();i++){
			System.out.println(accessDb.get(i));
		}
	}
	
	public boolean checkAdmin(){
		return isAdmin;
	}
	
	public void addDbAccess(String dbNum){
		accessDb.add(dbNum);
	}
	
	public boolean checkDbAccess(String dbNum){
		return (accessDb.contains(dbNum));
	}
	
}
