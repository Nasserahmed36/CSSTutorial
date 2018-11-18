package com.atypon.domain;

import java.util.HashMap;
import java.util.Map;

public enum LicenceType {

    FREE(1),
    LIMITED(2);

    private int id;
    private static Map<Integer, LicenceType> idToLicenceMap;

    static {
        idToLicenceMap = new HashMap<>();
        for (LicenceType licenceType : LicenceType.values()) {
            idToLicenceMap.put(licenceType.id, licenceType);
        }
    }

    LicenceType(int id) {
        this.id = id;
    }

    public static LicenceType getLicenceType(int id) {
        return idToLicenceMap.get(id);
    }


}
