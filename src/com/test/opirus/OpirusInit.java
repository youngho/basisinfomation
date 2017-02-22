package com.test.opirus;

/**
 * @author jaedeuk.ahn
 * @since 2014.01.21(THU)
 */

import com.test.util.Utils;

import java.io.IOException;


public class OpirusInit {
	
	public long decisionTimeL =0; 
	
	public static void main(String[] args) throws IOException {
		
		String  line= "<MSG>\n"
				+"<SYSTEM>OPIRUS</SYSTEM>\n"
				+"<CMD>PROGRAM_LOAD</CMD>\n"
				+"<LOTID>KGDTEST</LOTID>\n"
				+"<PARTNUMBER>H5TQXXXXXXX-XXX</PARTNUMBER>\n"
				+"<PROCESS>T092RWK</PROCESS>\n"
				+"<MODEL>T5503</MODEL>\n"
				+"<HANDLER>STH5600</HANDLER>\n"
				+"<TESTER>kd00</TESTER>\n"
				+"<HEAD>A</HEAD>\n"
				+"<TYPE>PP</TYPE>\n"
				+"<TMODE>NOR</TMODE>\n"
				+"<BOARD></BOARD>\n"
				+"<PARA></PARA>\n"
				+"<PRE_PGM>000.016</PRE_PGM>\n"
				+"<PRE_REV>0</PRE_REV>\n"
				+"<TAR_FILE_CHK>NO</TAR_FILE_CHK>\n"
				+"<NCF_CODE>---</NCF_CODE>\n"
				+"<HMODE>CONTINUE</HMODE>\n"
				+"<FSST_USING>NO</FSST_USING>\n"
				+"<SCKCTRL>NO</SCKCTRL>\n"
				+"<PURPOSE_TYPE>-</PURPOSE_TYPE>\n"
				+"<FAB>-</FAB>\n"
				+"<GRADE>-</GRADE>\n"
				+"<OPERATOR>-</OPERATOR>\n"
				+"<QTY>-</QTY>\n"
				+"</MSG>\n";
	
		System.out.println(line);
		OpirusInit a = new OpirusInit();
		a.getOpirusLoad(line,"","");
	}
	
	public  String getOpirusLoad( String tmpData,String strStartTime, String ip){
		
		PgmGetTable getTable  = new PgmGetTable();
		PgmEquVariable global  =  new PgmEquVariable();
		PgmNewVariable  new_Val = new PgmNewVariable();
		PgmRev  Rev_Load =  new PgmRev();
		HistoryInsert Pgm_Insert = new HistoryInsert();
		PgmTimeChk PgmDiff = new PgmTimeChk();
		HistoryUpdate Pgm_Update = new HistoryUpdate();
				
		String strtmpData=tmpData;
		String strReturn =null;
		String strLotId = "";
		
		if( !strtmpData.equals("") || !strtmpData.equals("Nodata") ){
			try{				
				getTable.Parse_Data(strtmpData.toString());	
				strLotId = getTable.global.strLot_Id;
				
				if(getTable.global.strCmd.equals("PROGRAM_CHECK_RESULT") && getTable.global.strPgmChkResult.equals("OK") ){
					strReturn= Pgm_Update.Pgm_UpdateDb(getTable, global, strLotId);
					
				}else if (getTable.global.strCmd.equals("PROGRAM_LOAD")){ // ???? ???? ????..
					
//					bTestInfoPassFlag = getTable.getPgmInfo(conn,strLotId,"");
//					getTable.getMESInfo(strLotId,""); //MES 연결사용
					getTable.getPgmInfo(strLotId,"");
					
//					if( bTestInfoPassFlag ){
					if( getTable.global.strTableFlag.equals("OK")){
						/*cho TPCOS_HISTORY 테이블 없음 GET Rev_Load 주석처리*/
//						Rev_Load.getRev(getTable, global,strLotId);
						PgmDiff.makeTar(getTable, global, Rev_Load, new_Val, PgmDiff, strLotId);
						
					}else{
						Utils.makeLog(strLotId+" [OpirusLoadMain]   <PGM_TABLE>NO</PGM_TABLE>");
						getTable.global.strTableFlag = "NO";
					}
					
//					decisionTimeL = Pgm_Insert.Pgm_InsertDb(conn,getTable, global, Time_Load, Rev_Load, new_Val, PgmDiff,Hnad_Table, Board_Load , Temp_Load, strStartTime,strLotId);
					/*cho LOTIN_INFO_HIST insert 루틴이지만 막아놓음 ? */
					decisionTimeL = Pgm_Insert.Pgm_InsertDb(getTable, global, Rev_Load, new_Val, PgmDiff, strStartTime,strLotId);
//					strReturn = PgmDiff.set_Return(conn,getTable, global, Time_Load, Rev_Load, new_Val, PgmDiff, Hnad_Table, Board_Load , Temp_Load,strLotId);
					strReturn = PgmDiff.set_Return(getTable, global, Rev_Load, new_Val, PgmDiff, strLotId);
					
				}
				
				UpdateEqpLayout eqpInfo = new UpdateEqpLayout(getTable.global.strTester_Num, ip);
				eqpInfo.insertEqpLayout();
				
				return 	strReturn;
				
			}catch(Exception ex){
				Utils.makeLog(strLotId+" [OPIRUS Main getOpirusLoad ERROR] "+ex);
				Utils.makeLog("ERROR",strLotId+" [OPIRUS Main getOpirusLoad ERROR] "+ex);
				strReturn = "Nodata";
				return strReturn;
			}finally{
				//new_Val.hs.clear();
				new_Val = null;				
			}
		}else{			
			Utils.makeLog(strLotId+" Socket No DATA..");
			strReturn = "Nodata";
			return strReturn;
		}
	}
	
}
