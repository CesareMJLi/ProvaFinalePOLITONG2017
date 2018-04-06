package com.OCS;

/*
 * 
 * Author: Mingju Li 10574864 876508
 * 
 * Initialize the tool bar
 * 
 * Help: show help information to the administrator
 * Contact Us: show the information of the developer of the window
 * 
 * 
 * */

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.border.EtchedBorder;

public class ToolBar extends JToolBar{
	
	private static final long serialVersionUID = 1L;
	private static String helpMsg=
			"Welcome! Here you can find some help!"
			+ "\n The 1st Menu is THE MANAGEMENT OF PRODUCTS"
			+ "\n The 2nd Menu is THE MANAGEMENT OF COMPONENTS"
			+ "\n The 3rd Menu is THE MANAGEMENT OF ORDER";
	private static String contactMsg=
			"Any Suggestions, please send an email to server developer:"
			+ "\n                       mingju.li@mail.polimi.it"
			+ "\n or my personal email: cesareli1995@gmail.com";
	private static String developerMsg=
			"The OnlineComputerSystem(OCS) server was developed by Mingju Li and Jingyu Zhou"
			+ "\n     Mingju  Li  - in charge of the development of Main Frame and Internal Frame"
			+ "\n     Jingyu Zhou - in charge of the development of Database and Communication";
	private static String userMsg=
			"Computer:edit computer-insert/update/delete computer infomation \n"+
			"         check computer-view the general informaion/configuration of computer \n"+
			"Component:edit component-insert/update/delete component information \n"+
			"          check component-view the general information of component \n"+
			"Order:current order-view the order having not been shipped and their details\n"+
			"      history order-view all the order Settled/Delivered/Confirmed/Cancelled";
	
	private JButton help=new JButton();
	private JButton contactUs=new JButton();
	private JButton developerInfo=new JButton();
	private JButton userManual=new JButton();
	
	public ToolBar() {
		super();
		this.setFloatable(false);
		this.setVisible(true);
		initialize();
	}

	
	private void initialize() {
		this.setSize(new Dimension(0,100));
		this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		
		help.setText("Help");
		help.setToolTipText("Get Help");
		help.setFocusable(false);
		help.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		//change the shape of the mouse when moved to this item
		help.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JOptionPane.showMessageDialog(null,helpMsg);
				//show the message
			}
		});
		
		contactUs.setText("Contact Us");
		contactUs.setToolTipText("Contact the developer");
		contactUs.setFocusable(false);
		contactUs.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		//change the shape of the mouse when moved to this item
		contactUs.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JOptionPane.showMessageDialog(null,contactMsg);
				//show the message 
			}
		});
		
		developerInfo.setText("Developer Info");
		developerInfo.setToolTipText("View Developer information");
		developerInfo.setFocusable(false);
		developerInfo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		//change the shape of the mouse when moved to this item
		developerInfo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JOptionPane.showMessageDialog(null,developerMsg);
				//show the message 
			}
		});
		
		userManual.setText("User Manual");
		userManual.setToolTipText("View how the system works");
		userManual.setFocusable(false);
		userManual.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		//change the shape of the mouse when moved to this item
		userManual.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JOptionPane.showMessageDialog(null,userMsg);
				//show the message 
			}
		});
		
		this.add(help);
		this.add(contactUs);
		this.add(developerInfo);
		this.add(userManual);
	}
}