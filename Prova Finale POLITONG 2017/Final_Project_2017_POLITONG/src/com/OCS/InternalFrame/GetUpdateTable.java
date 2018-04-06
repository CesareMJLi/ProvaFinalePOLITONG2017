package com.OCS.InternalFrame;

/*
 * 
 * Author: Mingju Li 10574864 876508
 * 
 * Initialize the update of the table information
 * Update-Update the current order/history order table
 * UpdateComponent-Update the component table
 * UpdateComputer-Update the computer table
 * 
 * */

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import com.OCS.Dao.model.*;

public class GetUpdateTable {
	
	private static final long serialVersionUID=1L;
	
	public GetUpdateTable() {
		
	}
	
	/*
	public static void Update(List<Order> list, final DefaultTableModel defaultTable) {
		int num = defaultTable.getRowCount();
		for (int i = 0; i < num; i++)
			defaultTable.removeRow(0);
		Iterator iterator = list.iterator();
		int i=0;
		while (iterator.hasNext()) {
			Order order = (Order) iterator.next();
			defaultTable.addRow(new Object[0]);
			defaultTable.setValueAt(false,i,0);
			defaultTable.setValueAt(order.getState(), i, 1);
			defaultTable.setValueAt(order.getID(), i, 2);
			defaultTable.setValueAt(order.getDatetime(), i, 3);
			defaultTable.setValueAt(order.getPrice(), i, 4);
			defaultTable.setValueAt(order.getPayment(), i, 5);
			defaultTable.setValueAt(order.getDelivery(), i, 6);

			i++;	
		}
	}
	*/ 
	public static void Update(List<Order> list, DefaultTableModel defaultTable){
		int row=defaultTable.getRowCount();
		for(int i=0;i<row;i++)
			defaultTable.removeRow(0);
		Iterator iterator=list.iterator();
		
		while(iterator.hasNext()){
			Order order = (Order) iterator.next();
			Vector data=new Vector();
			
			data.add(false);
			data.add(order.getState());
			data.add(order.getID());
			data.add(order.getDatetime());
			data.add(order.getPrice());
			data.add(order.getPayment());
			data.add(order.getDelivery());
			defaultTable.addRow(data);
			
		}
	}
	
	public static void UpdateComponent(List<Component> list, DefaultTableModel defaultTable){
		int row=defaultTable.getRowCount();
		for(int i=0;i<row;i++)
			defaultTable.removeRow(0);
		Iterator iterator=list.iterator();
		//int i=0;
		while(iterator.hasNext()){
			Component component=(Component)iterator.next();
			Vector data=new Vector();
			data.add(component.getId());
			data.add(component.getName());
			data.add(component.getType());
			data.add(component.getPrice());
			defaultTable.addRow(data);
		}
	}
	
	public static void UpdateComputer(List<Computer> list, final DefaultTableModel defaultTable) {
		int row=defaultTable.getRowCount();
		for (int i=0;i<row;i++)
			defaultTable.removeRow(0);
		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			Computer computer = (Computer) iterator.next();
			Vector data = new Vector();
			String imag = computer.getPicture();
			if(imag==null){
				imag="No Picture";
				data.add(imag);
			}else{
				data.add(imag);
			}
			data.add(computer.getId());
			data.add(computer.getName());
			data.add(computer.getType());
			data.add(computer.getPrice());
			data.add(computer.getColor());
			data.add(computer.getSize());
			data.add(computer.getStock());
			data.add(computer.getMemory());
			data.add(computer.getGraphic());
			data.add(computer.getProcessor());
			defaultTable.addRow(data);
		}	
	}
	
	/**
	 * 把数据库查询结果表投放到TABLE模型中
	 * @param list
	 * @param dftm
	 */
	/*
	private void update(List<Order> list, final DefaultTableModel dftm) {
		int num = dftm.getRowCount();
		for (int i = 0; i < num; i++)
			dftm.removeRow(0);
		Iterator iterator = list.iterator();
		int i=0;//代表行号
		while (iterator.hasNext()) {
			Order order = (Order) iterator.next();
			dftm.addRow(new Object[0]);
			dftm.setValueAt(order.getID(), i, 1);
			dftm.setValueAt(order.getPrice(), i, 3);
			dftm.setValueAt(order.getDatetime(), i, 2);
			dftm.setValueAt(order.getPayment(), i, 4);
			dftm.setValueAt(order.getDelivery(), i, 5);
			dftm.setValueAt(order.getState(), i, 0);
			i++;	
		}
	}
	*/
}
