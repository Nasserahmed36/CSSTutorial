package com.atypon.domain.dao;

import com.atypon.domain.Identity;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IdentityDaoImpl implements IdentityDao {

    private final DataSource dataSource;

    public IdentityDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public boolean createIdentity(Identity identity) {
        String sql = "INSERT  INTO identity(username, first_name,last_name," +
                "password,email, type) values (?,?,?,?,?,?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, identity.getUsername());
            statement.setString(2, identity.getFirstName());
            statement.setString(3, identity.getLastName());
            statement.setString(4, identity.getPassword());
            statement.setString(5, identity.getEmail());
            statement.setString(6, identity.getType());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Identity getIdentity(String username) {
        Identity identity = null;
        String sql = "SELECT * FROM identity WHERE username=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                identity = extractIdentity(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return identity;
    }

    @Override
    public Identity getIdentity(String username, String password) {
        Identity identity = null;
        String sql = "SELECT * FROM identity WHERE username=? AND password=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                identity = extractIdentity(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return identity;
    }


    @Override
    public boolean updateIdentity(Identity identity) {
        String sql = "UPDATE identity SET first_name=?,last_name=?," +
                "password=?, email=? WHERE username=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, identity.getFirstName());
            statement.setString(2, identity.getLastName());
            statement.setString(3, identity.getPassword());
            statement.setString(4, identity.getEmail());
            statement.setString(5, identity.getUsername());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteIdentity(String username) {
        String sql = "DELETE FROM identity WHERE username=?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public List<Identity> getAll() {
        List<Identity> identities = new ArrayList<>();
        String sql = "SELECT * FROM identity";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Identity identity = extractIdentity(resultSet);
                identities.add(identity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return identities;
    }

    private Identity extractIdentity(ResultSet resultSet) throws SQLException {
        Identity identity = new Identity();
        identity.setUsername(resultSet.getString("username"));
        identity.setPassword(resultSet.getString("password"));
        identity.setFirstName(resultSet.getString("first_name"));
        identity.setLastName(resultSet.getString("last_name"));
        identity.setEmail(resultSet.getString("email"));
        identity.setType(resultSet.getString("type"));
        return identity;
    }


}
