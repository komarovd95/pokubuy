package ru.ssau.pokubuy.domain.user;

import java.util.Objects;

public final class PgUser implements User {
    private final long id;
    private final String username;
    private final String password;
    private final String avatar;
    private final UserRole role;

    public PgUser(long id, String username, String password, String avatar, UserRole role) {
        this.id = id;
        this.username = Objects.requireNonNull(username);
        this.password = Objects.requireNonNull(password);
        this.avatar = Objects.requireNonNull(avatar);
        this.role = Objects.requireNonNull(role);
    }


    @Override
    public long id() {
        return id;
    }

    @Override
    public String username() {
        return username;
    }

    @Override
    public String password() {
        return password;
    }

    @Override
    public String avatar() {
        return avatar;
    }

    @Override
    public UserRole role() {
        return role;
    }
}
