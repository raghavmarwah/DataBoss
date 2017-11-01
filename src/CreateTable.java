import javax.swing.*;
import javax.swing.border.Border;

import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

@SuppressWarnings("serial")
public class CreateTable extends JFrame implements ActionListener{

	final int MAX_ATT = 6; //Maximum number of attributes allowed per table!
	
	//GUI members
	Container con = getContentPane();
	JPanel panel1 = new JPanel();
	JButton createTable = new JButton("Create Table!");
	JLabel instructLabel = new JLabel("Enter table name: ");
	JTextField tableName = new JTextField(10);
	
	//CheckBox Array
	JCheckBox[] rowCheck = new JCheckBox[MAX_ATT];
	
	//ComboBox Array
	String[] dataTypesSupported = {"Integer","Decimal","Character","String"};
	@SuppressWarnings("unchecked")
	JComboBox<String>[] rowCombo = new JComboBox[MAX_ATT];
	
	//TextField Array
	JTextField[] rowName = new JTextField[MAX_ATT];
	
	public CreateTable(){
		
		super("Create Table");
		setVisible(true);
		setBounds(0,0,600,370);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Border padding = BorderFactory.createEmptyBorder(20,40,20,40);
		panel1.setBorder(padding);
		
		//Initializing all Object arrays
		for(int i=0;i<MAX_ATT;i++){
			rowCheck[i] = new JCheckBox();
			rowCombo[i] = new JComboBox<String>(dataTypesSupported);
			rowName[i] = new JTextField(20);
		}
		
		//Adding ActionListener to CheckBoxes, Disabling TextFields and ComboBoxes
		for(int i=0;i<MAX_ATT;i++){
			rowCheck[i].addActionListener(this);			
			rowCheck[i].setHorizontalAlignment(JCheckBox.CENTER);

			rowCombo[i].setEnabled(false);
			rowName[i].setEnabled(false);
		}
		
		panel1.setLayout(new GridLayout(6,3,10,10));
		//Adding items to panel 1
		for(int i=0;i<MAX_ATT;i++){
			panel1.add(rowCheck[i]);
			panel1.add(rowCombo[i]);
			panel1.add(rowName[i]);
		}
		
		attach(panel1,-100,0,700,300);
		attach(instructLabel,60,320,200,20);
		attach(tableName,200,320,150,20);
		attach(createTable,360,320,150,20);
		
		revalidate();
		repaint();
		
	}
	
	public void attach(Component o, int x, int y, int w, int h){
		o.setBounds(x,y,w,h);
		con.add(o);
	}
	
	public static void main(String[] args){
		new CreateTable();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(source instanceof JCheckBox){
			for(int i=0;i<MAX_ATT;i++){
				if(source==rowCheck[i]){
					if(rowCheck[i].isSelected()){
						rowCombo[i].setEnabled(true);
						rowName[i].setEnabled(true);
					}
					else{
						rowCombo[i].setEnabled(false);
						rowName[i].setEnabled(false);
					}
				}
			}
		}
		else if(source instanceof JButton){
			if(source==createTable){
				
				File file = new File(tableName.getText());
				String dataToWrite = "public class "+tableName.getText()+"{\n";
				try {
					
					FileWriter outputFile = new FileWriter(file);
					
					outputFile.close();
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

}
