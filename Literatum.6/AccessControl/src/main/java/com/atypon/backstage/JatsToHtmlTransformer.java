package com.atypon.backstage;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class JatsToHtmlTransformer implements FileTransformer {

    private final String xslLocation;

    public JatsToHtmlTransformer(String xslLocation) {
        this.xslLocation = xslLocation;
    }

    @Override
    public void transform(String sourceFileLocation, String outputFileLocation) throws FileNotFoundException, TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Source xslDoc = new StreamSource(xslLocation);
        Source xmlSourceDoc = new StreamSource(sourceFileLocation);
        OutputStream outputHtmlFile = new FileOutputStream(outputFileLocation);
        Transformer transformer = transformerFactory.newTransformer(xslDoc);
        transformer.transform(xmlSourceDoc, new StreamResult(outputHtmlFile));
    }

}
