package com.test.opirus;
import com.test.util.DbUtils;
import com.test.util.Utils;
import com.test.mes.H101ClientTest_start;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * @author jaedeuk.ahn 
 * @since 2014.01.21(THU)
 */
public class PgmGetTable {
	
	PgmEquVariable global  = new PgmEquVariable();	
	PgmNewVariable new_Val = new PgmNewVariable();
	
	//private int purposeChkCnt = 0; // 재검색은 한번만 함.
	/// socket data parse
	public void Parse_Data(String allData){
		
		ArrayList<String> tmpKey=new ArrayList<String>();
		String tmpArrData ="";
		
		try{			
			StringTokenizer strtoken = new StringTokenizer(allData,"\n");
			
				global.strCmd = "";			
				global.strLot_Id = "";
				global.strPartnumber = "";
				global.strProcess = "";
				global.strTester = "";
				global.strH_Model = "";
				global.strTester_Num = "";
				global.strHead = "";
				global.strType = "";
				global.strBoard_Id = "";
				global.strEx_Pgm = "";
				global.strEx_revno = "";
				global.strTmode = "";
				global.strPara = "";
				global.strTarFileChk = "";
				global.strPgmChkResult = "";
				//2010 02 02
				global.strNcfCode = "";
				//2011 05 13 구교두
				//2011 07 08 고태경
				global.strSckCtrl = "";
				//2011 10 21 장용철
				global.strMainPgm = "";
				global.strGlobal = "";
				global.strSubModel = "";
				//2012.08.26 KJS
				global.strPurposeType = "";
				
				//global.strSECPartnumber = "";
				
				
			if(allData.startsWith("<MSG>")){
				while(strtoken.hasMoreTokens()){	
					StringTokenizer _strSplit = new StringTokenizer(strtoken.nextToken(),"<>");
					while(_strSplit.hasMoreTokens()){
						tmpKey.add(_strSplit.nextToken());
					}
					
					if (tmpKey.size() == 3){
						tmpArrData = tmpKey.get(1);	
					}else{
						tmpArrData="";
					}
						if(tmpKey.get(0).contains("SYSTEM")){
							global.strSystem = tmpArrData;						
						}else if(tmpKey.get(0).contains("CMD")){
							global.strCmd = tmpArrData;						
						}else if(tmpKey.get(0).equals("LOTID")){
							global.strLot_Id = tmpArrData;
						}else if(tmpKey.get(0).equals("PARTNUMBER")){
							global.strPartnumber = tmpArrData;
							//global.strSECPartnumber = tmpArrData;
						}else if(tmpKey.get(0).equals("PROCESS")){
							global.strProcess = tmpArrData;
						}else if(tmpKey.get(0).equals("MODEL")){
							global.strTester = tmpArrData;
							if(global.strTester.equals("T5377"))
							{
								global.strTester = "T5375";
							}							
						}else if(tmpKey.get(0).equals("HANDLER")){
							global.strH_Model = tmpArrData;
						}else if(tmpKey.get(0).equals("TESTER")){
							global.strTester_Num = tmpArrData;
						}else if(tmpKey.get(0).equals("HEAD")){
							global.strHead = tmpArrData;
						}else if(tmpKey.get(0).equals("TYPE")){
							global.strType = tmpArrData;
						}else if(tmpKey.get(0).equals("BOARD")){
							global.strBoard_Id = tmpArrData;
						}else if(tmpKey.get(0).equals("PRE_PGM")){
							global.strEx_Pgm = tmpArrData;
						}else if(tmpKey.get(0).equals("PRE_REV")){
							global.strEx_revno = tmpArrData;
						}else if(tmpKey.get(0).equals("TMODE")){
							global.strTmode = tmpArrData;
						}else if(tmpKey.get(0).equals("PARA")){
							global.strPara = tmpArrData;
						}else if(tmpKey.get(0).equals("TAR_FILE_CHK")){
							global.strTarFileChk = tmpArrData;
						}else if(tmpKey.get(0).equals("PGM_CHK_RESULT")){
							global.strPgmChkResult = tmpArrData;
						}// 2010 02 02
						else if(tmpKey.get(0).equals("NCF_CODE")){
							global.strNcfCode = tmpArrData;
						}else if( tmpKey.get(0).equals("SCKCTRL")){
							global.strSckCtrl = tmpArrData;
						}else if( tmpKey.get(0).equals("MAIN_PGM")){
							global.strMainPgm = tmpArrData;
						}else if( tmpKey.get(0).equals("GLOBAL")){
							global.strGlobal = tmpArrData;
						}else if( tmpKey.get(0).equals("SUB_MODEL")){
							global.strSubModel = tmpArrData;
						}else if( tmpKey.get(0).equals("PURPOSE_TYPE")){
							global.strPurposeType = tmpArrData;
						}else if( tmpKey.get(0).equals("FAB")){
							global.strFab = tmpArrData;
						}else if( tmpKey.get(0).equals("GRADE")){
							global.strGrade = tmpArrData;
						}else if( tmpKey.get(0).equals("OPERATOR")){
							global.strOperator = tmpArrData;
						}else if( tmpKey.get(0).equals("QTY")){
							global.strQty = tmpArrData;
						}
						//
						

						//strTmsUse
					
					/*
					 * 
					 * 
					else if(tmpKey.get(0).equals("PRE_LOAD_TIME")){
						global.strL_Time = tmpArrData;
					}else if(tmpKey.get(0).equals("T_IP")){
						global.strT_Ip = tmpArrData;
					}
					*/		
					tmpKey.clear();
				}// while end
		
			}
		}catch(Exception ex){
			Utils.makeLog("ERROR",global.strLot_Id+"   [PgmGetTable Parse_Data ERROR #1] "+ex);
			Utils.makeLog(global.strLot_Id+"   [PgmGetTable Parse_Data ERROR #1]" + ex + "   Data=["+tmpArrData+"]");
		}finally{
			Utils.makeLog(global.strLot_Id+"   [Parse Data End]");	
		}
		
	}
	public void getMESInfo(String strLotId, String  strBoard_Tema){
		H101ClientTest_start ct = new H101ClientTest_start();
		if(ct.InitMsgHandler() == false) {
			System.out.println("false");
			return;
		}

		global = ct.ViewLot(global);
		ct.TermMsgHandler();
	}
	//public boolean getPgmInfo(Connection conn, String strLotId, String  strBoard_Tema){
	/// last_table check ( test standard info table)
	public void getPgmInfo(String strLotId, String  strBoard_Tema){
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ResultSet rsc = null;
		ResultSet rs_sub = null;
		String strQry = "";	
		int count=0;
		
		String strtmpProc="";
				
		//M,Q Lot SH PPROCESS
		//boolean isSH = true;
		//String strSH_PorcessFlag= "";
		//String strSH_Porcess = "";
		//String strSH_Lot1st= "";
		//String strSH_Lot2st= "";
		//String strSH_Porcess1st= "";
		//String strSH_Porcess2st= "";
		
		Connection conn;
		DbUtils db = new DbUtils();
	       
	    conn = db.getConnection();
	    
		try{
			
			strtmpProc = global.strProcess;	
			
			//if( purposeChkCnt == 0){
			//	new_Val.strQProcess = "";
			//}
			new_Val.strQProcess = "";
			new_Val.strQPartnumber = "";
			new_Val.strQMain_Pgm = "";
			new_Val.strQPgmCnt = "";
			new_Val.strQSubPgm = "";
			new_Val.strQGlobal = "";							
			new_Val.strQTemp ="";
			new_Val.strQTemp_Limit = "";
			new_Val.strQSpecial_PARA = "";
			new_Val.strQMask_Flow="";
			new_Val.strQMask_Code="";
			new_Val.strQRts_Flow="";
			new_Val.strQPrime_H_Yield="";
			new_Val.strTmsUse="NO";
			new_Val.strNcfCode="";	
			global.strPL_PgmChkResult = "";
			
			//new_Val.strPgmMonitorCnt="";
			//new_Val.strOpirusMonitorCnt="";
			//new_Val.strShProcess= "";
			
			String tmpBoard ="";
						
			try{				
				tmpBoard = strBoard_Tema.substring(3,4);
			}catch(StringIndexOutOfBoundsException se){
				tmpBoard = "";
			}catch(Exception e){
				tmpBoard = "";
				if(tmpBoard !=null && tmpBoard.equals("")){
					Utils.makeLog("Error","getPgmInfo Board parse miss  strBoard_Tema[" +strBoard_Tema+"] " +e);
				}
			}
			
			/*
			 * SMART PART ID -> SEC PART ID 
			
			PartIdCheck partcheck = new PartIdCheck();
			String chgPart = "";
			chgPart = partcheck.changePartId(strLotId, global.strPartnumber);
			Utils.makeLog("getPgmInfo PART ID CHANGE - SMART ["+ global.strPartnumber +"] , SEC ["+chgPart+"]");
			//global.strSECPartnumber = chgPart;
			 */
			/*
			 * 7 자린데.. 992 가 아니면 안내려주는거.. 
			 */
			if( global.strTester.equals("T5585") && global.strProcess.length() ==4){
				if((global.strPartnumber.startsWith("K4T") && tmpBoard.equals("A"))||(global.strPartnumber.startsWith("K4B") && tmpBoard.equals("D") )){
					strtmpProc = global.strProcess+"992";
					new_Val.strQProcess = strtmpProc;			
					strtmpProc = new_Val.strQProcess;
					Utils.makeLog("PROCESS Change [" +new_Val.strQProcess+"]");
				}
			}
			
			if(global.strProcess.length()!=7){
				
				if (global.strPara.length() == 2){
					global.strPara = "0"+global.strPara;
				}else if (global.strPara.length() == 1){
					global.strPara = "00"+global.strPara;
				}
				strtmpProc = global.strProcess + global.strPara;				
				strQry = getLastTableCount(global.strTester, global.strPartnumber, strtmpProc);
				
				stmt = conn.createStatement();
				rs = stmt.executeQuery(strQry);
				if (rs.next()) {
					count = rs.getInt("CNT");					
					if ( count == 0){
						strtmpProc = global.strProcess +"000";
						global.strTableFlag ="NO";
						global.strPL_PgmChkResult = "NO_REGISTOR";
					}
				}// rs end
				if ( rs != null){ rs.close(); }
				
				if (stmt != null){ stmt.close();}
				
			}
			else{				
				strtmpProc = global.strProcess;
				new_Val.strQProcess = strtmpProc;
			}

//			strQry =" SELECT COUNT(*) CNT  FROM LAST_TABLE  "
//					+" WHERE TESTER ='"+ global.strTester +"'"
//					+" AND '"+global.strPartnumber +"' LIKE PARTNUMBER "
//					+" AND PROCESS ='"+new_Val.strQProcess +"'";

			strQry =" SELECT COUNT(*) CNT  FROM LAST_TABLE  "
					+" WHERE TESTER ='"+ global.strTester +"'"
					+" AND "+ " PARTNUMBER  LIKE  '" + global.strPartnumber
					+"' AND PROCESS ='"+new_Val.strQProcess +"'";
//			pstmt = conn.prepareStatement(strQry);
//			pstmt.setString(1, global.strTester);
//			rsc =  pstmt.executeQuery();
				stmt = conn.createStatement();
				rsc = stmt.executeQuery(strQry);
				
				if (rsc.next()){
					count = rsc.getInt("CNT");
					
					strQry = getLastTable( global.strTester ,global.strPartnumber , new_Val.strQProcess);
					
					if(rsc.getInt("CNT") == 0 ){
						global.strTableFlag ="NO";
						global.strPL_PgmChkResult = "NO_REGISTOR";							
					}else if (count == 1){
							stmt = conn.createStatement();
							rs_sub = stmt.executeQuery(strQry);
							
							while(rs_sub.next()){
								
								new_Val.strQPartnumber = rs_sub.getString("PARTNUMBER");
								new_Val.strQMain_Pgm = rs_sub.getString("MAIN_PGM");
								new_Val.strQPgmCnt = rs_sub.getString("PGMCNT");
								new_Val.strQSubPgm = rs_sub.getString("SUB_PGM");
								new_Val.strQGlobal = rs_sub.getString("GLOBAL");							
								new_Val.strQTemp = rs_sub.getString("TEMP");
								new_Val.strQTemp_Limit = rs_sub.getString("TEMP_LIMIT");
								new_Val.strQSpecial_PARA = rs_sub.getString("SPECIAL_PARA");
								new_Val.strQsys_date = rs_sub.getString("SYS_DATE");								
								new_Val.strQMask_Flow=rs_sub.getString("MASK_FLOW");
								new_Val.strQMask_Code=rs_sub.getString("MASK_COMMENTS");
								//20090615
								new_Val.strQRts_Flow=rs_sub.getString("RTS_FLOW");
								new_Val.strQPrime_H_Yield=rs_sub.getString("RUN_SBL_YIELD");
								new_Val.strTmsUse = rs_sub.getString("TMS_USING");
								//2010 02 02
								new_Val.strNcfCode = rs_sub.getString("NCF_RULE");
							}
							global.strTableFlag ="OK";
							global.strPL_PgmChkResult = "";
							if (stmt != null){ stmt.close();}
					
					}else{						
						stmt = conn.createStatement();
						rs_sub = stmt.executeQuery(strQry);
						int itmp=0;
						String tmpdata ="";						
						
						while(rs_sub.next()){
							tmpdata = rs_sub.getString("Partnumber").replaceAll("_", "");
							
													
							if ( itmp < tmpdata.length()){
								itmp = tmpdata.length();
								
								new_Val.strQPartnumber = rs_sub.getString("PARTNUMBER");
								new_Val.strQMain_Pgm = rs_sub.getString("MAIN_PGM");
								new_Val.strQPgmCnt = rs_sub.getString("PGMCNT");
								new_Val.strQSubPgm = rs_sub.getString("SUB_PGM");
								new_Val.strQGlobal = rs_sub.getString("GLOBAL");							
								new_Val.strQTemp = rs_sub.getString("TEMP");
								new_Val.strQTemp_Limit = rs_sub.getString("TEMP_LIMIT");
								new_Val.strQSpecial_PARA = rs_sub.getString("SPECIAL_PARA");
								//new_Val.strQsys_date = rs_sub.getString("SYS_DATE");
								new_Val.strQMask_Flow=rs_sub.getString("MASK_FLOW");
								new_Val.strQMask_Code=rs_sub.getString("MASK_COMMENTS");
							
								new_Val.strQRts_Flow=rs_sub.getString("RTS_FLOW");
								new_Val.strQPrime_H_Yield=rs_sub.getString("RUN_SBL_YIELD");
								new_Val.strTmsUse = rs_sub.getString("TMS_USING");
								
								new_Val.strNcfCode = rs_sub.getString("NCF_RULE");

							}
						}
						if (stmt != null){ stmt.close();}
						global.strTableFlag ="OK";
						global.strPL_PgmChkResult = "";
					}
				}// rsc end 
			
				Utils.makeLog(strLotId+"   [getPgmInfo] LAST_TABLE : "+strQry);
			
				if ( rs != null){ rs.close(); }
				if ( rsc != null){ rsc.close(); }
				if (stmt != null){ stmt.close();}
			}catch(Exception ex){
				try{
					Utils.makeLog("ERROR",strLotId+"   [PgmGetTable getPgmInfo ERROR #1] "+ex);
					Utils.makeLog("ERROR",strLotId+"   [PgmGetTable getPgmInfo SQL] "+strQry);
					Utils.makeLog(strLotId+"   [PgmGetTable getPgmInfo ERROR #1] " + ex);
				}catch(Exception ec){
					Utils.makeLog("ERROR",strLotId+"   [PgmGetTable getPgmInfo ERROR #2] "+ec);
					Utils.makeLog(strLotId+"   [PgmGetTable getPgmInfo ERROR #2] " + ec);
				}
			}finally {
				try {	
					if ( rs != null){ rs.close(); }
					if ( rsc != null){ rsc.close(); }
					if (stmt != null){ stmt.close();}
				} catch (Exception ex) {
					Utils.makeLog("ERROR",strLotId+"   [PgmGetTable getPgmInfo ERROR #3] "+ex);
					Utils.makeLog(strLotId+"   [PgmGetTable getPgmInfo #3]" + ex);
				}
			}
	}
	
	/// lastable row count check
	private String getLastTableCount(String strTester,String strPartnumber,String strProc){
		
		return " SELECT COUNT(*) CNT  FROM LAST_TABLE  "
				+" WHERE TESTER ='"+ strTester +"'"
				+" AND '"+strPartnumber +"' LIKE PARTNUMBER "
				+" AND PROCESS ='"+ strProc +"'";		
	}
	
	private String getLastTable(String strTester,String strPartnumber,String strProc){
//		mssql
//		return "SELECT PARTNUMBER, MAIN_PGM, ISNULL(PGMCNT,'0') PGMCNT, ISNULL(SUB_PGM,' ') SUB_PGM, GLOBAL,"
//				+" ISNULL(TEMP,'NO') TEMP, ISNULL(TEMP_LIMIT,'NO') TEMP_LIMIT, SYS_DATE, SPECIAL_PARA, "
//				+" ISNULL(MASK_FLOW,'N') MASK_FLOW , MASK_COMMENTS, ISNULL(RTS_FLOW,'N') RTS_FLOW, "
//				+" ISNULL(RUN_SBL_YIELD,'100') RUN_SBL_YIELD, ISNULL(TMS_USING,'NO') TMS_USING, "
//				+" ISNULL(NCF_RULE,'---') NCF_RULE "
//				+" ,OPERATOR"
//				//2UI
//				+" , ISNULL(MULTIUI_FLAG,'NO') MULTIUI_FLAG"
//				+" FROM LAST_TABLE "
//				+" WHERE TESTER ='"+ strTester +"'"
//				+" AND " +" PARTNUMBER LIKE '"+strPartnumber
//				+"' AND PROCESS ='"+ strProc +"'";

//		oracle
		return "SELECT PARTNUMBER, MAIN_PGM, NVL(PGMCNT,'0') PGMCNT, NVL(SUB_PGM,' ') SUB_PGM, GLOBAL,"
				+" NVL(TEMP,'NO') TEMP, NVL(TEMP_LIMIT,'NO') TEMP_LIMIT, SYS_DATE, SPECIAL_PARA, "
				+" NVL(MASK_FLOW,'N') MASK_FLOW , MASK_COMMENTS, NVL(RTS_FLOW,'N') RTS_FLOW, "
				+" NVL(RUN_SBL_YIELD,'100') RUN_SBL_YIELD, NVL(TMS_USING,'NO') TMS_USING, "
				+" NVL(NCF_RULE,'---') NCF_RULE "
				+" ,OPERATOR"
				//2UI
				+" , NVL(MULTIUI_FLAG,'NO') MULTIUI_FLAG"
				+" FROM LAST_TABLE "
				+" WHERE TESTER ='"+ strTester +"'"
				+" AND " +" PARTNUMBER LIKE '"+strPartnumber
				+"' AND PROCESS ='"+ strProc +"'";

	}
}