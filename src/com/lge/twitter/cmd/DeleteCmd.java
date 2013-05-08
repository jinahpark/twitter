package com.lge.twitter.cmd;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.json.XML;

import com.lge.twitter.biz.TwitterContext;
import com.lge.twitter.cmd.Command.ReturnType;


//parameter: id
//return: none

public class DeleteCmd {

	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		TwitterContext biz = TwitterContext.getInstance();
		Command cmd = new Command(request, response);
		ReturnType type = cmd.getReturnType();

		String param = request.getParameter("id");
		
		if(param == null){
			response.getWriter().println("ID parameter is null");
		}

		int id = Integer.parseInt(param);
		boolean result = biz.delete(id);

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