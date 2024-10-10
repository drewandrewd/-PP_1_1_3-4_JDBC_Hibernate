package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    @Override
    public void createUsersTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS users (
                    id BIGINT PRIMARY KEY AUTO_INCREMENT,
                    name VARCHAR(50),
                    lastName VARCHAR(50),
                    age TINYINT
                );    
                """;
        try (Statement statement = Util.CONNECTION.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void dropUsersTable() {
        String sql = """
                DROP TABLE IF EXISTS users
                """;
        try (Statement statement = Util.CONNECTION.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)";
        try {
            Util.CONNECTION.setAutoCommit(false);
            try (PreparedStatement statement = Util.CONNECTION.prepareStatement(sql)) {
                statement.setString(1, name);
                statement.setString(2, lastName);
                statement.setString(3, String.valueOf(age));
                statement.executeUpdate();
                Util.CONNECTION.commit();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
                Util.CONNECTION.rollback();
            } finally {
                Util.CONNECTION.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

    }

    @Override
    public void removeUserById(long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try {
            Util.CONNECTION.setAutoCommit(false);
            try (PreparedStatement statement = Util.CONNECTION.prepareStatement(sql)) {
                statement.setString(1, String.valueOf(id));
                statement.executeUpdate();
                Util.CONNECTION.commit();
            } catch (SQLException e) {
                Util.CONNECTION.rollback();
                System.err.println(e.getMessage());
            } finally {
                Util.CONNECTION.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Statement statement = Util.CONNECTION.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                list.add(user);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE users";
        try (Statement statement = Util.CONNECTION.createStatement()) {
            statement.executeUpdate(sql);

        } catch (SQLException e) {
            System.err.println(e.getMessage());

        }
    }
}
