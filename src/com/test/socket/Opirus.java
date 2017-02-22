	package com.test.socket;


	import com.test.opirus.Properties;
import com.test.util.Utils;

import java.io.File;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/**
 * Opirus Main Process
 * 
 * @author jaedeuk.ahn
 * @since 2014.01.21(THU)
 */

	// main Class
	public class Opirus {

		private ServerSocket server;
		//public static List list = Collections.synchronizedList(new ArrayList());
		public static HashMap<String, Thread> hs ;

		public Opirus(){		
			hs = new HashMap<String, Thread>();
			System.out.println("Process start");
			init();  
		}
		
		public void init(){
			
			try {
				Properties prop = new Properties();		
				prop.loadProperties("opirus.properties");
				server = new ServerSocket(prop.getSocketPort());
			
				while(true){
					
					
					Socket socket = server.accept();// ready for socket
					socket.setSoTimeout(60*100000 );
					InetAddress addr = socket.getInetAddress();  
					String ip=  String.valueOf(addr.getHostAddress());
				
					File LogFile = new File(prop.getBaseDir() + "/log" + "/" + "smart_opirus."+Utils.getDate("dd", 0));
					
					//log file lastModified date  info
					long fileDate = LogFile.lastModified();		
					String info =  Utils.getDateMillis("MMdd", fileDate);
					
					//now date
					String MonthDay = Utils.getDate("MMdd", 0);					
					Utils.makeLog(" lastModified=["+info+"]  MonthDay=["+MonthDay+"]");
					if(!info.equals(MonthDay)){						
						Utils.moveBackupLog(prop.getBaseDir() + "/log","smart_opirus."+Utils.getDate("dd", 0));
						Utils.moveBackupLog(prop.getBaseDir() + "/log","smart_opirus_Error."+Utils.getDate("dd", 0));
					}
					
					try{
						String[] tmpsplit = ip.split(":");
						ip = tmpsplit[0].replaceAll("/","");		
						Utils.makeLog("[Socket Received Ip] = [" + ip+"]");
						
						ServerHelper helper = new ServerHelper(socket,ip); // create work thread
				
						hs.put(ip, helper); // map in data : thread object
						helper.start(); // workthread start.
						
					}catch(Exception ex){					
						Utils.makeLog(Thread.activeCount() + " Get IP ERROR :" +ex +"  " + ip +" / " + String.valueOf(socket.getRemoteSocketAddress()));
						Utils.makeLog(Thread.activeCount() + " ERROR","Get IP ERROR :"+ex +"  " + ip +" / " + String.valueOf(socket.getRemoteSocketAddress()));
					}				
				}
				
			} catch (Exception e) {
				Utils.makeLog("Opirus Main ERROR : " + e);
				Utils.makeLog("ERROR"," Opirus Main ERROR : " + e);
			}
		}
		public static void main(String[] args) {
			System.out.println("Server Start!");
			new Opirus();
		}
	}
