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
			//GZIPOutputStream outStream = new GZIPOutputStream(outStream); //압축할때 jar로 만들때..
	
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
						fileName = entry.getName().substring(entry.getName().lastIndexOf("/") + 1);	//이건 파일이 폴더째 저장되는 경우 폴더를 없애고 한 곳에 파일을 넣기 위해서 이다.. 삭제 가능..
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
