package com.tapi.tester;

import java.io.*;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
/**
 * Test Tooling API
 *
 */
public class AppConfig {
	
	private InputStream inputStream;
	private Properties prop;
	private final String propFileName = "config.properties";
 	
	public String getUsername() throws IOException {
		return getPropValue("username");
	}
	
	public String getPassword() throws IOException {
		return getPropValue("password");
	}
	
	public String getDataEndpoint() throws IOException {
		return getPropValue("endpoint") + "services/Soap/u/" + getPropValue("apiVersion");
	}
	
	public String getToolingEndpoint() throws IOException {
		return getPropValue("endpoint") + "services/Soap/T/" + getPropValue("apiVersion");
	}
	
	public String getSessionId() throws IOException {
		return getPropValue("sessionId");
	}

	private String getPropValue(String param) throws IOException {
		Properties prop = getProperties();
		return prop.getProperty(param);

	}
	
	private Properties getProperties() throws IOException {
		prop = new Properties();
		try {
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
 
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
 
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			inputStream.close();
		}
		return prop;
	}
	
	public byte[] getZip() throws IOException {
		String s = "hello world";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);
        try {
            ZipEntry entry = new ZipEntry("test.txt");
            zos.putNextEntry(entry);
            zos.write(s.getBytes());
            zos.closeEntry();
        } catch (IOException ioe) {
        	System.out.println(ioe.getMessage());
        }
        return baos.toByteArray();
	}
}