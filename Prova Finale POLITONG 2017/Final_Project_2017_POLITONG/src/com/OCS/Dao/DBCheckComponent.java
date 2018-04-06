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
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.OCS.Dao.model.*;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class DBCheckComponent implements DBConfig{
	
	/*
	 * DBCheckComponent �����ݿ��м��Component��Ϣ
	 * 
	 * getConnection		:	�������ݿ�
	 * select				:	��ѯȫ��Component��������Ϣ
	 * getComponentID		:	��ѯָ��Component��ID
	 * getComponentType		:	��ѯָ��Component��Type
	 * getComponentPrice	:	��ѯָ��Component��Price
	 * getComponentNameList	:	��ѯָ��Type������Component����
	 * getnewPrice			:	�����µ�Price
	 * selectType			:	��ѯָ��Type����Ϣ
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
	
	
    public static List<Component> select() {

    	Map<String, String> map = new HashMap<String, String>();
    	map.put("id_component", "id");
    	map.put("name_component", "name");
    	map.put("type_component", "type");
    	map.put("price_component", "price");

    	BeanProcessor bean = new BeanProcessor(map);
    	RowProcessor processor = new BasicRowProcessor(bean);
    	
    	QueryRunner runner = new QueryRunner(); 
    	
        String sql = "select * from tb_component;"; 
        // select * from tb_component;
        
        //Connection conn = getConnection(); 
        Connection conn = (new initConnection()).getConn();
        
        ResultSetHandler<List<Component>> rsh = new BeanListHandler<Component>(Component.class,processor); 
        try {
            List<Component> result = (List<Component>)runner.query(conn, sql, rsh); 
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

	public static String getComponentID(String componentName) {
		QueryRunner runner = new QueryRunner(); 
		
		String sql = "select id_component from tb_component where name_component = '"+ componentName+"';"; 
		//select id_component from tb_component where name_component = componentName;
		
		//Connection conn = getConnection(); 
		 Connection conn = (new initConnection()).getConn();
		
		String componentID = null;
		try {
			componentID = runner.query(conn, sql, new ScalarHandler<String>());
			return componentID;
		} 
		catch (SQLException e1) { 
			e1.printStackTrace();
			return null;
		}
	}
	
	public static String getComponentType(String componentName) {
		QueryRunner runner = new QueryRunner(); 
		
		String sql = "select type_component from tb_component where name_component = '"+ componentName+"';"; 
		// select type_component from tb_component where name_component = componentName;
		
		//Connection conn = getConnection(); 
		 Connection conn = (new initConnection()).getConn();
		
		String componentType = null;
		try {
			componentType = runner.query(conn, sql, new ScalarHandler<String>());
			return componentType;
		} 
		catch (SQLException e1) {	 
			e1.printStackTrace();
			return null;
		}
	}
	
	public static double getComponentPrice(String componentName) {
		QueryRunner runner = new QueryRunner(); 
		
		String sql = "select price_component from tb_component where name_component = '"+ componentName+"';"; 
		// select type_component from tb_component where name_component = componentName;
		
		//Connection conn = getConnection(); 
		 Connection conn = (new initConnection()).getConn();
		
		double componentPrice = 0;
		try {
			componentPrice = runner.query(conn, sql, new ScalarHandler<Double>());
			return componentPrice;
		} 
		catch (SQLException e1) {			 
			e1.printStackTrace();
			return 0;
		}
	}
	
	public static List<String> getComponentNameList(String type){
		QueryRunner runner = new QueryRunner(); 
		
		String sql = "select name_component from tb_component where type_component = '"+type+"';"; 
		// select name_component from tb_component where type_component = type;
		
		//Connection conn = getConnection(); 
		 Connection conn = (new initConnection()).getConn();
		
		ResultSetHandler<List<String>> rsh = new ColumnListHandler<String>(); 	
		try {
			List<String> result = (List<String>)runner.query(conn, sql, rsh); 
			return result;
		} 
		catch (SQLException e1) {	 
			e1.printStackTrace();
			return null;
		}
	}
	
	public static double getnewPrice(String itemname,String precomponentname,double price){
		String type_component = DBCheckComponent.getComponentType(itemname);
		double price_component = DBCheckComponent.getComponentPrice(itemname);
		double pre_price = DBCheckComponent.getComponentPrice(precomponentname);
		double newPrice = price + (price_component - pre_price);
		
		return newPrice;
	}
	
	public static List<Component> selectType(String type) {
    	Map<String, String> map = new HashMap<String, String>();
    	map.put("id_component", "id");
    	map.put("name_component", "name");
    	map.put("type_component", "type");
    	map.put("price_component", "price");
    	BeanProcessor bean = new BeanProcessor(map);
    	RowProcessor processor = new BasicRowProcessor(bean);
    	
        QueryRunner runner = new QueryRunner();
        
        String sql = "select * from tb_component where type_component = '"+type+"';"; 
        // select * from tb_component where type_component = type;
        
        Connection conn = (new initConnection()).getConn();
        ResultSetHandler<List<Component>> rsh = new BeanListHandler<Component>(Component.class,processor);
        try {
            List<Component> result = (List<Component>)runner.query(conn, sql, rsh);
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
