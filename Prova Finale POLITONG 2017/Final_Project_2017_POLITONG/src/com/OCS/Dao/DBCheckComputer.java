package com.OCS.Dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.RowProcessor;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.OCS.Dao.model.*;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class DBCheckComputer implements DBConfig{

	/*
	 * DBCheckComputer 在数据库中检查Computer信息
	 * 
	 * getConnection		:	连接数据库
	 * select				:	查询全部商品的信息
	 * selectwithcondition	:	查询符合Condition的商品信息
	 */
	
	/*
    public static Connection getConnection() {
        MysqlDataSource mds = new MysqlDataSource();
        mds.setDatabaseName(databaseName);
        mds.setUser(username);
        mds.setPassword(password);
        try {
            return mds.getConnection();
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
	*/
	
	
    public static List<Computer> select() {

    	Map<String, String> map = new HashMap<String, String>();
    	map.put("id_computer", "id");
    	map.put("name_computer", "name");
    	map.put("type_computer", "type");
    	map.put("price_computer", "price");
    	map.put("picture_computer", "picture");

    	BeanProcessor bean = new BeanProcessor(map);
    	RowProcessor processor = new BasicRowProcessor(bean);
  	 
    	Map<String, String> map2 = new HashMap<String, String>();
    	map2.put("id_component", "id");
    	map2.put("name_component", "name");
    	map2.put("type_component", "type");
    	map2.put("price_component", "price");

    	BeanProcessor bean2 = new BeanProcessor(map2);
    	RowProcessor processor2 = new BasicRowProcessor(bean2);
    	
        QueryRunner runner = new QueryRunner(); 
        
        String sql = "select * from tb_computer;"; 
        // select * from tb_computer;
        
        //Connection conn = getConnection(); 
        Connection conn = (new initConnection()).getConn();
        
        ResultSetHandler<List<Computer>> rsh = new BeanListHandler<Computer>(Computer.class,processor); 
        
        try {
        	List<Computer> result = (List<Computer>)runner.query(conn, sql, rsh); 
            Iterator iterator = result.iterator();
            while (iterator.hasNext()) {
    			Computer computer = (Computer) iterator.next();
    			
    			String sql2 = "select id_component from tb_configuration " + "where id_computer = '"+computer.getId()+"';";
    			// select id_component from tb_computer_has_component where id_computer = computer.getId();
    			
    			ResultSetHandler<List<String>> rsh2 = new ColumnListHandler();;
    			List<String>result2 = (List<String>)runner.query(conn,sql2,rsh2);
    			Iterator iterator2 = result2.iterator();
    			while (iterator2.hasNext()){
    				String idcomponent = (String) iterator2.next();
    				
    				String sql3 ="select * from tb_component" + " where id_component ='"+idcomponent+"';";
    				// select * from tb_component where id_component = idcomponent;
    				
    				ResultSetHandler<Component> rsh3 = new BeanHandler<Component>(Component.class,processor2);   				
    				Component result3 = (Component) runner.query(conn, sql3, rsh3);
    				if(result3.getType().equals("Stock")){
    					computer.setStock(result3.getName());
    				}
    				if(result3.getType().equals("Memory")){
    					computer.setMemory(result3.getName());
    				}
    				if(result3.getType().equals("Color")){
    					computer.setColor(result3.getName());
    				}
    				if(result3.getType().equals("Screen")){
    					computer.setSize(result3.getName());
    				}
    				if(result3.getType().equals("Graphics")){
    					computer.setGraphic(result3.getName());
    				}
    				if(result3.getType().equals("Processor")){
    					computer.setProcessor(result3.getName());
    				}
    			}
            }
          
            return result;
        } 
        catch (SQLException e) {
            e.printStackTrace();
        } 
        finally {
            DbUtils.closeQuietly(conn);
        }
        
        return null;
    }
    
    public static List<Computer> selectwithcondition(String condition) {

    	Map<String, String> map = new HashMap<String, String>();
    	map.put("id_computer", "id");
    	map.put("name_computer", "name");
    	map.put("type_computer", "type");
    	map.put("price_computer", "price");
    	map.put("picture_computer", "picture");

    	BeanProcessor bean = new BeanProcessor(map);
    	RowProcessor processor = new BasicRowProcessor(bean);
    	
    	Map<String, String> map2 = new HashMap<String, String>();
    	map2.put("id_component", "id");
    	map2.put("name_component", "name");
    	map2.put("type_component", "type");
    	map2.put("price_component", "price");

    	BeanProcessor bean2 = new BeanProcessor(map2);
    	RowProcessor processor2 = new BasicRowProcessor(bean2);
    	 	
        QueryRunner runner = new QueryRunner(); 
        
        String sql = "select * from tb_computer where type_computer ='"+condition+"';"; 
        // select * from tb_computer where type_computer = condition ;
        
        //Connection conn = getConnection(); 
        Connection conn = (new initConnection()).getConn();
        
        ResultSetHandler<List<Computer>> rsh = new BeanListHandler<Computer>(Computer.class,processor); 
        try {
            List<Computer> result = (List<Computer>)runner.query(conn, sql, rsh); 
            Iterator iterator = result.iterator();
            while (iterator.hasNext()) {
    			Computer computer = (Computer) iterator.next();
    			
    			String sql2 = "select id_component from tb_configuration " + "where id_computer = '"+computer.getId()+"';";
    			// select id_component from tb_computer_has_component where id_computer = computer.getId();
    			
    			ResultSetHandler<List<String>> rsh2 = new ColumnListHandler();;
    			List<String>result2 = (List<String>)runner.query(conn,sql2,rsh2);
    			Iterator iterator2 = result2.iterator();
    			while (iterator2.hasNext()){
    				String idcomponent = (String) iterator2.next();
    				
    				String sql3 ="select * from tb_component" + " where id_component ='"+idcomponent+"';";
    				// select * from tb_component where id_component = idcomponent;
    				
    				ResultSetHandler<Component> rsh3 = new BeanHandler<Component>(Component.class,processor2);   				
    				Component result3 = (Component) runner.query(conn, sql3, rsh3);
    				if(result3.getType().equals("Stock")){
    					computer.setStock(result3.getName());
    				}
    				if(result3.getType().equals("Memory")){
    					computer.setMemory(result3.getName());
    				}
    				if(result3.getType().equals("Color")){
    					computer.setColor(result3.getName());
    				}
    				if(result3.getType().equals("Screen")){
    					computer.setSize(result3.getName());
    				}
    				if(result3.getType().equals("Graphics")){
    					computer.setGraphic(result3.getName());
    				}
    				if(result3.getType().equals("Processor")){
    					computer.setProcessor(result3.getName());
    				}
    			}
            }
            return result;
        } 
        catch (SQLException e) {
            e.printStackTrace();
        } 
        finally {
            DbUtils.closeQuietly(conn);
        }
        
        return null;
    }
    
 
}
