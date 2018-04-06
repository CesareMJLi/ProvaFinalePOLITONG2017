package com.OCS.Socket;
//开启一个线程运行服务器
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
