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
 * Esta es la clase FTPFileDownload, se encarga de la descarga de un archivo
 * mediante el protocolo FTP. Implementa la clase Subject que le perimite ser
 * observada. Esto para que podamos monitorear el proceso de descarga del
 * archivo
 * 
 * @author guis
 */
public class FtpFileDownload implements Subject {

	// Variable que almacena a los observadores
	private List<Observer> observadores;
	// Almacena el porcentaje de la descarga
	private double porcentaje;

	// Constantes del servidor FTP
	private static final String SERVIDOR = "www.peru-software.com";
	private static final int PUERTO = 21;
	private static final String USUARIO = "pp20172@peru-software.com";
	private static final String CLAVE = "fisi20172";

	// FTPClient de apache-commons-net
	private static FTPClient ftp = new FTPClient();

	/**
	 * Constructor que inicializa el procentaje y la lista de observadores
	 */
	public FtpFileDownload() {
		this.porcentaje = 0.0;
		this.observadores = new ArrayList<>();
	}

	/**
	 * Este metodo permite descargar un archivo en un servidor FTP
	 * 
	 * @param nombreRemoto
	 *            Nombre del archivo que se desea descargar
	 * @param nombreLocal
	 *            Nombre que se le asignara al archivo al momento de descargarlo
	 */
	public void download(String nombreRemoto, String nombreLocal) {

		try {
			
			// Para que si no encuentra el archivo 
			// que mande descarga fallida tambien
			boolean exito = false;
			// Conectando al servidor
			ftp.connect(SERVIDOR, PUERTO);
			// Iniciando sesion
			ftp.login(USUARIO, CLAVE);
			// Entramos a modo pasivo para evitar problemas con el
			// firewall
			ftp.enterLocalPassiveMode();

			// Obtenemos toda la lista de los archivos en el servidor con el
			// nombre remoto
			FTPFile[] archivosDirectorio = ftp.listFiles(nombreRemoto);

			// Si el tamaño del arreglo es menor a 1 entonces existes el archivo
			if (archivosDirectorio.length == 1) {

				// Recuperamos el archivo descargado
				FTPFile archivoDescargando = archivosDirectorio[0];

				// Recuperamos su longitud
				long sizeArchivo = archivoDescargando.getSize();

				// Establecemos el tipo de archivo en binario
				ftp.setFileType(FTP.BINARY_FILE_TYPE);

				// Esta parte la cambie un poco
				// Use el try-resources para que ambos objetos se cierren al final de
				// de la descarga, se pueden poner varios elementos en el try-resources
				// primero el OutputStream y luego el InputStream por el este necesita
				// al otro

				try (OutputStream outputStream2 = new BufferedOutputStream(new FileOutputStream(nombreLocal));
						InputStream inputStream = ftp.retrieveFileStream(nombreRemoto)) {

					// Buffer de descarga
					byte[] buffer = new byte[4096];

					// Cantidad de bytes leidos en cada ciclo
					// Total actual acumulado de bytes descargados
					int byteRead = -1, totalActual = 0;

					while ((byteRead = inputStream.read(buffer)) != -1) {

						// Escribimos el flujo en el archivo de la maquina cliente
						outputStream2.write(buffer, 0, byteRead);

						// Incrementamos el total de bytes acumulados
						totalActual += byteRead;

						// Calculamos el porcentaje
						porcentaje = (totalActual * 1.0) / sizeArchivo * 100;

						// Y notificamos al observer
						notifyObservers();

					}

					exito = ftp.completePendingCommand();

				}
			}
		
			// Este mensaje ya iria despues y si no hay el archivo botara descarga fallida
			Logger.getLogger(FtpFileDownload.class.getName()).log(Level.INFO,
					exito ? "Descarga exitosa" : "Descaga fallida");
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
		observadores.forEach(obs -> obs.update(this));
	}

	public double getPorcentaje() {
		return porcentaje;
	}
}