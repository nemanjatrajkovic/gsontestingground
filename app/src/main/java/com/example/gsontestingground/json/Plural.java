package com.example.gsontestingground.json;

import com.google.gson.annotations.SerializedName;

public class Plural {

	@SerializedName("nameId")
	private String nameId;

	@SerializedName("value")
	private Value value;

	public String getNameId(){
		return nameId;
	}

	public Value getValue(){
		return value;
	}

	@Override
 	public String toString(){
		return 
			"Plural{" +
			"nameId = '" + nameId + '\'' + 
			",value = '" + value + '\'' + 
			"}";
		}
}