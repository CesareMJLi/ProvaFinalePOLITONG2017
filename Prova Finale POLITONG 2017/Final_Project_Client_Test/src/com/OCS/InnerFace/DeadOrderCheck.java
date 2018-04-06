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
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.OCS.Dao.model.*;
import com.OCS.client.MainBox;

public class DeadOrderCheck extends JInternalFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable Table;
	private Socket SocketClient;
	private DataInputStream In = null;
	private DataOutputStream Out = null;
	private ObjectInputStream InBean = null;
	private ObjectOutputStream OutBean = null;
	private String accept;
	public DeadOrderCheck() {
		super();//鍏堟瀯閫犱竴涓唴閮ㄧ獥鍙�
		this.SocketClient = MainBox.getSocketClient();
		setIconifiable(true);//寮�鍚唴閮ㄧ獥鍙ｆ渶灏忓寲鍔熻兘
		setClosable(true);//寮�鍚唴閮ㄧ獥鍙ｅ叧闂姛鑳�
		setTitle("Dead Order");//璁剧疆绐楀彛鏍囬
		getContentPane().setLayout(new GridBagLayout());//绐楀彛鍐呴儴甯冨眬璁剧疆
		setBounds(100, 100, 600, 375);//绐楀彛澶у皬璁剧疆

		Table = new JTable();//鏂板缓涓�涓〃
		Table.setEnabled(true);//璁剧疆琛ㄦ牸浣胯兘鍏抽棴锛屽嵆涓嶄笌鐢ㄦ埛浜や簰
		Table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);//琛ㄦ牸鑷姩璋冩暣灏哄鏂瑰紡
		final DefaultTableModel dftm = new DefaultTableModel()//閲嶅啓涓�涓〃鏍兼牸寮�
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
					boolean[] editables = {true,false,false,false, false,false,false};
					   public boolean isCellEditable(int row, int col)
					   {
					      return editables[col];
					   }
					
			
				};
		Table.setModel(dftm);
		String[] tableHeads = new String[]{"Choose","Status", "ID", "Date", "Price", "Payment","Address"};//娣诲姞琛ㄥご
		dftm.setColumnIdentifiers(tableHeads);//鎶婅〃澶磋缃负姣忔爮鐨勬爣绀�
		for(int i=0;i<7;i++){
			TableColumn column = null;	//鎶婄數鑴戝悕涓�鏍忕敾澶т竴鐐�	
		    column = Table.getColumnModel().getColumn(i);
		    if(i==0)column.setPreferredWidth(30);
		    else if(i==1)column.setPreferredWidth(80);
		    else if(i==2)column.setPreferredWidth(150);
		    else if(i==5)column.setPreferredWidth(100);
		    else if(i==6)column.setPreferredWidth(150);
		    else if(i==3)column.setPreferredWidth(100);
		    
		}
		//鎶婅〃鏍兼斁缃埌鏈夋粴鍔ㄦ潯鐨勯潰鏉夸笂,鎺у埗琛ㄦ牸浣嶇疆
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
		setupComponet(new JLabel("                    "), 2, 1, 2, 1, false);
		setupComponet(new JLabel("                              "), 4, 1, 3, 1, false);
		setupComponet(new JLabel("          "), 0, 1, 1, 1, false);
		setupComponet(new JLabel("          "), 8, 1, 1, 1, false);
		final JButton showButton = new JButton();
		showButton.addActionListener(new ActionListener(){
			public void actionPerformed(final ActionEvent e){				
				  try {
					  Out = new DataOutputStream(SocketClient.getOutputStream());
						Out.writeUTF("SHOWDEADORDER");
					    Out.flush();
					    Out.writeUTF(MainBox.getCzyStateLabel().getText());
					    Out.flush();
					InBean = new ObjectInputStream(SocketClient.getInputStream());
					List<Order> list = (List<Order>)InBean.readObject();
				    updateTable(list, dftm);	
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				    
			}
			
		});
		setupComponet(showButton, 1, 1, 1, 1, false);
		showButton.setText("Show ALL");
		showButton.doClick();
		final JButton deleteButton = new JButton();
		deleteButton.addActionListener(new ActionListener(){
			public void actionPerformed(final ActionEvent e){
				//閬嶅巻閫変腑鐨勮鍗�
				  for(int j=0;j<Table.getRowCount();j++){
					 Boolean checked = Boolean.valueOf(Table.getValueAt(j,0).toString()) ;//妫�鏌ヨ琛屾槸鍚﹁閫変腑
					 if(checked){//濡傛灉琚�変腑锛屽垯鎵ц锛氬垯鎿嶄綔鏁版嵁搴撳垹闄よ鍗曞拰璁㈠崟鎵�鏈夐」锛�
						String id_order = Table.getValueAt(j, 2).toString();
						String state_order = Table.getValueAt(j, 1).toString();
						try {
								Out = new DataOutputStream(SocketClient.getOutputStream());
								Out.writeUTF("DELETEORDER");
							    Out.flush();
							    Out.writeUTF(id_order);
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
				  showButton.doClick();
				    
			}
			
		});
		setupComponet(deleteButton, 7, 1, 1, 1, false);
		deleteButton.setText("Delete");
					
		
		
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
		gridBagConstrains.insets = new Insets(0, 1, 0, 1);
		if (fill)
			gridBagConstrains.fill = GridBagConstraints.HORIZONTAL;
		getContentPane().add(component, gridBagConstrains);
	}
	
	private void updateTable(List<Order> list, final DefaultTableModel dftm) {
		int num = dftm.getRowCount();//鍒ゆ柇琛ㄦ湁澶氬皯琛�
		for (int i = 0; i < num; i++)
			dftm.removeRow(0);//鎶婅〃涓i琛岀幇鏈夊唴瀹瑰幓鎺�
		Iterator iterator = list.iterator();//鍒涘缓涓�涓凯浠ｅ櫒锛岀敤浜庨亶鍘嗛摼琛�
		int i=0;//浠ｈ〃琛屽彿
		while (iterator.hasNext()) {
			Order order = (Order) iterator.next();//鑾峰彇閾捐〃涓殑鍏冪礌			
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
			else if (state.equals("3")){
				dftm.setValueAt("canceled", i, 1);}
			i++;	
		}
	}
	

}
