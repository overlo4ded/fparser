package com.overlo4ded.fol.reader;

import java.util.ArrayList;
import java.util.List;

public class Message {
	
	private String message;
	private List<String> attachments;
	
	public Message(){
		super();
	}
	public Message(String message, List<String> attachments){
		this();
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
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb	.append("**************************************").append("\n")
			.append(this.getMessage());
			for(String a:this.getAttachments()){
				sb	.append("===================").append("\n")
					.append(a)
					.append("===================").append("\n");
			}	
			
		sb 	.append("**************************************").append("\n");
		return sb.toString();
	}

}
