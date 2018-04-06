package com.OCS.InternalFrame;

/*
 * 
 * Author: Mingju Li 10574864 876508
 * 
 * Initialize the internal frame of edit computer
 * This is for the system administrator to edit the information of the computer
 * 
 * Computer element:
 * -ID
 * -Name
 * -Type
 * -Price
 * 
 * Configuration Information-For each computer having specific configuration:
 * -Color
 * -Size
 * -Stock
 * -Memory
 * -Graphics
 * -Processor
 * 
 * 
 * */

import javax.swing.JInternalFrame;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


import com.OCS.Dao.*;
import com.OCS.Dao.model.*;

public class EditComputer extends JInternalFrame{
	
	private static final long serialVersionUID=1L;
	
	private JLabel computerID=new JLabel("ID");
	private JTextField textFieldID;
	
	private JLabel computerName=new JLabel("Name");
	private JTextField textFieldName;
	
	private JLabel computerPrice=new JLabel("Price");
	private JTextField textFieldPrice;
	
	private JLabel computerPicture=new JLabel();
	private JTextField textFieldPicture;
	//The picture of a computer could be left empty
	
	private JLabel computerType=new JLabel("Type");
	private JComboBox comboboxType;
	
	private JLabel computerConfig=new JLabel("Configuration");
	private JRadioButton defaultButton=new JRadioButton("Default");
	private JRadioButton customButton=new JRadioButton("Custom");
	
	private JLabel configColor = new JLabel("Color");
	private JComboBox comboboxColor;
	
	private JLabel configSize = new JLabel("Size");
	private JComboBox comboboxSize;
	
	private JLabel configStock = new JLabel("Stock");
	private JComboBox comboboxStock;
	
	private JLabel configMemory = new JLabel("Memory");
	private JComboBox comboboxMemory;
	
	private JLabel configGraphics = new JLabel("Graphics");
	private JComboBox comboboxGraphics;
	
	private JLabel configProcessor = new JLabel("Processor");
	private JComboBox comboboxProcessor;
	
	private JButton addButton=new JButton("Add");
	private JButton deleteButton=new JButton("Delete");
	private JButton resetButton=new JButton("Reset");
	
	private String type[]={"DESKTOP","LAPTOP", "SERVER"};
	
	public EditComputer() {

		super();
		setIconifiable(true);
		setResizable(false);
		setClosable(true);
		setTitle("Add Computer");
		setBounds(10, 10, 460, 300);
		setLayout(new GridBagLayout());
		setVisible(true);
		
		getContentPane().add(computerID,
				new GetConstraint(0, 0, 1, 0, false));
		textFieldID=new JTextField();
		getContentPane().add(textFieldID,
				new GetConstraint(1, 0, 5, 200, true));
		
		getContentPane().add(computerName,
				new GetConstraint(0, 1, 1, 0, false));
		textFieldName=new JTextField();
		getContentPane().add(textFieldName,
				new GetConstraint(1, 1, 5, 100, true));

		getContentPane().add(computerPrice,
				new GetConstraint(0, 2, 1, 0, false));
		textFieldPrice=new JTextField();
		getContentPane().add(textFieldPrice,
				new GetConstraint(1, 2, 2, 100, true));
		
		getContentPane().add(computerType,
				new GetConstraint(3,2,1,0,false));
		comboboxType=new JComboBox();
		comboboxType.setModel(new DefaultComboBoxModel(type));
		comboboxType.setPreferredSize(new Dimension(120, 21));
		getContentPane().add(comboboxType,
				new GetConstraint(4, 2, 1, 0, true));
		
		//////////////////////////////////////////////////////////
		
		getContentPane().add(computerConfig,
				new GetConstraint(0,3,1,0,false));
		ButtonGroup bg=new ButtonGroup();
		bg.add(defaultButton);
		bg.add(customButton);
		defaultButton.setSelected(true);;
		getContentPane().add(defaultButton,
				new GetConstraint(3,3,1,0,false));
		getContentPane().add(customButton,
				new GetConstraint(4,3,1,0,false));
		//////////////////////////////////////////////////////////
		
		getContentPane().add(configColor,
				new GetConstraint(0,4,1,0,false));
		comboboxColor=new JComboBox();
		List<String> colorlist=searchitemlist("Color");
		setupCombo(comboboxColor,colorlist);		
		comboboxColor.setPreferredSize(new Dimension(120, 21));
		getContentPane().add(comboboxColor,
				new GetConstraint(1, 4, 1, 0, true));

		getContentPane().add(configSize,
				new GetConstraint(3,4,1,0,false));
		comboboxSize=new JComboBox();
		List<String> sizelist=searchitemlist("Screen");
		setupCombo(comboboxSize,sizelist);
		comboboxSize.setPreferredSize(new Dimension(120, 21));

		getContentPane().add(comboboxSize,
				new GetConstraint(4, 4, 1, 0, true));


		getContentPane().add(configStock,
				new GetConstraint(0,5,1,0,false));
		comboboxStock=new JComboBox();
		List<String> stocklist=searchitemlist("Stock");
		setupCombo(comboboxStock,stocklist);
		comboboxStock.setPreferredSize(new Dimension(120, 21));

		getContentPane().add(comboboxStock,
				new GetConstraint(1, 5, 1, 0, true));
		

		getContentPane().add(configMemory,
				new GetConstraint(3,5,1,0,false));
		comboboxMemory=new JComboBox();
		List<String> memorylist=searchitemlist("Memory");
		setupCombo(comboboxMemory,memorylist);
		comboboxMemory.setPreferredSize(new Dimension(120, 21));
		getContentPane().add(comboboxMemory,
				new GetConstraint(4, 5, 1, 0, true));

		getContentPane().add(configGraphics,
				new GetConstraint(0,6,1,0,false));
		comboboxGraphics=new JComboBox();
		List<String> graphicslist = searchitemlist("Graphics");
		setupCombo(comboboxGraphics,graphicslist);
		comboboxGraphics.setPreferredSize(new Dimension(120, 21));
		getContentPane().add(comboboxGraphics,
				new GetConstraint(1, 6, 1, 0, true));
		
		getContentPane().add(configProcessor,
				new GetConstraint(3,6,1,0,false));
		comboboxProcessor=new JComboBox();
		List<String> processorlist=searchitemlist("Processor");
		setupCombo(comboboxProcessor,processorlist);
		comboboxProcessor.setPreferredSize(new Dimension(120, 21));
		getContentPane().add(comboboxProcessor,
				new GetConstraint(4, 6, 1, 0, true));
		
////////////////////////The picture is an option attribute for a computer////////////////////////
		
		computerPicture.setText("Picture£º");
		getContentPane().add(computerPicture,
				new GetConstraint(0, 7, 1, 0, false));
		textFieldPicture=new JTextField();
		getContentPane().add(textFieldPicture,
				new GetConstraint(1, 7, 5, 100, true));
		
/////////////////////////////////////////////////////////////////////////////////////////////////
		
		comboboxType.setSelectedItem(null);
		comboboxColor.setSelectedItem(null);
		comboboxSize.setSelectedItem(null);
		comboboxStock.setSelectedItem(null);
		comboboxMemory.setSelectedItem(null);
		comboboxGraphics.setSelectedItem(null);
		comboboxProcessor.setSelectedItem(null);
		
//////////////////////////////Button Add Action Listener///////////////////////////////////////
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				if (textFieldID.getText().equals("")
						|| textFieldName.getText().equals("")
						|| textFieldPrice.getText().equals("")) {
					JOptionPane.showMessageDialog(EditComputer.this,
							"Information Incomplete!", "Add Computer", JOptionPane.ERROR_MESSAGE);
					return;
					/*Check whether the information of the computer is complete
					 * */
				}
				try {
					String id=textFieldID.getText();
					if (DBEditComputer.exists(id)) {
						JOptionPane.showMessageDialog(EditComputer.this,
								"Computer Already Exists!", "Failure",
								JOptionPane.WARNING_MESSAGE);
						return;
						/*Check whether the Computer ID has already been used
						 * */
					}		
				} catch (Exception e1) {
					System.out.println("Error!Fail to check the Computer ID-EditComputer");
				}
				
				Computer computer=new Computer();
				computer.setId(textFieldID.getText());
				computer.setName(textFieldName.getText());
				computer.setType(comboboxType.getSelectedItem().toString());
				computer.setPrice(Float.parseFloat(textFieldPrice.getText()));
				String pic=textFieldPicture.getText();
				if(pic==""){
					pic="No Picture";
				}
				computer.setPicture(pic);
				
				/*If the default button is selected 
				 * The information of the components would be left empty because it is default configuration
				 * */
				
				/*
				if(defaultButton.isSelected()){
					try{
						if (DBAddcomputer.save(computer)) {
							JOptionPane.showMessageDialog(EditComputer.this,
									"This computer (default configuration) is saved successfully!", "Operation Success",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
					} catch(Exception e1){
						System.out.println("Fail to save the default computer-EditComputer");
					}
				}else{
				*/
				
					/*If the custom Button is selected
					 * The information of the component would be saved because it is customed  
					 * */
					String id_computer = textFieldID.getText();
					
					String name_color = comboboxColor.getSelectedItem().toString();
					String id_color = DBCheckComponent.getComponentID(name_color);
					
					String name_size = comboboxSize.getSelectedItem().toString();
					String id_size = DBCheckComponent.getComponentID(name_size);

					String name_stock = comboboxStock.getSelectedItem().toString();
					String id_stock = DBCheckComponent.getComponentID(name_stock);

					String name_memory = comboboxMemory.getSelectedItem().toString();
					String id_memory = DBCheckComponent.getComponentID(name_memory);
		
					String name_graphics = comboboxGraphics.getSelectedItem().toString();
					String id_graphics = DBCheckComponent.getComponentID(name_graphics);

					String name_processor = comboboxProcessor.getSelectedItem().toString();
					String id_processor = DBCheckComponent.getComponentID(name_processor);
					
					try {
						if (DBEditComputer.save(computer)&&
								DBEditComputer.savecomputerconfig(id_computer, id_color)&&
								DBEditComputer.savecomputerconfig(id_computer, id_size)&&
								DBEditComputer.savecomputerconfig(id_computer, id_stock)&&
								DBEditComputer.savecomputerconfig(id_computer, id_memory)&&
								DBEditComputer.savecomputerconfig(id_computer, id_graphics)&&
								DBEditComputer.savecomputerconfig(id_computer, id_processor)) {
							JOptionPane.showMessageDialog(EditComputer.this,
									"This computer & its configuration is saved successfully!", "Operation Success",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}			
					} catch (Exception e1) {
						System.out.println("Fail to save the customed computer-EditComputer");
					}
				/*
				}
				*/
			}
		});
		getContentPane().add(addButton,
				new GetConstraint(1, 8, 1, 1, false));
		
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				if (textFieldID.getText().equals("")) {
					JOptionPane.showMessageDialog(EditComputer.this,
							"Information Incomplete! ID must be submitted to delete a computer",
							"Delete Computer", JOptionPane.ERROR_MESSAGE);
					return;
					/*The Administrator must input the computer ID to delete the computer
					 * */
				}
				try {
					String id = textFieldID.getText();
					if (!DBEditComputer.exists(id)) {
						JOptionPane.showMessageDialog(EditComputer.this,
								"Computer Not Found", "Failure",
								JOptionPane.WARNING_MESSAGE);
						return;
						/*Check whether this computer exists in the database
						 * */
					}		
				} catch (Exception e1) {
					System.out.println("Error! Fail to Find the computer-EditComputer");
				}
				
				try {			
					String id = textFieldID.getText();
					if (DBEditComputer.delete(id)) {
						JOptionPane.showMessageDialog(EditComputer.this,
								"Successfully Deleted", "Success!",
								JOptionPane.INFORMATION_MESSAGE);
						return;
						/*Delete the computer information from the database
						 * */
					}
				} catch (Exception e1) {
					System.out.println("Error! Fail to delete the computer-EditComputer");
				}
			}
		});
		getContentPane().add(deleteButton,
				new GetConstraint(3, 8, 1, 1, false));
		
		getContentPane().add(resetButton,
				new GetConstraint(5, 8, 1, 1, false));
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				textFieldID.setText("");
				textFieldName.setText("");
				textFieldPrice.setText("");
				textFieldPicture.setText("");
				comboboxType.setSelectedItem(null);
				comboboxColor.setSelectedItem(null);
				comboboxSize.setSelectedItem(null);
				comboboxStock.setSelectedItem(null);
				comboboxMemory.setSelectedItem(null);
				comboboxGraphics.setSelectedItem(null);
				comboboxProcessor.setSelectedItem(null);
			/*Reset all the information of the comboBox and text field
			 * */
			}
		});
		
		setVisible(true);
	}		
	
	public List<String> searchitemlist(String name){
		    List<String> itemlist = null ;
		    itemlist = DBCheckComponent.getComponentNameList(name);
		    return itemlist;		
	}
	
	public void setupCombo (JComboBox combo, List<String> itemlist){
		Iterator iterator = itemlist.iterator();
		while(iterator.hasNext()){
			String item = (String) iterator.next();
			combo.addItem(item);
		}	
	}	
	
}
