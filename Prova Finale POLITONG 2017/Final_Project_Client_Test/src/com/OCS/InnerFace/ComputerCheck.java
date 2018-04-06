package com.OCS.InnerFace;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyVetoException;
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
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import com.OCS.Dao.model.*;
import com.OCS.client.MainBox;
import com.OCS.SocketClient.PayProcess;

public class ComputerCheck extends JInternalFrame{
	private JTable table;
	private JTextField conditionContent;
	//private JComboBox conditionOperation;
	private JComboBox conditionName;

	private JButton button;
	public Socket socketClient = null;
	private DataInputStream in = null;
	private DataOutputStream out = null;
	private ObjectInputStream inBean = null;
	private ObjectOutputStream outBean = null;
	private String accept;
	private List<Computer> list;
	private List<String> computerlist;
	private List<String> componentlist;
	public static List<OrderInDetail> orderdetiallist;
	private static Order order;
	
	private JLabel totalprice ;
	private double TotalPrice = 0;
	
	private String oldvalue;
	private boolean oldchoose;
	int Selectedrow = -1;
	int Selectedcolumn = -1;
		
	public ComputerCheck() {
		super();
		socketClient = MainBox.getSocketClient();
		setIconifiable(true);
		setClosable(true);
		setTitle("Computer");
		getContentPane().setLayout(new GridBagLayout());
		setBounds(100, 100, 650, 375);

		table = new JTable();
		table.setEnabled(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		final DefaultTableModel dftm = new DefaultTableModel()
				{
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;
					public Class<?> getColumnClass(int column)
					{
						switch(column)
						{
						case 0:
							return Boolean.class;
						case 1:
							return ImageIcon.class;
						case 2:
							return String.class;
						case 3:
							return String.class;
						case 4:
							return String.class;
						case 5:
							return double.class;
						case 6:
							return String.class;
						case 7:
							return String.class;
						case 8:
							return String.class;
						case 9:
							return String.class;
						case 10:
							return String.class;
						case 11:
							return String.class;
							default:
								return String.class;
						}
					}
					boolean[] editables = {true,false,false,false, false,false,true,true,true,true,true,true};
					   public boolean isCellEditable(int row, int col)
					   {
					      return editables[col];
					   }
					
			
				};
				table.setModel(dftm);
		String[] tableHeads = new String[]{ "Choose","Image","ID", "Name", "Type", "Price","Color","Size","Desk","Memory","Graphic","CPU"};
		dftm.setColumnIdentifiers(tableHeads);
		TableColumn column = null;
		    column = table.getColumnModel().getColumn(3);		   
		    column.setPreferredWidth(100);	 
		    column = table.getColumnModel().getColumn(0);
		    column.setPreferredWidth(30);
		    column = table.getColumnModel().getColumn(1);
		    column.setPreferredWidth(120);
		    table.setRowHeight(80);
		    table.addMouseMotionListener(new MouseMotionListener(){
				public void mouseDragged(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				public void mouseMoved(MouseEvent e) {
					// TODO Auto-generated method stub
					 Selectedrow= table.rowAtPoint(e.getPoint());
					 Selectedcolumn = table.columnAtPoint(e.getPoint());
					if (Selectedrow != -1&&Selectedcolumn>5){
						oldvalue = (String) table.getValueAt(Selectedrow, Selectedcolumn);
							int i = Selectedcolumn;
					    	TableColumn newColumn = table.getColumnModel().getColumn(i);
					    	final JComboBox comboBox = new JComboBox();
					    	List<String> itemlist = null;
					    	if (i==6){
					    		itemlist = searchitemlist("COMBOCOLOR");		    		
					    	}
					    	else if (i==7){
					    		itemlist = searchitemlist("COMBOSIZE");		    		
					    	}	
					    	else if (i==8){
					    		itemlist = searchitemlist("COMBOSTOCK");		    		
					    	}	
					    	else if (i==9){
					    		itemlist = searchitemlist("COMBOMEMORY");		    		
					    	}	
					    	else if (i==10){
					    		itemlist = searchitemlist("COMBOGRAPHIC");		    		
					    	}	
					    	else if (i==11){
					    		itemlist = searchitemlist("COMBOPROCESSOR");		    		
					    	}	
					    	setupCombo(comboBox,itemlist);	
					    	comboBox.addActionListener(new ActionListener(){
								public void actionPerformed(ActionEvent e) {								
									String pre_component = oldvalue;
									String SelectedItem = (String) comboBox.getSelectedItem();
									if(SelectedItem.equals(oldvalue)){}
									else{										
										double nowprice = (Double) table.getValueAt(table.getSelectedRow(), 5);
										double newPrice = getnewPrice(SelectedItem,pre_component,nowprice);	
										dftm.setValueAt(newPrice, table.getSelectedRow(), 5);
										boolean check = (Boolean) table.getValueAt(table.getSelectedRow(), 0);
										if(check){
											TotalPrice  = TotalPrice - nowprice + newPrice;
											totalprice.setText("  Total Price: $"+TotalPrice);
										}
									}										
								}					    		
					    	});						    	
					    	newColumn.setCellEditor(new DefaultCellEditor(comboBox));					    	
					}else if (Selectedrow != -1&& Selectedcolumn ==0){						
						TableColumn newColumn = table.getColumnModel().getColumn(0);
						final double price =(Double) table.getValueAt(Selectedrow, 5);
						final JCheckBox checkbox = new JCheckBox();
						oldchoose = (Boolean) table.getValueAt(Selectedrow, Selectedcolumn);
						checkbox.addActionListener(new ActionListener(){
							public void actionPerformed(ActionEvent e) {
								boolean newchoose = checkbox.isSelected();								
								if (newchoose==oldchoose){}
								else if(newchoose == true){
									TotalPrice = TotalPrice + price;
									totalprice.setText("  Total Price: $"+TotalPrice);
									oldchoose = true;
								}
								else{
									TotalPrice = TotalPrice - price;
									totalprice.setText("  Total Price: $"+TotalPrice);
									oldchoose = false;
								}
							}							
						});
						newColumn.setCellEditor(new DefaultCellEditor(checkbox));
					}
				}						         		               
		     });
///////////////////////////////////////////////////////////////////////////////////////////////
		final JScrollPane scrollPane = new JScrollPane(table);
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
////////////////////////////////////////////////////////////////////////////
		setupComponet(new JLabel("Choose: "), 0, 0, 1, 5, false);
		conditionName = new JComboBox();
		conditionName.setModel(new DefaultComboBoxModel(new String[]{"DESKTOP","LAPTOP", "SERVER"}));
		setupComponet(conditionName, 1, 0, 1, 30, true);
		conditionName.setSelectedItem(null);

	/*	conditionOperation = new JComboBox();
		conditionOperation.setModel(new DefaultComboBoxModel(new String[]{"绛変簬",
				"鍖呭惈"}));
		setupComponet(conditionOperation, 2, 0, 1, 30, true);*/

	/*	conditionContent = new JTextField();
		setupComponet(conditionContent, 2, 0, 2, 140, true);*/
		final JLabel label = new JLabel();
		setupComponet(label,2,0,2,140,true);

		final JButton queryButton = new JButton();
		queryButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				  try {
					out = new DataOutputStream(socketClient.getOutputStream());
					out.writeUTF("QUERY");
		    	    out.flush();
		    	    String condition = conditionName.getSelectedItem().toString();
		    	    out.writeUTF(condition);
		    	    out.flush();
		    	    inBean = new ObjectInputStream(socketClient.getInputStream());
		    	    List<Computer> list = (List<Computer>)inBean.readObject();
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
		setupComponet(queryButton, 2, 0, 2, 1, false);
		queryButton.setText("Find");
		
////////////////////////////////////////////////////////////////////////////////////////////
		final JButton showAllButton = new JButton();
		showAllButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				  try {
					out = new DataOutputStream(socketClient.getOutputStream());
					out.writeUTF("SHOWALLCPR");
		    	    out.flush();
		    	    inBean = new ObjectInputStream(socketClient.getInputStream());
		    	    @SuppressWarnings("unchecked")
					List<Computer> list = (List<Computer>)inBean.readObject();
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
				  orderdetiallist = new ArrayList<OrderInDetail>();
				  ////////////////////////////////////////////////////////////////////////////////////////
				  Map<String,Integer> computermap = new HashMap<String,Integer>();
				  Map<String,Integer> componentmap = new HashMap<String,Integer>();        			   
				  ////////////////////////////////////////////////////////////////////////////////////////
				  for(int j=0;j<table.getRowCount();j++){
					 Boolean checked = Boolean.valueOf(table.getValueAt(j,0).toString()) ;
					 if(checked){
						String id_computer = table.getValueAt(j, 2).toString();
						int number = 1;
						double price = (Double) table.getValueAt(j, 5);
						String color = table.getValueAt(j,6).toString();
						String size = table.getValueAt(j, 7).toString();
						String stock = table.getValueAt(j,8).toString();
						String memory = table.getValueAt(j, 9).toString();
						String graphic = table.getValueAt(j,10).toString();
						String processor = table.getValueAt(j,11).toString();
															
						OrderInDetail order_detial = new OrderInDetail();
						order_detial.setcomputerID(id_computer);
						order_detial.setprice(price);
						order_detial.setcolor(color);
						order_detial.setsize(size);
						order_detial.setstock(stock);
						order_detial.setmemory(memory);
						order_detial.setgraphics(graphic);
						order_detial.setprocessor(processor);
						order_detial.setorderID(id_order);
						order_detial.setnumber(number);
						orderdetiallist.add(order_detial);
				
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
				  if(orderdetiallist.size()!=0){
					  try {
						  /*
							out = new DataOutputStream(socketClient.getOutputStream());
							out.writeUTF("ORDER");
				    	    out.flush();
				    	    outBean = new ObjectOutputStream(socketClient.getOutputStream());						
							outBean.writeObject(computermap);
							outBean.flush();
							outBean.writeObject(componentmap);
							outBean.flush();
				    	    in = new DataInputStream(socketClient.getInputStream());
				    	    accept = in.readUTF();			    	   
						    	    if (accept.equals("True")){//璁㈠崟鏍告煡鎴愬姛锛屽脊鍑哄璇濇锛岄�夋嫨浠樻鏂瑰紡锛屽～鍐欐敹璐у湴鍧�锛屽寘瑁呰鍗曞苟鍙戦��	
						   */ 	    	
						    	    	new Thread(new PayProcess(order,orderdetiallist,computermap,componentmap)).start();					    	    	 						    	    				    	    				    
						    	    //}
						    /*	   
						    	    	else//璁㈠崟涓嶆垚鍔熸彁绀猴細搴撳瓨涓嶈冻

						    	    {	   JOptionPane.showMessageDialog(ComputerCheck.this,
											"鍟嗗搧搴撳瓨涓嶈冻.", "浜ゆ槗澶辫触",
											JOptionPane.ERROR_MESSAGE);
						    	    }
						    	*/
								} catch (Exception e1) {
									e1.printStackTrace();
								}
				  }
			}
		});
		setupComponet(buynowButton, 4, 2, 1, 1, false);
		buynowButton.setText("Buy Now");
///////////////////////////////////////////////////////////////////////////////		
		final JButton addtobagButton = new JButton();
		addtobagButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<Bag> baglist = new ArrayList<Bag>();
				for(int j=0;j<table.getRowCount();j++){
					 Boolean checked = Boolean.valueOf(table.getValueAt(j,0).toString()) ;			 
					 if(checked){
						String id_computer = table.getValueAt(j, 2).toString();
						String id_color = table.getValueAt(j,6).toString();
						String id_size = table.getValueAt(j, 7).toString();
						String id_strock = table.getValueAt(j, 8).toString();
						String id_memory = table.getValueAt(j, 9).toString();
						String id_graphic = table.getValueAt(j, 10).toString();
						String id_processor = table.getValueAt(j, 11).toString();
						double id_price = (Double) table.getValueAt(j, 5);	
						String client = MainBox.getCzyStateLabel().getText();
						Bag bag = new Bag();
						bag.setcomputerID(id_computer);
						bag.setprice(id_price);
						bag.setcolor(id_color);
						bag.setsize(id_size);
						bag.setstock(id_strock);
						bag.setmemory(id_memory);
						bag.setgraphics(id_graphic);
						bag.setprocessor(id_processor);
						bag.setclientID(client);
						baglist.add(bag);						
					 }
				  }
					try {
							out = new DataOutputStream(socketClient.getOutputStream());
							out.writeUTF("BAG");
				    	    out.flush();
				    	    out.writeUTF(MainBox.getCzyStateLabel().getText());
				    	    out.flush();
				    	    outBean = new ObjectOutputStream(socketClient.getOutputStream());						
							outBean.writeObject(baglist);
							outBean.flush();
							 in = new DataInputStream(socketClient.getInputStream());
					    	    accept = in.readUTF();			    	   
							    	    if (accept.equals("True")){   	    	
							    	    	JOptionPane.showMessageDialog(ComputerCheck.this,
														"Put in Successful.", "Put in Successful",
													JOptionPane.INFORMATION_MESSAGE);	    						    	    				    	    				    
							    	    }
							    	    else

							    	    {	   JOptionPane.showMessageDialog(ComputerCheck.this,
												"Put in Failed.", "Put in Failed",
												JOptionPane.ERROR_MESSAGE);
							    	    }
						} catch (IOException e1) {
							e1.printStackTrace();
						}
				
			}			
		});
		setupComponet(addtobagButton, 5, 2, 1, 1, false);
		addtobagButton.setText("Put into Cart");
	}
	
	/**
	 * 娣诲姞缁勪欢鏂规硶
	 * @param component
	 * @param gridx
	 * @param gridy
	 * @param gridwidth
	 * @param ipadx
	 * @param fill
	 */
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
	
	/**
	 * 鎶婃暟鎹簱鏌ヨ缁撴灉琛ㄦ姇鏀惧埌TABLE妯″瀷涓�
	 * @param list
	 * @param dftm
	 */
	private void updateTable(List<Computer> list, final DefaultTableModel dftm) {
		int num = dftm.getRowCount();//鍒ゆ柇琛ㄦ湁澶氬皯琛�
		for (int i = 0; i < num; i++)
			dftm.removeRow(0);//鎶婅〃涓i琛岀幇鏈夊唴瀹瑰幓鎺�		
		Iterator iterator = list.iterator();//鍒涘缓涓�涓凯浠ｅ櫒锛岀敤浜庨亶鍘嗛摼琛�

		int i=0;
		while (iterator.hasNext()) {
			Computer computer = (Computer) iterator.next();//鑾峰彇閾捐〃涓殑鍏冪礌
			dftm.addRow(new Object[0]);
			dftm.setValueAt(false,i,0);
			/*
			Icon imag = new ImageIcon(getClass().getResource(computer.getPicture()));
			dftm.setValueAt(imag, i, 1);
			*/
			String pic="Picture";
			dftm.setValueAt(pic, i, 1);
			//Icon imag = new ImageIcon(getClass().getResource(computer.getPicture()));
			//dftm.setValueAt(imag, i, 1);

			dftm.setValueAt(computer.getId(), i, 2);
			dftm.setValueAt(computer.getName(), i, 3);
			dftm.setValueAt(computer.getType(), i, 4);
			dftm.setValueAt(computer.getPrice(), i, 5);
			dftm.setValueAt(computer.getColor(), i, 6);
			dftm.setValueAt(computer.getSize(), i, 7);
			dftm.setValueAt(computer.getStock(), i, 8);
			dftm.setValueAt(computer.getMemory(), i, 9);
			dftm.setValueAt(computer.getGraphic(), i, 10);
			dftm.setValueAt(computer.getProcessor(), i, 11);
			i++;	
		}
	}
	/**
	 * 鎶婇」鐩覆鏀惧埌combobox閲�
	 * @param combobox
	 * @param itemstring
	 */
	public void setupCombo (JComboBox comboBox, List<String> itemlist){
		Iterator iterator = itemlist.iterator();
		while(iterator.hasNext()){
			String item = (String) iterator.next();
			comboBox.addItem(item);
		}
		
	}
	/**
	 * 鍚戞暟鎹簱鏌ヨ閰嶄欢鍙�夐」
	 * @param name
	 * @return
	 */
	public List<String> searchitemlist(String name){
		try {
			out = new DataOutputStream(socketClient.getOutputStream());
			out.writeUTF(name);
			out.flush();
			inBean = new ObjectInputStream(socketClient.getInputStream());
    	    List<String> itemlist = (List<String>)inBean.readObject();
    	    return itemlist;
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
				return null;
				}	
	}
	public double getnewPrice(String item,String precomponent,double price){
		try {
			out = new DataOutputStream(socketClient.getOutputStream());
			out.writeUTF("NEWPRICE");
			out.flush();
			out.writeUTF(item);
			out.flush();
			out.writeUTF(precomponent);
			out.flush();
			out.writeDouble(price);
			out.flush();
			inBean = new ObjectInputStream(socketClient.getInputStream());
    	    double newPrice = (Double)inBean.readObject();
    	    return newPrice;
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
				return 0;
				}	
	}

}


