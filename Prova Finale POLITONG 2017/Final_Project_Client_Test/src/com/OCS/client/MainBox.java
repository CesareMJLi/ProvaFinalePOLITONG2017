package com.OCS.client;

import static java.awt.BorderLayout.*;


import static javax.swing.border.BevelBorder.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;
import javax.swing.*;

public class MainBox extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel frameContentPane = null;
	private MenuBar frameMenuBar = null;
	private ToolBar toolBar = null;
	private static ClientDesktop desktopPane = null;
	private JPanel statePanel = null;
	private static JLabel stateLabel = null;
	private JLabel nameLabel = null;
	private JLabel nowDateLabel = null;
	private JSeparator jSeparator1 = null;
	private static JLabel czyStateLabel = null;
	private JSeparator jSeparator2 = null;
	
	/////////////////////////閫氫俊閮ㄥ垎////////////////////////////
	 private static Socket socketClient;
	 DataOutputStream out = null;
	 DataInputStream in = null;
	
	/**
	 * 绋嬪簭涓绘柟娉曪紝杩愯绋嬪簭鐨勫叆鍙�
	 * 
	 * @param args
	 * @throws IOException 
	 * 
	 */
	public static void main(String[] args) throws  IOException{
		
		Socket socketClient = new Socket(InetAddress.getLocalHost(), 12345);
		MainBox.socketClient = socketClient;
		
		SplashScreen splashScreen = SplashScreen.getSplashScreen();//锛燂紵鑾峰彇闂睆瀵硅薄
		LoginBox login = new LoginBox(socketClient);
		//濡傛灉涓嶉棯灞忕殑璇濇墽琛屼互涓嬫搷浣�
		if (splashScreen != null) {
			try {
				login.setDefaultCloseOperation(EXIT_ON_CLOSE);
				Thread.sleep(3000);//锛熺嚎绋嬬潯鐪�
			} catch (InterruptedException e) {
			}
		}
		login.setDefaultCloseOperation(EXIT_ON_CLOSE);
		login.setVisible(true);//浣縧ogin鐣岄潰鍙
		
	}
	
	
	/**
	 * This method initializes jJToolBarBar
	 * 
	 * @return JToolBar
	 */
	private ToolBar getJJToolBarBar() {
		if (toolBar == null) {
			toolBar = new ToolBar(getFrameMenuBar());
			toolBar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		return toolBar;
	}
	
	/**
	 * 鍒濆鍖栫獥浣撹彍鍗曟爮鐨勬柟娉�
	 * 
	 * @return JMenuBar
	 */
	protected MenuBar getFrameMenuBar() {
		if (frameMenuBar == null) {
			frameMenuBar = new MenuBar(getDesktopPane(), getStateLabel(),socketClient);
			//JMenuItem shop = frameMenuBar.getcomputershopItem();
			//shop.doClick();
		}
		return frameMenuBar;
	}
	
	/**
	 * This method initializes desktopPane
	 * 
	 * @return JDesktopPane
	 */
	public static ClientDesktop getDesktopPane() {
		if (desktopPane == null) {
			desktopPane = new ClientDesktop();
		}
		return desktopPane;
	}
	
	/**
	 * This method initializes statePanel
	 * 
	 * @return JPanel
	 */
	private JPanel getStatePanel() {
		if (statePanel == null) {
			GridBagConstraints gridBagConstraints6 = new GridBagConstraints();
			gridBagConstraints6.gridx = 2;
			gridBagConstraints6.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints6.insets = new Insets(0, 5, 0, 5);
			gridBagConstraints6.gridy = 0;
			GridBagConstraints gridBagConstraints4 = new GridBagConstraints();
			gridBagConstraints4.gridx = 3;
			gridBagConstraints4.gridy = 0;
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridx = 6;
			gridBagConstraints3.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints3.insets = new Insets(0, 5, 0, 5);
			gridBagConstraints3.gridy = 0;
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.gridx = 5;
			gridBagConstraints11.insets = new Insets(0, 5, 0, 5);
			gridBagConstraints11.gridy = 0;
			nowDateLabel = new JLabel();
			Date now = new Date();
			nowDateLabel.setText(String.format("%tF", now));
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 7;
			gridBagConstraints2.weightx = 0.0;
			gridBagConstraints2.fill = GridBagConstraints.NONE;
			gridBagConstraints2.gridy = 0;
			nameLabel = new JLabel("Welcome to Online Computer Selling  ");
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 4;
			gridBagConstraints1.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints1.weighty = 1.0;
			gridBagConstraints1.insets = new Insets(0, 5, 0, 5);
			gridBagConstraints1.gridy = 0;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.gridy = 0;
			statePanel = new JPanel();
			statePanel.setLayout(new GridBagLayout());
			statePanel.setBorder(BorderFactory.createBevelBorder(RAISED));
			statePanel.add(getStateLabel(), gridBagConstraints);
			statePanel.add(getJSeparator(), gridBagConstraints1);
			statePanel.add(nameLabel, gridBagConstraints2);
			statePanel.add(getJSeparator1(), gridBagConstraints3);
			statePanel.add(nowDateLabel, gridBagConstraints11);
			statePanel.add(getCzyStateLabel(), gridBagConstraints4);
			statePanel.add(getJSeparator2(), gridBagConstraints6);
		}
		return statePanel;
	}
	
	public static JLabel getCzyStateLabel() {
		if (czyStateLabel == null) {
			czyStateLabel = new JLabel("operator锛�");
		}
		return czyStateLabel;
	}
	
	public static JLabel getStateLabel() {
		if (stateLabel == null) {
			stateLabel = new JLabel();
			stateLabel.setText("No window choosed");
		}
		return stateLabel;
	}
	
	/**
	 * This method initializes jSeparator
	 * 
	 * @return JSeparator
	 */
	private JSeparator getJSeparator() {
		JSeparator jSeparator = new JSeparator();
		jSeparator.setOrientation(JSeparator.VERTICAL);
		return jSeparator;
	}
	
	/**
	 * This method initializes jSeparator1
	 * 
	 * @return JSeparator
	 */
	private JSeparator getJSeparator1() {
		if (jSeparator1 == null) {
			jSeparator1 = new JSeparator();
			jSeparator1.setOrientation(SwingConstants.VERTICAL);
		}
		return jSeparator1;
	}
	
	/**
	 * This method initializes jSeparator2
	 * 
	 * @return JSeparator
	 */
	private JSeparator getJSeparator2() {
		if (jSeparator2 == null) {
			jSeparator2 = new JSeparator();
			jSeparator2.setOrientation(SwingConstants.VERTICAL);
		}
		return jSeparator2;
	}
	
	/**
	 * This is the default constructor
	 */
	public MainBox(Socket socketClient) {
		super();
		initialize();
		this.socketClient = socketClient;
	}
	
	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(800, 600);
		this.setJMenuBar(getFrameMenuBar());
		this.setContentPane(getFrameContentPane());
		this.setBackground(Color.CYAN);
		this.setTitle("OCS Client");
	}
	
	/**
	 * This method initializes frameContentPane
	 * 
	 * @return JPanel
	 */
	private JPanel getFrameContentPane() {
		if (frameContentPane == null) {
			frameContentPane = new JPanel();
			frameContentPane.setLayout(new BorderLayout());
			frameContentPane.add(getStatePanel(), SOUTH);
			frameContentPane.add(getJJToolBarBar(), NORTH);
			frameContentPane.add(getDesktopPane(), CENTER);
		}
		frameContentPane.setBackground(Color.CYAN);
		return frameContentPane;
	}
	public static Socket getSocketClient(){
		return socketClient;
	}
}