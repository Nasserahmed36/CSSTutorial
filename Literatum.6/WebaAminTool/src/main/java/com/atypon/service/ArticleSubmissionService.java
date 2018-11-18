package com.atypon.service;

import com.atypon.domain.ArticleSubmission;

import java.io.InputStream;
import java.util.List;

public interface ArticleSubmissionService {
    boolean save(ArticleSubmission articleSubmission, InputStream fileInputStream);
    List<ArticleSubmission> getAll();
}
