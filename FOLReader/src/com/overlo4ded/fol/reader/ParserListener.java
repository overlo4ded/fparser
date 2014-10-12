package com.overlo4ded.fol.reader;

public interface ParserListener {
	
	public void completed(String page);
	public void computedIterations(int min, int max);

}
