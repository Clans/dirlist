package com.dmitrytarianyk;

import java.io.*;
import java.nio.charset.Charset;

public class HtmlFileWriter {

    private static final String INDEX_HTML = "listing.html";

    private final File file;

    public HtmlFileWriter(File file) {
        this.file = new File(file.getAbsolutePath() + File.separator + INDEX_HTML);
    }

    public void write(String s) {
        try (OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(file), Charset.forName("UTF-8"))) {
            out.write(s);
            System.out.println("Done");
            System.out.println("Your file is here: " + file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
