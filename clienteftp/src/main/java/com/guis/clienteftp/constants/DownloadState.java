package com.guis.clienteftp.constants;

// Enumeracion de los estados posibles durante la descarga con un mensaje para cada uno
public enum DownloadState {
	
	INICIANDOSE("La descarga se está iniciando"),
	DESCARGANDO("Descargando ..."),
	EXITO("Descarga completada"),
	ERROR("Descarga fallida");
	
	private final String msj;
	
	DownloadState(String msj) {
		this.msj = msj;
	}

	public String getMsj() {
		return msj;
	}	
}
