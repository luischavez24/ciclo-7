package com.guis.clienteftp;

import java.io.IOException;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class DownloadObserverTest {
	public static void main(String[] args) {
		
		try {
			
			FTPClient ftp = ConexionFTP.getFTP().getClient();
			List<FTPFile> files = ConexionFTP.getFTP().getFilesOnRoot(ftp);
			ConexionFTP.getFTP().closeClient(ftp);
			
			files.forEach(ftpFile -> {
			
				Thread t =  new FtpFileDownload(ftpFile);
				t.start();

			});
			
		} catch (IOException e) {
			e.printStackTrace();
		} 

	}
}
