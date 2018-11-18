package com.atypon.web.form;


public class JournalForm {
    private String printIssn;
    private String electronicIssn;
    private String id;
    private String title;
    private String publisherName;
    private String publisherLocation;


    public String getPrintIssn() {
        return printIssn;
    }

    public void setPrintIsnn(String pIsnn) {
        this.printIssn = pIsnn;
    }

    public String getElectronicIssn() {
        return electronicIssn;
    }

    public void setElectronicIssn(String eIsnn) {
        this.electronicIssn = eIsnn;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getPublisherLocation() {
        return publisherLocation;
    }

    public void setPublisherLocation(String publisherLocation) {
        this.publisherLocation = publisherLocation;
    }


}
