package com.example.gsontestingground.json;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Map;

import com.google.gson.annotations.SerializedName;

public class Response{

	@SerializedName("singles")
	private Map<String, Single> singles;

	@SerializedName("plurals")
	private List<Plural> plurals;

	@SerializedName("arrays")
	private List<ArraysItem> arrays;

	public Map<String, Single> getSingles(){
		return singles;
	}

	public List<Plural> getPlurals(){
		return plurals;
	}

	public List<ArraysItem> getArrays(){
		return arrays;
	}

	@NonNull
	@Override
 	public String toString(){
		return 
			"Response{" + 
			"singles = '" + singles + '\'' + 
			",plurals = '" + plurals + '\'' + 
			",arrays = '" + arrays + '\'' + 
			"}";
		}
}