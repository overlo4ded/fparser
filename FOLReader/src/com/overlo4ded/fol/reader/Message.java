package com.overlo4ded.fol.reader;

import java.util.ArrayList;
import java.util.List;

public class Message {
	
	private String posturl;
	private String user;
	private String title;
	private String message;
	private List<String> attachments;
	
	public Message(){
		super();
	}
	public Message(String posturl,String user, String title, String message, List<String> attachments){
		this();
		this.posturl=posturl;
		this.user=user;
		this.title=title;
		this.message=message;
		this.attachments=attachments;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<String> getAttachments() {
		if(attachments==null){attachments=new ArrayList<String>();}
		return attachments;
	}
	public void setAttachments(List<String> attachments) {
		this.attachments = attachments;
	}
	public String toHTML(){
		StringBuffer sb = new StringBuffer("");
		sb	.append("<p>").append("\n")
			.append("<b>").append("post URL:").append(this.getPosturl()).append("</b><br/>").append("\n")
			.append("<b>").append("title:").append(this.getTitle()).append("</b><br/>").append("\n")
			.append("<b>").append("user:").append(this.getUser()).append("</b><br/>").append("\n")
			.append(this.getMessage()).append("<br/>").append("\n");
			for(String a:this.getAttachments()){
				sb	.append("<img src=\"").append(a).append("\" />").append("\n");
			}
			
		sb	.append("</p>").append("\n");
		return sb.toString();
	}
	public String toString(){
		StringBuffer sb = new StringBuffer("");
		sb	.append("\t").append("<message>").append("\n")
			.append("\t\t").append("<post-url>").append(this.getPosturl()).append("</post-url>").append("\n")
			.append("\t\t").append("<user>").append(this.getUser()).append("</user>").append("\n")
			.append("\t\t").append("<title>").append(this.getTitle()).append("</title>").append("\n")
			.append("\t\t").append("<text>").append(this.getMessage()).append("</text>").append("\n")
			.append("\t\t").append("<attachments>").append("\n");
			for(String a:this.getAttachments()){
				sb	.append("\t\t").append("<attach>").append(a).append("</attach>").append("\n");
			}
		sb	.append("\t\t").append("</attachments>").append("\n")
			.append("\t\t").append("<html><![CDATA[").append(this.toHTML()).append("]]></html>").append("\n")
			.append("\t").append("</message>").append("\n");
		return sb.toString();
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPosturl() {
		return posturl;
	}
	public void setPosturl(String posturl) {
		this.posturl = posturl;
	}	

}
