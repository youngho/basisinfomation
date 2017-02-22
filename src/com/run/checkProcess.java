package com.run;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class checkProcess {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		checkProcess createMain = new checkProcess();
		createMain.work();
	}
	
	private void work(){
		System.out.println("Opirus Process checking..");	
		
		String pid = "";
		String sProcessName = "com.test.socket.Opirus";
		
		GetPid gpid = new GetPid(); 		
		pid = gpid.getPid(sProcessName);
		
		if( pid == null || pid.equals("")){			
			startProcess();
		}
		
		System.out.println("Opirus Process checking End.");
	}
	
	private void startProcess(){
		
		try {
			System.out.println("START Opirus Process ..");
			
			Process process = Runtime.getRuntime().exec("./startOpirus.bat");
			
			BufferedReader err = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			while (true) {
				String errLine = err.readLine();				
				if (errLine == null){
					break;
				}else{
					System.out.println(errLine);
				}
			}
			
			System.out.println("START Process End.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
