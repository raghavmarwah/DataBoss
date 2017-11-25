import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class ClientLogin extends JFrame implements ActionListener, ListSelectionListener{

	//GUI Members
	Container con = getContentPane();
	JLabel loginLabel = new JLabel("Enter your credentials!");
	JLabel usnameLabel = new JLabel("Username: ");
	JLabel passLabel = new JLabel("Password: ");
	JLabel selectDbLabel = new JLabel("Select a DB");
	JLabel selectTableLabel = new JLabel("Select a table");
	JTextField usnameInput = new JTextField();
	JTextField passInput = new JTextField();
	JButton loginButton = new JButton("Login");
	JButton displayTable = new JButton("Display Table");
	JButton addData = new JButton("Add Data");
	JList<String> dbList = new JList<>();
	JList<String> tableList = new JList<>();
	JScrollPane sp = new JScrollPane(dbList);
	JScrollPane sp2 = new JScrollPane(tableList);
	
	public ClientLogin(){
		super("Client Login");
		setVisible(true);
		setBounds(0,0,850,380);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		displayTable.setEnabled(false);
		
		sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		sp2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		displayTable.addActionListener(this);
		addData.addActionListener(this);
		loginButton.addActionListener(this);
		
		dbList.setModel(new DefaultListModel<String>());
		tableList.setModel(new DefaultListModel<String>());
		dbList.addListSelectionListener(this);
		tableList.addListSelectionListener(this);
		
		attach(loginLabel,20,120,200,20);
		attach(usnameLabel,20,160,100,20);
		attach(usnameInput,110,160,120,20);
		attach(passLabel,20,190,100,20);
		attach(passInput,110,190,120,20);
		attach(loginButton,80,230,100,20);
		attach(selectDbLabel,260,20,80,20);
		attach(sp,260,50,200,280);
		attach(selectTableLabel,480,20,150,20);
		attach(sp2,480,50,200,280);
		attach(displayTable,690,160,130,20);
		attach(addData,690,190,130,20);
		
		revalidate();
		repaint();
	}
	public void attach(Component o, int x, int y, int w, int h){
		o.setBounds(x,y,w,h);
		con.add(o);
	}
	
	public static void main(String[] args){
		new ClientLogin();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(source instanceof JButton){
			if(source==loginButton){

				DefaultListModel<String> tempListModel  = (DefaultListModel<String>) dbList.getModel();
				tempListModel.removeAllElements();
				boolean loginSuccess = false;
				String username = usnameInput.getText().toString();
				String password = passInput.getText().toString();
				File userFile = new File("user.list");
				UserTemplate userObject = null;
				
				try{
					ObjectInputStream userFileIn = new ObjectInputStream(new FileInputStream(userFile));				
					try{
						while(true){
							userObject = (UserTemplate) userFileIn.readObject();
							if(username.equals(userObject.returnUserName())&&password.equals(userObject.returnPassword())){
								loginSuccess = true;
								break;
							}
						}
					}catch(EOFException eof){
								
					}finally{
						userFileIn.close();
					}
				}catch(IOException | ClassNotFoundException ex){
					
				}
				if(loginSuccess==false){
					JOptionPane.showMessageDialog(this, "User profile not found!");
					usnameInput.setText("");
					passInput.setText("");
				}
				if(userObject.isAdmin==true){
					try{
						File file = new File("db.list");
						DbTemplate dbObject = null;
						ObjectInputStream dbFileIn = new ObjectInputStream(new FileInputStream(file));
						try{
							while(true){
								dbObject = (DbTemplate) dbFileIn.readObject();
								tempListModel.addElement(dbObject.returnDbName());
							}
						}catch(EOFException eof){
				
						}catch(ClassNotFoundException cnfex){
							
						}
						finally{
							dbFileIn.close();
						}
					}catch(IOException ioex){}
				}
				else{
					String accessibleDbs = userObject.returnAccessDb();
					String[] accessTokens = accessibleDbs.split(",");
					for(int i=0;i<accessTokens.length;i++){
						tempListModel.addElement(accessTokens[i]);
					}
				}
			}
			else if(source==displayTable){
				String selectedDb = dbList.getSelectedValue().toString();
				String selectedTable = tableList.getSelectedValue().toString();
				try{
					new DisplayTable(selectedDb+"_"+selectedTable);
				}catch(IOException ioex){}
			}
		}
	}
	@Override
	public void valueChanged(ListSelectionEvent e) {
		Object source = e.getSource();
		if(source==dbList){
			DefaultListModel<String> tempListModel  = (DefaultListModel<String>) tableList.getModel();
			tempListModel.removeAllElements();
			
			String selectedDb = dbList.getSelectedValue().toString();
			File file = new File("db.list");
			DbTemplate dbObject = null;
			
			try{
				ObjectInputStream dbFileIn = new ObjectInputStream(new FileInputStream(file));
				try{
					while(true){
						dbObject = (DbTemplate) dbFileIn.readObject();
						if(dbObject.returnDbName().equals(selectedDb)){
							break;
						}
					}
				}catch(EOFException eof){
		
				}finally{
					dbFileIn.close();
				}
			}catch(IOException | ClassNotFoundException ex){
				
			}
			String tablesInDb = dbObject.displayTables();
			String[] tablesTokens = tablesInDb.split(",");
			for(int i=0;i<tablesTokens.length;i++){
				tempListModel.addElement(tablesTokens[i]);
			}
			tableList.setSelectedIndex(0);
			displayTable.setEnabled(true);
		}
	}

}
