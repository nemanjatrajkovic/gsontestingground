package com.example.gsontestingground.json.io;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

interface WithExecutor<T> {

    @Nullable
    Future<T> apply(@NonNull final ExecutorService executor);
}
