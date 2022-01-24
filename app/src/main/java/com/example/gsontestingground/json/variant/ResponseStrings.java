package com.example.gsontestingground.json.variant;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * CANDIDATE
 * Translations variant is the candidate package for .json strings translation pojo classes,
 * based on key/value mappings of string ids & string values.
 * See classes:
 * - StringSingle
 * - StringsArray,
 * - ResponseStringsTest.java for example use
 */
public class ResponseStrings {

    @Expose
    final LinkedHashMap<String, StringSingle> singlesMap = new LinkedHashMap<>();

    @Expose
    final LinkedHashMap<String, StringsArray> arraysMap = new LinkedHashMap<>();

    public ResponseStrings(
            @NonNull final Map<String, StringSingle> singlesMap,
            @NonNull final Map<String, StringsArray> arraysMap
    ) {
        for (Map.Entry<String, StringSingle> en : singlesMap.entrySet()) {
            this.singlesMap.put(en.getKey(), en.getValue().copy(en.getKey()));
        }
        for (Map.Entry<String, StringsArray> en : arraysMap.entrySet()) {
            this.arraysMap.put(en.getKey(), en.getValue());
        }
    }

    public Map<String, StringSingle> getSinglesMap() {
        return singlesMap;
    }

    public Map<String, StringsArray> getArraysMap() {
        return arraysMap;
    }

    @Nullable
    public StringSingle getSingle(@NonNull final String key) {
        return singlesMap.get(key);
    }

    @Nullable
    public StringsArray getArray(@NonNull final String key) {
        return arraysMap.get(key);
    }

    public String getSingleString(@NonNull final String key) {
        final StringSingle single = singlesMap.get(key);
        assert single != null;
        return single.getValue();
    }

    public String[] getArrayString(@NonNull final String key) {
        final StringsArray single = arraysMap.get(key);
        assert single != null;
        return single.getValueArray();
    }

    public static LinkedHashMap<String, StringSingle> createSinglesMap() {
        final LinkedHashMap<String, StringSingle> singlesMap = new LinkedHashMap<>();
        final String[] ids = {"hello", "welcome", "anotherSingle"};
        final String[] values = {"Hello StringSingle", "Welcome StringSingle", "Another StringSingle"};
        for (int i = 0; i < ids.length; i++) {
            final String key = ids[i];
            final String value = values[i];
            singlesMap.put(key, new StringSingle(key, value, "Description: " + key));
        }

        return singlesMap;
    }

    public static LinkedHashMap<String, StringsArray> createArraysMap() {
        final LinkedHashMap<String, StringsArray> arraysMap = new LinkedHashMap<>();

        final String[] countries = {"Germany", "Serbia", "Russia", "Israel"};
        final String countriesId = "countries";
        arraysMap.put(countriesId, new StringsArray(countriesId, countries));

        final String[] languageAbbreviations = {"DE", "SR", "RUS", "ISR"};
        final String langIds = "languages";
        arraysMap.put(langIds, new StringsArray(langIds, languageAbbreviations));

        return arraysMap;
    }

    public static Map<String, StringSingle> createSinglesMapLarge() {
        final Map<String, StringSingle> singlesMap = new HashMap<>();
        final int limitIds = 1000;
        final String idBase = "singleId_";
        final String valueBase = "value single base text num #";
        final String descriptionBase = "description single base text num #";
        for (int ind = 0; ind < limitIds; ind++) {
            final String id = idBase + ind;
            final String value = valueBase + ind;
            final String description = descriptionBase + ind;
            if (ind % 2 == 0) {
                singlesMap.put(id, new StringSingle(id, value, description));
            } else {
                singlesMap.put(id, new StringSingle(id, value));
            }
        }

        return singlesMap;
    }

    public static LinkedHashMap<String, StringsArray> createArraysMapLarge() {
        final LinkedHashMap<String, StringsArray> arraysMap = new LinkedHashMap<>();
        final int limitIds = 1000;
        final String idBase = "arrayId_";
        final int limitArrLen = 20;
        final String valueBase = "value array base text num #";
        final String descriptionBase = "description base text #";
        for (int ind = 0; ind < limitIds; ind++) {
            final String id = idBase + ind;
            final String[] arr = new String[limitArrLen];
            final String description = descriptionBase + ind;
            for (int j = 0; j < arr.length; j++) {
                arr[j] = valueBase + j;
            }
            arraysMap.put(id, new StringsArray(id, arr, description));
        }

        return arraysMap;
    }

    public static ResponseStrings createResponseStringsLarge() {
        return new ResponseStrings(createSinglesMapLarge(), createArraysMapLarge());
    }

    public static ResponseStrings createResponseStringJson() {
        return new ResponseStrings(createSinglesMap(), createArraysMap());
    }

    @Override
    public String toString() {
        final LinkedHashMap<String, StringSingle> singles = singlesMap != null ? singlesMap : new LinkedHashMap<>();
        final LinkedHashMap<String, StringsArray> arrays = arraysMap != null ? arraysMap : new LinkedHashMap<>();
        return "ResponseStringsJson{" +
                "singlesMap: " + joinSinglesMap(singlesMap) +
                "arraysJsonMap: " + joinArraysMap(arrays) +
                '}';
    }

    public static String joinSinglesMap(@NonNull final LinkedHashMap<String, StringSingle> singlesMap) {
        final StringBuilder sbd = new StringBuilder();
        for (final StringSingle single : singlesMap.values()) {
            sbd.append(single.toString()).append(",");
        }
        return sbd.toString();
    }

    public static String joinArraysMap(@NonNull final Map<String, StringsArray> arraysMap) {
        if (arraysMap.entrySet().isEmpty()) return "empty!!";
        final StringBuilder sbd = new StringBuilder();
        for (final Map.Entry<String, StringsArray> en : arraysMap.entrySet()) {
            sbd.append(en.toString()).append(",");
        }
        return sbd.toString();
    }

    static class SingleId {
        public final String id;
        public final String value;
        public final String description;

        public SingleId(String id, String value, String description) {
            this.id = id;
            this.value = value;
            this.description = description;
        }

        public SingleId(String id, String value) {
            this(id, value, null);
        }
    }

    public final String toXml() {
        final StringBuilder sbd = new StringBuilder();
        final String ls = System.lineSeparator();
        // <resources></resources>
        sbd.append("<resources>")/*.append(ls)*/;
        // <string></string>
        final ArrayList<SingleId> singleIds = new ArrayList<>();
        for (final Map.Entry<String, StringSingle> en : singlesMap.entrySet()) {
            final StringSingle value = en.getValue();
            singleIds.add(new SingleId(en.getKey(), value.getValue(), value.getDescription()));
        }
       /* Collections.sort(singleIds, (i1, i2) -> i1.id.compareTo(i2.id));
        for (final SingleId singleId : singleIds) {
            final String description = singleId.id;
            if (description != null) {
                sbd.append("<!-- ").append(description).append(" -->")*//*.append(ls)*//*;
            }
            sbd.append("<string name=\"").append(singleId.id).append("\">")
                    .append(singleId.value).append("</string>")
            *//*.append(ls)*//*;
        }*/
        for (final Map.Entry<String, StringSingle> en : singlesMap.entrySet()) {
            final String description = en.getValue().getDescription();
            if (description != null) {
                sbd.append("<!-- ").append(description).append(" -->");
                //sbd.append(ls);
            }
            sbd.append("<string name=\"").append(en.getKey()).append("\">")
                    .append(en.getValue().getValue()).append("</string>");
            //sbd.append(ls);

        }
        final String space = "    ";
        // <string-array></string-array>
        for (Map.Entry<String, StringsArray> en : arraysMap.entrySet()) {
            final String description = en.getValue().getDescription();
            if (description != null) {
                sbd.append("<!-- ").append(description).append(" -->")/*.append(ls)*/;
            }
            sbd.append("<string-array name=\"").append(en.getKey()).append("\">")/*.append(ls)*/;
            final String[] arr = en.getValue().getValueArray();
            for (final String value : arr) {
                sbd/*.append(space)*/.append("<item>").append(value).append("</item>")/*.append(ls)*/;
            }
            sbd.append("</string-array>")/*.append(ls)*/;
            /*sbd.append(ls)*/;
        }
        sbd.append("</resources>")/*.append(ls)*/;
        return sbd.toString();
    }
}
