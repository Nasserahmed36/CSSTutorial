package com.atypon.domain;

public class ArticleSubmission {
    private String seriesIssn;
    private String articleFileName;
    private String status;
    private long timestamp;
    private String path;

    public String getSeriesIssn() {
        return seriesIssn;
    }

    public void setSeriesIssn(String seriesIssn) {
        this.seriesIssn = seriesIssn;
    }

    public String getArticleFileName() {
        return articleFileName;
    }

    public void setArticleFileName(String articleFileName) {
        this.articleFileName = articleFileName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "ArticleSubmission{" +
                "seriesIssn='" + seriesIssn + '\'' +
                ", articleFileName='" + articleFileName + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
