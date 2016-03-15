package main;

import java.io.IOException;

import org.apache.commons.httpclient.HttpException;

import com.rwhitear.ucsdHttpRequest.UCSDHttpRequest;
import com.rwhitear.ucsdHttpRequest.constants.HttpRequestConstants;

public class TestMain {

	public static void main(String[] args) throws HttpException, IOException {
		
		UCSDHttpRequest request = new UCSDHttpRequest("10.113.89.25","https", 5392);
		
		request.addContentTypeHeader(HttpRequestConstants.CONTENT_TYPE_JSON);
		
		//request.addRequestHeaders("X-Cloupia-Request-Key", "1234567890");
		
		request.setUri("/v1/tokens");
		
		request.setMethodType("POST");
		
		request.setBodyText("{\"data\":{\"username\":\"apiuser\",\"password\":\"C1sco123\"}}");
		
		request.execute();
		
		System.out.println("Status Code: " +request.getStatusCode());
		
		System.out.println("HTTP Response:\n\n" +request.getHttpResponse());	
		
		
		System.out.println("MethodType: " +request.getMethodType());
	}

}
