package com.OCS.InnerFace;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.OCS.Dao.model.*;
import com.OCS.client.MainBox;

public class PaySend extends JInternalFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel ConPanel;
	private JCheckBox Card;
	private ButtonGroup GroupBut;
	private JCheckBox Cash;
	private JCheckBox BankTran;
	private JTextArea AddText;
	private JButton OKBut;
	protected DataOutputStream Out;
	protected ObjectOutputStream OutB;
	protected DataInputStream In;
	protected String Accept;
	
	public PaySend(final Order Ord, final List<OrderInDetail> OrdList, final Map<String, Integer> ComputerMap, final Map<String, Integer> ComponentMap){
		super();
		setTitle("PaySend");
		setIconifiable(true);
		setClosable(true);
		setVisible(true);
		getContentPane().setLayout(new GridBagLayout());
		setBounds(100, 100, 500, 300);
		ConPanel=new JPanel();
		
		ConPanel.setBounds(100, 100, 400, 300);
		ConPanel.setLayout(new GridBagLayout());
		ConPanel.setVisible(true);
		
		final JLabel PayWay=new JLabel();
		PayWay.setText("How to Pay: ");
		SetComponent(PayWay, 0, 0, 1, 20, false);
		Card=new JCheckBox("Card         ");
		GroupBut=new ButtonGroup();
		GroupBut.add(Card);
		SetComponent(Card, 1, 0, 1, 10, false);
		Cash=new JCheckBox("Cash         ");
		GroupBut.add(Cash);
		SetComponent(Cash, 1, 1, 1, 10, false);
		BankTran=new JCheckBox("Bank Transfer");
		GroupBut.add(BankTran);
		SetComponent(BankTran, 1, 2, 1, 10, false);
		
		final JLabel SendGoal=new JLabel();
		SendGoal.setText("Address: ");
		SetComponent(SendGoal, 0, 3, 1, 20, false);
		AddText=new JTextArea();
		SetComponent(AddText, 1, 3, 3, 250, false);
		
		OKBut=new JButton();
		OKBut.setText("OK");
		OKBut.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent A) {
				// TODO Auto-generated method stub
				Socket SocketClient=MainBox.getSocketClient();
				Ord.setPayment(GetPayWay());
				Ord.setDelivery(GetAdd());
				try{
					Out=new DataOutputStream(SocketClient.getOutputStream());
					Out.writeUTF("SAVEORDER");
					Out.flush();
					OutB=new ObjectOutputStream(SocketClient.getOutputStream());
					OutB.writeObject(ComputerMap);
					OutB.flush();
					OutB.writeObject(ComponentMap);
					OutB.flush();
					OutB=new ObjectOutputStream(SocketClient.getOutputStream());
					OutB.writeObject(Ord);
					OutB.flush();
					OutB.writeObject(OrdList);
					OutB.flush();
					In=new DataInputStream(SocketClient.getInputStream());
					Accept=In.readUTF();
					if(Accept.equals("True")){
						JOptionPane.showMessageDialog(PaySend.this, "Order Submit", "Successful", JOptionPane.INFORMATION_MESSAGE);
					}
					else {
						JOptionPane.showMessageDialog(PaySend.this, "Submit Error", "Failed", JOptionPane.ERROR_MESSAGE);
					}
				} catch (IOException I){
					I.printStackTrace();
				}
				setVisible(false);
			}
		});
		SetComponent(OKBut, 3, 4, 1, 0, false);
		getContentPane().add(ConPanel);
	}

	protected String GetAdd() {
		// TODO Auto-generated method stub
		String Add=AddText.getText();
		return Add;
	}

	protected String GetPayWay() {
		// TODO Auto-generated method stub
		String Payment=null;
		if (Card.isSelected()){
			Payment=Card.getText();
		}
		else if(Cash.isSelected()){
			Payment=Cash.getText();
		}
		else if(BankTran.isSelected()){
			Payment=BankTran.getText();
		}
		return Payment;
	}

	private void SetComponent(JComponent Com, int x, int y, int w, int i,
			boolean b) {
		// TODO Auto-generated method stub
		final GridBagConstraints GBC=new GridBagConstraints();
		GBC.gridx=x;
		GBC.gridy=y;
		GBC.insets=new Insets(5, 1, 3, 1);
		if(w>1){
			GBC.gridwidth=w;
		}
		if(i>0){
			GBC.ipadx=i;
		}
		if(b){
			GBC.fill=GridBagConstraints.HORIZONTAL;
		}
		add(Com,GBC);
	}
	
}