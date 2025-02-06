package kr.or.iei.news.model.vo;

import java.io.Serializable;

public class NewsItem implements Serializable {
	private String title;
    private String link;
    private String description;
	public NewsItem() {
		super();
		// TODO Auto-generated constructor stub
	}
	public NewsItem(String title, String link, String description) {
		super();
		this.title = title;
		this.link = link;
		this.description = description;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "{ title : "+title+", link : " + link + ", description : "+description + "}";
	}
    
}
