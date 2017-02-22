package com.test.tar;

import java.io.File;
import java.io.FileOutputStream;

import com.ice.tar.TarArchive;
import com.ice.tar.TarEntry;
import com.test.util.Utils;
public class CreateTar {
/**
* @author jaedeuk.ahn
* @since 2014.01.21(THU)	 
* import jar : tartool.jar
* make .tar file
* @param archiveFile
* @param tobeTarFiles
*/
	public boolean createTarArchive( File archiveFile, File[] tobeTarFiles ){
		boolean bRet = false;
	
		
		try{
			FileOutputStream outStream = new FileOutputStream(archiveFile);
			//GZIPOutputStream outStream = new GZIPOutputStream(outStream); //�����Ҷ� jar�� ���鶧..
	
			//int blockSize = TarBuffer.DEFAULT_BLKSIZE;
			int blockSize = 0;
			TarArchive archive = new TarArchive( outStream, blockSize );
			
			
			if( archive != null) {
				TarEntry entry = null;
				String fileName = null;
				
				for (int i = 0; i < tobeTarFiles.length; i++) {
					if (tobeTarFiles[i] == null || !tobeTarFiles[i].exists()|| tobeTarFiles[i].isDirectory())
						continue;
									
					// System.out.println(tobeTarFiles[i]);
					//Utils.makeLog("[" + tobeTarFiles[i].getName()+"]");
					
					entry = new TarEntry(tobeTarFiles[i]);
			
					if (entry.getName().lastIndexOf("/") > -1) {
						fileName = entry.getName().substring(entry.getName().lastIndexOf("/") + 1);	//�̰� ������ ����° ����Ǵ� ��� ������ ���ְ� �� ���� ������ �ֱ� ���ؼ� �̴�.. ���� ����..
						entry.setName(fileName);
					}
			
					archive.writeEntry(entry, true);
					
				}
		
				archive.closeArchive();
			}
	
			outStream.close();
			// System.out.println("Adding completed OK");			
			bRet = true;
						
		}catch (Exception e){
			Utils.makeLog("[CreateTar createTarArchive Error ]" + e);
			Utils.makeLog("ERROR","[CreateTar createTarArchive Error ]" + e);
			return false;
		}
		return bRet;
	 }
	 	 
}
