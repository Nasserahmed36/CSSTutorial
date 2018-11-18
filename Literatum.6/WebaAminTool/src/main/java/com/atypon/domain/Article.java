package com.atypon.domain;

import java.util.List;

public class Article {
    private String doi;
    private String title;
    private String issueDoi;
    private List<Author> authors;

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Author> getAuthors() {
        return authors;
    }


    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public String getIssueDoi() {
        return issueDoi;
    }

    public void setIssueDoi(String issueDoi) {
        this.issueDoi = issueDoi;
    }

    private static class Author {
        private String givenName;
        private String surName;
        private String degrees;

        public String getGivenName() {
            return givenName;
        }

        public void setGivenName(String givenName) {
            this.givenName = givenName;
        }

        public String getSurName() {
            return surName;
        }

        public void setSurName(String surName) {
            this.surName = surName;
        }

        public String getDegrees() {
            return degrees;
        }

        public void setDegrees(String degrees) {
            this.degrees = degrees;
        }
    }
}
