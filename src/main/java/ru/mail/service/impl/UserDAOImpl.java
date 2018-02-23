package ru.mail.service.impl;

import org.springframework.stereotype.Service;
import ru.mail.service.UserDAO;
import ru.mail.service.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Denis Monich
 * this class realise all logic to create valid users adn get valid/invalid user
 */
@Service
public class UserDAOImpl implements UserDAO {


    private List<User> users;

    public UserDAOImpl() {
    }

    /**
     *
     * @param username email or login which user wrote to login form
     * @return valid user or null if user invalid
     */
    @Override
    public User loadUserByUsername(String username){
        User returnUser=null;
        iniDataForTesting();
        for (User user : users) {
            if(user.getUserEmail().equals(username)){
                returnUser=new User(username,user.getUserPassword());
                break;
            }
        }
        return returnUser;
    }
    /*
    creating default users
    */
    private void iniDataForTesting() {
        users = new ArrayList<User>();
        users.add(new User("denis@mail.ru", "1234"));
        users.add(new User("vadim@mail.ru", "1234"));
        users.add(new User("leha@mail.ru", "1234"));
        users.add(new User("timoha@mail.ru", "1234"));
    }
}
