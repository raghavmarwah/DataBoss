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
	
	public CreateTable(String tableClassName){
		
		super("Create Table");
		setVisible(true);
		setBounds(0,0,600,390);
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
		tableName.setEnabled(false);
		createTable.addActionListener(this);
		
		panel1.setLayout(new GridLayout(6,3,10,10));
		//Adding items to panel 1
		for(int i=0;i<MAX_ATT;i++){
			panel1.add(rowCheck[i]);
			panel1.add(rowCombo[i]);
			panel1.add(rowName[i]);
		}
		
		tableName.setText(tableClassName);
		
		attach(panel1,-100,0,700,300);
		attach(instructLabel,60,320,200,20);
		attach(tableName,200,320,150,20);
		attach(createTable,360,320,150,20);
		
		revalidate();
		repaint();
		
	}
	public void resetAll(){
		for(int i=0;i<MAX_ATT;i++){
			rowCheck[i].setSelected(false);
			rowCombo[i].setSelectedIndex(0);
			rowCombo[i].setEnabled(false);
			rowName[i].setText("");
			rowName[i].setEnabled(false);
			tableName.setText("");
		}
	}
	public void attach(Component o, int x, int y, int w, int h){
		o.setBounds(x,y,w,h);
		con.add(o);
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
				
				//dataToWrite
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
				dataToWrite+="\n\n\t@Override\n\tpublic String toString(){\n\t\treturn (\"\"";
				for(int i=0,j=1;i<MAX_ATT;i++){
					if(rowCheck[i].isSelected()){
						if(j==numAttributes)
							dataToWrite+=(" + "+rowName[i].getText());
						else
							dataToWrite+=(" + "+rowName[i].getText()+" + \",\"");
						j++;
					}
				}
				dataToWrite+=");\n\t}";
				dataToWrite+="\n\n}";
				
				//dataToWrite2
				String dataToWrite2 = "import java.io.*;\nimport java.util.*;\n\n"+"public class Driver"+className+"{\n";
				dataToWrite2+="\tpublic static void main(String[] args) throws IOException, ClassNotFoundException{\n";
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
								dataToWrite2+=(", (args["+j+"]).charAt(0)");
						}
						else{
							if(j==1)
								dataToWrite2+=("args["+j+"]");
							else
								dataToWrite2+=(", args["+j+"]");
						}
						j++;
					}
				}
				dataToWrite2+=");\n";
				dataToWrite2+="\t\t\tFile file = new File(\"DB_"+className+".dat\");\n\t\t\tif(!file.exists()){\n";
				dataToWrite2+="\t\t\t\tObjectOutputStream binaryFile = new ObjectOutputStream(new FileOutputStream(file, true));\n";
				dataToWrite2+="\t\t\t\tbinaryFile.writeObject(temp);\n";
				dataToWrite2+="\t\t\t\tbinaryFile.close();\n\t\t\t}\n\t\t\telse{\n";
				dataToWrite2+="\t\t\t\tAppendingObjectOutputStream binaryFile = new AppendingObjectOutputStream(new FileOutputStream(file, true));\n";
				dataToWrite2+="\t\t\t\tbinaryFile.writeObject(temp);\n";
				dataToWrite2+="\t\t\t\tbinaryFile.close();\n\t\t\t}";
				dataToWrite2+="\t\t}\n";
				dataToWrite2+="\t\telse if(args[0].compareTo(\"1\")==0){\n";
				dataToWrite2+="\t\t\t"+className+" temp;\n";
				dataToWrite2+="\t\t\tObjectInputStream binaryFile = new ObjectInputStream(new FileInputStream(\"DB_"+className+".dat\"));\n";
				dataToWrite2+="\t\t\tPrintWriter textFile = new PrintWriter(\"DB_"+className+".txt\");\n";
				dataToWrite2+="\t\t\ttry{\n\t\t\t\twhile(true){\n\t\t\t\t\ttemp=("+className+")binaryFile.readObject();\n";
				dataToWrite2+="\t\t\t\t\ttextFile.println(temp.toString());\n";
				dataToWrite2+="\t\t\t\t}\n\t\t\t}catch(EOFException eof){}\n\t\t\tbinaryFile.close();\n\t\t\ttextFile.close();\n\t\t}\n";
				dataToWrite2+="\t}\n}";
				
				//dataToWrite3
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
					
					//Compiling the Driver file!
					new JavaCmdRun("javac Driver"+className+".java");
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				resetAll();
				JOptionPane.showMessageDialog(this, className+" table created!!");
				System.exit(0);
			}
		}
	}

}
