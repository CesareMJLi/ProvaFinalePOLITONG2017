package com.OCS.Dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.RowProcessor;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.OCS.Dao.model.*;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class DBEditOrder implements DBConfig{
	private static int id_client;
	
	/*
	 * DBAddorder : 在数据库中加入Order信息
	 * 
	 * save					:	保存一个Order
	 * getOrderList			:	得到所有Order列表
	 * getActiveOrderList	:	得到指定Order列表
	 * ChangeOrderState		:	更新Order状态
	 * getClientActiveOrder	:	得到Client的有效的Order的信息
	 * getClientDeadOrder	:	得到Client的无效的Order的信息
	 * DeleteOrder			:	删除Order
	 * getDetaillist		:	得到Order细节
	 */
	
	public static boolean save(Order order, List<OrderInDetail> detiallist) {
		
		QueryRunner runner = new QueryRunner();  
		
		String sql = "insert into tb_order (id_order,price_order,date_order,state_order,id_client,payment,delivery) values (?, ?, ?, ?,?,?,?);";  
		// insert into tb_order (id_order,price_order,date_order,state_order,id_client,payment,delivery) values (?, ?, ?, ?,?,?,?);
		
		Connection conn = (new initConnection()).getConn();
		
		String sql2 = "select id_client from tb_client where name_client = '"+order.getClient()+"';";
		// select id_client from tb_client where name_client = order.getClient();
		
		String sql3 = "insert into tb_order_in_detail(orderID,computerID,number,price,color,size,stock,memory,graphics,processor)"
					+ "values(?,?,?,?,?,?,?,?,?,?);";
		// insert into tb_order_detial(orderID,computerID,number,price,color,size,stock,memory,graphics,processor) values(?,?,?,?,?,?,?,?,?,?);
		
		try {
			id_client = runner.query(conn, sql2, new ScalarHandler<Integer>());
		} 
		catch (SQLException e1) {
			e1.printStackTrace();
		} 
		
		Object[] params = { order.getID(), order.getPrice(), order.getDatetime(), order.getState(), id_client, order.getPayment(), order.getDelivery()};
		try {
			int result = runner.update(conn, sql, params); // 
			if (result > 0) {
				Iterator iterator = detiallist.iterator();
				while(iterator.hasNext()){
					OrderInDetail detial = (OrderInDetail) iterator.next();
					Object[] params3 = {detial.getorderID(), detial.getcomputerID(), detial.getnumber(), detial.getprice(),
										detial.getcolor(), detial.getsize(), detial.getstock(), detial.getmemory(),
										detial.getgraphics(), detial.getprocessor()};
					int result3 = runner.update(conn,sql3,params3);
				}
				return true;
			} 
			else {
				return false;
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		} 
		finally {
			DbUtils.closeQuietly(conn); 
		}
		
		return false; 
	}

	public static List<Order> getOrderList() {
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("id_order", "id");
		map.put("price_order", "price");
		map.put("date_order", "datetime");
		map.put("state_order", "state");
		map.put("id_client", "client");
		map.put("payment", "payment");
		map.put("delivery", "delivery");

		BeanProcessor bean = new BeanProcessor(map);
		RowProcessor processor = new BasicRowProcessor(bean);
		QueryRunner runner = new QueryRunner();  
		
		String sql = "select * from tb_order;";
		// select * from tb_order;
		
		Connection conn = (new initConnection()).getConn();
		ResultSetHandler<List<Order>> rsh = new BeanListHandler<Order>(Order.class,processor);  
		List<Order> result;
		try {
			result = (List<Order>)runner.query(conn, sql, rsh);
			return result;
		}
		catch (SQLException e) {
			e.printStackTrace();
		} 
		  
		return null;
	}
	
	public static List<Order> getOrderwithState(String state) {
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("id_order", "id");
		map.put("price_order", "price");
		map.put("date_order", "datetime");
		map.put("state_order", "state");
		map.put("id_client", "client");
		map.put("payment", "payment");
		map.put("delivery", "delivery");

		BeanProcessor bean = new BeanProcessor(map);
		RowProcessor processor = new BasicRowProcessor(bean);
		QueryRunner runner = new QueryRunner();  
		
		String sql0 = "select * from tb_order where state_order='"+"0"+"';";
		String sql1 = "select * from tb_order where state_order='"+"1"+"';";
		String sql2 = "select * from tb_order where state_order='"+"2"+"';";
		String sql3 = "select * from tb_order where state_order='"+"3"+"';";
		// select * from tb_order;
		
		Connection conn = (new initConnection()).getConn();
		ResultSetHandler<List<Order>> rsh = new BeanListHandler<Order>(Order.class,processor);  
		List<Order> result;
		try {
			switch(state){
			case "0":
				result = (List<Order>)runner.query(conn, sql0, rsh);
				return result;
			case "1":
				result = (List<Order>)runner.query(conn, sql1, rsh);
				return result;
			case "2":
				result = (List<Order>)runner.query(conn, sql2, rsh);
				return result;
			case "3":
				result = (List<Order>)runner.query(conn, sql3, rsh);
				return result;
			}
			//result = (List<Order>)runner.query(conn, sql, rsh);
			//return result;
		}
		catch (SQLException e) {
			e.printStackTrace();
		} 
		  
		return null;
	}
	
	
	public static List<Order> getActiveOrderList() {

		Map<String, String> map = new HashMap<String, String>();
		map.put("id_order", "id");
		map.put("price_order", "price");
		map.put("date_order", "datetime");
		map.put("state_order", "state");
		map.put("id_client", "client");
		map.put("payment", "payment");
		map.put("delivery", "delivery");
	
		BeanProcessor bean = new BeanProcessor(map);
		RowProcessor processor = new BasicRowProcessor(bean);
		QueryRunner runner = new QueryRunner();  
		
		String sql = "select * from tb_order where state_order='"+"0"+"';"; 
		// select * from tb_order where state_order='0';
		
		Connection conn = (new initConnection()).getConn();
		ResultSetHandler<List<Order>> rsh = new BeanListHandler<Order>(Order.class,processor);  
		List<Order> result;
		try {
			result = (List<Order>)runner.query(conn, sql, rsh);
			return result;
		} 
		catch (SQLException e) {
			e.printStackTrace();
		} 
			
		return null;
	}

	public static void ChangeOrderState(String id_order,String orderstate) {
		QueryRunner runner = new QueryRunner();  
		Connection conn = (new initConnection()).getConn();
		try {
			
			String sql = "update tb_order set state_order = '"+orderstate+"'where id_order = '"+ id_order+"';";  
			// update tb_order set state_order = orderstate where id_order = id_order;
			
	   		int q = runner.update(conn, sql);	
		}
		catch (SQLException e) {
			e.printStackTrace();
		} 
		finally {
			DbUtils.closeQuietly(conn);  
		}
	}

	public static List<Order> getClientActiveOrder(int id_client2) {
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("id_order", "id");
		map.put("price_order", "price");
		map.put("date_order", "datetime");
		map.put("state_order", "state");
		map.put("id_client", "client");
		map.put("payment", "payment");
		map.put("delivery", "delivery");
		
		BeanProcessor bean = new BeanProcessor(map);
		RowProcessor processor = new BasicRowProcessor(bean);
		QueryRunner runner = new QueryRunner();  
			
		String sql = "select * from tb_order where state_order<2 and id_client ='"+id_client2+"';";  
		// select * from tb_order where state_order<2 and id_client = id_client2;
		
		Connection conn = (new initConnection()).getConn();
		ResultSetHandler<List<Order>> rsh = new BeanListHandler<Order>(Order.class,processor);  
		List<Order> result;
		try {
			result = (List<Order>)runner.query(conn, sql, rsh);
			return result;
		} 
		catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return null;
	}
	
	public static List<Order> getClientDeadOrder(int id_client2) {
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("id_order", "id");
		map.put("price_order", "price");
		map.put("date_order", "datetime");
		map.put("state_order", "state");
		map.put("id_client", "client");
		map.put("payment", "payment");
		map.put("delivery", "delivery");
		
		BeanProcessor bean = new BeanProcessor(map);
		RowProcessor processor = new BasicRowProcessor(bean);
		QueryRunner runner = new QueryRunner();  
		
		String sql = "select * from tb_order where state_order>1 and id_client ='"+id_client2+"';";  
		// select * from tb_order where state_order>1 and id_client = id_client2;
		
		Connection conn = (new initConnection()).getConn();
		ResultSetHandler<List<Order>> rsh = new BeanListHandler<Order>(Order.class,processor);  
		List<Order> result;
		try {
			result = (List<Order>)runner.query(conn, sql, rsh);
			return result;
		} 
		catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return null;
	}
	
	public static void DeleteOrder(String id_order) {
		QueryRunner runner = new QueryRunner();  
		Connection conn = (new initConnection()).getConn();
		try {				
			
	   			String sql = "delete from tb_order where id_order = '"+id_order+"';";  
	   			// delete from tb_order where id_order = id_order;
	   			
	   			int q = runner.update(conn, sql);	
		} 
		catch (SQLException e) {
				e.printStackTrace();
		} 
		finally {
				DbUtils.closeQuietly(conn);  
		}
	}
	
	public static List<OrderInDetail> getDetiallist(String id_order) {
		QueryRunner runner = new QueryRunner();  
		
		String sql = "select * from tb_order_in_detail where orderID = '"+id_order+"';";  
		// select * from tb_order_detial where orderID = id_order;
		
		Connection conn = (new initConnection()).getConn();
		ResultSetHandler<List<OrderInDetail>> rsh = new BeanListHandler<OrderInDetail>(OrderInDetail.class);  
		List<OrderInDetail> result;
		try {
			result = (List<OrderInDetail>)runner.query(conn, sql, rsh);
			return result;
		} 
		catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return null;
	}

}
