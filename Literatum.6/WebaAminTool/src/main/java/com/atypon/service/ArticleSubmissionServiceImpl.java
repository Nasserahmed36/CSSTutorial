package com.atypon.service;

import com.atypon.domain.ArticleSubmission;
import com.atypon.domain.dao.ArticleSubmissionDao;

import java.io.InputStream;
import java.util.List;

public class ArticleSubmissionServiceImpl implements ArticleSubmissionService {

    private ArticleSubmissionDao dao;

    public ArticleSubmissionServiceImpl(ArticleSubmissionDao dao) {
        this.dao = dao;
    }

    @Override
    public boolean save(ArticleSubmission articleSubmission, InputStream fileInputStream) {
        return dao.save(articleSubmission, fileInputStream);
    }


    @Override
    public List<ArticleSubmission> getAll() {
        return dao.getAll();
    }

//
//      articleSubmission.setArticleFileName(form.getArticleFileName());
//            articleSubmission.setSeriesIssn(form.getSeriesIssn());
//            articleSubmission.setTimestamp(System.currentTimeMillis());
//    String articlePath = outputPath + File.separator +
//            articleSubmission.getSeriesIssn() + File.separator +
//            articleSubmission.getArticleFileName();
}
