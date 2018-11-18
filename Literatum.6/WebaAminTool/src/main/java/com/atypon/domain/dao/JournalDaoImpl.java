package com.atypon.domain.dao;

import com.atypon.domain.Journal;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JournalDaoImpl implements JournalDao {

    private final DataSource dataSource;

    public JournalDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public boolean create(Journal journal) {
        String sql = "INSERT INTO journal  (print_issn, electronic_issn ,ID," +
                "title,publisher_name, publisher_location) values (?,?,?,?,?,?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, journal.getPrintIssn());
            statement.setString(2, journal.getElectronicIssn());
            statement.setString(3, journal.getId());
            statement.setString(4, journal.getTitle());
            statement.setString(5, journal.getPublisherName());
            statement.setString(6, journal.getPublisherLocation());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Journal get(String issn) {
        Journal journal = null;
        String sql = "SELECT print_issn, electronic_issn, ID, title, publisher_name," +
                " publisher_location FROM journal WHERE print_issn=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, issn);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                journal = extractJournal(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return journal;
    }

    private Journal extractJournal(ResultSet resultSet) throws SQLException {
        Journal journal = new Journal();
        journal.setPrintIssn(resultSet.getString(1));
        journal.setElectronicIssn(resultSet.getString(2));
        journal.setId(resultSet.getString(3));
        journal.setTitle(resultSet.getString(4));
        journal.setPublisherName(resultSet.getString(5));
        journal.setPublisherLocation(resultSet.getString(6));
        return journal;
    }

    @Override
    public boolean update(Journal journal) {
        String sql = "UPDATE journal SET electronic_issn=?," +
                "ID=?, title=?,publisher_name=?,publisher_location=? WHERE print_issn=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, journal.getElectronicIssn());
            statement.setString(2, journal.getId());
            statement.setString(3, journal.getTitle());
            statement.setString(4, journal.getPublisherName());
            statement.setString(5, journal.getPublisherLocation());
            statement.setString(6, journal.getPrintIssn());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(String issn) {
        String sql = "DELETE FROM journal WHERE print_issn=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, issn);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }
}
