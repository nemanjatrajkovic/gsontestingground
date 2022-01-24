package com.example.gsontestingground.json;

import com.google.gson.annotations.SerializedName;

public class Value{

	@SerializedName("zero")
	private String zero;

	@SerializedName("four")
	private String four;

	@SerializedName("one")
	private String one;

	@SerializedName("few")
	private String few;

	@SerializedName("many")
	private String many;

	@SerializedName("two")
	private String two;

	@SerializedName("three")
	private String three;

	public String getZero(){
		return zero;
	}

	public String getFour(){
		return four;
	}

	public String getOne(){
		return one;
	}

	public String getFew(){
		return few;
	}

	public String getMany(){
		return many;
	}

	public String getTwo(){
		return two;
	}

	public String getThree(){
		return three;
	}

	@Override
 	public String toString(){
		return 
			"Value{" + 
			"zero = '" + zero + '\'' + 
			",four = '" + four + '\'' + 
			",one = '" + one + '\'' + 
			",few = '" + few + '\'' + 
			",many = '" + many + '\'' + 
			",two = '" + two + '\'' + 
			",three = '" + three + '\'' + 
			"}";
		}
}