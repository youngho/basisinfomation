package com.run;
import java.io.IOException;


public class killProcess {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		killProcess createMain = new killProcess();
		createMain.work();
		
		
	}
	
	public void work(){
		System.out.println("START Opirus Process kill..");		
		String sProcessName = "com.test.socket.Opirus";
		String pid = "";
		
		GetPid gpid = new GetPid(); 
			
		pid = gpid.getPid(sProcessName);
		stopProcess(pid);
		
		System.out.println("Work End.");
	}

	
	
	private void stopProcess(String pid){
		try {
			Process process = Runtime.getRuntime().exec("taskkill /f /pid "+pid);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
