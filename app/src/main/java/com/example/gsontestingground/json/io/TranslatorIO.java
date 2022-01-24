package com.example.gsontestingground.json.io;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.gsontestingground.BuildConfig;
import com.example.gsontestingground.json.variant.ResponseStrings;
import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class TranslatorIO {

    static final String tag = TranslatorIO.class.getSimpleName();
    public static final Locale defaultLocale = Locale.ENGLISH;
    public static final TranslatorIO instance = new TranslatorIO();

    final String assetsBasePath = "src/main/assets";
    final String translations = "translations";
    final String assetsTranslationsBasePath = concatFilePath("%s%s%s", assetsBasePath, "/", translations);

    @NonNull
    private Context context;

    private final Object execLock = new Object();
    private final AtomicBoolean isProcessingFiles = new AtomicBoolean(false);
    @Nullable
    private ExecutorService executor;

    @NonNull
    private static final ArrayList<String> fileNames = new ArrayList<>();
    private static final ArrayList<String> fileNames2 = new ArrayList<>();

    @NonNull
    private static final List<Locale> supportedLocales = new ArrayList<>();

    public static List<Locale> supportedLocales() {
        return new ArrayList<>(supportedLocales);
    }

    private static void initSupportedLocales() {
        supportedLocales.add(Locale.ENGLISH);
        supportedLocales.add(Locale.GERMAN);
        for (Locale locale : supportedLocales) {
            fileNames.add(fileNameForLocale(locale));
        }
    }

    public static final String defaultFileName = "strings.json";
    static final String fileBase = "strings";
    static final char underscore = '_';
    static final char dot = '.';
    static final String jsonExt = "json";
    static final String stringsFileNamePattern = "strings[_]{0,1}[a-z]*.json";

    static {
        initSupportedLocales();
        // assets
        fileNames2.add("single.json");
        fileNames2.add("test.json");
        fileNames2.add("translations/single.json");

    }

    static boolean isFileStringsJsonName(final String fileName) {
        if (fileName.length() < defaultFileName.length()) return false;
        final String regexDot = "\\.";
        final String[] nameExtension = fileName.split(regexDot);
        if (nameExtension.length != 2) return false;

        final String name = nameExtension[0];
        final String temp = "str";
        final String subName = name.substring(defaultFileName.length());
        if (subName.length() > 0 && subName.charAt(0) != underscore) return false;

        return true;
    }

    public static boolean isStringsJsonFileName(final String fileName) {
        return fileName.matches(stringsFileNamePattern);
    }

    public static int getLocaleIndex(@NonNull final Locale locale) {
        final int found = supportedLocales.indexOf(locale);
        return found == -1 ? 0 : found;
    }

    public static String getLocaleFileName(@NonNull final Locale locale) {
        final int index = getLocaleIndex(locale);
        return fileNames.get(index);
    }

    /**
     * Create a strings.json file name for a given locale
     *
     * @param locale
     * @return example [strings.json
     */
    public static String fileNameForLocale(final Locale locale) {
        final StringBuilder sbd = new StringBuilder();
        sbd.append(fileBase);
        if (locale != defaultLocale) {
            sbd.append(underscore).append(locale.getLanguage());
        }
        sbd.append(dot).append(jsonExt);
        return sbd.toString();
    }


    private TranslatorIO() {
    }

    private TranslatorIO(final @NonNull Context context) {
        this.context = context;
    }

    public static Translation getTranslation() {
        return Translation.instance;
    }

    /*public void processFiles(@Nullable final StringResult result) {
        final StringBuilder strings = new StringBuilder();
        for (final String fileName : fileNames) {
            printLog("Reading file: " + fileName);
                readAssetsFile(fileName, strings);
        }
        if (result != null) result.onComplete(strings);
    }*/

    public void processFilesAsync(@Nullable final IStringResult result) {
        if (!isProcessingFiles.compareAndSet(false, true)) {
            printLog("IPF:: Return Already IN PROGRESS.");
            return;
        }

        submit(() -> {
            printLog("IPF:: Process START:: thd: %s", Thread.currentThread().getName());
            final StringBuilder strings = new StringBuilder();
            for (final String fileName : fileNames2) {
                printLog("Reading file: " + fileName);
                final StringBuilder sbdLocal = readAssetsFileJsonPretty(fileName, strings);
                if (result != null) result.onResult(sbdLocal);
            }
            for (final String fileName : fileNames2) {
                strings.append("\n")
                        .append(fileName)
                        .append("\n");
            }
            strings.append("\n");
            for (String fileName : fileNames) {
                strings.append("\n")
                        .append(concatFilePath(translations, fileName))
                        .append("\n")
                        .append(fileName)
                        .append("\n");
            }
            if (result != null) result.onComplete(strings);
            printLog("IPF:: Process END:: thd: %s", Thread.currentThread().getName());
            isProcessingFiles.set(false);
        });
    }

    public void processFileIndexAsync(final int ind, @Nullable final IStringResult result) {
        if (!isProcessingFiles.compareAndSet(false, true)) {
            printLog("IPF:: Return Already IN PROGRESS.");
            return;
        }

        final int pos = ind == -1 ? 0 : ind;
        final String nameSimple = fileNames.get(pos);
        final Locale current = supportedLocales.get(pos);
        Translation.instance.setCurrentLocale(current);
        final String fileName = concatFilePath(translations, nameSimple);

        submit(() -> {
            printLog("IPF:: index Process START:: thd: %s", Thread.currentThread().getName());
            printLog("Reading file: " + fileName);
            final StringBuilder strings = readAssetsFileJson(fileName);
            printLog("IPF:: index:: json strings" + strings.toString());
            final Gson gson = new Gson();
            final ResponseStrings responseStrings = gson.fromJson(strings.toString(), ResponseStrings.class);
            Translation.instance.updateStrings(responseStrings);
            if (result != null) {
                result.onComplete(strings);
            }
            printLog("IPF:: index Process END:: thd: %s", Thread.currentThread().getName());
            isProcessingFiles.set(false);
        });
    }

    public void processFileByLocaleAsync(final Locale locale, @Nullable final IStringResult result) {
        if (!isProcessingFiles.compareAndSet(false, true)) {
            printLog("IPF:: Return Already IN PROGRESS.");
            return;
        }

        final int pos = getLocaleIndex(locale);
        processFileIndexAsync(pos, result);
    }

    public static String concatFilePath(final String... parts) {
        final StringBuilder sbd = new StringBuilder();
        for (int i = 0, partsLength = parts.length; i < partsLength; i++) {
            final String part = parts[i];
            sbd.append(part);
            if (i < partsLength - 1) sbd.append("/");
        }
        return sbd.toString();
    }

    protected StringBuilder readAssetsFileJson(@NonNull final String fileName) {
        final StringBuilder sbd = new StringBuilder();
        try {
            final AssetManager assetMan = context.getResources().getAssets();
            try {
                final InputStream inputStream = assetMan.open(fileName);
                try (final BufferedReader bis = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
//                try (final BufferedInputStream bis = new BufferedInputStream(inputStream)) {
                    printLog(">>>>>>> RAFJ START read json file");
                    for (int bite = bis.read(); bite != -1; bite = bis.read()) {
                        final char ch = (char) bite;
                        sbd.append(ch);
                    }
                    printLog(">>>>>>> RAFJ END read json file");
                } catch (IOException ioe) {
                    printLog(ioe, "Error reading file ", fileName);
                }
            } catch (Exception exc) {
                printLog(exc, "ERROR reading json file:: " + fileName);
            }
        } catch (Throwable exc) {
            printLog(exc, "ERROR: AssetManager.get");
        }
        printLog("Done file [%s]:: total read: %s", fileName, sbd.length());

        return sbd;
    }

    protected StringBuilder readAssetsFileJsonPretty(@NonNull final String fileName, @Nullable final StringBuilder strings) {
        final StringBuilder sbd = new StringBuilder();
        final AssetManager assetMan = context.getResources().getAssets();
        printLog("TIO:: pretty fileName:"+fileName);
        try (final BufferedInputStream bis = new BufferedInputStream(assetMan.open(fileName))) {
            sbd.append("\n").append(fileName).append("::\n");
            for (int bite = bis.read(); bite != -1; bite = bis.read()) {
                final char ch = (char) bite;
                sbd.append(ch);
            }
            sbd.append("\n-------\n");
            printLog(sbd.toString());
        } catch (Throwable ioe) {
            printLog(ioe, "Error reading file ", fileName);
        }
        printLog("Done file [%s]:: total read: %s", fileName, sbd.length());
        if (strings != null) strings.append(sbd);

        return sbd;
    }

    protected StringBuilder readFileAppendNewLine(@NonNull final String fileName, StringBuilder strings) {
        final StringBuilder sbd = new StringBuilder();
        final File file = new File(fileName);
        try (final BufferedReader br = new BufferedReader(new FileReader(file))) {
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                sbd.append(line).append("\n");
            }
            printLog(sbd.toString());
        } catch (IOException ioe) {
            printLog(ioe, "Error reading file: %s", fileName);
        }

        return sbd;
    }

    protected StringBuilder readFile(@NonNull final String fileName) {
        final StringBuilder sbd = new StringBuilder();
        final File file = new File(fileName);
        try (final BufferedReader br = new BufferedReader(new FileReader(file))) {
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                sbd.append(line);
            }
            printLog(sbd.toString());
        } catch (IOException ioe) {
            printLog(ioe, "Error reading file: %s", fileName);
        }

        return sbd;
    }

    protected void printLog(@NonNull final String msg, Object... args) {
        try {
            final String msgAndArgs = String.format(msg, args);
            if (BuildConfig.DEBUG) {
                Log.d(tag, msgAndArgs);
            }
        } catch (Exception exc) {
            Log.e(tag, "msg", exc);
        }
    }

    protected void printLog(@NonNull final Throwable exc, @NonNull final String msg, Object... args) {
        try {
            final String msgAndArgs = String.format(msg, args);
            if (BuildConfig.DEBUG) {
                Log.d(tag, msgAndArgs, exc);
            }
        } catch (Exception err) {
            Log.e(tag, "msg", err);
        }
    }

    public void setExecutor(@Nullable final ExecutorService executor) {
        synchronized (execLock) {
            this.executor = executor;
        }
    }

    @Nullable
    protected Future<?> submit(@NonNull final Runnable task) {
        synchronized (execLock) {
            return withExecutor(exec -> exec.submit(task));
        }
    }

    protected <T> Future<T> submit(@NonNull final Callable<T> task) {
        synchronized (execLock) {
            return withExecutor(exec -> exec.submit(task));
        }
    }

    @Nullable
    protected <T> Future<T> withExecutor(@NonNull final WithExecutor<T> candidate) {
        final ExecutorService exec = executor;
        if (exec == null) {
            printLog("Executor is null! Cannot submit task.");
            return null;
        }

        return candidate.apply(exec);
    }

    public void onCleared() {
        shutdown();
    }

    protected void shutdown() {
        synchronized (execLock) {
            final ExecutorService exec = this.executor;
            if (exec == null) return;
            exec.shutdown();
        }
    }


    public void setContext(@Nullable final Context context) {
        this.context = context != null ? context.getApplicationContext() : context;
    }
}
