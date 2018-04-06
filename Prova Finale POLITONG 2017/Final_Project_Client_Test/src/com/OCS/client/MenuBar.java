package com.OCS.client;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import com.OCS.InnerFace.*;

public class MenuBar extends JMenuBar{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JMenu computershop_Menu = null;

	private JMenuItem computershopItem = null;

	private JMenuItem componentshopItem = null;

	private JMenu shoppingbag_Menu = null;

	private JMenuItem shoppingbagItem = null;

	private JMenu myorder_Menu = null;

	private JMenuItem activeorderItem = null;

	private JMenuItem deadorderItem = null;

	private JMenu myinformation_Menu = null;

	private JMenuItem myinformationItem = null;

	private JMenuItem changepasswordItem = null;

	private JMenuItem changeemailItem = null;

	private JLabel stateLabel = null;

	private JDesktopPane desktopPanel = null;

	private Map<JMenuItem, JInternalFrame> iFrames = null;

	private int nextFrameX, nextFrameY;
	
	private Socket socketClient;
	////////////////////////////////////////////////////
	/**
	 * 榛樿鐨勬瀯閫犳柟娉�
	 * 
	 */
	private MenuBar() {
	}
	//鍒濆鍖栬彍鍗曟爮鐣岄潰鐨勬柟娉�
	public MenuBar(JDesktopPane desktopPanel, JLabel label,Socket socketClient) {
		super();
		this.socketClient = socketClient;
		iFrames = new HashMap<JMenuItem, JInternalFrame>(); //涓�涓彍鍗曢」涓庡唴閮ㄧ獥鍙ｆ槧灏勭殑鍝堝笇琛�
		this.desktopPanel = desktopPanel;
		this.stateLabel = label;
		initialize();
	}

	/**
	 * 鍒濆鍖栬彍鍗曟爮鐣岄潰鐨勬柟娉�
	 * 涓鸿彍鍗曟爮鍔犲叆鑿滃崟
	 */
	private void initialize() {
		this.setSize(new Dimension(600, 24));
		add(getcomputershop_Menu());
		//add(getshoppingbag_Menu());
		add(getmyorder_Menu());
		//add(getmyinformation_Menu());
	}
/////////////////////////////////////////////////////////////////////////
	/**
	 * 鍒濆鍖栧晢鍩庤彍鍗曠殑鏂规硶
	 * 
	 * @return javax.swing.JMenu
	 */
	public JMenu getcomputershop_Menu() {
		if (computershop_Menu == null) {
			computershop_Menu = new JMenu();
			computershop_Menu.setText("Store(S)");
			computershop_Menu.setMnemonic(KeyEvent.VK_S);
			computershop_Menu.add(getcomputershopItem());
			//computershop_Menu.add(getcomponentshopItem());
			
		}
		return computershop_Menu;
	}

	/**
	 * 鍒濆鍖栵紙鐢佃剳鍟嗗煄锛夎彍鍗曢」鐨勬柟娉� 璇ユ柟娉曞畾涔夎彍鍗曢」鎵撳紑寰呭晢鍩庣獥鍙�,骞朵娇绐楀彛澶勪簬琚�夋嫨鐘舵��
	 * 
	 * @return javax.swing.JMenuItem
	 */
	public JMenuItem getcomputershopItem() {
		if (computershopItem == null) {
			computershopItem = new JMenuItem();
			computershopItem.setText("Computer");
			computershopItem.setIcon(new ImageIcon(getClass().getResource(
					"/res/icon/Computer.jpg")));
			computershopItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					createIFrame(computershopItem, ComputerCheck.class);
				}
			});
		}
		return computershopItem;
	}
	/**
	 * 鍒濆鍖栵紙閰嶄欢鍟嗗煄锛夎彍鍗曢」鐨勬柟娉� 璇ユ柟娉曞畾涔夎彍鍗曢」鎵撳紑寰呭晢鍩庣獥鍙�,骞朵娇绐楀彛澶勪簬琚�夋嫨鐘舵��
	 * 
	 * @return javax.swing.JMenuItem
	 */
	public JMenuItem getcomponentshopItem() {
		if (componentshopItem == null) {
			componentshopItem = new JMenuItem();
			componentshopItem.setText("Component");
			componentshopItem.setIcon(new ImageIcon(getClass().getResource(
					"/res/icon/Component.jpg")));
			componentshopItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					createIFrame(componentshopItem, ComponentCheck.class);//榧犳爣鐩戝惉锛屾墦寮�鍐呴儴绐楀彛
				}
			});
		}
		return componentshopItem;
	}
//////////////////////////////////////////////////////////////////////////////
	/**
	 * 鍒濆鍖栬喘鐗╄溅鑿滃崟鐨勬柟娉�
	 * 
	 * @return javax.swing.JMenu
	 */
/*	public JMenu getshoppingbag_Menu() {
		if (shoppingbag_Menu == null) {
			shoppingbag_Menu = new JMenu();
			shoppingbag_Menu.setText("Cart");
			//jinhuo_Menu.setMnemonic(KeyEvent.VK_J);//蹇嵎閿�
			shoppingbag_Menu.add(getshoppingbagItem());
			
		}
		return shoppingbag_Menu;
	}*/

	/**
	 * 鍒濆鍖栵紙璐墿杞︼級鑿滃崟椤圭殑鏂规硶 璇ユ柟娉曞畾涔夎彍鍗曢」鎵撳紑璐墿杞︾獥鍙�,骞朵娇绐楀彛澶勪簬琚�夋嫨鐘舵��
	 * 
	 * @return javax.swing.JMenuItem
	 */
	public JMenuItem getshoppingbagItem() {
		if (shoppingbagItem == null) {
			shoppingbagItem = new JMenuItem();
			shoppingbagItem.setText("Cart");
			shoppingbagItem.setIcon(new ImageIcon(getClass().getResource(
					"/res/icon/Cart.jpg")));
			shoppingbagItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					createIFrame(shoppingbagItem, CartCheck.class);//榧犳爣鐩戝惉锛屾墦寮�鍐呴儴绐楀彛
				}
			});
		}
		return shoppingbagItem;
	}
/////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 鍒濆鍖栨垜鐨勮鍗曡彍鍗曠殑鏂规硶
	 * 
	 * @return javax.swing.JMenu
	 */
	public JMenu getmyorder_Menu() {
		if (myorder_Menu == null) {
			myorder_Menu = new JMenu();
			myorder_Menu.setText("Shopping(P)");
			myorder_Menu.setMnemonic(KeyEvent.VK_P);
			myorder_Menu.add(getshoppingbagItem());
			myorder_Menu.add(getactiveorderItem());
			myorder_Menu.add(getdeadorderItem());
		}
		return myorder_Menu;
	}

	/**
	 * 鍒濆鍖栵紙褰撳墠璁㈠崟锛夎彍鍗曢」鐨勬柟娉� 璇ユ柟娉曞畾涔夎彍鍗曢」鎵撳紑鎴戠殑璁㈠崟绐楀彛,骞朵娇绐楀彛澶勪簬琚�夋嫨鐘舵��
	 * 
	 * @return javax.swing.JMenuItem
	 */
	public JMenuItem getactiveorderItem() {
		if (activeorderItem == null) {
			activeorderItem = new JMenuItem();
			activeorderItem.setText("Active Order");
			activeorderItem.setIcon(new ImageIcon(getClass().getResource(
					"/res/icon/ActiveOrder.jpg")));
			activeorderItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					createIFrame(activeorderItem, ActiveOrderCheck.class);//榧犳爣鐩戝惉锛屾墦寮�鍐呴儴绐楀彛
				}
			});
		}
		return activeorderItem;
	}
	/**
	 * 鍒濆鍖栵紙鍘嗗彶璁㈠崟锛夎彍鍗曢」鐨勬柟娉� 璇ユ柟娉曞畾涔夎彍鍗曢」鎵撳紑鎴戠殑璁㈠崟绐楀彛,骞朵娇绐楀彛澶勪簬琚�夋嫨鐘舵��
	 * 
	 * @return javax.swing.JMenuItem
	 */
	public JMenuItem getdeadorderItem() {
		if (deadorderItem == null) {
			deadorderItem = new JMenuItem();
			deadorderItem.setText("Dead Order");
			deadorderItem.setIcon(new ImageIcon(getClass().getResource(
					"/res/icon/DeadOrder.jpg")));
			deadorderItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					createIFrame(deadorderItem, DeadOrderCheck.class);
				}
			});
		}
		return deadorderItem;
	}
///////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 鍒濆鍖栦釜浜轰俊鎭彍鍗曠殑鏂规硶
	 * 
	 * @return javax.swing.JMenu
	 */
	public JMenu getmyinformation_Menu() {
		if (myinformation_Menu == null) {
			myinformation_Menu = new JMenu();
			myinformation_Menu.setText("Information(I)");
			myinformation_Menu.setMnemonic(KeyEvent.VK_I);
			myinformation_Menu.add(getchangepasswordItem());
			myinformation_Menu.add(getchangeemailItem());
		}
		return myinformation_Menu;
	}
	public JMenuItem getchangepasswordItem() {
		if (changepasswordItem == null) {
			changepasswordItem = new JMenuItem();
			changepasswordItem.setText("Key Change");
			changepasswordItem.setIcon(new ImageIcon(getClass().getResource(
					"/res/icon/changepassword.jpg")));
			changepasswordItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					createIFrame(changepasswordItem, KeyChange.class);//榧犳爣鐩戝惉锛屾墦寮�鍐呴儴绐楀彛
				}
			});
		}
		return changepasswordItem;
	}
	/**
	 * 鍒濆鍖栵紙鏇存敼閭锛夎彍鍗曢」鐨勬柟娉� 璇ユ柟娉曞畾涔夎彍鍗曢」鎵撳紑涓汉淇℃伅绐楀彛,骞朵娇绐楀彛澶勪簬琚�夋嫨鐘舵��
	 * 
	 * @return javax.swing.JMenuItem
	 */
	public JMenuItem getchangeemailItem() {
		if (changeemailItem == null) {
			changeemailItem = new JMenuItem();
			changeemailItem.setText("E-mail Change");
			changeemailItem.setIcon(new ImageIcon(getClass().getResource(
					"/res/icon/changemailbox.jpg")));
			changeemailItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					createIFrame(changeemailItem, EmailChange.class);//榧犳爣鐩戝惉锛屾墦寮�鍐呴儴绐楀彛
				}
			});
		}
		return changeemailItem;
	}
///////////////////////////////////////////////////////////////////////////////////////////	
	/**
	 * 鍒涘缓鍐呴儴绐椾綋鐨勬柟娉曪紝璇ユ柟娉曚娇鐢ㄥ彂灏勬妧鏈幏鍙栧唴閮ㄧ獥浣撶殑鏋勯�犳柟娉曪紝浠庤�屽垱寤哄唴閮ㄧ獥浣撱��
	 * 
	 * @param item锛氭縺娲昏鍐呴儴绐椾綋鐨勮彍鍗曢」
	 * @param clazz锛氬唴閮ㄧ獥浣撶殑Class瀵硅薄
	 */
	private JInternalFrame createIFrame(JMenuItem item, Class<?> clazz) {
		Constructor<?> constructor = null;
		try {
			constructor = clazz.getConstructor(new Class[]{});
		} catch (NoSuchMethodException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}//鑾峰彇鍐呴儴绐椾綋鐨勬瀯閫犲櫒
		JInternalFrame iFrame = iFrames.get(item);//浠ヨ彍鍗曢」涓簁ey锛屾煡鎵惧搴旂殑鍐呴儴绐椾綋
		try {
			//濡傛灉涓嶅瓨鍦ㄥ綋鍓嶇獥浣擄紝鎴栬�呯獥浣撳浜庡叧闂姸鎬侊紝灏辨墽琛屼互涓嬫搷浣�
			if (iFrame == null || iFrame.isClosed()) {
				iFrame = (JInternalFrame)constructor.newInstance();//浣跨敤鍐呴儴绐椾綋鏋勯�犲櫒锛屾瀯閫犱竴涓柊绐椾綋
			/*	Field field1=clazz.getDeclaredField("socketClient");
				field1.set(iFrame, socketClient);*/
				iFrames.put(item, iFrame);//鎶婃柊绐椾綋鑷充簬鍐呴儴绐椾綋鍝堝笇琛ㄤ腑
				iFrame.setFrameIcon(item.getIcon());//鍐呴儴绐椾綋鍥炬爣鍙栬嚜鑿滃崟椤瑰浘鏍�
				iFrame.setLocation(nextFrameX, nextFrameY);//璁剧疆鍐呴儴绐椾綋浣嶇疆
				////////////////////////////////////////////////////////////////////////////////
				int frameH = iFrame.getPreferredSize().height;//鑾峰彇鍐呴儴绐椾綋楂樺害
				int panelH = iFrame.getContentPane().getPreferredSize().height;//鑾峰彇妗岄潰楂樺害
				//璁剧疆涓嬩竴涓唴閮ㄧ獥鍙ｆ墦寮�浣嶇疆锛岄伩鍏嶇獥鍙ｉ噸鍙犵湅涓嶅埌鐨勭幇璞�
				int fSpacing = frameH - panelH;
				nextFrameX += fSpacing;
				nextFrameY += fSpacing;
				if (nextFrameX + iFrame.getWidth() > desktopPanel.getWidth())
					nextFrameX = 0;
				if (nextFrameY + iFrame.getHeight() > desktopPanel.getHeight())
					nextFrameY = 0;
				/////////////////////////////////////////////////////////////////////////////////
				desktopPanel.add(iFrame);//鎶婂唴閮ㄧ獥鍙ｆ斁鍒版闈笂
				iFrame.setResizable(false);//鍐呴儴绐椾綋澶у皬涓嶅彲鏀瑰彉
				iFrame.setMaximizable(false);//鍐呴儴绐椾綋涓嶈兘鏈�澶у寲
				iFrame.setVisible(true);//浣垮唴閮ㄧ獥浣撳彲瑙�
			}
			//濡傛灉宸插瓨鍦ㄥ綋鍓嶇獥浣擄紝鎴栬�呮墽琛屽畬鍒涘缓鎿嶄綔鍚庯紝灏辨墽琛屼互涓嬫搷浣�
			iFrame.setSelected(true);//浣垮綋鍓嶇獥浣撳浜庤閫変腑鐘舵��
			stateLabel.setText(iFrame.getTitle());//鎶婄姸鎬佹爮涓殑鏍囩璁剧疆鎴愬綋鍓嶇獥浣撳悕瀛�
			//涓哄綋鍓嶅唴閮ㄧ獥浣撴坊鍔犵洃鍚�
			iFrame.addInternalFrameListener(new InternalFrameAdapter() {
				/**
				 * 鍐呴儴绐椾綋鐨勬縺娲绘柟娉曪紝鑾峰彇鍐呴儴绐楀彛锛屽苟灏嗙姸鎬佹爮鏍囩璁剧疆涓哄唴閮ㄧ獥鍙ｅ悕绉�
				 */
				public void internalFrameActivated(InternalFrameEvent e) {
					super.internalFrameActivated(e);
					JInternalFrame frame = e.getInternalFrame();
					stateLabel.setText(frame.getTitle());
				}
				/**
				 * 鍐呴儴绐椾綋鐨勬渶灏忓寲鏂规硶锛岀姸鎬佹爮鏍囩鏇存敼涓衡�滄湭閫夋嫨绐楀彛鈥�
				 */
				public void internalFrameDeactivated(InternalFrameEvent e) {
					stateLabel.setText("No window choosed");
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return iFrame;
	}


}
