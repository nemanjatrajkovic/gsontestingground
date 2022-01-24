package com.example.gsontestingground.json.map;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class SinglesMap {

    @SerializedName("map")
    private Map<String, String> map = new HashMap<>();

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(@NonNull final Map<String, String> map) {
        this.map = map;
    }

    public void add(final String key, final String value) {
        map.put(key, value);
    }

    @NonNull
    @Override
    public String toString() {
        return "SinglesMap{" + "map = '" + join(", ", map) + '\'' + "}";
    }


    String join(final String delimiter, Map<String, String> map) {
        final StringBuilder sbd = new StringBuilder();
        for (final Map.Entry<String, String> e : map.entrySet()) {
            sbd.append(e.getKey()).append(":").append(e.getValue()).append(delimiter);
        }
        return sbd.toString();
    }

}