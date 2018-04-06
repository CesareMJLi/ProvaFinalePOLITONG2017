package com.OCS.InternalFrame;

/*
 * 
 * Author: Mingju Li 10574864 876508
 * 
 * Initialize the internal frame of edit component
 * This is for the system administrator to edit the information of the component
 * 
 * Component element:
 * -ID
 * -Name
 * -Type
 * -Price
 * 
 * */

import javax.swing.JInternalFrame;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.OCS.Dao.*;
import com.OCS.Dao.model.*;


public class EditComponent extends JInternalFrame{
	
	private static final long serialVersionUID = 1L;
	
	private JLabel componentID=new JLabel(" ID ");
	private JTextField textFieldID;
	private String id;
	
	private JLabel componentName=new JLabel("Name");
	private JTextField textFieldName;
	
	private JLabel componentPrice=new JLabel("Price");
	private JTextField textFieldPrice;
	
	private JLabel componentType=new JLabel("Type");
	private JComboBox comboBoxType;
	
	private JButton addButton=new JButton("Add");
	private JButton deleteButton=new JButton("Delete");
	private JButton resetButton=new JButton("Reset");
	
	private String type[]={"Stock","Memory", "Graphics","Color","Screen","Processor"};
	
	public EditComponent() {
		super();
		setIconifiable(true);
		setResizable(false);
		setClosable(true);
		setTitle("Edit Component");
		setBounds(10, 10, 460, 300);
		setLayout(new GridBagLayout());
		setVisible(true);		
		
		////////////////////////////////////////////////////////
		getContentPane().add(componentID,
				new GetConstraint(0, 0, 1, 0, false));
		
		textFieldID = new JTextField();
		getContentPane().add(textFieldID,
				new GetConstraint(1, 0, 5, 200, true));
		
		////////////////////////////////////////////////////////
		getContentPane().add(componentName,
				new GetConstraint(0, 1, 1, 0, false));
		
		textFieldName = new JTextField();
		getContentPane().add(textFieldName,
				new GetConstraint(1, 1, 5, 100, true));
		
		////////////////////////////////////////////////////////
		getContentPane().add(componentPrice,
				new GetConstraint(3, 2, 1, 0, false));
		
		textFieldPrice = new JTextField();
		getContentPane().add(textFieldPrice,
				new GetConstraint(4, 2, 1, 100, true));
		////////////////////////////////////////////////////////
		getContentPane().add(componentType,
				new GetConstraint(0,2,1,0,false));
		
		comboBoxType = new JComboBox();
		comboBoxType.setPreferredSize(new Dimension(120, 21));
		comboBoxType.setModel(new DefaultComboBoxModel(type));
		getContentPane().add(comboBoxType,
				new GetConstraint( 1, 2, 1, 0, true));
				
		getContentPane().add(addButton,
				new GetConstraint(1, 7, 1, 1, false));
		
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				if (textFieldID.getText().equals("")
					|| textFieldName.getText().equals("")
						|| textFieldPrice.getText().equals("")) {
							JOptionPane.showMessageDialog(EditComponent.this,
									"Information Incomplete!", "Add Component", JOptionPane.ERROR_MESSAGE);
							return;
						}
				/*Verify the input information
				 * The input information must be complete to execute the next process
				 * */
						try {
							id = textFieldID.getText();
							if (DBEditComponent.exists(id)) {
								JOptionPane.showMessageDialog(EditComponent.this,
										"Component Already Exits!", "Failure!",
										JOptionPane.WARNING_MESSAGE);
								return;
							}	
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						/*Verify whether this component has been in the database
						 * (or whether this ID has been used by another component)
						 * */
						
						Component component = new Component();
						component.setId(textFieldID.getText());
						component.setName(textFieldName.getText());
						component.setType(comboBoxType.getSelectedItem().toString());
						component.setPrice(Float.parseFloat(textFieldPrice.getText()));
						try {					
							if (DBEditComponent.save(component)) {
								JOptionPane.showMessageDialog(EditComponent.this,
										"Successfully Add", "Success!",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							/*Save this component in the table
							 * */
						} catch (Exception e1) {
							System.out.println("Error! Failt to save the component-EditComponent");
						}
					}
				});
		/*
		 * If the administrator would like to add a new component into the component database
		 * The information must be complete 
		 * Including: Component-ID Component-Name Component-Type Component-Price
		 * */
		
		getContentPane().add(deleteButton,
				new GetConstraint(2, 7, 1, 1, false));
		
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				if (textFieldID.getText().equals("")) {
							JOptionPane.showMessageDialog(EditComponent.this,
									"Information Incomplete! ID must be submitted to delete a component",
									"Delete Component", JOptionPane.ERROR_MESSAGE);
							return;
						}
						/*The administrator must input a component ID to delete it from the database
						 * */
						try {
							id = textFieldID.getText();	
							if (!DBEditComponent.exists(id)) {
								JOptionPane.showMessageDialog(EditComponent.this,
										"Component Not Found!", "Failure!",
										JOptionPane.WARNING_MESSAGE);
								return;
							}	
						} catch (Exception e1) {
							System.out.println("Error!Fail to find the component-EditComponent");
						}
						/*Check whether this component exist
						 * */
			
						try {				
								if (DBEditComponent.delete(id)) {
									JOptionPane.showMessageDialog(EditComponent.this,
											"Successfully Deleted", "Success!",
											JOptionPane.INFORMATION_MESSAGE);
									return;
								}
							} catch (Exception e1) {
								System.out.println("Error!Fail to delete the component-EditComponent");
							}
						}
				});
		/*
		 * If the administrator would like to delete a component from the database
		 * the administrator should input the ID in the ID area
		 * the application would search the ID 
		 * if ID is not submitted-INCOMPLETE INFORMATION
		 * if fail to find a match component-NOT FOUND
		 * execute the delete operation
		 * */
		getContentPane().add(resetButton,
				new GetConstraint(5, 7, 1, 1, false));
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				textFieldID.setText("");
				textFieldName.setText("");
				textFieldPrice.setText("");	
				/*Reset all the text field to be empty
				 * */
			}
		});		
		
		setVisible(true);
	}		
}




