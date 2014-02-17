package com.dmitrytarianyk;

public class StringBuilderHelper {

    private final StringBuilder builder;

    public StringBuilderHelper() {
        this.builder = new StringBuilder();
    }

    public StringBuilder appendLine(CharSequence s) {
        return builder.append(s).append(System.lineSeparator());
    }

    @Override
    public String toString() {
        return builder.toString();
    }
}
