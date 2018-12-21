package com.howtodoinjava.jersey;

import java.sql.SQLException;
import java.util.Properties;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSetInternalMethods;
import com.mysql.jdbc.Statement;

public class sqlInterceptor implements com.mysql.jdbc.StatementInterceptorV2{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean executeTopLevelOnly() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void init(Connection arg0, Properties arg1) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ResultSetInternalMethods postProcess(String arg0, Statement arg1, ResultSetInternalMethods arg2,
			Connection arg3, int arg4, boolean arg5, boolean arg6, SQLException arg7) throws SQLException {
		if(arg0!= null && (arg0.contains("select") || arg0.contains("insert") || arg0.contains("update") || arg0.contains("delete"))) {
		System.out.println("PostProcess arg0: "+arg0);
		}
		return null;
	}

	@Override
	public ResultSetInternalMethods preProcess(String arg0, Statement arg1, Connection arg2) throws SQLException {
		if(arg0!=null &&(arg0.contains("select") || arg0.contains("insert") || arg0.contains("update") || arg0.contains("delete"))) {
		System.out.println("PreProcess arg0: "+arg0);
		}
		return null;
	}

}
