package util;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Bill on 2017/4/13.
 * Email androidBaoCP@163.com
 */

public class PoemXmlParser {
    public ArrayList<XmlEntry> parser(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser parser = factory.newPullParser();
            // 设定不处理命名空间
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, "utf-8");
            parser.nextTag();
            return readXml(parser);
        } finally {
            in.close();
        }
    }

    // 从getResources()中获取的为XmlResourceParser，继承自XmlPullParser
    public ArrayList<XmlEntry> parser(XmlPullParser parser) throws XmlPullParserException, IOException {
        return readXml(parser);
    }

    private ArrayList<XmlEntry> readXml(XmlPullParser parser) throws IOException, XmlPullParserException {
        ArrayList<XmlEntry> entries = new ArrayList<>();
        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType != XmlPullParser.START_TAG) {
                eventType = parser.next();
                continue;
            }
            String name = parser.getName();
            if (name.equals("entry")) {
                eventType = parser.next();
                continue;
            }
            if (name.equals("poem")) {
                entries.add(readEntry(parser));
            }
        }
        return entries;
    }

    private XmlEntry readEntry(XmlPullParser parser) throws IOException, XmlPullParserException {

        String title = null;
        String author = null;
        String content = null;

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            switch (name) {
                case "title":
                    title = readText(parser);
                    break;
                case "author":
                    author = readText(parser);
                    break;
                case "content":
                    content = readText(parser);
                    break;
            }
        }
        return new XmlEntry(title, author, content);
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private void skip(XmlPullParser parser) throws IOException, XmlPullParserException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}
