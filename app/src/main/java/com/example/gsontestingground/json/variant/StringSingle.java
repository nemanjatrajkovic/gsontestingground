package com.example.gsontestingground.json.variant;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;

import java.util.IllegalFormatException;
import java.util.Objects;

public class StringSingle {

    @Expose(serialize = false, deserialize = false)
    @Nullable
    private transient final String nameId;

    @Expose
    @NonNull
    private final String value;

    @Expose
    @Nullable
    private final String description;

    public StringSingle(@Nullable String nameId, @NonNull String value, @Nullable String description) {
        this.nameId = nameId;
        this.value = value;
        this.description = description;
    }

    public StringSingle(@Nullable String nameId, @NonNull String value) {
        this(nameId, value, null);
    }

    public StringSingle(@NonNull String value) {
        this(null, value, null);
    }

    @NonNull
    public String getValue() {
        return value;
    }

    @NonNull
    public String getValueArgs(final Object...args) {
        return String.format(value, args);
    }

    @NonNull
    public String getValueArgsSafe(final Object...args) {
        String result;
        try {
            result = getValueArgs(args);
        } catch (IllegalFormatException ife) {
            result = value;
            ife.printStackTrace();
        }
        return result;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "{\n" +
//                "nameId='" + nameId + '\''+
                ", \nvalue='" + value + '\'' +
                ", \ndescription='" + description + '\'' +
                "\n}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringSingle that = (StringSingle) o;
        return Objects.equals(nameId, that.nameId) && value.equals(that.value) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameId, value, description);
    }

    public StringSingle copy(@Nullable String nameId, @NonNull String value, @Nullable String description) {
        return new StringSingle(nameId, value, description);
    }

    public StringSingle copy(@Nullable String nameId, @NonNull String value) {
        return new StringSingle(nameId, value, this.description);
    }

    public StringSingle copy(@Nullable String nameId) {
        return new StringSingle(nameId, this.value, this.description);
    }
}
