import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class DisplayTable extends JFrame implements ActionListener, ListSelectionListener{
	
	final int MAX_ATT = 6; //Maximum number of attributes allowed per table!
	int numAttributes = 0; //Number of attributes in the current table!
	static String classNameGlobal;
	
	//ArrayList
	ArrayList<String> dataList = new ArrayList<>();
	
	//GUI Members
	Container con = getContentPane();
	JList<String> dataOut = new JList<>();
	JScrollPane sp = new JScrollPane(dataOut);
	JButton showAll = new JButton("Show All");
	JButton refineSearch = new JButton("Refine!");
	JLabel whereLabel = new JLabel("WHERE:");
	JTextField whereData = new JTextField();
	String[] operators = {"==","<=",">=","<",">","!="};
	JComboBox<String> whereItem = new JComboBox<>();
	JComboBox<String> whereOperator = new JComboBox<>(operators);
	
	//Add Data members
	JPanel dataPanel = new JPanel(new GridLayout(2,6,10,10));
	JPanel buttonPanel = new JPanel(new BorderLayout());
	JButton addData = new JButton("Add Data!");
	
	//Label Array
	JLabel[] attributeName = new JLabel[6];
	
	//Text-field Array
	JTextField[] dataToSave = new JTextField[6];
	
	
	public DisplayTable(String className) throws IOException{
		super("Table: "+className);
		setVisible(true);
		setBounds(0,0,850,460);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		classNameGlobal = className;
		
		sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		sp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		attach(sp,10,10,500,300);
		attach(whereLabel,520,70,60,20);
		attach(whereItem,580,70,90,20);
		attach(whereOperator,680,70,50,20);
		attach(whereData,740,70,100,20);
		attach(refineSearch,580,110,100,20);
		attach(showAll,690,110,100,20);
		
		//Getting info about the table from .info file
				File infoFile = new File(className+".info");
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
				attach(dataPanel,20,350,640,70);
				attach(addData,680,350,150,70);
				addData.addActionListener(this);
		
		revalidate();
		repaint();
		
	}
	public void attach(Component o, int x, int y, int w, int h){
		o.setBounds(x,y,w,h);
		con.add(o);
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		
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
				
				String cmd = "java Driver"+classNameGlobal+argString;
				Runtime.getRuntime().exec(cmd);
			
			} catch (Exception e1) {
				System.out.println(e1.getMessage());
			}
			for(int i=0;i<numAttributes;i++){
				dataToSave[i].setText("");
			}
			JOptionPane.showMessageDialog(this, "Data addition successful!");
		}
	}

}
