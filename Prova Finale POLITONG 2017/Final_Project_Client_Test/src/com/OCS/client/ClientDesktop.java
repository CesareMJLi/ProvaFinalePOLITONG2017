package com.OCS.client;

import java.net.URL;
import java.awt.*;
import javax.swing.*;

public class ClientDesktop extends JDesktopPane{
	
	/**
	 *  default
	 */
	private static final long serialVersionUID = 1L;
	private final Image BackIma;
	
	public ClientDesktop(){
		super();
		URL url=ClientDesktop.class.getResource("/res/back.jpg");
		BackIma=new ImageIcon(url).getImage();
	}
	protected void PainCom(Graphics G){
		//int Width=getWidth();
		//int Height=this.getHeight();
		G.setColor(Color.CYAN);
		//G.drawImage(BackIma, 0, 0, Width, Height, this);
	}
}
