import javax.swing.*;
import javax.swing.border.Border;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

@SuppressWarnings("serial")
public class AddDataToTable extends JFrame implements ActionListener {

	final int MAX_ATT = 6; //Maximum number of attributes allowed per table!
	int numAttributes = 0; //Number of attributes in the current table!
	static String[] argsGlobal;
	
	//GUI Members
	Container con = getContentPane();
	JPanel dataPanel = new JPanel(new GridLayout(2,6,10,10));
	JPanel buttonPanel = new JPanel(new BorderLayout());
	JButton addData = new JButton("Add Data!");
	
	//Label Array
	JLabel[] attributeName = new JLabel[6];
	
	//Text-field Array
	JTextField[] dataToSave = new JTextField[6];
	
	public AddDataToTable(String[] args) throws IOException{
		super("Add Data: "+args[0]);
		setVisible(true);
		setBounds(0,0,690,130);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Getting info about the table from .info file
		File infoFile = new File(args[0]+".info");
		BufferedReader readFile = new BufferedReader(new FileReader(infoFile));
		String fileData = readFile.readLine();
		String[] splitFileData = fileData.split(",");
		readFile.close();
		numAttributes = Integer.parseInt(splitFileData[0]);
		
		//Initializing Object Arrays
		for(int i=0,j=1;i<MAX_ATT;i++){
			if(j<=numAttributes){
				attributeName[i] = new JLabel(splitFileData[j].charAt(0)+splitFileData[j].substring(1));
				attributeName[i].setHorizontalTextPosition(JLabel.CENTER);
				dataToSave[i] = new JTextField(10);
				j++;
			}
			else{
				attributeName[i] = new JLabel("n/a");
				dataToSave[i] = new JTextField(10);
				//Following have been disabled because the attributes were not present in the table!
				attributeName[i].setEnabled(false);
				dataToSave[i].setEnabled(false);
			}
		}
		//Adding the objects to dataPanel
		for(int i=0;i<MAX_ATT;i++){
			dataPanel.add(attributeName[i]);
		}
		for(int i=0;i<MAX_ATT;i++){
			dataPanel.add(dataToSave[i]);
		}
		attach(dataPanel,20,20,500,70);
		attach(addData,540,20,130,70);
		addData.addActionListener(this);
		
		revalidate();
		repaint();
	}
	
	public void attach(Component o, int x, int y, int w, int h){
		o.setBounds(x,y,w,h);
		con.add(o);
	}

	public static void main(String[] args) throws IOException {
		argsGlobal = args;
		new AddDataToTable(args);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(source==addData){
			String argString = " 0";
			for(int i=0;i<numAttributes;i++){
				argString+=" "+dataToSave[i].getText();
			}
			try {
				
				String cmd = "java Driver"+argsGlobal[0]+argString;
				Runtime.getRuntime().exec(cmd);
			
			} catch (IOException e1) {
				System.out.println(e1.getMessage());
			}
			for(int i=0;i<numAttributes;i++){
				dataToSave[i].setText("");
			}
			JOptionPane.showMessageDialog(this, "Data addition successful!");
		}
	}

}
