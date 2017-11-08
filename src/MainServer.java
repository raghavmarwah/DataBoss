import java.util.*;
import java.io.*;

public class MainServer {

	public static void main(String[] args) {
		
		Scanner stdin = new Scanner(System.in);
		
		System.out.println("Welcome to DataBoss Server v1.2!\n");
		int userChoice = 0;
		do{
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
			
		}while(userChoice!=3);
	}
	public static void userSettings(){
		
	}
	public static void dbSettings(){
		
	}
	public static void print(String data){
		System.out.println(data);
	}

}
