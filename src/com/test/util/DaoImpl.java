package com.test.util;

/**
 * @author jaedeuk.ahn
 * @since 2014.01.21(THU)
 */

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DaoImpl {
	
	
	
	public static int selectQueryInt( String sSql)throws Exception {
		
		Connection conn =null;
		DbUtils db = new DbUtils();
		
		Statement stmt = null;     
		ResultSet rs = null;
		int iReturn = 0;
		// 인자로 넘어온 것들을 확인
		if (sSql == null || sSql.equals("")) {
			String m_sError = null;
			if (sSql == null)
				m_sError = "sSql에 null이 넘어왔습니다.";
			else if (sSql.equals(""))
				m_sError = "sSql 문에 아무것도 명령어가 없습니다.";
			throw new SQLException(m_sError);
		}

		try {			
			conn = db.getConnection();
			
			stmt = db.conn.createStatement();
			rs = stmt.executeQuery(sSql);
		
			int nColumnCount = rs.getMetaData().getColumnCount();
			if (nColumnCount != 0) {
				while (rs.next()) {
					iReturn= rs.getInt(1);
				}
			}
		} catch (SQLException ex) {
			String err = "";
			err = ex +"  "+ sSql;
			
			if(rs != null) rs.close();
	        if(stmt != null) stmt.close();
	        if(conn != null) conn.close();
	        db.closeConnection();       
			
			throw new Exception(err);
		} finally {
			try {
				if(rs != null) rs.close();
		        if(stmt != null) stmt.close();
		        if(conn != null) conn.close();
		        db.closeConnection();       
			} catch (Exception ex) {}
		}
		return iReturn;
	}	
	
	public static ArrayList< String[]> selectQueryList( String sSql) throws SQLException {
		
		Connection conn =null;
		DbUtils db = new DbUtils();
				
		Statement stmt = null;     
	    ResultSet rs = null;
	       
	    ArrayList<String[]> resultList = new ArrayList<String[]>();
	    
	    conn = db.getConnection();
	    				
        if (sSql == null || sSql.equals("")) {
	        String m_sError = null;
	        if (sSql == null)
	        	m_sError = "selectQuery Sql is null.";
	        else if (sSql.equals(""))
	        	m_sError = "selectQuery Sql is empty.";
	        throw new SQLException(m_sError);
        }

        try{
        	if( conn != null ){
	        	Utils.makeLog(sSql);	        	        	
	        	stmt = conn.createStatement();
		        rs = stmt.executeQuery(sSql);
		        
		        int nColumnCount = rs.getMetaData().getColumnCount();
		        if (nColumnCount != 0) {			        
			        while (rs.next()) {
				        String[] arrRow = new String[nColumnCount];			       
				        for (int nIndex = 0; nIndex < nColumnCount; nIndex++) {
				        	arrRow[nIndex] = rs.getString(nIndex + 1);				        	
				        }
				        resultList.add(arrRow);
			        }
		        }
		        
        	}else{
        		
	        	Utils.makeLog("[Connection is NULL]");
	        	Utils.makeLog("ERROR", "[Connection is NULL] Query -> "+ sSql);
	        
        	}
        } catch (SQLException ex) {        	
        	Utils.makeLog("ERROR", "[selectQuery SQLException] "+ex );
        	Utils.makeLog( "ERROR", "[selectQuery SQLException] "+sSql +" \n ");        	
        } catch(Exception e){
        	Utils.makeLog("ERROR", "[selectQuery Exception] "+e );
        	Utils.makeLog("ERROR", "[selectQuery Exception] "+sSql +" \n ");
        } finally {
	        try {
	        	if(rs != null) rs.close();
		        if(stmt != null) stmt.close();
		        if(conn != null) conn.close();
		        db.closeConnection();       
	        } catch (Exception ex) {}
        }        
        
        return resultList;
	}

		
	
	public static int excuteUpdate( String sSql) throws Exception {
		
		Connection conn =null;
		DbUtils db = new DbUtils();
				
		Statement stmt = null;	    	       
	    conn = db.getConnection();
	    				
        if (sSql == null || sSql.equals("")) {
	        String m_sError = null;
	        if (sSql == null)
	        	m_sError = "excuteQuery Sql is null.";
	        else if (sSql.equals(""))
	        	m_sError = "excuteQuery Sql is empty.";
	        throw new SQLException(m_sError);
        }
        
    	if( conn != null ){
        	Utils.makeLog(sSql);	        	        	
        	stmt = conn.createStatement();
	        stmt.executeUpdate(sSql);
    	}else{
        	Utils.makeLog("ERROR", "[Connection is NULL] Query -> "+ sSql);	  
    		throw new SQLException("[Connection is NULL] Query -> "+ sSql);        	      
    	}
    
        try {	        	
	        if(stmt != null) stmt.close();
	        if(conn != null) conn.close();
	        db.closeConnection();       
        } catch (Exception ex) {}
               
        return 0;
	}

}
