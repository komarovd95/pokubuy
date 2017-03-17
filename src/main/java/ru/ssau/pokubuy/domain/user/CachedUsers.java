package ru.ssau.pokubuy.domain.user;

import java.util.Iterator;
import java.util.Objects;

public final class CachedUsers implements Users {
    private final Users users;
    private Iterator<User> cached;

    public CachedUsers(Users users) {
        this.users = Objects.requireNonNull(users);
    }


    @Override
    public Iterator<User> iterator() {
        if (cached == null) { // TODO null
            cached = users.iterator();
        }

        return cached;
    }

    @Override
    public User add(String username, String password, String avatar, UserRole role) {
        return users.add(username, password, avatar, role);
    }
}
