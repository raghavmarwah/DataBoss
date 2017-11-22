import java.util.*;
import java.io.*;

public class MainServer {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		
		Scanner stdin = new Scanner(System.in);
		
		print("Welcome to DataBoss Server v1.2!");
		
		boolean loginSuccess = false;
		String username;
		String password;
		File userFile = new File("user.list");
		UserTemplate userObject = null;
		
		do{
			
			ObjectInputStream userFileIn = new ObjectInputStream(new FileInputStream(userFile));
			System.out.print("\nEnter your username: ");
			username = stdin.next();
			System.out.print("Enter your password: ");
			password = stdin.next();
			
			try{
				while(true){
					userObject = (UserTemplate) userFileIn.readObject();
					if(username.equals(userObject.returnUserName())&&password.equals(userObject.returnPassword())&&userObject.checkAdmin()==true){
						loginSuccess = true;
						break;
					}
				}
			}catch(EOFException eof){
						
			}finally{
				userFileIn.close();
			}
			if(loginSuccess==false){
				print("\nUnsuccessful, please try again..");
			}
		}while(!loginSuccess);
		
		int userChoice = 0;
		for(;;){
			userChoice = 0;
			print("\nOption Menu");
			print("~~~~~~~~~~~");
			print("1. User profile settings");
			print("2. Database editor");
			print("3. Exit");
			
			System.out.print("\n*Please enter your choice: ");
			userChoice = stdin.nextInt();
			
			if(userChoice==1)
				userSettings();
			else if(userChoice==2)
				dbSettings();
			else if(userChoice==3)
				System.exit(0);
			else{
				stdin.nextLine();
				print("\nInvalid option selected! Press enter\\return key to continue.");
				stdin.nextLine();
			}
			
		}
	}
	public static void userSettings() throws IOException, ClassNotFoundException{
		Scanner stdin = new Scanner(System.in);
		int userChoice = 0;
			
		for(;;){
			userChoice = 0;
			print("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			print("\n1. Create a new user profile");
			print("2. Modify database privileges");
			print("3. Display user privileges");
			print("4. Back to main menu");
			
			System.out.print("\n*Please enter your choice: ");
			userChoice = stdin.nextInt();
			
			if(userChoice==1)
				createUserProfile();
			else if(userChoice==2)
				userPrivileges();
			else if(userChoice==3)
				dispUserPrivileges();
			else if(userChoice==4)
				return;
			else{
				stdin.nextLine();
				print("\nInvalid option selected! Press enter\\return key to continue.");
				stdin.nextLine();
			}
		}
	}
	public static void createUserProfile() throws IOException, ClassNotFoundException{

		Scanner stdin = new Scanner(System.in);
		String usname;
		String pass;
		char isAdminChar;
		boolean isAdmin;
		
		
		print("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.print("\nEnter username: ");
		usname = stdin.next();
		System.out.print("Enter password: ");
		pass = stdin.next();
		System.out.print("Is this user an administrator? (y/n): ");
		isAdminChar = stdin.next().toLowerCase().charAt(0);
		
		if(isAdminChar=='y')
			isAdmin = true;
		else
			isAdmin = false;
		
		UserTemplate tempUser = new UserTemplate(usname, pass, isAdmin);
		File userFile = new File("user.list");
		if(userFile.exists()){
			AppendingObjectOutputStream userFileOut = new AppendingObjectOutputStream(new FileOutputStream(userFile, true));
			userFileOut.writeObject(tempUser);
			userFileOut.close();
		}
		else{
			ObjectOutputStream userFileOut = new ObjectOutputStream(new FileOutputStream(userFile, true));
			userFileOut.writeObject(tempUser);
			userFileOut.close();
		}
		print("\nSuccessfully added the user!");
		return;
		
	}
	public static void userPrivileges() throws ClassNotFoundException, IOException{

		print("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		Scanner stdin = new Scanner(System.in);
		File dbFile = new File("db.list");
		File userFile = new File("user.list");
		File tempFile = new File("user.temp");
		String userName;
		String accessListInput;
		boolean userFound = false;
		
		if(dbFile.exists() && userFile.exists()){
			DbTemplate dbObject = null;
			UserTemplate userObject = null;
			ObjectInputStream dbFileIn = new ObjectInputStream(new FileInputStream(dbFile));
			print("\nAll available databases:\n");
			int count = 1;
			try{
				while(true){
					dbObject = (DbTemplate) dbFileIn.readObject();
					print(count+++". "+dbObject.returnDbName());
				}
			}catch(EOFException eof){
	
			}finally{
				dbFileIn.close();
			}
			System.out.print("\n*Enter username: ");
			userName = stdin.next();
			ObjectInputStream userFileIn = new ObjectInputStream(new FileInputStream(userFile));
			count=1;
			try{
				while(true){
					userObject = (UserTemplate) userFileIn.readObject();
					if(count==1){
						ObjectOutputStream userFileOut = new ObjectOutputStream(new FileOutputStream(tempFile, true));
						if(userObject.returnUserName().equals(userName)){
							userFound = true;
							System.out.print("User can access the following DBs: ");
							print(userObject.returnAccessDb());
							System.out.print("\nEnter DB name to add to privilege, use commas for more than one: ");
							stdin.nextLine();
							accessListInput = stdin.nextLine();
							String[] splitData = accessListInput.split(",");
							for(int i=0;i<splitData.length;i++){
								userObject.addDbAccess(splitData[i].replaceAll("\\s",""));
							}
						}
						userFileOut.writeObject(userObject);
						userFileOut.close();
					}
					else{
						AppendingObjectOutputStream userFileOut = new AppendingObjectOutputStream(new FileOutputStream(tempFile, true));
						if(userObject.returnUserName().equals(userName)){
							userFound = true;
							System.out.print("User can access the following DBs: ");
							print(userObject.returnAccessDb());
							System.out.print("\nEnter DB name to add to privilege, use commas for more than one: ");
							stdin.nextLine();
							accessListInput = stdin.nextLine();
							String[] splitData = accessListInput.split(",");
							for(int i=0;i<splitData.length;i++){
								userObject.addDbAccess(splitData[i].replaceAll("\\s",""));
							}
						}
						userFileOut.writeObject(userObject);
						userFileOut.close();
					}
					count++;
				}
			}catch(EOFException eof){
	
			}
			if(userFound==false){
				print("No user with that username exists!");
			}
			userFileIn.close();
			userFile.delete();
			tempFile.renameTo(userFile);
		}
		else{
			print("\nNo data available!!");
		}
		return;
	}
	public static void dispUserPrivileges() throws ClassNotFoundException, IOException{
		File userFile = new File("user.list");
		if(userFile.exists()){
			UserTemplate userObject = null;
			ObjectInputStream userFileIn = new ObjectInputStream(new FileInputStream(userFile));
			print("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			print("\nAll user and their privileges:\n");
			int count = 1;
			try{
				while(true){
					userObject = (UserTemplate) userFileIn.readObject();
					print(count+++". "+userObject.returnUserName()+": "+userObject.returnAccessDb());
				}
			}catch(EOFException eof){
	
			}finally{
				userFileIn.close();
			}
		}
		else{
			print("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			print("\nNo data available!!");
		}
	}
	
	public static void dbSettings() throws IOException, ClassNotFoundException{
		
		Scanner stdin = new Scanner(System.in);
		int userChoice = 0;
			
		for(;;){
			userChoice = 0;
			print("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			print("\n1. Create a new database");
			print("2. Add table to existing database");
			print("3. Display all DBs");
			print("4. Back to main menu");
			
			System.out.print("\n*Please enter your choice: ");
			userChoice = stdin.nextInt();
			
			if(userChoice==1)
				createDatabase();
			else if(userChoice==2)
				addTable();
			else if(userChoice==3)
				displayAllDbs();
			else if(userChoice==4)
				return;
			else{
				stdin.nextLine();
				print("\nInvalid option selected! Press enter\\return key to continue.");
				stdin.nextLine();
			}
		}
	}
	//createDatabase() adds a DB to the db.list file.
	public static void createDatabase() throws IOException{
		
		Scanner stdin = new Scanner(System.in);
		System.out.print("\nEnter a name for your database: ");
		String dbName = stdin.next();
		dbName = Character.toUpperCase(dbName.charAt(0))+dbName.substring(1);
		
		File file = new File("db.list");
		DbTemplate dbObject;
		
		if(file.exists()){
			AppendingObjectOutputStream dbFileOut = new AppendingObjectOutputStream(new FileOutputStream(file, true));
			dbObject = new DbTemplate(dbName);
			dbFileOut.writeObject(dbObject);
			dbFileOut.close();
		}
		else{
			ObjectOutputStream dbFileOut = new ObjectOutputStream(new FileOutputStream(file, true));
			dbObject = new DbTemplate(dbName);
			dbFileOut.writeObject(dbObject);
			dbFileOut.close();
		}
		print("\nSuccessfully created the database!");
		return;
		
	}
	//addTable() displays all DBs and lets you add a table to the selected DB.
	public static void addTable() throws IOException, ClassNotFoundException{
		
		Scanner stdin = new Scanner(System.in);
		File file = new File("db.list");
		File file2 = new File("db.temp");
		
		if(file.exists()){
			DbTemplate dbObject = null;
			ObjectInputStream dbFileIn = new ObjectInputStream(new FileInputStream(file));
			print("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			print("\nList of available databases:\n");
			int count = 1;
			try{
				while(true){
					dbObject = (DbTemplate) dbFileIn.readObject();
					print(count+++". "+dbObject.returnDbName());
				}
			}catch(EOFException eof){
	
			}finally{
				dbFileIn.close();
			}
			System.out.print("\n*Please enter DB number: ");
			int selectedDb = stdin.nextInt();
			String selectedDbName = "";
			System.out.print("\n*Enter table name: ");
			String newTableName = stdin.next();
			newTableName = Character.toUpperCase(newTableName.charAt(0))+newTableName.substring(1);
			
			ObjectInputStream dbFileIn2 = new ObjectInputStream(new FileInputStream(file));
			int count2 = 1;
			try{
				while(true){
					dbObject = (DbTemplate) dbFileIn2.readObject();
					if(count2==1){
						ObjectOutputStream dbFileOut = new ObjectOutputStream(new FileOutputStream(file2, true));
						if(count2==selectedDb){
							dbObject.addTableToDb(newTableName);
							selectedDbName = dbObject.returnDbName();
						}
						dbFileOut.writeObject(dbObject);
						dbFileOut.close();
					}
					else{
						AppendingObjectOutputStream dbFileOut = new AppendingObjectOutputStream(new FileOutputStream(file2, true));
						if(count2==selectedDb){
							dbObject.addTableToDb(newTableName);
							selectedDbName = dbObject.returnDbName();
						}
						dbFileOut.writeObject(dbObject);
						dbFileOut.close();
					}
					count2++;
				}
			}catch(EOFException eof){
	
			}finally{
				dbFileIn2.close();
				file.delete();
				file2.renameTo(file);
			}
			CreateTable createTable = new CreateTable(selectedDbName+"_"+newTableName);
		}
		else{
			print("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			print("\nNo data available!!");
		return;
		}
		
	}
	public static void displayAllDbs() throws IOException, ClassNotFoundException{
		File file = new File("db.list");
		if(file.exists()){
			DbTemplate dbObject = null;
			ObjectInputStream dbFileIn = new ObjectInputStream(new FileInputStream(file));
			print("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			print("\nAll available databases:\n");
			int count = 1;
			try{
				while(true){
					dbObject = (DbTemplate) dbFileIn.readObject();
					print(count+++". "+dbObject.returnDbName()+": "+dbObject.displayTables());
				}
			}catch(EOFException eof){
	
			}finally{
				dbFileIn.close();
			}
		}
		else{
			print("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			print("\nNo data available!!");
		}
	}
	public static void print(String data){
		System.out.println(data);
	}

}
