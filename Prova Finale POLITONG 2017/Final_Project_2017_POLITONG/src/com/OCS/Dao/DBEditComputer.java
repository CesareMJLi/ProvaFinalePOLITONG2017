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

public class DBEditComputer implements DBConfig{
	
	/*
	 * DBAddComputer : 在数据库中加入Computer信息
	 * 
	 * exists				:	检查数据库中是否有指定的Computer信息
	 * save					:	将一个Computer信息存入数据库
	 * savecomputerconfig	:	将一个Component信息加到一个Computer信息内
	 * delete				:	将一个Computer信息从数据库中删除
	 */
	
    public static boolean exists(String computerID) {
		QueryRunner runner = new QueryRunner();
		
		String sql = "select id_computer from tb_computer where id_computer = '" + computerID + "';";
		// select id_computer from tb_computer where id_computer = computerID;
		
		Connection conn = (new initConnection()).getConn();
		ResultSetHandler<List<Object>> rsh = new ColumnListHandler();
		try {
			List<Object> result = runner.query(conn, sql, rsh);
			return (result.size() > 0);
		} 
		catch (SQLException e) {
			System.out.println("Error! Fail to execute DBAddcomputer.exists");
		} 
		finally {
			DbUtils.closeQuietly(conn); 
		}
		
		return false; 
    }
   
    public static boolean save(Computer computer) {
		QueryRunner runner = new QueryRunner();
		
		String sql = "insert into tb_computer (id_computer, name_computer, type_computer,price_computer,picture_computer) values (?, ?, ?, ?,?);";
		// insert into tb_computer (id_computer, name_computer, type_computer,price_computer,picture_computer) values (?, ?, ?, ?,?);
		
		Connection conn = (new initConnection()).getConn();
		Object[] params = { computer.getId(), computer.getName(), computer.getType(),computer.getPrice(),computer.getPicture()};
		try {
			int result = runner.update(conn, sql, params);
			return (result > 0);
		} 
		catch (SQLException e) {
			System.out.println("Error! Fail to execute DBAddcomputer.save");
		} 
		finally {
			DbUtils.closeQuietly(conn);
		}
		
		return false;
    }
    
    public static boolean savecomputerconfig(String computerID, String componentID) {
		QueryRunner runner = new QueryRunner(); 
		
		String sql = "insert into tb_configuration (id_computer, id_component) values (?, ?);";
		// insert into tb_computer_has_component (id_computer, id_component) values (?, ?);
		
		Connection conn = (new initConnection()).getConn();
		Object[] params = { computerID,componentID};
		try {
			int result = runner.update(conn, sql, params);
			return (result > 0);
		} 
		catch (SQLException e) {
			System.out.println("Error! Fail to execute DBAddcomputer.savecomputerconfig");
		} 
		finally {
			DbUtils.closeQuietly(conn); 
		}
		
		return false; 
    }
    
    public static boolean delete(String computerID) {
		QueryRunner runner = new QueryRunner();
		
		String sql = "delete from tb_computer where id_computer = '" + computerID + "';";
		// delete from tb_computer where id_computer = computerID;
		
		Connection conn = (new initConnection()).getConn();		
		try {
			runner.update(conn, sql);
			return true;
		} 
		catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error! Fail to execute DBAddcomputer.delete");
		} 
		finally {
			DbUtils.closeQuietly(conn);
		}
		return false; 
    }

}
