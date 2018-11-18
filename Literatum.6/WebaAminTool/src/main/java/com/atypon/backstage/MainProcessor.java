package com.atypon.backstage;

import com.atypon.commons.FileUtils;
import com.atypon.domain.ArticleSubmission;

import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;

public class MainProcessor implements Processor<ArticleSubmission> {

    private static final String RESOURCES_PATH = MainProcessor.class.getResource("/").getPath();
    private static final String XSL_FILES = RESOURCES_PATH + File.separator + "xsl";
    private static final String JATS_HTML_XSL_FILE = XSL_FILES + File.separator + "jatsToHtml.xsl";
    private static final String OUTPUT_PATH = "/mnt/ssd/ideaProjects/Literatum/processedContent";

    private final FileTransformer jatsToHtmlTransformer;

    public MainProcessor() {
        jatsToHtmlTransformer = new JatsToHtmlTransformer(JATS_HTML_XSL_FILE);
    }


    @Override
    public void process(ArticleSubmission articleSubmission) throws ProcessingException {
        String unzipDir = unzip(articleSubmission);
        if (validate(unzipDir)) {
            try {
                process(articleSubmission, unzipDir);
            } catch (IOException | TransformerException e) {
                throw new ProcessingException(e);
            }
        } else {
            notify(false);
        }
    }

    private String unzip(ArticleSubmission articleSubmission) {
        String articlePath = articleSubmission.getPath();
        String destDir = articlePath.replace(".zip", "");
        FileUtils.unzip(articlePath, destDir);
        return destDir;
    }

    private boolean validate(String unzipDir) {
        return true;
    }

    private void process(ArticleSubmission articleSubmission, String unzipDir) throws ProcessingException, IOException, TransformerException {
        ArticleSubmissionNavigator navigator = new ArticleSubmissionNavigator(unzipDir);
        File article = navigator.getArticleFile();

        File outputDir = new File(OUTPUT_PATH + File.separator +
                articleSubmission.getArticleFileName().replace(".zip",""));
        outputDir.mkdirs();
        addDtd(navigator);
        jatsToHtmlTransformer.transform(article.getPath(),
                outputDir.getPath() + File.separator + navigator.getArticleDoi() + ".html");
    }

    private void addDtd(ArticleSubmissionNavigator navigator) throws IOException {
        File dtdFile= new File(navigator.getArticleDir().getAbsolutePath() + File.separator + "JATS-archivearticle1.dtd");
        dtdFile.createNewFile();
    }

    private void notify(boolean success) {

    }

    public static void main(String[] args) {
    }
}
