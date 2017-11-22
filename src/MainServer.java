import java.util.*;
import java.io.*;

public class MainServer {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		
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
		File file2 = new File("temp.list");
		
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
			System.out.print("\n*Enter table name: ");
			String newTableName = stdin.next();
			newTableName = Character.toUpperCase(newTableName.charAt(0))+newTableName.substring(1);
			
			ObjectInputStream dbFileIn2 = new ObjectInputStream(new FileInputStream(file));
			int count2 = 1;
			try{
				while(true){
					if(count2==1){
						ObjectOutputStream dbFileOut = new ObjectOutputStream(new FileOutputStream(file2, true));
						if(count2==selectedDb){
							dbObject = (DbTemplate) dbFileIn2.readObject();
							dbObject.addTableToDb(newTableName);
							dbFileOut.writeObject(dbObject);
						}
						else{
							dbObject = (DbTemplate) dbFileIn2.readObject();
							dbFileOut.writeObject(dbObject);
						}
						dbFileOut.close();
					}
					else{
						AppendingObjectOutputStream dbFileOut = new AppendingObjectOutputStream(new FileOutputStream(file2, true));
						if(count2==selectedDb){
							dbObject = (DbTemplate) dbFileIn2.readObject();
							dbObject.addTableToDb(newTableName);
							dbFileOut.writeObject(dbObject);
						}
						else{
							dbObject = (DbTemplate) dbFileIn2.readObject();
							dbFileOut.writeObject(dbObject);
						}
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
			CreateTable createTable = new CreateTable(newTableName);
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