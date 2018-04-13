package com.guis.clienteftp;

import com.guis.clienteftp.interfaces.Observer;
import com.guis.clienteftp.interfaces.Subject;

public class PorcentajeObserver implements Observer{
	
	private FtpFileDownload ftpFileDownload;
	
	public PorcentajeObserver(FtpFileDownload ftpFileDownload) {

		this.ftpFileDownload = ftpFileDownload;
		
		ftpFileDownload.addObserver(this);
	}
	
	@Override
	public void update(Subject o) {
		if(o == ftpFileDownload) {
			System.out.printf("Porcentaje %.1f %% \n", ftpFileDownload.getPorcentaje());
		}
	}

}
