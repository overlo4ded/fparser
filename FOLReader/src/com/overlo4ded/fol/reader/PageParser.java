package com.overlo4ded.fol.reader;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.htmlcleaner.HtmlCleaner;

public class PageParser {

	private static final String START_TITLE="<strong>";
	private static final String END_TITLE="<\\/strong>";
	private static final String START_MESSAGE="<!-- message -->";
	private static final String END_MESSAGE="<!-- / message -->";
	private static final String START_ATTACH="<!-- attachments -->";
	private static final String END_ATTACH="<!-- / attachments -->";
	
	private List<ParserListener> parserListeners=new ArrayList<ParserListener>();
	
	public PageParser(){
		super();
	}
	
	public List<Message> parse(String pageurl,String page){
		int start_idx = 0;
		List<Message> messages = new ArrayList<Message>();
		HtmlCleaner cleaner = new HtmlCleaner();
		while(start_idx>=0){
			// EXTRACT MESSAGE
			start_idx = page.indexOf(START_MESSAGE)+START_MESSAGE.length();
			if(start_idx<0){return messages;}
			int end_idx = page.indexOf(END_MESSAGE,start_idx);
			if(end_idx<=start_idx){return messages;}
			String initialpart = page.substring(0,start_idx);
			
			int msgnum_sidx=initialpart.lastIndexOf("id=\"postmenu_")+"id=\"postmenu_".length();
			String post=initialpart.substring(msgnum_sidx,start_idx);
			String postnum = post.substring(0,post.indexOf("\""));
			String posturl = pageurl+"#post"+postnum;
			
			
			int username1_sidx=initialpart.lastIndexOf("class=\"bigusername\"");
			String puser = initialpart.substring(username1_sidx+"class=\"bigusername\"".length(),start_idx);
			int username1_eidx=puser.indexOf("</a>");
			int username_sidx = puser.indexOf(">")+">".length();
			String user = puser.substring(username_sidx,username1_eidx);
			
			int titleidx = initialpart.lastIndexOf("<strong>")+"<strong>".length();
			int title_eidx = initialpart.lastIndexOf("</strong>");
			String title = initialpart.substring(titleidx,title_eidx);
			
			String msg1 = page.substring(start_idx,end_idx);	
			String msg = cleaner.clean(msg1).getText().toString();
			page=page.substring(end_idx+END_MESSAGE.length());
		
			page = StringUtils.trim(page);
			
			//CHECK ATTACH
			int start_attach_idx = page.indexOf(START_ATTACH);
			while(start_attach_idx==0){
				start_attach_idx=START_ATTACH.length();
				int end_attach_idx = page.indexOf(END_ATTACH,start_attach_idx);
				if(end_attach_idx>start_attach_idx){
					String attach = page.substring(start_attach_idx,end_attach_idx);
					//
					final String URL_ATTACH_START="http://www.finanzaonline.com/forum/attachments/";
					int uas=attach.indexOf(URL_ATTACH_START);
					String imgtoken = attach.substring(uas);
					String url=imgtoken.substring(0,imgtoken.indexOf("\""));
					//
					List<String> attachments = new ArrayList<String>();
					attachments.add(url);
					Message message = new Message(posturl,user,title,msg,attachments);
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
