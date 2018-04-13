package com.guis.singleton.threadsafe;

import org.apache.log4j.Logger;

public class ThreadSafeSingleton {
	
	private static ThreadSafeSingleton INSTANCE;
	
	private static final Logger LOG = Logger.getLogger(ThreadSafeSingleton.class.getName());
	
	private ThreadSafeSingleton() { 
	}
	
	public static synchronized ThreadSafeSingleton getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new ThreadSafeSingleton();
			LOG.info("Se ha creado el singleton: " + Thread.currentThread().getName());
		}
		
		return INSTANCE;
	}
}
