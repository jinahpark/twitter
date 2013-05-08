package com.lge.twitter.cmd;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Command {
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public enum ReturnType{
		XML, JSON
	}
	
	//constructor 
	public Command(){	
	}
	
	//constructor 
	protected Command(HttpServletRequest request, HttpServletResponse response){
		this.request = request;
		this.response = response;
	}
	
	protected ReturnType getReturnType(){
		if(request.getRequestURI().contains(".xml")){
			return ReturnType.XML;
		}else{
			return ReturnType.JSON;
		}
	}
}
