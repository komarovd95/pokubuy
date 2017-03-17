package ru.ssau.pokubuy.domain.category;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public final class PgSubCategories implements Categories {
    private transient final DataSource dataSource;

    private final long superCategoryId;

    public PgSubCategories(DataSource dataSource, long superCategoryId) {
        this.dataSource = Objects.requireNonNull(dataSource);
        this.superCategoryId = superCategoryId;
    }

    @Override
    public Iterator<Category> iterator() {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT c.category_id, c.category_name FROM categories c " +
                            "WHERE c.category_super_id = ?"
            );

            statement.setLong(1, superCategoryId);

            ResultSet resultSet = statement.executeQuery();
            List<Category> categories = new ArrayList<>();

            while (resultSet.next()) {
                categories.add(new PgCategory(
                        dataSource,
                        resultSet.getLong("category_id"),
                        resultSet.getString("category_name")
                ));
            }

            return Collections.unmodifiableList(categories).iterator();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
