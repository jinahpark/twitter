package com.lge.twitter.cmd;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.lge.twitter.biz.Tweet;
import com.lge.twitter.biz.TwitterContext;
import com.lge.twitter.cmd.Command.ReturnType;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class ListCmd {

	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		TwitterContext biz = TwitterContext.getInstance();
		Command cmd = new Command(request, response);

		ReturnType type = cmd.getReturnType();
			
		HashMap<Integer, Tweet> listed = biz.list();
			
		Set<Entry<Integer, Tweet>> set = listed.entrySet();
		Iterator<Entry<Integer, Tweet>> iterator = set.iterator();
			
		switch(type) {
			case XML: 				
				XStream xStream = new XStream(new DomDriver());
				xStream.alias("item", Tweet.class);
				
				StringBuffer xml = new StringBuffer();
				xml.append("<xml>").append("\n");
				
				while(iterator.hasNext()){
					Entry<Integer, Tweet> Tweet = (Entry<Integer, Tweet>) iterator.next();
					Tweet tweet = Tweet.getValue();
					xml.append(xStream.toXML(tweet)).append("\n");
				}
				
				xml.append("</xml>").append("\n");
				response.getWriter().print(xml);
				break;         
				
			case JSON:  
				JSONArray list = new JSONArray();
				
				while(iterator.hasNext()){
					Entry<Integer, Tweet> Tweet = (Entry<Integer, Tweet>) iterator.next();
					
					JSONObject jo = new JSONObject();
					jo.put("id", Tweet.getKey());
					jo.put("tweet", Tweet.getValue());
					list.put(jo);
				}
				response.getWriter().print(list.toString());
				break;        
		}
	}
}


