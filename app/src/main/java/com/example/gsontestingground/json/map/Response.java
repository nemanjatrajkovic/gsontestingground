package com.example.gsontestingground.json.map;

public class Response{
	private SinglesMap singlesMap2;

	public SinglesMap getSinglesMap2(){
		return singlesMap2;
	}

	@Override
 	public String toString(){
		return 
			"Response{" + 
			"singlesMap2 = '" + singlesMap2 + '\'' + 
			"}";
		}
}
