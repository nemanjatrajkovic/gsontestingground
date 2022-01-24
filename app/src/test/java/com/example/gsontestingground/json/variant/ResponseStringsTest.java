package com.example.gsontestingground.json.variant;

import com.example.gsontestingground.json.io.TranslatorIO;
import com.example.gsontestingground.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ResponseStringsTest {

    private final String fileTestLargeMaps = "testLargeMaps.json";
    private final String fileTestLargeMapsXml = "testLargeMaps.xml";

    @Test
    public void writeLargeMapsObjectToJsonFile() {
        final File file = Paths.get(Utils.assetsBasePath, fileTestLargeMaps).toFile();
        try (final BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            final Instant start = Instant.now();
            final ResponseStrings responseStrings = ResponseStrings.createResponseStringsLarge();
            final Gson gson = new GsonBuilder()
//                    .excludeFieldsWithModifiers(Modifier.TRANSIENT)
                    .excludeFieldsWithoutExposeAnnotation()
                    .create();
            final String json = gson.toJson(responseStrings);
            bw.write(json);
            final Instant end = Instant.now();
            System.out.println();
            System.out.printf("Total elapsed time: %s\n", Duration.between(start, end).toMillis());
            System.out.println();
            System.out.println("Written responseStrings to file: " +file.getAbsolutePath());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    @Test
    public void readLargeMapsFromJsonFile() {
        final File file = Paths.get(Utils.assetsBasePath, fileTestLargeMaps).toFile();
        final Instant start = Instant.now();
        try (final BufferedReader br = new BufferedReader(new FileReader(file))) {
            final StringBuilder sbd = new StringBuilder();
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                sbd.append(line);
            }
            final Gson gson = new GsonBuilder().create();
            final ResponseStrings responseStrings = gson.fromJson(sbd.toString(), ResponseStrings.class);
            final Instant end = Instant.now();
            System.out.printf("Total time elapsed: %s\n", Duration.between(start, end).toMillis());
            System.out.println();
            System.out.println("Printing responseStrings deserialized from json file");
            System.out.println(responseStrings.toString());
            System.out.println();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @Test
    public void writeLargeMapsObjectToXmlFile() {
        final File file = Paths.get(Utils.assetsBasePath, fileTestLargeMapsXml).toFile();
        try (final BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            final Instant start = Instant.now();
            final ResponseStrings responseStrings = ResponseStrings.createResponseStringsLarge();
            final String xml = responseStrings.toXml();
            bw.write(xml);
            final Instant end = Instant.now();
            System.out.println();
            System.out.printf("Total elapsed time: %s\n", Duration.between(start, end).toMillis());
            System.out.println();
            System.out.println("Written responseStrings to file: " +file.getAbsolutePath());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    @Test
    public void readLargeMapsFromXmlFile() {
        final File file = Paths.get(Utils.assetsBasePath, fileTestLargeMapsXml).toFile();
        final Instant start = Instant.now();
        try (final BufferedReader br = new BufferedReader(new FileReader(file))) {
            final StringBuilder sbd = new StringBuilder();
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                sbd.append(line);
            }
            final Gson gson = new GsonBuilder().create();
            final ResponseStrings responseStrings = gson.fromJson(sbd.toString(), ResponseStrings.class);
            final Instant end = Instant.now();
            System.out.printf("Total time elapsed: %s\n", Duration.between(start, end).toMillis());
            System.out.println();
            System.out.println("Printing responseStrings deserialized from json file");
//            System.out.println(responseStrings.toString());
            System.out.println();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @Test
    public void testLocaleLangSetup() {
        final List<Locale> locales = new ArrayList<>();
        locales.add(Locale.ENGLISH);
        locales.add(Locale.GERMANY);
        locales.forEach(locale -> {
           printLocale(locale);
        });
    }

    @Test
    public void testLocaleBasedFileName() {
        final List<Locale> locales = new ArrayList<>();
        locales.add(Locale.ENGLISH);
        locales.add(Locale.GERMANY);
        locales.forEach(locale -> {
            System.out.printf("File name for locale %s:: %s\n", locale.getDisplayName(), TranslatorIO.fileNameForLocale(locale));
            System.out.println("toString:: " +locale.toString());
        });
    }

    private void printLocale(final Locale locale) {
        String iso3c = ""; String iso3l = "";
        try {
            iso3c = locale.getISO3Country();
            iso3l = locale.getISO3Language();
        } catch (Exception exc) {
            System.out.println("No ISO3:: " + exc.getMessage());
        }
        System.out.printf("    dn:%30s\n    dc:%30s\n    dl:%30s\n     l:%30s\n iso3c:%30s\n iso3l:%30s\n LOC_dl:%30s\n LOC_dn:%30s\n LOC_dc:%30s\n\n",
                locale.getDisplayName(), locale.getDisplayCountry(),
                locale.getDisplayLanguage(), locale.getLanguage(),
                iso3c, iso3l,
                locale.getDisplayLanguage(Locale.GERMANY), locale.getDisplayName(Locale.GERMANY),
                locale.getDisplayCountry(Locale.GERMANY)
        );
    }

    @Test
    public void listJavaLocales() {
        for (final Locale locale : Locale.getAvailableLocales()) {
            printLocale(locale);
        }
        System.out.printf("\nTotal available Locales: %s <<<\n", Locale.getAvailableLocales().length);
    }

    @Test
    public void testFileName() {
        final String fnPattern = "strings[_]{0,1}[a-z]*.json";
        final List<String> fileNames = new ArrayList<>();
        fileNames.add("strings.json");
        fileNames.add("strings_de.json");
        fileNames.add("strings_de_DE.json");
        fileNames.forEach(name -> {
            System.out.printf("> Is [%s] a stirngs json file name: [%s]\n", name, TranslatorIO.isStringsJsonFileName(name));
            System.out.printf("split %s:\n", name);
            final String[] split = name.split("_");
            for (String s : split) {
                System.out.printf("  %s\n", s);
            }
            System.out.println();
        });
    }
}