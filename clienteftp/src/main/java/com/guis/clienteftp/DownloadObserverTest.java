package com.guis.clienteftp;

public class DownloadObserverTest {
	public static void main(String[] args) {
		
		FtpFileDownload ftpFileDownload = new FtpFileDownload();
		
		PorcentajeObserver porcetanjeObserver = new PorcentajeObserver(ftpFileDownload);
		
		ftpFileDownload.download("/so01.pptx", "intro-observer.pptx");
	}
}
