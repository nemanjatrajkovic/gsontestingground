package com.example.gsontestingground.json.io;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gsontestingground.BuildConfig;

import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TranslationsViewModel extends ViewModel {

    private final ExecutorService executor = Executors.newFixedThreadPool(Math.max(1, Runtime.getRuntime().availableProcessors()/2));
    private final Object execLock = new Object();
    private static final TranslatorIO translatorIO = TranslatorIO.instance;

    private MutableLiveData<Integer> selectedPosition = new MutableLiveData<>(-1);
    public LiveData<Integer> selectedPositionLive() {
        return selectedPosition;
    }

    public TranslationsViewModel() {
        translatorIO.setExecutor(executor);
    }

    public void setTranslatorIOContext(@Nullable final Context context) {
        translatorIO.setContext(context);
    }

    @Nullable
    public Future<?> submit(@NonNull final Runnable task) {
        synchronized (execLock) {
            return withExecutor(exec -> exec.submit(task));
        }
    }

    public <T> Future<T> submit(@NonNull final Callable<T> task) {
        synchronized (execLock) {
            return withExecutor(exec -> exec.submit(task));
        }
    }

    @Nullable
    public  <T> Future<T> withExecutor(@NonNull final WithExecutor<T> candidate) {
        final ExecutorService exec = executor;
        if (exec == null) return null;

        return candidate.apply(exec);
    }

    private void shutdownExecutor() {
        synchronized (execLock) {
            withExecutor(exec -> {
                exec.shutdown();
                return null;
            });
        }
    }

    @Override
    protected void onCleared() {
        shutdownExecutor();
        super.onCleared();
    }

    public void processFiles(@Nullable final IStringResult result) {
        translatorIO.processFilesAsync(result);
    }

    public int getSelectedLangPos() {
        final Integer value = selectedPositionLive().getValue();
        return value == null ? 0 : value;
    }

    public void updateLanguageSelection(final int selectedPos) {
        final int pos = getSelectedLangPos() == -1 ? 0 : getSelectedLangPos();
        final int ind = selectedPos == -1 ? pos : selectedPos;
        translatorIO.processFileIndexAsync(ind, new IStringResult() {
            @Override
            public void onResult(@NonNull StringBuilder stringBuilder) {

            }

            @Override
            public void onComplete(@NonNull StringBuilder stringBuilder) {
                selectedPosition.postValue(ind);
            }
        });
    }

    public void updateLanguageSelection(@Nullable final Locale locale) {
        final Locale selectedLocale = locale != null ? locale : TranslatorIO.defaultLocale;
        translatorIO.processFileByLocaleAsync(locale, new IStringResult() {
            @Override
            public void onResult(@NonNull StringBuilder stringBuilder) {

            }

            @Override
            public void onComplete(@NonNull StringBuilder stringBuilder) {
                final Integer selectedPos = TranslatorIO.getLocaleIndex(selectedLocale);
                selectedPosition.postValue(selectedPos);
            }
        });
    }

    public int getOther() {
        final int pos = getSelectedLangPos();
        switch (pos) {
            case 0: return 1;
            default:
                return 0;
        }
    }
}
