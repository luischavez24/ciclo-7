package com.guis.singleton.threadsafe;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class ThreadSafeTest {
	
	private static final Logger LOG = Logger.getLogger(ThreadSafeTest.class.getName());
	
	public static void main(String[] args) {
		
		PropertyConfigurator.configure("log4j.properties");
		
		for (int i = 0; i < 10; i++) {
			Thread hilo = new Thread(new Runnable() {
				
				@Override
				public void run() {
					LOG.info("HILO: " + Thread.currentThread().getName() + ", " + ThreadSafeSingleton.getInstance());
					
				}
			});
			
			hilo.start();
		}
	}
}
