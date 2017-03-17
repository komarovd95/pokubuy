package ru.ssau.pokubuy.domain.user;

public interface User {
    long id();
    String username();
    String password();
    String avatar();
    UserRole role();
}
