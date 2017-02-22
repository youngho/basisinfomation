package com.test.opirus;

/**
 * @author jaedeuk.ahn
 * @since 2014.01.21(THU)
 */

import java.io.*;
import java.util.StringTokenizer;

import com.test.util.Utils;

public class Properties {

	String mstrBaseDir;
	String mstrDbDriver;
	String mstrDbUrl;
	String mstrDbName;
	String mstrDbUser;
	String mstrDbPass;
		
	int mstrSocketPort;
	int mstrSocketTimeout;
	int mstrMsgBufSize;
	
	int mConnectTOut;
	int mDataTOut;
	
	String mstrFileDir;
	String mstrTarDir;
	String mstrQaFileDir;
	
	public Properties()
	{
		mstrBaseDir="";
		mstrDbDriver="";
		mstrDbUrl="";
		mstrDbUser="";
		mstrDbPass="";
		mConnectTOut=10000;
		mDataTOut=10000;
		
		mstrSocketPort=0;
		mstrSocketTimeout=10000;
		mstrMsgBufSize=1024;
		mstrFileDir="";
		mstrTarDir= "";
		mstrQaFileDir= "";
		
	}

	public String getBaseDir()
	{
		return mstrBaseDir;
	}

	public void setBaseDir(String dir)
	{
		mstrBaseDir = dir;
	}

	public String getDbDriver()
	{
		return mstrDbDriver;
	}

	public void setDbDriver(String driver)
	{
		mstrDbDriver = driver;
	}

	public String getDbUrl()
	{
		return mstrDbUrl;
	}

	public void setDbUrl(String url)
	{
		mstrDbUrl = url;
	}

	public String getDbName()
	{
		return mstrDbName;
	}

	public void setDbName(String name)
	{
		mstrDbName = name;
	}
	
	public String getDbUser()
	{
		return mstrDbUser;
	}

	public void setDbUser(String user)
	{
		mstrDbUser = user;
	}

	public String getDbPass()
	{
		return mstrDbPass;
	}

	public void setDbPass(String pass)
	{
		mstrDbPass = pass;
	}


	public int getSocketPort()
	{
		return mstrSocketPort;
	}

	public void setSocketPort(String port)
	{
		mstrSocketPort = Integer.parseInt(port);
	}
	public int getSocketTimeout()
	{
		return mstrSocketTimeout;
	}

	public void setSocketTimeout(String time)
	{
		mstrSocketTimeout = Integer.parseInt(time);
	}	
	public int getMsgBufSize()
	{
		return mstrMsgBufSize;
	}
	public void setMsgBufSize(String size)
	{
		mstrMsgBufSize = Integer.parseInt(size);
	}
	
	public String getFileDir()
	{
		return mstrFileDir;
	}
	public void setFileDir(String dir)
	{
		mstrFileDir = dir;
	}
	
	public String getQaFileDir()
	{
		return mstrQaFileDir;
	}
	public void setQaFileDir(String dir)
	{
		mstrQaFileDir = dir;
	}
	
	
	public String getTarDir()
	{
		return mstrTarDir;
	}
	public void setTarDir(String dir)
	{
		mstrTarDir = dir;
	}
	
	
	
	public boolean loadProperties(String filename)
	{
		
    	BufferedReader bfr=null;
    	String _rdbuf;
    	StringTokenizer _strtok;
    	String _strtoken;
    	String host,currentDir,propfile;

    	if(filename.equals(""))
    		return false;
    	
    	host=Utils.getMachineName();
    	
    	currentDir=System.getProperty("user.dir");
    	if(currentDir.endsWith("classes"))
    	{
    		propfile=currentDir+"/../conf/"+filename;
    	}
    	else
    	{
    		propfile=currentDir+"/conf/"+filename;
    	}
    	 //System.out.println("currentDir="+currentDir);
    	try
    	{
    		
    		bfr=new BufferedReader(new FileReader(propfile));
            while((_rdbuf=bfr.readLine())!=null)
            {
            	
                _strtok=new StringTokenizer(_rdbuf," =\t\r\n");
              
                while(_strtok.countTokens()==2)
                {
                	 if(host.contains("BRZ-D20171"))
                    {
                		 _strtoken=_strtok.nextToken();
                       
                        if(_strtoken.equals("JD_AHN_BASE_DIR"))
                        {
                            setBaseDir(_strtok.nextToken());
                        }
                        else if(_strtoken.equals("DB_DRIVER"))
                        {
                        	setDbDriver(_strtok.nextToken());
                        }
                        else if(_strtoken.equals("DB_URL"))
                        {
                        	setDbUrl(_strtok.nextToken());
                        }
                        else if(_strtoken.equals("DB_NAME"))
                        {
                        	setDbName(_strtok.nextToken());
                        }
                        else if(_strtoken.equals("DB_USER"))
                        {
                        	setDbUser(_strtok.nextToken());
                        }
                        else if(_strtoken.equals("DB_PASS"))
                        {
                        	setDbPass(_strtok.nextToken());
                        }                        
                        else if(_strtoken.equals("SOCKET_PORT"))
                        {
                        	setSocketPort(_strtok.nextToken());
                        }
                        else if(_strtoken.equals("SOCKET_TIMEOUT"))
                        {
                        	setSocketTimeout(_strtok.nextToken());
                        }
                        else if(_strtoken.equals("MESSAGE_BUFFER_SIZE"))
                        {
                        	setMsgBufSize(_strtok.nextToken());
                        }
                        else if(_strtoken.equals("JD_AHN_FILE_DIR"))
                        {
                        	setFileDir(_strtok.nextToken());
                        }
                        else if(_strtoken.equals("JD_AHN_TRA_DIR"))
                        {
                        	setTarDir(_strtok.nextToken());
                        }else if(_strtoken.equals("JD_AHN_FILE_DIR"))
                        {
                        	setFileDir(_strtok.nextToken());
                        }
                        else if(_strtoken.equals("JD_AHN_PGM_FILE_DIR_QA"))
                        {
                        	setQaFileDir(_strtok.nextToken());
                        }
                    }
                    else
                    {
                        _strtoken=_strtok.nextToken();
                        if(_strtoken.equals("BASE_DIR"))
                        {
                            setBaseDir(_strtok.nextToken());
                        }
                        else if(_strtoken.equals("DB_DRIVER"))
                        {
                        	setDbDriver(_strtok.nextToken());
                        }
                        else if(_strtoken.equals("DB_URL"))
                        {
                        	setDbUrl(_strtok.nextToken());
                        }
                        else if(_strtoken.equals("DB_NAME"))
                        {
                        	setDbName(_strtok.nextToken());
                        }
                        else if(_strtoken.equals("DB_USER"))
                        {
                        	setDbUser(_strtok.nextToken());
                        }
                        else if(_strtoken.equals("DB_PASS"))
                        {
                        	setDbPass(_strtok.nextToken());
                        }       
                        else if(_strtoken.equals("SOCKET_PORT"))
                        {
                        	setSocketPort(_strtok.nextToken());
                        }
                        else if(_strtoken.equals("SOCKET_TIMEOUT"))
                        {
                        	setSocketTimeout(_strtok.nextToken());
                        }
                        else if(_strtoken.equals("MESSAGE_BUFFER_SIZE"))
                        {
                        	setMsgBufSize(_strtok.nextToken());
                        }
                        else if(_strtoken.equals("PGM_FILE_DIR"))
                        {
                        	setFileDir(_strtok.nextToken());
                        }
                        else if(_strtoken.equals("PGM_TRA_DIR"))
                        {
                        	setTarDir(_strtok.nextToken());
                        }
                        else if(_strtoken.equals("PGM_FILE_DIR_QA"))
                        {
                        	setQaFileDir(_strtok.nextToken());
                        }
                    }
                }
            }
            
    	}
    	catch(FileNotFoundException ex)
    	{
    		Utils.writeLog(".", "Opirus", ex.getMessage());
    	}
    	catch (Exception ex)
    	{
    	}
    	finally
    	{
    		try
    		{
    			bfr.close();
    		}
    		catch(Exception sub_ex)
    		{
    			Utils.writeLog(".", "Opirus", sub_ex.getMessage());    			
    		}
    	}
    	return true;
	}
}
