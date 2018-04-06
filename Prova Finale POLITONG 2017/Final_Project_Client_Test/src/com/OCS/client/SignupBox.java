package com.OCS.client;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.OCS.Dao.model.*;

public class SignupBox extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MainBox mainBox;
	private JTextField NameField;
	private JPasswordField KeyField;
	private JPasswordField ReKeyField;
	private JTextField emailField;
	protected DataOutputStream Out;
	protected DataInputStream In;
	protected String accept;
	protected ObjectOutputStream OutB;
	private JButton ResetBut;
	
	public SignupBox(final Socket SocketClient){
		super();
		Dimension Size=getToolkit().getScreenSize();
		setLocation((Size.width-600)/2, (Size.height-500)/2);
		setSize(600,500);
		setTitle("Sign Up");
		JPanel SignFace=new JPanel();
		mainBox=new MainBox(SocketClient);
		
		SignFace.setBounds( 10, 10, 400, 300);
		SignFace.setLayout(new GridBagLayout());
		
		final JLabel UserName=new JLabel();
		UserName.setText("Username: ");
		SetComponent(SignFace, UserName, 0, 0, 1, 0, false);
		NameField=new JTextField();
		SetComponent(SignFace, NameField, 1, 0, 5, 200, true);
		
		final JLabel Key=new JLabel();
		Key.setText("Key: ");
		SetComponent(SignFace, Key, 0, 1, 1, 0, false);
		KeyField=new JPasswordField();
		SetComponent(SignFace, KeyField, 1, 1, 5, 100, true);
		
		final JLabel ReKey=new JLabel();
		ReKey.setText("repeat: ");
		SetComponent(SignFace, ReKey, 0, 2, 1, 0, false);
		ReKeyField=new JPasswordField();
		SetComponent(SignFace, ReKeyField, 1, 2, 5, 100, true);
		
		final JLabel email=new JLabel();
		email.setText("E-mail: ");
		SetComponent(SignFace, email, 0, 3, 1, 0, false);
		emailField=new JTextField();
		SetComponent(SignFace, emailField, 1, 3, 5, 0, true);
		final JButton AddBut=new JButton();
		AddBut.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent A) {
				// TODO Auto-generated method stub
				if(NameField.getText().equals("")
						||KeyField.getPassword().equals("")
						||ReKeyField.getPassword().equals("")
						||emailField.getText().equals("")){
					JOptionPane.showMessageDialog(null, "Please complete the message.", "Sign up", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(!Arrays.equals(KeyField.getPassword(),  ReKeyField.getPassword())){
					JOptionPane.showMessageDialog(SignupBox.this, "The keys must be Same.", "Warning", JOptionPane.WARNING_MESSAGE);
					return;
				}
				try{
					String UserName=NameField.getText();
					Out=new DataOutputStream(SocketClient.getOutputStream());
					Out.writeUTF("Sign up");
					Out.flush();
					Out.writeUTF(UserName);
					Out.flush();
					In=new DataInputStream(SocketClient.getInputStream());
					accept=In.readUTF();
					if (accept.equals("true")){
						JOptionPane.showMessageDialog(SignupBox.this, "Username has been used.", "Warning", JOptionPane.WARNING_MESSAGE);
					}
				} catch(Exception E){
					E.printStackTrace();
				}
				Client Cli=new Client();
				Cli.setUsername(NameField.getText());
				Cli.setPassword(String.valueOf(KeyField.getPassword()));
				Cli.setEmail(emailField.getText());
				
				try{
					OutB=new ObjectOutputStream(SocketClient.getOutputStream());
					OutB.writeObject(Cli);
					OutB.flush();
					accept=In.readUTF();
					if (accept.equals("ture")){
						JOptionPane.showMessageDialog(SignupBox.this, "Congratulation, registration successfully", "Successful", JOptionPane.INFORMATION_MESSAGE);
						mainBox.setDefaultCloseOperation(MainBox.EXIT_ON_CLOSE);
						mainBox.setVisible(true);
						MainBox.getCzyStateLabel().setText(NameField.getText());
						setVisible(false);
						return;
					}
				} catch(Exception E){
					E.printStackTrace();
				}
			}
		});
		AddBut.setText("Sign up");
		SetComponent(SignFace, AddBut, 1, 4, 1, 1, false);
		
		ResetBut=new JButton();
		ResetBut.setText("Reset");
		SetComponent(SignFace, ResetBut, 5, 4, 1, 1, false);
		
		ResetBut.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				NameField.setText("");
				KeyField.setText("");
				ReKeyField.setText("");
				emailField.setText("");
			}
		});
		setContentPane(SignFace);
	}

	private void SetComponent(JPanel SignFace, JComponent Com, int x, int y,
			int w, int i, boolean b) {
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
		SignFace.add(Com,GBC);
	}
}