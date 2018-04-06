package com.OCS;

/*
 * 
 * Author: Mingju Li 10574864 876508
 * 
 * Initialize the background of the server frame
 * 
 * 
 * */

import java.awt.*;
import java.net.URL;

import javax.swing.*;

public class ServerDesktop extends JDesktopPane {

	private static final long serialVersionUID = 1L;
	private final Image backImage;

	public ServerDesktop() {
		super();
		URL url = ServerDesktop.class.getResource("/resource/back.jpg");
		backImage = new ImageIcon(url).getImage();
	}
	
	protected void paintComponent(Graphics g) {
		//super.paintComponent(g);
		int width = getWidth();
		int height = this.getHeight();
		g.drawImage(backImage, 0, 0, width, height, this);
	}
}