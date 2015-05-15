package com.example.rss.util;

public class HtmlUtil {

    /**
     * Removes hard-coded width and height in images and iframes to display them properly in WebView
     */
    public static String cleanHtmlWidthAndHeights(String html) {
        return html
                .replaceAll("height=\"[0-9]+\"", " ")
                .replaceAll("width=\"[0-9]+\"", " ")
                .replaceAll("height: [0-9]+[\\w]+", " ")
                .replaceAll("width: [0-9]+[\\w]+", " ");
    }

    /**
     * Adds css to auto-size all images and iframes properly
     */
    public static String addAutoSizeSettings(String html) {

        return "<html>" +
                "<head>" +
                "<style>" +
                "iframe {max-width: 100%; width:auto; height: auto;} " +
                "img {max-width: 100%; width:auto; height: auto;}" +
                "</style>" +
                "</head>" +
                "<body>" +
                html +
                "</body>" +
                "</html>";
    }

}
