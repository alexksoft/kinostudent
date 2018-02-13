package com.ks.ui;

public class Tweet {
	String _author;
	String _content;
	public Tweet(String author, String content) {
		_author = author;
		_content = content;
	}
	public String getAuthor() {
		return _author;
	}
	public String getIcon() {
		return _author.toLowerCase();
	}
	public String getOwnerIcon() {
		return "owner-icon " + getIcon(); 
	}
	public String getAuthorIcon() {
		return "author-icon " + getIcon();
	}
	public String getContent() {
		return _content;
	}
	public int getTweets() {
		return new java.util.Random().nextInt(1000);
	}
	public int getFollowing() {
		return new java.util.Random().nextInt(1000);
	
	}
	public int getFollowers() {
		return new java.util.Random().nextInt(1000);	
	}
}

