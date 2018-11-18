package com.atypon.backstage;

public interface FileTransformer {
    void transform(String sourceFileLocation, String outputFileLocation) throws Exception;
}
