package com.OCS.Socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class SocketServer {
	public ServerSocket server;
	public LinkedList<Socket> mysocket = new LinkedList<Socket>(); 
	
	public final static int PORT=12345;
	
	public SocketServer(){
		
	}
	public void startServer() {    
        int count=0;    
        try {   
            server = new ServerSocket(PORT);    
            
            System.out.println("The Server service has been startas from port No.12345");   
            new Thread(new ListenThread(this)).start(); 
            
            while (true) {    
                Socket socket = server.accept();  
                mysocket.add(socket);  
                count++;    
                System.out.println("The user " + count + " connected successively");   
                new Thread(new ServerThread(socket)).start();
            }    
        } catch (IOException e) {    
            e.printStackTrace();    
            System.out.println("Error! Socket Server executed failed!");
        }    
    }    

}





