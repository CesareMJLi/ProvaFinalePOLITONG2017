package com.OCS.Socket;
//����һ���߳����з�����
public class ServerMainThread implements Runnable{
	private SocketServer server;
	
	public ServerMainThread(){
		//Empty constructor
	}
	
	public void run(){
		SocketServer server = new SocketServer();
		server.startServer(); 
		
		//the whole server thread start here
	}
}
