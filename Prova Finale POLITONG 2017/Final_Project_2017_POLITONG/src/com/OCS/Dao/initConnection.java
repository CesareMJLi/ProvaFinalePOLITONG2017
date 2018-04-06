package com.OCS.Dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class initConnection implements DBConfig{
	
	private MysqlDataSource mds;
	
	public initConnection(){
		mds = new MysqlDataSource();
        mds.setDatabaseName(databaseName);
        mds.setUser(username);
        mds.setPassword(password);
	}
	
	public Connection getConn() {	
		new initConnection();
		
        try {
            return mds.getConnection();
            //get the connection of the database
        } catch (SQLException e) {
        	System.out.println("Fail to get the connection of the database");
        }
        return null;// 如果获取失败就返回null
    }
	
}