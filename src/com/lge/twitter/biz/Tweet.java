package com.lge.twitter.biz;

public class Tweet {
	private int id;
	private String tweet;
	
	public Tweet(int id, String tweet){
		this.id = id;
		this.tweet = tweet;
	}
	
	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public String getTweet(){
		return tweet;
	}

	public void setTweet(String tweet){
		this.tweet = tweet;
	}
}

