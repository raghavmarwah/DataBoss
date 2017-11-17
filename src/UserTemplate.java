public class UserTemplate {
	
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
	public String returnAccessibleDb(){
		return accessDb;
	}
	public boolean checkAdmin(){
		return isAdmin;
	}
	public void addDbAccess(int num){
		if(accessDb.length()==0)
			accessDb+=num;
		else
			accessDb+=","+num;
	}
	public boolean checkDbAccess(String dbNum){
		boolean check = false;
		String[] splitData = accessDb.split(",");
		for(int i=0;i<splitData.length;i++){
			if(dbNum.equals(splitData[i]))
				check=true;
		}
		return check;
	}
	
}
