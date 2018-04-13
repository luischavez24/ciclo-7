package com.guis.clienteftp;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import com.guis.clienteftp.interfaces.Observer;
import com.guis.clienteftp.interfaces.Subject;

/**
 * 
 * @author guis
 */
public class FtpFileDownload implements Subject{

	private List<Observer> observadores;
	private double porcentaje;
	
    static final String SERVIDOR = "www.peru-software.com";
    static final int PUERTO = 21;
    static final String USUARIO = "pp20172@peru-software.com";
    static final String CLAVE = "fisi20172";

    static FTPClient ftp = new FTPClient();
    
    public FtpFileDownload() {
    	this.porcentaje = 0.0;
    	this.observadores = new ArrayList<>();
    }
    
    public void download(String nombreRemoto, String nombreLocal) {

        try {
        	
            // Conectando al servidor
            ftp.connect(SERVIDOR, PUERTO);
            // Iniciando sesion
            ftp.login(USUARIO, CLAVE);

            ftp.enterLocalPassiveMode();

            FTPFile[] archivosDirectorio = ftp.listFiles(nombreRemoto);
      
            if (archivosDirectorio.length == 1) {
                
                FTPFile archivoDescargando = archivosDirectorio[0];
                
                long sizeArchivo = archivoDescargando.getSize();
                
                ftp.setFileType(FTP.BINARY_FILE_TYPE);
                
                try (OutputStream outputStream2 = new BufferedOutputStream(new FileOutputStream(nombreLocal));
                	 InputStream inputStream = ftp.retrieveFileStream(nombreRemoto)) {
                
                	byte[] buffer = new byte[4096];

                    int byteRead = -1, totalActual = 0;
                    
                    while ((byteRead = inputStream.read(buffer)) != -1) {
                        
                        outputStream2.write(buffer, 0, byteRead);
                        
                        totalActual += byteRead;
                        
                        porcentaje = (totalActual * 1.0 ) / sizeArchivo * 100;
                        
                        notifyObservers();

                    }

                    boolean exito = ftp.completePendingCommand();
                    
                    Logger.getLogger(FtpFileDownload.class.getName()).log(Level.INFO, exito ? "Descarga exitosa" : "Descaga fallida");
                    
                }

            } else {
                System.out.println("ERROR");
            }

        } catch (IOException ex) {
        	
            Logger.getLogger(FtpFileDownload.class.getName()).log(Level.SEVERE, null, ex);
            
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.logout();
                    ftp.disconnect();
                } catch (IOException ex) {
                    Logger.getLogger(FtpFileDownload.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

	@Override
	public void addObserver(Observer o) {
		observadores.add(o);
	}

	@Override
	public void removeObserver(Observer o) {
		observadores.remove(o);
	}

	@Override
	public void notifyObservers() {
		observadores.forEach( obs -> obs.update(this));
	}

	public double getPorcentaje() {
		return porcentaje;
	}
}
