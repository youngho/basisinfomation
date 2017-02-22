package com.test.opirus;

/**
 * @author jaedeuk.ahn
 * @since 2014.01.21(THU)
 */

import com.test.tar.CreateTar;
import com.test.util.DaoImpl;
import com.test.util.Utils;

import java.io.File;
import java.io.FilenameFilter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;



public class PgmTimeChk {
	
	PgmNewVariable  new_Val = new PgmNewVariable();
	String strFtp_Flag="";
	String strPgm_Flag="";
	//String strChkSysdate="";
	String strQTar_Pgm ="";
	String tmpPgm ="";
	String tmpData;
	String tmpData2;
	String tmpData3;
	String tmpData4;
	File[] arrFS;
	ArrayList<File> tmpArry=new ArrayList<File>();
	ArrayList<File> tmpDateArry=new ArrayList<File>();
	String pgm_total_cnt ="";
    String pgm_change_cnt = "";
        
    
//	public void makeTar(Connection conn,PgmGetTable getTable, PgmEquVariable global, PgmStTime sttime, PgmRev  pgm_Load, PgmNewVariable  new_Val, PgmTimeChk Pgm_T_Chk, HandGetTable Hnad_Table, PgmBoard Board_Load, String strLotId){
    public void makeTar(PgmGetTable getTable, PgmEquVariable global, PgmRev  pgm_Load, PgmNewVariable  new_Val, PgmTimeChk Pgm_T_Chk, String strLotId){				
							
		Properties prop = new Properties();
		prop.loadProperties("opirus.properties");
		
		String strQry="";
		String strGetDate ="";
		String strGetSubPgm ="";
		String strGetProcess ="";
		String strGetPartnumber ="";
		String strChkSysdate="";
		
		String tmpDir="";	
		
		tmpDir = (getTable.global.strPartnumber);
		String tpPerDir =getTable.global.strTester;
		tmpDir = tpPerDir + "/"  + tmpDir;
		/*
		if(tpPerDir.startsWith("X8802")){
			tmpDir = "X8802/"+tmpDir; 			
		}else if(tpPerDir.equals("X8822")){ 				
			tmpDir = "X8824/"+tmpDir; 			
		}else if(tpPerDir.equals("X7100")){
			tmpDir = "X7100/"+tmpDir; 
		}else if(tpPerDir.equals("X8100")){
			tmpDir = "X8100/"+tmpDir; 
		}else if(tpPerDir.startsWith("T")){
			tmpDir = "advan/"+tmpDir; 
		}else if(tpPerDir.startsWith("A60")){
			tmpDir = "ando/"+tmpDir; 
		}else if(tpPerDir.equals("AG93K")){
			tmpDir = "agilent/"+tmpDir; 
		}else if(tpPerDir.startsWith("MAGNUM")){
			tmpDir = "nextest/"+tmpDir; 
		}else if(tpPerDir.equals("HA6500")){
			tmpDir = "hitachi/"+tmpDir; 
		}else if(tpPerDir.equals("X8200")){
			tmpDir = "X8200/"+tmpDir; 
		}else if(tpPerDir.equals("UFLEX")){
		 	tmpDir = "UFLEX/"+tmpDir; 
		}
		else if(tpPerDir.startsWith("MEBOST")){				
			tmpDir = "MEBOST/"+tmpDir; 
		}
		else if(tpPerDir.startsWith("X8842")){
			tmpDir = "X8842/"+tmpDir;
		}
		else if(tpPerDir.startsWith("EST3000")){
			tmpDir = "EST3000/"+tmpDir;
		}
		else if(tpPerDir.startsWith("MAGNUM3")){
			tmpDir = "MAGNUM3/"+tmpDir;
		}
		else{
			tmpDir = "advan/"+tmpDir; 
		}
		*/
		tmpPgm = getTable.new_Val.strQMain_Pgm + " "+getTable.new_Val.strQSubPgm;
		
					
		// SELECT SYS_DATE FROM LOTIN_INFO_HIST TABLE
		new_Val.strQTar_Pgm ="";
		try{			
			new_Val.strQTar_Pgm ="";	
			
			strQry = "SELECT SYS_DATE, UNI_SUBPGM, RCV_PROCESS PROCESS,   "
//					+" substring(RCV_PARTNUMBER,1,13) PARTNUMBER FROM LOTIN_INFO_HIST "	//mssql
					+" SUBSTR(RCV_PARTNUMBER,1,13) PARTNUMBER FROM LOTIN_INFO_HIST " //oracle
				+" WHERE SYS_DATE = "
//					+" ( "//mssql
//					+" 	SELECT TOP 1 SYS_DATE "//mssql
					+" ( SELECT SYS_DATE FROM (" //orcle
					+" 	SELECT SYS_DATE " //orcle
					+" FROM LOTIN_INFO_HIST "
					+" WHERE RCV_HMODEL='"+getTable.global.strH_Model+"'"
					+" AND RCV_TMODEL='"+getTable.global.strTester+"'"
					+" AND RCV_TESTER='"+getTable.global.strTester_Num+"'"
//					+" AND SYS_DATE >= replace(replace(replace(replace(convert(varchar(20),getdate()-7,121),'-',''),' ',''),':',''),'.','') " mssql
					+" AND SYS_DATE >= TO_CHAR(SYSDATE-7,'YYYYMMDDHH24MISS') " //oracle
					+" AND UNI_TABLE_EXIST <> 'NO' "
					+" AND TESTER_CHECK_UPDATE='Y' " 
					+" ORDER BY  SYS_DATE DESC  ,TESTER_CHECK_UPDATE DESC "
//					+" ) " //mssql
					+" ) WHERE ROWNUM <= 1)" //orcle
				+" AND RCV_HMODEL='"+getTable.global.strH_Model+"'"
				+" AND RCV_TMODEL='"+getTable.global.strTester+"'"
				+" AND RCV_TESTER='"+getTable.global.strTester_Num+"'";
			 
			ArrayList<String[]> preLotInList =  DaoImpl.selectQueryList(strQry);
			for( String[] arr : preLotInList){
				strGetDate = arr[0];
				strGetSubPgm = arr[1];				
				strGetProcess = arr[2];
				strGetPartnumber = arr[3];
			}
//			while( rs.next()){
//				strGetDate = rs.getString("SYS_DATE");
//				strGetSubPgm= rs.getString("UNI_SUBPGM");				
//				strGetProcess =rs.getString("PROCESS");
//				strGetPartnumber =rs.getString("PARTNUMBER");
//			}
			Utils.makeLog(strLotId+"   [Get Sysdate] LOTIN_INFO_HIST In =["+strGetDate +"]");
			
		}catch(Exception ex){
			Utils.makeLog("ERROR",strLotId+"   [PgmTimeChk makeTar GET MAX_SYSDATE ERROR] " + ex);
			Utils.makeLog("ERROR",strLotId+"   [PgmTimeChk makeTar ] SQL -> " + strQry);
			Utils.makeLog(strLotId+"   [PgmTimeChk makeTar GET MAX_SYSDATE ERROR] " + ex);
		}
		try{
			if (strGetDate.equals("") || strGetDate.equals(" ")){
				strGetDate = getTable.new_Val.strQsys_date;
				
				Utils.makeLog(strLotId+"   [Get Sysdate] LAST_TABLE In ["+strGetDate +"]");
			}			
		}catch(Exception nex){
			strGetDate = getTable.new_Val.strQsys_date;
		}
		strChkSysdate = strGetDate;
		//new_Val.strQDiff_date = strChkSysdate;
	//	new_Val.strQTar_Pgm = getTable.global.strTester_Num +"_"+ getTable.global.strHead +"_"+Utils.getDate("yyyyMMddHHmmss", 0)+".tar";
	
	/**
	 *  tmpDateArry :  SYS_DATE 이후 DATA 담겨있는  ArrayList.
	 *  tmpArry : FilenameFilter() 의 true 를 return 받은 file ArrayList.
	 *  tmpData : Main_Pgm
	 *  tmpData2 : SubPgm
	 *  tmpData3 : SubPgm parse 하여 * 이전의 data 
	 */
		tmpData = getTable.new_Val.strQMain_Pgm;
		tmpData2 = getTable.new_Val.strQSubPgm;
		Utils.makeLog(strLotId+"   Main = ["+tmpData+"]   Sub = ["+tmpData2+"]");
		File fl = null;		
		
		Utils.makeLog(strLotId+"   TEST_DIR = ["+prop.getFileDir()+"/"+tmpDir+"]");
		fl = new File(prop.getFileDir()+"/"+tmpDir);
				
		if (fl.exists()){
			
			File[] arrFS_sub= null;
			strQTar_Pgm = getTable.global.strTester_Num +"_"+ getTable.global.strHead +"_"+Utils.getDate("yyyyMMddHHmmss", 0)+".tar";
			new_Val.strQTar_Pgm =strQTar_Pgm;
			arrFS_sub=fl.listFiles(new FilenameFilter()
			{
				public boolean accept(File arg0,String arg1)
				{
					if(arg1.indexOf(tmpData) > -1)
					{
						return true;
					}
					else
					{
						return false;
					}
				}
			});
			
			// 2014.03.20 main pgm not exist check.
			int iMainpgmCnt = 0;
			for( int y = 0; y < arrFS_sub.length; y ++){
				if( arrFS_sub[y].exists() ){
					if(getTable.global.strTester.contains("MAGNUM")){
						if(arrFS_sub[y].getName().endsWith(".exe")){
							tmpArry.add(arrFS_sub[y]);  // Main_Pgm
						}
					}else if(getTable.global.strTester.contains("60")){
						if((arrFS_sub[y].getName().endsWith(".dev")) || (arrFS_sub[y].getName().endsWith(".cod"))){
							tmpArry.add(arrFS_sub[y]);  // Main_Pgm
						}
					}else{
						tmpArry.add(arrFS_sub[y]);  // Main_Pgm
					}
					iMainpgmCnt++;
				}
			}
			if( iMainpgmCnt > 0){
								
				arrFS_sub = null;
				/* SubPgm start */ 
				Gfil fi=new Gfil();
				Gfil2 fi2=new Gfil2();
				try{
					StringTokenizer strtoken1 = new StringTokenizer(tmpData2," ");
					if(tmpData2.trim() != ""){
						while(strtoken1.hasMoreTokens()){ // SubPgm filename list loop
							String tmpstrData = strtoken1.nextToken();	
							int tmpLen = (tmpstrData.replaceAll("\\*","")).length();
							if( tmpstrData.length() == tmpLen){
							
								if (tmpData.indexOf(".") == 0){
									tmpData3 = tmpstrData;
									tmpData4 = "";
								}else{
									tmpData3 = "";
									tmpData4 = tmpstrData;
								}
								arrFS_sub = fl.listFiles(fi);
								
								for( int y = 0; y < arrFS_sub.length; y ++){
									tmpArry.add(arrFS_sub[y]);
								}		
							}else if( tmpstrData.length() == tmpLen+1){
								
								StringTokenizer strtoken = new StringTokenizer(tmpstrData,"*");
								while(strtoken.hasMoreTokens()){
									if(strtoken.countTokens()==1){
										if(tmpstrData.indexOf("*")==0){
											tmpData3 = "";
											tmpData4 = strtoken.nextToken();
										}else if(tmpstrData.length()-1 ==tmpstrData.indexOf("*") ){
											tmpData3 = strtoken.nextToken();
											tmpData4 = "";
										}
									}else if(strtoken.countTokens()==2){
										tmpData3 = strtoken.nextToken();
										tmpData4 = strtoken.nextToken();
									}
									
									arrFS_sub = fl.listFiles(fi);
									
									for( int y = 0; y < arrFS_sub.length; y ++){
										tmpArry.add(arrFS_sub[y]);
									}			
								}// while end	
							}else{
								if(tmpstrData.indexOf("*") == 0){
									tmpData3="";
								}else{
									tmpData3 =  tmpstrData.substring(0, tmpstrData.indexOf("*"));
								}
								tmpData4 =  tmpstrData.substring(tmpstrData.lastIndexOf("*")+1, tmpstrData.length());
								
								File[] tmpFLit = null;
								if(tmpData3.equals("")){
									tmpFLit = fl.listFiles(fi2);
								}else{
									tmpFLit = fl.listFiles(fi);
								}
							
								String strFList="";
								for(int i = 0 ; i < tmpFLit.length; i++) {
									int iflag=0;
									 strFList = tmpFLit[i].getName();
									
									 String strFListsub =""; // file name
									 if(tmpstrData.indexOf("*") != 0){
										  strFListsub = strFList.substring(tmpstrData.indexOf("*"), strFList.length());
									 }else{
										 strFListsub = strFList;
									 }
								     strFListsub = strFListsub.substring(0, strFList.length()-tmpData4.length()-tmpstrData.indexOf("*"));
									
									String strtmp3 ="";
										strtmp3 =tmpstrData.substring(tmpstrData.indexOf("*"),tmpstrData.length() );// sub pgm name
										strtmp3 =strtmp3.substring(0,strtmp3.length() - tmpData4.length());
										strtmp3 = strtmp3.replaceAll("\\*", "");	
									
									int tmpindex =-1;
									for( int j =0; j < strtmp3.length(); j++){					
										String strSub =strtmp3.substring(j,j+1);
										if(strFListsub.indexOf(strSub) > tmpindex){
											strFListsub = strFListsub.substring(strFListsub.indexOf(strSub), strFListsub.length());
										}else{
											iflag=1;
										}
									}
									if(iflag==0){
										tmpArry.add(tmpFLit[i]);
									}		
								}
							}
						}//  SubPgm filename list  loop end
					}
				}catch(Exception ex){
					Utils.makeLog(strLotId+"   SubPgm ERROR~! "+ex);
					Utils.makeLog("ERROR",strLotId+"   SubPgm ERROR~! "+ex);
				}
			
							
				arrFS = new File[tmpArry.size()];
				for(int y =0 ; y < tmpArry.size(); y++){
					arrFS[y] = tmpArry.get(y);  // SubPgm 을  ArrayList 에 add 한다.
				}
							
				
				/**
				 *  GET TIME OVER File List  
				 *  From  arrFS[]
				 *   To   tmpDateArry
				 * loop count = arrFS.length(Filter Data) 
				 *  RETURN  .tar File & strPgm_Flag = "TAR_LOAD"   or     strPgm_Flag = "NOT_LOAD"
				 * */
				try{
					pgm_total_cnt =Integer.toString(arrFS.length);							
					
					Utils.makeLog(strLotId+"   Pre TarFileChk =["+getTable.global.strTarFileChk+"]  Tester Number =["+getTable.global.strTester_Num+"] Now TarFileChk =["+getTable.global.strTarFileChk+"]");
					
					
					// 2014.02.19 KGD : JUST TAR_LOAD
					strPgm_Flag = "TAR_LOAD";				
					if ((arrFS != null) && ( arrFS.length >0)){
						Tar_Create( arrFS ,strQTar_Pgm ,strLotId);								
					}
					
					/*2014.02.19
					if(getTable.global.strTmode.equals("NOT_LOAD")){
						
						Utils.makeLog(strLotId+"   Not Make Tar File :  TMODE = NOT_LOAD");
						strFtp_Flag = "NO";
						strPgm_Flag = "NOT_LOAD";
						
					}else{				
							
						if ((!getTable.global.strEx_Pgm.equals(getTable.new_Val.strQMain_Pgm)) 
								|| (!getTable.global.strEx_revno.equals(pgm_Load.new_Val.strQRevNo)) 
								|| (getTable.global.strTmode.equals("ENG")) ||(!getTable.global.strTarFileChk.equals("OK")) 
								|| (!getTable.new_Val.strQSubPgm.equals(strGetSubPgm))
							//	|| (!tmpProd.equals(strGetPartnumber))
							//	|| (!(strtmpProcess).equals(strGetProcess)) 
								|| (Long.parseLong(strGetDate) < Long.parseLong(getTable.new_Val.strQsys_date)
						))
						{
							strPgm_Flag = "TAR_LOAD";
							
							if ((arrFS != null) && ( arrFS.length >0)){
								Tar_Create( arrFS ,strQTar_Pgm ,strLotId);								
							}	
						}else
						{		
							long old_Modi;
							for(int i=0; i < arrFS.length ; i++){
								old_Modi = arrFS[i].lastModified();		
								String tmpCdata= Format_Date(old_Modi);						
								if(Long.parseLong(strGetDate) <= Long.parseLong(tmpCdata)){
									tmpDateArry.add(arrFS[i]);
								}					
							}
							
							if ((tmpDateArry.size() >0) )
							{
								Utils.makeLog(strLotId+"   [GetTarFile] File= ["+ strQTar_Pgm+"] File Check Ok");
								strPgm_Flag = "TAR_LOAD";
								if ((arrFS != null) && ( arrFS.length >0)){
									Tar_Create( arrFS ,strQTar_Pgm,strLotId );
								}else{
									String type = "";
									if( arrFS == null){
										type =  "[arrFS is null]";
									}else{
										type =  "[arrFS.length = "+ arrFS.length+"]";
									}
									Utils.makeLog(strLotId+"   [Creat TarFile Cancle] "+type);
								}
							}else{
								Utils.makeLog(strLotId+"   [GetTarFile] File= [Not Make Tar File] ");
								strFtp_Flag = "NO";
								strPgm_Flag = "NOT_LOAD";				
							}
							
						    pgm_change_cnt = Integer.toString(tmpDateArry.size());
							
							Utils.makeLog(strLotId+"   Total File Cnt = ["+pgm_total_cnt+"] Chang File Cnt = [" +pgm_change_cnt+ "]");
						}					
					}
				*/
					Utils.makeLog(strLotId+"   [TAR Check] Mode= ["+ getTable.global.strTmode +"]  Pre_Rev= ["+ getTable.global.strEx_revno +"] Now_Rev= ["+ pgm_Load.new_Val.strQRevNo +"]  Pre_Pgm= ["+ getTable.global.strEx_Pgm +"] Now_Pgm= ["+ getTable.new_Val.strQMain_Pgm +"]");
				}catch(Exception ex){
					Utils.makeLog("ERROR",strLotId+"   [ TIME_CHECK FROM FILE   ERROR] " +ex);
					Utils.makeLog(strLotId+"   [TIME_CHECK FROM FILE   ERROR] " +ex);
				}
			}else{
				Utils.makeLog(strLotId+"   [NOT MakeTar File] -> NOT FOUND MAIN PGM ");
				strQTar_Pgm="";
				getTable.global.strTableFlag = "NO";
			}
		}else{

			Utils.makeLog(strLotId+"   [NOT MakeTar File] ->  NOT Dir found=[" +prop.getFileDir()+"/"+tmpDir+"]");
			strQTar_Pgm="";
			getTable.global.strTableFlag = "NO";
		}
		
	}
	
	
	class Gfil implements FilenameFilter{
		public boolean accept(File  dir,String  name){
		  if(name.startsWith(tmpData3)&& name.endsWith(tmpData4)  )
			{
				return true;
			}
			else
			{
				return false;
			}
		}
	}	
	class Gfil2 implements FilenameFilter{
		public boolean accept(File  dir,String  name){
		  if( name.endsWith(tmpData4)  )
			{
				return true;
			}
			else
			{
				return false;
			}
		}
	}
	
	 public void Tar_Create(File[] fi, String fname, String strLotId)  {
			
		 CreateTar tar = new CreateTar();
		 Properties prop = new Properties();
		 prop.loadProperties("opirus.properties");
			
		 String tarFileName = prop.getTarDir()+"/"+fname;     //prop.getTarDir()  : tar파일 저장 경로..   fname: 압축파일 이름..
				 
		 try{
			 File file = new File(tarFileName);
			 File filePath = new File(prop.getTarDir());
			 if(!filePath.isDirectory()){
				 filePath.mkdirs();
			 }
			 if(tar.createTarArchive( file, fi)){
				 Utils.makeLog(strLotId+"   [TIME_CHECK Tar_Create File success ]" );
				/*
				 String command = "chmod 774 " + prop.getTarDir()+"/"+fname; 
				
				try {
					Process pr = Runtime.getRuntime().exec(command);
					new PrintStream(pr.getInputStream()).start();
					pr.waitFor();
					pr.exitValue();
				} catch (Throwable t) {
					//System.out.println("Caught " + t);
					 Utils.makeLog(strLotId+"   [TIME_CHECK Tar_Create File permission Error ] " +t +"["+command+"]");
					 Utils.makeLog("ERROR",strLotId+"   [TIME_CHECK Tar_Create File permission Error ] " +t +"["+command+"]");
				}			 
				*/
			 }else{
				 Utils.makeLog(strLotId+"   [TIME_CHECK Tar_Create fail] " );
				 Utils.makeLog("ERROR",strLotId+"   [TIME_CHECK Tar_Create fail] " );
				 
				 if( file.exists()){
					 file.delete();
				 }
			 }
			 File f =  new File(tarFileName);
			 Utils.makeLog(strLotId+"   [TIME_CHECK Tar_Create File] = "+file+" : "+file.length() +" File exists = ["+f.exists()+"]" );
						 
		 }catch(Exception ex){
			 Utils.makeLog("ERROR",strLotId+"   [TIME_CHECK Tar_Create ERROR] " +ex);
			 Utils.makeLog("ERROR",strLotId+"   [TIME_CHECK Tar_Create ERROR] fi.length :" +fi.length +"  tar name : " +fname);
			 Utils.makeLog(strLotId+"   [TIME_CHECK Tar_Create ERROR] " +ex);
		 }
	}	
	
	
	
	public String Format_Date(long date){
		String chgDate="";
			
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		chgDate = df.format(date);

		return chgDate;
	}
//	public String  set_Return(Connection conn,PgmGetTable getTable, PgmEquVariable global , 
//			PgmStTime sttime, PgmRev  pgm_Load, PgmNewVariable  new_Val, PgmTimeChk Pgm_T_Chk, 
//			HandGetTable Hnad_Table, PgmBoard Board_Load , PgmTempChk getTemp_Cal, String strLotId) {
	public String  set_Return(PgmGetTable getTable, PgmEquVariable global , 
			PgmRev  pgm_Load, PgmNewVariable  new_Val, PgmTimeChk Pgm_T_Chk, 
			String strLotId) {
		
		String strTarPgm="";
		String line = null;
		
		try{
			
			
			/*
			 * SMART PART ID -> SEC PART ID 
			 */
			PartIdCheck partcheck = new PartIdCheck();
			String chgPart = "";
			/*cho LAST_TABLE_PN2M 없는 테이블 GET chgPart 주석처리 01_21*/
			/*cho LAST_TABLE_PN2M 테이블 생성 chgPart 주석해제 01_30*/
			chgPart = partcheck.changePartId(strLotId, getTable.global.strPartnumber);
			Utils.makeLog("getPgmInfo PART ID CHANGE - SMART ["+ getTable.global.strPartnumber +"] , SEC ["+chgPart+"]");
			String strSECPartnumber = chgPart;
			
			
			if ( strPgm_Flag.equals("NOT_LOAD")){
				strTarPgm = "NOT_LOAD";
			}else{
				strTarPgm = Pgm_T_Chk.strQTar_Pgm;
			}
			String tmpMask="";
			if((getTable.new_Val.strQMask_Flow).equals("Y")){
				tmpMask = getTable.new_Val.strQMask_Code;
			}			
			if(pgm_Load.new_Val.strQRevNo == null){
				pgm_Load.new_Val.strQRevNo="";
			}
			if(getTable.new_Val.strQSubPgm.equals(" ")){
				getTable.new_Val.strQSubPgm="";
			}

			if (getTable.new_Val.strTmsUse != null && !getTable.new_Val.strTmsUse.equals("OK")){
				getTable.new_Val.strTmsUse="NO";
			}
						
			try{
				
				if(getTable.global.strTableFlag !=null && getTable.global.strTableFlag.equals("NO")){
					Utils.makeLog(strLotId+"   [PgmTimeChk tar Check] >>> strTableFlag=["+getTable.global.strTableFlag+"]");
					//getTable.global.strPL_PgmChkResult = "NO_REGISTOR";
				}
				
				Properties prop = new Properties();
				prop.loadProperties("opirus.properties");
				File tarF;
				
				if ( !strTarPgm.equals("NOT_LOAD")){
					//tarF = new File(prop.getTarDir()+"/size_test.tar");
					tarF = new File(prop.getTarDir()+"/"+Pgm_T_Chk.strQTar_Pgm);
					Utils.makeLog(strLotId+"   [PgmTimeChk tar Check] >>> FILE=["+tarF+"]   tarF.exists()=["+tarF.exists()+"]   tarF.length()=["+tarF.length()+"]");
				}else{
					tarF = null;
					Utils.makeLog(strLotId+"   [PgmTimeChk tar Check] >>> NOT_LOAD");
				}
				
			
									
				if( tarF != null &&  tarF.exists() && tarF.length() > 0 ){
					getTable.global.strPL_PgmChkResult = "";						
				}else{						
					if(tarF != null  &&  tarF.exists() && tarF.length() == 0  ){
						getTable.global.strTableFlag = "NO";
						getTable.global.strPL_PgmChkResult = "SIZE_ERR";
					}
					if(tarF != null &&  tarF.exists() && tarF.length() == 0){
						if(tarF.delete()){
							if( !tarF.exists())
								Utils.makeLog(strLotId+"   [PgmTimeChk tar Check -> DELETE SUCCESS] ");
							else
								Utils.makeLog(strLotId+"   [PgmTimeChk tar Check -> DELETE FAIL..] ");
						}else{
							Utils.makeLog(strLotId+"   [PgmTimeChk tar Check -> DELETE FAIL!!!!   ##############  ] ");
						}
					}
				}					
			}catch(Exception e){
				Utils.makeLog("ERROR",strLotId+"   [PgmTimeChk set_Return ] PGM_CHK_RESULT  ERROR \n "+e+"\n ***************************");
				Utils.makeLog(strLotId+"   [PgmTimeChk set_Return ERROR #100] PGM_CHK_RESULT ERROR  >>> "+e);
			}
			
			
	        line = "<MSG>\n"
	        	+"<SYSTEM>"+getTable.global.strSystem+"</SYSTEM>\n"
	        	+"<CMD>"+getTable.global.strCmd+"</CMD>\n"
	        	+"<PGM_TABLE>"+getTable.global.strTableFlag+"</PGM_TABLE>\n"
//	        	+"<HANDLER_TABLE>"+Hnad_Table.Hand_Val.strHstrFlag+"</HANDLER_TABLE>\n" 
	        	//+"<HANDLER_TABLE></HANDLER_TABLE>\n"
	        	
	        	+"<PGM_FLAG>"+strPgm_Flag+"</PGM_FLAG>\n"
	        	+"<TAR_PGM>"+strTarPgm+"</TAR_PGM>\n"
	        	
	        	+"<REV_NO>"+pgm_Load.new_Val.strQRevNo+"</REV_NO>\n"
	        	+"<PARTNUMBER>"+strSECPartnumber+"</PARTNUMBER>\n"
	        	+"<SMART_PARTNUMBER>"+getTable.global.strPartnumber+"</SMART_PARTNUMBER>\n"
	        	
	        	+"<PROCESS>"+getTable.new_Val.strQProcess+"</PROCESS>\n"
	        	+"<TEMP>"+getTable.new_Val.strQTemp+"</TEMP>\n"
	        	+"<TEMP_LIMIT>"+getTable.new_Val.strQTemp_Limit+"</TEMP_LIMIT>\n"
	        	+"<PARA>"+getTable.new_Val.strQSpecial_PARA+"</PARA>\n"
	        	//+"<ST_TIME>"+sttime.new_Val.strQST_Time+"</ST_TIME>\n"
	        	//+"<ST_REAL>"+sttime.new_Val.strQST_Real+"</ST_REAL>\n"
	        	//+"<BOARD>"+Board_Load.new_Val.strBoard_Tema+"</BOARD>\n"	        	
	        	//+"<PKG_TYPE_MATCH>"+Board_Load.new_Val.strPkgTypeMatch+"</PKG_TYPE_MATCH>\n"
	        		        	
	        	+"<GLOBAL>"+getTable.new_Val.strQGlobal+"</GLOBAL>\n"
	        	+"<MAIN_PGM>"+getTable.new_Val.strQMain_Pgm+"</MAIN_PGM>\n"
	        	+"<PGM_CNT>"+getTable.new_Val.strQPgmCnt+"</PGM_CNT>\n"
	        	+"<SUB_PGM>"+getTable.new_Val.strQSubPgm.replaceAll("\\n","")+"</SUB_PGM>\n"
	        	
//	        	+"<MODE>"+Hnad_Table.global.strHmlct_mode+"</MODE>\n"
//	        	+"<SMODULE>"+Hnad_Table.Hand_Val.strHsmodule+"</SMODULE>\n"
	        	//+"<MODE></MODE>\n"
	        	//+"<SMODULE></SMODULE>\n"
	        	
	        	+"<MASK>"+tmpMask+"</MASK>\n"
	        	//+"<TEMP_CAL>"+getTemp_Cal.new_Val.strQTemp_Cal+"</TEMP_CAL>\n"
	        	+"<TMS_USING>"+getTable.new_Val.strTmsUse+"</TMS_USING>\n"
	        	;
	        
	        	if(getTable.global.strTester.contains("MAGNUM")){
	        					        	
		        	try{
		        		if( (getTable.new_Val.strQRts_Flow== null) ||((getTable.new_Val.strQRts_Flow).equals("null"))){
		        			getTable.new_Val.strQRts_Flow ="";
		        		}
		        	}catch(Exception er){ 
		        		getTable.new_Val.strQRts_Flow ="";
		        	}
		        	try{
		        		if( (getTable.new_Val.strQPrime_H_Yield== null) ||((getTable.new_Val.strQPrime_H_Yield).equals("null"))){
		        			getTable.new_Val.strQPrime_H_Yield ="";
		        		}
		        	}catch(Exception er){ 
		        		getTable.new_Val.strQRts_Flow ="";
		        	}
		        	line=line+"<RTS_FLOW>"+getTable.new_Val.strQRts_Flow+"</RTS_FLOW>\n"
		        	+"<PRIME_H_YIELD>"+getTable.new_Val.strQPrime_H_Yield+"</PRIME_H_YIELD>\n";
	        	}
	        			        	
	        	//line = line +"<OP_PARTNUMBER>"+getTable.new_Val.strQPartnumber+"</OP_PARTNUMBER>\n";

			line = line +"<PGM_CHK_RESULT>"+ getTable.global.strPL_PgmChkResult +"</PGM_CHK_RESULT>\n";

			line = line +"<FAB>"+ getTable.global.strFab +"</FAB>\n";
			line = line +"<GRADE>"+ getTable.global.strGrade +"</GRADE>\n";
			line = line +"<QTY>"+ getTable.global.strQty +"</QTY>\n";


			//line = line +"<2UI_USE>"+getTable.new_Val.strMultiUI_Flag+"</2UI_USE>\n";
								
	        	line = line +"</MSG>\n";
	        System.out.println(line);
	        return line;
		}catch(Exception ex){
			Utils.makeLog("ERROR",strLotId+"   [PgmTimeChk set_Return ERROR #1] "+ex);
			Utils.makeLog("ERROR",strLotId+"   [PgmTimeChk set_Return ] CREATE DATA \n "+line);
			Utils.makeLog(strLotId+"   [PgmTimeChk set_Return ERROR #1] "+ex);
			return "";
		}
	}
					
}
/*
class PrintStream extends Thread 
{
	java.io.InputStream __is = null;
	public PrintStream(java.io.InputStream is) 
	{
		__is = is;
	} 

	public void run() 
	{
		try 
		{
			while(this != null) 
			{
				int _ch = __is.read();
				if(_ch != -1) 
					System.out.print((char)_ch);
				else break;
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		} 
	}
}
*/
