package com.lge.twitter.persistance;

import java.sql.*;
import java.util.HashMap;
import com.lge.twitter.biz.Tweet;
import com.mysql.jdbc.Connection;


public class SqlDatabase implements TwitterDatabase{
	
	String connectionString="jdbc:mysql://localhost:3306/twitter";
	String sqlId="root";
	String sqlPassword="wlsdk4825";
	
	@Override
	public Tweet create(int id, String tweet) {
		
		ResultSet rs = null;
		Connection conn = null;
		PreparedStatement psmt = null;
		int index=0;
		int newId=0;
		String newTweet = null;
		
		try{
			conn = (Connection)DriverManager.getConnection(connectionString,sqlId,sqlPassword);
			System.out.println("Connection Success at create()!!");
			
			if(id == -1){
		        String sql_1 = "select ifnull(max(id),'0') as num from tweet_history";

		        psmt = conn.prepareStatement(sql_1);
		        rs = psmt.executeQuery();
		        
		        while(rs.next()){
		        index = rs.getInt("num");
		        }
		        
		        index++;
		        System.out.println("index= "+ index);
			}
			
			//insert into twitter.tweet_history
			//(id, tweet) values (1, 'hello');
			String sql_2 = "insert into tweet_history" 
					       + "(id, tweet) values(?,?)";
			
			psmt = conn.prepareStatement(sql_2);
			
			psmt.setInt(1, index);
			psmt.setString(2, tweet);
			
			int updated = psmt.executeUpdate();
			
			if(updated != 1) {
				System.out.println("Insertion failed!!");
			}
			
			String sql_3 = "select * from tweet_history";
	   
			psmt = conn.prepareStatement(sql_3);
			rs = psmt.executeQuery();
			
			if(rs!=null){
				System.out.println("Creation Succeed!!");
				while(rs.next()){
					newId = rs.getInt("id");
					newTweet = rs.getString("tweet");
				}
			}

			Tweet tw = new Tweet(newId, newTweet);
			
			rs.close();
			psmt.close();
			conn.close();
			
			return tw;

		}catch(SQLException se){
			se.printStackTrace();
			System.out.println(se);
			return null;

		}
	}

	@Override
	public boolean delete(int id) {
		try {
			Connection conn = (Connection) DriverManager.getConnection(connectionString, sqlId, sqlPassword);
			System.out.println("Connection Success at delete()!!");
			
			String sql = "delete from tweet_history "
			              + "where id = ? ";
			
			PreparedStatement psmt = conn.prepareStatement(sql);
			
			psmt.setInt(1, id);
			int updated = psmt.executeUpdate();
			
			if(updated != 1) {
				System.out.println("Deletion failed!!");
			}
			System.out.println("Deletion Succeed!!");
			psmt.close();
			conn.close();
			return updated == 1;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	} 
	
	@Override
	public boolean update(int id, String tweet) {
		try {
			Connection conn = (Connection) DriverManager.getConnection(connectionString, sqlId, sqlPassword);
			System.out.println("Connection Success at update()!!");
			
			String sql = "update tweet_history " 
					     + "set tweet = ?"  
						 + "where id = ?";
			
			PreparedStatement psmt= conn.prepareStatement(sql);
			
			psmt.setString(1,tweet);	
			psmt.setInt(2, id);

			int updated = psmt.executeUpdate();
			
			if(updated != 1) {
				System.out.println("Update failed!!");
			}
			
			System.out.println("Update succeed!!");
			psmt.close();
			conn.close();
			return updated == 1;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public HashMap<Integer, Tweet> list() {

		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		//driver loading: driver manager¿¡ µî·Ï 
		try{
			Class.forName("com.mysql.jdbc.Driver");
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		
		try{
			conn = (Connection)DriverManager.getConnection(connectionString,sqlId,sqlPassword);
			System.out.println("Connection Success at list()!!");
			
	        String sql = "select * from tweet_history";
	        
			psmt = conn.prepareStatement(sql);
		    rs = psmt.executeQuery();
		    
		    HashMap<Integer, Tweet> list = new HashMap<Integer, Tweet>();
		   
		    if(rs != null){
		    	while(rs.next()){
					int id = rs.getInt("id");
					String tweet = rs.getString("tweet");
					Tweet tw = new Tweet(id, tweet);
					list.put(id, tw);
				}
		    }
		    System.out.println("Listing Succeed!!");
			rs.close();
			psmt.close();
		    conn.close();
		    return list;
		}catch(SQLException se){
			System.out.println("Connection Fail" + se);
			return null;
		}
		
	}
}

