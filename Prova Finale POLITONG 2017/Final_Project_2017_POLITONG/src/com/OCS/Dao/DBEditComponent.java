package com.OCS.Dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;

import com.OCS.Dao.model.*;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class DBEditComponent implements DBConfig{
    
	/*
	 * DBAddComponent 在数据库中加入Component信息
	 * 
     * exists	:	检查数据库中是否有指定的Component信息
     * save		:	将一个Component信息存入数据库
     * delete	:	将一个Component信息从数据库中删除
     */
	
    public static boolean exists(String componentID) {
		QueryRunner runner = new QueryRunner();
		
		String sql = "select id_component from tb_component where id_component = '" + componentID + "';";
		// select id_component from tb_component where id_component = componentID;
		
		Connection conn = (new initConnection()).getConn();
		ResultSetHandler<List<Object>> rsh = new ColumnListHandler();
		try {
			List<Object> result = runner.query(conn, sql, rsh);
			return (result.size() > 0);
		} 
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Fail to Execute DBAddcomponent.exist");
		} 
		finally {
			DbUtils.closeQuietly(conn);
		}
		
		return false;
    }
    
    public static boolean save(Component component) {
		QueryRunner runner = new QueryRunner();
		
		String sql = "insert into tb_component (id_component, name_component, type_component,price_component) values (?, ?, ?, ?);";
		// insert into tb_component (id_component, name_component, type_component,price_component) values (?, ?, ?, ?);
		
		Connection conn = (new initConnection()).getConn();
		
		Object[] params = { component.getId(), component.getName(), component.getType(),component.getPrice()};
		try {
			int result = runner.update(conn, sql, params);
			return (result > 0);
		} 
		catch (SQLException e) {
			System.out.println("Fail to execute the DBAddcomponent.save");
		} 
		finally {
			DbUtils.closeQuietly(conn);
		}
		
		return false;
    }
    
    public static boolean delete(String componentID) {
		QueryRunner runner = new QueryRunner();
		
		String sql = "delete from tb_component where id_component = '" + componentID + "';";
		// delete from tb_component where id_component = componentID;
		
		Connection conn = (new initConnection()).getConn();
		try {
			runner.update(conn, sql);
			return true;
		} 
		catch (SQLException e) {
			System.out.println("Failed to execute DBAddcomponent.delete");
		} 
		finally {
			DbUtils.closeQuietly(conn);
		}
		
		return false;
    }
}
