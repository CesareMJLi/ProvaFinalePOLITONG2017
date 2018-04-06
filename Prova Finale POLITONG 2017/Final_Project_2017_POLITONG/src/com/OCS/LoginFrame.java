package com.OCS;

/*
 * 
 * Author: Mingju Li 10574864 876508
 * 
 * The initialize of login frame
 * 
 * Firstly initial the login panel add this to the content
 * 
 * Set Username as "Administrator"
 * Set Password as "12345"
 * 
 * This is written in the program and no administrator registration is acceptable
 * 
 * 
 * */

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class LoginFrame extends JFrame {
	
	private static final long serialVersionUID=1L;
	
	public int width, height;
	
	private String USERNAME="Administrator";
	private String PASSWORD="12345";

	private LoginPanel loginPanel=null;
	
	private JLabel jLabel1=null;		
	private static String userStr="";
	private JTextField userField=null;
	
	private JLabel jLabel2=null;		
	private static String passwordStr="";
	private JPasswordField passwordField=null;
	
	private JButton loginButton=null;

	private ServerFrame mainFrame;
	
	public LoginFrame() {
		try {
			
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			mainFrame=new ServerFrame();
			//Start the main frame but do not set it visible until login
			initialize();	
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("An error occurs at the initialzation of login frame(LoginFrame.java)");
		}
	}
	
	/*Initialization of the login dialog
	 * */
	private void initialize() {
		Dimension size=getToolkit().getScreenSize();
		//get the screen size
		setLocation((size.width - 320) / 2, (size.height - 180) / 2);
		//Set the location of the login dialog in the center of the screen
		setSize(320, 180);
		this.setTitle("Caesar Tech. LOGIN");

		
		jLabel1=new JLabel();
		jLabel1.setText("USERNAME");
		jLabel1.setBounds(new Rectangle(85, 40, 81, 20));
		
		jLabel2=new JLabel();
		jLabel2.setText("PASSWORD");	
		jLabel2.setBounds(new Rectangle(85, 70, 81, 20));		
		
		loginPanel=new LoginPanel();
		loginPanel.setLayout(null);
		loginPanel.setForeground(Color.black);
		
		loginPanel.add(jLabel1, null);                
		loginPanel.add(getUserField(), null);
		
		loginPanel.add(jLabel2, null);
		loginPanel.add(getPasswordField(), null);
		
		loginPanel.add(getLoginButton(), null);	 
		
		setContentPane(loginPanel);
	}

	/*Initialization of the username area
	 * */
	private JTextField getUserField() {
		if (userField==null) {
			userField=new JTextField();
			userField.setBounds(new Rectangle(160, 40, 120, 22));
			userField.addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent e) {
					if (e.getKeyChar()=='\n')
						loginButton.doClick();
				}
			});
			//Press Enter to continue
		}
		return userField;
	}
	
	/*Initialization of the password text area
	 * */
	private JPasswordField getPasswordField() {
		if (passwordField==null) {
			passwordField=new JPasswordField();
			passwordField.setBounds(new Rectangle(160, 70, 120, 22));
			passwordField.addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent e) {
					if (e.getKeyChar()=='\n')
						loginButton.doClick();
				}
			});
			//Press Enter to continue
		}
		return passwordField;
	}
	
	/*Initialization of the login button
	 * */
	private JButton getLoginButton() {
		if (loginButton==null) {
			loginButton=new JButton();
			loginButton.setBounds(new Rectangle(190, 102, 60, 21));
			loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			loginButton.setIcon(new ImageIcon(getClass().getResource(
					"/resource/loginButton.jpg")));
			loginButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						userStr=userField.getText();
						passwordStr=new String(passwordField.getPassword());
						
						if(userStr==""){
							JOptionPane.showMessageDialog(LoginFrame.this,
									"Please Enter User Name!", "Login failed",
									JOptionPane.ERROR_MESSAGE);
							return;	
						}else if(passwordStr==""){
							JOptionPane.showMessageDialog(LoginFrame.this,
									"Please Enter Password!", "Login failed",
									JOptionPane.ERROR_MESSAGE);
							return;	
						}
						
						//validate the username
						if (!userStr.equals(USERNAME)){
							JOptionPane.showMessageDialog(LoginFrame.this,
									"Username Error!Please re-enter user name!", "Login failed",
									JOptionPane.ERROR_MESSAGE);
							return;		
						}
						
						//validate the password
						if (!passwordStr.equals(PASSWORD)){
							JOptionPane.showMessageDialog(LoginFrame.this,
									"Password Error!Please correct your password!", "Login failed",
									JOptionPane.ERROR_MESSAGE);
							return;		
						}
						
					} catch (Exception e1) {
						System.out.println("An error occurs while the login and validation!(LoginFrame.java)");
					}
					
					mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
					mainFrame.setVisible(true);

					setVisible(false);
					//set the login un-visible after successfully login
				}
			});
		}
		return loginButton;
	}
	
}
