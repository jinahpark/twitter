package com.lge.twitter.cmd;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.lge.twitter.biz.Tweet;
import com.lge.twitter.biz.TwitterContext;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;


public class CreateCmd extends Command{
	public void execute(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{

		TwitterContext biz = TwitterContext.getInstance();
		String tweet = request.getParameter("tweet");
		
		if(tweet == null){
			response.getWriter().println("Tweet parameter is null");
		}
		
		Command cmd = new Command(request, response);
		ReturnType type = cmd.getReturnType();
	
        Tweet created = biz.create(-1, tweet);
       
		switch(type) {
		case XML: 
			XStream xStream = new XStream(new DomDriver());
			xStream.alias("item", Tweet.class);
			response.getWriter().print(xStream.toXML(created));
			break; 
			
		case JSON:  
			JSONObject jo = new JSONObject();
			jo.put("id", created.getId());
			jo.put("tweet", created.getTweet());
			response.getWriter().print(jo.toString());
			break;        
		}
		
	}

}


