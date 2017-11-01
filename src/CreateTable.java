import javax.swing.*;
import javax.swing.border.Border;

import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

@SuppressWarnings("serial")
public class CreateTable extends JFrame implements ActionListener{

	//GUI members
	Container con = getContentPane();
	JPanel panel1 = new JPanel();
	
	//CheckBoxes
	JCheckBox row1Check = new JCheckBox();
	JCheckBox row2Check = new JCheckBox();
	JCheckBox row3Check = new JCheckBox();
	JCheckBox row4Check = new JCheckBox();
	JCheckBox row5Check = new JCheckBox();
	JCheckBox row6Check = new JCheckBox();
	//ComboBoxes
	String[] dataTypesSupported = {"Integer","Decimal","Character","String"};
	JComboBox<String> row1Combo = new JComboBox<>(dataTypesSupported);
	JComboBox<String> row2Combo = new JComboBox<>(dataTypesSupported);
	JComboBox<String> row3Combo = new JComboBox<>(dataTypesSupported);
	JComboBox<String> row4Combo = new JComboBox<>(dataTypesSupported);
	JComboBox<String> row5Combo = new JComboBox<>(dataTypesSupported);
	JComboBox<String> row6Combo = new JComboBox<>(dataTypesSupported);
	//TextFields
	JTextField row1Name = new JTextField(20);
	JTextField row2Name = new JTextField(20);
	JTextField row3Name = new JTextField(20);
	JTextField row4Name = new JTextField(20);
	JTextField row5Name = new JTextField(20);
	JTextField row6Name = new JTextField(20);
	
	public CreateTable(){
		
		super("Create Table");
		setVisible(true);
		setBounds(0,0,600,300);
		setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Border padding = BorderFactory.createEmptyBorder(20,40,20,40);
		panel1.setBorder(padding);
		row1Check.setHorizontalAlignment(JCheckBox.CENTER);
		row2Check.setHorizontalAlignment(JCheckBox.CENTER);
		row3Check.setHorizontalAlignment(JCheckBox.CENTER);
		row4Check.setHorizontalAlignment(JCheckBox.CENTER);
		row5Check.setHorizontalAlignment(JCheckBox.CENTER);
		row6Check.setHorizontalAlignment(JCheckBox.CENTER);
		
		//Disabling items
		row1Combo.setEnabled(false);
		row2Combo.setEnabled(false);
		row3Combo.setEnabled(false);
		row4Combo.setEnabled(false);
		row5Combo.setEnabled(false);
		row6Combo.setEnabled(false);
		
		row1Name.setEnabled(false);
		row2Name.setEnabled(false);
		row3Name.setEnabled(false);
		row4Name.setEnabled(false);
		row5Name.setEnabled(false);
		row6Name.setEnabled(false);
		
		//Adding ActionListener
		row1Check.addActionListener(this);
		row2Check.addActionListener(this);
		row3Check.addActionListener(this);
		row4Check.addActionListener(this);
		row5Check.addActionListener(this);
		row6Check.addActionListener(this);
		
		//Adding items
		panel1.add(row1Check);
		panel1.add(row1Combo);
		panel1.add(row1Name);
		panel1.add(row2Check);
		panel1.add(row2Combo);
		panel1.add(row2Name);
		panel1.add(row3Check);
		panel1.add(row3Combo);
		panel1.add(row3Name);
		panel1.add(row4Check);
		panel1.add(row4Combo);
		panel1.add(row4Name);
		panel1.add(row5Check);
		panel1.add(row5Combo);
		panel1.add(row5Name);
		panel1.add(row6Check);
		panel1.add(row6Combo);
		panel1.add(row6Name);
		panel1.setLayout(new GridLayout(6,3,10,10));
		attach(panel1,-100,0,700,300);
		
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
			if(source==row1Check){
				if(row1Check.isSelected()){
					row1Combo.setEnabled(true);
					row1Name.setEnabled(true);
				}
				else{
					row1Combo.setEnabled(false);
					row1Name.setEnabled(false);
				}
			}
			else if(source==row2Check){
				if(row2Check.isSelected()){
					row2Combo.setEnabled(true);
					row2Name.setEnabled(true);
				}
				else{
					row2Combo.setEnabled(false);
					row2Name.setEnabled(false);
				}
			}
			else if(source==row3Check){
				if(row3Check.isSelected()){
					row3Combo.setEnabled(true);
					row3Name.setEnabled(true);
				}
				else{
					row3Combo.setEnabled(false);
					row3Name.setEnabled(false);
				}
			}
			else if(source==row4Check){
				if(row4Check.isSelected()){
					row4Combo.setEnabled(true);
					row4Name.setEnabled(true);
				}
				else{
					row4Combo.setEnabled(false);
					row4Name.setEnabled(false);
				}
			}
			else if(source==row5Check){
				if(row5Check.isSelected()){
					row5Combo.setEnabled(true);
					row5Name.setEnabled(true);
				}
				else{
					row5Combo.setEnabled(false);
					row5Name.setEnabled(false);
				}
			}
			else if(source==row6Check){
				if(row6Check.isSelected()){
					row6Combo.setEnabled(true);
					row6Name.setEnabled(true);
				}
				else{
					row6Combo.setEnabled(false);
					row6Name.setEnabled(false);
				}
			}
		}
	}

}
