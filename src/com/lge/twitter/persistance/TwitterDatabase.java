package com.lge.twitter.persistance;

import java.util.HashMap;
import com.lge.twitter.biz.Tweet;

public interface TwitterDatabase {
	HashMap<Integer, Tweet> list();
	Tweet create(int id, String tweet);
	boolean delete(int id);
	boolean update(int id, String tweet);
} 
