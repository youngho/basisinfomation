package com.test.socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;

import com.test.util.Utils;

/**
 * @author jaedeuk.ahn
 * @since 2014.01.21(THU)
 */

public class ServerHelper   extends Thread{
	private Socket socket;
	private BufferedReader br;
	private BufferedWriter bw;
	private String ip;
	
	public ServerHelper(Socket s, String ip) throws IOException{
		this.socket = s;
		this.ip = ip;
		try{
			// create stream.
			br = new BufferedReader( new InputStreamReader( socket.getInputStream() ));
			bw = new BufferedWriter( new OutputStreamWriter( socket.getOutputStream() ) );			
	    }
	    catch(Exception e){
	    	Utils.makeLog(this.ip+" [ServerHelper run Error] "+ e);
			Utils.makeLog("ERROR",this.ip+" [ServerHelper run Error] "+ e);
	    }
	}
	
	public synchronized void run() {
		
		String tmpData1="";
		String tmpData="";
		String msg =""; 
		String strStartTime ="";
		com.test.opirus.OpirusInit main =  new com.test.opirus.OpirusInit();
		strStartTime = Utils.getDate("yyyy/MM/dd HH:mm:ss", 0);
		
		try {	
			while((msg = br.readLine())!=null){
				 tmpData += msg+"\n";
				if ( msg.contains("</MSG>" )){
					break;
				}			
			}
		
			Utils.makeLog(tmpData);	
			
			// return : socket data.. for send client 
			tmpData1 = main.getOpirusLoad(tmpData,strStartTime, ip);
			
			tmpData1 = tmpData1 +"\n";

			if (socket.isConnected()){
				bw.write(tmpData1);
				bw.flush();				
				Utils.makeLog("[Socket Send Data] "+String.valueOf(socket.getRemoteSocketAddress()) +"\n"+tmpData1);
			}else{
				Utils.makeLog("[Socket Connected ]" + socket.isConnected());				
			}
			bw.close();
			br.close();		
		
		}catch(SocketTimeoutException ex){
			//Thread.currentThread().interrupt();
			  Utils.makeLog(""+ socket.getRemoteSocketAddress() + "Socket close~! [ServerHelper SocketTimeoutException] "+ ex);
			  Utils.makeLog(""+ socket.getRemoteSocketAddress() + "ERROR","Socket close~! [ServerHelper SocketTimeoutException] "+ ex);
			  try{ 
				  this.socket.close();  
			  }catch(Exception ignored){ }
		
		}catch (Exception e) {
			if(e.getMessage().contains("InterruptedException")){
				 try{ 
					  this.socket.close();					  
				  }catch(Exception ignored){ }
				Utils.makeLog(this.ip+" [ServerHelper Interrupted] "+ e);
				Utils.makeLog("ERROR",this.ip+" [ServerHelper Interrupted] "+ e);
			}else{
				try{ 
					  this.socket.close();					  
				  }catch(Exception ignored){ }
				Utils.makeLog(this.ip+" [ServerHelper run Socket Send Error] "+ e);
				Utils.makeLog("ERROR",this.ip+" [ServerHelper run Socket Send Error] "+ e);
			}
		}finally{
			try{				
				if (bw != null ) bw.close();
				if (br != null ) br.close();
				
				try{
					if (this.socket != null){
						this.socket.close();
						this.socket = null;
					}
				}catch(Exception nex){nex.printStackTrace();}
				
				try{
					Opirus.hs.remove(this.ip);
					Thread.currentThread().interrupt();					
				}catch(Exception nex){
					Thread.currentThread().interrupt();	
				}
		      }catch(Exception ignored){
		    	  Utils.makeLog(Thread.activeCount() +"EA THREAD active  Filnally Exception~! " + ignored);
		      }
		    /* if(main.decisionTimeL > 200){
					Utils.makeLog(" [Err Code: 0001 : kill Process.]");
					Utils.makeLog("ERROR"," [Err Code: 0001 : kill Process.]");
					System.out.println(Utils.getDate("yyyy/MM/dd HH:mm:ss", 0) + " [Err Code: 0001 : kill Process.]");
					System.exit(1);
			  }
			  */
		}	
	}
	
}
