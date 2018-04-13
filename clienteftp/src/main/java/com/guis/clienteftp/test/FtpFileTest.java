package com.guis.clienteftp.test;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import com.guis.clienteftp.FtpFileDownload;

public class FtpFileTest {
	static final String SERVIDOR = "www.peru-software.com";
    static final int PUERTO = 21;
    static final String USUARIO = "pp20172@peru-software.com";
    static final String CLAVE = "fisi20172";

    static FTPClient ftp = new FTPClient();
    
    public static void main(String[] args) {
        try {
            // Conectando al servidor
            ftp.connect(SERVIDOR, PUERTO);
            // Iniciando sesion
            ftp.login(USUARIO, CLAVE);

            ftp.enterLocalPassiveMode();

            // Entrando a modo pasivo
            //ftp.enterLocalPassiveMode();
            /**
             * Aproximacion 1
             */
            // Localizaciones de los archivos
            /*String archivoRemoto = "/so01.pptx";
            String archivoLocal = "introduccion.pptx";
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            
            OutputStream  outputStream = new BufferedOutputStream(new FileOutputStream(archivoLocal));
            
            boolean exito = ftp.retrieveFile(archivoRemoto, outputStream);
            Logger.getLogger(FtpFileDownload.class.getName()).log(Level.INFO, exito ? "Descarga exitosa": "Descaga fallida" );
             */
            /**
             * Aproximacion 2
             */
            
            String archivoRemoto2 = "/so01.pptx";
            String archivoLocal2 = "introduccion2.pptx";

            FTPFile[] archivosDirectorio = ftp.listFiles(archivoRemoto2);
            System.out.println(archivosDirectorio.length);
            for (FTPFile archivo : archivosDirectorio) {
                System.out.println(archivo.getName() + "-" + archivo.isDirectory());
            }

            if (archivosDirectorio.length == 1) {
                
                FTPFile archivoDescargando = archivosDirectorio[0];
                long sizeArchivo = archivoDescargando.getSize();
                
                ftp.setFileType(FTP.BINARY_FILE_TYPE);
                OutputStream outputStream2 = new BufferedOutputStream(new FileOutputStream(archivoLocal2));
                InputStream inputStream = ftp.retrieveFileStream(archivoRemoto2);
                byte[] archivoEnBytes = new byte[4096];

                int byteRead = -1;
                
                int totalActual = 0;
                
                double porcentaje;
                
                while ((byteRead = inputStream.read(archivoEnBytes)) != -1) {
                    
                    outputStream2.write(archivoEnBytes, 0, byteRead);
                    
                    totalActual += byteRead;
                    
                    porcentaje = (totalActual * 1.0 )/sizeArchivo * 100;
                    
                    System.out.printf("%d/%d, PORCENTAJE: %.1f \n", totalActual, sizeArchivo, porcentaje);

                }

                boolean exito2 = ftp.completePendingCommand();
                Logger.getLogger(FtpFileDownload.class.getName()).log(Level.INFO, exito2 ? "Descarga exitosa" : "Descaga fallida");
                outputStream2.close();
                inputStream.close();
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
}
