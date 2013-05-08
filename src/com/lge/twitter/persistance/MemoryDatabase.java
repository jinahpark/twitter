package com.lge.twitter.persistance;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import com.lge.twitter.biz.Tweet;

public class MemoryDatabase implements TwitterDatabase {

	public HashMap<Integer, Tweet> list = new HashMap<Integer, Tweet>();
	
	public Tweet create(int id, String tweet){
			
		if(id == -1){
	        int size = list.size();
	        
	        if(!list.containsKey(size+1))
	       	 id = size + 1;
	        else
	       	 id = size + 2;
		}
		
		Tweet tw = new Tweet(id, tweet);
        list.put(id, tw);
        
		System.out.println("New Tweet succeeded!  [id]: "+ id + "  [tweet]: " +list.get(id).getTweet());
		return tw;
	}

	public boolean delete(int id){
		
		int count = list.size();
		if(count==0){
			return false;
		}
			
		list.remove(id);
		System.out.println("Tweet deletion succeeded! [id]: " + id);
		if(!list.containsKey(id)) 
			return true;
		else
			return false;
	}
	
	public boolean update(int id, String tweet){
		
		int count = list.size();
		
		if(count==0){
			return false;
		}

		Tweet tw = list.get(id);
		tw.setTweet(tweet);
		list.put(id, tw);
		System.out.println("Tweet Update succeeded!  [id]: "+ id + "  [tweet]: " +list.get(id).getTweet());

		if(list.get(id)==tw)
			return true;
		else
			return false;
	}
	
	public HashMap<Integer, Tweet> list(){
		
		Set<Entry<Integer, Tweet>> set = list.entrySet();
		Iterator<Entry<Integer, Tweet>> iterator = set.iterator();
		
		while(iterator.hasNext()){
			Entry<Integer, Tweet> Tweet = (Entry<Integer, Tweet>) iterator.next();
			System.out.println("Tweet list [id]: " + Tweet.getKey()+"  [tweet]: "+ Tweet.getValue().getTweet());
		}
		return list;
	}
}


