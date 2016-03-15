package com.rwhitear.ucsdHttpRequest.headers;

import com.rwhitear.ucsdHttpRequest.contentTypes.ContentTypes;

public class UCSDHttpRequestHeaders {
	
	private String[] headerValues = new String[2];
	
	private String key, value;
	
	public String getContentTypeHeader(String contentTypeHeader){
		
		String httpContentType = new ContentTypes().getContentType(contentTypeHeader);
		
		if( httpContentType != "" ) {
			return httpContentType;
		} else {
			return "";
		}
	}
	
	public String[] setHeaderValues(String key, String value) {
		this.key = key;
		this.value = value;
		
		this.headerValues[0] = this.key;
		
		this.headerValues[1] = this.value;
		
		return this.headerValues;
	}
	
}
