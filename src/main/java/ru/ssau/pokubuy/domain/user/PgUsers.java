package ru.ssau.pokubuy.domain.user;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

public final class PgUsers implements Users {
    private transient final DataSource dataSource;

    public PgUsers(DataSource dataSource) {
        this.dataSource = Objects.requireNonNull(dataSource);
    }

    @Override
    public Iterator<User> iterator() {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT u.* FROM users u"
            );

            ResultSet resultSet = statement.executeQuery();
            List<User> users = new ArrayList<>();

            while (resultSet.next()) {
                users.add(new PgUser(
                        resultSet.getLong("user_id"),
                        resultSet.getString("user_name"),
                        resultSet.getString("user_pass"),
                        resultSet.getString("user_avatar"),
                        UserRole.valueOf(resultSet.getString("user_role"))
                ));
            }

            return Collections.unmodifiableList(users).iterator();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User add(String username, String password, String avatar, UserRole role) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO users (user_name, user_pass, user_avatar, user_role) VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            statement.setString(1, Objects.requireNonNull(username));
            statement.setString(2, Objects.requireNonNull(password));
            statement.setString(3, Objects.requireNonNull(avatar));
            statement.setString(4, Objects.requireNonNull(role).toString());

            if (statement.executeUpdate() == 0) {
                throw new RuntimeException("No rows affected. INSERT failed");
            }

            ResultSet resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                connection.commit();

                return new PgUser(resultSet.getLong(1), username, password, avatar, role);
            } else {
                connection.rollback();

                throw new RuntimeException("No generated keys. INSERT failed");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
