package com.overlo4ded.fol.reader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class Reader {
	
	private List<ParserListener> parserListeners=new ArrayList<ParserListener>();
	
	public String read(String url, String endpage){
		
		HttpClient client = HttpClientBuilder.create().build();
		HttpResponse resp = null;
		HttpGet getreq = null;
		final String END_PHRASE=".html";
		int end_idx = url.indexOf(END_PHRASE);
		String partial = url.substring(url.length()-END_PHRASE.length()-4,end_idx);
		String numpage = partial.substring(partial.indexOf("-")+1);
		System.out.println(numpage);
		int inumpage = Integer.parseInt(numpage);
		int iendpage = inumpage;
		if(StringUtils.isNotEmpty(endpage)){
			iendpage=Integer.parseInt(endpage);
		}
		this.sendComputedIterations(inumpage, iendpage);
		String baseurl = url.substring(0,url.indexOf(numpage));
		StringBuffer sb = new StringBuffer("<messages>");
		while(inumpage<=iendpage){
			String newurl = baseurl+inumpage+".html";
			System.out.println("processing....:"+inumpage);
			getreq = new HttpGet(newurl);
			try {
				resp = client.execute(getreq);
				String res = EntityUtils.toString(resp.getEntity());
				PageParser parser = new PageParser();
				parser.addParserListeners(parserListeners);
				List<Message> messages = parser.parse(url,res);
				for(Message m:messages){
					sb.append(m.toString());
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally{
				EntityUtils.consumeQuietly(resp.getEntity());
				getreq.releaseConnection();
			}
			inumpage++;
		}
		sb.append("</messages>").append("\n");
		//System.out.println(sb.toString());
		return sb.toString();
	}
	
	public void addParserListener(ParserListener p){
		this.parserListeners.add(p);
	}
	public void addParserListeners(List<ParserListener> l){
		this.parserListeners.addAll(l);
	}
	private void sendComputedIterations(int min, int max){
		for(ParserListener l:parserListeners){
			l.computedIterations(min, max);
		}
	}

}
