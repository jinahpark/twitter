package com.lge.twitter.cmd;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.json.XML;

import com.lge.twitter.biz.TwitterContext;
import com.lge.twitter.cmd.Command.ReturnType;

public class UpdateCmd{

	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		TwitterContext biz = TwitterContext.getInstance();
		
		String param0 = request.getParameter("tweet");
		String param1 = request.getParameter("id");
		
		Command cmd = new Command(request, response);
		ReturnType type = cmd.getReturnType();

		if(param0 == null || param1 == null){
			response.getWriter().println("ID / Tweet parameter is null");
		}
		
		int id = Integer.parseInt(param1);
		boolean result=biz.update(id, param0);
						
		JSONObject jo = new JSONObject();
		jo.put("result", result == true ? "succeeded" : "failed");
		
		switch(type) {
			case XML: 
				response.getWriter().print(XML.toString(jo));

				break;                     
			case JSON:  
				response.getWriter().print(jo.toString());
				break;        
		}
	}
}