package ru.ssau.pokubuy;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import ru.ssau.pokubuy.domain.user.PgUsers;
import ru.ssau.pokubuy.domain.user.User;
import ru.ssau.pokubuy.domain.user.UserRole;
import ru.ssau.pokubuy.domain.user.Users;

import javax.sql.DataSource;

public final class Main {
    public static void main(String[] args) {
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl("jdbc:postgresql://localhost:5432/pokubuy");
        config.setUsername("postgres");
        config.setPassword("");
        config.setAutoCommit(false);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        DataSource dataSource = new HikariDataSource(config);

        Users users = new PgUsers(dataSource);

//        users.add("diman", "admin", "avatar", UserRole.ADMIN);

        for (User user : users) {
            System.out.println(user.username());
        }

    }
}
