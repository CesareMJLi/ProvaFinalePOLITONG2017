package com.OCS.client;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.border.EtchedBorder;

public class ToolBar extends JToolBar{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton contactUs=new JButton();
	private MenuBar menuBar;
	private static String contactMsg=
			"Any Suggestions, please send an email to client developer:"
			+ "\n                       Giustinoyang@gmail.com";
	private ToolBar() {
	}

	public ToolBar(MenuBar frameMenuBar) {
		super();
		this.menuBar = frameMenuBar;
		this.setFloatable(false);
		this.setVisible(true);
		initialize();
	}

	/**
	 * 界面初始化方法
	 * 设置大小，加入各个按钮
	 */
	private void initialize() {
		setSize(new Dimension(600, 24));
		setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
//		add(createToolButton(menuBar.getcomputershopItem()));
//		add(createToolButton(menuBar.getcomponentshopItem()));
		add(createToolButton(menuBar.getshoppingbagItem()));
//		add(createToolButton(menuBar.getactiveorderItem()));
//		add(createToolButton(menuBar.getdeadorderItem()));
//		add(createToolButton(menuBar.getchangepasswordItem()));
//		add(createToolButton(menuBar.getchangeemailItem()));
		contactUs.setText("Contact Us");
		contactUs.setToolTipText("Contact the developer");
		contactUs.setFocusable(false);
		contactUs.setIcon(new ImageIcon(getClass().getResource("/res/icon/Contact.jpg")));
		contactUs.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		contactUs.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JOptionPane.showMessageDialog(null,contactMsg);
			}
		});
		this.add(contactUs);
	}

	/**
	 * 创建工具栏按钮的方法
	 * 名称、图标、监听全都来自菜单项
	 * @return javax.swing.JButton
	 */
	private JButton createToolButton(final JMenuItem item) {
		JButton button = new JButton();
		button.setText(item.getText());
		button.setToolTipText(item.getText());
		button.setIcon(item.getIcon());
		button.setFocusable(false);
		button.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				item.doClick();
			}
		});
		return button;
	}

	public void setMenuBar(MenuBar menuBar) {
		this.menuBar = menuBar;
	}

}
