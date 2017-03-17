package ru.ssau.pokubuy.domain.user;

public interface Users extends Iterable<User> {
    User add(String username, String password, String avatar, UserRole role);
}
