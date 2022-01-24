package com.example.gsontestingground;

import com.example.gsontestingground.json.map.Albums;
import com.example.gsontestingground.json.map.Dataset;
import com.example.gsontestingground.json.map.DatasetTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class DataSetTypeAdapterTest {

    @Test
    public void testDataSetTypeAdapter() {
        final String url = "http://freemusicarchive.org/api/get/albums.json?api_key=60BLHNQCAOUFPIBZ&limit=5";
        try {
            final URL link = new URL(url);
            try (final InputStream stream = link.openConnection().getInputStream();
                 final BufferedInputStream bis = new BufferedInputStream(stream)
            ) {
                final StringBuilder sbd = new StringBuilder();
                int len; int read;
                while ((read = bis.read()) != -1) {
//                    final byte[] bite = new byte[64];
//                    System.out.printf("len: %s, read: %s\n", len, read);
//                    for (byte b : bite) {
//                        sbd.append((char) b);
//                    }
                    sbd.append((char)read);
                }
                System.out.println("sbd.length:: " +sbd.length());
                final String json = sbd.toString();
                System.out.println("JSON Pring::");
                System.out.println(json);
                System.out.println();
                // Create the custom type adapter and register it with the GsonBuilder
                // class.
                final Gson gson = new GsonBuilder().registerTypeAdapter(Dataset.class, new DatasetTypeAdapter()).create();
                // deserialize the json to Albums class. The Dataset objects are part of
                // the Albums class. Whenever Gson encounters an object of type DataSet
                // it calls the DatasetTypeAdapter to read and write json.
                final Albums albums = gson.fromJson(json, Albums.class);
                System.out.println(albums.getDataset()[1].getAlbum_title());
                // prints
                // http://freemusicarchive.org/music/The_Yes_Sirs/Through_The_Cracks_Mix_Vol_1/
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // assets path: /Users/nemanja/AndroidStudioProjects/simple-trials/GsonTestingGround/app/src/main/assets
    }
}
