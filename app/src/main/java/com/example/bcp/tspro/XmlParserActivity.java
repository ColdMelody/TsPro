package com.example.bcp.tspro;

import android.app.Activity;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

import adapter.XmlParserAdapter;
import util.PoemXmlParser;
import util.XmlEntry;

public class XmlParserActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xml_parser);
        ListView xmlContent = (ListView) findViewById(R.id.xml_content);
        XmlResourceParser poem = getResources().getXml(R.xml.poems);
        PoemXmlParser poemXmlParser = new PoemXmlParser();
        ArrayList<XmlEntry> list = null;
        try {
            list = poemXmlParser.parser(poem);
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
        XmlParserAdapter adapter = new XmlParserAdapter(this,list);
        xmlContent.setAdapter(adapter);
    }
}
