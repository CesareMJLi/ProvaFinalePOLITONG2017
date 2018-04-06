package com.OCS.InnerFace;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.OCS.Dao.model.*;
import com.OCS.client.MainBox;

public class ActiveOrderCheck extends JInternalFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable Table;
	private Socket SocketClient;
	private DataInputStream In=null;
	private DataOutputStream Out=null;
	private ObjectInputStream InBean=null;
	private String accept;
	public ActiveOrderCheck() {
		super();
		this.SocketClient = MainBox.getSocketClient();
		setIconifiable(true);
		setClosable(true);
		setTitle("Active Order");
		getContentPane().setLayout(new GridBagLayout());
		setBounds(100, 100, 600, 375);

		Table = new JTable();
		Table.setEnabled(true);
		Table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		final DefaultTableModel dftm = new DefaultTableModel(){
					/**
					 * 
					 */
			private static final long serialVersionUID = 1L;
			public Class<?> getColumnClass(int column){
				switch(column){
				case 0:
					return Boolean.class;
				case 1:
					return String.class;
				case 2:
					return String.class;
				case 3:
					return Timestamp.class;
				case 4:
					return String.class;
				case 5:
					return String.class;
				case 6:
					return String.class;
					default:
						return String.class;
				}
			}
			boolean[] editables = {true, false, false, false, false, false, false};
			public boolean isCellEditable(int row, int col)  {
			      return editables[col];
			}				
		};
		Table.setModel(dftm);
		String[] tableHeads = new String[]{"Choose","Status", "Number", "Date", "Price", "Payment","Address"};
		dftm.setColumnIdentifiers(tableHeads);
		for(int i=0;i<7;i++){
			TableColumn column = null;
		    column = Table.getColumnModel().getColumn(i);
		    if(i==0)column.setPreferredWidth(30);
		    else if(i==1)column.setPreferredWidth(100);
		    else if(i==2)column.setPreferredWidth(150);
		    else if(i==5)column.setPreferredWidth(100);
		    else if(i==6)column.setPreferredWidth(150);
		    else if(i==3)column.setPreferredWidth(100);
		}
		final JScrollPane scrollPane = new JScrollPane(Table);
		final GridBagConstraints gridBagConstraints_6 = new GridBagConstraints();
		gridBagConstraints_6.weighty = 1.0;
		gridBagConstraints_6.anchor = GridBagConstraints.NORTH;
		gridBagConstraints_6.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints_6.fill = GridBagConstraints.BOTH;
		gridBagConstraints_6.gridwidth = 9;
		gridBagConstraints_6.gridy = 0;
		gridBagConstraints_6.gridx = 0;
		getContentPane().add(scrollPane, gridBagConstraints_6);
////////////////////////////////////////////////////////////////////////////////////////////
		setupComponet(new JLabel("                 "), 2, 1, 2, 1, false);
		setupComponet(new JLabel("                 "), 5, 1, 2, 1, false);
		setupComponet(new JLabel("          "), 0, 1, 1, 1, false);
		setupComponet(new JLabel("          "), 8, 1, 1, 1, false);
		final JButton showButton = new JButton();
		showButton.addActionListener(new ActionListener(){
			public void actionPerformed(final ActionEvent e){				
				  try {
					  Out = new DataOutputStream(SocketClient.getOutputStream());
						Out.writeUTF("SHOWACTIVEORDER");
					    Out.flush();
					    Out.writeUTF(MainBox.getCzyStateLabel().getText());
					    Out.flush();
					InBean = new ObjectInputStream(SocketClient.getInputStream());
					@SuppressWarnings("unchecked")
					List<Order> list = (List<Order>)InBean.readObject();
				    updateTable(list, dftm);	
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}   
			}
		});
		setupComponet(showButton, 1, 1, 1, 1, false);
		showButton.setText("Flash");
		showButton.doClick();
		
		final JButton receiveButton = new JButton();
		receiveButton.addActionListener(new ActionListener(){
			public void actionPerformed(final ActionEvent e){
				  for(int j=0;j<Table.getRowCount();j++){
					 Boolean checked = Boolean.valueOf(Table.getValueAt(j,0).toString()) ;
					 if(checked){
						String id_order = Table.getValueAt(j, 2).toString();
						String state_order = Table.getValueAt(j, 1).toString();
						try {
							if(state_order.equals("shipping")){
								Out = new DataOutputStream(SocketClient.getOutputStream());
								Out.writeUTF("ARRIVEORDER");
							    Out.flush();
							    Out.writeUTF(id_order);
							    Out.flush();
							    In = new DataInputStream(SocketClient.getInputStream());
					    	    accept = In.readUTF();			    	   
					    	    if (accept.equals("True")){
					    	    	JOptionPane.showMessageDialog(ActiveOrderCheck.this,
											"Order'"+id_order+"'Delivered successfully.", "Delivered successfully",
											JOptionPane.INFORMATION_MESSAGE);
					    	    }
							}else {
								JOptionPane.showMessageDialog(ActiveOrderCheck.this,
											"Order'"+id_order+"'Delivered failed.", "Delivered failed",
											JOptionPane.ERROR_MESSAGE);
							}
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}											
					 }
				  }
				  showButton.doClick();				    
			}
		});
		setupComponet(receiveButton, 4, 1, 1, 1, false);
		receiveButton.setText("Delivered successfully");
	
		final JButton cancelButton = new JButton();
		cancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(final ActionEvent e){
				  for(int j=0;j<Table.getRowCount();j++){
					 Boolean checked = Boolean.valueOf(Table.getValueAt(j,0).toString()) ;
					 if(checked){
						String id_order = Table.getValueAt(j, 2).toString();
						String state_order = Table.getValueAt(j, 1).toString();
						try {
							if(state_order.equals("to be shipped")){
								Out = new DataOutputStream(SocketClient.getOutputStream());
								Out.writeUTF("CANCELORDER");
							    Out.flush();
							    Out.writeUTF(id_order);
							    Out.flush();
							    In = new DataInputStream(SocketClient.getInputStream());
					    	    accept = In.readUTF();			    	   
					    	    if (accept.equals("True")){
					    	    	JOptionPane.showMessageDialog(ActiveOrderCheck.this,
											"Order'"+id_order+"'Cancel successful.", "Cancel successful",
											JOptionPane.INFORMATION_MESSAGE);
					    	    }
							}else {
								JOptionPane.showMessageDialog(ActiveOrderCheck.this,
											"Order'"+id_order+"'Shipping, Cancel failed.", "Cancel failed",
											JOptionPane.ERROR_MESSAGE);
							}
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}											
					 }
				  }
				  showButton.doClick(); 
			}			
		});
		setupComponet(cancelButton, 7, 1, 1, 1, false);
		cancelButton.setText("Cancel Order");
}
/////////////////////////////////////////////////////////////////////////////////
	private void setupComponet(JComponent component, int gridx, int gridy,
			int gridwidth, int ipadx, boolean fill) {
		final GridBagConstraints gridBagConstrains = new GridBagConstraints();
		gridBagConstrains.gridx = gridx;
		gridBagConstrains.gridy = gridy;
		if (gridwidth > 1)
			gridBagConstrains.gridwidth = gridwidth;
		if (ipadx > 0)
			gridBagConstrains.ipadx = ipadx;
		gridBagConstrains.insets = new Insets(0, 1, 0, 1);
		if (fill)
			gridBagConstrains.fill = GridBagConstraints.HORIZONTAL;
		getContentPane().add(component, gridBagConstrains);
	}
	
	private void updateTable(List<Order> list, final DefaultTableModel dftm) {
		int num = dftm.getRowCount();
		for (int i = 0; i < num; i++)
			dftm.removeRow(0);
		Iterator iterator = list.iterator();
		int i=0;
		while (iterator.hasNext()) {
			Order order = (Order) iterator.next();
			dftm.addRow(new Object[0]);
			dftm.setValueAt(false,i,0);
			dftm.setValueAt(order.getID(), i, 2);
			dftm.setValueAt(order.getPrice(), i, 4);
			dftm.setValueAt(order.getDatetime(), i, 3);
			dftm.setValueAt(order.getPayment(), i, 5);
			dftm.setValueAt(order.getDelivery(), i, 6);
			String state = order.getState();
			if(state.equals("0")){
				dftm.setValueAt("to be shipped", i, 1);}
			else if (state.equals("1")){
				dftm.setValueAt("shipping", i, 1);}
			else if (state.equals("2")){
				dftm.setValueAt("finished", i, 1);}
			else if (state.equals("4")){
				dftm.setValueAt("canceled", i, 1);}			
			i++;	
		}
	}
}
