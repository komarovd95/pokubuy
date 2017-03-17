package ru.ssau.pokubuy.domain.category;

public interface Category {
    long id();
    String name();
    Category superCategory();
    Categories subCategories();
}
