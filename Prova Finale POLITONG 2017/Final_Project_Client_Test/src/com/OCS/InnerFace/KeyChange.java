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
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import com.OCS.client.MainBox;

public class KeyChange extends JInternalFrame{
	private JPasswordField oldpasswordField = null;
	private JPasswordField newpasswordField = null;
	private JPasswordField repasswordField = null;
	public Socket socketClient = null;
	private DataInputStream in = null;
	private DataOutputStream out = null;
	private ObjectInputStream inBean = null;
	private ObjectOutputStream outBean = null;
	private String accept;
	
	
	public KeyChange (){
		super();
		socketClient = MainBox.getSocketClient();
		setIconifiable(true);//开启内部窗口最小化功能
		setClosable(true);//开启内部窗口关闭功能
		setTitle("Change Key");//设置窗口标题
		getContentPane().setLayout(new GridBagLayout());//窗口内部布局设置
		setBounds(100, 100, 300, 250);//窗口大小设置
		final JLabel password = new JLabel("Old Key：");
		setupComponet(password, 0, 0, 1, 0, false);
		oldpasswordField = new JPasswordField();
		// 定位密码输入文本框
		setupComponet(oldpasswordField, 1, 0, 5, 100, true);
		////////////////////////////////////////////////////////
		final JLabel newpassword = new JLabel();
		newpassword.setText("New Key：");
		setupComponet(newpassword, 0, 1, 1, 0, false);
		newpasswordField= new JPasswordField();
		// 定位重复密码文本框
		setupComponet(newpasswordField, 1, 1, 5, 100, true);
		final JLabel repassword = new JLabel();
		repassword.setText("Repeat：");
		setupComponet(repassword, 0, 2, 1, 0, false);
		repasswordField= new JPasswordField();
		// 定位重复密码文本框
		setupComponet(repasswordField, 1, 2, 5, 100, true);
		final JButton confirmButton = new JButton();
		confirmButton.setText("Confirm");
		confirmButton.addActionListener(new ActionListener() {
			//先判断信息是否齐全，再执行数据库操作
			public void actionPerformed(final ActionEvent e){
				// 校验两次输入的密码是否相同
			       if (!Arrays.equals(newpasswordField.getPassword(), repasswordField.getPassword())) {
			           JOptionPane.showMessageDialog(null, "Two keys difference", "Warning", JOptionPane.WARNING_MESSAGE);
			           return;
			       }
				//校验原密码是否正确
				String oldpass = String.valueOf(oldpasswordField.getPassword());
				String userStr = MainBox.getCzyStateLabel().getText();
				try {
					out = new DataOutputStream(socketClient.getOutputStream());
					out.writeUTF("CHANGEPASSWORD");
				    out.flush();
		            out.writeUTF(userStr);
		            out.flush();
		            out.writeUTF(oldpass);
		            out.flush();
		            in = new DataInputStream(socketClient.getInputStream());  	
		            String check = in.readUTF();			            	
		            if (check.equals("false")) {
						JOptionPane.showMessageDialog(KeyChange.this,
								"Error: Incorrect old Password.", "Change failed",
								JOptionPane.ERROR_MESSAGE);
						return;
		            }		         
				    //存储新密码
				       String newpass = String.valueOf(newpasswordField.getPassword());			       
				       out.writeUTF(newpass);
				       out.flush();
				       accept = in.readUTF();
				       if(accept.equals("True")){
				    	   JOptionPane.showMessageDialog(KeyChange.this, "Change Successful", "Change Successful", JOptionPane.INFORMATION_MESSAGE);
				           return;
				       }else{
				    	   JOptionPane.showMessageDialog(KeyChange.this, "Change Failed", "Change Failed", JOptionPane.ERROR_MESSAGE);
				       }
				       
				} catch (IOException e1) {
					e1.printStackTrace();
				}			       
			}
		
	});
		setupComponet(confirmButton, 1, 3, 1, 1, false);
		
		final JButton resetButton = new JButton();
		resetButton.setText("Reset");
		setupComponet(resetButton, 5, 3, 1, 1, false);
		// 重添按钮的事件监听类
				resetButton.addActionListener(new ActionListener() {
					public void actionPerformed(final ActionEvent e) {
					    oldpasswordField.setText("");
						newpasswordField.setText("");
						repasswordField.setText("");
						
					}
				});
				
	}
	
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

}
