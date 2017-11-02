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
	int numAttributes = 0; //Number of attributes in the current table!
	
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
	String[] javaDataTypes = {"int","float","char","String"};
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
		createTable.addActionListener(this);
		
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
						numAttributes++;
					}
					else{
						rowCombo[i].setEnabled(false);
						rowName[i].setEnabled(false);
						numAttributes--;
					}
				}
			}
		}
		else if(source instanceof JButton){
			if(source==createTable){
				
				String className = Character.toUpperCase(tableName.getText().charAt(0))+tableName.getText().substring(1);
				File classFile = new File(className+".java");
				
				/*
				 *	Generating the string to store to the file.
				 *	The string stores a class to write to the file.
				 *	The class provides a data structure to mimic a table.
				 */
				
				String dataToWrite = "public class "+className+"{\n\n";
				for(int i=0;i<MAX_ATT;i++){
					if(rowCheck[i].isSelected())
						dataToWrite+=("\tprivate "+javaDataTypes[rowCombo[i].getSelectedIndex()]+" "+rowName[i].getText()+";\n");
				}
				dataToWrite+="\n\tpublic "+className+"(";
				for(int i=0;i<MAX_ATT;i++){
					if(rowCheck[i].isSelected()){
						if(i==0)
							dataToWrite+=(javaDataTypes[rowCombo[i].getSelectedIndex()]+" "+rowName[i].getText().substring(0,1)+i);
						else
							dataToWrite+=(" ,"+javaDataTypes[rowCombo[i].getSelectedIndex()]+" "+rowName[i].getText().substring(0,1)+i);
					}
				}
				dataToWrite+="){\n";
				for(int i=0;i<MAX_ATT;i++){
					if(rowCheck[i].isSelected())
						dataToWrite+=("\t\t"+rowName[i].getText()+"="+rowName[i].getText().substring(0,1)+i+";\n");
				}
				dataToWrite+="\t}\n";
				for(int i=0;i<MAX_ATT;i++){
					if(rowCheck[i].isSelected()){
						dataToWrite+="\n\tpublic "+javaDataTypes[rowCombo[i].getSelectedIndex()]+" return_"+rowName[i].getText()+"(){\n";
						dataToWrite+="\t\treturn "+rowName[i].getText()+";\n\t}";
					}
				}
				dataToWrite+="\n\n}";
				
				
				try {
					
					FileWriter classFileWriter = new FileWriter(classFile);
					classFileWriter.write(dataToWrite);
					classFileWriter.close();
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

}
