package com.OCS;

/*
 * 
 * Author: Mingju Li 10574864 876508
 * 
 * The very start point of the server service
 * The location of the main function
 * 
 * Firstly Initialize the login frame
 * When login frame finished, set visible
 * 
 * Add MenuBar
 * Add ToolBar
 * Add Desktop
 * Add StatePanel
 * 
 * 
 * */

import java.awt.*;
import javax.swing.*;

import com.OCS.Socket.ServerMainThread;

import static java.awt.BorderLayout.*;

/*
 * Create a main frame
 * */
public class ServerFrame extends JFrame {
	private static final long serialVersionUID=1L;
	
	private JPanel contentPanel=null;
	//The container of all JComponent of this JFrame
	
	private MenuBar menuBar=null;
	private ToolBar toolBar=null;
	private ServerDesktop desktop=null;
	private StatePanel statePanel=null;
	
	/*The main function
	 *The start of the whole server
	 * */
	public static void main(String args[]){
		new Thread(new ServerMainThread()).start();
		SplashScreen splashScreen=SplashScreen.getSplashScreen();
		LoginFrame login=new LoginFrame();
		
		if(splashScreen!=null){
			try{
				login.setDefaultCloseOperation(EXIT_ON_CLOSE);
				Thread.sleep(1000);
			}catch(Exception e){
				System.out.println("An Error occur in the splashing process(ServerFrame.java)");
			}
		}
		login.setVisible(true);
	}
	
	/*The initialization of menu bar
	 * */
	private MenuBar initMenuBar(){
		if(menuBar==null)
			menuBar=new MenuBar(initDesktop(),statePanel.getStateLabel());
		return menuBar;
	}
	
	/*The initialization of tool bar
	 * */
	private ToolBar initToolBar(){
		if(toolBar==null)
			toolBar=new ToolBar();
		return toolBar;
	}
	
	/*The initialization of desktop
	 * */
	private ServerDesktop initDesktop(){
		if(desktop==null)
			desktop=new ServerDesktop();
		return desktop;
	}
	
	/*The initialization of state panel 
	 * Show some necessary information on the state panel
	 * */
	private JPanel initStatePanel(){
		if(statePanel==null)
			statePanel=new StatePanel();
		return statePanel;
	}
	
	/*Default constructor
	 * */
	public ServerFrame(){
		super();
		this.setSize(900,640);
		this.setResizable(false);
		this.setTitle("Online Computer Selling(OCS)-Server-Caesar Technology");
		
		Dimension size=getToolkit().getScreenSize();
		setLocation((size.width-900)/2,(size.height-640)/2);
		
		if (contentPanel == null) {
			contentPanel = new JPanel();
			contentPanel.setLayout(new BorderLayout());
			contentPanel.add(initToolBar(), NORTH);
			contentPanel.add(initDesktop(), CENTER);
			contentPanel.add(initStatePanel(), SOUTH);
		}
		
		/*The initialization of the contentPanel must be done first
		 * Because the initialization of the menu bar depends on the state panel
		 * */
		this.setJMenuBar(initMenuBar());
		this.setContentPane(contentPanel);
	}	
}