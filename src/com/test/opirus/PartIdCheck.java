package com.test.opirus;

import java.sql.SQLException;
import java.util.ArrayList;

import com.test.util.DaoImpl;
import com.test.util.Utils;

public class PartIdCheck extends DaoImpl{

	public String changePartId(String strLotId, String partid){
		String rtnPartId = "";
		String sPart = "";
		int iPartLen=0;
		int iPrePartLen=0;
		String query = "SELECT PARTNUMBER,HPARTNUMBER FROM LAST_TABLE_PN2M WHERE '"+partid+"' LIKE HPARTNUMBER ";
		try {
			ArrayList< String[]> result = selectQueryList(query);
						
			if( result != null  && result.size() > 1){
				for(String[] s :  result){
					
					iPartLen = s[1].replaceAll("_", "").length();
					
					if( rtnPartId.length() == 0 || iPrePartLen < iPartLen ){					
						rtnPartId = s[0];
						sPart = s[1];
						iPrePartLen = sPart.replaceAll("_", "").length();
					}
				}
			}else{
				for(String[] s :  result){
					rtnPartId = s[0];
				}				
			}
		} catch (SQLException e) {
			try{
				Utils.makeLog("ERROR",strLotId+"   [PartIdCheck changePartId ERROR #1] "+e);
				Utils.makeLog("ERROR",strLotId+"   [PartIdCheck changePartId SQL] "+query);
				Utils.makeLog(strLotId+"   [PartIdCheck changePartId ERROR #1] " + e);
			}catch(Exception ec){
				Utils.makeLog("ERROR",strLotId+"   [PartIdCheck changePartId ERROR #2] "+ec);
				Utils.makeLog(strLotId+"   [PartIdCheck changePartId ERROR #2] " + ec);
			}
		}
		
		
		return rtnPartId;
	}
}
