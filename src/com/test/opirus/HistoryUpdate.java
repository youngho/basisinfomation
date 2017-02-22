package com.test.opirus;

/**
 * @author jaedeuk.ahn
 * @since 2014.01.21(THU)
 */

import java.sql.SQLException;
import java.util.ArrayList;

import com.test.util.DaoImpl;
import com.test.util.Utils;

public class HistoryUpdate {
	
	//when test pgm loadding pass.  update history table ( column : TESTER_CHECK_UPDATE )
	public String  Pgm_UpdateDb(PgmGetTable getTable, PgmEquVariable global ,String strLotId){
		
		//Statement stmt = null;	 
		//ResultSet rs = null;
		//ResultSet rsc = null;
		String strQry = null;
		int iHisCnt=0;
		String strFlag ="";
		String strChkResult ="PASS";
		String strComment ="OK";
		
		String strMainPgm ="";
		String strGlobal ="";
		
		String updatefail = "";
		String pgmfail = "";
		String globalfail = "";
		String global_MainPgm = getTable.global.strMainPgm;
		//String strCheckComment = "";
		//String strUniPrcess = "";
	
		ArrayList<String[]> resultList = new ArrayList<String[]>();
		
		if( getTable.global.strSubModel != null && getTable.global.strSubModel.equals("ASX")){
			global_MainPgm = global_MainPgm.toLowerCase();
		}
		
		/*
		 * SMART PART ID -> SEC PART ID 		
		PartIdCheck partcheck = new PartIdCheck();
		String chgPart = "";
		chgPart = partcheck.changePartId(strLotId, getTable.global.strPartnumber);
		Utils.makeLog("Pgm_UpdateDb PART ID CHANGE - SMART ["+ getTable.global.strPartnumber +"] , SEC ["+chgPart+"]");
		getTable.global.strPartnumber = chgPart;
		 */
		//String op_partnumber ="";
		
		try {
			strQry = " SELECT  COUNT(RCV_LOT) CNT FROM LOTIN_INFO_HIST "
				+" WHERE SYS_DATE BETWEEN  replace(replace(replace(replace(convert(varchar(20),getdate()-1,121),'-',''),' ',''),':',''),'.','') AND  replace(replace(replace(replace(convert(varchar(20),getdate(),121),'-',''),' ',''),':',''),'.','') "
				+" AND RCV_LOT='"+getTable.global.strLot_Id+"'"
				+" AND RCV_HMODEL='"+getTable.global.strH_Model+"'"
				+" AND RCV_TMODEL='"+getTable.global.strTester+"'"
				+" AND RCV_TESTER='"+getTable.global.strTester_Num+"'"
				+" AND UNI_PROCESS='"+getTable.global.strProcess+"'"
				+" AND RCV_PARTNUMBER='"+getTable.global.strPartnumber+"'"
				;
			//stmt = conn.createStatement();
			//rs = DaoImpl.selectQuery(strQry);
			iHisCnt = DaoImpl.selectQueryInt(strQry);
//			while( rs.next()){
//				iHisCnt = rs.getInt("CNT");
//			}
			strQry = "";
			
			//if ( stmt!= null) { stmt.close();}
			//if ( rs!= null) { rs.close();}
			
			if ( iHisCnt >= 0 ){
				
				try{
					
					int count =0;
					
					strQry =" SELECT COUNT(*) CNT  FROM LAST_TABLE  "
						+" WHERE TESTER ='"+ getTable.global.strTester +"'"
						+" AND '"+getTable.global.strPartnumber +"' LIKE PARTNUMBER "
						+" AND PROCESS ='"+ getTable.global.strProcess +"'";
					
						//stmt = conn.createStatement();
						//rsc = DaoImpl.selectQuery(strQry);
					count = DaoImpl.selectQueryInt(strQry);						
					//count = rsc.getInt("CNT");					
					if(count == 0 ){
						global.strTableFlag ="NO";
						global.strPL_PgmChkResult = "NO_REGISTOR";							
					}else if (count == 1){
						
						strQry = " SELECT MAIN_PGM, GLOBAL, PARTNUMBER, PROCESS "
							+" FROM LAST_TABLE "
							+" WHERE TESTER ='"+ getTable.global.strTester +"'"
							+" AND '"+getTable.global.strPartnumber +"' LIKE PARTNUMBER "
							+" AND PROCESS ='"+ getTable.global.strProcess +"'";
						
						//stmt = conn.createStatement();
						//rs = DaoImpl.selectQuery(strQry);
						resultList = DaoImpl.selectQueryList(strQry);
						for(String[] arr : resultList){
							strMainPgm = arr[0];
							strGlobal = arr[1];
							//op_partnumber = rs.getString("PARTNUMBER");
							//strUniPrcess = rs.getString("PROCESS");
						}
//						while( rs.next()){
//							strMainPgm = rs.getString("MAIN_PGM");
//							strGlobal = rs.getString("GLOBAL");
//							//op_partnumber = rs.getString("PARTNUMBER");
//							//strUniPrcess = rs.getString("PROCESS");
//						}
						Utils.makeLog("[UPDATE CHECK MAIN_PGM] MAIN PGM,GLOBAL CHECK  Qry="+strQry);
						strQry = "";
						
						//if ( stmt!= null) { stmt.close();}
						Utils.makeLog("[UPDATE CHECK MAIN_PGM] MAIN PGM,GLOBAL CHECK  LAST_TABLE.MAIN_PGM=[" +strMainPgm+"]   TESTER_MAIN_PGM=["+global_MainPgm+"]");
						Utils.makeLog("[UPDATE CHECK MAIN_PGM] MAIN PGM,GLOBAL CHECK  LAST_TABLE.GLOBAL=[" +strGlobal+"]   TESTER_MAIN_PGM=["+getTable.global.strGlobal+"]");
						
					}else{
						
						strQry = " SELECT MAIN_PGM, GLOBAL, PARTNUMBER, PROCESS"
							+" FROM LAST_TABLE "
							+" WHERE TESTER ='"+ getTable.global.strTester +"'"
							+" AND '"+getTable.global.strPartnumber +"' LIKE PARTNUMBER "
							+" AND PROCESS ='"+ getTable.global.strProcess +"'";
						
						//stmt = conn.createStatement();
						//rs = DaoImpl.selectQuery(strQry);
						
						int itmp=0;
						String tmpdata ="";						
						//String tmp_Prod="";
						
						resultList = DaoImpl.selectQueryList(strQry);
						for(String[] arr : resultList){
							tmpdata = arr[2].replaceAll("_", "");
							//tmp_Prod += rs.getString("PARTNUMBER")+", ";
							if ( itmp < tmpdata.length()){
								itmp = tmpdata.length();
								strMainPgm =arr[0];
								strGlobal = arr[1];
								//op_partnumber = rs.getString("PARTNUMBER");
								//strUniPrcess = rs.getString("PROCESS");
							}
						}
//						while(rs.next()){
//							tmpdata = rs.getString("PARTNUMBER").replaceAll("_", "");
//							//tmp_Prod += rs.getString("PARTNUMBER")+", ";
//							if ( itmp < tmpdata.length()){
//								itmp = tmpdata.length();
//								strMainPgm = rs.getString("MAIN_PGM");
//								strGlobal = rs.getString("GLOBAL");
//								//op_partnumber = rs.getString("PARTNUMBER");
//								//strUniPrcess = rs.getString("PROCESS");
//							}
//						}	
					}
					
				
						if( !strMainPgm.equals(global_MainPgm)){
							pgmfail = "FAIL";							
						}
						if( !strGlobal.equals(getTable.global.strGlobal)){
							globalfail = "FAIL";							
						}
					}catch (SQLException SQLe) {
						strChkResult = "FAIL";
						strComment = "DB_UPDATE_FAIL";						
						Utils.makeLog("[UPDATE CHECK MAIN_PGM] error : " + SQLe +"     "+strQry);
					}catch (Exception e) {
						strChkResult = "FAIL";
						strComment = "DB_UPDATE_FAIL";
						Utils.makeLog("[UPDATE CHECK MAIN_PGM] error : " + e);
					}finally{
						try{
							//if ( stmt!= null) { stmt.close();}
							//if ( rs!= null) { rs.close();}
							//if ( rsc!= null) { rsc.close();}							
						}catch (Exception e) {}
					}
					
					if( !pgmfail.equals("FAIL") && !globalfail.equals("FAIL")){
						try{
							strQry =" UPDATE LOTIN_INFO_HIST SET "
								+" TESTER_CHECK_UPDATE ='Y'"
								//+" WHERE SYS_DATE BETWEEN  CONVERT(SYSDATE-1,'YYYYMMDDHH24MISS') AND CONVERT(SYSDATE,'YYYYMMDDHH24MISS') "
								+" WHERE SYS_DATE BETWEEN  replace(replace(replace(replace(convert(varchar(20),getdate()-1,121),'-',''),' ',''),':',''),'.','') AND  replace(replace(replace(replace(convert(varchar(20),getdate(),121),'-',''),' ',''),':',''),'.','') "
								+" AND RCV_LOT='"+getTable.global.strLot_Id+"'"
								+" AND RCV_HMODEL='"+getTable.global.strH_Model+"'"
								+" AND RCV_TMODEL='"+getTable.global.strTester+"'"
								+" AND RCV_TESTER='"+getTable.global.strTester_Num+"'"
								+" AND UNI_PROCESS='"+getTable.global.strProcess+"'"
								+" AND RCV_PARTNUMBER='"+getTable.global.strPartnumber+"'";
							
							//stmt = conn.createStatement();
							DaoImpl.excuteUpdate(strQry);
							//stmt.executeUpdate(strQry);
							strChkResult = "PASS";
							strComment = "OK";
							Utils.makeLog(getTable.global.strLot_Id+" [UPDATE LOTIN_INFO_HIST] 'Y'=["+getTable.global.strTester_Num +"]");	
							
							//conn.commit();
							//if (stmt != null) {stmt.close();}
						}catch (Exception e) {
							updatefail = "FAIL";
							//conn.rollback();
							Utils.makeLog("[UPDATE LOTIN_INFO_HIST] error : " + e);
							Utils.makeLog("ERROR","[UPDATE LOTIN_INFO_HIST] error : " + e);
						}
					}else{
						strChkResult = "FAIL";
						strComment = "DB_UPDATE_FAIL";
					}
					
				}else{
					strChkResult = "FAIL";
					strComment = "DB_UPDATE_FAIL";
				}
												
				if(	pgmfail.equals("FAIL") && !globalfail.equals("FAIL") && !updatefail.equals("FAIL") ){
					strChkResult = "FAIL";
					strComment = "SETTING_FAIL";
					Utils.makeLog(getTable.global.strLot_Id+"  HistoryUpdate PGM_CHK FAIL TYPE-1\n"+strQry);
				}				
				if(	pgmfail.equals("FAIL") && !globalfail.equals("FAIL") && updatefail.equals("FAIL") ){
					strChkResult = "FAIL";
					strComment = "ALL_FAIL";
					Utils.makeLog(getTable.global.strLot_Id+"  HistoryUpdate PGM_CHK FAIL TYPE-2\n"+strQry);
				}				
				if(	pgmfail.equals("FAIL") && globalfail.equals("FAIL") && !updatefail.equals("FAIL") ){
					strChkResult = "FAIL";
					strComment = "SETTING_FAIL";
					Utils.makeLog(getTable.global.strLot_Id+"  HistoryUpdate PGM_CHK FAIL TYPE-3\n"+strQry);
				}
				if(	pgmfail.equals("FAIL") && globalfail.equals("FAIL") && updatefail.equals("FAIL") ){
					strChkResult = "FAIL";
					strComment = "ALL_FAIL";
					Utils.makeLog(getTable.global.strLot_Id+"  HistoryUpdate PGM_CHK FAIL TYPE-4\n"+strQry);
				}
				if(	!pgmfail.equals("FAIL") && !globalfail.equals("FAIL") && updatefail.equals("FAIL") ){
					strChkResult = "FAIL";
					strComment = "DB_UPDATE_FAIL";
					Utils.makeLog(getTable.global.strLot_Id+"  HistoryUpdate PGM_CHK FAIL TYPE-5\n"+strQry);
				}
				if(	!pgmfail.equals("FAIL") && globalfail.equals("FAIL") && !updatefail.equals("FAIL") ){
					strChkResult = "FAIL";
					strComment = "SETTING_FAIL";
					Utils.makeLog(getTable.global.strLot_Id+"  HistoryUpdate PGM_CHK FAIL TYPE-6\n"+strQry);
				}
				if(	!pgmfail.equals("FAIL") && globalfail.equals("FAIL") && updatefail.equals("FAIL") ){
					strChkResult = "FAIL";
					strComment = "ALL_FAIL";
					Utils.makeLog(getTable.global.strLot_Id+"  HistoryUpdate PGM_CHK FAIL TYPE-7 \n"+strQry);
				}
				strFlag= "<MSG>\n"
			        	+"<SYSTEM>"+getTable.global.strSystem+"</SYSTEM>\n"
			        	+"<CMD>"+getTable.global.strCmd+"</CMD>\n"
			        	+"<PGM_CHK_UPDATE>"+ strChkResult +"</PGM_CHK_UPDATE>\n"
			        	+"<COMMENT>"+ strComment +"</COMMENT>\n"
			        	//+"<op_partnumber>"+ op_partnumber +"</op_partnumber>\n"
			        	+"</MSG>";		

				//if (stmt != null) {stmt.close();}
				
			return strFlag;
		}catch(Exception ex){
			
			strFlag= "<MSG>\n"
	        	+"<SYSTEM>"+getTable.global.strSystem+"</SYSTEM>\n"
	        	+"<CMD>"+getTable.global.strCmd+"</CMD>\n"
	        	+"<PGM_CHK_UPDATE>NO</PGM_CHK_UPDATE>\n"
	        	+"<COMMENT>EXCEPTION</COMMENT>\n"
	        	//+"<op_partnumber>"+ op_partnumber +"</op_partnumber>\n"
	        	+"</MSG>";
	        
			Utils.makeLog("[UPDATE LOTIN_INFO_HIST] 'N'=["+getTable.global.strTester_Num +"] ");
			
			try {
				//if (stmt != null) {stmt.close();}
				//conn.rollback();
				Utils.makeLog("ERROR","[HistoryUpdate Pgm_UpdateDb ERROR #1]" + ex);
				Utils.makeLog("ERROR","[HistoryUpdate Pgm_UpdateDb ERROR #1]" + strQry);
				Utils.makeLog("[HistoryUpdate Pgm_UpdateDb ERROR] #1" + ex);
				return strFlag;
			} catch (Exception e) {
				Utils.makeLog("[Pgm_UpdateDb ERROR]" + e);
				Utils.makeLog("ERROR","[HistoryUpdate Pgm_UpdateDb ERROR #1]" + e);
				Utils.makeLog("ERROR","[HistoryUpdate Pgm_UpdateDb ERROR #1]" + strQry);
				return strFlag;
			}
		}finally {
			try {
				Utils.makeLog("[Opirus History Update]");				
				//if (stmt != null) {stmt.close();}
				//if (rs != null) {rs.close();}
			} catch (Exception ex) {
				Utils.makeLog("ERROR","[HistoryUpdate Pgm_UpdateDb ERROR #2]" + ex);
				Utils.makeLog("[HistoryUpdate Pgm_UpdateDb ERROR] #2" + ex);
			}
		}
	}
	
}
