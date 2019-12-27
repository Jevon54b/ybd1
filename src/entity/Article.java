package entity;

import java.sql.Time;

public class Article {

	private String id;
	private String title;
	private String author;
	private Time realease_time;
	private String content;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Time getRealease_time() {
		return realease_time;
	}
	public void setRealease_time(Time realease_time) {
		this.realease_time = realease_time;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
