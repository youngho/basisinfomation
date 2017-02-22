package com.test.opirus;
/**
 * @author jaedeuk.ahn
 * @since 2014.01.21(THU)
 */
import com.test.util.DaoImpl;
import com.test.util.Utils;

import java.util.ArrayList;



public class PgmRev {

	PgmNewVariable new_Val = new PgmNewVariable(); 
	
	public void getRev(PgmGetTable getTable, PgmEquVariable global ,String strLotId){
		
	//Statement stmt = null;
	//ResultSet rs = null;
	//ResultSet rsc = null;
	ArrayList<String[]> resultList = new ArrayList<String[]>();
	int itpcos_cnt = 0;
	String strQry = "";	
	String strtmp ="";
	
		try{			
			new_Val.strQTPcos_Max_Revno = "";
			new_Val.strQRevNo ="";			
			
			strQry = "SELECT count(*) CNT FROM TPCOS_HISTORY "
				+" WHERE FILE_NAME like '"+getTable.new_Val.strQMain_Pgm+".%'"
				//+" and APPROVAL_FLAG = '결재'"
				+" AND UPPER(FILE_TYPE)<>'SOURCE'";
			//stmt = connTpcos.createStatement();
			//rs = DaoImpl.selectQuery(strQry);
			
			itpcos_cnt = DaoImpl.selectQueryInt(strQry);
							
			if( itpcos_cnt > 0){
				strtmp = " itpcos_cnt > 0 ";
				strQry=" SELECT ISNULL(max(revno),'000.000') REVNO "
					+" FROM TPCOS_HISTORY "
					+" WHERE FILE_NAME like '"+getTable.new_Val.strQMain_Pgm+".%'"
					//+" and APPROVAL_FLAG = '결재'"
					+" AND UPPER(FILE_TYPE)<>'SOURCE'";
				//stmt = connTpcos.createStatement();
				//rsc = DaoImpl.selectQuery(strQry);
				resultList =  DaoImpl.selectQueryList(strQry);
				for(String[] arr : resultList){
					new_Val.strQTPcos_Max_Revno = arr[0];
					new_Val.strQRevNo = arr[1];
				}
				
				Utils.makeLog(strLotId+"   [Now PEPSY Rev] revno=["+new_Val.strQTPcos_Max_Revno+"]");
			
			}else{		
				strQry = "SELECT count(*) CNT FROM PEPSY_PGM_HISTORY "
					+" WHERE SRCPGM like '"+getTable.new_Val.strQMain_Pgm+"%'" 
					+" and APPROVAL_FLAG = '결재'";
				//stmt = connTpcos.createStatement();
				//rs = DaoImpl.selectQuery(strQry);
				
				itpcos_cnt = DaoImpl.selectQueryInt(strQry);
				
				if( itpcos_cnt > 0){
					strtmp = " itpcos_cnt > 0 ";
					strQry=" SELECT ISNULL(max(revno),'000.000') REVNO "
						+" FROM PEPSY_PGM_HISTORY " 
						+" WHERE SRCPGM like '"+getTable.new_Val.strQMain_Pgm+"%'" 
						+" and APPROVAL_FLAG = '결재'";
					//stmt = connTpcos.createStatement();
					//rsc = DaoImpl.selectQuery(strQry);
					resultList =  DaoImpl.selectQueryList(strQry);
					for(String[] arr : resultList){
						new_Val.strQTPcos_Max_Revno = arr[0];
						new_Val.strQRevNo = arr[1];
					}					
					Utils.makeLog(strLotId+"   [Now PEPSY Rev] revno=["+new_Val.strQTPcos_Max_Revno+"]");
				
				}
				/*else{		
					strtmp = " itpcos_cnt <= 0 ";
					strQry =" SELECT GREATEST(A.REVNO,B.REVNO) as REVNO  FROM " 
			                +" (SELECT ISNULL(MAX(REVNO),'000.000') REVNO FROM HIS_ADMINPGM WHERE SRCPGM LIKE '"+getTable.new_Val.strQMain_Pgm+"%'" 
			                +" AND APPROVAL = '1') A, "
			                +" (SELECT ISNULL(MAX(REVNO),'000.000') REVNO FROM TMP_ADMINPGM WHERE SRCPGM LIKE '"+getTable.new_Val.strQMain_Pgm+"%'" 
			                +" AND REDFLAG= '1') B";
					//stmt = connTpcos.createStatement();
					//rsc = DaoImpl.selectQueryList(strQry);
					resultList =  DaoImpl.selectQueryList(strQry);
					
					if( resultList != null &  resultList.size() > 0){
						for(String[] arr : resultList){
							new_Val.strQTPcos_Max_Revno = arr[0];		
							new_Val.strQRevNo = arr[0];
							Utils.makeLog(strLotId+"   [Now TPCOS Rev] Revno=["+new_Val.strQTPcos_Max_Revno+"]");
						}
					}else{
					//	new_Val.strQPgm_Flag ="1";
						new_Val.strQRevNo = "000.000";
						Utils.makeLog(strLotId+"   [GetPgmRev] No Program in pepsy_pgm_history");
					}
				}
				*/
				//stmt.close();
			}
			//stmt.close();
			
			
		}catch(Exception ex){
				Utils.makeLog("ERROR",strLotId+"   [PgmRev ERROR #1] " +strtmp  + ex);
				Utils.makeLog("ERROR",strLotId+"   [PgmRev ERROR SQL]  " + strQry);
				Utils.makeLog(strLotId+"   [PgmRev ERROR #1]" + ex);
				Utils.makeLog(strLotId+"   [PgmRev ERROR SQL]  " + strQry);
			
		}finally {
			try {	
				//if (rs != null){ rs.close(); }
				//if (rsc != null){ rsc.close(); }
				//if (stmt != null){ stmt.close();}
			} catch (Exception ex) {}
		}
		
		
	}
}
