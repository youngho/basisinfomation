package com.test.opirus;

/**
 * @author jaedeuk.ahn
 * @since 2014.01.21(THU)
 */

import com.test.util.DaoImpl;
import com.test.util.Utils;

public class UpdateEqpLayout {

	String strTester ;
	String strIp ;
	
	public UpdateEqpLayout(String tname, String ip){
		this.strTester = tname;
		this.strIp = ip;
	}

	public void insertEqpLayout(){
		String strQry = "";
		
		try {
			strQry =" SELECT COUNT(*) CNT  FROM EQUIPMENT_LAYOUT  "
					+" WHERE TESTER ='"+ strTester +"'";
				
			int count =0;
			
			count = DaoImpl.selectQueryInt(strQry);
			
			if( count < 1){
				strQry ="INSERT INTO EQUIPMENT_LAYOUT(IP, TESTER) VALUES('"+strIp+"','"+strTester+"')";
			}else{
				strQry ="UPDATE EQUIPMENT_LAYOUT SET IP='"+strIp+"' WHERE TESTER='"+strTester+"'";
			}
			DaoImpl.excuteUpdate(strQry);
		} catch (Exception e) {			
			Utils.makeLog("[UpdateEqpLayout ERROR] "+e );
        	Utils.makeLog("ERROR", "[UpdateEqpLayout ERROR] "+e +" \n "+strQry);  
		}
			
	}
}
