package com.atypon.backstage;

import javax.xml.transform.TransformerException;
import java.io.IOException;

public interface FileTransformer {
    void transform(String sourceFileLocation,
                   String outputFileLocation) throws IOException, TransformerException;
}
