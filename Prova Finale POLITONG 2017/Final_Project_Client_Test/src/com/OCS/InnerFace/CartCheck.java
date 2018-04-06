package com.OCS.InnerFace;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.OCS.SocketClient.PayProcess;
import com.OCS.Dao.model.*;
import com.OCS.client.MainBox;

public class CartCheck extends JInternalFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable Table;
	public Socket SocketClient = null;
	private DataInputStream In = null;
	private DataOutputStream Out = null;
	private ObjectInputStream InBean = null;
	private ObjectOutputStream OutBean = null;
	private String accept;
	public static List<OrderInDetail> OrderDetialList;
	private static Order order;
	private double Price;
	private JLabel totalprice ;
	private double TotalPrice = 0;
	private boolean OldChoose;
	int Selectedrow = -1;
	int Selectedcolumn = -1;
		
	public CartCheck() {
		super();
		SocketClient = MainBox.getSocketClient();
		setIconifiable(true);
		setClosable(true);
		setTitle("Cart");
		getContentPane().setLayout(new GridBagLayout());
		setBounds(100, 100, 650, 375);

		Table = new JTable();
		Table.setEnabled(true);
		Table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		final DefaultTableModel dftm = new DefaultTableModel(){
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;
					public Class<?> getColumnClass(int column){
						switch(column)
						{
						case 0:
							return Boolean.class;
						case 2:
							default:
								return String.class;
						}
					}
					boolean[] editables = {true,false,false,false,false,false,false,false,false,false,false};
					   public boolean isCellEditable(int row, int col)  {
					      return editables[col];
					   }
				};
				Table.setModel(dftm);
		String[] tableHeads = new String[]{ "choose","ID","ComputerID","Price","Num","Color","Size","Desk","Memory","Graphic","CPU"};//添加表头
		dftm.setColumnIdentifiers(tableHeads);  
		TableColumn column = null;		
	    column = Table.getColumnModel().getColumn(0);
	    column.setPreferredWidth(30);
		    Table.addMouseMotionListener(new MouseMotionListener(){
				public void mouseDragged(MouseEvent e) {
					// TODO Auto-generated method stub	
					
				}
				public void mouseMoved(MouseEvent e) {
					// TODO Auto-generated method stub
					 Selectedrow= Table.rowAtPoint(e.getPoint());
					 Selectedcolumn = Table.columnAtPoint(e.getPoint());									    	
					if (Selectedrow != -1&& Selectedcolumn ==0){						
						TableColumn newColumn = Table.getColumnModel().getColumn(0);
						Price =(Double) Table.getValueAt(Selectedrow, 3);
						int numb =  (Integer) Table.getValueAt(Selectedrow, 4);
						Price = Price*numb;
						final JCheckBox checkbox = new JCheckBox();
						OldChoose = (Boolean) Table.getValueAt(Selectedrow, Selectedcolumn);
						checkbox.addActionListener(new ActionListener(){
							public void actionPerformed(ActionEvent e) {
								boolean newchoose = checkbox.isSelected();								
								if (newchoose==OldChoose){}
								else if(newchoose == true){
									TotalPrice = TotalPrice + Price;
									totalprice.setText("  Total Price: $"+TotalPrice);
									OldChoose = true;
								}
								else{
									TotalPrice = TotalPrice - Price;
									totalprice.setText("  Total Price: $"+TotalPrice);
									OldChoose = false;
								}
							}							
						});
						newColumn.setCellEditor(new DefaultCellEditor(checkbox));
					}
				}						         		               
		     });
///////////////////////////////////////////////////////////////////////////////////////////////
		final JScrollPane scrollPane = new JScrollPane(Table);
		final GridBagConstraints gridBagConstraints_6 = new GridBagConstraints();
		gridBagConstraints_6.weighty = 1.0;
		gridBagConstraints_6.anchor = GridBagConstraints.NORTH;
		gridBagConstraints_6.insets = new Insets(0, 10, 0, 10);
		gridBagConstraints_6.fill = GridBagConstraints.BOTH;
		gridBagConstraints_6.gridwidth = 6;
		gridBagConstraints_6.gridy = 1;
		gridBagConstraints_6.gridx = 0;
		getContentPane().add(scrollPane, gridBagConstraints_6);
		totalprice = new JLabel("  Total Price: $"+TotalPrice);	
		setupComponet(totalprice,2,2,1,2,false);
		final JLabel label = new JLabel();
		setupComponet(label,0,0,4,370,true);
////////////////////////////////////////////////////////////////////////////////////////////
		final JButton showAllButton = new JButton();
		showAllButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				  try {
					Out = new DataOutputStream(SocketClient.getOutputStream());
					Out.writeUTF("SHOWALLBAG");
		    	    Out.flush();
		    	    Out.writeUTF(MainBox.getCzyStateLabel().getText());
		    	    Out.flush();
		    	    InBean = new ObjectInputStream(SocketClient.getInputStream());
		    	    @SuppressWarnings("unchecked")
					List<Bag> list = (List<Bag>)InBean.readObject();
		    	    updateTable(list, dftm);		    	    
		    	    TotalPrice = 0;	 
		    	    totalprice.setText("  Total Price: "+TotalPrice);
						} catch (IOException e1) {
							e1.printStackTrace();
						} catch (ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}		    	  			  
			}
		});
		setupComponet(showAllButton, 5, 0, 1, 1, false);
		showAllButton.setText("Show ALL");
		showAllButton.doClick();

		final JButton deletefrombagButton = new JButton();
		deletefrombagButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				List<Integer> deletelist = new ArrayList<Integer>();
				for(int j=0;j<Table.getRowCount();j++){
						 Boolean checked = Boolean.valueOf(Table.getValueAt(j,0).toString()) ;
						 if(checked){
								int id_bag = (Integer) Table.getValueAt(j, 1);
								try {
										Out = new DataOutputStream(SocketClient.getOutputStream());
										Out.writeUTF("DELETEBAG");
									    Out.flush();
									    Out.writeInt(id_bag);
									    Out.flush();
									    In = new DataInputStream(SocketClient.getInputStream());
							    	    accept = In.readUTF();			    	   
							    	    if (accept.equals("True")){
							    	    }							    
								}catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}											
							 }
						  }
						  showAllButton.doClick();
			}
		});
		setupComponet(deletefrombagButton, 5, 2, 1, 1, false);
		deletefrombagButton.setText("Delete");
////////////////////////////////////////////////////////////////////////////////////////////
		
		final JButton buynowButton = new JButton();
		buynowButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				  order = new Order();
				  SimpleDateFormat df = new SimpleDateFormat("yyMMddHHmm");
				  Timestamp datetime = new Timestamp(System.currentTimeMillis());
				  String client = MainBox.getCzyStateLabel().getText();				  
				  String id_order = client+df.format(datetime);				  
				  String state = "0";		  
				  OrderDetialList = new ArrayList<OrderInDetail>();
				  Map<String,Integer> computermap = new HashMap<String,Integer>();
				  Map<String,Integer> componentmap = new HashMap<String,Integer>();        			   
				  ////////////////////////////////////////////////////////////////////////////////////////
				  for(int j=0;j<Table.getRowCount();j++){
					 Boolean checked = Boolean.valueOf(Table.getValueAt(j,0).toString());
					 if(checked){
						String id_computer = Table.getValueAt(j, 2).toString();
						int number = (Integer) Table.getValueAt(j, 4);
						double price = (Double) Table.getValueAt(j, 3);
						String color = Table.getValueAt(j,5).toString();
						String size = Table.getValueAt(j, 6).toString();
						String stock = Table.getValueAt(j,7).toString();
						String memory = Table.getValueAt(j, 8).toString();
						String graphic = Table.getValueAt(j,9).toString();
						String processor = Table.getValueAt(j,10).toString();
															
						OrderInDetail order_detial = new OrderInDetail();
						order_detial.setcomputerID(id_computer);
						order_detial.setprice(price);
						order_detial.setcolor(color);
						order_detial.setsize(size);
						order_detial.setmemory(memory);
						order_detial.setgraphics(graphic);
						order_detial.setstock(stock);
						order_detial.setprocessor(processor);
						order_detial.setorderID(id_order);
						order_detial.setnumber(number);
						OrderDetialList.add(order_detial);
		
						int p = computermap.getOrDefault(id_computer, 0);
						if(p==0){computermap.put(id_computer, number);}
						else{computermap.put(id_computer, number+p);}
				
						p = componentmap.getOrDefault(color,0);
						if(p==0){componentmap.put(color, number);}
						else{componentmap.put(color, number+p);}
						p = componentmap.getOrDefault(size,0);
						if(p==0){componentmap.put(size, number);}
						else{componentmap.put(size, number+p);}
						p = componentmap.getOrDefault(stock,0);
						if(p==0){componentmap.put(stock, number);}
						else{componentmap.put(stock, number+p);}
						p = componentmap.getOrDefault(memory,0);
						if(p==0){componentmap.put(memory, number);}
						else{componentmap.put(memory, number+p);}
						p = componentmap.getOrDefault(graphic,0);
						if(p==0){componentmap.put(graphic, number);}
						else{componentmap.put(graphic, number+p);}
						p = componentmap.getOrDefault(processor,0);
						if(p==0){componentmap.put(processor, number);}
						else{componentmap.put(processor, number+p);}
						
					 }
				  }
				  ////////////////////////////////////////////////////////////////////////////////////////
				  order.setClient(client);			
				  order.setDatetime(datetime);				 
				  order.setID(id_order);				 
				  order.setState(state);
				  order.setPrice(TotalPrice);
				  if(OrderDetialList.size()!=0){
					 
					  try {
						  /*
						  Out = new DataOutputStream(SocketClient.getOutputStream());
						Out.writeUTF("ORDER");
			    	    Out.flush();
			    	    OutBean = new ObjectOutputStream(SocketClient.getOutputStream());						
						OutBean.writeObject(computermap);
						OutBean.flush();
						OutBean.writeObject(componentmap);
						OutBean.flush();
			    	    In = new DataInputStream(SocketClient.getInputStream());
			    	    accept = In.readUTF();			    	   
					    	    if (accept.equals("True")){	
					    	    */	    	    	
					    	    	new Thread(new PayProcess(order,OrderDetialList,computermap,componentmap)).start();	
					    	    	deletefrombagButton.doClick();
					/*    	   
				  	}
					    	    else
					    	    {	   JOptionPane.showMessageDialog(CartCheck.this,
										"商品库存不足.", "交易失败",
										JOptionPane.ERROR_MESSAGE);
					    	    } */
							} catch (Exception e1) {
								e1.printStackTrace();
							}
				  }
				  	
			}
		});
		setupComponet(buynowButton, 4, 2, 1, 1, false);
		buynowButton.setText("Buy Now");

	}///////////////////////////////////////////////////////////////////////////////		

	private void setupComponet(JComponent component, int gridx, int gridy,
			int gridwidth, int ipadx, boolean fill) {
		final GridBagConstraints gridBagConstrains = new GridBagConstraints();
		gridBagConstrains.gridx = gridx;
		gridBagConstrains.gridy = gridy;
		if (gridwidth > 1)
			gridBagConstrains.gridwidth = gridwidth;
		if (ipadx > 0)
			gridBagConstrains.ipadx = ipadx;
		gridBagConstrains.insets = new Insets(5, 1, 3, 1);
		if (fill)
			gridBagConstrains.fill = GridBagConstraints.HORIZONTAL;
		getContentPane().add(component, gridBagConstrains);
	}
	
	private void updateTable(List<Bag> list, final DefaultTableModel dftm) {
		int num = dftm.getRowCount();
		for (int i = 0; i < num; i++)
			dftm.removeRow(0);	
		Iterator iterator = list.iterator();

		int i=0;
		while (iterator.hasNext()) {
			Bag bag = (Bag) iterator.next();
			dftm.addRow(new Object[0]);
			dftm.setValueAt(false,i,0);
			dftm.setValueAt(bag.getID(), i, 1);
			dftm.setValueAt(bag.getcomputerID(), i, 2);
			dftm.setValueAt(bag.getprice(), i, 3);
			dftm.setValueAt(bag.getnumber(), i, 4);
			dftm.setValueAt(bag.getcolor(), i, 5);
			dftm.setValueAt(bag.getsize(), i, 6);
			dftm.setValueAt(bag.getstock(), i, 7);
			dftm.setValueAt(bag.getmemory(), i, 8);
			dftm.setValueAt(bag.getgraphics(), i, 9);
			dftm.setValueAt(bag.getprocessor(), i, 10);
			i++;	
		}
	}
	public static Order getOrder(){
		return order;
	}
	public static List<OrderInDetail> getdetaillist(){
		return OrderDetialList;
	}	
}

