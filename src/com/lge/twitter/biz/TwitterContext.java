package com.lge.twitter.biz;

import java.util.HashMap;
import com.lge.twitter.persistance.SqlDatabase;
import com.lge.twitter.persistance.TwitterDatabase;

public class TwitterContext {
	
	private static TwitterContext instance;

	public static TwitterContext getInstance(){
		if(instance == null)
			instance = new TwitterContext();
		return instance;
	}

	TwitterDatabase database;
	
	private TwitterContext(){
		database = new SqlDatabase();
		//database = new MemoryDatabase();
	}
	
	public Tweet create(int id, String tweet){
		return database.create(id, tweet);
	}
	
	public boolean update(int id, String tweet){
		return database.update(id, tweet);
	}
	
	public boolean delete(int id){
		return database.delete(id);
	}
	
	public HashMap<Integer, Tweet> list(){
		return database.list();
	}
}
