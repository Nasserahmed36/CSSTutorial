package com.atypon.domain.dao;

import com.atypon.domain.ContentLicence;
import com.atypon.domain.Licence;
import com.atypon.domain.UserLicence;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LicenceDaoImpl implements LicenceDao {

    private final DataSource dataSource;

    public LicenceDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public boolean createContentLicence(ContentLicence contentLicence) {
        String sql = "INSERT  INTO content_licence(content_id, licence_id," +
                " period, price) values (?,?,?,?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, contentLicence.getContentId());
            statement.setInt(2, contentLicence.getLicence().getId());
            statement.setInt(3, contentLicence.getPeriod());
            statement.setFloat(4, contentLicence.getPrice().floatValue());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean createUserLicence(UserLicence userLicence) {
        String sql = "INSERT INTO user_content_licence(username, content_licence_id," +
                " start_date) VALUES (?,?,?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, userLicence.getUsername());
            statement.setInt(2, userLicence.getContentLicence().getId());
            statement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteContentLicence(int contentLicenceId) {
        String sql = "DELETE FROM content_licence WHERE id=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, contentLicenceId);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteUserLicence(UserLicence userLicence) {
        String sql = "DELETE FROM user_content_licence  WHERE " +
                "username=? AND content_licence_id=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, userLicence.getUsername());
            statement.setInt(2, userLicence.getContentLicence().getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateContentLicence(ContentLicence contentLicence) {
        String sql = "UPDATE content_licence SET period=?, price=? " +
                "WHERE id=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, contentLicence.getPeriod());
            statement.setFloat(2, contentLicence.getPrice().floatValue());
            statement.setInt(3, contentLicence.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateUserLicenceDate(UserLicence userLicence) {
        String sql = "UPDATE user_content_licence SET start_date=? " +
                "WHERE content_licence_id=? and username=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setTimestamp(1, new Timestamp(userLicence.getStartDate()));
            statement.setInt(2, userLicence.getContentLicence().getId());
            statement.setString(3, userLicence.getUsername());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<UserLicence> getLicences(String userName, String contentId) {
        List<UserLicence> licences = new ArrayList<>();
        String sql = "SELECT l.id, l.type, cl.id ,cl.content_id,cl.period,cl.price ," +
                " ucl.start_date, ucl.username  from user_content_licence ucl join " +
                "content_licence cl on ucl.content_licence_id = cl.id join licence l on " +
                "cl.licence_id = l.id where username=? AND content_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, userName);
            statement.setString(2, contentId);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                Licence licence = new Licence();
                licence.setId(resultSet.getInt(1));
                licence.setType(resultSet.getString(2));
                ContentLicence contentLicence = new ContentLicence();
                contentLicence.setLicence(licence);
                contentLicence.setId(resultSet.getInt(3));
                contentLicence.setContentId(resultSet.getString(4));
                contentLicence.setPeriod(resultSet.getInt(5));
                contentLicence.setPrice(new BigDecimal(resultSet.getFloat(6)));
                UserLicence userLicence = new UserLicence();
                userLicence.setContentLicence(contentLicence);
                userLicence.setStartDate(resultSet.getTimestamp(7).getTime());
                userLicence.setUsername(resultSet.getString(8));
                licences.add(userLicence);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return licences;
    }

    @Override
    public List<ContentLicence> getLicences(String contentId) {
        List<ContentLicence> licences = new ArrayList<>();
        String sql = "select l.id,l.type, cl.id, cl.content_id, cl.period, cl.price" +
                " from content_licence cl join licence l on cl.licence_id = l.id " +
                "WHERE cl.content_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, contentId);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                Licence licence = new Licence();
                licence.setId(resultSet.getInt(1));
                licence.setType(resultSet.getString(2));
                ContentLicence contentLicence = new ContentLicence();
                contentLicence.setLicence(licence);
                contentLicence.setId(resultSet.getInt(3));
                contentLicence.setContentId(resultSet.getString(4));
                contentLicence.setPeriod(resultSet.getInt(5));
                contentLicence.setPrice(new BigDecimal(resultSet.getFloat(6)));
                licences.add(contentLicence);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return licences;
    }

}
