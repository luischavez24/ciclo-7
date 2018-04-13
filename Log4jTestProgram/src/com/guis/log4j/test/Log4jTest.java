package com.guis.log4j.test;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Log4jTest {
	
	public static Logger LOG = Logger.getLogger(Log4jTest.class);
	
	public static void main(String[] args) {
		PropertyConfigurator.configure("log4j.properties");
		
		LOG.debug("This is a debug message");
		LOG.info("This is a info message");
		LOG.warn("This is a warning");
		LOG.error("This is a error message");
		LOG.fatal("This is fatal message");
	}
	
}
