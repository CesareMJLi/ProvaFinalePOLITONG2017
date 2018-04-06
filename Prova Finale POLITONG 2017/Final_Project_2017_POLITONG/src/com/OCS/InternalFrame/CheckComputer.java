package com.OCS.InternalFrame;

/*
 * 
 * Author: Mingju Li 10574864 876508
 * 
 * Initialize the internal frame of check computer
 * This is for the system administrator to view the information of computer
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

public class CheckComputer extends JInternalFrame{
	private static final long serialVersionUID=1L;
	
	private JTable table;
	private JComboBox conditionName;
	
	private String tableHead[]={"IMAGE","ID", "NAME", "TYPE", "PRICE","COLOR",
			"SIZE","MEMORY","STOCK","GRAPHICS","PROCESSOR"};
	private String type[]={"LAPTOP","DESKTOP", "SERVER"};
	
	public CheckComputer() {
		
		super();
		setIconifiable(true);
		setResizable(false);
		setClosable(true);
		setTitle("Check Computer");
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
		gridBagConstraint.gridx=0;
		gridBagConstraint.gridy=1;
		gridBagConstraint.weighty=1.0;
		gridBagConstraint.anchor=GridBagConstraints.NORTH;
		gridBagConstraint.insets=new Insets(0, 10, 0, 10);
		gridBagConstraint.fill=GridBagConstraints.BOTH;
		gridBagConstraint.gridwidth=6;
		getContentPane().add(scrollPane, gridBagConstraint);
		
		getContentPane().add(new JLabel(" Select a type"),
				new GetConstraint( 0, 0, 1, 1, false));
		
		conditionName=new JComboBox();
		conditionName.setModel(new DefaultComboBoxModel(type));
		getContentPane().add(conditionName,
				new GetConstraint(1, 0, 1, 30, true));


		final JButton queryButton=new JButton("Search");
		queryButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				String type=conditionName.getSelectedItem().toString();
				List<Computer> list = DBCheckComputer.selectwithcondition(type);		
				GetUpdateTable.UpdateComputer(list, defaultTable);	
			}
		});
		getContentPane().add(queryButton,
				new GetConstraint(4, 0, 1, 1, false));
		
		final JButton showAllButton = new JButton("Show All");
		showAllButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				List<Computer> list = DBCheckComputer.select();		
				GetUpdateTable.UpdateComputer(list, defaultTable);	
			}
		});
		getContentPane().add(showAllButton,
				new GetConstraint(5, 0, 1, 1, false));
		
		showAllButton.doClick();
	}
}



