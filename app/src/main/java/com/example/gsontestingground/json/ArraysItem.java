package com.example.gsontestingground.json;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ArraysItem{

	@SerializedName("nameId")
	private String nameId;

	@SerializedName("value")
	private List<String> value;

	public String getNameId(){
		return nameId;
	}

	public List<String> getValue(){
		return value;
	}

	@Override
 	public String toString(){
		return 
			"ArraysItem{" + 
			"nameId = '" + nameId + '\'' + 
			",value = '" + value + '\'' + 
			"}";
		}
}