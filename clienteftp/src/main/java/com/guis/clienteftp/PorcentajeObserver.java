package com.guis.clienteftp;

import javax.swing.JProgressBar;

import com.guis.clienteftp.constants.DownloadState;
import com.guis.clienteftp.interfaces.Observer;
import com.guis.clienteftp.interfaces.Subject;

public class PorcentajeObserver implements Observer{
	
	private FtpFileDownload ftpFileDownload;
	
	private JProgressBar progressBar;
	
	public PorcentajeObserver(FtpFileDownload ftpFileDownload, JProgressBar progressBar) {

		this.ftpFileDownload = ftpFileDownload;
		this.progressBar = progressBar;
		this.progressBar.setMinimum(0);
		this.progressBar.setMaximum(100);
		this.progressBar.setToolTipText(ftpFileDownload.getFile().getName());
		ftpFileDownload.addObserver(this);
	}
	
	@Override
	public void update(Subject o) {
		StringBuilder builder = new StringBuilder();
		
		if(o == ftpFileDownload) {
			
			builder.append("Archivo  " +  ftpFileDownload.getFile().getName() + " - " + ftpFileDownload.getEstado().getMsj());
			
			if(ftpFileDownload.getEstado() == DownloadState.DESCARGANDO) {
				
				Double progressValue = ftpFileDownload.getPorcentaje();
				
				progressBar.setValue(progressValue.intValue());
				
				builder.append(String.format(" %.2f %%", ftpFileDownload.getPorcentaje()));
			}
			
			System.out.println(builder.toString());

		}
	}

}
