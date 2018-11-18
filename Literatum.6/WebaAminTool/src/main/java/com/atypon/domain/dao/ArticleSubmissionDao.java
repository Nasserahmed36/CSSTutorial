package com.atypon.domain.dao;

import com.atypon.domain.ArticleSubmission;

import java.io.InputStream;
import java.util.List;

public interface ArticleSubmissionDao {
    boolean save(ArticleSubmission submission, InputStream fileInputStream);
    List<ArticleSubmission> getAll();

}
