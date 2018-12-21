package com.howtodoinjava.jersey;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class DBconnection {
	Connection con;

	public DBconnection() {
		if (con == null) {
			try {
				MysqlDataSource ds = new MysqlDataSource();
				ds.setUser("root");
				ds.setDatabaseName("d&d_flavio");
				ds.setPort(3306);
				con = ((DataSource) ds).getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public Connection getCon() {
		return con;
	}
	public void closeConnection(Connection con)throws Exception {
		con.close();
	}
	
		
}
