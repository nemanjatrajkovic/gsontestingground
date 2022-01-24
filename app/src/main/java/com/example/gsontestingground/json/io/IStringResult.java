package com.example.gsontestingground.json.io;

import androidx.annotation.NonNull;

public interface IStringResult {

    void onResult(@NonNull final StringBuilder stringBuilder);
    void onComplete(@NonNull final StringBuilder stringBuilder);
}
