package hs.neo4j_hw.simple_api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

class Neo4jDriverConfig {
	
	private static final String CFG_FILE = "neo4j-driver-server.properties";
	private static final String CFG_FILE_OVERRIDE_DIR = "conf/";
	
	private final String scheme;
	private final String host;
	private final int port;
	private final String user;
	private final String password;
	
	Neo4jDriverConfig() throws IOException {
		// REFACTOR - better method for getting properties? Depends on the app container
		Properties neo4jProps = new Properties();
		File overrideCfgFile = new File(CFG_FILE_OVERRIDE_DIR, CFG_FILE);
		InputStream inputStream = null;
		if(overrideCfgFile.exists()) {
			// get the override from the conf directory
			inputStream = new FileInputStream(overrideCfgFile);
		} else {
			// get the default from the classpath
			inputStream = getClass().getClassLoader().getResourceAsStream(CFG_FILE);
			if(inputStream == null) {
				throw new FileNotFoundException("property file '" + CFG_FILE + "' not found in classpath");
			}
		}
		neo4jProps.load(inputStream);
		// TODO - Naive population of URI, user & password.  More robust configuration of URL for neo4j needed
		scheme = neo4jProps.getProperty("neo4j.driver.server.scheme", "bolt");   // can be "bolt+routing"
		host = neo4jProps.getProperty("neo4j.driver.server.host", "localhost");
		String portStr = neo4jProps.getProperty("neo4j.driver.server.port", "7687");
		port = Integer.parseInt(portStr);
		user = neo4jProps.getProperty("neo4j.driver.server.user", "neo4j");
		password = neo4jProps.getProperty("neo4j.driver.server.passwd");
		
	}
	
	public final String getUriString() {
		return scheme + "://" + host + ":" + Integer.toString(port);
	}
	
	public final String getUser() {
		return user;
	}
	
	public final String getPassword() {
		return password;
	}
	
	@Override
	public final String toString() {
		return getClass().getSimpleName() 
				+ ": URI=" + getUriString()
				+ ", user=" + user
				+ ", password=" + password;
	}

}
