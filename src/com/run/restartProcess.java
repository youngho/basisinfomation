package com.run;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class restartProcess {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		restartProcess createMain = new restartProcess();
		createMain.work();
	}

	private void work(){
		System.out.println("START Opirus Process Restart..");		
				
		killProcess killProc = new killProcess();
		killProc.work();
		
		startProcess();
		System.out.println("START Opirus Process Restart End.");
	}
	
	private void startProcess(){
		try {
			System.out.println("START Opirus Process ..");	
			//Process process = Runtime.getRuntime().exec("D:\\Process\\OPIRUS\\bin\\start.bat");
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
