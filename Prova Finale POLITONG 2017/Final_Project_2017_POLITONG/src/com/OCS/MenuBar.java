package com.OCS;

/*
 * 
 * Author: Mingju Li 10574864 876508
 * 
 * Initialize the information of the menu bar and menu item
 * 
 * Menu 1-Computer Menu:
 * Edit Computer
 * Check all the computer in the system
 * 
 * Menu 2-Component Menu:
 * Edit Component
 * Check all the component in the system
 * 
 * Menu 3-Order Menu:
 * Manage the current order
 * Manage the order history
 * 
 * 
 * */

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import com.OCS.InternalFrame.*;

public class MenuBar extends JMenuBar {
	
	private static final long serialVersionUID=1L;
	
/////////////////////////////ORDER MENU/////////////////////////////////////
	private JMenu orderMenu=null;
	private JMenuItem checkActiveOrder=null;
	private JMenuItem checkDeadOrder=null;
	
/////////////////////////////COMPUTER MENU/////////////////////////////////////	
	private JMenu computerMenu=null;
	private JMenuItem editComputer=null;
	private JMenuItem checkComputer=null;
	
/////////////////////////////COMPONENT MENU/////////////////////////////////////
	private JMenu componentMenu=null;
	private JMenuItem editComponent=null;
	private JMenuItem checkComponent=null;

//////////////////////////////////////////////////////////////////////////////////
	
	private JLabel state=null;
	private ServerDesktop desktop=null;
	private Map<JMenuItem, JInternalFrame> map=null;
	private int nextFrameX, nextFrameY;
	
	public MenuBar(ServerDesktop desktopPanel, JLabel label) {
		super();
		map=new HashMap<JMenuItem, JInternalFrame>();
		//An hash map of menu item and JInternalFrame
		
		this.desktop=desktopPanel;
		this.state=label;
		initialize();
	}

	private void initialize() {
		this.setSize(new Dimension(600, 24));
		initComputerMenu();
		initComponentMenu();
		initOrderMenu();
		/*In the initialization of each menu
		 * It contains the initialization of each menu item
		 * */
	}

/////////////////////////////ORDER MENU/////////////////////////////////////
	public void initOrderMenu() {
		if (orderMenu==null) {
			orderMenu=new JMenu("Order(O)");
			orderMenu.setMnemonic(KeyEvent.VK_O);
			orderMenu.add(initCheckActOrder());
			orderMenu.add(initCheckDeadOrder());
		}
		this.add(orderMenu);
	}

	public JMenuItem initCheckActOrder() {
		if (checkActiveOrder==null) {
			checkActiveOrder=new JMenuItem("Current Order");
			checkActiveOrder.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			checkActiveOrder.setIcon(new ImageIcon(getClass().getResource("/resource/icon/ordertocheck.jpg")));
			checkActiveOrder.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					createInternalFrame(checkActiveOrder, CurrentOrder.class);
				}
			});
		}
		return checkActiveOrder;
	}

	public JMenuItem initCheckDeadOrder() {
		if (checkDeadOrder==null) {
			checkDeadOrder=new JMenuItem("Order History");
			checkDeadOrder.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			checkDeadOrder.setIcon(new ImageIcon(getClass().getResource("/resource/icon/checkorder.jpg")));
			checkDeadOrder.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							createInternalFrame(checkDeadOrder, HistoryOrder.class);
						}
					});
		}
		return checkDeadOrder;
	}

/////////////////////////////COMPUTER MENU/////////////////////////////////////
	public void initComputerMenu() {
		if (computerMenu==null) {
			computerMenu=new JMenu("Computer(C)");
			computerMenu.setMnemonic(KeyEvent.VK_C);//快捷键
			computerMenu.add(initEditComputer());
			computerMenu.add(initCheckComputer());
		}
		this.add(computerMenu);
	}

	public JMenuItem initEditComputer() {
		if (editComputer==null) {
			editComputer=new JMenuItem("Edit Computer");
			editComputer.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			editComputer.setIcon(new ImageIcon(getClass().getResource("/resource/icon/editcomputer.jpg")));
			editComputer.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					createInternalFrame(editComputer, EditComputer.class);//鼠标监听，打开内部窗口
				}
			});
		}
		return editComputer;
	}

	public JMenuItem initCheckComputer() {
		if (checkComputer==null) {
			checkComputer=new JMenuItem("Manage Computer");
			checkComputer.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			checkComputer.setIcon(new ImageIcon(getClass().getResource("/resource/icon/checkcomputer.jpg")));
			checkComputer.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					createInternalFrame(checkComputer, CheckComputer.class);//鼠标监听，打开内部窗口
				}
			});
		}
		return checkComputer;
	}
	
/////////////////////////////COMPONENT MENU/////////////////////////////////////
	public void initComponentMenu() {
		if (componentMenu==null) {
			componentMenu=new JMenu("Component(M)");
			componentMenu.setMnemonic(KeyEvent.VK_M);//快捷键
			componentMenu.add(initEditComponent());
			componentMenu.add(initCheckComponent());
		}
		this.add(componentMenu);
	}

	public JMenuItem initEditComponent() {
		if (editComponent==null) {
			editComponent=new JMenuItem("Edit Component");
			editComponent.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			editComponent.setIcon(new ImageIcon(getClass().getResource("/resource/icon/editcomponent.jpg")));
			editComponent.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					createInternalFrame(editComponent, EditComponent.class);
				}
			});
		}
		return editComponent;
	}

	public JMenuItem initCheckComponent() {
		if (checkComponent==null) {
			checkComponent=new JMenuItem("Manage Component");
			checkComponent.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			checkComponent.setIcon(new ImageIcon(getClass().getResource("/resource/icon/checkcomponent.jpg")));
			checkComponent.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							createInternalFrame(checkComponent, CheckComponent.class);
						}
					});
		}
		return checkComponent;
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////
	/*Create an internal Frame
	 * item: the menu item
	 * class: the object of internal frame
	 * */
	
	private JInternalFrame createInternalFrame(JMenuItem item, Class c){
		Constructor con= c.getConstructors()[0];
		JInternalFrame internalFrame=map.get(item);
		try{
			if(internalFrame==null||internalFrame.isClosed()){
				internalFrame=(JInternalFrame) con.newInstance(new Object[]{});
				map.put(item, internalFrame);
				internalFrame.setFrameIcon(item.getIcon());
				internalFrame.setLocation(nextFrameX, nextFrameY);
				
				int internalSpace=50;
				nextFrameX+=internalSpace;
				nextFrameY+=internalSpace;
				
				if(nextFrameY+internalFrame.getHeight()>desktop.getHeight()){
					nextFrameX=0;
					nextFrameY=0;
				}
				
				desktop.add(internalFrame);
				internalFrame.setVisible(true);
				internalFrame.setMaximizable(false);
				internalFrame.setResizable(false);
			}
			
			internalFrame.setSelected(true);
			state.setText(internalFrame.getTitle());
			
			internalFrame.addInternalFrameListener(new InternalFrameAdapter(){
				public void internalFrameActivated(InternalFrameEvent e){
					super.internalFrameActivated(e);
					JInternalFrame frame=e.getInternalFrame();
					state.setText(frame.getTitle());
				}
				
				public void internalFrameDeactivated(InternalFrameEvent e){
					state.setText("No internal frame selected");
				}
			});
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("An error occurs at the initialization of menubar (MenuBar.java)");
		}
		return internalFrame;
	}
}
