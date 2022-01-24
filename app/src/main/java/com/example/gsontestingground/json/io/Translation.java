package com.example.gsontestingground.json.io;

import static com.example.gsontestingground.json.io.TranslatorIO.defaultLocale;

import androidx.annotation.NonNull;

import com.example.gsontestingground.json.variant.ResponseStrings;

import java.util.Locale;

public class Translation {

    public static final Translation instance = new Translation();
    private Locale currentLocale = defaultLocale;

    private ResponseStrings responseStrings;


    void updateStrings(@NonNull final ResponseStrings responseStrings) {
        synchronized (instance) {
            this.responseStrings = responseStrings;
        }
    }

    public static String getString(@NonNull final String stringId) {
        return instance.responseStrings.getSingleString(stringId);
    }

    public static String getString(@NonNull final String stringId, Object...args) {
        final String msg = instance.responseStrings.getSingleString(stringId);
        return String.format(instance.currentLocale, msg, args);
    }

    public static String[] getStringArray(@NonNull final String stringId) {
        return instance.responseStrings.getArrayString(stringId);
    }


    public void setCurrentLocale(@NonNull final Locale current) {
        this.currentLocale = current;
    }
}
