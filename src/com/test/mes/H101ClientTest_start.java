package com.test.mes;

import com.test.opirus.PgmEquVariable;

import java.util.Scanner;

public class H101ClientTest_start {

	public static void main(String[] args) {
		H101ClientTest_start ct = new H101ClientTest_start();
		PgmEquVariable global  = new PgmEquVariable();
		if(ct.InitMsgHandler() == false){
			System.out.println("false");
			return ;
		}

		global = ct.ViewLot(global);
//		if(ct.ViewLot(global) == false)
//		{
//			ct.TermMsgHandler();
//			return ;
//		}
		
		ct.TermMsgHandler();
	}
	
	public boolean InitMsgHandler()
	{
		if (h101stub.getInstance().init("TEST_CLIENT", 2, 
				"12.230.54.47:10101", 10104) == false)
		{
			return false;
		}
		
		EISCaster.setEISChannel("/MPJY/EISServer");
		EISCaster.setEISTTL(60000);
		
		return true;
	}

		
	public boolean TermMsgHandler()
	{
        try 
        {
        	h101stub.getInstance().term();

		} 
        catch (Exception e) 
		{
			System.out.println(e.getMessage());
		}
		
		return true;
	}
	
	public PgmEquVariable ViewLot(PgmEquVariable global)
	{
//		PgmEquVariable global  = new PgmEquVariable();
		EISType.BETSEIS_Lot_Start_Req_In_Tag in = new EISType.BETSEIS_Lot_Start_Req_In_Tag();
//		Scanner scan = new Scanner(System.in);
//		in.h_factory = "HMKA1";
//		in.h_language = '2';
//		in.h_password = "1";
//		in.h_proc_step = '1';
//		in.h_user_id = "1070529";
//		in.lot_id = "PBPT080";
		in.h_passport = "";//""
		in.h_language=1;//1
		in.h_factory="HMBT1";//HMBT1
		in.h_user_id = global.strOperator;//4
		in.h_password="";
		in.h_proc_step=1; //1
		in.factory="HMBT1"; //HMBT1
		in.res_id="T2413A";
		in.lot_id=global.strLot_Id;
		in.test_mode="M";

		
		EISType.BETSEIS_Lot_Start_Req_Out_Tag out = new EISType.BETSEIS_Lot_Start_Req_Out_Tag();
		
		boolean flag = EISCaster.BETSEIS_Lot_Start_Req(in, out);
		
 
		
		System.out.println("h_status_value : " + out.h_status_value);
		System.out.println("h_msg_code : " + out.h_msg_code);
		System.out.println("h_msg : " + out.h_msg);
		System.out.println("lot_id : " + out.lot_id);
		System.out.println("mat_id : " + out.mat_id);
		System.out.println("flow : " + out.flow);
		System.out.println("qty : " + out.qty);
		global.strPartnumber = out.mat_id;
		return global; //flag;
	}
}
