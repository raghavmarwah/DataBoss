import java.util.*;
import java.io.*;

public class MainServer {

	public static void main(String[] args) throws IOException {
		
		Scanner stdin = new Scanner(System.in);
		
		System.out.println("Welcome to DataBoss Server v1.2!");
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
	public static void userSettings(){
		
	}
	public static void dbSettings() throws IOException{
		
		Scanner stdin = new Scanner(System.in);
		int userChoice = 0;
		
		print("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			
		for(;;){
			userChoice = 0;
			print("\n1. Create a new database");
			print("2. Add table to existing database");
			print("3. Back to main menu");
			
			System.out.print("\n*Please enter your choice: ");
			userChoice = stdin.nextInt();
			
			if(userChoice==1)
				createDatabase();
			else if(userChoice==2)
				addTable();
			else if(userChoice==3)
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
		dbName = Character.toUpperCase(dbName.charAt(0))+dbName.substring(1).toLowerCase();
		
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
		print("\nSuccessfully created the table!");
		return;
		
	}
	//addTable() displays all DBs and lets you add a table to the selected DB.
	public static void addTable() throws IOException{
		
		File file = new File("db.list");
		DbTemplate dbObject;
		ObjectInputStream dbFileIn = new ObjectInputStream(new FileInputStream(file));
		print("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		print("\nList of available databases:\n");
		int count = 1;
		try{
			while(true){
				dbObject = (DbTemplate) dbFileIn.readObject();
				print(count+++". "+dbObject.returnDbName());
			}
		}catch(EOFException | ClassNotFoundException eof){

		}finally{
			dbFileIn.close();
		}
		return;
		
	}
	public static void print(String data){
		System.out.println(data);
	}

}
