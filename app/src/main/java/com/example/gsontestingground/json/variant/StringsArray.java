package com.example.gsontestingground.json.variant;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;

import java.util.Arrays;

public class StringsArray {

    @Expose
    @NonNull
    transient private final String nameId;

    @Expose
    @NonNull
    private final String[] values;

    @Expose
    @Nullable
    final String description;

    public StringsArray(@NonNull String nameId, @NonNull String[] values, @Nullable String description) {
        this.nameId = nameId;
        this.values = values;
        this.description = description;
    }

    public StringsArray(@NonNull String nameId, @NonNull String[] values) {
        this(nameId, values, null);
    }

    @NonNull
    public String getNameId() {
        return nameId;
    }

    @NonNull
    public String[] getValueArray() {
        return Arrays.copyOf(values, values.length);
    }

    @NonNull
    public int getValueArrayLength() {
        return values.length;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "{\n" +
                "nameId='" + nameId + '\'' +
                ", \nvalues=" + Arrays.toString(values) +
                ", \ndescription='" + description + '\'' +
                "\n}";
    }
}
