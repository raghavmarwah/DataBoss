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
				File driverFile = new File("Driver"+className+".java");
				File infoFile = new File(className+".info");
				
				/*
				 *	Generating the string to store to the file.
				 *	The string stores a class to write to the file.
				 *	The class provides a data structure to mimic a table.
				 *
				 *	dataToWrite: stores the data for the template class file.
				 *	dataToWrite2: stores the data for a class that creates an ArrayList of the template class file.
				 *	dataTOWrite3: stores number of attributes and name of each attribute field for the table. 
				 */
				
				String dataToWrite = "import java.io.Serializable;\n\npublic class "+className+" implements Serializable{\n\n";
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
				
				String dataToWrite2 = "import java.io.*;\nimport java.util.*;\n\n"+"public class Driver"+className+"{\n";
				dataToWrite2+="\tpublic static void main(String[] args) throws IOException{\n";
				dataToWrite2+="\t\tif(args[0].compareTo(\"0\")==0){\n";
				dataToWrite2+="\t\t\t"+className+" temp = new "+className+"(";
				for(int i=0, j=1;i<MAX_ATT;i++){
					if(rowCheck[i].isSelected()){
						if(rowCombo[i].getSelectedIndex()==0){
							if(j==1)
								dataToWrite2+=("Integer.parseInt(args["+j+"])");
							else
								dataToWrite2+=(", Integer.parseInt(args["+j+"])");
						}
						else if(rowCombo[i].getSelectedIndex()==1){
							if(j==1)
								dataToWrite2+=("Float.parseFloat(args["+j+"])");
							else
								dataToWrite2+=(", Float.parseFloat(args["+j+"])");
						}
						else if(rowCombo[i].getSelectedIndex()==2){
							if(j==1)
								dataToWrite2+=("(args["+j+"]).chatAt(0)");
							else
								dataToWrite2+=(", (args["+j+"].charAt(0)");
						}
						else{
							if(j==1)
								dataToWrite2+=("args["+j+"])");
							else
								dataToWrite2+=(", args["+j+"]");
						}
						j++;
					}
				}
				dataToWrite2+=");\n";
				dataToWrite2+="\t\t\tObjectOutputStream binaryFile = new ObjectOutputStream(new FileOutputStream(\"DB_"+className+".dat\"));\n";
				dataToWrite2+="\t\t\tbinaryFile.writeObject(temp);\n";
				dataToWrite2+="\t\t\tbinaryFile.close();\n";
				dataToWrite2+="\t\t}\n";
				dataToWrite2+="\t}\n}";
				
				String dataToWrite3 = numAttributes+"";
				for(int i=0;i<MAX_ATT;i++){
					if(rowCheck[i].isSelected())
						dataToWrite3+=(","+rowName[i].getText());
				}
				
				
				try {
					
					FileWriter classFileWriter = new FileWriter(classFile);
					classFileWriter.write(dataToWrite);
					classFileWriter.close();
					
					FileWriter driverFileWriter = new FileWriter(driverFile);
					driverFileWriter.write(dataToWrite2);
					driverFileWriter.close();
					
					FileWriter infoFileWriter = new FileWriter(infoFile);
					infoFileWriter.write(dataToWrite3);
					infoFileWriter.close();
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

}
