package com.example.gsontestingground.utils;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.gsontestingground.BuildConfig;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Utils {

    public static final String assetsBasePath = "src/main/assets";

    public static void printLog(@NonNull final String tag, @NonNull final String msg, Object...args) {
        final String msgAndArgs = String.format(msg, args);
        if (BuildConfig.DEBUG) {
            Log.d(tag, msgAndArgs);
        }
    }

    public static <T> T apply(@Nullable final T ref, @NonNull final Consumer<T> consumer) {
        if (ref != null) consumer.accept(ref);
        return ref;
    }

    public static <T, R> R let(@Nullable final T ref, @NonNull final Function<T, R> function) {
        return ref != null ? function.apply(ref) : null;
    }
}
