package com.OCS.Dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang.StringEscapeUtils;

import com.OCS.Dao.model.*;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class DBClientLogin implements DBConfig{
    
	/*
	 * DBClientLogin 用户登录数据库的操作
	 * 
     * exists			: 检查Username是否存在
     * check			: 检查Username和Password是否匹配
     * checkMailbox		: 检查Username和Email是否匹配
     * save				: 将User信息在数据库中注册
     * getEmail			: 得到一个Username的邮件信息
     * getClientID		: 得到一个Username的ID信息
     * changePasswd		: 更新一个Username对应的Password
     * changeMailbox	: 更新一个Username对应的Email
     */
	
    public static boolean exists(String username) {
        QueryRunner runner = new QueryRunner();
        
        String sql = "select id_client from tb_client where name_client = '" + username + "';";
        // select id_client from client where name_client = username;
        
        /*
         * tb_client:
         * id_client / name_client / psd_client / email_client
         */
        
        Connection conn = (new initConnection()).getConn();
        ResultSetHandler<List<Object>> rsh = new ColumnListHandler();
        try {
            List<Object> result = runner.query(conn,sql,rsh);
            if (result.size()>0) {
                return true;
            } else {
                return false;
            }
        } 
        catch (SQLException e) {
            System.out.println("Error! Fail to execute DBClientLogin.exists");
        } 
        finally {
            DbUtils.closeQuietly(conn);
        }
        
        return false;
    }
    
    public static boolean check(String username, String password) {
        username = StringEscapeUtils.escapeSql(username);
        QueryRunner runner = new QueryRunner();
        
        String sql = "select psd_client from tb_client where name_client = '" + username + "';";
        // select psd_client from client where name_client = username;
        
        Connection conn = (new initConnection()).getConn();
        ResultSetHandler<Object> rsh = new ScalarHandler();
        try {
            String result = (String) runner.query(conn, sql, rsh);
            if (result.equals(password)) {           
                return true;
            } 
            else {
                return false;
            }
        } 
        catch (SQLException e) {
            System.out.println("Error! Fail to execute DBClientLogin.check");
        } 
        finally {
            DbUtils.closeQuietly(conn);
        }
        
        return false;
    }
    
    public static boolean checkMailbox(String username, String mailbox) {
    	username = StringEscapeUtils.escapeSql(username);
        QueryRunner runner = new QueryRunner();
        
        String sql = "select email_client from tb_client where name_client = '" + username + "';";
        // select email_client from client where name_client = username;
        
        Connection conn = (new initConnection()).getConn();
        ResultSetHandler<Object> rsh = new ScalarHandler();
        try {
            String result = (String) runner.query(conn, sql, rsh);
            if (result.equals(mailbox)) {
                return true;
            } 
            else {
                return false;
            }
        } 
        catch (SQLException e) {
            System.out.println("Error! Fail to execute DBClientLogin.checkMailbox");
        } 
        finally {
            DbUtils.closeQuietly(conn);
        }
        
        return false;
    }
    
    public static boolean save(Client user) {
        QueryRunner runner = new QueryRunner();
        
        String sql = "insert into tb_client (name_client, psd_client, email_client) values (?, ?, ?);";
        // insert into client (name_client, psd_client, email_client) values (?, ?, ?);
        
        Connection conn = (new initConnection()).getConn();
        Object[] params = { user.getUsername(), user.getPassword(), user.getEmail() };
        try {
            int result = runner.update(conn, sql, params);
            if (result > 0) {
                return true;
            } 
            else {
                return false;
            }
        } 
        catch (SQLException e) {
            System.out.println("Error!Fail to execute DBClientLogin.save");
        } 
        finally {
            DbUtils.closeQuietly(conn);
        }
        
        return false;
    }
    
    public static String getEmail(String username){
    	QueryRunner runner = new QueryRunner();  
    	Connection conn = (new initConnection()).getConn();
    	
        String sql = "select email_client from tb_client where name_client = '"+username+"';";
        // select email_client from client where name_client = username;
        
        try {
			 String email = runner.query(conn, sql, new ScalarHandler<String>());
			 return email;
		} 
        catch (SQLException e) {
            System.out.println("Error!Fail to execute DBClientLogin.getEmail");
        } 
        finally {
            DbUtils.closeQuietly(conn);
        }
        
        return null;
    }
    
	public static int getClientID(String name_client) {
		QueryRunner runner = new QueryRunner();        
		Connection conn = (new initConnection()).getConn();
		
        String sql = "select id_client from tb_client where name_client = '"+name_client+"';";
        // select id_client from client where name_client = name_client;
        
        try {
			 int id_client = (int) runner.query(conn, sql, new ScalarHandler<Integer>());
			 return id_client;
		}
        catch (SQLException e) {
            System.out.println("Error!Fail to execute DBClientLogin.getClientID");
        } 
        finally {
            DbUtils.closeQuietly(conn);
        }
        
        return 0;
	}
	
	public static boolean changePasswd(String client, String newpasswd) {
		QueryRunner runner = new QueryRunner();    
		Connection conn = (new initConnection()).getConn();
		
        String sql = "update tb_client set psd_client = '"+newpasswd+"'where name_client = '"+ client+"';";
        // update client set psd_client = newpasswd where name_client = client;
        
        try {
			int q = runner.update(conn, sql);
			return true;
		} 
        catch (SQLException e) {
            System.out.println("Error!Fail to execute DBClientLogin.changePasswd");
        } finally {
            DbUtils.closeQuietly(conn);
        }
        
        return false;
	}
	
	public static boolean changeMailbox(String client, String newmailbox) {
		QueryRunner runner = new QueryRunner();  
		Connection conn = (new initConnection()).getConn();
		
        String sql = "update tb_client set email_client = '"+newmailbox+"'where name_client = '"+ client+"';";
        // update client set email_client = newmailbox where name_client = client;
        
        try {
			int q = runner.update(conn, sql);
			return true;
		} 
        catch (SQLException e) {
            System.out.println("Error!Fail to execute DBClientLogin.changeMailbox");
        } 
        finally {
            DbUtils.closeQuietly(conn);
        }
        
        return false;
	}

}
