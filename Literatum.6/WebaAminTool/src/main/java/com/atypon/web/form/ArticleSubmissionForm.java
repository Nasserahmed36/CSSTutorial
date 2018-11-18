package com.atypon.web.form;

import javax.servlet.http.Part;

public class ArticleSubmissionForm {
    private String seriesIssn;
    private String articleFileName;
    private Part filePart;

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

    public Part getFilePart() {
        return filePart;
    }

    public void setFilePart(Part filePart) {
        this.filePart = filePart;
        if (filePart != null) {
            articleFileName = getFilename(filePart);
        }
    }

    private String getFilename(Part part) {
        String contentDispositionHeader =
                part.getHeader("content-disposition");
        String[] elements = contentDispositionHeader.split(";");
        for (String element : elements) {
            if (element.trim().startsWith("filename")) {
                return element.substring(element.indexOf('=') + 1)
                        .trim().replace("\"", "");
            }
        }
        return null;
    }
}
