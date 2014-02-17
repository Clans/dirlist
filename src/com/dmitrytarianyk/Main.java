package com.dmitrytarianyk;

import org.apache.commons.cli.*;

import java.io.File;

public class Main {

    private static final String OPT_D = "d";
    private static final String OPT_H = "h";

    public static void main(String[] args) {
        CommandLineParser parser = new BasicParser();
        Options options = new Options();

        Option folder = new Option(OPT_D, "dir", true, "path to directory");
        folder.setArgName("path");
        Option help = new Option("h", "help", false, "print this message");

        options.addOption(folder);
        options.addOption(help);

        try {
            CommandLine line = parser.parse(options, args);

            if (line.hasOption(OPT_D)) {
                String path = line.getOptionValue(OPT_D);
                File file = new File(path);

                if (file.canRead()) {
                    ListHelper helper = new ListHelper(file, new HtmlFileWriter(file));
                    helper.list();
                } else {
                    System.err.println("Invalid path");
                }
            } else {
                printUsage(options);
            }
        } catch (ParseException e) {
            System.err.println("Unexpected exception: " + e.getMessage());
            printUsage(options);
        }
    }

    private static void printUsage(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("java -jar dirlist.jar", "Options:", options, null, true);
    }
}
