package com.atypon.backstage;

import java.io.File;

public class ArticleSubmissionNavigator {

    private static final String ISSUE_FILES_DIR_NAME = "issue-files";


    private final String rootDir;
    private File manifest;
    private File issueDir;
    private File issueFilesDir;
    private File articleDir;
    private File issueMetadataFile;
    private File articleFile;
    private String articleDoi;
    private String issueId;

    public ArticleSubmissionNavigator(String rootDir) {
        this.rootDir = rootDir;
    }


    public File getManifestFile() {
        if (manifest == null) {
            File root = new File(rootDir);
            File[] files = root.listFiles();
            for (File file : files) {
                if (file.isFile()) {
                    manifest = file;
                    break;
                }
            }
        }
        return manifest;
    }

    public File getIssueDir() {
        if (issueDir == null) {
            File root = new File(rootDir);
            File[] files = root.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    issueDir = file;
                    break;
                }
            }
        }
        return issueDir;
    }

    public File getIssueFilesDir() {
        if(issueFilesDir == null) {
            File issueDir = getIssueDir();
            File[] files = issueDir.listFiles();
            for (File file : files) {
                if (file.getName().equals(ISSUE_FILES_DIR_NAME)) {
                    issueFilesDir = file;
                    break;
                }
            }
        }
        return issueFilesDir;
    }

    public File getArticleDir() {
        if(articleDir == null) {
            File issueDir = getIssueDir();
            File[] files = issueDir.listFiles();
            for (File file : files) {
                if (!file.getName().equals(ISSUE_FILES_DIR_NAME)) {
                    articleDir = file;
                    break;
                }
            }
        }
        return articleDir;
    }


    public File getIssueMetadataFile() {
        String issueId = getIssueId();
        File issueFilesDir = getIssueFilesDir();
        return new File(issueFilesDir.getPath() + File.separator + issueId + ".xml");
    }

    public File getArticleFile() {
        String articleDoi = getArticleDoi();
        File articleDir = getArticleDir();
        return new File(articleDir + File.separator + articleDoi + ".xml");
    }

    public String getArticleDoi() {
        return getArticleDir().getName();
    }

    public String getIssueId() {
        return getIssueDir().getName();
    }

}
