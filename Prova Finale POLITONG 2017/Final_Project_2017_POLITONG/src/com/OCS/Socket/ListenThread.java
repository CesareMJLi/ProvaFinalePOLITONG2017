package com.OCS.Socket;

class ListenThread implements Runnable  
{  
    private SocketServer socketServer;  
    
    public ListenThread(SocketServer socketServer)  {  
        this.socketServer = socketServer;  
    }
    
    public void run()  {  
        while(true)  {  
        	for(int i=0;i<socketServer.mysocket.size();i++){  
        		if(socketServer.mysocket.get(i).isClosed())  {   
                        socketServer.mysocket.remove(socketServer.mysocket.get(i));             
                        System.out.println("Client has gone offline.");  
                }  
            }  
          
	        try{  
	            Thread.sleep(1000);  
	        }catch(Exception e){
	        	e.printStackTrace();
	        	System.out.println("An error occurs at the listening thread");
	        }  
	        //This thread does not have a break
	        //As long as the main thread is running this listening thread is running
	        //Keep listening the process of the client
        }
    }  
    /*
     * This thread is started in the beginning of the socket server
     * It only start once and would always running to monitor the state of the client state
     * If a client drop the link this listen thread would notify the server thread
     * This would automatically started every 500 milli-second
     * 
     * */
}  

