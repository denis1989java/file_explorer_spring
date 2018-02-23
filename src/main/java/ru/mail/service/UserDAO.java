package ru.mail.service;

import ru.mail.service.model.User;

public interface UserDAO {
    User loadUserByUsername(String username);
}
