package com.OCS.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class LoginBox extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static String UserStr = null;
	private LoginFace LoginPane=null;
	private JTextField UserField=null;
	private Socket SocketClient;
	private MainBox mainBox;
	private JLabel JLabel1;
	private JLabel JLabel2;
	private JPasswordField KeyField;
	private JButton LoginBut;
	protected DataInputStream In;
	protected DataOutputStream Out;
	private JButton ExitBut;
	
	public LoginBox(Socket SocketClient){
		this.SocketClient=SocketClient;
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			mainBox=new MainBox(SocketClient);
			Initialize();
		} catch (Exception E){
			E.printStackTrace();
		}
	}

	private void Initialize() {
		// TODO Auto-generated method stub
		Dimension Size=getToolkit().getScreenSize();
		setLocation((Size.width - 300)/2,(Size.height - 170)/2);
		setSize(300,190);
		this.setTitle("LOGIN");
		setContentPane(GetLoginPanel());
	}
	private LoginFace GetLoginPanel() {
		// TODO Auto-generated method stub
		if (LoginPane==null){
			JLabel1=new JLabel();
			JLabel1.setBounds(new Rectangle(50,40,80,16));
			JLabel1.setText("Username: ");
			JLabel2=new JLabel();
			JLabel2.setBounds(new Rectangle(50, 70, 80, 16));
			JLabel2.setText("Key: ");
			
			LoginPane=new LoginFace();
			LoginPane.setLayout(null);
			LoginPane.setBackground(new Color(0xD8DDC7));
			
			LoginPane.add(JLabel1,null);
			LoginPane.add(JLabel2,null);
			LoginPane.add(GetUserField(),null);
			LoginPane.add(GetKeyField(),null);
			LoginPane.add(GetLoginBut(),null);
			LoginPane.add(GetExitBut(),null);
		}
		return LoginPane;
	}

	private JButton GetExitBut() {
		// TODO Auto-generated method stub
		if(ExitBut==null){
			ExitBut=new JButton();
			ExitBut.setBounds(new Rectangle(180, 115, 50, 20));
			ExitBut.setIcon(new ImageIcon(getClass().getResource("/res/signupButton.jpg")));
			ExitBut.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent A) {
					// TODO Auto-generated method stub
					setVisible(false);
					SignupBox Sign=new SignupBox(SocketClient);
					Sign.setDefaultCloseOperation(EXIT_ON_CLOSE);
					Sign.setVisible(true);
				}
			});
		}
		return ExitBut;
	}

	private JButton GetLoginBut() {
		// TODO Auto-generated method stub
		if (LoginBut==null){
			LoginBut=new JButton();
			LoginBut.setBounds(new Rectangle(110, 115, 50, 20));
			LoginBut.setIcon(new ImageIcon(getClass().getResource("/res/loginButton.jpg")));
			
			LoginBut.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent A) {
					// TODO Auto-generated method stub
					try{
						UserStr=UserField.getText();
						String KeyStr=String.valueOf(KeyField.getPassword());
						In=new DataInputStream(SocketClient.getInputStream());
						Out=new DataOutputStream(SocketClient.getOutputStream());
						Out.writeUTF("LOGIN");
						Out.flush();
						Out.writeUTF(UserStr);
						Out.flush();
						Out.writeUTF(KeyStr);
						Out.flush();
						String EX=In.readUTF();
						if (EX.equals("false")){
							JOptionPane.showMessageDialog(LoginBox.this, "Error: Username","Login Fail",JOptionPane.ERROR_MESSAGE);
							return;
						}
						String CK=In.readUTF();
						if (CK.equals("false")){
							JOptionPane.showMessageDialog(LoginBox.this, "Error: Key","Login Fail",JOptionPane.ERROR_MESSAGE);
							return;
						}
					} catch (Exception E){
						E.printStackTrace();
					}
					
					mainBox.setDefaultCloseOperation(EXIT_ON_CLOSE);
					mainBox.setVisible(true);
					MainBox.getCzyStateLabel().setText(UserStr);
					setVisible(false);
				}
			});
		}
		return LoginBut;
	}

	private JPasswordField GetKeyField() {
		// TODO Auto-generated method stub
		if(KeyField==null){
			KeyField=new JPasswordField();
			KeyField.setBounds(new Rectangle(150, 60, 120, 30));
			/*
			KeyField.addKeyListener(new KeyAdapter() {
				public void KeyType(KeyEvent K){
					if (K.getKeyChar()=='\n')
						LoginBut.doClick();
				}
			});
			*/
		}
		return KeyField;
	}

	private JTextField GetUserField() {
		// TODO Auto-generated method stub
		if(UserField==null){
			UserField=new JTextField();
			UserField.setBounds(new Rectangle(150, 25, 120, 30));
		}
		return UserField;
	}

	public String GetUserStr(){
		return UserStr;
	}
}