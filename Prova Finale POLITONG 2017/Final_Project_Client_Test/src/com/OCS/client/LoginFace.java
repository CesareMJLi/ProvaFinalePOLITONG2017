package com.OCS.client;

import java.awt.Graphics;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class LoginFace extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Image Img;
	
	public LoginFace(){
		super();
		URL Url=getClass().getResource("/res/login.jpg");
		Img=new ImageIcon(Url).getImage();
	}
	
	protected void Paint(Graphics G){
		super.paintComponent(G);
		G.drawImage(Img, 0, 0, this);
	}
}