package com.OCS.InternalFrame;

/*
 * 
 * Author: Mingju Li 10574864 876508
 * 
 * Initialize the internal frame of check current order
 * This is for the system administrator to view the information of the order which has not been shipped
 * The button of "Ship goods" was initialized and it would change the state of an order in the database
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

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.OCS.Dao.*;
import com.OCS.Dao.model.*;

public class CurrentOrder extends JInternalFrame{
	private static final long serialVersionUID=1L;
	
	private JTable table;
	
	private String tableHead[]={"Select","State", "No", "Date", "Price", "Payment","Address"};
	private Boolean edit[]={true,false,false,false, false,false,false};
	
	public CurrentOrder() {		
		super();
		setIconifiable(true);
		setResizable(false);
		setClosable(true);
		setTitle("Current Order");
		setBounds(10, 10, 460, 300);
		setLayout(new GridBagLayout());
		setVisible(true);

		table = new JTable();
		table.setEnabled(true);
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
		defaultTable.setColumnIdentifiers(tableHead);
		
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
		
		List<Order> orderList= DBEditOrder.getActiveOrderList();
		GetUpdateTable.Update(orderList,defaultTable);
		
		getContentPane().add(new JLabel("                                  "),
				new GetConstraint(1, 2, 1, 1, false));
		
		final JButton shipButton = new JButton("Confirm to ship");
		shipButton.addActionListener(new ActionListener(){
			public void actionPerformed(final ActionEvent e){
				  for(int j=0;j<table.getRowCount();j++){
					 Boolean checked = Boolean.valueOf(table.getValueAt(j,0).toString()) ;
					 if(checked){
						String id_order = table.getValueAt(j, 2).toString();
						DBEditOrder.ChangeOrderState(id_order,"1");									
					 }
				  }
				  List<Order> orderList= DBEditOrder.getActiveOrderList();
				  GetUpdateTable.Update(orderList,defaultTable);
			}	
		});
		getContentPane().add(shipButton,
				new GetConstraint(7, 2, 1, 1, false));	
		
		final JButton detailButton = new JButton("Check Detail");
		detailButton.addActionListener(new ActionListener(){
			public void actionPerformed(final ActionEvent e){
				  for(int j=0;j<table.getRowCount();j++){
					 Boolean checked = Boolean.valueOf(table.getValueAt(j,0).toString()) ;
					 if(checked){
						String id_order = table.getValueAt(j, 2).toString();
						List<OrderInDetail> details=DBEditOrder.getDetiallist(id_order);
						
						Iterator iterator = details.iterator();
						
						String msg="";
						
						while (iterator.hasNext()) {
							OrderInDetail order = (OrderInDetail) iterator.next();

							msg=msg+"No:       "+order.getorderID()+"\n";
							msg=msg+"OrderID:  "+order.getorderID()+"\n";
							msg=msg+"Product:  "+order.getcomputerID()+"\n";
							msg=msg+"Price:    "+order.getprice()+"\n";
							msg=msg+"Color:    "+order.getcolor()+"\n";
							msg=msg+"Memeory:  "+order.getmemory()+"\n";
							msg=msg+"Stock:    "+order.getstock()+"\n";
							msg=msg+"Processor:"+order.getprocessor()+"\n";
							msg=msg+"Screen:   "+order.getsize()+"\n";
							msg=msg+"Graphics: "+order.getgraphics()+"\n";	
						}
						
						JOptionPane.showMessageDialog(null,msg);
						//show the message
					 }
				  }
				  List<Order> orderList= DBEditOrder.getActiveOrderList();
				  GetUpdateTable.Update(orderList,defaultTable);
			}	
		});
		getContentPane().add(detailButton,
				new GetConstraint(6, 2, 1, 1, false));
		
	}
}
