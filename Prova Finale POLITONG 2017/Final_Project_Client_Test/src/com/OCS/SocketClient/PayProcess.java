package com.OCS.SocketClient;

import java.beans.PropertyVetoException;
import java.util.List;
import java.util.Map;

import javax.swing.JDesktopPane;
import javax.swing.JLabel;

import com.OCS.Dao.model.*;
import com.OCS.InnerFace.PaySend;
import com.OCS.client.MainBox;

public class PayProcess implements Runnable{

	private PaySend PayFra;
	private Order Ord;
	private List<OrderInDetail> OrdList;
	private Map<String, Integer> ComputerMap;
	private Map<String, Integer> ComponentMap;
	private JDesktopPane DeskPanel;
	private JLabel StateLabel;
	
	public PayProcess(Order Ord, List<OrderInDetail> OrdList, Map<String, Integer> ComputerMap, Map<String, Integer> ComponentMap){
		this.Ord=Ord;
		this.OrdList=OrdList;
		this.ComputerMap=ComputerMap;
		this.ComponentMap=ComponentMap;
	}

	public void run() {
		// TODO Auto-generated method stub
		PayFra=new PaySend(Ord, OrdList, ComputerMap, ComponentMap);
		DeskPanel=MainBox.getDesktopPane();
		DeskPanel.add(PayFra);
		StateLabel=MainBox.getStateLabel();
		StateLabel.setText(PayFra.getTitle());
		try{
			PayFra.setSelected(true);
		} catch(PropertyVetoException P){
			P.printStackTrace();
		}
	}
	

}