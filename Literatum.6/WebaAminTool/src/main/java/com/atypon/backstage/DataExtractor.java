package com.atypon.backstage;

import java.util.Map;

public interface DataExtractor {
    Map<String, String> extract(String fileLocation);
}
