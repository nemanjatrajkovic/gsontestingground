package com.example.gsontestingground.jsontest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class StringArrayItem {

    @NonNull
    final String nameId;
    @NonNull
    final String value;

    @Nullable
    final String description;

    public StringArrayItem(@NonNull String nameId, @NonNull String value, @Nullable String description) {
        this.nameId = nameId;
        this.value = value;
        this.description = description;
    }

    public StringArrayItem(@NonNull String nameId, @NonNull String value) {
        this(nameId, value, null);
    }
}
