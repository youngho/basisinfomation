package com.run;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class GetPid {
	
	public String getPid(String sProcessName){
		
		ArrayList<String> input = new ArrayList<String>();
		
		String line;
		String pid = "";
		try {
			Process process = Runtime.getRuntime().exec("jps -l");
			BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
			System.out.println("Checking Process..");
			while((line = in.readLine()) != null){
				input.add(line);
				String[] arrLine = line.split(" ");
				if(arrLine.length == 2){
					if( arrLine[1] .equals(sProcessName)){ // Find Process ID
						pid = arrLine[0];
						System.out.println("PID = "+ pid);						
						break;
					}
				}			
			}
			
			BufferedReader err = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			while (true) {
				String errLine = err.readLine();				
				if (errLine == null){
					break;
				}else{
					System.out.println(errLine);
				}
			}
			process.waitFor();
			if( pid == null || pid.equals("")){
				System.out.println("Can not find PID.");
			}
			System.out.println("Checking Process End.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pid;
	}
}
