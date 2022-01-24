package com.example.gsontestingground.json;

import androidx.arch.core.internal.SafeIterableMap;

import com.google.gson.annotations.SerializedName;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class Map {

	@SerializedName("singles")
	private LinkedHashMap<String, String> singles = new LinkedHashMap<>();

	@Override
 	public String toString(){
		final StringBuilder sbd = new StringBuilder();
		for (Entry<String, String> entry : singles.entrySet()) {
			final String k = entry.getKey();
			final String v = entry.getValue();
			sbd.append(k).append(" : ").append(v).append("\n");
		}
		return
			"Map{" + 
			"singles:" +sbd.toString()+
			"}";
		}
}