package com.example.gsontestingground.ui.language;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Locale;

public class LocaleInfo {
    public final Locale locale;

    private LocaleInfo(Locale locale) {
        this.locale = locale;
    }

    public String displayName() {
        return locale.getDisplayLanguage(locale);
    }

    @NonNull
    @Override
    public String toString() {
        return displayName();
    }

    public static LocaleInfo from(@NonNull final Locale locale) {
        return new LocaleInfo(locale);
    }

    public static LocaleInfo[] from(@NonNull final List<Locale> locales) {
        final LocaleInfo[] infos = new LocaleInfo[locales.size()];
        for (int i = 0; i < locales.size(); i++) {
            final Locale locale = locales.get(i);
            infos[i] = LocaleInfo.from(locale);
        }
        return infos;
    }
}
