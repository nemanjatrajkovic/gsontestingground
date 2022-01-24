package com.example.gsontestingground.json;

import com.google.gson.annotations.SerializedName;

public class Single {

	@SerializedName("nameId")
	private String nameId;

	@SerializedName("value")
	private String value;

	public String getNameId(){
		return nameId;
	}

	public String getValue(){
		return value;
	}

	@Override
 	public String toString(){
		return 
			"SinglesItem{" + 
			"nameId = '" + nameId + '\'' + 
			",value = '" + value + '\'' + 
			"}";
		}
}