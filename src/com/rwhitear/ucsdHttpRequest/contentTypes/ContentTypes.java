package com.rwhitear.ucsdHttpRequest.contentTypes;

public class ContentTypes {

	private String contentType = "";
	
	private String[][] supportedContentTypes = { 
			{"xml","application/xml"}, 
			{"json","application/json"} 
	};
	
	public String getContentType(String contentType) {
		this.contentType = contentType;	
		
		for( int i=0; i < supportedContentTypes.length; i++ ) {
			
			if( this.contentType == supportedContentTypes[i][0] ) {
				
				return supportedContentTypes[i][1];
				
			}
		
		}
		
		return "";
	}
}
