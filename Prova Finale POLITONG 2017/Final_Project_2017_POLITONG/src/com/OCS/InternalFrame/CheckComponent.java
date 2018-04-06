package com.OCS.InternalFrame;

/*
 * 
 * Author: Mingju Li 10574864 876508
 * 
 * Initialize the internal frame of check component
 * This is for the system administrator to view the information of component
 * 
 * */

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.OCS.Dao.*;
import com.OCS.Dao.model.*;

public class CheckComponent extends JInternalFrame{
	private static final long serialVersionUID=1L;
	
	private JTable table;
	private JComboBox conditionName;
	
	private String tableHead[]={"ID", "NAME", "TYPE", "PRICE"};
	private String type[]={"Stock","Processor", "Memory","Graphics","Color","Screen"};
	
	public CheckComponent() {
		super();
		setIconifiable(true);
		setResizable(false);
		setClosable(true);
		setTitle("Check Component");
		setBounds(10, 10, 460, 300);
		setLayout(new GridBagLayout());
		setVisible(true);

		table=new JTable();
		table.setEnabled(false);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		final DefaultTableModel defaultTable=(DefaultTableModel) table.getModel();
		defaultTable.setColumnIdentifiers(tableHead);
		
		final JScrollPane scrollPane=new JScrollPane(table);
		final GridBagConstraints gridBagConstraint=new GridBagConstraints();
		gridBagConstraint.gridy=1;
		gridBagConstraint.gridx=0;
		gridBagConstraint.weighty=1.0;
		gridBagConstraint.anchor=GridBagConstraints.NORTH;
		gridBagConstraint.insets=new Insets(0, 10, 0, 10);
		gridBagConstraint.fill=GridBagConstraints.BOTH;
		gridBagConstraint.gridwidth=6;
		getContentPane().add(scrollPane, gridBagConstraint);
		
		getContentPane().add(new JLabel(" Select a type"),
				new GetConstraint(0, 0, 1, 1, false));
		
		conditionName=new JComboBox();
		conditionName.setModel(new DefaultComboBoxModel(type));
		getContentPane().add(conditionName,
				new GetConstraint(1, 0, 1, 30, true));
		
		final JButton queryButton = new JButton("Search");
		queryButton.addActionListener(new ActionListener(){
			public void actionPerformed(final ActionEvent e){
				String type=conditionName.getSelectedItem().toString();
				List<Component> list = DBCheckComponent.selectType(type);
				GetUpdateTable.UpdateComponent(list, defaultTable);
			}
		});
		getContentPane().add(queryButton,
				new GetConstraint(4, 0, 1, 1, false));
		
		/*
		 * Show the specific type of the components
		 * This could be divided into different types as Memory, Graphics, ...
		 * */
		final JButton showAllButton = new JButton("Show All");
		showAllButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				List<Component> list = DBCheckComponent.select();
				GetUpdateTable.UpdateComponent(list, defaultTable);
			}
		});
		getContentPane().add(showAllButton,
				new GetConstraint(5, 0, 1, 1, false));
		
		/*
		 * Show all the components
		 * */
		
		showAllButton.doClick();
	}
}
