package com.example.gsontestingground;

import com.example.gsontestingground.json.Response;
import com.example.gsontestingground.json.map.SinglesMap;
import com.example.gsontestingground.json.variant.ResponseStrings;
import com.example.gsontestingground.json.variant.StringSingle;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;

public class GsonTest {

    final String assetsBasePath = "src/main/assets";
    final String translations = "translations";
//    final Path assetsTranslationsBasePath = Paths.get(assetsBasePath, translations);
    final String assetsTranslationsBasePath = String.format("%s%s%s", assetsBasePath, "/", translations);

    final String fileTestMap = "testMap.json";
    final String fileTest = "test.json";
    final String fileSingle = "single.json";
    final String fileStrings = "strings.json";

    @Test
    public void createJsonFile() {
//        final File file = new File(fileName);
//        try {
//            final boolean created = file.createNewFile();
//            System.out.println("created: " + created);
//            System.out.println("file: " + file.getAbsolutePath());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Test
    public void testTextFile() {
//        final File file = new File("test.txt");
//        try {
//            final ClassLoader classLoader = ClassLoader.getSystemClassLoader();
//            assert  classLoader != null;
//            final InputStream is = classLoader.getResourceAsStream("assets/test.txt");
//            int available;
//            while((available = is.available()) != -1) {
//                final byte[] chunk = new byte[available];
//                final int read = is.read(chunk);
//                System.out.println("Read: " +read);
//                System.out.print(Arrays.toString(chunk));
//            }
//        } catch (IOException ioe) {
//            ioe.printStackTrace();
//        }
    }

    @Test
    public void testReadFileWithForFromAssets() {
        // final String fileTestPath = "src/main/assets/test.json";
        final Path assetsPath = Paths.get(assetsBasePath, fileTest);
        final StringBuilder sbd = new StringBuilder();
        try (final BufferedReader br = new BufferedReader(new FileReader(assetsPath.toFile()))) {
            System.out.println("\nPrinting BufferedReader with for loop >>>>>>>>>>>>>");
            for (String line = br.readLine(); line != null; line = br.readLine()) {
//                System.out.println(line);
                sbd.append(line).append("\n");
            }
            System.out.println("Printing BufferedReader with for loop <<<<<<<<<<<<<\n");
            System.out.println();
//            final StringsTypeAdapter typeAdapter = new StringsTypeAdapter();
//            final Type type = new TypeToken<String>(){}.getType();
//            final Gson gson = new GsonBuilder().registerTypeAdapter(type, typeAdapter).create();
//            final String result = gson.fromJson(sbd.toString(), type);
            final Gson gson = new GsonBuilder().create();
            final com.example.gsontestingground.json.map.SinglesMap result = gson.fromJson(sbd.toString(), com.example.gsontestingground.json.map.SinglesMap.class);
            System.out.println(result.toString());
            System.out.println(new File(fileSingle).getAbsolutePath());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @Test
    public void writeJsonToFile() {
        final Path filePath = Paths.get(assetsBasePath, fileTestMap);
        try (final BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath.toFile()))) {
            final SinglesMap map = new SinglesMap();
            final int limit = 7;
            for (int i = 0; i < limit; i++) {
                final String k = "single_"+i; final String v = "value_"+i;
                map.add(k, v);
            }
            final Gson gson = new GsonBuilder().create();
            final String json = gson.toJson(map);
            for (int i = 0; i < json.length(); i++) {
                final int ch = json.charAt(i);
                bos.write(ch);
            }
            System.out.println(json);
        } catch (IOException ioe) {
            System.out.println("Error writing to file::::" +ioe.getMessage());
            ioe.printStackTrace();
        }
    }

    /**
     * Test java to json serialization, using classes {@link ResponseStrings}
     */
    @Test
    public void writeResponseStringJsonToFile() {
        final String fileName = "testResponseStrings.json";
        final File file = Paths.get(assetsBasePath, fileName).toFile();
        try (final BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            final ResponseStrings responseStrings = ResponseStrings.createResponseStringJson();
            final Gson gson = new GsonBuilder().create();
            final String json = gson.toJson(responseStrings);
            bw.write(json);
            System.out.println("Written to file: "+file.getAbsolutePath());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Test java to json deserialization, using classes {@link ResponseStrings}
     */
    @Test
    public void readResponseStringJsonFromFile() {
        final String fileName = "testResponseStrings.json";
        final File file = Paths.get(assetsBasePath, fileName).toFile();
        try (final BufferedReader br = new BufferedReader(new FileReader(file))) {
            final StringBuilder sbd = new StringBuilder();
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                sbd.append(line).append("\n");
            }
            final Gson gson = new GsonBuilder().create();
            final ResponseStrings responseStrings = gson.fromJson(sbd.toString(), ResponseStrings.class);
            System.out.println();
            System.out.println("Example string single with args");
            final String key = "welcome";
            final StringSingle single = responseStrings.getSingle(key);
            assert single != null;
            System.out.printf("key: %s, value: %s\n", key, single.getValue());
            System.out.printf("%s\n", single.getValueArgs(1));
            System.out.println();
            System.out.println();
            System.out.println(responseStrings.toString());
            System.out.println();
            System.out.println("Reading from file: " +file.getAbsolutePath()+ " complete.");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @Test
    public void testReadMapFileWithForFromAssets() {
        // final String fileTestPath = "src/main/assets/test.json";
        final Path assetsPath = Paths.get(assetsBasePath, fileTestMap);
        final StringBuilder sbd = new StringBuilder();
        try (final BufferedReader br = new BufferedReader(new FileReader(assetsPath.toFile()))) {
            System.out.println("\nPrinting BufferedReader with for loop >>>>>>>>>>>>>");
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                System.out.println(line);
                sbd.append(line).append("\n");
            }
            System.out.println("Printing BufferedReader with for loop <<<<<<<<<<<<<\n");
            System.out.println();
            final Gson gson = new GsonBuilder().create();
            final SinglesMap result = gson.fromJson(sbd.toString(), SinglesMap.class);
            System.out.println(result.toString());
            System.out.println(new File(fileSingle).getAbsolutePath());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > > >
     *
     * Class generator
     * Generate Strings.java class from testMap.json file - {@link #assetsBasePath}/{@link #fileTestMap}
     *
     */
    @Test
    public void createStringsIdClass() {
//        final Path assetsPath = Paths.get(assetsTranslationsBasePath.toFile().getAbsolutePath(), fileStrings);
        final String assetsPath = String.format("%s%s%s", assetsTranslationsBasePath, "/", fileStrings);
        final File assetsPathFile = new File(assetsPath);
        final StringBuilder sbd = new StringBuilder();
        ResponseStrings responseStrings;
        try (final BufferedReader br = new BufferedReader(new FileReader(assetsPathFile))) {
            System.out.println("\nPrinting BufferedReader with for loop >>>>>>>>>>>>>");
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                System.out.println(line);
                sbd.append(line).append("\n");
            }
            System.out.println("Printing BufferedReader with for loop <<<<<<<<<<<<<\n");
            System.out.println();
            final Gson gson = new GsonBuilder().create();
            responseStrings = gson.fromJson(sbd.toString(), ResponseStrings.class);
            System.out.println(responseStrings.toString());
            System.out.println("assetsPath:: " +assetsPathFile.getAbsolutePath());
        } catch (IOException ioe) {
            ioe.printStackTrace();
            responseStrings = null;
        }

        assert responseStrings != null;
        sbd.setLength(0);
        final String newLine = "\n";
        final String openBrace = "{";
        final String closeBrace = "}";
        final String packageNameLine = "com.example.gsontestingground.json";
        final String stringsClassName = "StringsId";
        sbd.append("package ").append(packageNameLine).append(";").append(newLine).append(newLine);
        sbd.append("/**").append(newLine);
        sbd.append(" * Generated by the test ").append(getClass().getSimpleName())
                .append(".createStringsIdClass()").append(newLine);
        sbd.append(" * Author: Nemanja Trajkovic").append(newLine);
        sbd.append(" */").append(newLine);
        sbd.append("public class ").append(stringsClassName).append(" ").append(openBrace).append(newLine).append(newLine);
        final Map<String, StringSingle> map = responseStrings.getSinglesMap();
        for (final String key : map.keySet()) {
            sbd.append("public static final String ").append(key).append(" = \"").append(key).append("\";");
            sbd.append(newLine);
        }
        sbd.append(newLine);
        sbd.append(closeBrace);
        System.out.println("<><><><><><><>");
        System.out.println(sbd.toString());
        final String packageBasePath = "src/main/java/com/example/gsontestingground/json";
        final Path stringsClassPath = Paths.get(packageBasePath, stringsClassName+".java");
        try (final BufferedWriter bos = new BufferedWriter(new FileWriter(stringsClassPath.toFile()))) {
            bos.write(sbd.toString());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        System.out.println(stringsClassPath.toFile().getAbsolutePath());
    }

    @Test
    public void singleReadFileWithFor() {
        final StringBuilder sbd = new StringBuilder();
        try (final BufferedReader br = new BufferedReader(new FileReader(fileSingle))) {
            System.out.println("\nPrinting BufferedReader with for loop >>>>>>>>>>>>>");
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                System.out.println(line);
            }
            System.out.println("Printing BufferedReader with for loop <<<<<<<<<<<<<\n");
            System.out.println();
//            final StringsTypeAdapter typeAdapter = new StringsTypeAdapter();
//            final Gson gson = new GsonBuilder().registerTypeAdapter().create();
//            gson.fromJson(sbd.toString(), typeAdapter);
            System.out.println(new File(fileSingle).getAbsolutePath());
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @Test
    public void testReadFileWIthFor() {
        try (final BufferedReader br = new BufferedReader(new FileReader(fileTest))) {
            System.out.println("\nPrinting BufferedReader with for loop >>>>>>>>>>>>>");
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                System.out.println(line);
            }
            System.out.println("Printing BufferedReader with for loop <<<<<<<<<<<<<\n");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @Test
    public void testReadFileInputStreamWithFor() {
        try (final BufferedInputStream br = new BufferedInputStream(new FileInputStream(fileTest))) {
            System.out.println("\nPrinting BufferedInputStream with for loop >>>>>>>>>>>>>");
            for (int line = br.read(); line != -1; line = br.read()) {
                System.out.print((char)line);
            }
            System.out.println("\nPrinting BufferedInputStream with for loop <<<<<<<<<<<<<\n");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @Test
    public void testGson() {
        final Gson gson = new GsonBuilder().create();
        try (final JsonReader reader = gson.newJsonReader(new FileReader(fileSingle))) {
            final Response response = gson.fromJson(reader, Response.class);
            response.getSingles().forEach((key, value) -> {
                System.out.printf("(k, v):: %s : %s\n", key, value);
            });
            response.getArrays().forEach(array -> {
                System.out.println(String.join(", ", array.getValue()));
            });
            System.out.println(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void mapToJson() {
        final Single base = new Single("singleId", "singleValue");
        final int limit = 10;
        final Singles singles = new Singles();
        for (int i = 0; i < limit; i++) {
            final Single next = new Single(base.nameId + "_" + i, base.value + "_" + i);
            singles.pubSingle(next);
        }
        final Gson gson = new GsonBuilder().create();
        final String json = gson.toJson(singles);
        System.out.println(json);
    }

    @Test
    public void jsonToMap() {
        final File file = new File(fileTest);
        final Type type = new TypeToken<LinkedHashMap<String, Single>>() {}.getType();
        final Gson gson = new GsonBuilder().create();
        try (final JsonReader reader = new JsonReader(new BufferedReader(new FileReader(fileTest)))) {
            final SinglesMap singlesMap = gson.fromJson(reader, type);
            singlesMap.getMap().forEach((k, v) -> {
                System.out.printf("%s : %s", k, v);
            });
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    static class Single {
        public final String nameId;
        public final String value;

        Single(String nameId, String value) {
            this.nameId = nameId;
            this.value = value;
        }
    }

    static class Singles {
        final Map<String, Single> map = new LinkedHashMap<>();

        public Single getSingle(final String nameId) {
            return map.get(nameId);
        }

        public Single putSingle(final String nameId, final String value) {
            return map.put(nameId, new Single(nameId, value));
        }

        public void pubSingle(final Single single) {
            map.put(single.nameId, single);
        }
    }

}
