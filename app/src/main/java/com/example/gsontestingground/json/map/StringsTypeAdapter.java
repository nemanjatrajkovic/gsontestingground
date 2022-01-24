package com.example.gsontestingground.json.map;

import com.example.gsontestingground.utils.Utils;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class StringsTypeAdapter extends TypeAdapter<String> {

    static final String tag = StringsTypeAdapter.class.getSimpleName();

    protected final String nameIdTag = "nameId";
    @Override
    public void write(JsonWriter out, String value) throws IOException {

    }

    @Override
    public String read(JsonReader reader) throws IOException {
        JsonToken token = reader.peek();
        String data = ":::";
        if (token.equals(JsonToken.BEGIN_OBJECT)) {
            reader.beginObject();
            while (!reader.peek().equals(JsonToken.END_OBJECT)) {
                if (reader.peek().equals(JsonToken.NAME)) {
                    while(reader.peek().equals(JsonToken.NAME)) {
                        final String name = reader.nextName();
                        final String str = "";//reader.nextString();
                        System.out.printf("%s:: name, string -> %s : %s\n", tag, name, str);
                    }
//                   final String nextName = reader.nextName();
////                    Utils.printLog(tag, "nextName:: %s", nextName);
//                    System.out.printf("%s:: nextName:: %s\n", tag, nextName);
//                    data = nextName;
//                   if (nameIdTag.equals(nextName)) {
//                       final String nextString = reader.nextString();
////                       Utils.printLog(tag, "'%s':'%s'", nextName, nextString);
//                       System.out.printf("%s:: '%s':'%s'\n", tag, nextName, nextString);
//                       data += " : " + nextString;
//                   }

                } else {
                    reader.nextName();
                }
            }
            reader.endObject();

        }
        return data;
    }
}
