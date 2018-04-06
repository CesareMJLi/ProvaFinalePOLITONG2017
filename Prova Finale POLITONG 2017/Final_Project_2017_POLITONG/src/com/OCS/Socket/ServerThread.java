
package com.OCS.Socket;

import java.io.DataInputStream;


import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.OCS.Dao.*;
import com.OCS.Dao.model.*;

public class ServerThread implements Runnable {    
    
    private Socket socket;  
    private String accept = "NULL";  
    private String username,passwd;  
    private DataInputStream in = null;  
    private DataOutputStream out = null;  
    private ObjectInputStream inBean = null;
    private ObjectOutputStream outBean = null;
    private Client client;
    private Boolean check = false;
    
    public ServerThread(Socket socket)  
    {    		 
        this.socket = socket;    		  
    }    
    
    public void run()  
    {    
    	boolean exitLable= true;
    	while(exitLable) {
    		
    		// Read
    		
    		try {    
    		    in = new DataInputStream(socket.getInputStream()); 
    		    accept = in.readUTF(); 
    		    System.out.println("The server is at service for the client");      		      		    
    		}    		      
    		catch (IOException e) {  
        		try {
        			in.close();
        			socket.close();
        			exitLable = false;
    			} 
        		catch (IOException e1) {
        			e.printStackTrace();
        			System.out.println("Error at the initialization of server thread");
        		}    		       
    		} 
    		
    		// LOG IN
    		
    		if(accept.equals("LOGIN"))  
    		{ 
        		System.out.println("Receive LOGIN request");
    		    try  
    		    {  
    		    	out = new DataOutputStream(socket.getOutputStream());     
    		    	username = in.readUTF(); 
    		    	passwd = in.readUTF();
    		    	if (DBClientLogin.exists(username)) {
    		    		out.writeUTF("true"); 
    		    		out.flush();
    		    		if (DBClientLogin.check(username, passwd)) {
    		    			out.writeUTF("true");   
    		    			out.flush();
       				}else {
       					out.writeUTF("false");
       					out.flush();
       					}      			
    		    	}else {
    		        	  out.writeUTF("false");
    		        	  out.flush();
    		    	}    		    		    		     
    		    }
    		    catch(IOException e) {
    		    	e.printStackTrace();
    		    	System.out.println("Error at LOGIN Request");
    		    }  
    		} 
    		
    		// SIGN UP
    		
    		else if(accept.equals("SIGNUP"))  
    		{
    			System.out.println("Receive SIGNUP request");
    			try {
    				out = new DataOutputStream(socket.getOutputStream());
    		     	username = in.readUTF();    			
    		     	if(DBClientLogin.exists(username)) {
    		     		out.writeUTF("true");
    		     		out.flush();
    		     	}
    		     	else {
    		     		out.writeUTF("false");
    		     		out.flush();
    		     		inBean = new ObjectInputStream(socket.getInputStream());
    		     		Client client = (Client) inBean.readObject();
    		     		if(DBClientLogin.save(client)) {
    		     			out.writeUTF("true");
    		     			out.flush();
    		     		}
    		     		else {
    		     			out.writeUTF("false");
    		     			out.flush();
    		     		}    			
    		     	}    			
				} 
    			catch (IOException | ClassNotFoundException e) {	
    				e.printStackTrace();
				}
    		} 

    		// SHOW ALL Computer
    		
    		else if(accept.equals("SHOWALLCPR")) {
        		System.out.println("Receive CHECK COMPUTER request");
        		List<Computer> list = DBCheckComputer.select();
        		try {
        			outBean = new ObjectOutputStream(socket.getOutputStream());
        			outBean.writeObject(list);
        			outBean.flush();
				} 
        		catch (IOException e) {
        			e.printStackTrace();
				}	
    		}
    		
    		// QUERY
    		
    		else if(accept.equals("QUERY")) {
        		System.out.println("Receive QUERY request");        		
        		try {
        			in = new DataInputStream(socket.getInputStream());   
        			String condition = in.readUTF(); 
        			List<Computer> list = DBCheckComputer.selectwithcondition(condition);
        			outBean = new ObjectOutputStream(socket.getOutputStream());
        			outBean.writeObject(list);
        			outBean.flush();
				} 
        		catch (IOException e) {
        			e.printStackTrace();
				}
    		}
    		
    		// SHOW ACTIVE ORDER
    		
    		else if(accept.equals("SHOWACTIVEORDER")) {
        		System.out.println("Receive CURRENT ORDER request");        		 			   
        		try {
        			in = new DataInputStream(socket.getInputStream());
        			String name_client = in.readUTF();
        			int id_client = DBClientLogin.getClientID(name_client);
	        		outBean = new ObjectOutputStream(socket.getOutputStream());
	        		List<Order> list = DBEditOrder.getClientActiveOrder(id_client);
	        		outBean.writeObject(list);
	        		outBean.flush();    			    			
				} 
        		catch (IOException e) {
	    			e.printStackTrace();
				}        		
    		}
    		
    		// SHOW DEAD ORDER
    		
    		else if(accept.equals("SHOWDEADORDER")) {
        		System.out.println("Receive ORDER HISTORY request");        		 			   
        		try {
        			in = new DataInputStream(socket.getInputStream());
        			String name_client = in.readUTF();
        			int id_client = DBClientLogin.getClientID(name_client);
	        		outBean = new ObjectOutputStream(socket.getOutputStream());
	        		List<Order> list = DBEditOrder.getClientDeadOrder(id_client);
	        		outBean.writeObject(list);
	        		outBean.flush();    			    			
				} 
        		catch (IOException e) {
        			e.printStackTrace();
				}
    		}
    		
    		// Bag
    		
    		else if(accept.equals("BAG")) {
        		System.out.println("Receive ADD INTO SHOPPING BAG request");
        		try {				
        			in = new DataInputStream(socket.getInputStream());
        			String name_client = (String) in.readUTF();
        			int id_client = DBClientLogin.getClientID(name_client);
        			inBean = new ObjectInputStream(socket.getInputStream());
        			List<Bag> baglist = (List<Bag>) inBean.readObject();
        			
        			if(DBCheckBag.addtoBag(id_client,baglist)) {
        				out = new DataOutputStream(socket.getOutputStream());
        				out.writeUTF("True");
        				out.flush();
        			}
        			else {
        				out = new DataOutputStream(socket.getOutputStream());
        				out.writeUTF("False");
        				out.flush();
        			}
        		} 
        		catch (IOException | ClassNotFoundException e) {
        			e.printStackTrace();
        		}
    		}
    		
    		// SHOW ALL BAG
    		
    		else if(accept.equals("SHOWALLBAG")) {
        		System.out.println("Receive SHOW BAG CONTENT request");        		 			   
        		try {
        			in = new DataInputStream(socket.getInputStream());
        			String name_client = in.readUTF();
        			int id_client = DBClientLogin.getClientID(name_client);
	        		outBean = new ObjectOutputStream(socket.getOutputStream());
	        		List<Bag> list = DBCheckBag.getClientBag(id_client);
	        		outBean.writeObject(list);
	        		outBean.flush();    			    			
				} 
        		catch (IOException e) {
        			e.printStackTrace();
				}
    		}
    		
    		// ORDER
    		
    		/*
    		else if(accept.equals("ORDER")) {
        		System.out.println("Receive SET ORDER request");   		
        		try {
        			inBean = new ObjectInputStream(socket.getInputStream());
        			Map<String,Integer> computermap = (Map<String,Integer>) inBean.readObject();
        			Map<String,Integer> componentmap = (Map<String,Integer>) inBean.readObject();	        		  
        			Iterator<Map.Entry<String, Integer>> entries = computermap.entrySet().iterator();
        			
        			out = new DataOutputStream(socket.getOutputStream());
        			out.writeUTF("True");//核查库存后判断是否弹出付款对话框
        			out.flush();			        			   
        		} 
        		catch (IOException e) {
        			e.printStackTrace();
				} 
        		catch (ClassNotFoundException e) {
        			e.printStackTrace();
				}
    		}
    		*/
    		// SAVE ORDER
    		
    		else if (accept.equals("SAVEORDER")) {
    			System.out.println("Receive SAVE ORDER request");
        		try {     		   
        			inBean = new ObjectInputStream(socket.getInputStream());
        			Map<String,Integer> computermap = (Map<String,Integer>) inBean.readObject();
        			Map<String,Integer> componentmap = (Map<String,Integer>) inBean.readObject();	       		   
        			inBean = new ObjectInputStream(socket.getInputStream());
        			Order order = (Order)inBean.readObject();
        			List<OrderInDetail> detiallist = (List<OrderInDetail>) inBean.readObject(); 
        			if(DBEditOrder.save(order,detiallist)) {
        				out.writeUTF("True");
        				out.flush();	
        				Iterator<Map.Entry<String, Integer>> entries = computermap.entrySet().iterator();				   
        				System.out.println("New order to be handled!");      			       
        			}      		
        		} 
        		catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
        		}			   
    		}
    		
    		// ARRIVE ORDER
    		
    		else if(accept.equals("ARRIVEORDER")) {
        		System.out.println("Receive DELIVERY CONFIRMATION request");
        		try {
        			in = new DataInputStream(socket.getInputStream());
        			String id_order = in.readUTF();
        			DBEditOrder.ChangeOrderState(id_order, "2");	
    				out = new DataOutputStream(socket.getOutputStream());
    				out.writeUTF("True");
				    out.flush();
				} 
        		catch (IOException e) {
    				e.printStackTrace();
				}
    		}
    		
    		// CANCEL ORDER
    		
    		else if(accept.equals("CANCELORDER")) {
        		System.out.println("Receive CANCEL ORDER request");
        		try {
        			in = new DataInputStream(socket.getInputStream());
        			String id_order = in.readUTF();
        			DBEditOrder.ChangeOrderState(id_order, "3");	
    				List<OrderInDetail> detiallist = DBEditOrder.getDetiallist(id_order);
        			Map<String,Integer> computermap = new HashMap<String,Integer>();
        			Map<String,Integer> componentmap = new HashMap<String,Integer>();
        			Iterator iterator = detiallist.iterator();
        			while(iterator.hasNext()) {
        				
        				OrderInDetail detial = (OrderInDetail) iterator.next();
        				
        				int p = computermap.getOrDefault(detial.getcomputerID(), 0);
        				if(p==0) {computermap.put(detial.getcomputerID(), detial.getnumber());}
        				else {computermap.put(detial.getcomputerID(), p+detial.getnumber());}
        				
        				p = componentmap.getOrDefault(detial.getcolor(), 0);
        				if(p==0) {componentmap.put(detial.getcolor(),detial.getnumber());}
        				else {componentmap.put(detial.getcolor(), p+detial.getnumber());}
        				
        				p = componentmap.getOrDefault(detial.getsize(), 0);
	    				if(p==0) {componentmap.put(detial.getsize(),detial.getnumber());}
	    				else {componentmap.put(detial.getsize(), p+detial.getnumber());}
	    				
	    				p = componentmap.getOrDefault(detial.getstock(), 0);
	    				if(p==0) {componentmap.put(detial.getstock(),detial.getnumber());}
	    				else {componentmap.put(detial.getstock(), p+detial.getnumber());}
	    				
	    				p = componentmap.getOrDefault(detial.getmemory(), 0);
	    				if(p==0) {componentmap.put(detial.getmemory(),detial.getnumber());}
	    				else {componentmap.put(detial.getmemory(), p+detial.getnumber());}
	    				
	    				p = componentmap.getOrDefault(detial.getgraphics(), 0);
	    				if(p==0) {componentmap.put(detial.getgraphics(),detial.getnumber());}
	    				else {componentmap.put(detial.getgraphics(), p+detial.getnumber());}
	    				
	    				p = componentmap.getOrDefault(detial.getprocessor(), 0);
	    				if(p==0) {componentmap.put(detial.getprocessor(),detial.getnumber());}
	    				else {componentmap.put(detial.getprocessor(), p+detial.getnumber());}
	    				
	    			}
	    			System.out.println(computermap);
	    			System.out.println(componentmap);
	    			Iterator<Map.Entry<String, Integer>> entries = computermap.entrySet().iterator();
	    			out = new DataOutputStream(socket.getOutputStream());
	    			out.writeUTF("True");
	    			out.flush();
        		} 
        		catch (IOException e) {
        			e.printStackTrace();
				}
    		}
    		
    		// DELETE ORDER
    		
    		else if(accept.equals("DELETEORDER")) {
        		System.out.println("Receive DELETE ORDER request ");
        		try {
        			in = new DataInputStream(socket.getInputStream());
        			String id_order = in.readUTF();
        			DBEditOrder.DeleteOrder(id_order);	
	    			out = new DataOutputStream(socket.getOutputStream());
	    			out.writeUTF("True");
				    out.flush();
				} 
        		catch (IOException e) {
        			e.printStackTrace();
				}
    		}
    		
    		// DELETEBAG
    		
    		else if(accept.equals("DELETEBAG")) {
        		System.out.println("Receive DELETE BAG request");
        		try {
        			in = new DataInputStream(socket.getInputStream());
        			int id_bag = in.readInt();
        			DBCheckBag.DeleteBag(id_bag);
        			out = new DataOutputStream(socket.getOutputStream());
        			out.writeUTF("True");
				    out.flush();
				} 
        		catch (IOException e) {
        			e.printStackTrace();
				}
    		}
    		
    		// COMBO COLOR
    		
    		else if (accept.equals("COMBOCOLOR")) {
        		System.out.println("Get COLOR");
        		List<String> itemlist = DBCheckComponent.getComponentNameList("Color");
        		try {
        			outBean = new ObjectOutputStream(socket.getOutputStream());
        			outBean.writeObject(itemlist);
        			outBean.flush();
        		} 
        		catch (IOException e) {
        			e.printStackTrace();
				}
    		}
    		
    		// COMBO SIZE
    		
    		else if (accept.equals("COMBOSIZE")) {
        		System.out.println("Get SIZE");
        		List<String> itemlist = DBCheckComponent.getComponentNameList("Screen");
        		try {
        			outBean = new ObjectOutputStream(socket.getOutputStream());
        			outBean.writeObject(itemlist);
        			outBean.flush();
				} 
        		catch (IOException e) {
        			e.printStackTrace();
				}
    		}
    		
    		// COMBO STOCK
    		
    		else if (accept.equals("COMBOSTOCK")) {
        		System.out.println("Get STOCK");
        		List<String> itemlist = DBCheckComponent.getComponentNameList("Stock");
	        	try {
	        		outBean = new ObjectOutputStream(socket.getOutputStream());
	        		outBean.writeObject(itemlist);
	        		outBean.flush();
				} 
	        	catch (IOException e) {
	        		e.printStackTrace();
				}
    		}
    		
    		// COMBO MEMORY
    		
    		else if (accept.equals("COMBOMEMORY")) {
        		System.out.println("Get MEMORY");
        		List<String> itemlist = DBCheckComponent.getComponentNameList("Memory");
        		try {
        			outBean = new ObjectOutputStream(socket.getOutputStream());
        			outBean.writeObject(itemlist);
        			outBean.flush();
				} 
        		catch (IOException e) {
        			e.printStackTrace();
				}
    		}
    		
    		// COMBO GRAPHIC
    		
    		else if (accept.equals("COMBOGRAPHIC")) {
        		System.out.println("Get GRAPHIC");
        		List<String> itemlist = DBCheckComponent.getComponentNameList("Graphics");
        		try {
        			outBean = new ObjectOutputStream(socket.getOutputStream());
        			outBean.writeObject(itemlist);
        			outBean.flush();
				} 
        		catch (IOException e) {
        			e.printStackTrace();
				}
    		}
    		
    		// COMBO PROCESSOR
    		
    		else if (accept.equals("COMBOPROCESSOR")) {
        		System.out.println("Get PROCESSOR");
        		List<String> itemlist = DBCheckComponent.getComponentNameList("Processor");
	        	try {
	        		outBean = new ObjectOutputStream(socket.getOutputStream());
	        		outBean.writeObject(itemlist);
	        		outBean.flush();
				} 
	        	catch (IOException e) {
	        		e.printStackTrace();
				}
    		}
    		
    		// NEW PRICE
    		
    		else if (accept.equals("NEWPRICE")) {
        		System.out.println("set NEW PRICE");
	        	try {
	        		in = new DataInputStream(socket.getInputStream());    
	        		String itemname = in.readUTF();
	        		String precomponentname = in.readUTF();
	        		double price = in.readDouble();
	        		double newPrice = DBCheckComponent.getnewPrice(itemname,precomponentname,price);
	        		outBean = new ObjectOutputStream(socket.getOutputStream());
	        		outBean.writeObject(newPrice);
	        		outBean.flush();
				} 
	        	catch (IOException e) {
	        		e.printStackTrace();
	        	}
    		}
    		

    		try  
    		{  
    		    Thread.sleep(500);  
    		}  
    		catch(Exception e)  
    		{
        		 		  	
    		}  
       }      
    }

	private String getmessage(List<OrderInDetail> detiallist) {
		Iterator iterator = detiallist.iterator();
		String content = "";
		while(iterator.hasNext()) {
			OrderInDetail detial = (OrderInDetail) iterator.next();
			String idcomputer = flushLeft(' ',12 , detial.getcomputerID());
			String price = flushLeft(' ',8 , "$"+Double.toString(detial.getprice()));
			String color = flushLeft(' ',5 , detial.getcolor());
			String size = flushLeft(' ',5 , detial.getsize());
			String stock = flushLeft(' ',5 , detial.getstock());
			String memory = flushLeft(' ',8 , detial.getmemory());
			String graphics = flushLeft(' ',8 , detial.getgraphics());
			String processor = flushLeft(' ',10 , detial.getprocessor());
			String number = flushLeft(' ',8 , "*"+Integer.toString(detial.getnumber()));
			content = content+ idcomputer + price+number+ color+ size+ stock+memory+ graphics+processor+"\n";    			
		}
		return content;
	} 
	 	
	/*	
	 * 	c			要填充的字符    
	 *  length		填充后字符串的总长度    
	 *  content		要格式化的字符串    
	 */  
	
	public String flushLeft(char c, long length, String content) {    		  
		String str = "";     
		long cl = 0;    
		String cs = "";     
		if (content.length() > length) {     
			str = content;     
		}
		else {    
			for (int i = 0; i < length - content.length(); i++) {     
				cs += c;     
			}  
		}  
		str = content + cs;      
		return str;      
	}   
} 
 
  