package com.guis.clienteftp;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.io.IOException;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

// Frame de la aplicacion
public class DownloadObserverTest extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private JPanel root;
	
	public DownloadObserverTest() {
		
		super();
		setTitle("Acelerador de descargas");
		setBounds(0, 0, 400, 700);
		
		// Panel de las barras de progreso
		root = new JPanel();
		root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
		add(root);
		
		// Metodo que realiza la descarga
		performDownload();
	}
	
	// Igual al main pero con algunos agregados (*)
	private void performDownload() {
		
		try {
			
			FTPClient ftp = ConexionFTP.getFTP().getClient();
			List<FTPFile> files = ConexionFTP.getFTP().getFilesOnRoot(ftp);
			ConexionFTP.getFTP().closeClient(ftp);
			
			root.add(Box.createRigidArea(new Dimension(0,10))); // *
			
			files.forEach(ftpFile -> {
				
				FtpFileDownload downloadThread =  new FtpFileDownload(ftpFile);
				
				JProgressBar progressBar = new JProgressBar(); // *
				
				new PorcentajeObserver(downloadThread, progressBar); // *
				// Se agrego un parametro mas al constructor del observador para la barra de progreso
				
				// Config barra
				progressBar.setPreferredSize(new Dimension(400, 300)); // *
				progressBar.setMaximumSize(new Dimension(400, 250)); // *
				progressBar.setStringPainted(true); // *
				
				// Se agrega cada barra al panel
				root.add(progressBar);
				root.add(Box.createRigidArea(new Dimension(0,5)));
				
				// Inicia el hilo
				downloadThread.start();
			});
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame marco = new DownloadObserverTest();
				marco.setDefaultCloseOperation(EXIT_ON_CLOSE);
				marco.setVisible(true);
			}
		});
	}
}
