package com.rwhitear.ucsdHttpRequest;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.protocol.Protocol;

import com.rwhitear.ucsdHttpRequest.constants.*;
import com.rwhitear.ucsdHttpRequest.headers.UCSDHttpRequestHeaders;
import com.rwhitear.ucsdHttpRequest.socketFactories.MySSLSocketFactory;

/**
 * 
 * This class is the main entry point for this library. Use the methods here for
 * HTTP(S) connection setup and control.
 * 
 * @author Russ Whitear (rwhitear@cisco.com)
 *
 * @version 1.0
 * 
 */
public class UCSDHttpRequest {

	/**
	 * IP address of target system.
	 */
	private String ipAddress = "";
	
	/**
	 * Target system access protocol. 
	 * 
	 *     "http"  - Unencrypted HTTP Request.
	 *     "https" - Encrypted HTTPS Request (Not necessarily over TCP port 443).
	 *    
	 * Defaults to "http".
	 */
	private String protocol = HttpRequestConstants.PROTOCOL_HTTP;
	
	/**
	 * TCP port with which to access the target system. Defaults to port 80.
	 */
	private int port = HttpRequestConstants.TCP_PORT_HTTP;
	
	/**
	 * Username for use if HTTP authentication is required.
	 */
	private String username = "";
	
	/**
	 * Password for use if HTTP basic authentication is required.
	 */
	private String password = "";
	
	/**
	 * URI of the request. Defaults to "/".
	 */
	private String uri = "/";
	
	/**
	 * An ArrayList of HTTP Content-Type headers in the form "Content-Type: application/json" etc.
	 */
	private String contentTypeHeader = "";

	/**
	 * Cookie policy.
	 */
	private String cookiePolicy = "default";
	
	
	private ArrayList<String[]> requestHeaders = new ArrayList<String[]>();
	
	private int statusCode;
	
	private String httpResponse;
	
	private String methodType = HttpRequestConstants.METHOD_TYPE_GET;
	
	private String bodyText = "";
	
	
	// Constructors.
	

	/**
	 * Default constructor.
	 */
	public UCSDHttpRequest() {
		
	}
	
	/**
	 * 
	 * @param ipAddress IP address of target system.
	 * @param protocol Transport protocol of target system ("http" or "https").
	 */
	public UCSDHttpRequest(String ipAddress, String protocol) {
		this.ipAddress = ipAddress;
		this.protocol  = protocol;
	}
	
	/**
	 * 
	 * @param ipAddress IP address of target system.
	 * @param protocol Transport protocol of target system ("http" or "https").
	 * @param username Username for HTTP basic authentication.
	 * @param password Password for HTTP basic authentication.
	 */
	public UCSDHttpRequest(String ipAddress, String protocol, String username, String password) {
		this.ipAddress = ipAddress;
		this.protocol  = protocol;
		this.username = username;
		this.password = password;
	}
	
	/**
	 * 
	 * @param ipAddress IP address of target system.
	 * @param protocol Transport protocol of target system ("http" or "https").
	 * @param port TCP port for target system (Not necessarily 80 or 443).
	 */
	public UCSDHttpRequest(String ipAddress, String protocol, int port) {
		this.ipAddress = ipAddress;
		this.protocol  = protocol;
		this.port = port;
	}
	
	/**
	 * 
	 * @param ipAddress IP address of target system.
	 * @param protocol Transport protocol of target system ("http" or "https").
	 * @param port TCP port for target system (Not necessarily 80 or 443).
	 * @param username Username for HTTP basic authentication.
	 * @param password Password for HTTP basic authentication.
	 */
	public UCSDHttpRequest(String ipAddress, String protocol, int port, String username, String password) {
		this.ipAddress = ipAddress;
		this.protocol  = protocol;
		this.port = port;
		this.username = username;
		this.password = password;
	}
	
	
	/**
	 * 
	 * @param headerType Attempt to add an HTTP Content-Type header string. Currently supported
	 *        values are "xml" and "json".
	 */
	public void addContentTypeHeader(String headerType) {
		
		String cth = new UCSDHttpRequestHeaders().getContentTypeHeader(headerType);
		
		if( cth != "" ) {
			this.contentTypeHeader = cth;
		} 
		
	}

	
	@SuppressWarnings("deprecation")
	public void execute() throws HttpException, IOException {
		
		// Setup the HTTP(S) Connection.
		HttpClient client = new HttpClient();
		
		if( this.protocol == "https" ) {
			
			System.out.println("SSL Connection requested...");
			
			Protocol.registerProtocol("https", new Protocol("https", new MySSLSocketFactory(), this.port));
		
		}
			
		client.getHostConfiguration().setHost(this.ipAddress, this.port, this.protocol);
			
		client.getParams().setCookiePolicy(this.cookiePolicy);
		
		if( methodType == HttpRequestConstants.METHOD_TYPE_GET ) {
			
			GetMethod clientMethod = new GetMethod(this.uri);
		
			clientMethod.addRequestHeader("Content-Type", this.contentTypeHeader);
		
			for( String[] iter : this.requestHeaders ) {
			
				clientMethod.addRequestHeader(iter[0], iter[1]);
			
			}
		
			client.executeMethod(clientMethod);
		
			this.statusCode = clientMethod.getStatusCode();
		
			this.httpResponse = clientMethod.getResponseBodyAsString();
		
			clientMethod.releaseConnection();
			
		} else if(methodType == HttpRequestConstants.METHOD_TYPE_POST ) {
			
			PostMethod clientMethod = new PostMethod(this.uri);
			
			clientMethod.setRequestEntity(new StringRequestEntity(this.bodyText));
			
			clientMethod.addRequestHeader("Content-Type", this.contentTypeHeader);
			
			for( String[] iter : this.requestHeaders ) {
			
				clientMethod.addRequestHeader(iter[0], iter[1]);
			
			}
		
			client.executeMethod(clientMethod);
		
			this.statusCode = clientMethod.getStatusCode();
		
			this.httpResponse = clientMethod.getResponseBodyAsString();
		
			clientMethod.releaseConnection();

		} else if(methodType == HttpRequestConstants.METHOD_TYPE_PUT ) {
			
			PutMethod clientMethod = new PutMethod(this.uri);
			
			clientMethod.setRequestEntity(new StringRequestEntity(this.bodyText));
			
			clientMethod.addRequestHeader("Content-Type", this.contentTypeHeader);
			
			for( String[] iter : this.requestHeaders ) {
			
				clientMethod.addRequestHeader(iter[0], iter[1]);
			
			}
		
			client.executeMethod(clientMethod);
		
			this.statusCode = clientMethod.getStatusCode();
		
			this.httpResponse = clientMethod.getResponseBodyAsString();
		
			clientMethod.releaseConnection();

		} else if(methodType == HttpRequestConstants.METHOD_TYPE_DELETE ) {
			
			DeleteMethod clientMethod = new DeleteMethod(this.uri);
			
			clientMethod.addRequestHeader("Content-Type", this.contentTypeHeader);
			
			for( String[] iter : this.requestHeaders ) {
			
				clientMethod.addRequestHeader(iter[0], iter[1]);
			
			}
		
			client.executeMethod(clientMethod);
		
			this.statusCode = clientMethod.getStatusCode();
		
			this.httpResponse = clientMethod.getResponseBodyAsString();
		
			clientMethod.releaseConnection();

		}
		
	}
	
	
	/**
	 * @return ipAddress Returns the IP address of the target system.
	 */
	public String getIpAddress() {
		return ipAddress;
	}

	/**
	 * @param ipAddress Sets the IP address of the target system.
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	/**
	 * @return protocol Returns the access protocol for the target system ("http" or "https").
	 */
	public String getProtocol() {
		return protocol;
	}

	/** 
	 * @param protocol Sets the access protocol for the target system ("http" or "https").
	 */
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	/**
	 * @return port Returns the TCP port for the target system (Not necessarily 80 or 443).
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port Sets the TCP port for the target system (Not necessarily 80 or 443).
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * @return Returns the username if HTTP basic authentication is required.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username Sets the username if HTTP basic authentication is required.
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/** 
	 * @return Returns the password if HTTP basic authentication is required.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password Sets the password if HTTP basic authentication is required.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return - an ArrayList of HTTP Content-Type headers.
	 */
	public String getContentTypeHeader() {
		return contentTypeHeader;
	}

	/**
	 * @param contentTypeHeaders An ArrayList of HTTP Content-Type Header strings. 
	 * 
	 * E.g. "Content-Type: application/json".
	 */
	public void setContentTypeHeaders(String contentTypeHeader) {
		this.contentTypeHeader = contentTypeHeader;
	}
	
	/**
	 * @return cookiePolicy - Cookie policy for request.
	 */
	public String getCookiePolicy() {
		return cookiePolicy;
	}

	/**
	 * @param cookiePolicy Cookie policy to set for request. Default is "default".
	 */
	public void setCookiePolicy(String cookiePolicy) {
		this.cookiePolicy = cookiePolicy;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public ArrayList<String[]> getRequestHeaders() {
		return requestHeaders;
	}

	public void addRequestHeaders(String key, String value) {
		
		String[] newHeader = new UCSDHttpRequestHeaders().setHeaderValues(key,value);
		
		requestHeaders.add(newHeader);
		
	}

	public int getStatusCode() {
		return statusCode;
	}

	public String getHttpResponse() {
		return httpResponse;
	}

	public String getMethodType() {
		return methodType;
	}

	public void setMethodType(String methodType) {
		this.methodType = methodType;
	}

	public String getBodyText() {
		return bodyText;
	}

	public void setBodyText(String bodyText) {
		this.bodyText = bodyText;
	}

}
