package com.OCS;

/*
 * 
 * Author: Mingju Li 10574864 876508
 * 
 * Initialize the background for the login frame
 * 
 * 
 * */

import java.awt.*;
import java.net.URL;
import javax.swing.*;

public class LoginPanel extends JPanel {
	private static final long serialVersionUID=1L;
	private Image icon;
	
	public LoginPanel() {
		super();
		URL url=getClass().getResource("/resource/login.jpg");
		icon=new ImageIcon(url).getImage();
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(icon, 10, 30, this);
	}
}

