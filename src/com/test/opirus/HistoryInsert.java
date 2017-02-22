package com.test.opirus;

/**
 * @author jaedeuk.ahn
 * @since 2014.01.21(THU)
 */

import com.test.util.Utils;



public class HistoryInsert {
	
	public long tmpDiff = 0;
	
	public long Pgm_InsertDb(PgmGetTable getTable, PgmEquVariable global , 
			PgmRev  pgm_Load, PgmNewVariable  new_Val, PgmTimeChk Pgm_T_Chk,
			String strStartTime ,String strLotId){
		
		//Statement stmt = null;	 
		String strQry = null;
		Properties prop = new Properties();		
		prop.loadProperties("opirus.properties");
		
		try {			
			//conn.setAutoCommit(false);
					
			strQry=  " INSERT INTO LOTIN_INFO_HIST ("
					+" SYS_DATE, RCV_LOT, RCV_PARTNUMBER, RCV_PROCESS, RCV_PARA"
					+", RCV_TMODEL, RCV_HMODEL, RCV_TESTER, RCV_HEAD, RCV_PGM"
					+", RCV_REVNO  ,RCV_BOARD, RCV_TYPE, RCV_T_MODE, UNI_TABLE_EXIST"
					+", UNI_TARPGM, UNI_REVNO, UNI_PARTNUMBER, UNI_PROCESS, UNI_MAINPGM"
					+", UNI_SUBPGM, PGM_TOTAL_CNT, PGM_CHANGE_CNT, UNI_GLOBAL, UNI_TEMP"
					+", UNI_TEMP_LIMIT, UNI_PARA, UNI_MASK_SBL, RCV_TAR_FILE_CHK, USE_PORT" 
					+", DECISION_TIME"
				  +" ) VALUES ( "  ;
				
			//stmt = conn.createStatement();

			try{
				if ( Pgm_T_Chk.strQTar_Pgm == null) {
					Pgm_T_Chk.strQTar_Pgm = "NOT_LOAD";			
					}
				if ( Pgm_T_Chk.strQTar_Pgm == "") {
					Pgm_T_Chk.strQTar_Pgm = "NOT_LOAD";			
					}
				if ( Pgm_T_Chk.strPgm_Flag.equals("NOT_LOAD")){
					Pgm_T_Chk.strQTar_Pgm = "NOT_LOAD";
				}else{
					Pgm_T_Chk.strQTar_Pgm = Pgm_T_Chk.strQTar_Pgm;
				}
			}catch(NullPointerException nex){
				Pgm_T_Chk.strQTar_Pgm = "NOT_LOAD";		
			}
			
			if((getTable.new_Val.strQPartnumber == null)||(getTable.new_Val.strQPartnumber.equals(" "))||(getTable.new_Val.strQPartnumber.equals(""))){
				getTable.new_Val.strQPartnumber="-";
				getTable.new_Val.strQProcess="-";
			}
			tmpDiff = Utils.getTimeDiff(strStartTime, Utils.getDate("yyyy/MM/dd HH:mm:ss", 0));

//			strQry = strQry +"replace(replace(replace(replace(convert(varchar(20),getdate(),121),'-',''),' ',''),':',''),'.','') ";//mssql
			strQry = strQry +"TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS') ";//oracle
			strQry = strQry + ", '"+getTable.global.strLot_Id+"'";
			//strQry = strQry + ", '"+ getTable.global.strPartnumber+"'";
			strQry = strQry + ", '"+ getTable.global.strPartnumber+"'";//strSECPPartnumber
			strQry = strQry + ", '"+ getTable.global.strProcess+"'";
			strQry = strQry + ", '"+getTable.global.strPara+"'";
			
			strQry = strQry + ", '"+ getTable.global.strTester+"'";			
			strQry = strQry + ", '"+ getTable.global.strH_Model+"'";			
			strQry = strQry + ", '"+ getTable.global.strTester_Num+"'";			
			strQry = strQry + ", '"+ getTable.global.strHead+"'";
			strQry = strQry + ", '"+ getTable.global.strEx_Pgm+"'";
			
			strQry = strQry + ", '"+ getTable.global.strEx_revno+"'";
			strQry = strQry + ", '"+ getTable.global.strBoard_Id+"'";
			strQry = strQry + ", '"+getTable.global.strType+"'";
			strQry = strQry + ", '"+getTable.global.strTmode+"'"; 
			strQry = strQry + ", '"+getTable.global.strTableFlag+"'";
			
			strQry = strQry + ", '"+Pgm_T_Chk.strQTar_Pgm+"'";
			strQry = strQry + ", '"+pgm_Load.new_Val.strQRevNo+"'";			
			strQry = strQry + ", '"+getTable.new_Val.strQPartnumber+"'";
			strQry = strQry + ", '"+getTable.new_Val.strQProcess+"'";	
			strQry = strQry + ", '"+getTable.new_Val.strQMain_Pgm+"'";
			
			strQry = strQry + ", '"+getTable.new_Val.strQSubPgm+"'";
			strQry = strQry + ", '"+Pgm_T_Chk.pgm_total_cnt+"'"; 
			strQry = strQry + ", '"+Pgm_T_Chk.pgm_change_cnt+"'";			
			strQry = strQry + ", '"+getTable.new_Val.strQGlobal+"'";			
			strQry = strQry + ", '"+getTable.new_Val.strQTemp+"'";
			
			strQry = strQry + ", '"+getTable.new_Val.strQTemp_Limit+"'";
			strQry = strQry + ", '"+getTable.new_Val.strQSpecial_PARA+"'";
			strQry = strQry + ", '"+getTable.new_Val.strQMask_Code+"'";			
			strQry = strQry + ", '"+getTable.global.strTarFileChk+"'";
			strQry = strQry + ", '"+prop.getSocketPort()+"'";
			
			strQry = strQry + ", '"+String.valueOf(tmpDiff)+"'";
			strQry = strQry + " )";
		
			System.out.println(strQry);
			
			//stmt.executeUpdate(strQry);
			//DaoImpl.excuteUpdate(strQry);
			//conn.commit();			
			//stmt.close();
			 
		}catch(Exception ex){
			try {
				//conn.rollback();
				Utils.makeLog("ERROR",strLotId+"   [PgmInsert Pgm_InsertDb ERROR #1]" + ex);
				Utils.makeLog(strLotId+"   [PgmInsert Pgm_InsertDb ERROR] #1" + ex);
			} catch (Exception e) {
				Utils.makeLog(strLotId+"   [Pgm_InsertDb ERROR]" + e);
			}
		}finally {
			try {
				Utils.makeLog(strLotId+"   [Opirus History Insert]");
				//if (stmt != null) {stmt.close();}
			} catch (Exception ex) {
				Utils.makeLog("ERROR",strLotId+"   [PgmInsert Pgm_InsertDb ERROR #2]" + ex);
				Utils.makeLog(strLotId+"   [PgmInsert Pgm_InsertDb ERROR] #2" + ex);
			}
			
		}
		return tmpDiff;
	}
}
