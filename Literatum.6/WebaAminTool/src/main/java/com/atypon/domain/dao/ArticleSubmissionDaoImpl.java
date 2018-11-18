package com.atypon.domain.dao;

import com.atypon.commons.FileUtils;
import com.atypon.domain.ArticleSubmission;

import javax.sql.DataSource;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArticleSubmissionDaoImpl implements ArticleSubmissionDao {

    private final DataSource dataSource;
    private final String outputDirPath;

    public ArticleSubmissionDaoImpl(DataSource dataSource, String outputDirPath) {
        this.dataSource = dataSource;
        this.outputDirPath = outputDirPath;
    }

    public boolean save(ArticleSubmission submission, InputStream inputStream) {

        String sql = "INSERT  INTO submission(series_issn, file_name, date," +
                "status, path) values (?,?,?,?,?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            connection.setAutoCommit(false);
            statement.setString(1, submission.getSeriesIssn());
            statement.setString(2, submission.getArticleFileName());
            statement.setTimestamp(3, new Timestamp(submission.getTimestamp()));
            statement.setString(4, submission.getStatus());
            statement.setString(5, submission.getPath());
            statement.execute();
            String outputFile = outputDirPath + File.separator + submission.getSeriesIssn() +
                    File.separator + submission.getArticleFileName();
            createNecessaryParentDirs(outputFile);
            FileUtils.download(inputStream, outputFile);
            connection.commit();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<ArticleSubmission> getAll() {
        List<ArticleSubmission> submissions = new ArrayList<>();
        String sql = "SELECT * FROM submission";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ArticleSubmission submission = extractSubmission(resultSet);
                submissions.add(submission);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return submissions;
    }

    private ArticleSubmission extractSubmission(ResultSet resultSet) throws SQLException {
        ArticleSubmission submission = new ArticleSubmission();
        submission.setSeriesIssn(resultSet.getString("series_issn"));
        submission.setArticleFileName(resultSet.getString("file_name"));
        submission.setTimestamp(resultSet.getTimestamp("date").getTime());
        submission.setPath(resultSet.getString("path"));
        submission.setStatus(resultSet.getString("status"));
        return submission;
    }

    private void createNecessaryParentDirs(String filePath) throws IOException {
        if(!new File(filePath).getParentFile().mkdirs()) {
            throw new IOException("File dirs cannot be created");
        }
    }


}
