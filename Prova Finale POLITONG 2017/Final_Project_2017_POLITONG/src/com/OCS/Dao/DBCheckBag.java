package com.OCS.Dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.OCS.Dao.model.*;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class DBCheckBag implements DBConfig{
	
	/*
	 * DBCheckBag 在数据库中操作Bag信息
	 * 	
	 * getClientBag					:	得到无条件的client bag信息
	 * getClientBagwithcondition	:	得到有条件的client bag信息
	 * addtoBag						:	将bag信息加入数据库
	 * DeleteBag					:	将bag信息从数据库中删除
	 */

	public static List<Bag> getClientBag(int id_client) {
		QueryRunner runner = new QueryRunner(); 
		
        String sql = "select * from tb_shopping_bag where clientID = '"+id_client+"';"; 
        // select * from bag where clientID = id_client;
        
        Connection conn = (new initConnection()).getConn();
        ResultSetHandler<List<Bag>> rsh = new BeanListHandler<Bag>(Bag.class); 
        List<Bag> result;
		try {
			result = (List<Bag>)runner.query(conn, sql, rsh);
			return result;
		} 
		catch (SQLException e) {
			e.printStackTrace();
		} 
        
		return null;
	}

	public static List<Bag> getClientBagwithcondition(int id_client, String condition) {
		QueryRunner runner = new QueryRunner(); 
		
        String sql = "select * from tb_shopping_bag where clientID = '"+id_client+"';"; 
        // select * from bag where clientID = id_client;
        
        Connection conn = (new initConnection()).getConn();
        ResultSetHandler<List<Bag>> rsh = new BeanListHandler<Bag>(Bag.class); 
        List<Bag> result;
		try {
			result = (List<Bag>)runner.query(conn, sql, rsh);
			return result;
		} 
		catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return null;
	}
	
	public static boolean addtoBag(int id_client, List<Bag> baglist) {
		QueryRunner runner = new QueryRunner(); 
		Connection conn = (new initConnection()).getConn();
        Iterator iterator = baglist.iterator();
         
        while(iterator.hasNext()){
        	int number = 1;
        	int ID = 0;
        	Bag bag = (Bag) iterator.next();
        	String computerID = bag.getcomputerID();
        	double price = bag.getprice();
        	String color = bag.getcolor();
        	String size = bag.getsize();
        	String stock = bag.getstock();
        	String memory = bag.getmemory();
        	String graphics = bag.getgraphics();
        	String processor = bag.getprocessor(); 
        	
	        String sql0 = "select ID from tb_shopping_bag where clientID ='"+id_client+"' and computerID ='"+computerID+"'"
	        			+ " and price = '" + price + "'" + " and color ='"+ color +"' and size ='"+size+ "' and stock ='"+stock
	        			+ "' and " + "memory ='"+memory+"' and graphics ='"+graphics+"' and processor ='"+ processor+"';"; 
	        
	        String sql1 = "insert into tb_shopping_bag(clientID,computerID,number,price,color,size,stock,memory,graphics,processor)"
	        			+ "values(?,?,?,?,?,?,?,?,?,?);";
	       
	        // select ID from bag where 
	        //  	clientID = id_client and computerID = computerID and price = price and color = color and size = size and 
	        //  	stock = stock and memory = memory and graphics = graphics and processor = processor;

	        // insert into tb_bag(clientID,computerID,number,price,color,size,stock,memory,graphics,processor) values(?,?,?,?,?,?,?,?,?,?);
	        
	        ResultSetHandler<List<Object>> rsh = new ColumnListHandler(); 
	        Object[] params = { id_client,computerID,number,price,color,size,stock,memory,graphics,processor};
	        try {
				List<Object> result = runner.query(conn, sql0, rsh);
				if (result.size() > 0) {
					ID = runner.query(conn,sql0,new ScalarHandler<Integer>());
					System.out.println("bag ID is:"+ID);
					number = runner.query(conn,"select number from tb_shopping_bag where ID = '"+ID+"';" , new ScalarHandler<Integer>()); 
					System.out.println("number of computer is: "+number);
					number++;
					int q = runner.update(conn, "update tb_shopping_bag set number ='"+number+"' where ID = '"+ID+"';");
	            } 
				else {
	            	int q = runner.update(conn, sql1, params);
	            }
			} 
	        catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
        }      
        
		return true;		
	}

	public static void DeleteBag(int id_bag) {
		QueryRunner runner = new QueryRunner(); 
		Connection conn = (new initConnection()).getConn();
	    try {	    	 	
	    	
	    	String sql = "delete from tb_shopping_bag where ID = '"+id_bag+"';"; 
	    	// delete from bag where ID = id_bag;
	    	
	   	    int q = runner.update(conn, sql);	            
	   	} 
	    catch (SQLException e) {
	        e.printStackTrace();
	    } 
	    finally {
	        DbUtils.closeQuietly(conn); 
	    }
	}

}
