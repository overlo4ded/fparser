package com.overlo4ded.fol.reader;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class PageParser {

	private static final String START_MESSAGE="<!-- message -->";
	private static final String END_MESSAGE="<!-- / message -->";
	private static final String START_ATTACH="<!-- attachments -->";
	private static final String END_ATTACH="<!-- / attachments -->";
	
	private List<ParserListener> parserListeners=new ArrayList<ParserListener>();
	
	public PageParser(){
		super();
	}
	
	public List<Message> parse(String page){
		int start_idx = 0;
		List<Message> messages = new ArrayList<Message>();
		while(start_idx>=0){
			// EXTRACT MESSAGE
			start_idx = page.indexOf(START_MESSAGE)+START_MESSAGE.length();
			if(start_idx<0){return messages;}
			int end_idx = page.indexOf(END_MESSAGE,start_idx);
			if(end_idx<=start_idx){return messages;}
			String msg = page.substring(start_idx,end_idx);
			page=page.substring(end_idx+END_MESSAGE.length());
		
			page = StringUtils.trim(page);
			
			//CHECK ATTACH
			int start_attach_idx = page.indexOf(START_ATTACH);
			while(start_attach_idx==0){
				start_attach_idx=START_ATTACH.length();
				int end_attach_idx = page.indexOf(END_ATTACH,start_attach_idx);
				if(end_attach_idx>start_attach_idx){
					String attach = page.substring(start_attach_idx,end_attach_idx);
					List<String> attachments = new ArrayList<String>();
					attachments.add(attach);
					Message message = new Message(msg,attachments);
					messages.add(message);
					
					page=page.substring(end_attach_idx+END_ATTACH.length());
					page=page.trim();
					start_attach_idx = page.indexOf(START_ATTACH);
				}
			}
			
		}
		this.sendCompleted("");
		return messages;
	}
	
	public void addParserListener(ParserListener p){
		this.parserListeners.add(p);
	}
	public void addParserListeners(List<ParserListener> l){
		this.parserListeners.addAll(l);
	}
	private void sendCompleted(String page){
		for(ParserListener l:parserListeners){
			l.completed(page);
		}
	}




}
