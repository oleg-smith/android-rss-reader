package com.example.rss.util;

import com.example.rss.RssItemDto;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FeedUtil {

    private static final String ELEMENT_CONTENT_ENCODED = "content:encoded";
    private static final String ELEMENT_TITLE = "title";
    private static final String ELEMENT_DESCRIPTION = "description";
    private static final String ELEMENT_PUB_DATE = "pubDate";
    private static final String ELEMENT_LINK = "link";
    public static final String DATE_FORMAT = "MM/dd - hh:mm:ss";

    /**
     * Download feed xml by URL
     */
    public static List<RssItemDto> downloadRssItems(String feedUrl) {

        List<RssItemDto> rssItems = new ArrayList<>();

        try {
            URL url = new URL(feedUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream is = conn.getInputStream();

                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();

                Document document = db.parse(is);
                Element element = document.getDocumentElement();

                NodeList nodeList = element.getElementsByTagName("item");

                if (nodeList.getLength() > 0) {

                    for (int i = 0; i < nodeList.getLength(); i++) {

                        Element entry = (Element) nodeList.item(i);

                        String content = entry.getElementsByTagName(ELEMENT_CONTENT_ENCODED)
                                .item(0)
                                .getFirstChild()
                                .getNodeValue();

                        content = HtmlUtil.cleanHtmlWidthAndHeights(content);

                        Element titleElement = (Element) entry.getElementsByTagName(ELEMENT_TITLE)
                                .item(0);

                        Element descriptionElement =
                                (Element) entry.getElementsByTagName(ELEMENT_DESCRIPTION).item(0);

                        Element publicationDateElement = (Element) entry.getElementsByTagName(ELEMENT_PUB_DATE).item(0);

                        Element linkElement = (Element) entry.getElementsByTagName(ELEMENT_LINK).item(0);

                        String title = titleElement.getFirstChild().getNodeValue();
                        String description = descriptionElement.getFirstChild().getNodeValue();
                        Date date = new Date(publicationDateElement.getFirstChild().getNodeValue());
                        String link = linkElement.getFirstChild().getNodeValue();

                        RssItemDto rssItem = new RssItemDto(title, description, date, link, content);

                        rssItems.add(rssItem);
                    }
                }

            }

        } catch (Exception e) {
            // todo proper error handling
            e.printStackTrace();
        }

        return rssItems;
    }

    public static String formatRssItem(RssItemDto item) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
        return String.format("%s  (%s)", item.getTitle(), sdf.format(item.getPubDate()));
    }
}
