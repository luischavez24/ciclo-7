package com.guis.clienteftp;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class ConexionFTP {
	private static ConexionFTP conexionFTP;
	private static Semaphore downloadSemaphore;
	
	static {
		downloadSemaphore = new Semaphore(3);
	}
	// Constantes del servidor FTP
	private static final String SERVIDOR = "www.peru-software.com";
	private static final int PUERTO = 21;
	private static final String USUARIO = "pp20172@peru-software.com";
	private static final String CLAVE = "fisi20172";

	
	private ConexionFTP () {
		
	}
	
	public static ConexionFTP getFTP() {
		if(conexionFTP == null) {
			synchronized(ConexionFTP.class) {
				if(conexionFTP == null) {
					conexionFTP = new ConexionFTP();
				}
			}
		}
		
		return conexionFTP;
	}
	
	public FTPClient getClient() {
		System.out.println("Quedan " + downloadSemaphore.availablePermits() + " clientes disponibles");
		FTPClient ftp;
		
		//if(downloadSemaphore.tryAcquire()) {
			try {
				
				downloadSemaphore.acquire();
				// Conectando al servidor
				ftp = new FTPClient();
				ftp.connect(SERVIDOR, PUERTO);
				// Iniciando sesion
				ftp.login(USUARIO, CLAVE);
				// Entramos a modo pasivo para evitar problemas con el firewall
				ftp.enterLocalPassiveMode();

				// Establecemos el tipo de archivo en binario
				ftp.setFileType(FTP.BINARY_FILE_TYPE);
			} catch (Exception  e) {
				ftp = null;
				e.printStackTrace();
			}
			
			return ftp;
		//}
		
		//return null;
	}
	
	public List<FTPFile> getFilesOnRoot(FTPClient ftp) throws IOException {
		return Arrays.asList(ftp.listFiles());
	}
	
	public void closeClient(FTPClient ftp) {
		if (ftp.isConnected()) {
			try {
				ftp.logout();
				ftp.disconnect();
				
			} catch (IOException ex) {
				Logger.getLogger(FtpFileDownload.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		
		downloadSemaphore.release();
	}
}
