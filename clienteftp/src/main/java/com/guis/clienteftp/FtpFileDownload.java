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
public class FtpFileDownload  extends Thread implements Subject {

	// Variable que almacena a los observadores
	private List<Observer> observadores;

	public FTPFile file;
	
	private String nombreArchivo;
	
	// Almacena el porcentaje de la descarga
	private double porcentaje;

	/**
	 * Constructor que inicializa el procentaje y la lista de observadores
	 * 
	 * @param nombreRemoto
	 *            Nombre del archivo que se desea descargar
	 * @param nombreLocal
	 *            Nombre que se le asignara al archivo al momento de descargarlo
	 */
	
	public FtpFileDownload(FTPFile file) {
		super(file.getName());
		this.file = file;
		this.nombreArchivo = file.getName();
		this.porcentaje = 0.0;
		this.observadores = new ArrayList<>();
	}
	
	public FtpFileDownload(FTPFile file, String nombreArchivo) {
		super(file.getName());
		this.file = file;
		this.nombreArchivo = nombreArchivo;
		this.porcentaje = 0.0;
		this.observadores = new ArrayList<>();
	}
	
	@Override
	public void run() {
		try {
			new PorcentajeObserver(this);
			download();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Este metodo permite descargar un archivo en un servidor FTP
	 * @throws InterruptedException 
	 */
	public void download() throws IOException, InterruptedException {
		FTPClient ftp = ConexionFTP.getFTP().getClient();
		// Para que si no encuentra el archivo
		// que mande descarga fallida tambien
		boolean exito = false;

		long sizeArchivo = file.getSize();
		
		// Si el tamaño del arreglo es menor a 1 entonces existes el archivo
		if (file.isFile() && !file.getName().equals(".ftpquota")) {	

			try (InputStream inputStream  = ftp.retrieveFileStream(file.getName());
				 OutputStream outputStream2 = new BufferedOutputStream(new FileOutputStream(nombreArchivo))) {

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
			
			// Este mensaje ya iria despues y si no hay el archivo botara descarga fallida
			Logger.getLogger(FtpFileDownload.class.getName()).log(Level.INFO,
					(exito ? "Descarga exitosa - " : "Descaga fallida - ") + file.getName());
		}
		
		ConexionFTP.getFTP().closeClient(ftp);
		

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