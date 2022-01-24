package com.example.gsontestingground;

import androidx.annotation.NonNull;

class Person {
    final String first;
    final String last;

    Person(String first, String last) {
        this.first = first;
        this.last = last;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("Person {%s, %s}", first, last);
    }
}
