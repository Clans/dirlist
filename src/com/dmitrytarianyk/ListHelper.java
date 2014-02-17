package com.dmitrytarianyk;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

public class ListHelper {

    private final File file;
    private final HtmlFileWriter writer;

    private String indent = "  ";
    private File originalFile;
    private StringBuilderHelper sbh = new StringBuilderHelper();

    public ListHelper(File file, HtmlFileWriter writer) {
        this.originalFile = file;
        this.file = file;
        this.writer = writer;
    }

    public void list() {
        if (file == null) {
            throw new NullPointerException("File cannot be null...");
        }

        if (!file.isDirectory()) {
            System.out.println(file.getAbsolutePath() + " is not a directory");
            return;
        }

        appendHeaders(sbh);
        sbh.appendLine("<div id=\"listContainer\">");

        sbh.appendLine("<div class=\"search-wrapper\">");
        sbh.appendLine("<form>");
        sbh.appendLine("<input type=\"text\" name=\"focus\" required class=\"search-box\" placeholder=\"Search...\"/>");
        sbh.appendLine("<button class=\"close-icon\" type=\"reset\"></button>");
        sbh.appendLine("</form>");
        sbh.appendLine("</div>");

        sbh.appendLine("<div class=\"listControl\"><a id=\"expandList\">Expand All</a><a id=\"collapseList\">Collapse All</a></div>");
        sbh.appendLine("<ul id=\"expList\">");
        recursiveList(file, false);
        sbh.appendLine("</ul>");
        sbh.appendLine("</div>");
        appendFooters(sbh);
        writer.write(sbh.toString());
    }

    public void recursiveList(File file, boolean printFolder) {
        if (file.isDirectory()) {
            if (printFolder) {
                indent = getIndent(file);
                sbh.appendLine(indent + "<li><div class=\"folder\">" + file.getName() + "</div>");
                sbh.appendLine(indent + "<ul>");
            }

            File allFiles[] = file.listFiles();

            Arrays.sort(allFiles, new FileNameComparator());
            Arrays.sort(allFiles, new FileTypeComparator());

            for (File aFile : allFiles) {
                recursiveList(aFile, true);
            }

            if (printFolder) {
                sbh.appendLine(indent + "</ul>");
                sbh.appendLine(indent + "</li>");
            }
        } else if (file.isFile()) {
            sbh.appendLine(indent + "  <li><div class=\"file\">" + file.getName() + "</div></li>");
        }
    }

    private String getIndent(File fileObject) {
        String original = originalFile.getAbsolutePath();
        String fileStr = fileObject.getAbsolutePath();
        String subString = fileStr.substring(original.length(), fileStr.length());
        String indent = "  ";
        for (int index = 0; index < subString.length(); index++) {
            char aChar = subString.charAt(index);
            if (aChar == File.separatorChar) {
                indent = indent + "  ";
            }
        }
        return indent;
    }

    public void appendHeaders(StringBuilderHelper sbh) {
        sbh.appendLine("<!DOCTYPE HTML>");
        sbh.appendLine("<html>");
        sbh.appendLine("<head>");
        sbh.appendLine("<title>" + originalFile.getAbsolutePath() + " directory listing</title>");
        sbh.appendLine("<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\" />");
        sbh.appendLine("<script type=\"text/javascript\" src=\"http://ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js\"></script>");
        appendCss(sbh);
        appendJs(sbh);
        sbh.appendLine("</head>");
        sbh.appendLine("<body>");
    }

    private void appendCss(StringBuilderHelper sbh) {
        sbh.appendLine("<style type='text/css'>");
        sbh.appendLine(".search-box, .close-icon, .search-wrapper {\n" +
                "    position: relative;\n" +
                "    padding: 8px;\n" +
                "}\n" +
                "\n" +
                ".search-wrapper {\n" +
                "    width: 500px;\n" +
                "    margin: 20px 0 15px 34px;\n" +
                "}\n" +
                "\n" +
                ".search-box {\n" +
                "    width: 80%;\n" +
                "    border: 1px solid #ccc;\n" +
                "    outline: 0;\n" +
                "    border-radius: 10px;\n" +
                "    box-shadow: inset 0 0px 3px #ccc;\n" +
                "    background-color: #f9f9f9;\n" +
                "}\n" +
                "\n" +
                ".search-box:focus {\n" +
                "    border: 1px solid #999;\n" +
                "    background-color: #fff;\n" +
                "}\n" +
                "\n" +
                ".close-icon {\n" +
                "    border: 1px solid transparent;\n" +
                "    background-color: transparent;\n" +
                "    display: inline-block;\n" +
                "    vertical-align: middle;\n" +
                "    outline: 0;\n" +
                "    cursor: pointer;\n" +
                "}\n" +
                "\n" +
                ".close-icon:after {\n" +
                "    content: \"X\";\n" +
                "    display: block;\n" +
                "    width: 15px;\n" +
                "    height: 15px;\n" +
                "    position: absolute;\n" +
                "    background-color: #cccccc;\n" +
                "    z-index: 1;\n" +
                "    right: 30px;\n" +
                "    top: 0;\n" +
                "    bottom: 6px;\n" +
                "    margin: auto;\n" +
                "    padding: 2px;\n" +
                "    border-radius: 50%;\n" +
                "    text-align: center;\n" +
                "    color: white;\n" +
                "    font-weight: normal;\n" +
                "    font-family: Verdana,serif;\n" +
                "    font-size: 12px;\n" +
                "    cursor: pointer;\n" +
                "}\n" +
                "\n" +
                ".search-box:not(:valid) ~ .close-icon {\n" +
                "    display: none;\n" +
                "}\n" +
                "\n" +
                "#listContainer {\n" +
                "    width: 600px;\n" +
                "    margin: 0 auto;\n" +
                "}\n" +
                "\n" +
                "#expList li {\n" +
                "    list-style-type: none;\n" +
                "}\n" +
                "\n" +
                ".listControl {\n" +
                "    margin: 15px 0 15px 40px;\n" +
                "}\n" +
                "\n" +
                ".listControl a {\n" +
                "    background-color: #f9f9f9;\n" +
                "    border: 1px solid #dcdcdc;\n" +
                "    color: #666666;\n" +
                "    cursor: pointer;\n" +
                "    height: 1.5em;\n" +
                "    line-height: 1.5em;\n" +
                "    margin-right: 5px;\n" +
                "    padding: 4px 10px;\n" +
                "    font-weight: bold;\n" +
                "    border-radius: 10px;\n" +
                "}\n" +
                "\n" +
                ".listControl a:hover {\n" +
                "    background-color: #e9e9e9;\n" +
                "    border: 1px solid #ccc;\n" +
                "}\n" +
                "\n" +
                "#expList div {\n" +
                "    padding: 2px 0 2px 6px;\n" +
                "    border: 1px solid #fff;\n" +
                "}\n" +
                "\n" +
                "#expList div:hover {\n" +
                "    background-color: #e9e9e9;\n" +
                "    cursor: pointer;\n" +
                "    border-radius: 6px;\n" +
                "    border: 1px solid #ccc;\n" +
                "}\n" +
                "\n" +
                "#expList .file:before {\n" +
                "    content: \"\\00A0\\00A0\\00A0\\00A0\\00A0\";\n" +
                "}\n" +
                "\n" +
                "#expList .collapsed:before {\n" +
                "    content: \"\\25BA\\00A0\";\n" +
                "}\n" +
                "\n" +
                "#expList .expanded:before {\n" +
                "    content: \"\\25BC\\00A0\";\n" +
                "}");
        sbh.appendLine("</style>");
    }

    private void appendJs(StringBuilderHelper sbh) {
        sbh.appendLine("<script type=\"text/javascript\">");
        sbh.appendLine("$(function () {\n" +
                "    $(\"#expList li > ul\").hide();\n" +
                "    $(\"#expList li\").click(function (event) {\n" +
                "        $(this).children('ul').stop().slideToggle(250);\n" +
                "        $(this).has('ul').children(':first-child').toggleClass('expanded');\n" +
                "        event.stopPropagation();\n" +
                "    });\n" +
                "    $('#expList li > div.folder').addClass('collapsed');\n" +
                "});\n" +
                "\n" +
                "$(function () {\n" +
                "    $('#expandList').click(function (event) {\n" +
                "        $(\"#expList li > ul\").show();\n" +
                "        $('#expList li > div.folder').addClass('expanded');\n" +
                "    });\n" +
                "});\n" +
                "\n" +
                "$(function () {\n" +
                "    $('#collapseList').click(function() {\n" +
                "        $(\"#expList li > ul\").hide();\n" +
                "        $('#expList li > div.folder').removeClass('expanded');\n" +
                "    });\n" +
                "});");
        sbh.appendLine("</script>");
    }

    public void appendFooters(StringBuilderHelper sbh) {
        sbh.appendLine("</body>");
        sbh.appendLine("</html>");
    }

    class FileTypeComparator implements Comparator<File> {

        @Override
        public int compare(File file1, File file2) {
            if (file1.isDirectory() && file2.isFile())
                return -1;
            if (file1.isDirectory() && file2.isDirectory()) {
                return 0;
            }
            if (file1.isFile() && file2.isFile()) {
                return 0;
            }
            return 1;
        }
    }

    class FileNameComparator implements Comparator<File> {

        @Override
        public int compare(File file1, File file2) {
            return String.CASE_INSENSITIVE_ORDER.compare(file1.getName(), file2.getName());
        }
    }
}
