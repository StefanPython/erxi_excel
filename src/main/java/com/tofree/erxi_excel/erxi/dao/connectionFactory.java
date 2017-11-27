package com.tofree.erxi_excel.erxi.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connectionFactory {
    private static Logger log = LoggerFactory.getLogger(connectionFactory.class);
	private static String driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static String url="jdbc:sqlserver://localhost:1433;DatabaseName=erxi_app";
	private static String userName="erxi_api";
	private static String pwd="^ba9)32!Kq";

//	private static String driver="org.gjt.mm.mysql.Driver";
//	private static String url="jdbc:mysql://127.0.0.1/test";
//	private static String userName="root";
//	private static String pwd="";


	private connectionFactory(){}
	public static Connection getConnection() {
		Connection conn=null;
		try {
			Class.forName(driver);
			conn=DriverManager.getConnection(url,userName,pwd);
			log.info("得到连接:"+conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
		
	}

	public static void closeConenction(){
		Connection conn=getConnection();
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
