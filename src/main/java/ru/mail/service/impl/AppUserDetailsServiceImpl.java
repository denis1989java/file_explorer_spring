package ru.mail.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.mail.service.UserDAO;
import ru.mail.service.model.AppUserPrincipal;
import ru.mail.service.model.User;

/**
 * @author Denis Monich
 * this class implements loading exist's user and transfer it for shaping of Principal object
 */
@Service(value = "appUserDetailsService")
public class AppUserDetailsServiceImpl implements UserDetailsService {

    private final UserDAO userDao;

    @Autowired
    public AppUserDetailsServiceImpl(UserDAO userDAO) {
        this.userDao = userDAO;
    }

    /**
     *
     * @param username email or login which user wrote to login form
     * @return UserPrincipal object
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userDao.loadUserByUsername(username.toLowerCase());
        if (user == null) {
            return null;
        }
        return new AppUserPrincipal(user);
    }
}
