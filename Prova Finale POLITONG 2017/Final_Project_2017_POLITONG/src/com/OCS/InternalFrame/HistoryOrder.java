package com.OCS.InternalFrame;

/*
 * 
 * Author: Mingju Li 10574864 876508
 * 
 * Initialize the internal frame of check all the order
 * (ORDERED, DELIVERED, CONFIRMED, CANCELLED)
 * 
 * Order State:
 * 0:ORDER SAVED
 * 1:ORDER DELIVERED
 * 2:ORDER DELIVERY CONFIRMED
 * 3:ORDER CANCELLED
 * 
 * */

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

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

public class HistoryOrder extends JInternalFrame{
	private static final long serialVersionUID=1L;
	
	private JTable table;

	private JComboBox conditionName;
	
	private String[] tableHead = new String[]{"Selected","State", "ID", "Date", "Pirce", "Payment","Address"};
	private boolean[] edit = {false,false,false, false,false,false,false};
	
	public HistoryOrder() {
		super();
		setIconifiable(true);
		setResizable(false);
		setClosable(true);
		setTitle("History Order");
		setBounds(10, 10, 460, 300);
		setLayout(new GridBagLayout());
		setVisible(true);

		table = new JTable();
		table.setEnabled(false);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		final DefaultTableModel defaultTable = new DefaultTableModel(){
			public Class<?> getColumnClass(int column){
				switch(column){
					case 0:return Boolean.class;
					case 1:return String.class;
					case 2:return String.class;
					case 3:return Timestamp.class;
					case 4:return String.class;
					case 5:return String.class;
					case 6:return String.class;
					default:return String.class;
					}
				}
			public boolean isCellEditable(int row, int col){
				 return edit[col];
			}
		};
		
		
		table.setModel(defaultTable);
		defaultTable.setColumnIdentifiers(tableHead);//把表头设置为每栏的标示
		
		final JScrollPane scrollPane=new JScrollPane(table);
		final GridBagConstraints gridBagConstraint=new GridBagConstraints();
		gridBagConstraint.gridy=1;
		gridBagConstraint.gridx=0;
		gridBagConstraint.weighty=1.0;
		gridBagConstraint.anchor=GridBagConstraints.NORTH;
		gridBagConstraint.insets=new Insets(0, 10, 0, 10);
		gridBagConstraint.fill=GridBagConstraints.BOTH;
		gridBagConstraint.gridwidth=10;
		getContentPane().add(scrollPane, gridBagConstraint);

		getContentPane().add(new JLabel(" Select order state"),
				new GetConstraint(0, 0, 1, 1, false));
		
		conditionName = new JComboBox();
		conditionName.setModel(new DefaultComboBoxModel(new String[]{"0-Not shipped",
				"1-Not Confirmed", "2-Confirm received", "3-Cancelled"}));
		getContentPane().add(conditionName,
				new GetConstraint(1, 0, 1, 30, true));
		
		final JButton queryButton = new JButton("Search");
		getContentPane().add(queryButton,
				new GetConstraint(4, 0, 1, 1, false));
		queryButton.addActionListener(new ActionListener(){
			public void actionPerformed(final ActionEvent e) {
				String state=conditionName.getSelectedItem().toString();
				List<Order> list=null;
				switch(state){
				case "0-Not shipped":
					list = DBEditOrder.getOrderwithState("0");
					break;
				case "1-Not Confirmed":
					list = DBEditOrder.getOrderwithState("1");
					break;
				case "2-Confirm received":
					list = DBEditOrder.getOrderwithState("2");
					break;
				case "3-Cancelled":
					list = DBEditOrder.getOrderwithState("3");
					break;
				}
				GetUpdateTable.Update(list,defaultTable);
			}
		});
		
		final JButton showAllButton = new JButton("Show All");
		showAllButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				List<Order> list = DBEditOrder.getOrderList();
				GetUpdateTable.Update(list,defaultTable);
			}
		});
		//setupComponet(showAllButton, 5, 0, 1, 1, false);
		getContentPane().add(showAllButton,
				new GetConstraint(5, 0, 1, 1, false));
		showAllButton.doClick();
	}

}
