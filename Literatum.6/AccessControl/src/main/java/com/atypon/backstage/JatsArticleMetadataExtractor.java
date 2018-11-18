package com.atypon.backstage;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.Map;

public class JatsArticleMetadataExtractor implements DataExtractor {

    @Override
    public Map<String, String> extract(String fileLocation) {
        return null;
    }

    private static class JatsHandler extends DefaultHandler {


        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) {
        }
    }

}
