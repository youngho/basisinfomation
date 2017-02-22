package com.test.util;

/**
 * @author jaedeuk.ahn
 * @since 2014.01.21(THU)
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.StringTokenizer;

import com.test.opirus.Properties;

public class Utils {

	public static int NOTIME = 0;
	public static int TIMEONLY = 1;
	public static int DATEONLY = 2;
	public static int DATETIME = 3;
	public static int NORMAL = 4;
	
	private Calendar cal;
	private int iYear;
	private int iMonth;
	private int iDate;
	Utils(){
		cal=Calendar.getInstance();
		iYear=0;
		iMonth=0;
		iDate=0;
	}

	/**
	 * 로그 생성 
	 */
	public static void writeLog(String astrDir, String astrPrefix,
			String astrMsg) {
		writeLog(astrDir, astrPrefix, astrMsg, DATETIME, true);
	}

	/**
	 * 
	 */
	public static void writeLog(String astrDir, String astrPrefix,
			String astrMsg, int aiTimeMode) {
		writeLog(astrDir, astrPrefix, astrMsg, aiTimeMode, true);
	}

	/**
	 * Log 파일 생성
	 */
	public static void writeLog(String astrDir, String astrPrefix,
			String astrMsg, int aiTimeMode, boolean abCR) {
		BufferedWriter bWriter;
		File fFile;

		SimpleDateFormat dateformat = new SimpleDateFormat();
		dateformat.applyPattern("yyyyMMdd");
		String strDay = dateformat.format(new Date());
		dateformat.applyPattern("dd");
		String strMonth = dateformat.format(new Date());
		dateformat.applyPattern("yyyy/MM/dd");
		String strDate = dateformat.format(new Date());
		dateformat.applyPattern("HH:mm:ss");
		String strTime = dateformat.format(new Date());

		try {
			fFile = new File(astrDir);
			if (fFile.exists() == false)
			{
				fFile.mkdirs();
			}

			bWriter = new BufferedWriter(new FileWriter(astrDir + "/"
					+ astrPrefix + "."+ strMonth, true));
			if (aiTimeMode == TIMEONLY)
				bWriter.write("[" + strTime + "] ");
			else if (aiTimeMode == DATEONLY)
				bWriter.write("[" + strDay + "] ");
			else if (aiTimeMode == NORMAL)
				bWriter.write("                      ");			
			else if (aiTimeMode == DATETIME)
				bWriter.write("[" + strDate + " " + strTime + "] ");
			bWriter.write(astrMsg);
			if (abCR == true)
				bWriter.newLine();
			bWriter.close();
		} catch (IOException ex) {
			ex.printStackTrace();		
		}
	}


	public static void writeLog(String astrPrefix, String astrMsg) {
		writeLog(astrPrefix, astrMsg, DATETIME, true);
	}

	
	public static void writeLog(String astrPrefix, String astrMsg,
			int aiTimeMode) {
		writeLog(astrPrefix, astrMsg, aiTimeMode, true);
	}

	public static void writeLog(String astrPrefix, String astrMsg,
			int aiTimeMode, boolean abCR) {
		BufferedWriter bWriter;
		Properties prop = new Properties();
		prop.loadProperties("opirus.properties");

	//	File fFile;

		SimpleDateFormat dateformat = new SimpleDateFormat();
		dateformat.applyPattern("yyyyMMdd");
		String strDay = dateformat.format(new Date());
		dateformat.applyPattern("dd");
		String strMonth = dateformat.format(new Date());
		dateformat.applyPattern("yyyy/MM/dd");
		String strDate = dateformat.format(new Date());
		dateformat.applyPattern("HH:mm:ss");
		String strTime = dateformat.format(new Date());

		try {			
			//fFile = new File(prop.getPGMBaseDir()+"."+strMonth);			

			bWriter = new BufferedWriter(new FileWriter(prop.getBaseDir()
					+ astrPrefix + "."+ strMonth, true));
			if (aiTimeMode == TIMEONLY)
				bWriter.write("[" + strTime + "] ");
			else if (aiTimeMode == DATEONLY)
				bWriter.write("[" + strDay + "] ");
			else if (aiTimeMode == NORMAL)
				bWriter.write("                      ");				
			else if (aiTimeMode == DATETIME)
				bWriter.write("[" + strDate + " " + strTime + "] ");
			bWriter.write(astrMsg);
			if (abCR == true)
				bWriter.newLine();
			bWriter.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Write Log
	 * @param sMsg
	 */	
	public static void makeLog(String sMsg) {

		Properties prop = new Properties();
		prop.loadProperties("opirus.properties");
		
		try {
			File sFile = new File(prop.getBaseDir()+"/log");
			if(!sFile.isDirectory()){
				sFile.mkdirs();
			}
			
			if(sMsg.replaceAll( " " , "" ).equals("")){				
				writeLog(prop.getBaseDir() + "/log", "smart_opirus",
						sMsg, 4);
			}else{				
				writeLog(prop.getBaseDir() + "/log", "smart_opirus",sMsg);
			}			
		} catch (Exception ie) {			
			writeLog(prop.getBaseDir() + "/log", "smart_opirus","[ERR]" + ie);
		}
	}		
	
	public static void makeLog(String sType,String sMsg) {

		Properties prop = new Properties();
		prop.loadProperties("opirus.properties");
		File sFile = new File(prop.getBaseDir()+"/errLog");
		if(sFile.exists()){
			sFile.mkdirs();
		}
		try {
			if(sMsg.replaceAll( " " , "" ).equals("")){				
				writeLog(prop.getBaseDir() + "/log", "smart_opirus_Error",
						sMsg, 4);
			}else{				
				writeLog(prop.getBaseDir() + "/log", "smart_opirus_Error",sMsg);
			}			
		} catch (Exception ie) {			
			writeLog(prop.getBaseDir() + "/log", "smart_opirus_Error","[ERR]" + ie);
		}
	}		
	
	/**
	 * 
	 * @param offset
	 * @return
	 */
	public static String getDate(String pattern, int offset) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, offset);
		String strRet = sdf.format(cal.getTime());

		return strRet;
	}

	/**
	 * 
	 * @param date
	 * @param pattern
	 * @param offset
	 * @return
	 */
	public static String getDate(String date, String pattern, int offset) {
		int iYear;
		int iMonth;
		int iDate;
		String strRet;

		iYear = Integer.parseInt(date.substring(0, 4));
		iMonth = Integer.parseInt(date.substring(4, 6)) - 1;
		iDate = Integer.parseInt(date.substring(6, 8));
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Calendar cal = Calendar.getInstance();
		cal.set(iYear, iMonth, iDate);
		cal.add(Calendar.DATE, offset);

		strRet = sdf.format(cal.getTime());
		return strRet;
	}

	/**
	 * 
	 * @param astrDate
	 * @return
	 */
	public static int getDayOfWeek(String astrDate) {
		int iYear;
		int iMonth;
		int iDate;

		iYear = Integer.parseInt(astrDate.substring(0, 4));
		iMonth = Integer.parseInt(astrDate.substring(4, 6)) - 1;
		iDate = Integer.parseInt(astrDate.substring(6, 8));

		Calendar cal = Calendar.getInstance();
		cal.set(iYear, iMonth, iDate);
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * IP, 호스트 네임이 있는지 확인 후 Return
	 * 
	 * @return
	 */
	public static String getMachineName() {
		String strHost;
		try {
			strHost = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException ex) {
			strHost = "unknown";
		}
		return strHost;
	}

	public static String getCurrentTime(String TimeMode) {
		Calendar today = Calendar.getInstance();
		SimpleDateFormat dateformat = new SimpleDateFormat(TimeMode);
		String day = dateformat.format(today.getTime());
		return day;
	}
	
	
	/* 4000 Byte 미만씩 잘라준다 */
	public static String strLength(String arg1) {
		String ngsub = null;
		String ngsub2 = null;

		if (arg1.getBytes().length >= 4000) {
			int src = arg1.substring(0, 4000).lastIndexOf(",");
			ngsub = arg1.substring(0, src + 1);
			ngsub2 = arg1.substring(src + 1, arg1.getBytes().length);
		}
		return ngsub + "-" + ngsub2;
	}
	
	public void setDate(String date)
	{
		if(date.length()==8)
		{
			iYear=Integer.parseInt(date.substring(0,4));
			iMonth=Integer.parseInt(date.substring(4,6));
			iDate=Integer.parseInt(date.substring(6,8));
		}
		else
		{
			iYear=Integer.parseInt(date.substring(0,2));
			iMonth=Integer.parseInt(date.substring(2,4));
			iDate=Integer.parseInt(date.substring(4,6));
			iYear+=2000;
		}
			cal.set(iYear,iMonth-1,iDate);

	}
	public String getDate(int iAmount)
	{
		SimpleDateFormat sdformat=new SimpleDateFormat("yyyyMMdd");
		String strRtn="";

		cal.add(Calendar.DATE,iAmount);

		strRtn=sdformat.format(cal.getTime());
		return strRtn;
	}
	
	public static void moveBackupLog(String directory,String fname){
		
		File dir = new File(directory+"/log_back");
    	if (!dir.isDirectory())
        {
    		dir.mkdirs();
        }
    	
    	File backFile = new File(directory+"/"+fname);
    	
    	if(!backFile.renameTo(new File(directory+"/log_back/"+fname))){
    		Utils.makeLog("LOG BACK UP FAIL..");	  	
    	}
	  	
	}
	
	public static long getTimeDiff(String date1,String date2){

		long strPre =0 ;
		  try{
			  	Date frDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(date1);
				Date toDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(date2);
				
				long diffMil = toDate.getTime() - frDate.getTime();
				
				long diffSec = diffMil/1000;
				strPre = diffSec;
		  }catch(NullPointerException nex){
			  strPre=0;
		  }catch(Exception ex){
			  Utils.makeLog("getTimeDiff ERROR:"+ex +"  date1=["+date1+"]   date2=["+date2+"]");
			  strPre=0;
		  }
		  return strPre;		 
	}
	
	public static String getValue(HashMap<String, String> hs, String key){
		String value = "";
		try{
			value = hs.get(key);
			if( value == null) value = "";
			if( value.equals("null")) value = "";
			
		}catch(Exception ee){
			value="";
		}
		
		return value;
	}
	public static ArrayList<String> StringtoList(String str, String delimiter){
		ArrayList<String> returnList = new ArrayList<String>();
		
		try{
			StringTokenizer st= new StringTokenizer( str, delimiter);
			while( st.hasMoreElements()){
				returnList.add(st.nextToken());
			}
		}catch (Exception e) {
			returnList = null;
		}
		
		
		return returnList;
	}
	public static String getDate(String date, String pattern, String pattern2) {
		
		String strRet="";

		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		SimpleDateFormat sdf2 = new SimpleDateFormat(pattern2);
		
		try {
			Date dt = (Date) sdf.parse(date);			
			strRet = sdf2.format(dt);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		return strRet;
	}
	
	//날짜형태가 Millis로 나타낸걸 pattern 형식으로 변경
	public static String getDateMillis(String pattern, long longMillis) {
		Calendar cal = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		cal.setTimeInMillis(longMillis);
		String strRet = sdf.format(cal.getTime());

		return strRet;
	}
}
