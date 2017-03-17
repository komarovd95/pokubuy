package ru.ssau.pokubuy.domain.category;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public final class PgCategory implements Category {
    private transient final DataSource dataSource;

    private final long id;
    private final String name;

    public PgCategory(DataSource dataSource, long id, String name) {
        this.dataSource = Objects.requireNonNull(dataSource);
        this.id = id;
        this.name = Objects.requireNonNull(name);
    }

    @Override
    public long id() {
        return id;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Category superCategory() {
        return null;
    }

    @Override
    public Categories subCategories() {
        return new PgSubCategories(dataSource, id);
    }
}
